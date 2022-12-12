 package cuponex.empresas;

import cuponex.usuarios.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ConexionServiciosWeb;
import util.Constante;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojos.Empresa;
import util.Utilidades;

public class EmpresasController implements Initializable {

    @FXML
    private TextField searchInput;
    @FXML
    private TableView<Empresa> table;
    @FXML
    private TableColumn nombre;
    @FXML
    private TableColumn nombreCom;
    @FXML
    private TableColumn representante;
    @FXML
    private TableColumn correo;
    @FXML
    private TableColumn direccion;
    @FXML
    private TableColumn codigo_P;
    @FXML
    private TableColumn ciudad;
    @FXML
    private TableColumn telefono;
    @FXML
    private TableColumn paginaW;
    @FXML
    private TableColumn rfc;
    @FXML
    private TableColumn categoria;
    @FXML
    private ComboBox<String> searchBySelector;
    
    private String searchBySelectedOption = "nombre";
    private ObservableList<Empresa> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchBySelector.getItems().addAll("Nombre", "RFC", "Representante");
        searchBySelector.getSelectionModel().selectFirst();
        
        nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        nombreCom.setCellValueFactory(new PropertyValueFactory("nombreCom"));
        representante.setCellValueFactory(new PropertyValueFactory("representante"));
        correo.setCellValueFactory(new PropertyValueFactory("correo"));
        direccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        codigo_P.setCellValueFactory(new PropertyValueFactory("codigo_P"));
        ciudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        telefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        paginaW.setCellValueFactory(new PropertyValueFactory("paginaW"));
        rfc.setCellValueFactory(new PropertyValueFactory("rfc"));
        categoria.setCellValueFactory(new PropertyValueFactory("categoriaE"));
        
        this.loadData();
    }    
    
    public void reload() {
        table.getItems().clear();
        this.loadData();
    }     

    @FXML
    private void createHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Crear.fxml"));
            
            Parent root = (Parent) loader.load();
            CrearController controller = loader.getController();
            controller.initData(this);

            this.loadScreenWithData(root);
        } catch(Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar ventana", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editHandler(ActionEvent event) {
        int filaSeleccionada = table.getSelectionModel().getSelectedIndex();
        if (filaSeleccionada < 0) {
            Utilidades.mostrarAlertaSimple("Advertencia","Selecciona un usuario para modificarlo",Alert.AlertType.WARNING);
            return;
        }
        
        try {
            Empresa data = list.get(filaSeleccionada);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            
            Parent root = (Parent) loader.load();
            EditController controller = loader.getController();
            controller.initData(data, this);

            this.loadScreenWithData(root);
        } catch(Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar ventana", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deletehandler(ActionEvent event) {
        int filaSeleccionada = table.getSelectionModel().getSelectedIndex();
        if (filaSeleccionada < 0) {
            Utilidades.mostrarAlertaSimple("Advertencia","Selecciona un usuario para eliminarlo",Alert.AlertType.WARNING);
            return;
        }
        
        try {
            int id = list.get(filaSeleccionada).getIdEmpresa();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete.fxml"));
            
            Parent root = (Parent) loader.load();
            DeleteController controller = loader.getController();
            controller.initData(id, this);

            this.loadScreenWithData(root);
        } catch(Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar ventana", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void searchHandler(ActionEvent event) {         
        String searchQuery = this.searchInput.getText();
        
        if (searchQuery.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "No dejes el campo de busqueda vacio", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
            
            Parent root = (Parent) loader.load();
            SearchController controller = loader.getController();
            boolean isOk = controller.initData(searchQuery, this.searchBySelectedOption);
            
            if (!isOk) {
                return;
            }
            
            this.loadScreenWithData(root);
        } catch(Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar ventana", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void backHandler(ActionEvent event) {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }
    
    private void loadData() {
        String urlWS = Constante.URL_BASE + "empresa/all";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Empresa> >(){}.getType();
            ArrayList data = gson.fromJson(resultadoWS, typeList);
            
            list.addAll(data);
            table.setItems(list);
        }catch (Exception e){
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
    }
    
    private void loadScreenWithData(Parent root) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void searchByHandler(ActionEvent event) {
        this.searchInput.setText("");
        this.searchBySelectedOption = searchBySelector.getValue().toLowerCase();
        this.searchInput.setPromptText("Buscar por "+this.searchBySelectedOption);
    }
}
