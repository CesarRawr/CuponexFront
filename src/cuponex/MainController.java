package cuponex;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Utilidades;

public class MainController implements Initializable {

    @FXML
    private Label hook;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logout(ActionEvent event) {
        this.loadLogin();
    }
    
    @FXML
    private void usuarios(ActionEvent event) {
        this.loadScreen("usuarios/Usuarios");
    }

    @FXML
    private void empresas(ActionEvent event) {
        this.loadScreen("empresas/Empresas");
    }

    @FXML
    private void sucursales(ActionEvent event) {
        this.loadScreen("sucursales/Sucursales");
    }

    @FXML
    private void promociones(ActionEvent event) {
        this.loadScreen("promociones/Promociones");
    }
    
    private void loadScreen(String screenName) {
        try{
            Parent screen = FXMLLoader.load(getClass().getResource(screenName + ".fxml"));
            Scene scenaFormulario = new Scene(screen);
            Stage scenario = new Stage();
            
            scenario.setScene(scenaFormulario);
            scenario.initModality(Modality.APPLICATION_MODAL);
            scenario.showAndWait();
        } catch (Exception e){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar ventana", Alert.AlertType.ERROR);
        }
    }
    
    private void loadLogin() {
        try {
            Parent vistaMenu = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scenaFormulario = new Scene(vistaMenu);
            Stage scenarioBase = (Stage) this.hook.getScene().getWindow();
            scenarioBase.setScene(scenaFormulario);
            scenarioBase.show();
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar pantalla", Alert.AlertType.ERROR);
        }
    }
}
