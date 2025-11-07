/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enums;

/**
 *
 * @author Hp
 */
public enum ModoAccion {
    NINGUNO(""),
    ATACAR("Atacar"),
    MOVER("Mover"),
    ATACAR_ESPECIAL("Ataque Especial"),
    ESPECIAL_VAMPIRO("Chupar Sangre"),
    INVOCAR("Invocar Zombie");
    
    private final String Descripcion;
    
    ModoAccion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }
}
