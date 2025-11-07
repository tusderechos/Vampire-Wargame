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

public class Vampiro extends Ficha {
    
    public Vampiro(Bando Color) {
        super("Vampiro", Color, TipoFicha.VAMPIRO, 3, 4, 5); //3 puntos de ataque, 4 puntos de vida, 5 puntos de escudo
    }
    
    @Override
    public ArrayList<Posicion> MovimientosBasicos(Tablero tablero) {
        ArrayList<Posicion> res = new ArrayList<>();
        
        for (Posicion pos : tablero.Adyacentes8(Pos)) {
            Casilla casilla = tablero.get(pos);
            
            if (casilla != null && casilla.CasillaLibre()) {
                res.add(pos);
            }
        }
        
        return res;
    }
    
    @Override
    public ArrayList<Posicion> MovimientosEspeciales(Tablero tablero) {
        return new ArrayList<>();
    }
    
    @Override
    public ArrayList<Posicion> AtaquesNormales(Tablero tablero) {
        ArrayList<Posicion> Enemigos = new ArrayList<>();
        
        for (Posicion adyacente : tablero.Adyacentes8(Pos)) {
            Casilla casilla = tablero.get(adyacente);
            
            if (!casilla.CasillaLibre() && casilla.getOcupante().getColor() != this.Color) {
                Enemigos.add(adyacente);
            }
        }
        
        return Enemigos;
    }
    
    @Override
    public ArrayList<Posicion> AtaquesEspeciales(Tablero tablero) {
        return AtaquesNormales(tablero);
    }
    
    public void ChuparSangre(Ficha enemigo) {
        enemigo.RecibirDano(1);
        this.Vidas += 1;
    }
}
