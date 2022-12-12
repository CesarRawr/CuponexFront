package pojos;

public class Sucursal {
    private Integer idSucursal;
    private Integer idEmpresa;
    private String nombre;
    private String direccion;
    private String codigo_P;
    private String colonia;
    private String ciudad;
    private String telefono;
    private float latitud;
    private float longitud;
    private String encargado;
    private String empresa;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, Integer idEmpresa, String nombre, String direccion, String codigo_P, String colonia, String ciudad, String telefono, float latitud, float longitud, String encargado) {
        this.idSucursal = idSucursal;
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigo_P = codigo_P;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.encargado = encargado;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
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

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    
}
