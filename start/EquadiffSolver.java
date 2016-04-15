/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.school.equadiffsolver.start;

import resolution.AResolution;
import resolution.DiffFiniesJacobi;
import resolution.DiffFiniesJacobiParrallele;
import fonction.FonctionParChaine;
import resolution.DifferencesFinies;
import resolution.NewClass;
import resolution.VolumesFinis;
import fonction.IFonction;
import resolution.IResolution;
import java.util.Arrays;

/**
 *
 * @author KevinJio
 */
public class EquadiffSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        FonctionParChaine fonction = new FonctionParChaine("1/(X-2)");
        //fonction.setExpression("1/(X-2)");
        
        //*
        IResolution solveur1 = new DifferencesFinies();
        IResolution solveur = new VolumesFinis();
        double u0 = 5;
        double u1 = 10;
        int n = 100;
        
        
        double[] mesh = {0.5, 0.75};
        double[] res = solveur.solve(fonction, u0, u1, n);
        double[] res1 = solveur1.solve(fonction, u0, u1, n);
        
        System.out.println("Solution avec Volumes finis: " + Arrays.toString(res));
        System.out.println("Solution avec Différences finies: " + Arrays.toString(res1));
        /*
        System.out.println("Affichage des erreurs absolues minimales et maximales: ");
        double max=0, min=1;
        double diff = 0;
        for (int i=0; i<n; i++) {
            diff = Math.abs(res1[i] - res[i]);
            
            if (diff > max)
                max = diff;
            if (diff < min)
                min =diff;
        }
        System.out.println("Erreur maximale = "+max+", Erreur minimale = "+min);
        
        //*/
        
        
    }
    
}
