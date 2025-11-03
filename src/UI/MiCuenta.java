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
import java.text.SimpleDateFormat;

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
    
    private String UsuarioActivo;
    private MenuPrincipal menuPrincipal;
    private CuentasMem Memoria;
    
    public MiCuenta (CuentasMem Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        this.menuPrincipal = menuPrincipal;
        
        if (this.UsuarioActivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        
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
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        
        LblTitulo = new JLabel("MI CUENTA", SwingConstants.CENTER);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        LblTitulo.setForeground(Color.WHITE);
        
        LblSubtitulo = new JLabel("Encuentra informacion sobre tu cuenta", SwingConstants.CENTER);
        LblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        LblSubtitulo.setForeground(Color.WHITE);
        
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.setOpaque(false);
        
        PanelHeader.add(LblTitulo);
        PanelHeader.add(LblSubtitulo);
        
        
        BtnCambiarPass = new JButton("Cambiar Contraseña");
        BtnCambiarPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnCambiarPass.addActionListener(e -> AbrirCambiarPass());

        BtnCerrarCuenta = new JButton("Cerrar Cuenta");
        BtnCerrarCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnCerrarCuenta.addActionListener(e -> onCerrarCuenta());

        BtnSalir = new JButton("Salir");
        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        
        PanelBotones.add(BtnCambiarPass);
        PanelBotones.add(BtnCerrarCuenta);
        PanelBotones.add(BtnSalir);
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setOpaque(false);
        
        int indice = Memoria.indexOf(UsuarioActivo);
        
        LblUsuario = new JLabel("Nombre de Usuario:" + UsuarioActivo);
        LblUsuario.setForeground(Color.WHITE);
        LblUsuario.setFont(new Font("Arial", Font.BOLD, 24));
        
        LblPuntos = new JLabel("Puntaje: " + Memoria.getPuntos(indice));
        LblPuntos.setForeground(Color.WHITE);
        LblPuntos.setFont(new Font("Arial", Font.BOLD, 24));
        
        LblFechaIngreso = new JLabel("Fecha de ingreso: " + Memoria.getFechaIngresoFormat(indice, ""));
        LblFechaIngreso.setForeground(Color.WHITE);
        LblFechaIngreso.setFont(new Font("Arial", Font.BOLD, 24));
        
        LblActivo = new JLabel("Estado: " + Memoria.isActivo(indice));
        LblActivo.setForeground(Color.WHITE);
        LblActivo.setFont(new Font("Arial", Font.BOLD, 24));
        
        Component glue2 = Box.createGlue(); 
        Component glue3 = Box.createGlue(); 
        Component glue4 = Box.createGlue(); 
        Component glue5 = Box.createGlue(); 
        Component glue6 = Box.createGlue(); 
        
        PanelInfo.add(glue2);
        PanelInfo.add(LblUsuario);
        PanelInfo.add(glue3);
        PanelInfo.add(LblPuntos);
        PanelInfo.add(glue4);
        PanelInfo.add(LblFechaIngreso);
        PanelInfo.add(glue5);
        PanelInfo.add(LblActivo);
        PanelInfo.add(glue6);
        
        CargarDatosUsuario();
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelHeader, BorderLayout.NORTH);
        PanelFondo.add(PanelInfo, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    public void mostrar() {
        setLocationRelativeTo(null);
        
        getRootPane().setDefaultButton(BtnCambiarPass);
        menuPrincipal.setVisible(false);
        this.setVisible(true);
    }
    
    private void AbrirCambiarPass() {
        new CambiarPass(this, Memoria, UsuarioActivo).setVisible(true);
    }
    
    private void onCerrarCuenta() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres eliminar tu cuenta?\nEsta accion no se puede deshacer", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        
        boolean confirm = Memoria.Eliminar(UsuarioActivo);
        if (!confirm) {
            JOptionPane.showMessageDialog(this, "Hubo un error al momento de eliminar la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Cuenta Eliminada", "Error", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
        if (menuPrincipal != null) {
            menuPrincipal.dispose();
        }
        
        new MenuInicial().setVisible(true);
    }
    
    private void onSalir() {
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
            return;
        }
        
        int indice = getIndiceUsuarioActual();
        
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "El indice del usuario es -1", "Error", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            menuPrincipal.setVisible(true);
            return;
        }
        
        LblUsuario.setText("Usuario: " + UsuarioActivo);
        LblPuntos.setText("Puntos: " + Memoria.getPuntos(indice));
        LblFechaIngreso.setText("Fecha de Ingreso: " + Memoria.getFechaIngresoFormat(indice, "dd/MM/yyyy HH:mm"));
        LblActivo.setText(Memoria.isActivo(indice) ? "Estado: ACTIVO" : "Estado: INACTIVO");
        
    }
    
    public String getUsuarioActual() {
        return menuPrincipal.getUsuarioActivo();
    }
    
    public int getIndiceUsuarioActual() {
        return Memoria.indexOf(getUsuarioActual());
    }
}