/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuponex.sucursales;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
public class DeleteController implements Initializable {
    private int id;
    private SucursalesController controller;
    
    @FXML
    private Label hook;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData(int id, SucursalesController controller) {
        this.id = id;
        this.controller = controller;
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) hook.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acceptHandler(ActionEvent event) {
        String path = "sucursal/eliminar";

        try {
            String urlServicio = Constante.URL_BASE + path;
            String parametros = "idSucursal="+this.id;

            String resultadoWS = ConexionServiciosWeb.peticionServicioDELETE(urlServicio, parametros);
            Gson gson = new Gson();

            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
            
            Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
            
            // Reload
            this.controller.reload();
            
            Stage stage = (Stage) hook.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
