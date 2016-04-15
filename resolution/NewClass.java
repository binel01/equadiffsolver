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
public class NewClass extends AResolution {
    
    private double bNorme = 0;
    
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh) {
        return solveJacobi(fonction, u0, u1, mesh, 0.00001);
    }
    
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh, double epsilon) {
        return solveJacobi(fonction, u0, u1, mesh, epsilon);   
    }
    //*
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, int n){
        return solve(fonction, u0, u1, buildMesh(n), 0.00001);
    }
    //*/
    private double[] solveJacobi(IFonction fonction, double u0, double u1, double[] mesh, double epsilon) {
        int n = mesh.length;
        double[] b = new double[n];
        double h;
        
        
        // Remplissage de la matrice A
        if(n==0) {
            System.out.println("Erreur: n == 0");
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
        double temp2 = -1.0/hCarre;
        double temp1 = 2.0/hCarre; 
        double[] temp = {temp1, temp2};
        double[] u = new double[n];

        
        // Remplissage du vecteur second membre b
        try{
            //b = fonction.evaluer(mesh);
            for (int i=0; i<n; i++) 
                b[i] = fonction.evaluer(mesh[i]);
        }catch(Exception e){
            System.out.println("Erreur d'évaluation de la maille !!");
            e.printStackTrace();
            return null;
        }
        
        b[0] = b[0] + (u0 / hCarre);
        b[n-1] = b[n-1] + (u1 / hCarre);
        
        bNorme = calculeNorme(b);

        long start = System.currentTimeMillis();
        
        while (!verifier(temp, u, b, epsilon)) { 
            u[0] = (b[0] - u[1]*temp2) / temp1;
            for (int i=1; i<n-1; i++) {
                u[i] = (b[i] - (u[i-1] + u[i+1])*temp2) / temp1;
            }
            u[n-1] = (b[n-1] - u[n-2]*temp2) / temp1;
        }
        
                
        long end = System.currentTimeMillis();
        
        System.out.println("Le temps mis avec GaussSiedel optimisé est de "+ (end - start) + " milli secondes");
        
        return u;
    }
    
    public boolean verifier(double[] mat, double[] sol, double[] bVect, double eps) {
        int taille = sol.length;
        double[] residus = new double[taille];
        double residusNorme =0;
        
        
        for (int i=0; i<taille; i++) {
            residus[i] = 0;
        }
        
        residus[0] = (bVect[0] - sol[0]*mat[0] - sol[1]*mat[1]);
        for (int i=1; i<taille-1; i++) {
            residus[i] = (bVect[i] - sol[i]*mat[0] - (sol[i-1] + sol[i+1])*mat[1]);
        }
        residus[taille-1] = (bVect[taille-1] - sol[taille-1]*mat[0] - sol[taille-2]*mat[1]);
        
        residusNorme = calculeNorme(residus);
        
        return (residusNorme / bNorme < eps) ? true : false;
    }
    
    
    public double calculeNorme(double[] vecteur) {
        double norme = 0;
        int n = vecteur.length;
        
        for (int i=0; i<n; i++) {
            norme = norme + vecteur[i]*vecteur[i];
        }
        
        norme = Math.sqrt(norme);
        
        return norme;
    }
    
}
