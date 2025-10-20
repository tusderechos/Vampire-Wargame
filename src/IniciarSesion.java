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

public class IniciarSesion extends JFrame {
    
    private JLabel LblTitulo;
    private JLabel LblUsuario;
    private JTextField TxtUsuario;
    private JLabel LblContra;
    private JPasswordField PassContra;
    private JButton BtnLogin;
    private JButton BtnCancelar;
    private CuentasMem Memoria;
    private MenuInicial menuInicial;

    public IniciarSesion(CuentasMem Memoria, MenuInicial menuInicial) {
        this.Memoria = Memoria;
        this.menuInicial = menuInicial;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_login.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                
            }
        };
    }
    
    
}
