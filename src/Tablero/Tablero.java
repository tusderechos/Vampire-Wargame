/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablero;

/**
 *
 * @author Hp
 */

import Enums.Bando;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import Fichas.*;
import Ruleta.TipoFicha;
import Interfaces.Capturable;

public final class Tablero {
    
    private int Filas;
    private int Cols;
    private Casilla[][] grid;
    public static final int TOTAL_PIEZAS_POR_JUGADOR = 6;
    
    private Capturable CapturaListener;
    
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
    
    public void setCapturaListener(Capturable listener) {
        this.CapturaListener = listener;
    }
    
    private void NotificarCaptura(Ficha ficha) {
        if (CapturaListener != null && ficha != null) {
            CapturaListener.onCaptura(ficha);
        }
    }
    
    public final int getFilas() {
        return Filas;
    }
    public final int getColumnas() {
        return Cols;
    }
    
    public boolean Dentro(Posicion Pos) {
        return Pos != null && Pos.Fila >= 0 && Pos.Fila < Filas && Pos.Col >= 0 && Pos.Col < Cols;
    }
    
    public Casilla get(Posicion Pos) {
        if (!Dentro(Pos)) {
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
    
    public void ColocarInicial() {
        //Limpiar
        for (int fila = 0; fila < Filas; fila++) {
            for (int col = 0; col < Cols; col++) {
                grid[fila][col].LiberarCasilla();
            }
        }
        
        //Fichas blancas
        Colocar(new HombreLobo(Bando.NEGRAS), new Posicion(0, 0));
        Colocar(new Vampiro(Bando.NEGRAS), new Posicion(0, 1));
        Colocar(new Muerte(Bando.NEGRAS), new Posicion(0, 2));
        Colocar(new Muerte(Bando.NEGRAS), new Posicion(0, 3));
        Colocar(new Vampiro(Bando.NEGRAS), new Posicion(0, 4));
        Colocar(new HombreLobo(Bando.NEGRAS), new Posicion(0, 5));
        
        //Fichas negras
        Colocar(new HombreLobo(Bando.BLANCAS), new Posicion(5, 0));
        Colocar(new Vampiro(Bando.BLANCAS), new Posicion(5, 1));
        Colocar(new Muerte(Bando.BLANCAS), new Posicion(5, 2));
        Colocar(new Muerte(Bando.BLANCAS), new Posicion(5, 3));
        Colocar(new Vampiro(Bando.BLANCAS), new Posicion(5, 4));
        Colocar(new HombreLobo(Bando.BLANCAS), new Posicion(5, 5));
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
        
        if (!CasillaDestino.CasillaLibre()) {
            JOptionPane.showMessageDialog(null, "La casilla de destino esta ocupada: " + destino);
            return;
        }
        
        Ficha ficha = CasillaOrigen.getOcupante();
        
        CasillaOrigen.LiberarCasilla();
        CasillaDestino.OcuparCasilla(ficha);
        
        ficha.setPos(destino);
    }
    
    public void QuitarFicha(Posicion Pos) {
        if (!Dentro(Pos)) {
            return;
        }
        
        Casilla casilla = get(Pos);
        
        if (casilla == null || casilla.CasillaLibre()) {
            return;
        }
        
        Ficha capturada = casilla.getOcupante();
        casilla.LiberarCasilla();
                
        NotificarCaptura(capturada);
    }
    
    public boolean AtacarFicha(Posicion origen, Posicion destino, boolean especial) {
        if (!Dentro(origen) || !Dentro(destino)) {
            return false;
        }
        
        Casilla casillaorigen = get(origen);
        Casilla casilladestino = get(destino);
        
        if (casillaorigen == null || casilladestino == null || casillaorigen.CasillaLibre() || casilladestino.CasillaLibre()) {
            return false;
        }
        
        Ficha atacante = casillaorigen.getOcupante();
        Ficha objetivo = casilladestino.getOcupante();
        
        if (atacante.getColor() == objetivo.getColor()) {
            return false;
        }
        
        //Verifca que el destino este autorizado por la pieza en si
        boolean normales = ContienePos(atacante.AtaquesNormales(this), destino);
        boolean especiales = ContienePos(atacante.AtaquesEspeciales(this), destino);
        
        if (!normales && !especiales) {
            return false;
        }
        
        if (!especial) {
            
            if (!normales) {
                //Si no esta en la lita de ataques normales, no se puede usar como ataque normal
                return false;
            }
            
            objetivo.RecibirDano(atacante.getAtaque());
            
        } else {
            //Ataque especial
            if (atacante instanceof Vampiro) {
                //Falso porque el vampiro usa su propia ruta especial, que es la de chupar sangre, no usa esta funcion
                return false;
            }
            
            if (!(atacante instanceof Muerte)) {
                
                if (!especiales) {
                    return false;
                }
                
                objetivo.RecibirDanoDirecto(atacante.getAtaque());
            } else {
                Muerte muerte = (Muerte) atacante;
                
                //Lanza bendita
                if (ContienePos(muerte.PosicionesLanza(this), destino)) {
                    objetivo.RecibirDanoDirecto(2);
                    
                //Ataque con zombie
                } else if (ContienePos(muerte.EnemigosAlrededorZombieAliados(this), destino)) {
                    objetivo.RecibirDano(1);
                } else {
                    return false;
                }
            }
        }
        
        if (objetivo.getVidas() <= 0) {
            QuitarFicha(destino);
        }
        
        return true;
    }
    
    public boolean Atacar(Posicion origen, Posicion destino) {
        return AtacarFicha(origen, destino, false);
    }
    
    private boolean ContienePos(ArrayList<Posicion> lista, Posicion pos) {
        if (lista == null || pos == null) {
            return false;
        }
        
        for (Posicion posicion : lista) {
            if (posicion != null && posicion.Fila == pos.Fila && posicion.Col == posicion.Col) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean AtaqueChuparSangre(Posicion origen, Posicion destino) {
        if (!Dentro(origen) || !Dentro(destino)) {
            return false;
        }
        
        Casilla casillaorigen = get(origen);
        Casilla casilladestino = get(destino);
        
        if (casillaorigen == null || casilladestino == null || casillaorigen.CasillaLibre() || casilladestino.CasillaLibre()) {
            return false;
        }
        
        Ficha atacante = casillaorigen.getOcupante();
        Ficha objetivo = casilladestino.getOcupante();
        
        if (!(atacante instanceof Vampiro)) {
            return false;
        }
        if (atacante.getColor() == objetivo.getColor()) {
            return false;
        }
        if (!esAdyacente8(origen, destino)) {
            return false;
        }
        
        objetivo.RecibirDanoDirecto(1);
        
        //Curar al vampiro todo poderoso roto y destructor de mundos
        try {
            atacante.setVidas(atacante.getVidas() + 1);
        } catch (Exception e) {
        }
        
        //Automaticamente quitar pieza al morir
        try {
            int vidarestante = objetivo.getVidas();
            
            if (vidarestante <= 0) {
                QuitarFicha(destino);
            }
        } catch (Exception e) {
        }
        
        return true;
    }
    
    public boolean InvocarZombie(Posicion origen, Posicion destino) {
        if (!Dentro(origen) || !Dentro(destino)) {
            return false;
        }
        
        Casilla casillaorigen = get(origen);
        Casilla casilladestino = get(destino);
        
        if (casillaorigen == null || casilladestino == null || casillaorigen.CasillaLibre()) {
            return false;
        }
        
        Ficha muerte = casillaorigen.getOcupante();
        
        if (!(muerte instanceof Muerte)) {
            return false;
        }
        if (!casilladestino.CasillaLibre()) {
            return false;
        }
        
        Zombie nuevo = new Zombie(muerte.getColor());
        
        casilladestino.OcuparCasilla(nuevo);
        nuevo.setPos(destino);
        
        return true;
    }
    
    public int ContarPiezas(int fila, int col, Bando bando) {
        if (fila >= getFilas()) {
            return 0;
        }
        
        int siguientefila = fila;
        int siguientecol = col + 1;
        
        if (siguientecol >= getColumnas()) {
            siguientecol = 0;
            siguientefila++;
        }
        
        int suma = ContarPiezas(siguientefila, siguientecol, bando);
        
        Casilla casilla = get(new Posicion(fila, col));
        
        if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() == bando) {
            suma++;
        }
        
        return suma;
    }
    
    private boolean esAdyacente8(Posicion a, Posicion b) {
        int filasdiagonal = Math.abs(a.Fila - b.Fila);
        int colsdiagonal = Math.abs(a.Col - b.Col);
        
        return (filasdiagonal <= 1 && colsdiagonal <= 1 && !(filasdiagonal == 0 && colsdiagonal == 0));
    }
    
    public ArrayList<Posicion> Adyacentes8(Posicion Pos) {
        ArrayList<Posicion> res = new ArrayList<>();
        
        for (int direccionfila = -1; direccionfila <= 1; direccionfila++) {
            for (int direccioncol = -1; direccioncol <= 1; direccioncol++) {
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
           
            if (Dentro(posicion)) {
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
    
    public ArrayList<Posicion> BuscarFichasPorTipo(Bando Color, TipoFicha tipo) {
        ArrayList<Posicion> resultado = new ArrayList<>();
        
        for (int fila = 0; fila < Filas; fila++) {
            for (int col = 0; col < Cols; col++) {
                Casilla casilla = grid[fila][col];
                
                if (!casilla.CasillaLibre()) {
                    Ficha ficha = casilla.getOcupante();
                    
                    if (ficha.getColor() == Color && ficha.getTipo() == tipo) {
                        resultado.add(new Posicion(fila, col));
                    }
                }
            }
        }
        
        return resultado;
    }
    
    public boolean JugadorTieneFichaTipo(Bando bando, TipoFicha tipo) {
        for (int fila = 0; fila < getFilas(); fila++) {
            for (int col = 0; col < getColumnas(); col++) {
                Casilla casilla = get(new Posicion(fila, col));
                
                if (casilla != null && !casilla.CasillaLibre()) {
                    Ficha ficha = casilla.getOcupante();
                    
                    if (ficha.getColor() == bando && ficha.getTipo() == tipo) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}
