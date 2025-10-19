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

public class MenuInicial extends JFrame {
    
    public JLabel LblTitulo;
    public JButton BtnIniciarSesion;
    public JButton BtnCrearCuenta;
    public JButton BtnSalir;
    
    public IniciarSesion iniciarSesion;
    public CrearCuenta crearCuenta;

    public MenuInicial() {
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_inicial.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
                
            }
        };
        
        setTitle("Vampire Wargame");
        this.setContentPane(PanelFondo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnIniciarSesion = new JButton("INICIAR SESION");
        BtnIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnCrearCuenta = new JButton("CREAR CUENTA");
        BtnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir = new JButton("SALIR");
        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        PanelBotones.add(BtnIniciarSesion);
        PanelBotones.add(BtnCrearCuenta);
        PanelBotones.add(BtnSalir);
        
        LblTitulo = new JLabel("VAMPIRE WARGAME");
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LblTitulo.setForeground(Color.WHITE);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.add(LblTitulo, BorderLayout.NORTH);
        PanelFondo.repaint();
    }
    
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
            new MenuInicial().setVisible(true);
        });
    }    
}
