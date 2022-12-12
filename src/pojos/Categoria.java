/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

/**
 *
 * @author Cesar
 */
public class Categoria {
    private int idCatalogo;
    private String nombre;

    public Categoria(int idCatalogo, String nombre) {
        this.idCatalogo = idCatalogo;
        this.nombre = nombre;
    }

    public int getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(int idCataogo) {
        this.idCatalogo = idCataogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
