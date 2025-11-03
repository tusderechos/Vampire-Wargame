package UI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import ManejoDatos.CuentasMem;
import javax.swing.*;
import java.awt.*;

public class CambiarPass extends JDialog {
    
    private JLabel LblActual;
    private JLabel LblNueva;
    private JLabel LblConfirmar;
    
    private JPasswordField PassActual;
    private JPasswordField PassNueva;
    private JPasswordField PassConfirmar;
    
    private JButton BtnAceptar;
    private JButton BtnCancelar;
    
    private CuentasMem Memoria;
    private String UsuarioActivo;
    
    public CambiarPass(JFrame frame, CuentasMem Memoria, String UsuarioActivo) {
        super(frame, true);
        this.Memoria = Memoria;
        this.UsuarioActivo = UsuarioActivo;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_cambiarpass.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
    
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Cambiar Contraseña");
        setSize(420, 320);
        setResizable(false);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelPass = new JPanel();
        PanelPass.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        PanelPass.setLayout(new BoxLayout(PanelPass, BoxLayout.Y_AXIS));
        PanelPass.setOpaque(false);
        
        LblActual = new JLabel("Contraseña Actual: ");
        LblActual.setFont(new Font("Arial", Font.BOLD, 16));
        LblActual.setForeground(Color.WHITE);
        PassActual = new JPasswordField();
        PassActual.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        
        Component glue = Box.createVerticalStrut(10);
        
        LblNueva = new JLabel("Contraseña Nueva: ");
        LblNueva.setFont(new Font("Arial", Font.BOLD, 16));
        LblNueva.setForeground(Color.WHITE);
        PassNueva = new JPasswordField();
        PassNueva.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        
        Component glue2 = Box.createVerticalStrut(10);
        
        LblConfirmar = new JLabel("Confirmar Contraseña: ");
        LblConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        LblConfirmar.setForeground(Color.WHITE);
        PassConfirmar = new JPasswordField();
        PassConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        
        Component glue3 = Box.createVerticalStrut(14);
        
        PanelPass.add(LblActual);
        PanelPass.add(PassActual);
        PanelPass.add(glue);
        PanelPass.add(LblNueva);
        PanelPass.add(PassNueva);
        PanelPass.add(glue2);
        PanelPass.add(LblConfirmar);
        PanelPass.add(PassConfirmar);
        PanelPass.add(glue3);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        PanelBotones.setOpaque(false);
        
        BtnAceptar = new JButton("Aceptar Cambios");
        BtnAceptar.addActionListener(e -> onCambiar());

        BtnCancelar = new JButton("Cancelar");
        BtnCancelar.addActionListener(e -> dispose());
        
        PanelBotones.add(BtnAceptar);
        PanelBotones.add(BtnCancelar);
        
        PanelPass.add(PanelBotones);
        
        setContentPane(PanelFondo);
        getRootPane().setDefaultButton(BtnAceptar);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelPass, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    private void onCambiar() {
        String actual = new String(PassActual.getPassword());
        String nueva = new String(PassNueva.getPassword());
        String confirmar = new String(PassConfirmar.getPassword());
        
        if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No deje campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (nueva.length() != 5) {
            JOptionPane.showMessageDialog(this, "La nueva contraseña debe tener exactamente 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            PassNueva.requestFocusInWindow();
            return;
        }
        for (int i = 0; i < nueva.length(); i++) {
            if (Character.isWhitespace(nueva.charAt(i))) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                PassNueva.requestFocusInWindow();
                return;
            }
        }
        
        String simbolos = "!#$/()?-_.,<>|";
        boolean TieneSimbolo = false;
        
        for (int i = 0; i < simbolos.length(); i++) {            
            if (nueva.indexOf(simbolos.charAt(i)) >= 0) {
                TieneSimbolo = true;
                break;
            }
        }
            
        if (!TieneSimbolo) {
            JOptionPane.showMessageDialog(this, "La contraseña tiene que tener como minimo un simbolo", "Error", JOptionPane.ERROR_MESSAGE);
            PassNueva.requestFocusInWindow();
            return;
        }
        
        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas deben ser iguales", "Error", JOptionPane.ERROR_MESSAGE);
            PassConfirmar.requestFocus();
            return;
        }
        
        int indice = Memoria.indexOf(UsuarioActivo);
        
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Cuenta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Memoria.ValidarContrasenaActual(indice, actual)) {
            JOptionPane.showMessageDialog(this, "La contraseña actual es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            PassActual.requestFocusInWindow();
            return;
        }
        
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres cambiar la contraseña?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        
        boolean confirm = Memoria.ActualizarContrasena(indice, nueva);
        if (confirm) {
            JOptionPane.showMessageDialog(this, "Contraseña actualizada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error al actualziar la contraseña.\nIntentelo una vez mas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
