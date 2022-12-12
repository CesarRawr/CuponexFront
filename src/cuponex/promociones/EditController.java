package cuponex.promociones;

import cuponex.sucursales.*;
import cuponex.empresas.*;
import cuponex.usuarios.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import modelo.ConexionServiciosWeb;
import pojos.Categoria;
import pojos.Empresa;
import pojos.Promocion;
import pojos.RespuestaLogin;
import pojos.Sucursal;
import pojos.Usuario;
import util.Constante;
import util.Utilidades;

public class EditController implements Initializable {
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private TextField costoProm;
    @FXML
    private TextField porcentDesc;
    @FXML
    private TextField restricciones;
    @FXML
    private ComboBox<String> estatus;
    @FXML
    private DatePicker fecha_In;
    @FXML
    private DatePicker fecha_Fn;
    @FXML
    private ComboBox<String> tipoProm;
    @FXML
    private ComboBox<Categoria> categoria;
    @FXML
    private ImageView imagen;
    @FXML
    private ComboBox<Empresa> empresaSelector;
    @FXML
    private ListView<Sucursal> sucursales;
    
    private Promocion data;
    private PromocionesController controller;
    private byte [] byteImage = null;
    private String imageType = "";

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
    
    private Callback<ListView<Categoria>, ListCell<Categoria>> cellFactoryCategoria = new Callback<ListView<Categoria>, ListCell<Categoria>>() {
        
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
    
    private Callback<ListView<Sucursal>, ListCell<Sucursal>> cellFactorySucursal = new Callback<ListView<Sucursal>, ListCell<Sucursal>>() {
        
        @Override
        public ListCell<Sucursal> call(ListView<Sucursal> l) {
            return new ListCell<Sucursal>() {

                @Override
                protected void updateItem(Sucursal item, boolean empty) {
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
        sucursales.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // Agregat formato a fecha de inicio
        fecha_In.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                fecha_In.setPromptText(pattern.toLowerCase());
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
        
        // Agregar formato a fecha final
        fecha_Fn.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                fecha_In.setPromptText(pattern.toLowerCase());
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
        
        // Settear opciones a estatus
        estatus.getItems().addAll("Activo", "Inactivo");
        estatus.getSelectionModel().selectFirst();
        
        // Settear opciones a tipo promocion
        tipoProm.getItems().addAll("Descuento", "Costo Rebajado");
        
        // Cargar info en combobox
        this.loadEmpresas();
        this.loadCategorias();
    }    
    
    public void initData(Promocion data, PromocionesController controller) {
        this.controller = controller;
        this.data = data;
        this.loadAllSucursales();
        this.loadInputData();
    }

    @FXML
    private void createHandler(ActionEvent event) {
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
        
        // Check if there isnt an enterprice selected
        if (empresaSelector.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Error", "Selecciona una empresa y al menos una sucursal", Alert.AlertType.INFORMATION);
            return;
        }
        
        // Check if comboboxes are not empty
        for (Node node : dad.getChildren()) {
            if (node instanceof ComboBox) {
                if (((ComboBox)node).getValue() == null) {
                    Utilidades.mostrarAlertaSimple("Error", "No dejes opciones sin seleccionar", Alert.AlertType.INFORMATION);
                    return;
                }
            }
        }
        
        // Check if image is empty
        if (byteImage == null) {
            Utilidades.mostrarAlertaSimple("Error", "Selecciona una imagen", Alert.AlertType.INFORMATION);
            return;
        }
        
        // Check if theres no sucursales selected
        if (sucursales.getSelectionModel().getSelectedItems().size() == 0) {
            Utilidades.mostrarAlertaSimple("Error", "Selecciona al menos una sucursal", Alert.AlertType.INFORMATION);
            return;
        }
        
        int costo = 0;
        int descuento = 0;
        try {
            // Setting numbers
            costo = Integer.parseInt(costoProm.getText());
            descuento = Integer.parseInt(porcentDesc.getText());
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Costo y descuento deben ser numeros", Alert.AlertType.INFORMATION);
            return;
        }
        
        // Setting status
        Boolean isActive = true;
        if (estatus.getValue().equals("Inactivo")) {
            isActive = false;
        }
        
        // Setting sucursales ids
        ArrayList<Integer> sucursalesId = new ArrayList<Integer>();
        ObservableList<Sucursal> data = sucursales.getSelectionModel().getSelectedItems();
        for (int i=0; i < data.size(); i++) {
            sucursalesId.add(data.get(i).getIdSucursal());
        }
        
        try {
            Gson gson = new Gson();
            
            String path = "promocion/modificar";
            String urlServicio = Constante.URL_BASE + path;
            
            Promocion promocion = new Promocion();
            promocion.setIdPromocion(this.data.getIdPromocion());
            promocion.setNombre(nombre.getText());
            promocion.setDescripcion(descripcion.getText());
            promocion.setRestricciones(restricciones.getText());
            promocion.setPorcentDesc(descuento);
            promocion.setCostoProm(costo);
            promocion.setTipoProm(tipoProm.getValue());
            promocion.setIs_active(isActive);
            promocion.setIdCategoriaE(categoria.getValue().getIdCatalogo());
            promocion.setSucursales(sucursalesId);
            
            // Sending data
            String resultadoWS = ConexionServiciosWeb.peticionServicioPUTJson(urlServicio, gson.toJson(promocion));
            System.out.println(resultadoWS);
            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
            
            // Sending image
            String urlImage = "promocion/subirImagen/"+Integer.toString(this.data.getIdPromocion());
            System.out.println(Constante.URL_BASE + urlImage);
            String resultado = ConexionServiciosWeb.peticionServicioPOSTImage(Constante.URL_BASE + urlImage, this.byteImage);
            RespuestaLogin respuesta = gson.fromJson(resultado, RespuestaLogin.class);
            if(respuesta.getError()){
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuesta.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
            
            Utilidades.mostrarAlertaSimple("Mensaje", respuestaLogin.getMensaje(), Alert.AlertType.INFORMATION);
            
            // Reload pr scene
            this.controller.reload();
            
            Stage stage = (Stage) nombre.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
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
        
        // Inicializar info
        nombre.setText(data.getNombre());
        descripcion.setText(data.getDescripcion());
        costoProm.setText(Integer.toString(data.getCostoProm()));
        porcentDesc.setText(Integer.toString(data.getPorcentDesc()));;
        restricciones.setText(data.getRestricciones());
        
        String cap = data.getEstatus().substring(0, 1).toUpperCase() + data.getEstatus().substring(1);
        estatus.getSelectionModel().select(cap);
        
        fecha_In.setValue(fecha_In.getConverter().fromString(data.getFecha_In()));
        fecha_Fn.setValue(fecha_Fn.getConverter().fromString(data.getFecha_Fn()));
        
        cap = data.getTipoProm().substring(0, 1).toUpperCase() + data.getTipoProm().substring(1);
        tipoProm.getSelectionModel().select(cap);
        
        try {
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(data.getImagen());
            InputStream is = new ByteArrayInputStream(imageBytes);
            
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            Image img = new Image(is);
            
            this.imagen.setImage(img);
            this.byteImage = imageBytes;
            this.imageType = mimeType.split("\\/")[1];
            
            is.close();
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar la imgen", Alert.AlertType.INFORMATION);
            return;
        }
        
        
        // Combobox
        // Setting sucursales
        ArrayList<Sucursal> selectedSucursales = this.getSucursalesByPromocionId(data.getIdPromocion());
        if (selectedSucursales.size() > 0) {
            int arrIndex = 0;
            int arr[] = new int[selectedSucursales.size()];
            
            for (int i=0; i<selectedSucursales.size(); i++) {
                int index = 0;
                ListIterator<Sucursal> sucursalList =  sucursales.getItems().listIterator();
                while (sucursalList.hasNext()) {
                    if (sucursalList.next().getIdSucursal()== selectedSucursales.get(i).getIdSucursal()) {
                        arr[arrIndex] = index;
                        arrIndex++;
                        break;
                    }

                    index++;
                }
            }
            
            sucursales.getSelectionModel().selectIndices(arr[0], arr);
            
            // Setting empresa combobox
            ListIterator<Empresa> empresaList =  empresaSelector.getItems().listIterator();
            int index = 0;
            while (empresaList.hasNext()) {
                if (empresaList.next().getIdEmpresa()== sucursales.getSelectionModel().getSelectedItems().get(0).getIdEmpresa()) {
                    break;
                }

                index++;
            }
            empresaSelector.getSelectionModel().select(index);
        }
        
        // Setting categorias
        ListIterator<Categoria> categoriaList =  categoria.getItems().listIterator();
        int index = 0;
        while (categoriaList.hasNext()) {
            if (categoriaList.next().getIdCatalogo()== data.getIdCategoriaE()) {
                break;
            }

            index++;
        }
        categoria.getSelectionModel().select(index);
    }

    @FXML
    private void chooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {                
                // Solo png y jpg
                String imageName = file.getName();
                String[] imageNameArr = imageName.split("\\.");
                
                this.imageType = imageNameArr[imageNameArr.length-1].toLowerCase();
                if (!this.imageType.equals("png") && !this.imageType.equals("jpg") && !this.imageType.equals("jpeg")) {
                    Utilidades.mostrarAlertaSimple("Error", "Solo archivos png y jpg", Alert.AlertType.INFORMATION);
                    return;
                }
                
                // Si el archivo es mayor a 1mb
                if (file.length() > 1000000) {
                    Utilidades.mostrarAlertaSimple("Error", "La imagen debe pesar menos de 1mb", Alert.AlertType.INFORMATION);
                    return;
                }
                
                // Settear imagen en imageviewer
                Image image1 = new Image(file.toURI().toString());
                this.imagen.setImage(image1);
                
                // Transformar imagen en bytes
                BufferedImage bImage = ImageIO.read(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, imageType, bos);
                this.byteImage = bos.toByteArray();
            } catch(Exception e) {
                e.printStackTrace();
                Utilidades.mostrarAlertaSimple("Error", "Algo salió mal con la imagen", Alert.AlertType.INFORMATION);
                return;
            }
       }
    }

    @FXML
    private void empresaSelectorHandler(ActionEvent event) {
        sucursales.getItems().clear();
        this.loadSucursales();
    }
    
    private void loadCategorias() {
        String urlWS = Constante.URL_BASE + "empresa/categorias";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Categoria> >(){}.getType();
            ArrayList data = gson.fromJson(resultadoWS, typeList);
            
            categoria.setButtonCell(cellFactoryCategoria.call(null));
            categoria.setCellFactory(cellFactoryCategoria);
            categoria.getItems().setAll(data);
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
    }
    
    private void loadAllSucursales() {
        ArrayList<Sucursal> data = null;
        String urlWS = Constante.URL_BASE + "sucursal/all";
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Sucursal> >(){}.getType();
            data = gson.fromJson(resultadoWS, typeList);
            
            sucursales.setCellFactory(cellFactorySucursal);
            sucursales.getItems().setAll(data);
        }catch (Exception e){
            e.getStackTrace();
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
    }
    
    private void loadSucursales() {
        ArrayList<Sucursal> data = null;
        String urlWS = Constante.URL_BASE + "sucursal/allByEmpresa/" + Integer.toString(empresaSelector.getValue().getIdEmpresa());
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Sucursal> >(){}.getType();
            data = gson.fromJson(resultadoWS, typeList);
            
            sucursales.setCellFactory(cellFactorySucursal);
            sucursales.getItems().setAll(data);
        }catch (Exception e){
            e.getStackTrace();
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
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
    
    private ArrayList<Sucursal> getSucursalesByPromocionId(int idPromocion) {
        ArrayList<Sucursal> data = null;
        String urlWS = Constante.URL_BASE + "sucursal/allByPromocion/" + Integer.toString(idPromocion);
        
        System.out.println(urlWS);
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken< ArrayList<Sucursal> >(){}.getType();
            data = gson.fromJson(resultadoWS, typeList);
        }catch (Exception e){
            e.getStackTrace();
            Utilidades.mostrarAlertaSimple(
                "Error de conexion", 
                "Por el momento no se puedo cargar la lista", 
                Alert.AlertType.ERROR
            );
        }
        
        return data;
    }
}
