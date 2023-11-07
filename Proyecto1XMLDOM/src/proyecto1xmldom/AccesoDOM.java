/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1xmldom;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.io.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Robyn & Jade
 */
public class AccesoDOM {
    
    Document documento;
    
    public int crearDOM (File file){
        try{
            System.out.println("Abriendo el XML para crear el DOM.....");

            //creamos nuevo objeto DocumentBuilder al que apunta la variable factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            //ignorar comentarios y espacios blancos
            dbf.setIgnoringComments(true);
            dbf.setIgnoringElementContentWhitespace(true);
            
            //DocumentBuilder.parse genera el DOM en memoria
            DocumentBuilder builder = dbf.newDocumentBuilder();
            documento = builder.parse(file);
            
            //ahora doc apunta al árbol DOM y podemos recorrerlo
            System.out.println("El DOM ha sido creado con éxito.\n");
            return 0;
        } catch (FileNotFoundException ex){
            System.out.println("El archivo no ha sido encontrado. Error: "+ex);
            ex.printStackTrace();
            return -1;
        } catch(Exception ex){
            System.out.println("Error" +ex);
            ex.printStackTrace();
            return -1;
        }
    }
    
}
