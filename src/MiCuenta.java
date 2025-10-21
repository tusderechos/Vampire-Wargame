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

public class MiCuenta extends JFrame {
    
    private JLabel LblTitulo;
    private JLabel LblSubtitulo;
    private JButton BtnCambiarPass;
    private JButton BtnCerrarCuenta;
    private JButton BtnSalir;
    
    private MenuPrincipal menuPrincipal;
    
    public MiCuenta (MenuPrincipal menuPrincipal) {
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_micuenta.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
    
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Mi Cuenta");
        this.setContentPane(PanelFondo);
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        
        LblTitulo = new JLabel("Mi Cuenta");
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LblTitulo.setForeground(Color.WHITE);
        
        LblSubtitulo = new JLabel("Encuentra informacion sobre tu cuenta");
        LblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        LblSubtitulo.setForeground(Color.WHITE);
        
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.add(LblTitulo);
        PanelHeader.add(LblSubtitulo);
        
        
        BtnCambiarPass = new JButton("Cambiar Contraseña");
//        BtnCambiarPass.addActionListener(e -> onCambiarPass());

        BtnCerrarCuenta = new JButton("Cerrar Cuenta");
//        BtnCerrarCuenta.addActionListener(e -> onCerrarCuenta());

        BtnSalir = new JButton("Salir");
        BtnSalir.addActionListener(e -> onSalir());
    }
    
    public void mostrar() {
        setLocationRelativeTo(null);
        
        getRootPane().setDefaultButton(BtnCambiarPass);
        menuPrincipal.setVisible(false);
        this.setVisible(true);
    }
    
    public void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir al menu principal", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
    }
}

