/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import fonction.IFonction;
import resolution.IResolution;

/**
 *
 * @author KevinJio
 */
public abstract class AResolution implements IResolution{
    
    public AResolution(){
        
    }
    
    @Override
    public double[] buildMesh(int n){
        if(n<0){
            return null;
        }
        double mesh[] = new double[n];
        if(n != 0){
            double pas = 1.0/(n+1);
            double val = pas;
            for(int i=0; i<n; i++){
                mesh[i] = val;
                val += pas;
            }
        }
        
        return mesh;
    }
    
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, int n){
        return solve(fonction, u0, u1, buildMesh(n));
    }
    
    @Override
    public double erreurAbsolue(double[] v1, double[] v2){
        int n = v1.length;
        if(v2.length != n){
            return Double.MAX_VALUE;
        }
        
        double erreurMax = 0;
        for(int i=0; i<n; i++){
            erreurMax = Math.max(erreurMax, Math.abs(v1[i] - v2[i]));
        }
        
        return erreurMax;
    }
    
    @Override
    public double erreurEuclidienne(double[] v1, double[] v2){
        int n = v1.length;
        if(v2.length != n){
            return Double.MAX_VALUE;
        }
        
        double diff;
        double somme = 0;
        for(int i=0; i<n; i++){
            diff = v1[i] - v2[i];
            somme += diff * diff;
        }
        
        return Math.sqrt(somme);
    }
}
