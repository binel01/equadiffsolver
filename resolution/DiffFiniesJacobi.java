/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import fonction.IFonction;

/**
 *
 * @author binel
 */
public class DiffFiniesJacobi extends AResolution {
     
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh) {
        return solveJacobi(fonction, u0, u1, mesh, 0.0001);
    }
    
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh, double epsilon) {
        
        return solveJacobi(fonction, u0, u1, mesh, epsilon);   
    }

    private double[] solveJacobi(IFonction fonction, double u0, double u1, double[] mesh, double epsilon) {
        int n = mesh.length;
        double[][] A = new double[n][n];
        double[] x = new double[n];
        double[] b;
        double[] resultat = new double[n];
        double h;
        
        // Remplissage de la matrice A
        if(n==0) {
            return new double[0];
        }
        
        //Calcul de h
        h = mesh[0];
        for(int i=0; i<n-1; i++){
            h = Math.max(h, mesh[i+1] - mesh[i]);
        }
        h = Math.max(h, 1 - mesh[n-1]);
        
        // Construction de la matrice A
        double hCarre = h * h;
        double temp1 = -1.0/hCarre;
        double temp2 = 2.0/hCarre;        
 
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i == j) {
                    A[i][j] = temp2;
                }
                
                else if (j == i-1) {
                    A[i][j] = temp1;
                }
                
                else if (j-1 == i) {
                    A[i][j] = temp1;
                }
                
                else {
                    A[i][j] = 0;
                }
            }
        }

        
        // Remplissage du vecteur initial x0
        for (int i=0; i<n; i++) {
            x[i] = Math.round(Math.random()*10);
        }
        
        // Remplissage du vecteur second membre b
        try{
            b = fonction.evaluer(mesh);
        }catch(Exception e){
            return null;
        }
        
        b[0] = b[0] + (u0 / hCarre);
        b[n-1] = b[n-1] + (u1 / hCarre);
        
        // Résolution du système d'équation
        while (!verifierPrecision(A, x, b, epsilon)) {
            double somme = 0;
            x = resultat;
            
            for (int i=0; i<n; i++) {
                somme = 0;
                for (int j=0; j<n; j++) {
                    if (j != i) {
                        somme = somme + A[i][j]*x[j]; 
                    }
                }
            
                resultat[i] = (b[i] - somme) / A[i][i];
            }
        }
        
        
        return resultat;
    }
    
    // Vérification de la précision à epsilon près
    public boolean verifierPrecision(double[][] A, double[] x, double[] b, double epsilon) {
        double somme = 0;
        int n = x.length;
        double[] residus = new double[n];
        double normeb = calculeNorme(b);
        double normeResidus = 0;
        double erreur = 0;
        
        for (int i=0; i<n; i++) {
            somme = 0;
            for (int j=0; j<n; j++) {
                somme = somme + A[i][j]*x[j];
            }
            
            residus[i] = b[i] - somme;
        }
        
        normeResidus = calculeNorme(residus);        
        erreur = (normeResidus / normeb);
        
        return (erreur < epsilon) ? (true) : (false);
    }

    public double calculeNorme(double[] beta) {
        double norme = 0;
        int n = beta.length;
        
        for (int i=0; i<n; i++) {
            norme = norme + beta[i]*beta[i];
        }
        
        norme = Math.sqrt(norme);
        
        return norme;
    }
}
