/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

/**
 *
 * @author andre
 */
public class RespuestaLogin {
    private int id;
    private Boolean error;
    private String mensaje;
    private Integer idUsuarioSesion;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer idRol;
    private String nombreRol;

    public RespuestaLogin() {
    }

    public RespuestaLogin(Boolean error, String mensaje, Integer idUsuarioSesion, String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol, String nombreRol) {
        this.error = error;
        this.mensaje = mensaje;
        this.idUsuarioSesion = idUsuarioSesion;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdUsuarioSesion() {
        return idUsuarioSesion;
    }

    public void setIdUsuarioSesion(Integer idUsuarioSesion) {
        this.idUsuarioSesion = idUsuarioSesion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
