package cuponex.empresas;

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
import pojos.Usuario;
import util.Constante;
import util.Utilidades;

public class EditController implements Initializable {

    private Empresa data;
    private EmpresasController controller;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadCategorias();
    }    
    
    public void initData(Empresa data, EmpresasController controller) {
        this.controller = controller;
        this.data = data;
        this.loadInputData();
    }

    @FXML
    private void createHandler(ActionEvent event) {
        String path = "empresa/modificar";
        
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
            String urlServicio = Constante.URL_BASE + path;
            String parametros = "idEmpresa="+data.getIdEmpresa()+
                                "&nombre="+nombre.getText()+
                                "&nombreCom="+nombreCom.getText()+
                                "&representante="+representante.getText()+
                                "&correo="+correo.getText()+
                                "&direccion="+direccion.getText()+
                                "&codigo_P="+codigo_P.getText()+
                                "&ciudad="+ciudad.getText()+
                                "&telefono="+telefono.getText()+
                                "&paginaW="+paginaW.getText()+
                                "&rfc="+rfc.getText()+
                                "&idCategoriaE="+categoriaSelector.getValue().getIdCatalogo();

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
    }
}
