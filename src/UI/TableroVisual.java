/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Hp
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Tablero.*;
import Fichas.*;
import Interfaces.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class TableroVisual extends JPanel {
    
    private Tablero tablero;
    private int TamanoCelda = 80;
    
    private Color ColorClaro = new Color(210, 210, 210);
    private Color ColorOscuro = new Color(60, 60, 60);
    private Color ColorGrid = new Color(30, 30, 30, 80);
    private Color ColorSeleccion = new Color(255, 215, 0, 120);
    private Color ColorDestino = new Color(30, 144, 255, 120);
    private Color ColorTexto = Color.WHITE;
    
    private Posicion Seleccion;
    private ArrayList<Posicion> Destino = new ArrayList<>();
    private boolean Habilitado = true;
    
    private ArrayList<Posicion> DestinosMovimiento = new ArrayList<>();
    private ArrayList<Posicion> DestinosAtaque = new ArrayList<>();
    private Posicion SeleccionActual = null;
    
    private Clickable clickable;
    private Providable providable;
    
    public TableroVisual(Tablero tablero) {
        this.tablero = tablero;
        setOpaque(true);
        setBackground(Color.BLACK);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Habilitado || tablero == null) {
                    return;
                }
                
                Posicion pos = PixelaPos(e.getX(), e.getY());
                
                if (pos == null) {
                    return;
                }
                
                if (clickable != null) {
                    clickable.ClickEn(pos);
                }
            }
        });
    }
    
    public TableroVisual(Tablero tablero, int TamanoCelda) {
        this(tablero);
        this.TamanoCelda = TamanoCelda;
    }
    
    public void setClickable(Clickable listener) {
        this.clickable = listener;
    }
    
    public void setProvidable(Providable proveedor) {
        this.providable = proveedor;
    }
    
    public void setHabilitado(boolean valor) {
        this.Habilitado = valor;
    }
    
    public void setSeleccion(Posicion pos) {
        this.Seleccion = pos;
    }
    
    public void setDestinos(ArrayList<Posicion> posicion) {
        setDestinosMovimiento(posicion);
        DestinosAtaque.clear();
        
        repaint();
    }
    
    public void limpiarDestinos() {
        DestinosMovimiento.clear();
        DestinosAtaque.clear();
        
        repaint();
    }
    
    public void setTamanoCelda(int tamano) {
        if (tamano <= 24) {
            tamano = 24;
        }
        
        this.TamanoCelda = tamano;
        revalidate();
        repaint();
    }
    
    public void setColor(Color claro, Color oscuro, Color grid) {
        if (claro != null) {
            this.ColorClaro = claro;
        }
        
        if (oscuro != null) {
            this.ColorOscuro = oscuro;
        }
        
        if (grid != null) {
            this.ColorGrid = grid;
        }
        
        repaint();
    }
    
    public void setDestinosMovimiento(ArrayList<Posicion> lista) {
        DestinosMovimiento.clear();
        
        if (lista != null) {
            DestinosMovimiento.addAll(lista);
        }
        
        repaint();
    }
    
    public void setDestinosAtaque(ArrayList<Posicion> lista) {
        DestinosAtaque.clear();
        
        if (lista != null) {
            DestinosAtaque.addAll(lista);
        }
        
        repaint();
    }
    
    public void Seleccionar(Posicion pos) {
        SeleccionActual = pos;
        
        repaint();
    }
    
    public void LimpiarSeleccion() {
        SeleccionActual = null;
        
        repaint();
    }
    
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
        revalidate();
        
        repaint();
    } 
    
    
    public Posicion PixelaPos(int x, int y) {
        if (tablero == null) {
            return null;
        }
        
        int fila = y / TamanoCelda;
        int col = x / TamanoCelda;
        Posicion pos = new Posicion(fila, col);
        
        return tablero.Dentro(pos) ? pos : null;
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (tablero == null) {
            return new Dimension(0, 0);
        }
        
        return new Dimension(tablero.getColumnas() * TamanoCelda, tablero.getFilas() * TamanoCelda);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (tablero == null) {
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int filas = tablero.getFilas();
        int cols = tablero.getColumnas();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        //Aqui se dibuja el tablero
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < cols; col++) {
                boolean par = ((fila + col) % 2 == 0);
                
                g2d.setColor(par ? ColorClaro : ColorOscuro);
                
                int x = col * TamanoCelda;
                int y = fila * TamanoCelda;
                
                g2d.fillRect(x, y, TamanoCelda, TamanoCelda);
            }
        }
        
        g2d.setColor(ColorGrid);
        for (int col = 0; col < cols; col++) {
            int x = col * TamanoCelda;
            g2d.drawLine(x, 0, x, filas * TamanoCelda);
        }
        
        for (int fila = 0; fila < filas; fila++) {
            int y = filas * TamanoCelda;
            g2d.drawLine(0, y, cols * TamanoCelda, y);
        }
        
        
        //Resaltado de seleccion
        if (Seleccion != null && tablero.Dentro(Seleccion)) {
            g2d.setColor(ColorSeleccion);
            g2d.fillRect(Seleccion.Col * TamanoCelda, Seleccion.Fila * TamanoCelda, TamanoCelda, TamanoCelda);
        }
        
        //Resaltado de destino
        g2d.setColor(ColorDestino);
        
        for (Posicion destino : Destino) {
            if (destino != null && tablero.Dentro(destino)) {
                int x = destino.Col * TamanoCelda;
                int y = destino.Fila * TamanoCelda;
                
                g.fillRect(x, y, TamanoCelda, TamanoCelda);
            }
        }
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Colores de overlay
        Color colormover = new Color(100, 200, 120, 130); //Verde
        Color coloratacar = new Color(220, 90, 90, 140); //Rojo
        Color colorseleccion = new Color(255, 220, 100); //Dorado
        
        int celdaW = TamanoCelda;
        int celdaH = TamanoCelda;
        
        //Movimientos
        g2.setColor(colormover);
        for (Posicion p : DestinosMovimiento) {
            if (tablero.Dentro(p)) {
                int rx = p.Col * celdaW;
                int ry = p.Fila * celdaH;
                
                g2.fillRect(rx, ry, celdaW, celdaH);
            }
        }
        
        //Ataques
        g2.setColor(coloratacar);
        for (Posicion p : DestinosAtaque) {
            if (tablero.Dentro(p)) {
                int rx = p.Col * celdaW;
                int ry = p.Fila * celdaH;
                
                g2.fillRect(rx, ry, celdaW, celdaH);
            }
        }
        
        //Seleccion de origen
        if (SeleccionActual != null && tablero.Dentro(SeleccionActual)) {
            int rx = SeleccionActual.Col * celdaW;
            int ry = SeleccionActual.Fila * celdaH;
            
            g2.setColor(colorseleccion);
            g2.setStroke(new BasicStroke(3f));
            g2.drawRect(rx + 1, ry + 1, celdaW - 2, celdaH - 2);
        }
        
        //Aqui dibujo las fichas
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < cols; col++) {
                Posicion pos = new Posicion(fila, col);
                Casilla casilla = tablero.get(pos);
                
                if (casilla == null || casilla.CasillaLibre()) {
                    continue;
                }
                
                Ficha ficha = casilla.getOcupante();
                Image imagen = null;
                
                if (providable != null) {
                    ImageIcon icono = providable.IconoDe(ficha);
                    
                    if (icono != null) {
                        imagen = icono.getImage();
                    }
                }
                
                
                if (imagen == null) {
                    String ruta = "/images/";
                    
                    if (ficha instanceof HombreLobo) {
                        ruta += (ficha.getColor().name().equals("BLANCAS")) ? "IconoHombreLoboBlanco.PNG" : "IconoHombreLoboNegro.PNG";
                    } else if (ficha instanceof Vampiro) {
                        ruta += (ficha.getColor().name().equals("BLANCAS")) ? "IconoVampiroBlanco.PNG" : "IconoVampiroNegro.PNG";
                    } else if (ficha instanceof Muerte) {
                        ruta += (ficha.getColor().name().equals("BLANCAS")) ? "IconoMuerteBlanco.PNG" : "IconoMuerteNegro.PNG";
                    } else if (ficha instanceof Zombie) {
                        ruta += (ficha.getColor().name().equals("BLANCAS")) ? "IconoZombieBlanco.PNG" : "IconoZombieoNegro.PNG";
                    } else {
                        ruta = null;
                    }
                    
                    if (ruta != null) {
                        URL url = getClass().getResource(ruta);
                        
                        if (url != null) {
                            imagen = new ImageIcon(url).getImage();
                        }
                    }
                }
                
                int x = col * TamanoCelda;
                int y = fila * TamanoCelda;
                
                if (imagen != null) {
                    //Escalado con un padding para que no toque los bordes
                    int pad = Math.max(6, TamanoCelda / 10);
                    int dw = TamanoCelda - pad * 2;
                    int dh = TamanoCelda - pad * 2;
                    
                    g2d.drawImage(imagen, x + pad, y + pad, dw, dh, this);
                } else {
                    //Si no encuentra la imagen por cualquier razon, se hace una pequena ficha circular con la inicial de la ficha que deberia de estar ahi
                    int pad = Math.max(8, TamanoCelda / 8);
                    int radio = TamanoCelda - pad * 2;
                    
                    //Color por bando
                    if (ficha.getColor().name().equals("BLANCAS")) {
                        g2d.setColor(new Color(240, 240, 240));
                    } else {
                        g2d.setColor(new Color(30, 30, 30));
                    }
                    
                    g2d.fillOval(x + pad, y + pad, radio, radio);
                    
                    g2d.setColor(ColorTexto);
                    
                    String letra = ficha.getNombre().isEmpty() ? "?" : String.valueOf(ficha.getNombre().charAt(0));
                    Font fuentevieja = g2d.getFont();
                    
                    g2d.setFont(fuentevieja.deriveFont(Font.BOLD, Math.max(14f, TamanoCelda * 0.35f)));
                    
                    FontMetrics fm = g2d.getFontMetrics();
                    int textoX = x + (TamanoCelda - fm.stringWidth(letra)) / 2;
                    int textoY = y + (TamanoCelda + fm.getAscent() - fm.getDescent()) / 2;
                    
                    g2d.drawString(letra, textoX, textoY);
                    g2d.setFont(fuentevieja);
                }
            }
        }
    }
}
