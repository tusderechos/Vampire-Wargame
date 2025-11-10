/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fichas;

/**
 *
 * @author Hp
 */

import Enums.Bando;
import Ruleta.TipoFicha;
import Tablero.*;
import java.util.ArrayList;

public class HombreLobo extends Ficha {
    
    public HombreLobo(Bando Color) {
        super("Hombre Lobo", Color, TipoFicha.HOMBRE_LOBO, 5, 5, 2); //5 puntos de ataque, 5 puntos de vida y 2 puntos de escudo
    }
    
    @Override
    public ArrayList<Posicion> MovimientosBasicos(Tablero tablero) {
        ArrayList<Posicion> MovsPosibles = new ArrayList<>();
        int[][] Direcciones = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, //Movimientos rectos
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; //Movimientos diagonales
        
        for (int[] Direccion : Direcciones) {
            Posicion destino = new Posicion(Pos.Fila + Direccion[0], Pos.Col + Direccion[1]);
            
            if (tablero.Dentro(destino) && tablero.get(destino).CasillaLibre()) {
                MovsPosibles.add(destino);
            }
        }
        
        return MovsPosibles;
    }
    
    /*
        El hombre lobo puede saltar 2 casillas en cualquier direccion, empezando desde donde esta parado
    */
    @Override
    public ArrayList<Posicion> MovimientosEspeciales(Tablero tablero) {
        ArrayList<Posicion> MovsPosibles = new ArrayList<>();
        int[][] Direcciones = {
            {-2, 0}, {2, 0}, {0, -2}, {0, 2}, //Movimientos rectos
            {-2, -2}, {-2, 2}, {2, -2}, {2, 2}}; //Movimientos diagonales
        
        for (int[] direccion : Direcciones) {
            Posicion destino = new Posicion(Pos.Fila + direccion[0], Pos.Col + direccion[1]);
            
            if (tablero.Dentro(destino) && tablero.get(destino).CasillaLibre()) {
                MovsPosibles.add(destino);
            }
        }
        
        return MovsPosibles;
    }
    
    @Override
    public ArrayList<Posicion> AtaquesNormales(Tablero tablero) {
        ArrayList<Posicion> Enemigos = new ArrayList<>();
        Posicion pos = getPos();
        
        if (pos == null) {
            return Enemigos;
        }
        
        for (int fila = -1; fila <= 1; fila++) {
            for (int col = -1; col <= 1; col++) {
                
                if (fila == 0 && col == 0) {
                    continue;
                }
                
                Posicion p = new Posicion(pos.Fila + fila, pos.Col + col);
                
                if (!tablero.Dentro(p)) {
                    continue;
                }
                
                Casilla casilla = tablero.get(p);
                
                if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() != this.Color) {
                    Enemigos.add(p);
                }
            }
        }
        
        return Enemigos;
    }
    
    @Override
    public ArrayList<Posicion> AtaquesEspeciales(Tablero tablero) {
        return new ArrayList<>(); //No tiene un ataque especial
    }
}
