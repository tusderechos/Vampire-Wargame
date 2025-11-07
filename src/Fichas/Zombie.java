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

public class Zombie extends Ficha {
    
    public Zombie(Bando Color) {
        super("Zombie", Color, TipoFicha.ZOMBIE, 1, 1, 0); //1 punto de ataque, 1 punto de vida y 0 puntos de escudo
    }
    
    /*
        No se mueve don Zombie
    */
    @Override
    public ArrayList<Posicion> MovimientosBasicos(Tablero tablero) {
        return new ArrayList<>();
    }
    
    @Override
    public ArrayList<Posicion> MovimientosEspeciales(Tablero tablero) {
        return new ArrayList<>();
    }
    
    /*
        Ataca en 8 direcciones, pero su ataque solamente lo decice la Muerte
    */
    @Override
    public ArrayList<Posicion> AtaquesNormales(Tablero tablero) {
        ArrayList<Posicion> Enemigos = new ArrayList<>();
        
        for (Posicion pos : tablero.Adyacentes8(Pos)) {
            Casilla casilla = tablero.get(pos);
            
            if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() != this.Color) {
                Enemigos.add(pos);
            }
        }
        
        return Enemigos;
    }
    
    @Override
    public ArrayList<Posicion> AtaquesEspeciales(Tablero tablero) {
        return new ArrayList<>();
    }
}
