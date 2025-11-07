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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Reportes extends JFrame {
    
    private JLabel LblTitulo;
    private JTable TblRanking;
    private JTable TblLogs;
    private JScrollPane ScrollRanking;
    private JScrollPane ScrollLogs;
    
    private JButton BtnRanking;
    private JButton BtnLogs;
    private JButton BtnRefrescar;
    private JButton BtnSalir;
    
    private JCheckBox CBIncluirInactivos;
    
    private MenuPrincipal menuPrincipal;
    private CuentasMem Memoria;
    private String UsuarioActivo;
    
    
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
        
        DefaultTableModel ModeloRanking = new DefaultTableModel(new String[]{"Posicion", "Usuario", "Puntos"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int indicecolumna) {
                if (indicecolumna == 0 || indicecolumna == 2) {
                    return Integer.class;
                }
                
                return String.class;
            }
        };
        
        DefaultTableModel ModeloLogs = new DefaultTableModel(new String[] {"Fecha", "Rival", "Resultado"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
        
        Color fondosemi = new Color(0, 0, 0, 90);
        Color grid = new Color(80, 80, 80);
        Color headerbg = new Color(0, 0, 0, 150);
        
        TblRanking = new JTable(ModeloRanking);
        TblLogs = new JTable(ModeloLogs);
        
        for (JTable tabla : new JTable[]{TblRanking, TblLogs}) {
            tabla.setOpaque(false);
            ((DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class)).setOpaque(false);
            tabla.setRowHeight(26);
            tabla.setShowHorizontalLines(true);
            tabla.setShowVerticalLines(false);
            tabla.setGridColor(grid);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tabla.setFillsViewportHeight(true);
            tabla.setSelectionForeground(Color.WHITE);
            tabla.setSelectionBackground(headerbg);
        }
        
        JTableHeader hdrR = TblRanking.getTableHeader();
        hdrR.setReorderingAllowed(false);
        hdrR.setFont(new Font("Arial", Font.BOLD, 13));
        hdrR.setForeground(Color.WHITE);
        hdrR.setBackground(headerbg);
        ((DefaultTableCellRenderer)hdrR.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
        JTableHeader hdrL = TblLogs.getTableHeader();
        hdrL.setReorderingAllowed(false);
        hdrL.setFont(new Font("Arial", Font.BOLD, 13));
        hdrL.setForeground(Color.WHITE);
        hdrL.setBackground(headerbg);
        ((DefaultTableCellRenderer)hdrL.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
        
        //Alineacion de columnas
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        
        TableColumnModel tcmR = TblRanking.getColumnModel();
        tcmR.getColumn(0).setCellRenderer(centro);
        tcmR.getColumn(2).setCellRenderer(centro);
        
        DefaultTableCellRenderer RendererBlanco = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.WHITE);
                
                if (isSelected) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                
                return c;
            }
        };
        for (int i = 0; i < tcmR.getColumnCount(); i++) {
            tcmR.getColumn(i).setCellRenderer(RendererBlanco);
        }
        
        TblRanking.setSelectionBackground(new Color(0, 0, 0, 0));
        TblRanking.setSelectionForeground(Color.WHITE);
        TblLogs.setSelectionBackground(new Color(0, 0, 0, 0));
        TblLogs.setSelectionForeground(Color.WHITE);
        
        DefaultTableCellRenderer RendererBase = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
                setOpaque(false);
                c.setForeground(Color.WHITE);
                c.setFont(c.getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN));
                
                return c;
            }
        };
        
        DefaultTableCellRenderer RendererUsuario = new DefaultTableCellRenderer() {
            private final Color Dorado = new Color(230, 220, 150);
            
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
                setOpaque(false);
                c.setForeground(Dorado);
                c.setFont(c.getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN));
                
                return c;
            }
        };
        //Asignar el renderer a la columna de usuario
        for (int i = 0; i < TblRanking.getColumnCount(); i++) {
            TblRanking.getColumnModel().getColumn(i).setCellRenderer(RendererBase);
        }
        TblRanking.getColumnModel().getColumn(1).setCellRenderer(RendererUsuario);
        
        for (int i = 0; i < TblLogs.getColumnCount(); i++) {
            TblLogs.getColumnModel().getColumn(i).setCellRenderer(RendererBase);
        }
        
        TableColumnModel tcmL = TblLogs.getColumnModel();
        DefaultTableCellRenderer RendererLogs = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
                c.setForeground(new Color(230, 220, 150));
                
                if (isSelected) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                
                return c;
            }
        };
        for (int i = 0; i < tcmL.getColumnCount(); i++) {
            tcmL.getColumn(i).setCellRenderer(RendererLogs);
        }
        
        //Anchos
        TblRanking.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tcmR.getColumn(0).setPreferredWidth(90);
        tcmR.getColumn(1).setPreferredWidth(360);
        tcmR.getColumn(2).setPreferredWidth(120);
        
        TblLogs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tcmL.getColumn(0).setPreferredWidth(180);
        tcmL.getColumn(1).setPreferredWidth(320);
        tcmL.getColumn(2).setPreferredWidth(180);
        
        
        ScrollRanking = new JScrollPane(TblRanking);
        ScrollRanking.setOpaque(false);
        ScrollRanking.getViewport().setOpaque(false);
        ScrollRanking.getViewport().setBackground(fondosemi);
        AjustarAnchosProporcional(TblRanking, 15, 55, 30);
        HookResizeProporcional(ScrollRanking, () -> AjustarAnchosProporcional(TblRanking, 15, 55, 30));
        
        ScrollLogs = new JScrollPane(TblLogs);
        ScrollLogs.setOpaque(false);
        ScrollLogs.getViewport().setOpaque(false);
        ScrollLogs.getViewport().setBackground(fondosemi);
        AjustarAnchosProporcional(TblLogs, 35, 45, 20);
        HookResizeProporcional(ScrollLogs, () -> AjustarAnchosProporcional(TblLogs, 35, 45, 20));
        
        PanelTablas.add(ScrollRanking);
        PanelTablas.add(ScrollLogs);
        
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnRanking = new JButton("RANKING");
        BtnRanking.setFont(new Font("Arial", Font.BOLD, 14));
        BtnRanking.addActionListener(e -> MostrarRanking());
        
        CBIncluirInactivos = new JCheckBox("Incluir Inactivos");
        CBIncluirInactivos.setOpaque(false);
        CBIncluirInactivos.setForeground(Color.WHITE);
        CBIncluirInactivos.setFont(new Font("Arial", Font.PLAIN, 13));
        
        BtnLogs = new JButton("LOGS");
        BtnLogs.setFont(new Font("Arial", Font.BOLD, 14));
        BtnLogs.addActionListener(e -> MostrarLogs());
        
        BtnRefrescar = new JButton("REFRESCAR");
        BtnRefrescar.setFont(new Font("Arial", Font.BOLD, 14));
        BtnRefrescar.addActionListener(e -> MostrarRanking());
        
        BtnSalir = new JButton("SALIR");
        BtnSalir.setFont(new Font("Arial", Font.BOLD, 14));
