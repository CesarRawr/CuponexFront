package cuponex;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modelo.ConexionServiciosWeb;
import pojos.RespuestaLogin;
import util.Constante;
import util.Utilidades;

public class TestController implements Initializable {
    @FXML
    private ComboBox<?> lista_empresas;
    @FXML
    private ComboBox<?> lista_empresas1;
    @FXML
    private ComboBox<?> lista_categorias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicIngresarSistema(ActionEvent event) {
        String path = "acceso/escritorio";
        
        String nombre = "Juanito";
        String password = "12345";
        
        if(nombre.isEmpty() || password.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Debes ingresar el Número de personal y/o contraseña para ingresar ", Alert.AlertType.WARNING);
        }else{
            try {
                String urlServicio = Constante.URL_BASE + path;
                String parametros = "nombre="+nombre+"&password="+password;
                String resultadoWS = ConexionServiciosWeb.peticionServicioPOST(urlServicio, parametros);
                Gson gson = new Gson();
                
                RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
                if(!respuestaLogin.getError()){
                    Utilidades.mostrarAlertaSimple("Credenciales correctas", "Bienvenido "+respuestaLogin.getNombre()+" "+respuestaLogin.getApellidoPaterno()+" al sistema", Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                }
            }catch (Exception e){
                Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void registroUsuarioHandler(ActionEvent event) {
        String path = "administrador/registrar";
        
        String nombre = "alll";
        String apellidoP = "gulll";
        String apellidoM = "mos";
        String correo = "correo@correosss.com";
        String password = "1234";
        
        if(nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || correo.isEmpty() || password.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Debes ingresar el Número de personal y/o contraseña para ingresar ", Alert.AlertType.WARNING);
        }else{
            try {
                String urlServicio = Constante.URL_BASE + path;
                String parametros = "nombre="+nombre+
                                    "&apellidoP="+apellidoP+
                                    "&apellidoM="+apellidoM+
                                    "&correo="+correo+
                                    "&password="+password;
                
                String resultadoWS = ConexionServiciosWeb.peticionServicioPOST(urlServicio, parametros);
                Gson gson = new Gson();
                
                RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
                if(!respuestaLogin.getError()){
                    Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                }
            }catch (Exception e){
                Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void edicionUsuarioHandler(ActionEvent event) {
        String path = "administrador/modificar";
        
        String idUAdmin = "4";
        String nombre = "all";
        String apellidoP = "gulll";
        String apellidoM = "mos";
        String password = "1234";
        
        if(nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || password.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "No dejes campos vacios", Alert.AlertType.WARNING);
        }else{
            try {
                String urlServicio = Constante.URL_BASE + path;
                String parametros = "idUAdmin="+idUAdmin+
                                    "&nombre="+nombre+
                                    "&apellidoP="+apellidoP+
                                    "&apellidoM="+apellidoM+
                                    "&password="+password;
                
                String resultadoWS = ConexionServiciosWeb.peticionServicioPUT(urlServicio, parametros);
                Gson gson = new Gson();
                
                RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
                if(!respuestaLogin.getError()){
                    Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                }
            }catch (Exception e){
                Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void eliminarUsuarioHandler(ActionEvent event) {
        String path = "administrador/eliminar";
        
        String idUAdmin = "4";
        
        if(idUAdmin.isEmpty()){
            Utilidades.mostrarAlertaSimple("Error", "Error desconocido", Alert.AlertType.WARNING);
        }else{
            try {
                String urlServicio = Constante.URL_BASE + path;
                String parametros = "idUAdmin="+idUAdmin;
                
                String resultadoWS = ConexionServiciosWeb.peticionServicioDELETE(urlServicio, parametros);
                Gson gson = new Gson();
                
                RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
                if(!respuestaLogin.getError()){
                    Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                }
            }catch (Exception e){
                Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void eliminarEmpresaHandler(ActionEvent event) {
    }

    @FXML
    private void editarEmpresaHandler(ActionEvent event) {
    }

    @FXML
    private void registrarEmpresaHandler(ActionEvent event) {
        String path = "empresa/registrar";
        
        String nombre = "alll";
        String apellidoP = "gulll";
        String apellidoM = "mos";
        String correo = "correo@correosss.com";
        String password = "1234";
        
        if(nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || correo.isEmpty() || password.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Debes ingresar el Número de personal y/o contraseña para ingresar ", Alert.AlertType.WARNING);
        }else{
            try {
                String urlServicio = Constante.URL_BASE + path;
                String parametros = "nombre="+nombre+
                                    "&apellidoP="+apellidoP+
                                    "&apellidoM="+apellidoM+
                                    "&correo="+correo+
                                    "&password="+password;
                
                String resultadoWS = ConexionServiciosWeb.peticionServicioPOST(urlServicio, parametros);
                Gson gson = new Gson();
                
                RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
                if(!respuestaLogin.getError()){
                    Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                }
            }catch (Exception e){
                Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void buscarEmpresaNombreHandler(ActionEvent event) {
    }

    @FXML
    private void buscarEmpresaNrcHandler(ActionEvent event) {
    }

    @FXML
    private void buscarEmpresaRepresentanteHandler(ActionEvent event) {
    }

    @FXML
    private void eliminarSucursalHandler(ActionEvent event) {
    }

    @FXML
    private void editarSucursalHandler(ActionEvent event) {
    }

    @FXML
    private void registrarSucursalHandler(ActionEvent event) {
    }

    @FXML
    private void buscarSucursalNombreHandler(ActionEvent event) {
    }

    @FXML
    private void buscarSucursalDireccionHandler(ActionEvent event) {
    }

    @FXML
    private void eliminarPromocionHandler(ActionEvent event) {
    }

    @FXML
    private void edicionPromocionHandler(ActionEvent event) {
    }

    @FXML
    private void registroPromocionHandler(ActionEvent event) {
    }
    
    private void loadCategorias() {
        
    }
}
