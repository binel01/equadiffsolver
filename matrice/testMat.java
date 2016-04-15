/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrice;

import matrice.sparse.MatriceCRS;


/**
 *
 * @author binel
 */
public class testMat {
    public static void main(String args[]) {
        double[][] A = {
            {10, 0, 0, 0, -2, 0},
            {3, 9, 0, 0, 0, 3},
            {0, 7, 8, 7, 0, 0},
            {3, 0, 8, 7, 5, 0},
            {0, 8, 0, 9, 9, 13},
            {0, 4, 0, 0, 2, -1}
        };
        
        
        //MatriceCRS mat = new MatriceCRS(A, 6, 6);
        
        double[][] B = {{1, 2, 1}, {2, 1, 1}, {1, 1, 2}};
        
        DefaultMatrix mat = new DefaultMatrix(B, 3, 3);
        mat.afficherMatrice();
        //System.out.println(mat.toString());
    
        //System.out.println(mat.get(0, 1));
        
        //mat.set(0, 1, -6);
        //mat.afficherMatrice();
        //System.out.println(mat.toString());
        
        //mat.set(0, 2, -20);
        //mat.afficherMatrice();
        //System.out.println(mat.toString());
        
        //mat.prodMat(mat).afficherMatrice();
        System.out.print("Le déterminant est: " + mat.determinant()+"\n");
        
        
        
    }
}
