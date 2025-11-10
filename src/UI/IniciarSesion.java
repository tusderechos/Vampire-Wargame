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

public class IniciarSesion extends JDialog {
    
    private JLabel LblTitulo;
    private JLabel LblUsuario;
    private JTextField TxtUsuario;
    private JLabel LblContra;
    private JPasswordField PassContra;
    private JButton BtnLogin;
    private JButton BtnCancelar;
    
    private final CuentasMem Memoria;
    private final MenuInicial menuInicial;

    public IniciarSesion(MenuInicial menuInicial, CuentasMem Memoria, boolean modal) {
        super(menuInicial, modal);
        this.Memoria = Memoria;
        this.menuInicial = menuInicial;
        
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
        
        LblTitulo = new JLabel("INICIAR SESION", SwingConstants.CENTER);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LblTitulo.setForeground(Color.WHITE);
        EstilizarTitulo(LblTitulo);
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 0));
        PanelInfo.setOpaque(false);
        
        LblUsuario = new JLabel("Usuario");
        LblUsuario.setForeground(Color.WHITE);
        TxtUsuario = new JTextField("");
        TxtUsuario.setMaximumSize(new Dimension(250, 45));
        
        LblContra = new JLabel("Contraseña");
        LblContra.setForeground(Color.WHITE);
        PassContra = new JPasswordField("");
        PassContra.setMaximumSize(new Dimension(250, 45));
        
        EstilizarLabel(LblUsuario);
        EstilizarLabel(LblContra);
        
        EstilizarCampoTexto(TxtUsuario);
        EstilizarCampoTexto(PassContra);
        
        PanelInfo.add(LblUsuario);
        PanelInfo.add(TxtUsuario);
        PanelInfo.add(LblContra);
        PanelInfo.add(PassContra);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        PanelBotones.setOpaque(false);
        
        BtnLogin = new JButton("INICIAR SESION");
        BtnLogin.addActionListener(e -> onLogin());
        EstilizarBoton(BtnLogin);
                
        BtnCancelar = new JButton("CANCELAR");
        BtnCancelar.addActionListener(e -> onSalir());
        EstilizarBoton(BtnCancelar);
        
        PanelBotones.add(Box.createHorizontalStrut(40));
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(BtnLogin);
        PanelBotones.add(Box.createHorizontalStrut(60));
        PanelBotones.add(BtnCancelar);
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(Box.createHorizontalStrut(40));
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(LblTitulo, BorderLayout.NORTH);
        PanelFondo.add(PanelInfo, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    private void LimpiarCampos() {
        TxtUsuario.setText("");
        PassContra.setText("");
        TxtUsuario.requestFocus();
    }
    
    public void mostrar() {
        LimpiarCampos();
        setLocationRelativeTo(menuInicial);        
        getRootPane().setDefaultButton(BtnLogin);
        setVisible(true);
    }
    
    private void onLogin() {
        String usuario = TxtUsuario.getText();
        String contrasena = new String(PassContra.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (int i = 0; i < usuario.length(); i++) {
            if (Character.isWhitespace(usuario.charAt(i))) {
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
            if (Character.isWhitespace(contrasena.charAt(i))) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                PassContra.requestFocus();
                return;
            }
        }
        
        String simbolos = "!#$/()?-_.,<>|";
        boolean TieneSimbolo = false;
        
        for (int i = 0; i < simbolos.length(); i++) {            
            if (contrasena.indexOf(simbolos.charAt(i)) >= 0) {
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
            menuInicial.onLoginExitoso(usuario);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ha habido un error en el login, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            LimpiarCampos();
        }
    }
    
    private void onSalir() {
        dispose();
    }
    
    private void EstilizarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
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
    
    private void EstilizarCampoTexto(JTextComponent campo) {
        campo.setOpaque(true);
        campo.setForeground(Color.BLACK);
        campo.setCaretColor(new Color(255, 230, 200));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        //Borde solo abajo, como tipo "underline"
        Border underline = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 180, 0));
        
        //Padding interno
        Border padding = BorderFactory.createEmptyBorder(4, 6, 4, 6);
        
        campo.setBorder(BorderFactory.createCompoundBorder(underline, padding));
    }
    
    private void EstilizarTitulo(JLabel titulo) {
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        Font base = new Font("Old English Text MT", Font.BOLD, 38);
        titulo.setFont(base);
        
        titulo.setForeground(new Color(230, 200, 120));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(0, 0, 0, 170)); //Franja oscura
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    }
    
    private void EstilizarLabel(JLabel label) {
        label.setFont(new Font("Old English Text MT", Font.BOLD, 22));
        label.setForeground(new Color(230, 230, 150));
        label.setBackground(new Color(0, 0, 0, 150));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 30, 0))));
    }
}
