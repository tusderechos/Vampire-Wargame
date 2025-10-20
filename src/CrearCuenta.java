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
    
    private JLabel LblTitulo;
    private JLabel LblUsuario;
    private JTextField TxtUsuario;
    private JLabel LblContra;
    private JPasswordField PassContrasena;
    private JLabel LblConfirmarContra;
    private JPasswordField PassConfirmarContra;
    private JButton BtnCrear;
    private JButton BtnCancelar;
    private final CuentasMem Memoria;
    private final MenuInicial menuInicial;

    public CrearCuenta(CuentasMem Memoria, MenuInicial menuInicial) {
        this.Memoria = Memoria;
        this.menuInicial = menuInicial;
        
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
        TxtUsuario = new JTextField("");
        TxtUsuario.setMaximumSize(new Dimension(250, 45));
        
        LblContra = new JLabel("Contraseña");
        LblContra.setForeground(Color.WHITE);
        PassContrasena = new JPasswordField("");
        PassContrasena.setMaximumSize(new Dimension(250, 45));
        
        LblConfirmarContra = new JLabel("Confirmar Contraseña");
        LblConfirmarContra.setForeground(Color.WHITE);
        PassConfirmarContra = new JPasswordField("");
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
        BtnCrear.addActionListener(e -> onCrear());
        
        BtnCancelar = new JButton("CANCELAR");
        BtnCancelar.addActionListener(e -> onSalir());
        
        PanelBotones.add(BtnCrear);
        PanelBotones.add(BtnCancelar);
        
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(LblTitulo, BorderLayout.NORTH);
        PanelFondo.add(PanelInfo, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    public void mostrar() {
        LimpiarCampos();
        setLocationRelativeTo(null);
        TxtUsuario.requestFocusInWindow();
        
        getRootPane().setDefaultButton(BtnCrear);
        menuInicial.setVisible(false);
        this.setVisible(true);
    }
    
    public void LimpiarCampos() {
        TxtUsuario.setText("");
        PassContrasena.setText("");
        PassConfirmarContra.setText("");
        TxtUsuario.requestFocus();
    }
    
    public void onCrear() {
        String usuario = TxtUsuario.getText();
        char[] contra = PassContrasena.getPassword();
        String contrasena = new String(contra);
        char[] confcontra = PassConfirmarContra.getPassword();
        String confirmarcontra = new String(confcontra);
        
        if (usuario.isEmpty() || contrasena.isEmpty() || confirmarcontra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese algun dato", "Error", JOptionPane.ERROR_MESSAGE);
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
        
        if (contrasena.length() != 5 || confirmarcontra.length() != 5) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener exactamente 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            PassContrasena.requestFocus();
            return;
        }
        
        for (int i = 0; i < contrasena.length(); i++) {
            char c = contrasena.charAt(i);
            if (Character.isWhitespace(c)) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                PassContrasena.requestFocus();
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
            PassContrasena.requestFocus();
            return;
        }
        
        if (!contrasena.equals(confirmarcontra)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas deben ser iguales", "Error", JOptionPane.ERROR_MESSAGE);
            PassConfirmarContra.requestFocus();
            return;
        }
        
        if (Memoria.isFull()) {
            JOptionPane.showMessageDialog(this, "Capacidad de cuentas llena", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Memoria.ExisteUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Memoria.Agregar(usuario, contrasena) == true) {
            JOptionPane.showMessageDialog(this, "Cuenta creada exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            menuInicial.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error creando la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
            LimpiarCampos();
            TxtUsuario.requestFocus();
        }
    }
    
    private void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir de la creacion de cuenta?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
            menuInicial.setVisible(true);
        }
    }
}
