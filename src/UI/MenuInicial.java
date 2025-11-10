package UI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
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

public class MenuInicial extends JFrame {
    
    public JLabel LblTitulo;
    public JButton BtnIniciarSesion;
    public JButton BtnCrearCuenta;
    public JButton BtnSalir;
    
    public final CuentasMem Memoria;
    
    public MenuInicial() {
        this(new CuentasMem(40));
    }

    public MenuInicial(CuentasMem Memoria) {
        this.Memoria = Memoria;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_inicial.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
                
                //Para crear un degradado oscuro transparente arriba del fondo
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 0), 0, getHeight(), new Color(0, 0, 0, 200));
                
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        setTitle("Vampire Wargame - Menu Inicial");
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
        BtnIniciarSesion.addActionListener(e -> AbrirIniciarSesion());
        BtnIniciarSesion.setBounds(330, 460, 150, 30);
        EstilizarBoton(BtnIniciarSesion);
        
        BtnCrearCuenta = new JButton("CREAR CUENTA");
        BtnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnCrearCuenta.addActionListener(e -> AbrirCrearCuenta());
        BtnCrearCuenta.setBounds(330, 500, 150, 30);
        EstilizarBoton(BtnCrearCuenta);
        
        BtnSalir = new JButton("SALIR");
        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());
        BtnIniciarSesion.setBounds(330, 540, 150, 30);
        EstilizarBoton(BtnSalir);
        
        PanelBotones.add(Box.createVerticalStrut(10));
        PanelBotones.add(BtnIniciarSesion);
        PanelBotones.add(Box.createVerticalStrut(6));
        PanelBotones.add(BtnCrearCuenta);
        PanelBotones.add(Box.createVerticalStrut(6));
        PanelBotones.add(BtnSalir);
        PanelBotones.add(Box.createVerticalStrut(10));
        
        LblTitulo = new JLabel("VAMPIRE WARGAME");
        EstilizarLabel(LblTitulo);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(LblTitulo, BorderLayout.NORTH);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    private void AbrirIniciarSesion() {
        IniciarSesion iniciarSesion = new IniciarSesion(this, Memoria, true);
        iniciarSesion.setVisible(true);
    }
    
    private void AbrirCrearCuenta() {
        CrearCuenta crearCuenta = new CrearCuenta(this, Memoria, true);
        crearCuenta.setVisible(true);
    }
    
    public void onLoginExitoso(String usuario) {
        if (usuario == null || usuario.isBlank()) {
            JOptionPane.showMessageDialog(this, "Usuario Invalido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        dispose();
        new MenuPrincipal(Memoria, usuario).setVisible(true);
    }
    
    private void onSalir() {
        int Opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (Opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void EstilizarBoton(JButton boton) {
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
        label.setFont(new Font("Old English Text MT", Font.BOLD, 38));
        label.setForeground(new Color(255, 215, 130));
        label.setBackground(new Color(0, 0, 0, 190));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0), BorderFactory.createLineBorder(new Color(0, 0, 0, 120), 2)));
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
            new MenuInicial().setVisible(true);
        });
    }    
}
