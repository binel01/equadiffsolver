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
public interface IMatrice {
    /**
     * Renvoie le nombre de lignes de la matrice
     * @return 
     */
    public int getNBLignes();
    
    /**
     * Renvoie le nombre de colonnes de la matrice
     * @return 
     */
    public int getNBCol();
    
    /**
     * Retourne l'élément à la position (i,j) dans la matrice stockée
     * @param i
     * @param j
     * @return 
     */
    public double get(int i, int j);
    
    /**
     * Permet de positionner l'élémént x à la position (i,j) dans la matrice
     * @param i
     * @param j
     * @param x 
     */
    public void set(int i, int j, double x);
    
    /**
     * Stocke le tableau passé en paramètre dans le format désiré
     * @param matrice
     * @param nbLignes
     * @param nbCols
     */
    public void compresser(double[][] matrice, int nbLignes, int nbCols);
    
    /**
     * Affiche la matrice telle qu'elle est réellement
     */
    public void afficherMatrice();
    
    /**
     * Permet d'éffectuer le produit de deux matrices
     * @return 
     */
    public IMatrice prodMat(IMatrice matrice2);
    
    /**
     * Effectue la somme de la matrice courante et de matrice2
     * @param matrice2
     * @return 
     */
    public IMatrice sommeMat(IMatrice matrice2);
    
    /**
     * Retourne la différence entre la matrice courante et matrice
     * @param matrice2
     * @return 
     */
    public IMatrice diffMat(IMatrice matrice2);
    
    /**
     * Retourne l'inverse de la matrice stockée
     * @return 
     */
    public IMatrice inverser();
    
    /**
     * Retourne le déterminant de la matrice stockée
     * @return 
     */
    public double determinant();
    
    /**
     * Retourne la transposée de la matrice
     * @return 
     */
    public IMatrice transposer();
}
