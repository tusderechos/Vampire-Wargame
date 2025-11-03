/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablero;

/**
 *
 * @author Hp
 */

import Fichas.Ficha;
import javax.swing.JOptionPane;

public class Casilla {
    
    private Posicion Pos;
    private Ficha Ocupante;
    
    public Casilla(Posicion Pos) {
        this.Pos = Pos;
        this.Ocupante = null;
    }
    
    public Posicion getPos() {
        return Pos;
    }
    
    public boolean CasillaLibre() {
        return Ocupante == null;
    }
    
    public Ficha getOcupante() {
        return Ocupante;
    }
    
    public void OcuparCasilla(Ficha ficha) {
        if (ficha == null) {
            JOptionPane.showMessageDialog(null, "Elige una ficha para mover");
            return;
        }
        
        if (!CasillaLibre()) {
            JOptionPane.showMessageDialog(null, "La casilla " + Pos + " ya esta ocupada");
            return;
        }
        
        this.Ocupante = ficha;
        ficha.setPos(Pos);
    }
    
    public void LiberarCasilla() {
        this.Ocupante = null;
    }
}
