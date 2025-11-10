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
    private boolean Girando = false;
    private boolean FaseFrenado = false;
    
    private final Random random = new Random();
    private Timer timer;
    
    private long UltimoTick;
    private final double VEL_GIRO_RAPIDO = 360.0;
    
    private long DuracionMs = 1500;
    private long TInicio;
    private double AnguloActual = 0;
    private double AnguloInicio;
    private double SpanTotal;
    
    private Listenable Listener;
    
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
    
    public void setListener(Listenable listen) {
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
    
    public boolean isGirando() {
        return Girando;
    }
    
    public void setIntentos(int n) {
        IntentosRestantes = Math.max(0, n);
    }
    
    public boolean QuedanIntentos() {
        return IntentosRestantes > 0;
    }
    
    public void RestarIntentos() {
        if (IntentosRestantes > 0) {
            IntentosRestantes--;
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
    
    public void GirarUnaVez() {
        if (!Girando) {
            if (!QuedanIntentos() || Items.isEmpty()) {
                return;
            }
            
            IntentosRestantes--;
            IniciarGiroContinuo();
            return;
        }
        
        if (Girando && !FaseFrenado) {
            IniciarFrenado();
        }
    }
    
    private void IniciarGiroContinuo() {
        Girando = true;
        FaseFrenado = false;
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        UltimoTick = Calendar.getInstance().getTimeInMillis();
        
        timer = new Timer(16, e -> {
           long ahora = Calendar.getInstance().getTimeInMillis();
           double dt = (ahora - UltimoTick) / 1000.0;
           UltimoTick = ahora;
           
           //Sumar angulo a velocidad constante
           AnguloActual = norm360(AnguloActual + VEL_GIRO_RAPIDO * dt);
           
           repaint();
        });
        
        timer.start();
    }
    
    private void IniciarFrenado() {
        FaseFrenado = true;
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        AnguloInicio = AnguloActual;
        TInicio = Calendar.getInstance().getTimeInMillis();
        
        int vueltasextra = 1 + random.nextInt(3);
        double extra = random.nextDouble() * 360.0;
        SpanTotal = vueltasextra * 360.0 + extra;
        
        timer = new Timer(16, e -> {
            long ahora = Calendar.getInstance().getTimeInMillis();
            double t = Math.min(1.0, ahora - TInicio) / (double) DuracionMs;
            
            //easing
            double ease = 1 - Math.pow(1 - t, 3);
            AnguloActual = norm360(AnguloInicio + SpanTotal * ease);
            
            repaint();
            
            if (t >= 1.0) {
                ((Timer) e.getSource()).stop();
                Girando = false;
                FaseFrenado = false;
                
                TipoFicha resultado = CalcularResultadoPorAngulo(AnguloActual);
                
                if (Listener != null && resultado != null) {
                    try {
                        Listener.onResultado(resultado);
                    } catch (Throwable ignorar) {
                    }
                }
            }
        });
    }
    
    public void Detener() {
        if (Girando && !FaseFrenado) {
            IniciarFrenado();
        }
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
        
        double angulorad = Math.toRadians(AnguloActual);
        g2d.rotate(angulorad, cx, cy);
        
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
        g2d.rotate(-angulorad, cx, cy);
        
        //Puntero (arriba)
        int[] puntosX = {cx, cx - 10, cx + 10};
        int[] puntosY = {cy - r - 2, cy - r + 18, cy - r + 18};
        
        g2d.setColor(new Color(230, 200, 50));
        g2d.fillPolygon(puntosX, puntosY, 3);
        
        g2d.setColor(new Color(120, 100, 20));
        g2d.fillPolygon(puntosX, puntosY, 3);
    }
    
    private static double norm360(double a) {
        double r = a % 360.0;
        
        return (r < 0) ? r + 360.0 : r;
    }
    
    private static double Normalizar(double angulo) {
        double t = angulo % (Math.PI * 2.0);
        
        if (t < 0) {
            t += Math.PI * 2.0;
        }
        
        return t;
    }
    
    private TipoFicha CalcularResultadoPorAngulo(double angulo) {
        if (Items.isEmpty()) {
            return null;
        }
        
        //Angulo actual de la ruleta pero en radianes
        double angulorad = Math.toRadians(angulo);
        
        double punteroscreen = -Math.PI / 2.0;
        
        //Angulo, en el sistema de la rueda antes de rotar
        double theta = punteroscreen - angulorad;
        theta = Normalizar(theta);
        
        //Sectores
        double inicio = -Math.PI / 2.0;
        double inicioN = Normalizar(inicio);
        
        int n = Items.size();
        double paso = (Math.PI * 2.0) / n;
        
        //Disrancia regular desde el inicio del sector
        double rel = theta - inicioN;
        
        if (rel < 0) {
            rel += Math.PI * 2.0;
        }
        
        //Averiguar en que sector cayo
        int indice = (int) Math.floor(rel / paso);
        
        if (indice < 0) {
            indice = 0;
        }
        if (indice >= n) {
            indice = n - 1;
        }
        
        return Items.get(indice);
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