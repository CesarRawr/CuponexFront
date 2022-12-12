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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ConexionServiciosWeb;
import pojos.RespuestaLogin;
import util.Constante;
import util.Utilidades;

public class LoginController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private TextField nombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void loginHandler(ActionEvent event) {
        String path = "acceso/escritorio";
        String nombre = this.nombre.getText();
        String password = this.password.getText();
        
        if(nombre.isEmpty() || password.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "No deje campos vacios", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            String urlServicio = Constante.URL_BASE + path;
            String parametros = "nombre="+nombre+"&password="+password;
            String resultadoWS = ConexionServiciosWeb.peticionServicioPOST(urlServicio, parametros);
            Gson gson = new Gson();

            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);              
                return;
            }
            
            // Redirigir a nueva pagina
            loadNextScreen();
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadNextScreen() {
        try {
            Parent vistaMenu = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scenaFormulario = new Scene(vistaMenu);
            Stage scenarioBase = (Stage) this.nombre.getScene().getWindow();
            scenarioBase.setScene(scenaFormulario);
            scenarioBase.show();
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar pantalla", Alert.AlertType.ERROR);
        }
    }
}
