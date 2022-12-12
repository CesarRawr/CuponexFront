/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuponex.usuarios;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class SearchController implements Initializable {

    private String nombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData(String nombre) {
        this.nombre = nombre;
        System.out.println(this.nombre);
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
    }

    @FXML
    private void acceptHandler(ActionEvent event) {
    }
}
