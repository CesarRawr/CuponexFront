package pojos;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Promocion {
    
    @Expose
    private Integer idPromocion;
    @Expose
    private String nombre;
    @Expose
    private String descripcion;
    @Expose
    private String fecha_In;
    @Expose
    private String fecha_Fn;
    @Expose
    private String restricciones;
    @Expose
    private Integer porcentDesc;
    @Expose
    private Integer costoProm;
    @Expose
    private String tipoProm;
    @Expose
    private String categoria;
    @Expose
    private Integer idCategoriaE;
    @Expose
    private Boolean is_active;
    @Expose
    private ArrayList<Integer> sucursales;
    
    private String estatus;
    private String imagen;

    public Promocion() {
    }
    
    

    public Promocion(String nPromocion, String descripcion, String fecha_In, String fecha_Fn, String restricciones, Integer porcentDesc, Integer costoProm, String tipoProm, Integer idCategoriaE, Integer idEstatusP, Integer idSucursal) {
        this.nombre = nPromocion;
        this.descripcion = descripcion;
        this.fecha_In = fecha_In;
        this.fecha_Fn = fecha_Fn;
        this.restricciones = restricciones;
        this.porcentDesc = porcentDesc;
        this.costoProm = costoProm;
        this.tipoProm = tipoProm;
        this.idCategoriaE = idCategoriaE;
        this.setIs_active(is_active);
        
    }

    public Promocion(Integer idPromocion, String nPromocion, String descripcion, String restricciones, Integer porcentDesc, Integer costoProm, String tipoProm) {
        this.idPromocion = idPromocion;
        this.nombre = nPromocion;
        this.descripcion = descripcion;
        this.restricciones = restricciones;
        this.porcentDesc = porcentDesc;
        this.costoProm = costoProm;
        this.tipoProm = tipoProm;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_In() {
        return fecha_In;
    }

    public void setFecha_In(String fecha_In) {
        this.fecha_In = fecha_In;
    }

    public String getFecha_Fn() {
        return fecha_Fn;
    }

    public void setFecha_Fn(String fecha_Fn) {
        this.fecha_Fn = fecha_Fn;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    public Integer getPorcentDesc() {
        return porcentDesc;
    }

    public void setPorcentDesc(Integer porcentDesc) {
        this.porcentDesc = porcentDesc;
    }

    public Integer getCostoProm() {
        return costoProm;
    }

    public void setCostoProm(Integer costoProm) {
        this.costoProm = costoProm;
    }

    public String getTipoProm() {
        return tipoProm;
    }

    public void setTipoProm(String tipoProm) {
        this.tipoProm = tipoProm;
    }

    public Integer getIdCategoriaE() {
        return idCategoriaE;
    }

    public void setIdCategoriaE(Integer idCategoriaE) {
        this.idCategoriaE = idCategoriaE;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
        
        if (is_active) {
            this.setEstatus("activo");
            return;
        }
        
        this.setEstatus("inactivo");
    }
    
    public ArrayList<Integer> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<Integer> sucursales) {
        this.sucursales = sucursales;
    }
}
