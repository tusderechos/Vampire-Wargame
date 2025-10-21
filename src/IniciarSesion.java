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
    private MenuPrincipal menuPrincipal;

    public IniciarSesion(CuentasMem Memoria, MenuInicial menuInicial, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.menuInicial = menuInicial;
        this.menuPrincipal = menuPrincipal;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_login.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Iniciar Sesion");
        setContentPane(PanelFondo);
        setSize(700, 700);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        LblTitulo = new JLabel("INICIAR SESION");
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LblTitulo.setForeground(Color.WHITE);
        
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setOpaque(false);
        
        LblUsuario = new JLabel("Usuario");
        LblUsuario.setForeground(Color.WHITE);
        TxtUsuario = new JTextField("");
        TxtUsuario.setMaximumSize(new Dimension(250, 45));
        
        LblContra = new JLabel("Contraseña");
        LblContra.setForeground(Color.WHITE);
        PassContra = new JPasswordField("");
        PassContra.setMaximumSize(new Dimension(250, 45));
        
        PanelInfo.add(LblUsuario);
        PanelInfo.add(TxtUsuario);
        PanelInfo.add(LblContra);
        PanelInfo.add(PassContra);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnLogin = new JButton("INICIAR SESION");
        BtnLogin.addActionListener(e -> onLogin());
        
        Component glue = Box.createGlue();
        
        BtnCancelar = new JButton("CANCELAR");
        BtnCancelar.addActionListener(e -> onSalir());
        
        PanelBotones.add(BtnLogin);
        PanelBotones.add(glue);
        PanelBotones.add(BtnCancelar);
        
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(LblTitulo, BorderLayout.NORTH);
        PanelFondo.add(PanelInfo, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    public void LimpiarCampos() {
        TxtUsuario.setText("");
        PassContra.setText("");
        TxtUsuario.requestFocus();
    }
    
    public void mostrar() {
        LimpiarCampos();
        setLocationRelativeTo(null);
        TxtUsuario.requestFocusInWindow();
        
        getRootPane().setDefaultButton(BtnLogin);
        menuInicial.setVisible(false);
        this.setVisible(true);
    }
    
    public void onLogin() {
        String usuario = TxtUsuario.getText();
        
        char[] pass = PassContra.getPassword();
        String contrasena = new String(pass);
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (int i = 0; i < usuario.length(); i++) {
            char c = usuario.charAt(i);
            if (Character.isWhitespace(c)) {
                JOptionPane.showMessageDialog(this, "El usuario no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                TxtUsuario.requestFocus();
                return;
            }
        }
        
        if (contrasena.length() != 5) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener exactamente 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            PassContra.requestFocus();
            return;
        }
        
        for (int i = 0; i < contrasena.length(); i++) {
            char c = contrasena.charAt(i);
            if (Character.isWhitespace(c)) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                PassContra.requestFocus();
                return;
            }
        }
        
        String simbolos = "!#$/()?-_.,<>|";
        boolean TieneSimbolo = false;
        
        for (int i = 0; i < simbolos.length(); i++) {
            char simbolo = simbolos.charAt(i);
            
            if (contrasena.indexOf(simbolo) >= 0) {
                TieneSimbolo = true;
                break;
            }
        }
            
        if (!TieneSimbolo) {
            JOptionPane.showMessageDialog(this, "La contraseña tiene que tener como minimo un simbolo", "Error", JOptionPane.ERROR_MESSAGE);
            PassContra.requestFocus();
            return;
        }
        
        
        if (Memoria.ValidarLogin(usuario, contrasena)) {
            JOptionPane.showMessageDialog(this, "Se ha hecho un login exitosamente", "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            menuPrincipal.setUsuarioActivo(usuario);
            this.dispose();
            menuPrincipal.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Ha habido un error en el login, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            LimpiarCampos();
            TxtUsuario.requestFocus();
        }
    }
    
    private void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir del login?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
            menuInicial.setVisible(true);
        }
    }
}