//        BtnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());
        
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(BtnRanking);
        PanelBotones.add(Box.createHorizontalStrut(10));
        PanelBotones.add(CBIncluirInactivos);
        PanelBotones.add(Box.createHorizontalStrut(20));
        PanelBotones.add(BtnLogs);
        PanelBotones.add(Box.createHorizontalStrut(20));
        PanelBotones.add(BtnRefrescar);
        PanelBotones.add(Box.createHorizontalStrut(20));
        PanelBotones.add(BtnSalir);
        PanelBotones.add(Box.createHorizontalGlue());
        
        
        PanelFondo.setLayout(new BorderLayout(10, 10));
        add(LblTitulo, BorderLayout.NORTH);
        add(PanelTablas, BorderLayout.CENTER);
        add(PanelBotones, BorderLayout.SOUTH);
        
        MostrarRanking();
        
        repaint();
    }
    
    private void MostrarRanking() {
        DefaultTableModel modelo = (DefaultTableModel) TblRanking.getModel();
        modelo.setRowCount(0);
        
        int total = Memoria.getRegistrados();
        boolean incluirinactivos = CBIncluirInactivos.isSelected();
        
        ArrayList<Object[]> ListaRanking = new ArrayList<>();
        
        for (int i = 0; i < total; i++) {
            if (incluirinactivos || Memoria.isActivo(i)) {
                String usuario = Memoria.getUsuario(i);
                
                if (usuario != null) {
                    int puntos = Memoria.getPuntos(i);
                    ListaRanking.add(new Object[]{usuario, puntos});
                }
            }
        }
        
        for (int fila = 0; fila < TblRanking.getRowCount(); fila++) {
            String usuario = TblRanking.getValueAt(fila, 1).toString();
            
            if (usuario.equals(UsuarioActivo)) {
                TblRanking.setRowSelectionInterval(fila, fila);
                break;
            }
        }
        
        ListaRanking.sort((a, b) -> {
            int pb = (Integer) b[1];
            int ba = (Integer) a[1];
            
            return Integer.compare(pb, ba);
        }); //Al barro que se me olvido que ando haciendo aqui pero tiene downcasting indirecto (creo, no se si cuenta con enteros o cosas asi)
        
        int posicion = 1;
        
        for (Object[] jugador : ListaRanking) {
            String usuario = (String) jugador[0];
            int puntos = (Integer) jugador[1];
            modelo.addRow(new Object[]{posicion, usuario, puntos});
            posicion++;
        }
                
        if (ListaRanking.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay jugadores activos", "Ranking vacio", JOptionPane.INFORMATION_MESSAGE);
        }
        
        AjustarAnchosProporcional(TblRanking, 15, 55, 30);
        SeleccionarFilaUsuarioActivo();
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
            if (log != null && log.length >= 3) {
                String fecha = log[0];
                modelo.addRow(new Object[]{log[0], log[1], log[2]});
            }
        }
        
        AjustarAnchosProporcional(TblLogs, 35, 45, 20);
    }
    
    private void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir al menu principal?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
    }
    
    private void AjustarAnchosProporcional(JTable tabla, int... porcentajes) {
        if (tabla.getColumnCount() != porcentajes.length) {
            return;
        }
        
        int total = 0;
        
        for (int p : porcentajes) {
            total += p;
        }
        
        if (total == 0) {
            return;
        }
        
        int anchotabla = tabla.getParent() != null ? tabla.getParent().getWidth() : tabla.getWidth();
        
        if (anchotabla <= 0) {
            anchotabla = 600;
        }
        
        TableColumnModel tcm = tabla.getColumnModel();
        
        for (int i = 0; i < porcentajes.length; i++) {
            int w = (anchotabla * porcentajes[i]) / total;
            tcm.getColumn(i).setPreferredWidth(Math.max(60, w));
        }
    }
    
    private void HookResizeProporcional(JScrollPane scroll, Runnable ajustar) {
        scroll.getViewport().addComponentListener(new ComponentAdapter() {
            @Override 
            public void componentResized(ComponentEvent e) { 
                ajustar.run(); 
            }
        });
    }
    
    private void SeleccionarFilaUsuarioActivo() {
        if (UsuarioActivo == null) {
            return;
        }
        
        for (int i = 0; i < TblRanking.getRowCount(); i++) {
            Object v = TblRanking.getValueAt(i, 1);
            
            if (v != null && UsuarioActivo.equals(v.toString())) {
                TblRanking.setRowSelectionInterval(i, i);
                TblRanking.scrollRectToVisible(TblRanking.getCellRect(i, 0, true));
                break;
            }
        }
    }
}

