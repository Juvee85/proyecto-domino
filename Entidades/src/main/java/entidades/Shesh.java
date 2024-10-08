/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;

import entidades.Pozo.Ficha;

/**
 *
 * @author 
 */
public class Shesh {
    public static void main(String[] args) {
        Pozo pozo = new Pozo();
        
        System.out.println("Fichas en pozo: " + pozo.fichasRestantes());
        
        Ficha f = pozo.sacarFicha();
               
        while (pozo.fichasRestantes() > 0) {
            System.out.println(f);
            f = pozo.sacarFicha();
        }
        
        System.out.println("Fichas en pozo: " + pozo.fichasRestantes());
    }
}
