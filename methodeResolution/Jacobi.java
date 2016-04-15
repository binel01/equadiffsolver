/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodeResolution;

/**
 *
 * @author binel
 */
public class Jacobi extends AMethodeResolution {

    private double[][] matrice;
    private double[] b;
    double epsilon;
    
    public Jacobi() {
        
    }
    
    public Jacobi(double[][] matrice, double[] b, double epsilon) {
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
        int n = b.length;
        double[] resultat = new double[n];
        double[] x = new double[n];
        
        for (int i=0; i<n; i++) {
            x[i] = 0;
        }
        
        long start = System.currentTimeMillis();
        
        while (!verifierPrecision(mat, x, bVect, eps)) {
            double somme = 0;
            x = resultat;
            
            for (int i=0; i<n; i++) {
                somme = 0;
                for (int j=0; j<n; j++) {
                    if (j != i) {
                        somme = somme + mat[i][j]*x[j]; 
                    }
                }
            
                resultat[i] = (bVect[i] - somme) / mat[i][i];
            }
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println("Le temps mis est de "+ (end - start) + " milli secondes");
        
        return resultat;
    }
}
