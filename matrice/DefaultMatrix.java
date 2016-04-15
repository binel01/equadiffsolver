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
public class DefaultMatrix extends AMatrice {
    private double[][] matrice;
    
    public DefaultMatrix(int taille) {
        System.out.println("Taille de la sous matrice: " + taille);
        matrice = new double[taille][taille];
        this.nbLignes = taille;
        this.nbCol = taille;
    }
    
    public DefaultMatrix(int nbLignes, int nbCol) {
        matrice = new double[nbLignes][nbCol];
        this.nbLignes = nbLignes;
        this.nbCol = nbCol;
    }
    
    public DefaultMatrix(double[][] matrice, int nbLignes, int nbCol) {
        this.nbCol = nbCol;
        this.nbLignes = nbLignes;
        this.matrice = matrice;
    }
    
    @Override
    public double get(int i, int j) {
        return matrice[i][j];
    }
    
    @Override
    public void set(int i, int j, double x) {
        matrice[i][j] = x;
    }

    @Override
    public IMatrice prodMat(IMatrice matrice2) {
        int n1 = this.getNBLignes();
        int n2 = matrice2.getNBLignes();
        int m1 = this.getNBCol();
        int m2 = matrice2.getNBCol();
        
        double[][] res = new double[n1][m2];
        
        for (int i=0; i<n1; i++) {
            for (int j=0; j<m2; j++) {
                res[i][j] = 0;
                
                for (int t=0; t<n2; t++) {
                    res[i][j] += this.get(i, t) * matrice2.get(t, j);
                }
            }
        }
        
        return new DefaultMatrix(res, n1, m2);
    }

    @Override
    public IMatrice sommeMat(IMatrice matrice2) {
        int n = this.getNBLignes();
        int m = this.getNBCol();
        double[][] result = new double[n][m];
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                result[i][j] = this.get(i, j) + matrice2.get(i, j);
            }
        }
        
        return new DefaultMatrix(result, this.nbLignes, this.nbCol);
    }
    
    @Override
    public IMatrice diffMat(IMatrice matrice2) {
        int n = this.getNBLignes();
        int m = this.getNBCol();
        double[][] result = new double[n][m];
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                result[i][j] = this.get(i, j) - matrice2.get(i, j);
            }
        }
        
        return new DefaultMatrix(result, this.nbLignes, this.nbCol);
    }
    
    @Override
    public IMatrice inverser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double determinant() {
        int nb = this.getNBLignes();
        int nb2 =this.getNBCol();
        
        if (nb != nb2) {
            System.out.println("Erreur: la matrice n'est pas caree\nNB_LIN="+this.getNBLignes()+" et NB_COL="+this.getNBCol());
            return 0;
        }
        
        double resultat = 0.0;
        
        if (nb == 1) {
            return this.get(0, 0);
        }
        
        if (nb == 2) {
            return this.get(0, 0)*this.get(1, 1) - this.get(0, 1)*this.get(1, 0);
        }
        
        DefaultMatrix mat = new DefaultMatrix(nb-1);
        
        int u=0, v=0;
        for (int i=0; i<nb; i++) {
            if (this.get(0, i) != 0) {
                System.out.println("-----------------Valeur de i = "+i+"-------------");
                for (int m=1; m<nb; m++) {
                    for (int n=0; n<nb; n++) {
                        if (n != i) {
                            mat.set(u, v, this.get(m, n));
                            v++;
                        }
                    }
                    u++;
                    v=0;
                }
                mat.afficherMatrice();
                    
                resultat += Math.pow(-1, i)*this.get(0, i)*mat.determinant();
                u=0;
                v=0;
            }
        }
            
        return resultat;
    }

    @Override
    public IMatrice transposer() {
        int n = this.getNBLignes();
        int m = this.getNBCol();
        double[][] result = new double[n][m];
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                result[i][j] = this.get(j, i);
            }
        }
        
        return new DefaultMatrix(result, this.nbLignes, this.nbCol);
    }
}
