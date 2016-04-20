/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resolution;

import Jama.Matrix;
import fonction.IFonction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import org.ojalgo.access.Access2D.Builder;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.BasicMatrix.Factory;
import org.ojalgo.matrix.PrimitiveMatrix;




/**
 *
 * @author KevinJio
 */
public class DifferencesFinies extends AResolution{
    
    @Override
    public double[] solve(IFonction fonction, double u0, double u1, double[] mesh){
        return solveOjAlgo(fonction, u0, u1, mesh);
    }

    public double[] solveJama(IFonction fonction, double u0, double u1, double[] mesh) {
        int n = mesh.length;
        if(n==0){
            return new double[0];
        }
        
        double b[];
        try{
            b = fonction.evaluer(mesh);
        }catch(Exception e){
            return null;
        }
        
        double h = mesh[0];
        for(int i=0; i<n-1; i++){
            h = Math.max(h, mesh[i+1] - mesh[i]);
        }
        h = Math.max(h, 1 - mesh[n-1]);
        
        double hCarre = h * h;
        double a[][] = new double[n][n];
        double temp1 = -1.0/hCarre;
        double temp2 = 2.0/hCarre;
        
        a[0][0] = temp2;
        if(n>1){
            for(int i=1; i<n; i++){
                a[i][i] = temp2;
                a[i][i-1] = temp1;
                a[i-1][i] = temp1;
            }
        }
        Matrix aMatrix = new Matrix(a);
        
        //Construction de la matrice b
        b[0] = b[0] + u0 / hCarre;
        b[n-1] = b[n-1] + u1 / hCarre;        
        Matrix bMatrix = new Matrix(b, n);
        
        //Résolution de l'équation
        Matrix res = aMatrix.solve(bMatrix);
        return res.getColumnPackedCopy();
    }   
    
    public double[] solveOjAlgo(IFonction fonction, double u0, double u1, double[] mesh){
        int n = mesh.length;
        if(n==0){
            return new double[0];
        }
        
        //Calcul de h
        double h = mesh[0];
        for(int i=0; i<n-1; i++){
            h = Math.max(h, mesh[i+1] - mesh[i]);
        }
        h = Math.max(h, 1 - mesh[n-1]);
        
        //Construction de la matrice A
        double hCarre = h * h;
        double temp1 = -1.0/hCarre;
        double temp2 = 2.0/hCarre;        
        final Factory<PrimitiveMatrix> tmpFactory = PrimitiveMatrix.FACTORY;
        Builder<PrimitiveMatrix> tmpBuilder = tmpFactory.getBuilder(n, n);
        tmpBuilder.set(0, 0, temp2);
        if(n>1){
            for (int i = 1; i < n; i++) {
                tmpBuilder.set(i, i, temp2);
                tmpBuilder.set(i, i-1, temp1);
                tmpBuilder.set(i-1, i, temp1);
            }            
        }
        final BasicMatrix aMatrix = tmpBuilder.build();
        
        //Construction de la matrice B
        double b[];
        try{
            b = fonction.evaluer(mesh);
        }catch(Exception e){
            return null;
        }
        
        b[0] = b[0] + u0 / hCarre;
        b[n-1] = b[n-1] + u1 / hCarre;
        
        tmpBuilder = tmpFactory.getBuilder(n, 1);
        for (int i = 0; i < b.length; i++) {
            tmpBuilder.set(i, 0, b[i]);
        } 
        final BasicMatrix bMatrix = tmpBuilder.build();
        
        //Résolution de l'équation
        return aMatrix.solve(bMatrix).toRawCopy1D();
    }
    
    public double[] solveCommonMaths(IFonction fonction, double u0, double u1, double[] mesh) {
        int n = mesh.length;
        if(n==0){
            return new double[0];
        }
        
        double b[];
        try{
            b = fonction.evaluer(mesh);
        }catch(Exception e){
            return null;
        }
        
        double h = mesh[0];
        for(int i=0; i<n-1; i++){
            h = Math.max(h, mesh[i+1] - mesh[i]);
        }
        h = Math.max(h, 1 - mesh[n-1]);
        
        double hCarre = h * h;
        double a[][] = new double[n][n];
        double temp1 = -1.0/hCarre;
        double temp2 = 2.0/hCarre;
        
        a[0][0] = temp2;
        if(n>1){
            for(int i=1; i<n; i++){
                a[i][i] = temp2;
                a[i][i-1] = temp1;
                a[i-1][i] = temp1;
            }
        }
        //Matrix aMatrix = new Matrix(a);
        
        // Création de la matrice A
        RealMatrix matrice = new Array2DRowRealMatrix(a, false);
        DecompositionSolver solver = new LUDecomposition(matrice).getSolver();
        
        
        //Construction du vecteur b
        b[0] = b[0] + u0 / hCarre;
        b[n-1] = b[n-1] + u1 / hCarre;        
        
        RealVector bVect = new ArrayRealVector(b, false);
        //Matrix bMatrix = new Matrix(b, n);
        
        //Résolution de l'équation
        
        //RealVector res = solver.solve(bVect);
        // double[] res1 = res.toArray();
        
        return solver.solve(bVect).toArray();
    }   

    
}
