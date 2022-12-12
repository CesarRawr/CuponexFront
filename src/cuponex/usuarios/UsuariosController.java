package cuponex.usuarios;

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
import pojos.Usuario;
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
import util.Utilidades;

public class UsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> table;
    @FXML
    private TableColumn idUAdmin;
    @FXML
    private TableColumn nombre;
    @FXML
    private TableColumn apellidoP;
    @FXML
    private TableColumn apellidoM;
    @FXML
    private TableColumn correo;
    
    private ObservableList<Usuario> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idUAdmin.setCellValueFactory(new PropertyValueFactory("idUAdmin"));
        nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        apellidoP.setCellValueFactory(new PropertyValueFactory("apellidoP"));
        apellidoM.setCellValueFactory(new PropertyValueFactory("apellidoM"));
        correo.setCellValueFactory(new PropertyValueFactory("correo"));
        
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
            int idUsuario = list.get(filaSeleccionada).getIdUAdmin();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            
            Parent root = (Parent) loader.load();
            EditController controller = loader.getController();
            controller.initData(idUsuario, this);

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
            int idUsuario = list.get(filaSeleccionada).getIdUAdmin();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete.fxml"));
            
            Parent root = (Parent) loader.load();
            DeleteController controller = loader.getController();
            controller.initData(idUsuario, this);

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
        String urlWS = Constante.URL_BASE + "administrador/all";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Usuario> >(){}.getType();
            ArrayList data = gson.fromJson(resultadoWS, typeList);
            
            list.addAll(data);
            table.setItems(list);
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "Por el momento no se puede obtener la información de los médicos", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void loadScreenWithData(Parent root) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}
