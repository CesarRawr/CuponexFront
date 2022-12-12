package cuponex.sucursales;

import cuponex.empresas.*;
import cuponex.usuarios.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.ConexionServiciosWeb;
import pojos.Categoria;
import pojos.Empresa;
import pojos.RespuestaLogin;
import pojos.Sucursal;
import pojos.Usuario;
import util.Constante;
import util.Utilidades;

public class EditController implements Initializable {
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

    private Sucursal data;
    private SucursalesController controller;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadEmpresas();
    }    
    
    public void initData(Sucursal data, SucursalesController controller) {
        this.controller = controller;
        this.data = data;
        this.loadInputData();
    }

    @FXML
    private void createHandler(ActionEvent event) {
        String path = "sucursal/modificar";
        
        AnchorPane dad = (AnchorPane) nombre.getScene().getRoot();
        // Check if textfierlds are not empty
        for (Node node : dad.getChildren()) {
            if (node instanceof TextField) {
                String text = ((TextField)node).getText();
                if (text.isEmpty()) {
                    Utilidades.mostrarAlertaSimple("Error", "No dejes campos vacios", Alert.AlertType.INFORMATION);
                    return;
                }
            }
        }
        
        // Check if comboboxes are not empty
        for (Node node : dad.getChildren()) {
            if (node instanceof ComboBox) {
                if (((ComboBox)node).getValue() == null) {
                    Utilidades.mostrarAlertaSimple("Error", "No dejes combobox vacios", Alert.AlertType.INFORMATION);
                    return;
                }
            }
        }
        
        try {
            Float lat = Float.parseFloat(latitud.getText());
            Float lon = Float.parseFloat(longitud.getText());
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Latitud y longitud deben ser numeros", Alert.AlertType.INFORMATION);
            return;
        }
        
        try {
            String urlServicio = Constante.URL_BASE + path;
            String parametros = "idSucursal="+data.getIdSucursal()+
                                "&nombre="+nombre.getText()+
                                "&direccion="+direccion.getText()+
                                "&codigo_P="+codigo_P.getText()+
                                "&colonia="+colonia.getText()+
                                "&ciudad="+ciudad.getText()+
                                "&telefono="+telefono.getText()+
                                "&latitud="+latitud.getText()+
                                "&longitud="+longitud.getText()+
                                "&encargado="+encargado.getText()+
                                "&idEmpresa="+empresaSelector.getValue().getIdEmpresa();

            String resultadoWS = ConexionServiciosWeb.peticionServicioPUT(urlServicio, parametros);
            Gson gson = new Gson();

            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
            
            Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
            
            // Reload
            this.controller.reload();
            
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
    
    private void loadInputData() {
        if (data == null) {
            Utilidades.mostrarAlertaSimple(
                "Error", 
                "Error al cargar la información", 
                Alert.AlertType.ERROR
            );
            return;
        }
        
        
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
    }
}
