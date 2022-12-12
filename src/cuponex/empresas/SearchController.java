/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuponex.empresas;

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
    private TextField nombreCom;
    @FXML
    private TextField representante;
    @FXML
    private TextField correo;
    @FXML
    private TextField direccion;
    @FXML
    private TextField rfc;
    @FXML
    private TextField paginaW;
    @FXML
    private TextField telefono;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField codigo_P;
    @FXML
    private ComboBox<Categoria> categoriaSelector;
    
    private Callback<ListView<Categoria>, ListCell<Categoria>> cellFactory = new Callback<ListView<Categoria>, ListCell<Categoria>>() {
        
        @Override
        public ListCell<Categoria> call(ListView<Categoria> l) {
            return new ListCell<Categoria>() {

                @Override
                protected void updateItem(Categoria item, boolean empty) {
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
        
        if (searchType.equals("rfc")) {
            return this.search("byRFC/"+searchQuery);
        }
        
        return this.search("byRep/"+searchQuery);
    }

    private boolean search(String query) {
        System.out.print(query);
        Empresa data = fetch(query);
        
        if (data == null) {
            Utilidades.mostrarAlertaSimple(
                "Error", 
                "No se encontró la empresa", 
                Alert.AlertType.ERROR
            );
            
            return false;
        }
        
        this.loadCategorias();
        
        nombre.setText(data.getNombre());
        nombreCom.setText(data.getNombreCom());
        representante.setText(data.getRepresentante());
        correo.setText(data.getCorreo());
        direccion.setText(data.getDireccion());
        rfc.setText(data.getRfc());
        paginaW.setText(data.getPaginaW());
        telefono.setText(data.getTelefono());
        ciudad.setText(data.getCiudad());
        codigo_P.setText(data.getCodigo_P());
        
        ListIterator<Categoria> searchableList =  categoriaSelector.getItems().listIterator();
        
        int index = 0;
        while (searchableList.hasNext()) {
            if (searchableList.next().getIdCatalogo() == data.getIdCategoriaE()) {
                break;
            }
            
            index++;
        }
        
        categoriaSelector.getSelectionModel().select(index);
        
        return true;
    }
    
    private Empresa fetch(String query) {
        Empresa data = null;
        String urlWS = Constante.URL_BASE + "empresa/" + query;
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken<Empresa>(){}.getType();
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
    
    private void loadCategorias() {
        String urlWS = Constante.URL_BASE + "empresa/categorias";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Categoria> >(){}.getType();
            ArrayList data = gson.fromJson(resultadoWS, typeList);
            
            categoriaSelector.setButtonCell(cellFactory.call(null));
            categoriaSelector.setCellFactory(cellFactory);
            categoriaSelector.getItems().setAll(data);
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
    }
}
