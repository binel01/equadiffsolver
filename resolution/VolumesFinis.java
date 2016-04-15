/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import fonction.IFonction;
import org.ojalgo.access.Access2D.Builder;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;


/**
 *
 * @author binel
 */
public class VolumesFinis extends AResolution {
    
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh) {
        
        return solveOjalgo(fonction, u0, u1, mesh);
    }
    
    private double[] solveOjalgo(IFonction fonction, double u0, double u1, double[] mesh) {    
        double[] result = null;
        int n = mesh.length;
        
        if (n == 0) {
            return result;
        }
        

        // Calcul des h(i) et h(i+1/2)
        double[] hi2 = new double[n+1];
        double[] hi = new double[n];
        
        /*
        mesh2[0] = 0;
        mesh2[n+1] = 1;
        
        for (int i=0; i<n; i++)  
            mesh2[i+1] = mesh[i];
        
        hi2[0] = mesh2[1];
        for (int i=1; i<n; i++) {
            hi2[i] = mesh2[i+1] - mesh2[i]; // hi2[i] = mesh[i] - mesh[i-1];
            hi[i-1] = 0.5*(hi2[i] + hi2[i-1]);
        }
        hi2[n] = mesh2[n+1] - mesh2[n];
        hi[n-1] = 0.5*(hi2[n] - hi2[n-1]);
        //*/
        
        hi2[0] = mesh[0];
        for (int i=1; i<n; i++) {
            hi2[i] = mesh[i] - mesh[i-1];
            hi[i-1] = 0.5*(hi2[i] + hi2[i-1]);
        }
        hi2[n] = 1 - mesh[n-1];
        hi[n-1] = 0.5*(hi2[n] - hi2[n-1]);
        
        // Construction de la matrice A     
        double temp = 1.0/hi2[0] + 1.0/hi2[1];
        double temp1 = 0.0;
        double temp2 = 0.0;
        
        System.out.println(n);
        
        final BasicMatrix.Factory<PrimitiveMatrix> tmpFactory = PrimitiveMatrix.FACTORY;
        Builder<PrimitiveMatrix> tmpBuilder = tmpFactory.getBuilder(n, n);
        tmpBuilder.set(0, 0, temp);
        if(n>1){
            for (int i = 1; i < n; i++) {
                tmpBuilder.set(i, i, (1.0/hi2[i] + 1.0/hi2[i+1]));
                tmpBuilder.set(i, i-1, -1.0/hi2[i]);
                tmpBuilder.set(i-1, i, -1.0/hi2[i+1]);
            }
        }
        
        final BasicMatrix aMatrix = tmpBuilder.build();
        
        
        //Construction du vecteur b
        double b[];
        try{
            b = fonction.evaluer(mesh);
            
            for (int i=0; i<n; i++) {
                b[i] = b[i] * hi[i];
            }
        }catch(Exception e){
            return null;
        }
        
                
        b[0] = b[0] + u0 / hi2[0];
        b[n-1] = b[n-1] + u1 / hi2[n-1];
        
        tmpBuilder = tmpFactory.getBuilder(n, 1);
        for (int i = 0; i < b.length; i++) {
            tmpBuilder.set(i, 0, b[i]);
        } 
        final BasicMatrix bMatrix = tmpBuilder.build();
        
        //Résolution de l'équation
        return aMatrix.solve(bMatrix).toRawCopy1D();
    }
}
