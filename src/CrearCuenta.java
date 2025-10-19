/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import javax.swing.*;
import java.awt.*;

public class CrearCuenta extends JFrame {
    
    public JLabel LblTitulo;
    public JLabel LblUsuario;
    public JTextField TxtUsuario;
    public JLabel LblContra;
    public JPasswordField PassContrasena;
    public JLabel LblConfirmarContra;
    public JPasswordField PassConfirmarContra;
    public JButton BtnCrear;
    public JButton BtnCancelar;
    public CuentasMem Memoria;
    public MenuInicial menuInicial;

    public CrearCuenta() {
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_crearcuenta.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Crear Cuenta");
        setContentPane(PanelFondo);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        LblTitulo = new JLabel("CREAR CUENTA");
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LblTitulo.setForeground(Color.WHITE);
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setOpaque(false);
        
        LblUsuario = new JLabel("Usuario");
        LblUsuario.setForeground(Color.WHITE);
        TxtUsuario = new JTextField("fbjasbf");
        TxtUsuario.setMaximumSize(new Dimension(250, 45));
        
        LblContra = new JLabel("Contraseña");
        LblContra.setForeground(Color.WHITE);
        PassContrasena = new JPasswordField("contra");
        PassContrasena.setMaximumSize(new Dimension(250, 45));
        
        LblConfirmarContra = new JLabel("Confirmar Contraseña");
        LblConfirmarContra.setForeground(Color.WHITE);
        PassConfirmarContra = new JPasswordField("confirmar contra");
        PassConfirmarContra.setMaximumSize(new Dimension(250, 45));
        
        PanelInfo.add(LblUsuario);
        PanelInfo.add(TxtUsuario);
        PanelInfo.add(LblContra);
        PanelInfo.add(PassContrasena);
        PanelInfo.add(LblConfirmarContra);
        PanelInfo.add(PassConfirmarContra);
        
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnCrear = new JButton("CREAR CUENTA");
        BtnCancelar = new JButton("CANCELAR");
        
        PanelBotones.add(BtnCrear);
        PanelBotones.add(BtnCancelar);
        
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(LblTitulo, BorderLayout.NORTH);
        PanelFondo.add(PanelInfo, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CrearCuenta().setVisible(true);
        });
    }
}
