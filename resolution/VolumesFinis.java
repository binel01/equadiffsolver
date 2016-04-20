/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import fonction.IFonction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
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
    
    public double[] solveOjalgo(IFonction fonction, double u0, double u1, double[] mesh) {    
        double[] result = null;
        int n = mesh.length;
        
        if (n == 0) {
            return result;
        }
        

        // Calcul des h(i) et h(i+1/2)
        double[] hi2 = new double[n+1];
        double[] hi = new double[n];
        
        hi2[0] = mesh[0];
        for (int i=1; i<n; i++) {
            hi2[i] = mesh[i] - mesh[i-1];
            hi[i-1] = 0.5*(hi2[i] + hi2[i-1]);
        }
        hi2[n] = 1 - mesh[n-1];
        hi[n-1] = 0.5*(hi2[n] + hi2[n-1]);
        
        // Construction de la matrice A     
        double temp = 1.0/hi2[0] + 1.0/hi2[1];
        
        
        final BasicMatrix.Factory<PrimitiveMatrix> tmpFactory = PrimitiveMatrix.FACTORY;
        Builder<PrimitiveMatrix> tmpBuilder = tmpFactory.getBuilder(n, n);
        tmpBuilder.set(0, 0, temp);
        
        if(n>1){
            //tmpBuilder.set(0, 1, -1.0/hi2[0]);
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
    
    public double[] solveCommonMaths(IFonction fonction, double u0, double u1, double[] mesh) {    
        double[] result = null;
        int n = mesh.length;
        
        if (n == 0) {
            return result;
        }
        

        // Calcul des h(i) et h(i+1/2)
        double[] hi2 = new double[n+1];
        double[] hi = new double[n];
        
        hi2[0] = mesh[0];
        for (int i=1; i<n; i++) {
            hi2[i] = mesh[i] - mesh[i-1];
            hi[i-1] = 0.5*(hi2[i] + hi2[i-1]);
        }
        hi2[n] = 1 - mesh[n-1];
        hi[n-1] = 0.5*(hi2[n] + hi2[n-1]);
        
        // Construction de la matrice A     
        double temp = 1.0/hi2[0] + 1.0/hi2[1];
    
        
        
        // Création de la matrice A
        RealMatrix matrice = new Array2DRowRealMatrix(n, n);
        matrice.addToEntry(0, 0, temp);
        
        if(n>1){
            //matrice.addToEntry(0, 1, -1.0/hi2[0]);
            for (int i = 1; i < n; i++) {
                matrice.addToEntry(i, i, (1.0/hi2[i] + 1.0/hi2[i+1]));
                matrice.addToEntry(i, i-1, -1.0/hi2[i]);
                matrice.addToEntry(i-1, i, -1.0/hi2[i+1]);
            }
        }
        
        DecompositionSolver solver = new LUDecomposition(matrice).getSolver();
        
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
        
        
        
        /*
        RealVector bVect = new ArrayRealVector(n);
        for (int i=0; i<n; i++)
            bVect.setEntry(i, b[i]);
        //*/
        ArrayRealVector bVect = new ArrayRealVector(b);
        //Matrix bMatrix = new Matrix(b, n);
        
        //Résolution de l'équation
        
        //RealVector res = solver.solve(bVect);
        // double[] res1 = res.toArray();
        
        return solver.solve(bVect).toArray();
    }
}
