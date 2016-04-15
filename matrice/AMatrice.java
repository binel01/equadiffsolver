/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrice;

/**
 *
 * @author binel
 */
public abstract class AMatrice implements IMatrice {
    protected int nbCol;
    protected int nbLignes;
    
    public AMatrice() {
    }
    
    @Override
    public int getNBLignes() {
        return nbLignes;
    }

    @Override
    public int getNBCol() {
        return nbCol;
    }
    
    @Override
    public void afficherMatrice() {
        for (int i=0; i<nbLignes; i++) {
            for (int j=0; j<nbCol; j++)
                System.out.print(get(i,j)+"  ");
            
            System.out.println();
        }
        System.out.println();
    }
    
    @Override
    public void compresser(double[][] matrice, int nbLignes, int nbCols) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
