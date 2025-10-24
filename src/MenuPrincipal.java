/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Hp
 */

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    
    private JLabel LblTitulo;
    private JLabel LblSubtitulo;
    private JButton BtnJugar;
    private JButton BtnCuenta;
    private JButton BtnReportes;
    private JButton BtnLogout;
    
    private String UsuarioActivo;
    private JLabel LblUsuario;
    
    private static MenuInicial menuInicial;
    private static MiCuenta miCuenta;
    private static CuentasMem Memoria;
    

    public MenuPrincipal(MenuInicial menuInicial) {
        this.menuInicial = menuInicial;
        miCuenta = new MiCuenta(this, Memoria);
        
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
        getRootPane().setDefaultButton(BtnJugar);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.setOpaque(false);
        
        LblTitulo = new JLabel("VAMPIRE WARGAME");
        LblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        LblTitulo.setForeground(Color.WHITE);
        
        LblSubtitulo = new JLabel("Minijuego de Castlevania: Lord of Shadows");
        LblSubtitulo.setFont(new Font("Fette Unz Fraktur", Font.PLAIN, 18));
        LblSubtitulo.setForeground(Color.WHITE);
        
        PanelHeader.add(LblTitulo);
        PanelHeader.add(LblSubtitulo);
        
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnJugar = new JButton("JUGAR VAMPIRE WARGAME");
        BtnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
//        BtnJugar.addActionListener(e -> onJugar());

        BtnCuenta = new JButton("MI CUENTA");
        BtnCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnCuenta.addActionListener(e -> miCuenta.mostrar());

        BtnReportes = new JButton("REPORTES");
        BtnReportes.setAlignmentX(Component.CENTER_ALIGNMENT);
//        BtnReportes.addActionListener(e -> onReportes());

        BtnLogout = new JButton("LOG OUT");
        BtnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnLogout.addActionListener(e -> onLogout());
        
        /*
            Ing si esta viendo esto es porque he decidido no quitar/cambiar las 5 creaciones de un glue,
            pido perdon pero asi va a tener que ser
        */
        Component glue = Box.createGlue();
        Component glue2 = Box.createGlue();
        Component glue3 = Box.createGlue();
        Component glue4 = Box.createGlue();
        Component glue5 = Box.createGlue();

        PanelBotones.add(glue);
        PanelBotones.add(BtnJugar);
        PanelBotones.add(glue2);
        PanelBotones.add(BtnCuenta);
        PanelBotones.add(glue3);
        PanelBotones.add(BtnReportes);
        PanelBotones.add(glue4);
        PanelBotones.add(BtnLogout);
        PanelBotones.add(glue5);
        
        LblUsuario = new JLabel("");
        LblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        LblUsuario.setForeground(Color.WHITE);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelHeader, BorderLayout.NORTH);
        PanelFondo.add(PanelBotones, BorderLayout.CENTER);
        PanelFondo.add(LblUsuario, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    public void onLogout() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres regresar al menu inicial?", "Aviso", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            UsuarioActivo = null;
            this.dispose();
            menuInicial.setVisible(true);
        }
    }

    public String getUsuarioActivo() {
        return UsuarioActivo;
    }

    public void setUsuarioActivo(String UsuarioActivo) {
        this.UsuarioActivo = UsuarioActivo;
        LblUsuario.setText(UsuarioActivo);
    }
}
