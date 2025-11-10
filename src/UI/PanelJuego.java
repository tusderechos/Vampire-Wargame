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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    private boolean RuletaEnProceso = false;
    
    private final ArrayList<Posicion> DestinosMovimientos = new ArrayList<>();
    private final ArrayList<Posicion> DestinosAtaques = new ArrayList<>();
    private final ArrayList<Posicion> DestinosInvocacion = new ArrayList<>();
    
    private CuentasMem Memoria;
    private String JugadorBlancas;
    private String JugadorNegras;
    
    private static final int PUNTOS_RETIRO = 3;
    
    public PanelJuego(CuentasMem Memoria, String JugadorBlancas, String JugadorNegras, MenuPrincipal menuPrincipal) {
        super("Vampire Wargame - Juego");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        this.Memoria = Memoria;
        this.menuPrincipal = menuPrincipal;
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
        CmtBlancas.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));
        CmtBlancas.setOpaque(true);
        CmtBlancas.setBackground(new Color(30, 30, 30));
        CmtBlancas.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        CmtBlancas.setPreferredSize(new Dimension(220, 40));
        CmtBlancas.setMaximumSize(new Dimension(220, 40));
        CmtBlancas.setMinimumSize(new Dimension(220, 40));
        
        CmtNegras = new JPanel();
        CmtNegras.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));
        CmtNegras.setOpaque(true);
        CmtNegras.setBackground(new Color(30, 30, 30));
        CmtNegras.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        CmtNegras.setPreferredSize(new Dimension(220, 40));
        CmtNegras.setMaximumSize(new Dimension(220, 40));
        CmtNegras.setMinimumSize(new Dimension(220, 40));
        
        JScrollPane ScrollCmtBlancas = new JScrollPane(CmtBlancas);
        ScrollCmtBlancas.setPreferredSize(new Dimension(220, 45));
        ScrollCmtBlancas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ScrollCmtBlancas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollCmtBlancas.setBorder(null);
        
        JScrollPane ScrollCmtNegras = new JScrollPane(CmtNegras);
        ScrollCmtNegras.setPreferredSize(new Dimension(220, 45));
        ScrollCmtNegras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ScrollCmtNegras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollCmtNegras.setBorder(null);
        
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
        Lateral.add(Box.createVerticalStrut(2));
        Lateral.add(ScrollCmtBlancas);
        
        Lateral.add(Box.createVerticalStrut(6));
        
        Lateral.add(lblcmtnegro);
        Lateral.add(Box.createVerticalStrut(2));
        Lateral.add(ScrollCmtNegras);
        
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
        
        JLabel lblregistro = new JLabel("Registro de Jugadas");
        lblregistro.setForeground(Color.WHITE);
        
        Lateral.add(lblregistro);
        Lateral.add(Box.createVerticalStrut(4));
        Lateral.add(ScrollLog);
        Lateral.add(Box.createVerticalStrut(10));
        
        tablero.setCapturaListener(ficha -> {
            if (ficha == null) {
                return;
            }
            
            System.out.println("CAPTURADA PANEL: " + ficha.getNombre() + " (" + ficha.getColor() + ")");
            
            //Intentar usar el mismo povidable que lo que tiene el tablero visual
            ImageIcon iconobase = (providable != null) ? providable.IconoDe(ficha) : null;
                        
            if (iconobase == null) {
                String ruta = "src/images/";
                
                if (ficha instanceof HombreLobo) {
                    ruta += (ficha.getColor() == Bando.BLANCAS) ? "IconoHombreLoboBlanco.PNG" : "IconoHombreLoboNegro.PNG";
                } else if (ficha instanceof Vampiro) {
                    ruta += (ficha.getColor() == Bando.BLANCAS) ? "IconoVampiroBlanco.PNG" : "IconoVampiroNegro.PNG";
                } else if (ficha instanceof Muerte) {
                    ruta += (ficha.getColor() == Bando.BLANCAS) ? "IconoMuerteBlanco.PNG" : "IconoMuerteNegro.PNG";
                } else if (ficha instanceof Zombie) {
                    ruta += (ficha.getColor() == Bando.BLANCAS) ? "IconoZombieBlanco.PNG" : "IconoZombieNegro.PNG";
                } else {
                    ruta = null;
                }
                
                if (ruta != null) {                    
                    iconobase = new ImageIcon(ruta);
                }
            }
            
            //Si no hay imagen, no se pone nada
            if (iconobase == null) {
                System.out.println("No se pudo cargar icono para " + ficha.getNombre());
                return;
            }
            
            Image imagen = iconobase.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon iconoescalado = new ImageIcon(imagen);
            
            JLabel lblicono = new JLabel(iconoescalado);
            lblicono.setPreferredSize(new Dimension(28, 28));
            
            //Agregar al cementerio correcto segun el color de la ficha capturada
            if (ficha.getColor() == Bando.BLANCAS) {
                System.out.println(" -> va a CEMENTERIO BLANCO");
                CmtBlancas.add(lblicono);
                CmtBlancas.revalidate();
                CmtBlancas.repaint();
            } else {
                System.out.println(" -> va a CEMENTERIO NEGRO");
                CmtNegras.add(lblicono);
                CmtNegras.revalidate();
                CmtNegras.repaint();
            }
        });
        
        
        panelRuleta = new PanelRuleta();
        panelRuleta.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ImageIcon IconoLobo = new ImageIcon("src/images/ruleta_hombrelobo.PNG");
        ImageIcon IconoVampiro = new ImageIcon("src/images/ruleta_vampiro.PNG");
        ImageIcon IconoMuerte = new ImageIcon("src/images/ruleta_muerte.PNG");
        
        panelRuleta.setIcons(IconoLobo, IconoVampiro, IconoMuerte);
        
        panelRuleta.setListener(res -> {
            SwingUtilities.invokeLater(() -> {
                onResultadoRuleta(res);
            });
        });
        
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
        ModoActual = ModoAccion.NINGUNO;
        
        LblFichaSeleccionada.setText("Ficha: --");
        FichaActual = null;
        
        OrigenSeleccionado = null;
        EsperandoOrigen = true;
        EsperandoDestino = false;
        
        DestinosMovimientos.clear();
        DestinosAtaques.clear();
        DestinosInvocacion.clear();
        
        tableroVisual.limpiarDestinos();
        tableroVisual.LimpiarSeleccion();
        tableroVisual.repaint();
        
        panelRuleta.setIntentosPorPiezasPerdidas(PiezasPerdidas(TurnoActual));
        
        ActualizarTurnoUI();
        IntentarGiroRuletaConPausa();
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
            Casilla casilla = tablero.get(celda);
            
            if (casilla != null && !casilla.CasillaLibre() && casilla.getOcupante().getColor() == TurnoActual) {
                ModoActual = ModoAccion.NINGUNO;
                
                DestinosAtaques.clear();
                DestinosInvocacion.clear();
                DestinosMovimientos.clear();
                
                tableroVisual.limpiarDestinos();
                tableroVisual.LimpiarSeleccion();
                
                SeleccionarOrigen(celda);
                
                return;
            }
            
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
        
        ModoActual = ModoAccion.NINGUNO;
        EsperandoOrigen = false;
        EsperandoDestino = true;
        
        DestinosMovimientos.clear();
        DestinosAtaques.clear();
        DestinosInvocacion.clear();
        
        tableroVisual.limpiarDestinos();
        tableroVisual.LimpiarSeleccion();
        
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
            
            //La lanza bendita
            ArrayList<Posicion> lanza = muerte.PosicionesLanza(tablero);
            if (lanza != null) {
                DestinosAtaques.addAll(lanza);
            }
            
            //La maldicion de invocar zombies
            for (int fila = 0; fila < tablero.getFilas(); fila++) {
                for (int col = 0; col < tablero.getColumnas(); col++) {
                    Posicion pos = new Posicion(fila, col);
                    Casilla casillaz = tablero.get(pos);
                    
                    if (casillaz != null && casillaz.CasillaLibre()) {
                        DestinosInvocacion.add(pos);
                    }
                }
            }
        }

        
        tableroVisual.setDestinosMovimientos(new ArrayList<Posicion>());
        tableroVisual.setDestinosAtaques(new ArrayList<Posicion>());
        tableroVisual.setDestinosInvocacion(new ArrayList<Posicion>());
        
        tableroVisual.Seleccionar(OrigenSeleccionado);
        tableroVisual.repaint();
        
        //Resaltar todas las piezas que sean del mismo tipo y color que la seleccionada para que el usuario sepa que es lo que tiene que mover
        ArrayList<Posicion> mismasfichas = tablero.BuscarFichasPorTipo(ficha.getColor(), ficha.getTipo());
        if (mismasfichas != null && !mismasfichas.isEmpty()) {
            tableroVisual.setDestinosAuxiliares(mismasfichas);
        }
        
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
        
        if (ModoActual == ModoAccion.NINGUNO) {
            if (movimiento) {
                ActivarModo(ModoAccion.MOVER);
            } else if (ataque) {
                ActivarModo(ModoAccion.ATACAR);
            } else if (invocar) {
                ActivarModo(ModoAccion.INVOCAR);
            } else {
                JOptionPane.showMessageDialog(this, "Esa casilla no es valida para esta ficha", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
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
                        LogArea.append("• " + jugador + " movio " + ficha.getNombre() + " de " + posorg + " a " + posdest + "\n");
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
                            LogArea.append("• " + jugador + " ataco con " + ficha.getNombre() + " en " + posdest + " y elimino a " + nombreobj + "\n");
                        } else {
                            LogArea.append("• " + jugador + " ataco con " + ficha.getNombre() + " a " + nombreobj + " en " + posdest + "\n");
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
                        LogArea.append("• " + jugador + " lanzo ATAQUE ESPECIAL con " + ficha.getNombre() + " sobre " + posdest + "\n");
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
                        LogArea.append("• " + jugador + " uso CHUPAR SANGRE con " + ficha.getNombre() + " en " + posdest + (eliminado2 ? " y elimino a " + nombreobj2 : "") + "\n");
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
                        LogArea.append("• " + jugador + " incovo un ZOMBIE en " + posdest + "\n");
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
                
                ModoActual = ModoAccion.NINGUNO;
                EsperandoOrigen = true;
                EsperandoDestino = false;
                OrigenSeleccionado = null;
                
                DestinosAtaques.clear();
                DestinosInvocacion.clear();
                DestinosMovimientos.clear();
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
        
        IntentarGiroRuletaConPausa();
    }
    
    private void ActivarModo(ModoAccion modo) {
        ModoActual = modo;
        
        ArrayList<Posicion> vacia = new ArrayList<>();
        
        switch (modo) {
            case MOVER:
                tableroVisual.setDestinosMovimientos(new ArrayList<>(DestinosMovimientos));
                tableroVisual.setDestinosAtaques(vacia);
                tableroVisual.setDestinosInvocacion(vacia);
                break;
                
            case ATACAR:
            case ATACAR_ESPECIAL:
            case ESPECIAL_VAMPIRO:
                tableroVisual.setDestinosMovimientos(vacia);
                tableroVisual.setDestinosAtaques(new ArrayList<>(DestinosAtaques));
                tableroVisual.setDestinosInvocacion(vacia);
                break;
                
            case INVOCAR:
                tableroVisual.setDestinosAtaques(vacia);
                tableroVisual.setDestinosMovimientos(vacia);
                tableroVisual.setDestinosMovimientos(new ArrayList<>(DestinosInvocacion));
                break;
            
            case NINGUNO:
            default:
                tableroVisual.setDestinosAtaques(vacia);
                tableroVisual.setDestinosInvocacion(vacia);
                tableroVisual.setDestinosMovimientos(vacia);
                break;
        }
        
        tableroVisual.repaint();
        LblFichaSeleccionada.setText("Modo: " + ModoActual.getDescripcion().toUpperCase());
    }
    
    private void IntentarGiroRuletaConPausa() {
        if (RuletaEnProceso) {
            return;
        }
        
        if (!panelRuleta.QuedanIntentos()) {
            SiguienteTurno();
            return;
        }
        
        RuletaEnProceso = true;
        panelRuleta.GirarUnaVez();
    }
    
    private void onResultadoRuleta(TipoFicha resultado) {
        boolean tiene = tablero.JugadorTieneFichaTipo(TurnoActual, resultado);
        
        if (!tiene) {
            JOptionPane.showMessageDialog(this, "No tienes una ficha " + resultado + "\nSe ha consumido 1 intento", "Ruleta", JOptionPane.INFORMATION_MESSAGE);
            
            new Timer(1200, e -> {
                ((Timer) e.getSource()).stop();
                
                RuletaEnProceso = false;
                IntentarGiroRuletaConPausa(); //Reinentar si aun quedan intentos
            }).start();
            
            return;
        }
        
        FichaActual = resultado;
        LblFichaSeleccionada.setText("Ficha: " + resultado.name());
        
        EsperandoOrigen = true;
        EsperandoDestino = false;
        OrigenSeleccionado = null;
        
        tableroVisual.limpiarDestinos();
        tableroVisual.LimpiarSeleccion();
        tableroVisual.repaint();
        
        RuletaEnProceso = false;
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
