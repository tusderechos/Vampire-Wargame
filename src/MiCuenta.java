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
    
    public JLabel LblTitulo;
    public JLabel LblSubtitulo;
    public JButton BtnCambiarPass;
    public JButton BtnCerrarCuenta;
    public JButton BtnSalir;
    
    public JLabel LblUsuario;
    public JLabel LblPuntos;
    public JLabel LblFechaIngreso;
    public JLabel LblActivo;
    
    public JPasswordField PassActual;
    public JPasswordField PassNueva;
    public JPasswordField PassConfirmar;
    public String UsuarioActivo;
    
    private MenuPrincipal menuPrincipal;
    private CuentasMem Memoria;
    
    public MiCuenta (MenuPrincipal menuPrincipal, CuentasMem Memoria) {
        this.menuPrincipal = menuPrincipal;
        
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
        PanelHeader.setOpaque(false);
        
        PanelHeader.add(LblTitulo);
        PanelHeader.add(LblSubtitulo);
        
        
        BtnCambiarPass = new JButton("Cambiar Contraseña");
//        BtnCambiarPass.addActionListener(e -> onCambiarPass());

        BtnCerrarCuenta = new JButton("Cerrar Cuenta");
//        BtnCerrarCuenta.addActionListener(e -> onCerrarCuenta());

        BtnSalir = new JButton("Salir");
        BtnSalir.addActionListener(e -> onSalir());
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS - 1));
        PanelBotones.setOpaque(false);
        
        PanelBotones.add(BtnCambiarPass);
        PanelBotones.add(BtnCerrarCuenta);
        PanelBotones.add(BtnSalir);
    }
    
    public void mostrar() {
        setLocationRelativeTo(null);
        
        getRootPane().setDefaultButton(BtnCambiarPass);
        CargarDatosUsuario();
        menuPrincipal.setVisible(false);
        this.setVisible(true);
    }
    
    public void LimpiarDatos() {
        PassActual.setText("");
        PassNueva.setText("");
        PassConfirmar.setText("");
        
        PassActual.requestFocus();
    }
    
    public void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir al menu principal", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
    }
    
    public void CargarDatosUsuario() {
        UsuarioActivo = menuPrincipal.getUsuarioActivo();
        
        if (UsuarioActivo == null) {
            JOptionPane.showMessageDialog(this, "El usuario esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
        
        int Indice = getIndiceUsuarioActual();
        
        if (Indice == -1) {
            JOptionPane.showMessageDialog(this, "El indice del usuario es -1", "Error", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
        
        
    }
    
    public String getUsuarioActual() {
        return menuPrincipal.getUsuarioActivo();
    }
    
    public int getIndiceUsuarioActual() {
        return Memoria.indexOf(getUsuarioActual());
    }
    
    public void onCambiarPass() {
        char[] charpass = PassActual.getPassword();
        char[] charnueva = PassNueva.getPassword();
        char[] charconfirmar = PassConfirmar.getPassword();
        
        String Passacc = new String(charpass);
        String Passnew = new String(charnueva);
        String Passconf = new String(charconfirmar);
        
        if (Passacc.isEmpty() || Passnew.isEmpty() || Passconf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede dejar espacios vacios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (int i = 0; i < Passacc.length(); i++) {
            char c = Passacc.charAt(i);
            if (Character.isWhitespace(c)) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede tener ningun tipo de espacios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        for (int i = 0; i < Passnew.length(); i++) {
            char c = Passnew.charAt(i);
            if (Character.isWhitespace(c)) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede tener ningun tipo de espacios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        for (int i = 0; i < Passconf.length(); i++) {
            char c = Passconf.charAt(i);
            if (Character.isWhitespace(c)) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede tener ningun tipo de espacios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}

