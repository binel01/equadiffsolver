/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrice.sparse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author binel
 */
public class MatriceCRS {
    private List<Double> v;
    private List<Integer> J;
    private List<Integer> Ip;
    private int nbCol;
    private int nbLigne;
    
    public MatriceCRS() {
        v = new ArrayList<>();
        J = new ArrayList<>();
        Ip = new ArrayList<>();
    }
    
    public MatriceCRS(double[][] matrice, int nbLigne, int nbCol) {
        v = new ArrayList<>();
        J = new ArrayList<>();
        Ip = new ArrayList<>();
    
        // Remplissage des différents vecteurs
        int n = matrice.length;
        this.nbLigne = nbLigne;
        this.nbCol = nbCol;
        
        int t=0;
        for (int i=0; i<nbLigne; i++) {
            Ip.add(t);
            
            for (int j=0; j<nbCol; j++) {
                if (matrice[i][j] != 0) {
                    v.add(matrice[i][j]);
                    J.add(j);
                    t++;
                }
            }
        }
        Ip.add(t);
    }
    
    @Override
    public String toString() {   
        String s = "v = " + v.toString() + "\nJ = " + J.toString() + "\nIp = " + Ip.toString();
        
        return s;
    }
    
    public double get(int i, int j) {
        for (int k=0; k<J.size(); k++) {
            if (J.get(k) == j) {
                if (Ip.get(i) <= k && Ip.get(i+1) > k) {
                    return v.get(k);
                }
            }
        }
        
        return 0;
    }
    
    public void set(int i, int j, double x) {
        for (int k=0; k<J.size(); k++) {
            if (J.get(k) == j) {
                if (Ip.get(i) <= k && Ip.get(i+1) >= k) {
                    v.set(k, x);
                    return;
                }
            }
        }
        
        if (x == 0)
            return;
        
        for (int k=Ip.get(i); k<Ip.get(i+1); k++) {
            if (J.get(k) < j && j < J.get(k+1)) {
                double var1 = v.get(k+1);
                int var2 = J.get(k+1);
                v.set(k+1, x);
                J.set(k+1, j);
                
                
                double var11;
                int var22;
                for (int m=k+2; m<v.size(); m++) {
                    var11 = v.get(m);
                    var22 = J.get(m);
                    
                    v.set(m, var1);
                    J.set(m, var2);
                    
                    var1 = var11;
                    var2 = var22;
                }
                v.add(var1);
                J.add(var2);
                break;
            }
        }
        
        for (int t=i+1; t<Ip.size(); t++) {
            Ip.set(t, Ip.get(t)+1);
        }
    }
}
