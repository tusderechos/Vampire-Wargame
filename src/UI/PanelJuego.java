/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Hp
 */

import Enums.Bando;
import Enums.ModoAccion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Tablero.*;
import Fichas.*;
import Interfaces.Providable;
import ManejoDatos.CuentasMem;
import Ruleta.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;


public class PanelJuego extends JFrame {
    
    private Tablero tablero;
    private TableroVisual tableroVisual;
    private PanelRuleta panelRuleta;
    private MenuPrincipal menuPrincipal;
    
    private Providable providable;
    
    private JLabel LblTitulo;
    private JLabel LblTurno;
    private JLabel LblFichaSeleccionada;
    private JLabel LblJugadores;
    
    private JButton BtnTerminarTurno;
    private JButton BtnSalir;
    
    private JPanel Raiz;
    private JPanel WrapperCentral;
    private JPanel Lateral;
    private JPanel CmtBlancas;
    private JPanel CmtNegras;
    
    private JTextArea LogArea;
        
    private Bando TurnoActual = Bando.BLANCAS;
    private TipoFicha FichaActual;
    
    private Posicion OrigenSeleccionado = null;
    private boolean EsperandoOrigen = false;
    private boolean EsperandoDestino = false;
        
    private ModoAccion ModoActual = ModoAccion.NINGUNO;
    
    private boolean PartidaTerminada = false;
    
    private final ArrayList<Posicion> DestinosMovimientos = new ArrayList<>();
    private final ArrayList<Posicion> DestinosAtaques = new ArrayList<>();
    private final ArrayList<Posicion> DestinosInvocacion = new ArrayList<>();
    
    private CuentasMem Memoria;
    private String JugadorBlancas;
    private String JugadorNegras;
    
    private static final int PUNTOS_RETIRO = 3;
    
