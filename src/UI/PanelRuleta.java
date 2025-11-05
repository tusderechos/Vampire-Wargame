/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Hp
 */

import Interfaces.*;
import Ruleta.TipoFicha;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class PanelRuleta extends JPanel {
    
    private final ArrayList<TipoFicha> Items = new ArrayList<>();
    
    private ImageIcon IconoLobo;
    private ImageIcon IconoMuerte;
    private ImageIcon IconoVampiro;
    
    private Color Fondo = new Color(25, 25, 25);
    private Color Borde = new Color(90, 90, 90);
    private Stroke BordeStroke = new BasicStroke(2f);
    
    private final Color[] PaletaColores = new Color[] {
        new Color(60, 60, 60), 
        new Color(45, 45, 45), 
        new Color(30, 30, 30)
    };
    
    private int IntentosRestantes = 1;
    
    private final Random random = new Random();
    private Timer timer;
    private long TInicio;
    private long DuracionMs = 3000;
        
    private double AnguloActual = 0;
    private double AnguloObjetivo;
    private double AnguloInicio;
    private double SpanTotal;
    private double t0;
    
    private boolean Girando = false;
    
    private Resultable Listener;
    
    public PanelRuleta() {
        setPreferredSize(new Dimension(220, 220));
        setOpaque(true);
        setBackground(Fondo);
        
        Items.add(TipoFicha.HOMBRE_LOBO);
        Items.add(TipoFicha.VAMPIRO);
        Items.add(TipoFicha.MUERTE);
        Items.add(TipoFicha.HOMBRE_LOBO);
        Items.add(TipoFicha.VAMPIRO);
        Items.add(TipoFicha.MUERTE);
    }
    
    public void setListener(Resultable listen) {
        this.Listener = listen;
    }
    
    public void setIcons(ImageIcon lobo, ImageIcon vampiro, ImageIcon muerte) {
        this.IconoLobo = lobo;
        this.IconoVampiro = vampiro;
        this.IconoMuerte = muerte;
        
        repaint();
    }
    
    public void setItems(ArrayList<TipoFicha> nuevas) {
        Items.clear();
        
        if (nuevas != null) {
            Items.addAll(nuevas);
        }
        
        repaint();
    }
    
    public void setIntentosPorPiezasPerdidas(int perdidas) {
        if (perdidas <= 1) {
            IntentosRestantes = 1;
        } else if (perdidas <= 3) {
            IntentosRestantes = 2;
        } else {
            IntentosRestantes = 3;
        }
    }
    
    public boolean QuedanIntentos() {
        return IntentosRestantes > 0;
    }
    
    public void RestarIntentos() {
        if (IntentosRestantes > 0) {
            IntentosRestantes--;
        }
    }
    
    public void GiraryDetener(int ms) {
        if (Girando || Items.isEmpty()) {
            return;
        }
        
        DuracionMs = Math.max(600, ms);
        
        int indiceobjetivo = random.nextInt(Items.size());
        
        double paso = (Math.PI * 2.0) / Items.size();
        double seccioncentro = (indiceobjetivo + 0.5) * paso - Math.PI / 2.0; //Puntero arriba
        
        int vueltas = 4 + random.nextInt(2);
        AnguloObjetivo = Normalizar(seccioncentro + vueltas * Math.PI * 2.0);
        
        AnguloInicio = Normalizar(AnguloActual);
        SpanTotal = Normalizar(AnguloObjetivo - AnguloInicio);
        
        if (SpanTotal < Math.toRadians(30)) {
            SpanTotal += 2 * Math.PI;
        }
        
        t0 = (double) Calendar.getInstance().getTimeInMillis();
        Girando = true;
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        timer = new Timer(16, (ActionEvent e) -> AnimacionTick());
        timer.start();
    }
    
    
    private void AnimacionTick() {
        double ahora = (double) Calendar.getInstance().getTimeInMillis();
        double progreso = ValorNormalizado((ahora - t0) / (double) DuracionMs);
        
        double ease = 1 - Math.pow(1 - progreso, 3.0);
        
        AnguloActual = Normalizar(AnguloActual + SpanTotal * ease); //Aqui aseguro que la ruleta no vaya a ir contra el reloj
        
        //Fin cuando se alcanza la duracion o esta practicamente en el objetivo
        if (progreso >= 1.0) {
            AnguloActual = AnguloObjetivo;
            Girando = false;
            timer.stop();
            
            if (Listener != null) {
                Listener.Seleccionado(Items.get(IndiceDesdeAngulo(AnguloActual)));
            }
        }
        
        repaint();
    }
    
    private int IndiceDesdeAngulo(double angulo) {
        double paso = (Math.PI * 2.0) / Items.size();
        double norm = Normalizar(angulo);
        
        double referencia = Normalizar(norm - (-Math.PI / 2.0)); //Puntero arriba
        int indice = (int) Math.floor(referencia / paso);
        
        if (indice < 0) {
            indice = 0;
        }
        if (indice >= Items.size()) {
            indice = Items.size() - 1;
        }
        
        return indice;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        int d = Math.min(w, h) - 10;
        int r = d / 2;
        int cx = w / 2;
        int cy = h / 2;
        
        //Fondo
        g2d.setColor(Fondo);
        g2d.fillRect(0, 0, w, h);
        
        //Disco base
        int x = cx - r;
        int y = cy - r;      
        
        g2d.setColor(new Color(35, 35, 35));
        g2d.fillOval(x, y, d, d);
        
        g2d.setColor(Borde);
        g2d.setStroke(BordeStroke);
        g2d.drawOval(x, y, d, d);
        
        g2d.rotate(AnguloActual, cx, cy);
        
        double paso = (Math.PI * 2.0) / Items.size();
        double inicio = -Math.PI / 2.0;
        
        //Sectores y iconos
        for (int i = 0; i < Items.size(); i++) {
            double a0 = inicio + i * paso;
            
            //Relleno del mismo
            g2d.setColor(PaletaColores[i % PaletaColores.length]);
            
            int deginicio = (int) Math.toDegrees(-a0);
            int degarco = (int) Math.toDegrees(-paso);
            
            g2d.fillArc(x, y, d, d, deginicio, degarco);
            
            //Icono centrado en el sector
            Image icon = IconoDe(Items.get(i));
            
            if (icon != null) {
                double ac = a0 + paso / 2.0;
                double rr = r * 0.30;
                
                int tamanoicono = (int) (d * 0.28);
                int ix = cx + (int) (rr * Math.cos(ac)) - tamanoicono / 2;
                int iy = cy + (int) (rr * Math.sin(ac)) - tamanoicono / 2;
                
                g2d.drawImage(icon, ix, iy, tamanoicono, tamanoicono, null);
            }
        }
        
        //"Desrotar" para poder dibujar un puntero fijo
        g2d.rotate(-AnguloActual, cx, cy);
        
        //Puntero (arriba)
        int[] puntosX = {cx, cx - 10, cx + 10};
        int[] puntosY = {cy - r - 2, cy - r + 18, cy - r + 18};
        
        g2d.setColor(new Color(230, 200, 50));
        g2d.fillPolygon(puntosX, puntosY, 3);
        
        g2d.setColor(new Color(120, 100, 20));
        g2d.fillPolygon(puntosX, puntosY, 3);
    }
    
    private static double Normalizar(double angulo) {
        double t = angulo % (Math.PI * 2.0);
        
        if (t < 0) {
            t += Math.PI * 2.0;
        }
        
        return t;
    }
    
    private static double ValorNormalizado(double v) {
        if (v < 0) {
            return 0;
        }
        if (v > 1) {
            return 1;
        }
        
        return v;
    }
    
    private static double DeltaAngular(double a, double b) {
        double d = (b - a) % (Math.PI * 2.0);
        
        if (d > Math.PI) {
            d -= Math.PI * 2.0;
        }
        if (d < -Math.PI) {
            d += Math.PI * 2.0;
        }
        
        return d;
    }
    
    private Image IconoDe(TipoFicha tipo) {
        switch (tipo) {
            case HOMBRE_LOBO:
                return IconoLobo != null ? IconoLobo.getImage() : null;
            case VAMPIRO:
                return IconoVampiro != null ? IconoVampiro.getImage() : null;
            case MUERTE:
                return IconoMuerte != null ? IconoMuerte.getImage() : null;
            default:
                return null;
        }
    }
} 