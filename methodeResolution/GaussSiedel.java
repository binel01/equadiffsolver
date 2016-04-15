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
public class GaussSiedel extends AMethodeResolution {
    
    private double[][] matrice;
    private double[] b;
    double epsilon;

    public GaussSiedel(double[][] matrice, double[] b, double epsilon) {
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
        double[] x = new double[n];
        
        for (int i=0; i<n; i++) {
            x[i] = i;
        }

        
        long start = System.currentTimeMillis();
        
        while (!verifierPrecision(mat, x, bVect, eps)) {
            double somme1 = 0;
            double somme2 = 0;
            
            for (int i=0; i<n; i++) {
                somme1 = 0;
                somme2 = 0;
                
                for (int j=0; j<i; j++) {
                    somme1 = somme1 + mat[i][j]*x[j]; 
                }
                
                for (int j=i+1; j<n; j++) {
                    somme2 = somme2 + mat[i][j]*x[j]; 
                }
                
                x[i] = (bVect[i] - somme1 - somme2) / mat[i][i];
            }
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println("Le temps mis avec GaussSiedel est: "+ (end - start) + " milli secondes");
        
        return x; 
    }
}
