/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Hp
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Tablero.*;
import Fichas.*;
import Interfaces.Resultable;
import Ruleta.*;


public class PanelJuego extends JFrame {
    
    private Tablero tablero;
    private TableroVisual tableroVisual;
    private PanelRuleta panelRuleta;
    
    private JLabel LblTitulo;
    private JLabel LblTurno;
    private JLabel LblFichaSeleccionada;
    
    private JButton BtnTerminarTurno;
    private JButton BtnSalir;
    
    private JPanel Raiz;
    private JPanel WrapperCentral;
    private JPanel Lateral;
        
    private Bando TurnoActual = Bando.BLANCAS;
    private TipoFicha FichaActual;
    
    private Posicion OrigenSeleccionado = null;
    private boolean EsperandoOrigen = false;
    private boolean EsperandoDestino = false;
    
    private final ArrayList<Posicion> DestinosMovimientos = new ArrayList<>();
    private final ArrayList<Posicion> DestinosAtaques = new ArrayList<>();
    
    public PanelJuego() {
        super("Vampire Wargame - Juego");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        tablero = new Tablero();
        tablero.ColocarInicial();
        
        tableroVisual = new TableroVisual(tablero);
        
        Raiz = new JPanel(new BorderLayout());
        Raiz.setBackground(Color.BLACK);
        
        WrapperCentral = new JPanel();
        WrapperCentral.setLayout(new GridBagLayout());
        WrapperCentral.setBackground(Color.BLACK);
        WrapperCentral.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        WrapperCentral.add(tableroVisual, new GridBagConstraints());
        
        
        Lateral = new JPanel();
        Lateral.setLayout(new BoxLayout(Lateral, BoxLayout.Y_AXIS));
        Lateral.setPreferredSize(new Dimension(260, 600));
        Lateral.setBackground(new Color(40, 40, 40));
        
        LblTitulo = new JLabel("PANEL DE JUEGO");
        LblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblTitulo.setForeground(Color.WHITE);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        LblTurno = new JLabel("Turno: BLANCAS");
        LblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblTurno.setForeground(Color.WHITE);
        LblTurno.setFont(new Font("Arial", Font.PLAIN, 14));
        
        
        LblFichaSeleccionada = new JLabel("Ficha: --");
        LblFichaSeleccionada.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblFichaSeleccionada.setForeground(Color.WHITE);
        LblFichaSeleccionada.setFont(new Font("Arial", Font.PLAIN, 14));
        
        
        panelRuleta = new PanelRuleta();
        panelRuleta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRuleta.setListener(this::onFichaSeleccionada);
        
        
        ImageIcon IconoLobo = new ImageIcon("src/images/ruleta_hombrelobo.PNG");
        ImageIcon IconoVampiro = new ImageIcon("src/images/ruleta_vampiro.PNG");
        ImageIcon IconoMuerte = new ImageIcon("src/images/ruleta_muerte.PNG");
        
        panelRuleta.setIcons(IconoLobo, IconoVampiro, IconoMuerte);
        
        
        BtnTerminarTurno = new JButton("Terminar Turno");
        BtnTerminarTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnTerminarTurno.addActionListener(e -> SiguienteTurno());

        BtnSalir = new JButton("RETIRAR");
        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());

        
        Lateral.add(Box.createVerticalStrut(16));
        Lateral.add(LblTitulo);
        Lateral.add(Box.createVerticalStrut(12));
        Lateral.add(LblTurno);
        Lateral.add(LblFichaSeleccionada);
        Lateral.add(Box.createVerticalStrut(16));
        Lateral.add(panelRuleta);
        Lateral.add(Box.createVerticalStrut(24));
        Lateral.add(BtnTerminarTurno);
        Lateral.add(Box.createVerticalStrut(8));
        Lateral.add(BtnSalir);
        Lateral.add(Box.createVerticalGlue());
        
        
        Raiz.add(WrapperCentral, BorderLayout.CENTER);
        Raiz.add(Lateral, BorderLayout.EAST);
        
