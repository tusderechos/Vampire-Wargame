/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fichas;



/**
 *
 * @author Hp
 */

import Tablero.Posicion;
import Tablero.Tablero;
import java.util.ArrayList;

public abstract class Ficha {
    
    public String Nombre;
    public Bando Color;
    public Posicion Pos;
    public int Ataque;
    public int Vidas;
    public int Escudo;

    public Ficha(String Nombre, Bando Color, int Ataque, int Vidas, int Escudo) {
        this.Nombre = Nombre;
        this.Color = Color;
        this.Ataque = Ataque;
        this.Vidas = Vidas;
        this.Escudo = Escudo;
    }

    public String getNombre() {
        return Nombre;
    }
    
    public Bando getColor() {
        return Color;
    }
    
    public Posicion getPos() {
        return Pos;
    }
    
    public void setPos(Posicion pos) {
        this.Pos = pos;
    }
    
    public int getAtaque() {
        return Ataque;
    }
    
    public int getVidas() {
        return Vidas;
    }
    
    public int getEscudo() {
        return Escudo;
    }
    
    public boolean Viva() {
        return Vidas > 0;
    }

    public void RecibirDano(int puntos) {
         if (Escudo > 0) {
             int puntosrestantes = puntos - Escudo; //Esto existe por si digamos que algo magicamente hace 6 puntos de ataque, quitaria primeramente los 5 puntos de escudo, y lo que queda despues de eso es lo que se le quita a la vida
             Escudo -= puntos;

             if (Escudo < 0) {
                 Escudo = 0;
             }
             if (puntosrestantes > 0) {
                 Vidas -= puntosrestantes;
             }
         } else {
             Vidas -= puntos;
         }

         if (Vidas < 0) {
             Vidas = 0;
         }
    }
   
    public void Mover(Posicion nuevapos) {
        this.Pos = nuevapos;
    }
    
    public void Eliminar() {
        this.Vidas = 0;
    }
    
    public abstract ArrayList<Posicion> MovimientosBasicos(Tablero tablero);
    
    public abstract ArrayList<Posicion> MovimientosEspeciales(Tablero tablero);
    
    public abstract ArrayList<Posicion> AtaquesNormales(Tablero tablero);
    
    public abstract ArrayList<Posicion> AtaquesEspeciales(Tablero tablero);
    
    public void RecibirDanoDirecto(int puntos) {
        Vidas -= puntos;
        
        if (Vidas < 0) {
            Vidas = 0;
        }
    }
    
    @Override
    public String toString() {
        return Nombre + " [" + Color + "]\n(Vidas: " + Vidas + ", Escudo: " + Escudo + ")";
    }
}
