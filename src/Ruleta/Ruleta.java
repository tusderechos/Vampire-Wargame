/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ruleta;

/**
 *
 * @author Hp
 */

import java.util.ArrayList;
import java.util.Random;

public class Ruleta {
    private ArrayList<TipoFicha> Caras = new ArrayList<>();
    private Random random = new Random();
    
    public Ruleta() {
        Caras.add(TipoFicha.HOMBRE_LOBO);
        Caras.add(TipoFicha.MUERTE);
        Caras.add(TipoFicha.VAMPIRO);
        Caras.add(TipoFicha.HOMBRE_LOBO);
        Caras.add(TipoFicha.MUERTE);
        Caras.add(TipoFicha.VAMPIRO);
    }
    
    public TipoFicha Girar() {
        int i = random.nextInt(Caras.size());
        
        return Caras.get(i);
    }
    
    public int IntentosPermitidos(int piezasperdidas) {
        if (piezasperdidas >= 2) {
            return 2;
        }
        if (piezasperdidas >= 4) {
            return 3;
        }
        
        return 1;
    }
}
