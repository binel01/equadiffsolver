/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.school.equadiffsolver.start;

/**
 *
 * @author binel
 */
public class Epsilon {
    public static void main(String args[]) {
        double eps = 1;
        int i = 0;
        
        while (eps + 1 > 1) {
            i++;
            eps *= 0.5;
        }
        
        System.out.println("Epsilon machine est: " + eps);
        System.out.println("Le nombre de bits de la mantisse est: " + i);
    }
}
