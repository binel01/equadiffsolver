/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import methodeResolution.GaussSiedel;
import fonction.IFonction;
import methodeResolution.GaussSiedelParallele;

/**
 *
 * @author binel
 */
public class DiffFiniesJacobiParrallele extends AResolution {
    
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh) {
        return solveJacobi(fonction, u0, u1, mesh, 0.00001);
    }
    
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh, double epsilon) {
        return solveJacobi(fonction, u0, u1, mesh, epsilon);   
    }
    
    private double[] solveJacobi(IFonction fonction, double u0, double u1, double[] mesh, double eps) {
        int n = mesh.length;
        double[][] A = new double[n][n];
        double[] b;
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
        
        // Remplissage du vecteur second membre b
        try{
            b = fonction.evaluer(mesh);
        }catch(Exception e){
            return null;
        }
        
        b[0] = b[0] + (u0 / hCarre);
        b[n-1] = b[n-1] + (u1 / hCarre);
        

        // Résolution du système d'équation
        
        GaussSiedelParallele jacobPar = new GaussSiedelParallele(A, b, eps);
        
        return jacobPar.solve();
    }

}
