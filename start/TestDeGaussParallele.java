/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.school.equadiffsolver.start;

import fonction.FonctionParChaine;
import java.util.Arrays;
import resolution.DifferencesFinies;
import resolution.IResolution;

/**
 * @author binel
 */
public class TestDeGaussParallele {
    public static void main(String[] args) throws Exception {
        
        FonctionParChaine fonction = new FonctionParChaine("X");
        
        IResolution solveur1 = new resolution.DiffFiniesJacobiParrallele();
        resolution.NewClass solveur = new resolution.NewClass();
        
        double u0 = 5;
        double u1 = 10;
        int n = 100;
        
        double[] res = solveur.solve(fonction, u0, u1, n);
        double[] res1 = solveur1.solve(fonction, u0, u1, n);
        
        System.out.println("Solution avec GaussSiedel optimisé: " + Arrays.toString(res));
        System.out.println("Solution avec GaussSiedel : " + Arrays.toString(res1));
        
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
    }
}