        setContentPane(Raiz);
        pack();
        setLocationRelativeTo(null);
        
        tableroVisual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ManejarClickTablero(e.getX(), e.getY());
            }
        });
        
        panelRuleta.setListener(new Resultable() {
            @Override
            public void Seleccionado(TipoFicha tipo) {
                onFichaSeleccionada(tipo);
            }
        });
        
        IniciarTurno();
    }
    
    private void IniciarTurno() {
        LblTurno.setText("Turno: " + (TurnoActual == Bando.BLANCAS ? "BLANCAS" : "NEGRAS"));
        LblFichaSeleccionada.setText("Ficha: --");
        FichaActual = null;
        
        OrigenSeleccionado = null;
        EsperandoOrigen = false;
        EsperandoDestino = false;
        
        DestinosMovimientos.clear();
        DestinosAtaques.clear();
        
        tableroVisual.limpiarDestinos();
        tableroVisual.LimpiarSeleccion();
        
        panelRuleta.setIntentosPorPiezasPerdidas(PiezasPerdidas(TurnoActual));
        
        Girar();
    }
    
    private void Girar() {
        panelRuleta.GiraryDetener(3000);
    }
    
    private void SiguienteTurno() {
        TurnoActual = (TurnoActual == Bando.BLANCAS) ? Bando.NEGRAS : Bando.BLANCAS;
        IniciarTurno();
    }
    
    private void onFichaSeleccionada(TipoFicha tipo) {
        //Resaltar solamente las fichas del tipo seleccionado
        ArrayList<Posicion> posiciones = tablero.BuscarFichasPorTipo(TurnoActual, tipo);
        
        if (posiciones == null || posiciones.isEmpty()) {            
            if (panelRuleta.QuedanIntentos()) {
                panelRuleta.RestarIntentos();
                Girar();
            } else {
                JOptionPane.showMessageDialog(this, "No tienes ninguna ficha " + tipo, "Sin fichas disponibles", JOptionPane.WARNING_MESSAGE);
                SiguienteTurno();
            }
            
            return;
        }
        
        FichaActual = tipo;
        LblFichaSeleccionada.setText("Ficha: " + tipo.name());
        
        tableroVisual.setDestinos(posiciones);
        tableroVisual.repaint();
        
        EsperandoOrigen = true;
        EsperandoDestino = false;
        OrigenSeleccionado = null;
        
        DestinosMovimientos.clear();
        DestinosAtaques.clear();
    }
    
    private void ManejarClickTablero(int origen, int destino) {
        Posicion celda = tableroVisual.PixelaPos(origen, destino);
        
        if (celda == null) {
            return;
        }
        
        if (EsperandoOrigen) {
            SeleccionarOrigen(celda);
            return;
        }
        
        if (EsperandoDestino && OrigenSeleccionado != null) {
            EjecutarAccionSegunDestino(OrigenSeleccionado, celda);
        }
    }
    
    private void SeleccionarOrigen(Posicion celda) {
        Casilla casilla = tablero.get(celda);
        
        if (casilla == null || casilla.CasillaLibre()) {
            return;
        }
        
        Ficha ficha = casilla.getOcupante();
        
        if (ficha.getColor() != TurnoActual) {
            JOptionPane.showMessageDialog(this, "Esa ficha no te pertenece\nElige otra ficha", "Pieza Invalida", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (ficha.getTipo() != FichaActual) {
           JOptionPane.showMessageDialog(this, "Debes elegir una pieza de tipo: " + FichaActual, "Pieza Invalida", JOptionPane.INFORMATION_MESSAGE);
           return;
        }
        
        OrigenSeleccionado = celda;
        EsperandoOrigen = false;
        EsperandoDestino = true;
        
        DestinosMovimientos.clear();
        DestinosAtaques.clear();
        
        //Movimientos
        ArrayList<Posicion> movsbasicos = ficha.MovimientosBasicos(tablero);
        if (movsbasicos != null) {
            DestinosMovimientos.addAll(movsbasicos);
        }
        ArrayList<Posicion> movsespeciales = ficha.MovimientosEspeciales(tablero);
        if (movsespeciales != null) {
            DestinosMovimientos.addAll(movsespeciales);
        }
        
        //Ataques
        ArrayList<Posicion> atknormales = ficha.AtaquesNormales(tablero);
        if (atknormales != null) {
            DestinosAtaques.addAll(atknormales);
        }
        ArrayList<Posicion> atkespeciales = ficha.AtaquesEspeciales(tablero);
        if (atknormales != null) {
            DestinosAtaques.addAll(atkespeciales);
        }
        
        if (ficha instanceof Muerte) {
            Muerte muerte = (Muerte) ficha;
            
            ArrayList<Posicion> lanza = muerte.PosicionesLanza(tablero);
            if (lanza != null) {
                DestinosAtaques.addAll(lanza);
            }
            
            ArrayList<Posicion> zombies = muerte.EnemigosAlrededorZombieAliados(tablero);
            if (zombies != null) {
                DestinosAtaques.addAll(zombies);
            }
        }
        
        //Aqui se juntan los arraylist de destinomovimientos y destinoataque para enviar al tablero
        ArrayList<Posicion> union = new ArrayList<>();
        union.addAll(DestinosMovimientos);
        union.addAll(DestinosAtaques);
        
        tableroVisual.setDestinos(union);
        tableroVisual.Seleccionar(OrigenSeleccionado);
        tableroVisual.repaint();
    }
    
    private void EjecutarAccionSegunDestino(Posicion origen, Posicion destino) {
        Casilla casillaorigen = tablero.get(origen);
        
        if (casillaorigen == null || casillaorigen.CasillaLibre()) {
            return;
        }
                
        boolean movimiento = Contiene(DestinosMovimientos, destino);
        boolean ataque = Contiene(DestinosAtaques, destino);
        
        if (!movimiento && !ataque) {
            JOptionPane.showMessageDialog(this, "Esa casilla no es valida para esta ficha", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {            
            if (movimiento) {
                tablero.MoverFicha(origen, destino);
            } else {
                boolean ok = tablero.Atacar(origen, destino);
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Ataque invalido", "Accion", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            tableroVisual.limpiarDestinos();
            tableroVisual.LimpiarSeleccion();
            tableroVisual.repaint();
            
            FindeAccionyTurno();
            
            SiguienteTurno();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un problema al aplicar la accion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean Contiene(ArrayList<Posicion> lista, Posicion pos) {
        if (lista == null || pos == null) {
            return false;
        }
        
        for(Posicion p : lista) {
            if (p.Fila == pos.Fila && p.Col == pos.Col) {
                return true;
            }
        }
        
        return false;
    }
    
    private int PiezasPerdidas(Bando bando) {
        int TotalInicial = Tablero.TOTAL_PIEZAS_POR_JUGADOR; //Numero de fichas que cada jugador tiene
        int Activas = 0;
        
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                Casilla casilla = tablero.get(new Posicion(fila, col));
                
                if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() == bando) {
                    Activas++;
                }
            }
        }
        
        return TotalInicial - Activas;
    }
    
    private void FindeAccionyTurno() {
        OrigenSeleccionado = null;
        EsperandoOrigen = false;
        EsperandoDestino = false;
        
        DestinosMovimientos.clear();
        DestinosAtaques.clear();
        
        if (panelRuleta.QuedanIntentos()) {
            panelRuleta.RestarIntentos();
            Girar();
        } else {
            SiguienteTurno();
        }
    }
    
    private void onSalir() {
        int Opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres retirarte de la partida?\nTu oponente ganara 3 puntos!\nEsta accion no es reversible", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (Opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
