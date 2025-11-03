/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablero;

/**
 *
 * @author Hp
 */

import javax.swing.JOptionPane;
import java.util.ArrayList;
import Fichas.Ficha;

public class Tablero {
    
    private int Filas;
    private int Cols;
    private Casilla[][] grid;
    
    public Tablero(int Filas, int Cols) {
        this.Filas = Filas;
        this.Cols = Cols;
        this.grid = new Casilla[Filas][Cols];
        
        for (int fila = 0; fila < Filas; fila++) {
            for (int col = 0; col < Cols; col++) {
                grid[fila][col] = new Casilla(new Posicion(fila, col));
            }
        }
    }
    
    public Tablero() {
        this(6, 6);
    }
    
    public int getFilas() {
        return Filas;
    }
    public int getColumnas() {
        return Cols;
    }
    
    public boolean Dentro(Posicion Pos) {
        return Pos != null && Pos.Fila >= 0 && Pos.Fila < Filas && Pos.Col >= 0 && Pos.Col < Cols;
    }
    
    public Casilla get(Posicion Pos) {
        if (!Dentro(Pos)) {
            JOptionPane.showMessageDialog(null, "Posicion fuera del tablero");
            return null;
        }
        
        return grid[Pos.Fila][Pos.Col];
    }
    
    public void Colocar(Ficha ficha, Posicion Pos) {
        if (ficha == null) {
            JOptionPane.showMessageDialog(null, "Elige una ficha para mover primero");
            return;
        }
        
        if (!Dentro(Pos)) {
            JOptionPane.showMessageDialog(null, "Posicion fuera del tablero");
            return;
        }
        
        Casilla destino = get(Pos);
        
        if (!destino.CasillaLibre()) {
            JOptionPane.showMessageDialog(null, "Esta casilla " + Pos + " ya esta ocupada");
            return;
        }
        
        destino.OcuparCasilla(ficha);
        ficha.setPos(Pos);
    }
    
    public void MoverFicha(Posicion origen, Posicion destino) {
        if (!Dentro(origen) || !Dentro(destino)) {
            JOptionPane.showMessageDialog(null, "Ese movimiento no esta permitido");
            return;
        }
        
        Casilla CasillaOrigen = get(origen);
        Casilla CasillaDestino = get(destino);
        
        if (CasillaOrigen.CasillaLibre()) {
            JOptionPane.showMessageDialog(null, "No hay ficha en la posicion origen: " + origen);
            return;
        }
        
        if (CasillaDestino.CasillaLibre()) {
            JOptionPane.showMessageDialog(null, "La casilla de destino esta ocupada: " + destino);
            return;
        }
        
        Ficha ficha = CasillaOrigen.getOcupante();
        
        CasillaOrigen.LiberarCasilla();
        CasillaDestino.OcuparCasilla(ficha);
        
        ficha.setPos(destino);
    }
    
    public void QuitarFicha(Posicion Pos) {
        Casilla casilla = get(Pos);
        
        if (casilla == null) {
            return;
        }
        if (!casilla.CasillaLibre()) {
            Ficha ficha = casilla.getOcupante();
            
            ficha.Eliminar();
            casilla.LiberarCasilla();
        }
    }
    
    public ArrayList<Posicion> Adyacentes8(Posicion Pos) {
        ArrayList<Posicion> res = new ArrayList<>();
        
        for (int direccionfila = -1; direccionfila <= 1; direccionfila++) {
            for (int direccioncol = 0; direccioncol < 10; direccioncol++) {
                if (direccionfila == 0 && direccioncol == 0) {
                    continue;
                }
                
                Posicion posicion = new Posicion(Pos.Fila + direccionfila, Pos.Col + direccioncol);
                
                if (Dentro(posicion)) {
                    res.add(posicion);
                }
            }
        }
        
        return res;
    }
    
    public ArrayList<Posicion> Adyacentes4(Posicion Pos) {
        ArrayList<Posicion> res = new ArrayList<>();
        
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] direccion : direcciones) {
           Posicion posicion = new Posicion(Pos.Fila + direccion[0], Pos.Col + direccion[1]);
            if (Dentro(Pos)) {
                res.add(posicion);
            }
        }
        
        return res;
    }
    
    /*
        Para ataque de lanza de la Muerte
    */
    public boolean LineaRectaDespejada(Posicion a, Posicion b) {
        if (a.Fila != b.Fila && a.Col != b.Col) {
            return false;
        }
        
        int direccionfila = Integer.compare(b.Fila, a.Fila);
        int direccioncol = Integer.compare(b.Col, a.Col);
        
        int fila = a.Fila + direccionfila;
        int col = a.Col + direccioncol;
        
        while (fila != b.Fila || col != b.Col) {
            Posicion actual = new Posicion(fila, col);
            
            if (!get(actual).CasillaLibre()) {
                return false;
            }
            
            fila += direccionfila;
            col += direccioncol;
        }
        
        return true;
    }
}
