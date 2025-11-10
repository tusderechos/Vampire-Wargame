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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

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
        setSize(500, 320);
        setResizable(false);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelPass = new JPanel();
        PanelPass.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        PanelPass.setLayout(new BoxLayout(PanelPass, BoxLayout.Y_AXIS));
        PanelPass.setOpaque(false);
        
        LblActual = new JLabel("Contraseña Actual: ");
        PassActual = new JPasswordField();
        
        EstilizarLabel(LblActual);
        EstilizarCampoTexto(PassActual);
        
        
        LblNueva = new JLabel("Contraseña Nueva: ");
        PassNueva = new JPasswordField();
        
        EstilizarLabel(LblNueva);
        EstilizarCampoTexto(PassNueva);
        
        
        LblConfirmar = new JLabel("Confirmar Contraseña: ");
        PassConfirmar = new JPasswordField();
        
        EstilizarLabel(LblConfirmar);
        EstilizarCampoTexto(PassConfirmar);
        
        
        PanelPass.add(Box.createVerticalGlue());
        PanelPass.add(LblActual);
        PanelPass.add(PassActual);
        PanelPass.add(Box.createVerticalStrut(5));
        PanelPass.add(LblNueva);
        PanelPass.add(PassNueva);
        PanelPass.add(Box.createVerticalStrut(5));
        PanelPass.add(LblConfirmar);
        PanelPass.add(PassConfirmar);
        PanelPass.add(Box.createVerticalGlue());
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        PanelBotones.setOpaque(false);
        
        BtnAceptar = new JButton("Aceptar Cambios");
        BtnAceptar.addActionListener(e -> onCambiar());

        BtnCancelar = new JButton("Cancelar");
        BtnCancelar.addActionListener(e -> dispose());
        
        EstilizarBoton(BtnAceptar);
        EstilizarBoton(BtnCancelar);
        
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(BtnAceptar);
//        PanelBotones.add(Box.createHorizontalStrut(5 ));
        PanelBotones.add(BtnCancelar);
        PanelBotones.add(Box.createHorizontalGlue());
        
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
        
        int indice = Memoria.getIndiceUsuario(UsuarioActivo);
        
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
    
    private void EstilizarCampoTexto(JTextComponent campo) {
        campo.setOpaque(true);
        campo.setForeground(Color.BLACK);
        campo.setCaretColor(new Color(255, 230, 200));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        
        //Borde solo abajo, como tipo "underline"
        Border underline = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 180, 0));
        
        //Padding interno
        Border padding = BorderFactory.createEmptyBorder(4, 6, 4, 6);
        
        campo.setBorder(BorderFactory.createCompoundBorder(underline, padding));
    }
    
    private void EstilizarBoton(JButton boton) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        boton.setBackground(new Color(25, 25, 25)); //Gris oscuro tipo metal
        boton.setForeground(new Color(220, 180, 120)); //Dorado suave
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 2), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(220, 44));
        
        //Mi querido, hermoso y celestial efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(60, 0, 0));
                boton.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(25, 25, 25));
                boton.setForeground(new Color(220, 180, 80));
            }
        });
    }
    
    private void EstilizarLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Old English Text MT", Font.BOLD, 22));
        label.setForeground(new Color(230, 230, 150));
        label.setBackground(new Color(0, 0, 0, 150));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 30, 0))));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
