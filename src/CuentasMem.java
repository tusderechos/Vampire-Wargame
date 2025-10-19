/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

public class CuentasMem {
    private String[] Usuarios;
    private String[] Contrasenas;
    private int Registrados;
    private int MAX;

    public CuentasMem(int max) {
        this.MAX = max;
        this.Usuarios = new String[MAX];
        this.Contrasenas = new String[MAX];
        Registrados = 0;
    }
    
    public int indexOf(String usuario) {
        usuario = usuario.trim();
        for (int i = 0; i < Registrados - 1; i++) {
            if (Usuarios[i].equals(usuario)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean ExisteUsuario(String usuario) {
        if (usuario == null) {
            return false;
        }
        
        usuario = usuario.trim();
        
        if (indexOf(usuario) >= 0) {
            return true;
        }
        
        return false;
    }
    
    public boolean isFull() {
        if (Registrados == MAX) {
            return true;
        }
        
        return false;
    }
    
    public boolean isEmpty() {
        if (Registrados == 0)
            return true;
        
        return false;
    }
    
    public boolean Agregar(String usuario, String contrasena) {
        if (Registrados == MAX) {
            return false;
        }
        
        if (usuario == null || contrasena == null || usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
            return false;
        }
        
        if (ExisteUsuario(usuario) == true) {
            return false;
        }
        
        Usuarios[Registrados] = usuario.trim();
        Contrasenas[Registrados] = contrasena;
        Registrados++;
        
        return true;
        
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
        
        if (contrasena.equals(Contrasenas[indice])) {
            return true;
        } else {
            return false;
        }
    }
}