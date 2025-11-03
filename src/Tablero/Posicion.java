/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablero;

/**
 *
 * @author Hp
 */
public class Posicion {
    
    private int Fila;
    private int Col;
    
    public Posicion(int Fila, int Col) {
        this.Fila = Fila;
        this.Col = Col;
    }
    
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        
        Posicion pos = (Posicion) objeto;
        
        return Fila == pos.Fila && Col == pos.Col;
    }

    @Override
    public int hashCode() {
        int resultado = 17;
        resultado = 31 * resultado + Fila;
        resultado = 31 * resultado + Col;
        return resultado;
    }
    
    @Override
    public String toString() {
        return "(" + Fila + ", " + Col + ")";
    }
}