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
public interface IMethodeResolution {
    
    public double calculeNorme(double[] vecteur);
    
    public boolean verifierPrecision(double[][] matrice, double[] xVecteur, double[] bVecteur, double epsilon);
    
    public double[] solve(double[][] matrice, double[] b, double epsilon);
    
    public double[] solve();
    
}
