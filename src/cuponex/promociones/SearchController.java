/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuponex.promociones;

import cuponex.sucursales.*;
import cuponex.empresas.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cuponex.usuarios.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import modelo.ConexionServiciosWeb;
import pojos.Categoria;
import pojos.Empresa;
import pojos.Promocion;
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
    private ComboBox<Empresa> empresaSelector;
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
    private ListView<Sucursal> sucursales;
    
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
    
    /**
     * Initializes the controller class.
     */
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
        
    }    
    
    public boolean initData(String searchQuery, String searchType) {
        // Cargar info en combobox
        this.loadEmpresas();
        this.loadCategorias();
        this.loadAllSucursales();
        
        searchQuery = searchQuery.replace(" ", "-");
        if (searchType.equals("nombre")) {
            return this.search("byName/"+searchQuery);
        }
        
        if (searchType.equals("fecha-de-inicio")) {
            return this.search("byFechaIn/"+searchQuery);
        }
        
        return this.search("byFechaFn/"+searchQuery);
    }

    private boolean search(String query) {
        System.out.print(query);
        Promocion data = fetch(query);
        
        if (data == null) {
            Utilidades.mostrarAlertaSimple(
                "Error", 
                "No se encontró la empresa", 
                Alert.AlertType.ERROR
            );
            
            return false;
        }
        
        System.out.println(data.getIdPromocion());
        
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
            return false;
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
                System.out.println(sucursales.getItems());
                while (sucursalList.hasNext()) {
                    if (sucursalList.next().getIdSucursal()== selectedSucursales.get(i).getIdSucursal()) {
                        arr[arrIndex] = index;
                        arrIndex++;
                        break;
                    }

                    index++;
                }
            }
            
            this.sucursales.getSelectionModel().selectIndices(arr[0], arr);
            
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
        
        return true;
    }
    
    private Promocion fetch(String query) {
        Promocion data = null;
        String urlWS = Constante.URL_BASE + "promocion/" + query;
        
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type typeList = new TypeToken<Promocion>(){}.getType();
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

    @FXML
    private void empresaSelectorHandler(ActionEvent event) {
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
}
