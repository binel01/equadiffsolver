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
public class JacobiParallele extends AMethodeResolution {
    
    static final int NB_THREADS = 5;
    
    private int n;
    private double[][] matrice1;
    private double[] b1;
    private double[] x1;
    private double epsilon1;
    
    private double[] resultat;
    
    
    public JacobiParallele(double[][] matrice, double[] b, double epsilon) {
        this.matrice1 = matrice;
        this.b1 = b;
        this.epsilon1 = epsilon;
    }
    
    @Override
    public double[] solve() {
        return solve(matrice1, b1, epsilon1);
    }
    
    @Override
    public double[] solve(double[][] mat, double[] bVect, double prec) {
        this.n = bVect.length;
        this.resultat = new double[n];
        this.x1 = new double[n];
        
        for (int i=0; i<n; i++) {
            x1[i] = 0;
        }
        
        long start = System.currentTimeMillis();
        
        List<Future<Double>> resultatsFuturs = new ArrayList<Future<Double>>();
        ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);
        
        int compteur = 0;
        while (!verifierPrecision(mat, x1, bVect, prec)) {
            x1 = resultat;
            
            // Début de la parallélisation
            for (int i=0; i<n; i++) {
                Callable<Double> callable = new SolverCallable(i);
                Future<Double> future = executor.submit(callable);
                resultatsFuturs.add(future);
            }
            
            // Récupération des résultats
            int t=0;
            for (Future<Double> f : resultatsFuturs) {
                try {
                    resultat[t++] = f.get();
                } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                }        
            }
                
            resultatsFuturs.clear();
        }
        
        executor.shutdownNow();
        
        long end = System.currentTimeMillis();
        
        System.out.println("Le temps mis est de "+ (end - start) + " milli secondes");
        
        return resultat;
    }
    
    public class SolverCallable implements Callable<Double>
    {
        private int i;
        
        public SolverCallable(int i) {
            this.i = i;    
        }
        
        
        @Override
        public Double call() throws Exception {
            double somme = 0;
            double result = 0;
            
            for (int j=0; j<n; j++) {
                if (j != i) {
                    somme = somme + matrice1[i][j]*x1[j]; 
                }
            }
            
            result = (b1[i] - somme) / matrice1[i][i];
            
            return result;
        }
    }
}
