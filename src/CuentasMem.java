/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CuentasMem {
    public String[] Usuarios;
    public String[] Contrasenas;
    
    public int[] Puntos;
    public Calendar[] FechaIngreso;
    public boolean[] Activo;
    
    public int Registrados;
    public int MAX;

    public CuentasMem(int max) {
        this.MAX = max;
        this.Registrados = 0;
        
        this.Usuarios = new String[MAX];
        this.Contrasenas = new String[MAX];
        this.Puntos = new int[MAX];
        this.FechaIngreso = new Calendar[MAX];
        this.Activo = new boolean[MAX];
    }
    
    public int indexOf(String usuario) {
        if (usuario == null) {
            return -1;
        }
        
        for (int i = 0; i < Registrados; i++) {
            if (usuario.equals(Usuarios[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean ExisteUsuario(String usuario) {
        if (usuario == null) {
            return false;
        }
                
        if (indexOf(usuario) >= 0) {
            return true;
        }
        
        return false;
    }
    
    public boolean isFull() {
        return Registrados == MAX;
    }
    
    public boolean isEmpty() {
        return Registrados == 0;
    }

    public int getRegistrados() {
        return Registrados;
    }

    public int getMAX() {
        return MAX;
    }
    
    public boolean ValidarLogin(String usuario, String contrasena) {
        if (usuario == null || contrasena == null) {
            return false;
        }

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            return false;
        }

        int indice = indexOf(usuario);

        if (indice == -1) {
            return false;
        }

        return contrasena.equals(Contrasenas[indice]);
    }
    
    public boolean Agregar(String usuario, String contrasena) {
        if (isFull()) {
            return false;
        }
        
        if (usuario == null || contrasena == null || usuario.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        
        if (ExisteUsuario(usuario)) {
            return false;
        }
        
        Usuarios[Registrados] = usuario;
        Contrasenas[Registrados] = contrasena;
        Puntos[Registrados] = 0;
        FechaIngreso[Registrados] = Calendar.getInstance();
        Activo[Registrados] = true;
        Registrados++;
        
        return true;
    }
    
    public boolean Eliminar(String Usuario) {
        int indice = indexOf(Usuario);
        
        if (indice == -1) {
            return false;
        }
        
        int ultimo = Registrados - 1;
        
        Usuarios[indice] = Usuarios[ultimo];
        Contrasenas[indice] = Contrasenas[ultimo];
        Puntos[indice] = Puntos[ultimo];
        FechaIngreso[indice] = FechaIngreso[ultimo];
        Activo[indice] = Activo[ultimo];
        
        Usuarios[ultimo] = null;
        Contrasenas[ultimo] = null;
        Puntos[ultimo] = 0;
        FechaIngreso[ultimo] = null;
        Activo[ultimo] = false;
        
        Registrados--;
        return true;
    }
    
    public String getUsuario(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return null;
        }
        
        return Usuarios[indice];
    }
    
    public boolean ActualizarContrasena(int indice, String nuevacontra) {
        if (indice < 0 || indice >= Registrados) {
            return false;
        }
        
        if (nuevacontra == null || nuevacontra.isEmpty()) {
            return false;
        }
        
        Contrasenas[indice] = nuevacontra;
        return true;
    }
    
    public boolean ValidarContrasenaActual(int indice, String contraactual) {
        if (indice < 0 || indice >= Registrados) {
            return false;
        }
        
        if (contraactual == null) {
            return false;
        }
        
        return contraactual.equals(Contrasenas[indice]);
    }

    public int getPuntos(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return 0;
        }
        
        return Puntos[indice];
    }

    public void setPuntos(int indice, int puntos) {
        if (indice < 0 || indice >= Registrados) {
            return;
        }
        
        Puntos[indice] = puntos;
    }
    
    public void SumarPuntos(int indice, int suma) {
        if (indice < 0 || indice >= Registrados) {
            return;
        }
        
        Puntos[indice] += suma;
    }

    public Calendar getFechaIngreso(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return null;
        }
        
        return FechaIngreso[indice];
    }
    
    public String getFechaIngresoFormat(int indice, String patron) {
        Calendar calendario = getFechaIngreso(indice);
        if (calendario == null) {
            return "";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat((patron == null || patron.isEmpty()) ? "dd/MM/yyyy HH:mm" : patron);
        
        return sdf.format(calendario.getTime());
    }
    
    public boolean isActivo(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return false;
        }
        
        return Activo[indice];
    }

    public String[] getUsuarios() {
        if (Registrados == 0) {
            return new String[0];
        }
        ArrayList<String> lista = new ArrayList<>();
        
        for (int i = 0; i < Registrados; i++) {
            if (Activo[i]) {
                lista.add(Usuarios[i]);
            }
        }
        
        return lista.toArray(new String[0]);
    }
    
    public int getIndiceUsuario(String usuario) {
        return indexOf(usuario);
    }
    
    
}