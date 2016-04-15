/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodeResolution;

import methodeResolution.IMethodeResolution;

/**
 *
 * @author binel
 */
public abstract class AMethodeResolution implements IMethodeResolution {
    
    
    /**
     * Permet de calculer la norme du vecteur passé en paramètre
     * @param vecteur
     * @return 
     */
    @Override
    public double calculeNorme(double[] vecteur) {
        double norme = 0;
        int n = vecteur.length;
        
        for (int i=0; i<n; i++) {
            norme = norme + vecteur[i]*vecteur[i];
        }
        
        norme = Math.sqrt(norme);
        
        return norme;
    }
    
    /**
     * Permet de vérifier la précision de la méthode de résolution
     * @param matrice
     * @param xVecteur
     * @param bVecteur
     * @param epsilon
     * @return 
     */
    @Override
    public boolean verifierPrecision(double[][] matrice, double[] xVecteur, double[] bVecteur, double epsilon) {
        double somme = 0;
        int n = xVecteur.length;
        double[] residus = new double[n];
        double normeb = calculeNorme(bVecteur);
        double normeResidus = 0;
        double erreur = 0;
        
        for (int i=0; i<n; i++) {
            somme = 0;
            for (int j=0; j<n; j++) {
                somme = somme + matrice[i][j]*xVecteur[j];
            }
            
            residus[i] = bVecteur[i] - somme;
        }
        
        normeResidus = calculeNorme(residus);        
        erreur = (normeResidus / normeb);
        
        System.out.println(erreur);
        
        return (erreur < epsilon) ? (true) : (false);
    }
    
}
