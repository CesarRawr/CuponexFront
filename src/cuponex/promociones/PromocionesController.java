 package cuponex.promociones;

import cuponex.sucursales.*;
import cuponex.empresas.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pojos.Empresa;
import pojos.Promocion;
import util.Utilidades;

public class PromocionesController implements Initializable {
    
    private String searchBySelectedOption = "nombre";
    private ObservableList<Promocion> list = FXCollections.observableArrayList();
    
    @FXML
    private TextField searchInput;
    @FXML
    private TableView<Promocion> table;
    @FXML
    private TableColumn nombre;
    @FXML
    private TableColumn descripcion;
    @FXML
    private TableColumn fecha_In;
    @FXML
    private TableColumn fecha_Fn;
    @FXML
    private TableColumn restricciones;
    @FXML
    private TableColumn porcentDesc;
    @FXML
    private TableColumn costoProm;
    @FXML
    private TableColumn tipoProm;
    @FXML
    private TableColumn categoria;
    @FXML
    private TableColumn estatus;
    @FXML
    private ComboBox<String> searchBySelector;
    @FXML
    private DatePicker datepicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datepicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datepicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        
        searchBySelector.getItems().addAll("Nombre", "Fecha de inicio", "Fecha de termino");
        searchBySelector.getSelectionModel().selectFirst();
        
        nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        descripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        fecha_In.setCellValueFactory(new PropertyValueFactory("fecha_In"));
        fecha_Fn.setCellValueFactory(new PropertyValueFactory("fecha_Fn"));
        restricciones.setCellValueFactory(new PropertyValueFactory("restricciones"));
        porcentDesc.setCellValueFactory(new PropertyValueFactory("porcentDesc"));
        costoProm.setCellValueFactory(new PropertyValueFactory("costoProm"));
        tipoProm.setCellValueFactory(new PropertyValueFactory("tipoProm"));
        categoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        estatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        
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
            Promocion data = list.get(filaSeleccionada);
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
            int id = list.get(filaSeleccionada).getIdPromocion();
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
        
        // Si el datepicker está seleccionado
        if (!this.searchBySelectedOption.equals("nombre")) {
            searchQuery = this.datepicker.getValue().toString();
        }
        
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
        String urlWS = Constante.URL_BASE + "promocion/all";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Promocion> >(){}.getType();
            ArrayList<Promocion> data = gson.fromJson(resultadoWS, typeList);
            
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
        String optionSelected = searchBySelector.getValue().toLowerCase().replace(" ", "-");
        this.searchBySelectedOption = optionSelected;
        
        // Ocultar datepicker si la opcion cambia a nombre
        if (this.searchBySelectedOption.equals("nombre")) {
            this.datepicker.setDisable(true);
            this.datepicker.setVisible(false);
            
            this.searchInput.setEditable(true);
            this.searchInput.setVisible(true);
            return;
        }
        
        // Solo ocultar una vez el searchInput
        if (searchInput.isVisible()) {
            this.searchInput.setEditable(false);
            this.searchInput.setVisible(false);
        }
        
        this.datepicker.setDisable(false);
        this.datepicker.setVisible(true);
        this.datepicker.setPromptText(searchBySelector.getValue().toLowerCase());
    }
}
