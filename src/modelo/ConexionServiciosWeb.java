/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.google.gson.Gson;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author andre
 */
public class ConexionServiciosWeb {
    public static String peticionServicioGET(String url) throws IOException{
        String resultado = "";
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("GET");
        
        //Realizamos la invocación del servicio
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta obtenido en petición: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón GET con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    public static String peticionServicioPOST(String url, String parametros) throws IOException{
        String resultado = "";
        
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("POST");
        conexionHTTP.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexionHTTP.setDoOutput(true);
        OutputStream outputSalida = conexionHTTP.getOutputStream();
        outputSalida.write(parametros.getBytes());
        outputSalida.flush();
        outputSalida.close();
        
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón POST con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    public static String peticionServicioPOSTJson(String url, String parametros) throws IOException{
        String resultado = "";
        
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("POST");
        conexionHTTP.setRequestProperty("Content-Type", "application/json");
        conexionHTTP.setDoOutput(true);
        OutputStream outputSalida = conexionHTTP.getOutputStream();
        outputSalida.write(parametros.getBytes());
        outputSalida.flush();
        outputSalida.close();
        
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón POST con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    public static String peticionServicioPOSTImage(String url, byte[] body) throws IOException{
        String resultado = "";
        
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("POST");
        conexionHTTP.setRequestProperty("Content-Type", "multipart/form-data");
        conexionHTTP.setDoOutput(true);
        OutputStream outputSalida = conexionHTTP.getOutputStream();
        outputSalida.write(body);
        outputSalida.flush();
        outputSalida.close();
        
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón POST con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    public static String peticionServicioPUT(String url, String parametros) throws IOException{
        String resultado = "";
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("PUT");
        conexionHTTP.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexionHTTP.setDoOutput(true);
        OutputStream outputSalida = conexionHTTP.getOutputStream();
        outputSalida.write(parametros.getBytes());
        outputSalida.flush();
        outputSalida.close();
        
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón PUT con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    public static String peticionServicioPUTJson(String url, String parametros) throws IOException{
        String resultado = "";
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("PUT");
        conexionHTTP.setRequestProperty("Content-Type", "application/json");
        conexionHTTP.setDoOutput(true);
        OutputStream outputSalida = conexionHTTP.getOutputStream();
        outputSalida.write(parametros.getBytes());
        outputSalida.flush();
        outputSalida.close();
        
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón PUT con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    public static String peticionServicioDELETE(String url, String parametros) throws IOException{
        String resultado = "";
        URL urlAcceso = new URL(url);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlAcceso.openConnection();
        conexionHTTP.setRequestMethod("DELETE");
        conexionHTTP.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexionHTTP.setDoOutput(true);
        OutputStream outputSalida = conexionHTTP.getOutputStream();
        outputSalida.write(parametros.getBytes());
        outputSalida.flush();
        outputSalida.close();
        
        int codigoRespuesta = conexionHTTP.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);
        if(codigoRespuesta == HttpURLConnection.HTTP_OK){
            resultado = convierteStreamCadena(conexionHTTP.getInputStream());
        }else{
            resultado = "Error en la peticón DELETE con código: " + codigoRespuesta;
        }
        return resultado;
    }
    
    private static String convierteStreamCadena(InputStream streamServicio) throws IOException{
         InputStreamReader isr = new InputStreamReader(streamServicio);
         BufferedReader in = new BufferedReader(isr);
         String inputLine;
         StringBuilder response = new StringBuilder();
         
         while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            return response.toString();
    }
}
