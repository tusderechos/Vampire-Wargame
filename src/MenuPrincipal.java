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
    
    private MenuInicial menuInicial;

    public MenuPrincipal(MenuInicial menuInicial) {
        this.menuInicial = menuInicial;
        
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
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    public void mostrar() {
        setLocationRelativeTo(null);
        
        getRootPane().setDefaultButton(BtnJugar);
        setVisible(true);
    }
}
