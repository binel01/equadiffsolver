/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodeResolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author binel
 */
public class GaussSiedelParallele extends AMethodeResolution {

    static final int NB_THREADS = 2;

    private double[][] matrice;
    private double[] b;
    private double epsilon;

    private double[] x;

    public GaussSiedelParallele(double[][] matrice, double[] b, double epsilon) {
        this.matrice = matrice;
        this.b = b;
        this.epsilon = epsilon;
    }

    @Override
    public double[] solve() {
        return solve(matrice, b, epsilon);
    }

    @Override
    public double[] solve(double[][] mat, double[] bVect, double eps) {
        int n = bVect.length;
        x = new double[n];
        
        for (int i = 0; i < n; i++) {
            x[i] = i;
        }
        
        long start = System.currentTimeMillis();
        
        List<Future<Double>> resultatsFuturs = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);
        
        double[] var = new double[2];
        while (!verifierPrecision(mat, x, bVect, eps)) {
            for (int i = 0; i < n; i++) {
                var[0] = 0.0;
                var[1] = 0.0;
                
                if (i >= 1 && i <= n - 2) {
                    Callable<Double> callable = new SolverCallable(0, i, i);
                    Callable<Double> callable2 = new SolverCallable(i+1, n, i);
                    Future<Double> future = executor.submit(callable);
                    Future<Double> future2 = executor.submit(callable2);
                    resultatsFuturs.add(future);
                    resultatsFuturs.add(future2);
                    
                    int t = 0;
                    for (Future<Double> f : resultatsFuturs) {
                        try {
                            var[t] = f.get();
                            t++;
                        } catch (InterruptedException | ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    }
                    
                    resultatsFuturs.clear();
                } 
                
                else {
                    if (i == 0) {
                        for (int j=1; j<n; j++) {
                            var[1] = var[1] + mat[i][j] * x[j];
                        }
                    }
                    else {
                        for (int j=0; j<n-1; j++) {
                            var[0] = var[0] + mat[i][j] * x[j];
                        }
                    }    
                }
                
                x[i] = (bVect[i] - var[0] - var[1]) / mat[i][i];
            }
        }

        executor.shutdownNow();
        
        long end = System.currentTimeMillis();
        
        System.out.println("Le temps mis est de "+ (end - start) + " milli secondes");

        return x;
    }

    public class SolverCallable implements Callable<Double> {

        private int start;
        private int end;
        private int i;

        public SolverCallable(int start, int end, int i) {
            this.start = start;
            this.end = end;
            this.i = i;
        }

        @Override
        public Double call() throws Exception {
            double somme = 0;

            for (int j=start; j<end; j++) {
                somme = somme + matrice[i][j] * x[j];
            }
            
            return somme;
        }
    }
}
