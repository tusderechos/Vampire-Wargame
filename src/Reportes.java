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
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Reportes extends JFrame {
    
    private JLabel LblTitulo;
    private JTable TblRanking;
    private JTable TblLogs;
    private JScrollPane ScrollRanking;
    private JScrollPane ScrollLogs;
    
    private JButton BtnRanking;
    private JButton BtnLogs;
    private JButton BtnSalir;
    
    private static MenuPrincipal menuPrincipal;
    private static CuentasMem Memoria;
    private static String UsuarioActivo;
    
    
    public Reportes(CuentasMem Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.menuPrincipal = menuPrincipal;
        this.UsuarioActivo = UsuarioActivo;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_reportes.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Reportes");
        this.setContentPane(PanelFondo);
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        LblTitulo = new JLabel("REPORTES", SwingConstants.CENTER);
        LblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 30));
        LblTitulo.setForeground(Color.WHITE);
        
        JPanel PanelTablas = new JPanel();
        PanelTablas.setLayout(new GridLayout(2, 1, 10, 10));
        PanelTablas.setOpaque(false);
        
        String[] ColumnasRanking = {"Posicion", "Usuario", "Puntos"};
        TblRanking = new JTable(new DefaultTableModel(ColumnasRanking, 0));
        ScrollRanking = new JScrollPane(TblRanking);
        
        String[] ColumnasLogs = {"Fecha", "Rival", "Resultado"};
        TblLogs = new JTable(new DefaultTableModel(ColumnasLogs, 0));
        ScrollLogs = new JScrollPane(TblLogs);
        
        PanelTablas.add(ScrollRanking);
        PanelTablas.add(ScrollLogs);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnRanking = new JButton("RANKING");
        BtnRanking.setFont(new Font("Arial", Font.BOLD, 14));
        BtnRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnRanking.addActionListener(e -> MostrarRanking());
        
        BtnLogs = new JButton("LOGS");
        BtnLogs.setFont(new Font("Arial", Font.BOLD, 14));
        BtnLogs.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnLogs.addActionListener(e -> MostrarLogs());
        
        BtnSalir = new JButton("SALIR");
        BtnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());
        
        PanelBotones.add(BtnRanking);
        PanelBotones.add(BtnLogs);
        PanelBotones.add(BtnSalir);
        
        
        PanelFondo.setLayout(new BorderLayout());
        add(LblTitulo, BorderLayout.NORTH);
        add(PanelTablas, BorderLayout.CENTER);
        add(PanelBotones, BorderLayout.SOUTH);
        repaint();
    }
    
    private void MostrarRanking() {
        DefaultTableModel modelo = (DefaultTableModel) TblRanking.getModel();
        modelo.setRowCount(0);
        
        int total = Memoria.getRegistrados();
        
        ArrayList<Object[]> ListaRanking = new ArrayList<>(); //CAMBIAR
        
        for (int i = 0; i < total; i++) {
            if (Memoria.isActivo(i)) {
                String usuario = Memoria.getUsuario(i);
                int puntos = Memoria.getPuntos(i);
                ListaRanking.add(new Object[]{usuario, puntos});
            }
        }
        
        ListaRanking.sort((a, b) -> Integer.compare((int) b[1], (int) a[1])); //Al barro que se me olvido que ando haciendo aqui pero tiene downcasting indirecto (creo, no se si cuenta con enteros o cosas asi)
        
        int posicion = 1;
        
        for (Object[] jugador : ListaRanking) {
            modelo.addRow(new Object[]{posicion, jugador[0], jugador[1]});
            posicion++;
        }
        
        if (ListaRanking.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay jugadores activos", "Ranking vacio", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void MostrarLogs() {
        DefaultTableModel modelo = (DefaultTableModel) TblLogs.getModel();
        modelo.setRowCount(0);
        
        ArrayList<String[]> logs = Memoria.getLogsUsuario(UsuarioActivo);
        
        if (logs == null || logs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay partidas registradas para este jugador", "Registro vacio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for(String[] log : logs) {
            modelo.addRow(new Object[]{log[0], log[1], log[2]});
        }
    }
    
    private void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir al menu principal", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
    }
}

