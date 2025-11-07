package UI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Hp
 */

import ManejoDatos.CuentasMem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MenuPrincipal extends JFrame {
    
    private JLabel LblTitulo;
    private JLabel LblSubtitulo;
    
    private JButton BtnJugar;
    private JButton BtnCuenta;
    private JButton BtnReportes;
    private JButton BtnLogout;
    
    private String UsuarioActivo;
    private JLabel LblUsuario;
    
    private CuentasMem Memoria;
    

    public MenuPrincipal(CuentasMem Memoria, String UsuarioActivo) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        
        if (this.UsuarioActivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inicia sesion o crea una cuenta!", "Error", JOptionPane.WARNING_MESSAGE);
            dispose();
            
            new MenuInicial().setVisible(true);
            return;
        }
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_principal.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Menu Principal");
        this.setContentPane(PanelFondo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.setOpaque(false);
        
        LblTitulo = new JLabel("VAMPIRE WARGAME", SwingConstants.CENTER);
        LblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        LblTitulo.setForeground(Color.WHITE);
        
        LblSubtitulo = new JLabel("Minijuego de Castlevania: Lord of Shadows", SwingConstants.CENTER);
        LblSubtitulo.setFont(new Font("Fette Unz Fraktur", Font.PLAIN, 18));
        LblSubtitulo.setForeground(Color.WHITE);
        
        PanelHeader.add(LblTitulo);
        PanelHeader.add(LblSubtitulo);
        
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnJugar = new JButton("JUGAR VAMPIRE WARGAME");
        BtnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnJugar.addActionListener(e -> onJugar());

        BtnCuenta = new JButton("MI CUENTA");
        BtnCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnCuenta.addActionListener(e -> AbrirMiCuenta());

        BtnReportes = new JButton("REPORTES");
        BtnReportes.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnReportes.addActionListener(e -> AbrirReportes());

        BtnLogout = new JButton("LOG OUT");
        BtnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnLogout.addActionListener(e -> onLogout());
        
        /*
            Ing si esta viendo esto es porque he decidido no quitar/cambiar las 5 creaciones de un glue,
            pido perdon pero asi va a tener que ser
        */
        Component glue = Box.createVerticalGlue();
        Component glue2 = Box.createVerticalStrut(12);
        Component glue3 = Box.createVerticalStrut(12);
        Component glue4 = Box.createVerticalStrut(12);
        Component glue5 = Box.createVerticalGlue();

        PanelBotones.add(glue);
        PanelBotones.add(BtnJugar);
        PanelBotones.add(glue2);
        PanelBotones.add(BtnCuenta);
        PanelBotones.add(glue3);
        PanelBotones.add(BtnReportes);
        PanelBotones.add(glue4);
        PanelBotones.add(BtnLogout);
        PanelBotones.add(glue5);
        
        LblUsuario = new JLabel("Usuario: " + this.UsuarioActivo);
        LblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        LblUsuario.setForeground(Color.WHITE);
        LblUsuario.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelHeader, BorderLayout.NORTH);
        PanelFondo.add(PanelBotones, BorderLayout.CENTER);
        PanelFondo.add(LblUsuario, BorderLayout.SOUTH);
        
        getRootPane().setDefaultButton(BtnJugar);
        
        PanelFondo.repaint();
    }
    
    private void onJugar() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] activos = (Memoria != null) ? Memoria.getUsuarios() : new String[0];
        
        if (activos.length < 2) {
            JOptionPane.showMessageDialog(this, "Necesitas como minimo 2 jugadores para poder iniciar el juego!", "Insuficientes Jugadores", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ArrayList<String> listarivales = new ArrayList<>();
        
        for (String usuario : activos) {
            if (!usuario.equals(UsuarioActivo)) {
                listarivales.add(usuario);
            }
        }
        
        if (listarivales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay oponenetes conectados actualmente!", "Sin Rivales", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JLabel LblBlancas = new JLabel("BLANCAS: " + UsuarioActivo);
        
        JComboBox<String> CmbNegras = new JComboBox<>(listarivales.toArray(new String[0]));
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 6, 6));
        
        panel.add(new JLabel("Jugador BLANCAS: "));
        panel.add(LblBlancas);
        panel.add(new JLabel("Jugador (Elige oponente): "));
        panel.add(CmbNegras);
        
        int eleccion = JOptionPane.showConfirmDialog(this, panel, "Elegir Oponente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (eleccion != JOptionPane.OK_OPTION) {
            return;
        }
        
        String negras = (String) CmbNegras.getSelectedItem();
        
        if (negras == null || negras.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un oponente!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PanelJuego juego = new PanelJuego(Memoria, UsuarioActivo, negras);
        this.setVisible(false);
        
        juego.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MenuPrincipal.this.setVisible(true);
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                MenuPrincipal.this.setVisible(true);
            }
        });
        
        juego.setVisible(true);
    }
    
    private void AbrirMiCuenta() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new MiCuenta(Memoria, UsuarioActivo, this).setVisible(true);
    }
    
    private void AbrirReportes() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new Reportes(Memoria, UsuarioActivo, this).setVisible(true);
    }
    
    private void onLogout() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres regresar al menu inicial?", "Aviso", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            new MenuInicial(Memoria).setVisible(true);
        }
    }

    public String getUsuarioActivo() {
        return UsuarioActivo;
    }

    public void setUsuarioActivo(String UsuarioActivo) {
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        LblUsuario.setText("Usuario: " + this.UsuarioActivo);
        setTitle("Vampire Wargame - Menu Principal" + (this.UsuarioActivo.isEmpty() ? "" : " (" + this.UsuarioActivo + ")"));
    }
}
