/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuponex.usuarios;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ConexionServiciosWeb;
import pojos.RespuestaLogin;
import util.Constante;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class CrearController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidoP;
    @FXML
    private TextField apellidoM;
    @FXML
    private TextField correo;
    @FXML
    private PasswordField password;
    
    private UsuariosController userController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initData(UsuariosController userController) {
        this.userController = userController;
    }

    @FXML
    private void createHandler(ActionEvent event) {
        String path = "administrador/registrar";
        
        if(nombre.getText().isEmpty() || apellidoP.getText().isEmpty() || apellidoM.getText().isEmpty() || correo.getText().isEmpty() || password.getText().isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "No deje campos vacios", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            String urlServicio = Constante.URL_BASE + path;
            String parametros = "&nombre="+nombre.getText()+
                                "&apellidoP="+apellidoP.getText()+
                                "&apellidoM="+apellidoM.getText()+
                                "&correo="+correo.getText()+
                                "&password="+password.getText();

            String resultadoWS = ConexionServiciosWeb.peticionServicioPOST(urlServicio, parametros);
            Gson gson = new Gson();

            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
            
            Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
            
            // Reload usuarios scene
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
    
}
