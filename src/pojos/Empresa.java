package pojos;

public class Empresa {
    
    private Integer idEmpresa;
    private String nombre;
    private String nombreCom;
    private String representante;
    private String correo;
    private String direccion;
    private String codigo_P;
    private String ciudad;
    private String telefono;
    private String paginaW;
    private String rfc;
    private String categoriaE;
    private Integer idEstatusE;
    private Integer idCategoriaE;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, String nombre, String nombreCom, String representante, String correo, String direccion, String codigo_P, String ciudad, String telefono, String paginaW, String rfc, Integer idEstatusE, Integer idCategoriaE) {
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.nombreCom = nombreCom;
        this.representante = representante;
        this.correo = correo;
        this.direccion = direccion;
        this.codigo_P = codigo_P;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.paginaW = paginaW;
        this.rfc = rfc;
        this.idEstatusE = idEstatusE;
        this.idCategoriaE = idCategoriaE;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCom() {
        return nombreCom;
    }

    public void setNombreCom(String nombreCom) {
        this.nombreCom = nombreCom;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigo_P() {
        return codigo_P;
    }

    public void setCodigo_P(String codigo_P) {
        this.codigo_P = codigo_P;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaW() {
        return paginaW;
    }

    public void setPaginaW(String paginaW) {
        this.paginaW = paginaW;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getIdEstatusE() {
        return idEstatusE;
    }

    public void setIdEstatusE(Integer idEstatusE) {
        this.idEstatusE = idEstatusE;
    }

    public Integer getIdCategoriaE() {
        return idCategoriaE;
    }

    public void setIdCategoriaE(Integer idCategoriaE) {
        this.idCategoriaE = idCategoriaE;
    }

    public String getCategoriaE() {
        return categoriaE;
    }

    public void setCategoriaE(String categoriaE) {
        this.categoriaE = categoriaE;
    }
}

