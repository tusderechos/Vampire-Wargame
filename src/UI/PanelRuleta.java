/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Hp
 */

import Ruleta.Listenable;
import Ruleta.Ruleta;
import Ruleta.TipoFicha;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PanelRuleta extends JPanel{
    
    private ArrayList<ImageIcon> Frames;
    private int Indice = 0;
    private Timer timer;
    private Listenable Listener;
    private Ruleta ruleta;
    
    private ImageIcon IconoLobo;
    private ImageIcon IconoVampiro;
    private ImageIcon IconoMuerte;
    
    private ImageIcon IconoActual;
    
    public PanelRuleta(Ruleta ruleta) {
        this.ruleta = ruleta;
        
        IconoLobo = new ImageIcon(getClass().getResource("/images/ruleta_hombrelobo.PNG"));
        IconoVampiro = new ImageIcon(getClass().getResource("/images/ruleta_vampiro.PNG"));
        IconoMuerte = new ImageIcon(getClass().getResource("/images/ruleta_muerte.PNG"));
        
        Frames = new ArrayList<>();
        
        Frames.add(IconoLobo);
        Frames.add(IconoVampiro);
        Frames.add(IconoMuerte);
        Frames.add(IconoLobo);
        Frames.add(IconoVampiro);
        Frames.add(IconoMuerte);
        
        IconoActual = Frames.get(0);
        setPreferredSize(new Dimension(140, 140));
        setOpaque(false);
    }
    
    public void setListener(Listenable listen) {
        this.Listener = listen;
    }
    
    public void GiraryDetener(int mstotal) {
        int delay = 60;
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        timer = new Timer(delay, (ActionEvent e) -> {
            Indice = (Indice + 1) % Frames.size();
            IconoActual = Frames.get(Indice);
            repaint();
        });
        timer.start();
        
        Timer fin = new Timer(mstotal, ev -> {
            ((Timer) ev.getSource()).stop();
            timer.stop();
            
            TipoFicha r = ruleta.Girar();
            
            switch (r) {
                case HOMBRE_LOBO:
                    IconoActual = IconoLobo;
                case VAMPIRO:
                    IconoActual = IconoVampiro;
                case MUERTE:
                    IconoActual = IconoMuerte;
            }
            repaint();
            
            if (Listener != null) {
                Listener.onResultado(r);
            }
        });
        
        fin.setRepeats(false);
        fin.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imagen = IconoActual.getImage();
        int ancho = getWidth();
        int alto = getHeight();
        int anchoimg = imagen.getWidth(null);
        int altoimg = imagen.getHeight(null);
        
        int x = (ancho - anchoimg) / 2;
        int y = (alto - altoimg) / 2;
        
        g.drawImage(imagen, x, y, this);
    }
}
