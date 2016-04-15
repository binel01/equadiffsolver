/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import fonction.IFonction;

/**
 *
 * @author KevinJio
 */
public interface IResolution {
    
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh);
    
    public double[] solve(IFonction fonction, double u0, double u1, int n);
    
    public double[] buildMesh(int n);
    
    public double erreurAbsolue(double[] v1, double[] v2);
    
    public double erreurEuclidienne(double[] v1, double[] v2);
    
}
