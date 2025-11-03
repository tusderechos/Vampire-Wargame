/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fichas;

/**
 *
 * @author Hp
 */

import Tablero.*;
import java.util.ArrayList;

public class Muerte extends Ficha {
    
    public Muerte(Bando Color) {
        super("Muerte", Color, 4, 3, 1); //4 puntos de ataque, 3 puntos de vida, 1 punto de escudo
    }
        
    @Override
    public ArrayList<Posicion> MovimientosBasicos(Tablero tablero) {
        ArrayList<Posicion> MovsPosibles = new ArrayList<>();
        
        for (Posicion posicion : tablero.Adyacentes8(Pos)) {
            Casilla casilla = tablero.get(posicion);
            
            if (casilla != null && casilla.CasillaLibre()) {
                MovsPosibles.add(posicion);
            }
        }
        
        return MovsPosibles;
    }
    
    /*
        Tecnicamente no tiene movimiento especial, ya que su especial es ofensivo / invocacion
    */
    @Override
    public ArrayList<Posicion> MovimientosEspeciales(Tablero tablero) {
        return new ArrayList<>();
    }
    
    @Override
    public ArrayList<Posicion> AtaquesNormales(Tablero tablero) {
        ArrayList<Posicion> Enemigos = new ArrayList<>();
        
        for (Posicion posicion : tablero.Adyacentes8(Pos)) {
            Casilla casilla = tablero.get(posicion);
            
            if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() != this.Color) {
                Enemigos.add(posicion);
            }
        }
        
        return Enemigos;
    }
    
    /*
        aqui combino los dos ataques especiales:
        
        1) Lanza: tira una lanza a 2 casilla en una linea recta, que no tenga obstrucciones, directamente al enemigo
        2) Ataque por zombie: hace que el zombie ataque a cualquier enemigo adyacente
    */
    @Override
    public ArrayList<Posicion> AtaquesEspeciales(Tablero tablero) {
        ArrayList<Posicion> Especiales = new ArrayList<>();
        
        Especiales.addAll(PosicionesLanza(tablero));
        
        Especiales.addAll(EnemigosAlrededorZombieAliados(tablero));
        
        //Evitar duplicados
        ArrayList<Posicion> Unicos = new ArrayList<>();
        for (Posicion posicion : Especiales) {
            boolean ya = false;
            
            for(Posicion pos : Unicos) {
                if (pos.equals(posicion)) {
                    ya = true;
                    break;
                }
            }
            if (!ya) {
                Unicos.add(posicion);
            }
        }
        
        return Unicos;
    }
    
    /*
        Ataque especial #1: Lanza
        Devuelve la posicion de enemigos a exactamente 2 casillas en una linea recta sin ninguna obstruccion entre la muerte y el enemigo
    */
    public ArrayList<Posicion> PosicionesLanza(Tablero tablero) {
        ArrayList<Posicion> res = new ArrayList<>();
        
        int [][] Direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for(int[] direccion : Direcciones) {
            Posicion pos1 = new Posicion(Pos.Fila + direccion[0], Pos.Col + direccion[1]); //Casilla intermedia
            Posicion pos2 = new Posicion(Pos.Fila + 2 * direccion[0], Pos.Col + 2 * direccion[1]); //Objetivo final
            
            if (!tablero.Dentro(pos2)) {
                continue;
            }
            
            Casilla casilla1 = tablero.get(pos1);
            Casilla casilla2 = tablero.get(pos2);
            
            if (casilla1.CasillaLibre() && !casilla2.CasillaLibre() && casilla2.getOcupante().getColor() != this.Color) {
                res.add(pos2);
            }
        }
        
        return res;
    }
    
    /*
        Aqui es donde se aplica el daño al objetivo
    */
    public void EjecutarLanza(Ficha objetivo) {
        if (objetivo == null || objetivo.getColor() == this.Color) {
            return;
        }
        
        objetivo.RecibirDanoDirecto(2);
    }
    
    /*
        Ataque especial #2: 
        Aqui regresa los enemigos que estan adyacentes a cualquier zombie aliado
    */
    public ArrayList<Posicion> EnemigosAlrededorZombieAliados(Tablero tablero) {
        ArrayList<Posicion> res = new ArrayList<>();
        
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                Posicion pos = new Posicion(fila, col);
                Casilla celda = tablero.get(pos);
                
                if (celda == null || celda.CasillaLibre()) {
                    continue;
                }
                
                Ficha fichaZ = celda.getOcupante();
                
                if (fichaZ instanceof Zombie && fichaZ.getColor() == this.Color) {
                    
                    //Revisa enemigos adyacentes a este zombie
                    for (Posicion posi : tablero.Adyacentes8(pos)) {
                        Casilla casillaE = tablero.get(posi);
                        
                        if (casillaE != null && !casillaE.CasillaLibre() && casillaE.getOcupante().getColor() != this.Color) {
                            //agregar si no estaba
                            boolean ya = false;
                            
                            for (Posicion w : res) {
                                if (w.equals(posi)) {
                                    ya = true;
                                    break;
                                }
                            }
                            
                            if (!ya) {
                                res.add(posi);
                            }
                        }
                    }
                }
            }
        }
        
        return res;
    }
    
    /*
        Aqui es donde se ejecuta el ataque po zombie contra el enemigo
    */
    public void EjecutarAtaqueZombie(Ficha objetivo) {
        if (objetivo == null || objetivo.getColor() == this.Color) {
            return;
        }
        
        objetivo.RecibirDano(1);
    }
}
