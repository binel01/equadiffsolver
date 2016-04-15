/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonction;

import fonction.IFonction;

/**
 *
 * @author KevinJio
 */
public abstract class AFonction implements IFonction{
    
    public AFonction(){
        
    }
    
    @Override
    public double evaluer(double x) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double[] evaluer(double[] x) throws Exception{
        double resultat[] = new double[x.length];        
        for(int i=0; i<x.length; i++){
            resultat[i] = evaluer(x[i]);
        }
        return resultat;
    }
    
}
