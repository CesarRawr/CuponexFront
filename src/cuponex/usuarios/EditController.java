package cuponex.usuarios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ConexionServiciosWeb;
import pojos.RespuestaLogin;
import pojos.Usuario;
import util.Constante;
import util.Utilidades;

public class EditController implements Initializable {

    private int idUsuario;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidoP;
    @FXML
    private TextField apellidoM;
    @FXML
    private TextField password;
    private UsuariosController userController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void initData(int idUsuario, UsuariosController userController) {
        this.userController = userController;
        this.idUsuario = idUsuario;
        this.loadInputData();
    }

    @FXML
    private void createHandler(ActionEvent event) {
        String path = "administrador/modificar";
        
        if(nombre.getText().isEmpty() || apellidoP.getText().isEmpty() || apellidoM.getText().isEmpty() || password.getText().isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "No deje campos vacios", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            String urlServicio = Constante.URL_BASE + path;
            String parametros = "idUAdmin="+Integer.toString(idUsuario)+
                                "&nombre="+nombre.getText()+
                                "&apellidoP="+apellidoP.getText()+
                                "&apellidoM="+apellidoM.getText()+
                                "&password="+password.getText();

            String resultadoWS = ConexionServiciosWeb.peticionServicioPUT(urlServicio, parametros);
            Gson gson = new Gson();

            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
            
            Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
            
            // Reload
            this.userController.reload();
            
            Stage stage = (Stage) nombre.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
    }
    
    private void loadInputData() {
        Usuario data = this.getData();
        if (data == null) {
            Utilidades.mostrarAlertaSimple(
                "Error", 
                "Error al cargar la información", 
                Alert.AlertType.ERROR
            );
            return;
        }
        
        this.nombre.setText(data.getNombre());
        this.apellidoP.setText(data.getApellidoP());
        this.apellidoM.setText(data.getApellidoM());
        this.password.setText(data.getPassword());
    }
    
    private Usuario getData() {
        Usuario result = null;
        String urlWS = Constante.URL_BASE + "administrador/byID/" + Integer.toString(this.idUsuario);
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken<Usuario>(){}.getType();
            result = gson.fromJson(resultadoWS, typeList);
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple(
                "Error", 
                "Error al cargar la información", 
                Alert.AlertType.ERROR
            );
        }
        
        return result;
    }
}
