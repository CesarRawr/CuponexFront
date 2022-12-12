/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuponex.sucursales;

import cuponex.empresas.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cuponex.usuarios.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.ConexionServiciosWeb;
import pojos.Categoria;
import pojos.Empresa;
import pojos.Sucursal;
import util.Constante;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class SearchController implements Initializable {
    @FXML
    private TextField nombre;
    @FXML
    private TextField direccion;
    @FXML
    private TextField codigo_P;
    @FXML
    private TextField colonia;
    @FXML
    private TextField encargado;
    @FXML
    private TextField longitud;
    @FXML
    private TextField latitud;
    @FXML
    private TextField telefono;
    @FXML
    private TextField ciudad;
    @FXML
    private ComboBox<Empresa> empresaSelector;

    private Callback<ListView<Empresa>, ListCell<Empresa>> cellFactory = new Callback<ListView<Empresa>, ListCell<Empresa>>() {
        
        @Override
        public ListCell<Empresa> call(ListView<Empresa> l) {
            return new ListCell<Empresa>() {

                @Override
                protected void updateItem(Empresa item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            };
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public boolean initData(String searchQuery, String searchType) {
        searchQuery = searchQuery.replace(" ", "-");
        if (searchType.equals("nombre")) {
            return this.search("byName/"+searchQuery);
        }
        
        return this.search("byDireccion/"+searchQuery);
    }

    private boolean search(String query) {
        System.out.print(query);
        Sucursal data = fetch(query);
        
        if (data == null) {
            Utilidades.mostrarAlertaSimple(
                "Error", 
                "No se encontró la empresa", 
                Alert.AlertType.ERROR
            );
            
            return false;
        }
        
        this.loadEmpresas();
        
        nombre.setText(data.getNombre());
        direccion.setText(data.getDireccion());
        codigo_P.setText(data.getCodigo_P());
        colonia.setText(data.getColonia());
        encargado.setText(data.getEncargado());
        longitud.setText(String.valueOf(data.getLongitud()));
        latitud.setText(String.valueOf(data.getLatitud()));
        telefono.setText(data.getTelefono());
        ciudad.setText(data.getCiudad());
        
        ListIterator<Empresa> searchableList =  empresaSelector.getItems().listIterator();
        
        int index = 0;
        while (searchableList.hasNext()) {
            if (searchableList.next().getIdEmpresa() == data.getIdEmpresa()) {
                break;
            }
            
            index++;
        }
        
        empresaSelector.getSelectionModel().select(index);
        
        return true;
    }
    
    private Sucursal fetch(String query) {
        Sucursal data = null;
        String urlWS = Constante.URL_BASE + "sucursal/" + query;
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken<Sucursal>(){}.getType();
            data = gson.fromJson(resultadoWS, typeList);
        }catch (Exception e){
            e.getStackTrace();
        }
        
        return data;
    }

    @FXML
    private void acceptHandler(ActionEvent event) {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
    }
    
    private void loadEmpresas() {
        String urlWS = Constante.URL_BASE + "empresa/all";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Empresa> >(){}.getType();
            ArrayList<Empresa> data = gson.fromJson(resultadoWS, typeList);
            
            empresaSelector.setButtonCell(cellFactory.call(null));
            empresaSelector.setCellFactory(cellFactory);
            empresaSelector.getItems().setAll(data);
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
    }
}