    public PanelJuego(CuentasMem Memoria, String JugadorBlancas, String JugadorNegras) {
        super("Vampire Wargame - Juego");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        this.Memoria = Memoria;
        this.JugadorBlancas = (JugadorBlancas == null) ? "" : JugadorBlancas.trim();
        this.JugadorNegras = (JugadorNegras == null) ? "" : JugadorNegras.trim();
        
        tablero = new Tablero();
        tablero.ColocarInicial();
        
        tableroVisual = new TableroVisual(tablero);
        if (providable != null) {
            tableroVisual.setProvidable(providable);
        }
        
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
        
        LblJugadores = new JLabel("Blancas: " + JugadorBlancas + "  |   Negras: " + JugadorNegras);
        LblJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblJugadores.setForeground(Color.WHITE);
        LblJugadores.setFont(new Font("Arial", Font.PLAIN, 13));
        
        
        CmtBlancas = new JPanel();
        CmtBlancas.setOpaque(false);
        CmtBlancas.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));
        
        CmtNegras = new JPanel();
        CmtNegras.setOpaque(false);
        CmtNegras.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));
        
        Lateral.add(Box.createVerticalStrut(8));
        Lateral.add(LblTitulo);
        Lateral.add(LblJugadores);
        Lateral.add(Box.createVerticalStrut(6));
        Lateral.add(LblTurno);
        Lateral.add(LblFichaSeleccionada);
        Lateral.add(Box.createVerticalStrut(10));
        
        JLabel lblcmtblanco = new JLabel("Cementerio BLANCAS");
        lblcmtblanco.setForeground(Color.WHITE);
        JLabel lblcmtnegro = new JLabel("Cementerio NEGRAS");
        lblcmtnegro.setForeground(Color.WHITE);
        
        Lateral.add(lblcmtblanco);
        Lateral.setForeground(Color.WHITE);
        Lateral.add(CmtBlancas);
        Lateral.add(Box.createVerticalStrut(6));
        Lateral.add(lblcmtnegro);
        Lateral.add(CmtNegras);
        Lateral.add(Box.createVerticalStrut(10));
        
        LogArea = new JTextArea(8, 20);
        LogArea.setEditable(false);
        LogArea.setBackground(new Color(25, 25, 25));
        LogArea.setForeground(new Color(255, 220, 150));
        LogArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        LogArea.setLineWrap(true);
        LogArea.setWrapStyleWord(true);
        
        JScrollPane ScrollLog = new JScrollPane(LogArea);
        ScrollLog.setAlignmentX(Component.CENTER_ALIGNMENT);
        ScrollLog.setPreferredSize(new Dimension(204, 140));
        
        JLabel lblregistro = new JLabel("Regitro de Jugadas");
        lblregistro.setForeground(Color.WHITE);
        
        Lateral.add(lblregistro);
        Lateral.add(Box.createVerticalStrut(4));
        Lateral.add(ScrollLog);
        Lateral.add(Box.createVerticalStrut(10));
        
        tablero.setCapturaListener(ficha -> {
            ImageIcon icono = (providable != null) ? providable.IconoDe(ficha) : null;
            JLabel lblicono = new JLabel(icono != null ? icono : new ImageIcon());
            
            lblicono.setPreferredSize(new Dimension(28, 28));
            
            if (ficha.getColor() == Bando.BLANCAS) {
                CmtBlancas.add(lblicono);
                CmtBlancas.revalidate();
                CmtBlancas.repaint();
            } else {
                CmtNegras.add(lblicono);
                CmtNegras.revalidate();
                CmtNegras.repaint();
            }            
        });
        
        
        panelRuleta = new PanelRuleta();
        panelRuleta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRuleta.setListener(this::onFichaSeleccionada);
        
        ImageIcon IconoLobo = new ImageIcon("src/images/ruleta_hombrelobo.PNG");
        ImageIcon IconoVampiro = new ImageIcon("src/images/ruleta_vampiro.PNG");
        ImageIcon IconoMuerte = new ImageIcon("src/images/ruleta_muerte.PNG");
        
        panelRuleta.setIcons(IconoLobo, IconoVampiro, IconoMuerte);
        
        Lateral.add(panelRuleta);
        Lateral.add(Box.createVerticalStrut(16));
        
        
        BtnTerminarTurno = new JButton("Terminar Turno");
        BtnTerminarTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnTerminarTurno.addActionListener(e -> SiguienteTurno());

        BtnSalir = new JButton("RETIRAR");
        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());
        
        
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
        
        TurnoActual = Bando.BLANCAS;
        ActualizarTurnoUI();
        IniciarTurno();        
    }
    
    private void log(String msg) {
        if (LogArea == null) {
            return;
        }
        
        LogArea.append(msg + "\n");
        LogArea.setCaretPosition(LogArea.getDocument().getLength());
    }
    
    private static String pos(Posicion p) {
        return "(" + p.Fila + "," + p.Col + ")";
    }
    
    private void MostrarMenuAccion(Ficha ficha, Posicion celda) {
        JPopupMenu menu = new JPopupMenu();
        
        //Mover
        if (DestinosMovimientos != null && !DestinosMovimientos.isEmpty()) {
            JMenuItem mover = new JMenuItem(ModoAccion.MOVER.getDescripcion());
            mover.addActionListener(e -> ActivarModo(ModoAccion.MOVER));
            
            menu.add(mover);
        }
        
        //Ataque normal
        if (DestinosAtaques != null && !DestinosAtaques.isEmpty()) {
            JMenuItem atacar = new JMenuItem(ModoAccion.ATACAR.getDescripcion());
            atacar.addActionListener(e -> ActivarModo(ModoAccion.ATACAR));
            
            menu.add(atacar);
        }
        
        //Ataque especial generico/Muerte
        if (ficha instanceof Muerte && DestinosAtaques != null && !DestinosAtaques.isEmpty()) {
            JMenuItem especial = new JMenuItem(ModoAccion.ATACAR_ESPECIAL.getDescripcion());
            especial.addActionListener(e -> ActivarModo(ModoAccion.ATACAR_ESPECIAL));
            
            menu.add(especial);
        }
        
        //Chupar sangre
        if (ficha instanceof Vampiro && DestinosAtaques != null && !DestinosAtaques.isEmpty()) {
            JMenuItem chupar = new JMenuItem(ModoAccion.ESPECIAL_VAMPIRO.getDescripcion());
            chupar.addActionListener(e -> ActivarModo(ModoAccion.ESPECIAL_VAMPIRO));
            
            menu.add(chupar);
        }
        
        //Invocar zombie
        if (ficha instanceof Muerte && DestinosInvocacion != null && !DestinosInvocacion.isEmpty()) {
            JMenuItem invocar = new JMenuItem(ModoAccion.INVOCAR.getDescripcion());
            invocar.addActionListener(e -> ActivarModo(ModoAccion.INVOCAR));
            
            menu.add(invocar);
        }
        
        if (menu.getComponentCount() == 0) {
            //Sin acciones disponibles
            JMenuItem nada = new JMenuItem("Sin acciones disponibles");
            nada.setEnabled(false);
            
            menu.add(nada);
        }
        
        int offX = tableroVisual.TamanoCelda + 6;
        int offY = 0;
        
        int pixelX = celda.Col * tableroVisual.TamanoCelda;
        int pixelY = celda.Fila * tableroVisual.TamanoCelda;
        
        menu.show(tableroVisual, pixelX + offX, pixelY + offY);
    }
    
    private void IniciarTurno() {
        ActualizarTurnoUI();
        
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
        ActualizarTurnoUI();
        IniciarTurno();
    }
    
    private void ChequearFinPartida() {
        if (PartidaTerminada) {
            return;
        }
        int blancas = ContarPiezas(Bando.BLANCAS);
        int negras = ContarPiezas(Bando.NEGRAS);
        
        if (blancas == 0 || negras == 0) {
            String ganador = (blancas > 0) ? JugadorBlancas : JugadorNegras;
            String perdedor = (blancas > 0) ? JugadorNegras : JugadorBlancas;
        
            TerminarPartida(ganador, perdedor, "Eliminacion Total");
        }
    }
    
    private void onFichaSeleccionada(TipoFicha tipo) {
        //Resaltar solamente las fichas del tipo seleccionado
        ArrayList<Posicion> posiciones = tablero.BuscarFichasPorTipo(TurnoActual, tipo);
        
        if (posiciones == null || posiciones.isEmpty()) {            
            if (panelRuleta.QuedanIntentos()) {
                panelRuleta.RestarIntentos();
                
                JOptionPane.showMessageDialog(this, "No tienes niguna ficha " + tipo, "Sin fichas disponibles", JOptionPane.WARNING_MESSAGE);
                
                Girar();
            } else {
                JOptionPane.showMessageDialog(this, "No tienes ninguna ficha " + tipo, "Sin fichas disponibles", JOptionPane.WARNING_MESSAGE);
                SiguienteTurno();
            }
            
            return;
        }
        
        FichaActual = tipo;
        LblFichaSeleccionada.setText("Ficha: " + tipo.name());
        
        tableroVisual.setDestinosMovimientos(posiciones);
        tableroVisual.setDestinosAtaques(new ArrayList<Posicion>());
        tableroVisual.setDestinosInvocacion(new ArrayList<Posicion>());
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
    
    private void ActualizarTurnoUI() {
        String bandostr = (TurnoActual == Bando.BLANCAS) ? "BLANCAS" : "NEGRAS";
        LblTurno.setText("Turno: " + bandostr + " (" + NombreTurnoActual() + ")");
    }
    
    private void SumarPuntos(String usuario, int delta, String motivo) {
        if (Memoria == null || usuario == null) {
            return;
        }
        
        int indice = Memoria.getIndiceUsuario(usuario);
        
        if (indice < 0) {
            return;
        }
        
        Memoria.SumarPuntos(indice, delta);
    }
    
    private void RegistrarLogPartida(String actor, String rival, String resultado) {
        if (Memoria == null) {
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fecha = sdf.format(new Date());
        
        Memoria.AgregarLog(actor, fecha, rival, resultado);
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
                DestinosInvocacion.addAll(zombies);
            }
        }

        
        tableroVisual.setDestinosMovimientos(DestinosMovimientos);
        tableroVisual.setDestinosAtaques(DestinosAtaques);
        tableroVisual.setDestinosInvocacion(DestinosInvocacion);
        
        tableroVisual.Seleccionar(OrigenSeleccionado);
        tableroVisual.repaint();
        
        MostrarMenuAccion(ficha, celda);
    }
    
    private void EjecutarAccionSegunDestino(Posicion origen, Posicion destino) {
        Casilla casillaorigen = tablero.get(origen);
        
        if (casillaorigen == null || casillaorigen.CasillaLibre()) {
            return;
        }
                
        boolean movimiento = Contiene(DestinosMovimientos, destino);
        boolean ataque = Contiene(DestinosAtaques, destino);
        boolean invocar = Contiene(DestinosInvocacion, destino);
        
        Ficha ficha = casillaorigen.getOcupante();
        String jugador = (TurnoActual == Bando.BLANCAS) ? JugadorBlancas : JugadorNegras;
        String posorg = "(" + origen.Fila + ", " + origen.Col + ")";
        String posdest = "(" + destino.Fila + ", " + destino.Col + ")";
        
        try {    
            boolean aplicado = false;
            
            switch(ModoActual) {
                case MOVER:
                    if (!movimiento) {
                        JOptionPane.showMessageDialog(this, "Esa casilla no es valida para mover", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    tablero.MoverFicha(origen, destino);
                    
                    if (LogArea != null) {
                        LogArea.append(jugador + " movio " + ficha.getNombre() + " de " + posorg + " a " + posdest + "\n");
                        LogArea.setCaretPosition(LogArea.getDocument().getLength());
                    }
                    
                    aplicado = true;
                    break;
                    
                case ATACAR:
                    if (!ataque) {
                        JOptionPane.showMessageDialog(this, "Esa casilla no es valida para atacar", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    Casilla casdestantes = tablero.get(destino);
                    Ficha objetivoantes = (casdestantes != null) ? casdestantes.getOcupante() : null;
                    String nombreobj = (objetivoantes != null) ? objetivoantes.getNombre() : "pieza";
                    
                    if (!tablero.Atacar(origen, destino)) {
                        JOptionPane.showMessageDialog(this, "Ataque invalido", "Accion", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    Casilla casdestdespues = tablero.get(destino);
                    boolean eliminado = (casdestdespues == null) || casdestdespues.CasillaLibre();
                    
                    if (LogArea != null) {
                        if (eliminado) {
                            LogArea.append(jugador + " ataco con " + ficha.getNombre() + " en " + posdest + " y elimino a " + nombreobj + "\n");
                        } else {
                            LogArea.append(jugador + " ataco con " + ficha.getNombre() + " a " + nombreobj + " en " + posdest + "\n");
                        }
                        
                        LogArea.setCaretPosition(LogArea.getDocument().getLength());
                    }
                    
                    aplicado = true;
                    break;
                    
                case ATACAR_ESPECIAL:
                    if (!ataque) {
                        JOptionPane.showMessageDialog(this, "Esa casilla no es valida para hacer un ataque especial", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    try {
                        aplicado = tablero.AtacarFicha(origen, destino, true);
                    } catch (Throwable e) {
                        aplicado = tablero.AtacarFicha(origen, destino, true);
                    }
                    
                    if (!aplicado) {
                        JOptionPane.showMessageDialog(this, "Ataque especial invalido", "Accion", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (LogArea != null) {
                        LogArea.append(jugador + " lanzo ATAQUE ESPECIAL con " + ficha.getNombre() + " sobre " + posdest + "\n");
                        LogArea.setCaretPosition(LogArea.getDocument().getLength());
                    }
                    
                    aplicado = true;
                    break;
                    
                case ESPECIAL_VAMPIRO:
                    if (!ataque) {
                        JOptionPane.showMessageDialog(this, "No puedes usar tu ataque especial ahi", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    Casilla casdest = tablero.get(destino);
                    if (casdest == null || casdest.CasillaLibre()) {
                        JOptionPane.showMessageDialog(this, "No hay objetivo para 'Chupar Sangre'", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    Ficha objetivo = casdest.getOcupante();
                    String nombreobj2 = (objetivo != null) ? objetivo.getNombre() : "pieza";
                    
                    if (!tablero.AtaqueChuparSangre(origen, destino)) {
                        JOptionPane.showMessageDialog(this, "Ataque especial 'Chupar Sangre' invalido", "Accion", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    boolean eliminado2 = false;
                    Casilla casdespues = tablero.get(destino);
                    eliminado2 = (casdespues == null) || casdespues.CasillaLibre();
                    
                    if (LogArea != null) {
                        LogArea.append(jugador + " uso CHUPAR SANGRE con " + ficha.getNombre() + " en " + posdest + (eliminado2 ? " y elimino a" + nombreobj2 : "") + "\n");
                        LogArea.setCaretPosition(LogArea.getDocument().getLength());
                    }
                    
                    aplicado = true;
                    break;
                    
                case INVOCAR:
                    if (!invocar) {
                        JOptionPane.showMessageDialog(this, "Debes elegir una casilla vacia para poder invocar un zombie", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (!tablero.InvocarZombie(origen, destino)) {
                        JOptionPane.showMessageDialog(this, "Invocacion invalida", "Accion", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (LogArea != null) {
                        LogArea.append(jugador + " incovo un ZOMBIE en " + posdest + "\n");
                        LogArea.setCaretPosition(LogArea.getDocument().getLength());
                    }
                    
                    aplicado = true;
                    break;
                    
                case NINGUNO:
                default:
                    SeleccionarOrigen(destino);
                    return;
            }
            
            if (aplicado) {
                tableroVisual.limpiarDestinos();
                tableroVisual.LimpiarSeleccion();
                
                tableroVisual.repaint();
                
                FindeAccionyTurno();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un problema al aplicar la accion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void TerminarPartida(String ganador, String perdedor, String causa) {
        if (PartidaTerminada) {
            return;
        }
        
        PartidaTerminada = true;
        
        if (panelRuleta != null) {
            panelRuleta.Detener();
        }
        
        JOptionPane.showMessageDialog(this, "Victoria de " + ganador + "!\n (" + causa + ")");
        
        RegistrarLogPartida(ganador, perdedor, ganador + " ha ganado contra el jugador " + perdedor + "( " + causa + ")");
        RegistrarLogPartida(perdedor, ganador, perdedor + " ha perdido contra el jugador " + ganador + "( " + causa + ")");
        
        if (Memoria != null) {
            int indice = Memoria.getIndiceUsuario(ganador);
            
            if (indice != -1) {
                Memoria.SumarPuntos(indice, 3);
            }
        }
        
        dispose();
        
        if (menuPrincipal != null) {
            menuPrincipal.setVisible(true);
        }
    }
    
    private String NombreTurnoActual() {
        return (TurnoActual == Bando.BLANCAS) ? JugadorBlancas : JugadorNegras;
    }
    
    private String NombreTurnoOponente() {
        return (TurnoActual == Bando.BLANCAS) ? JugadorNegras : JugadorBlancas;
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
    
    private int ContarPiezas(Bando bando) {
        int c = 0;
        
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                Casilla casilla = tablero.get(new Posicion(fila, col));
                
                if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() == bando) {
                    c++;
                }
            }
        }
        
        return c;
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
        if (PartidaTerminada) {
            return;
        }
        
        ChequearFinPartida();
        if (PartidaTerminada) {
            return;
        }
        
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
    
    private void ActivarModo(ModoAccion modo) {
        ModoActual = modo;
        
        switch (modo) {
            case MOVER:
                tableroVisual.setDestinosAtaques(new ArrayList<Posicion>());
                tableroVisual.setDestinosInvocacion(new ArrayList<Posicion>());
                break;
                
            case ATACAR:
            case ATACAR_ESPECIAL:
            case ESPECIAL_VAMPIRO:
                tableroVisual.setDestinosMovimientos(new ArrayList<Posicion>());
                tableroVisual.setDestinosInvocacion(new ArrayList<Posicion>());
                break;
                
            case INVOCAR:
                tableroVisual.setDestinosAtaques(new ArrayList<Posicion>());
                tableroVisual.setDestinosMovimientos(new ArrayList<Posicion>());
                break;
                
            default:
                break;
        }
        
        tableroVisual.repaint();
        LblFichaSeleccionada.setText("Modo: " + ModoActual.getDescripcion());
    }
    
    private void onSalir() {
        int Opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres retirarte de la partida?\nTu oponente ganara 3 puntos!\nEsta accion no es reversible", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (Opcion == JOptionPane.YES_OPTION) {
            String rival = NombreTurnoOponente();
            
            SumarPuntos(rival, PUNTOS_RETIRO, "RETIRO DEL RIVAL");
            RegistrarLogPartida(NombreTurnoActual(), rival, "RETIRO");
            dispose();
        }
    }
}
