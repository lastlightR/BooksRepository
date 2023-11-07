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
     public int anadirEnDOM(String title, String author, String publish_date,
            String genre, String description, Double price) {
        try {
            System.out.println("Añadir libro al árbol DOM de title:" + title);
            
            String ultimoId = obtenerUltimoId();
            String [] partes = ultimoId.split("k",3);
            String nuevoId = "bk" + partes[1];
            
            Node ntitle = documento.createElement("title");//crea etiquetas
            Node ntitle_text = documento.createTextNode(title);//crea el nodo texto
            ntitle.appendChild(ntitle_text);//añade el titulo a las
            Node nauthor = documento.createElement("author");
            Node nauthor_text = documento.createTextNode(author);
            nauthor.appendChild(nauthor_text);
            Node npublish_date = documento.createElement("publish_date");
            Node npublish_date_text = documento.createTextNode(publish_date);
            npublish_date.appendChild(npublish_date_text);
            Node ngenre = documento.createElement("genre");
            Node ngenre_text = documento.createTextNode(genre);
            ngenre.appendChild(ngenre_text);
            Node ndescription = documento.createElement("description");
            Node ndescription_text = documento.createTextNode(description);
            ndescription.appendChild(ndescription_text);
            Node nprice = documento.createElement("price");
            Node nprice_text = documento.createTextNode(price.toString());
            nprice.appendChild(nprice_text);

            //CREA LIBRO, con atributo y nodos Título y Autor
            Node nbook = documento.createElement("book");
            ((Element) nbook).setAttribute("id",nuevoId);
            nbook.appendChild(ntitle);
            nbook.appendChild(nauthor);
            nbook.appendChild(npublish_date);
            nbook.appendChild(ngenre);
            nbook.appendChild(ndescription);
            nbook.appendChild(nprice);
            nbook.appendChild(documento.createTextNode("\n"));
            Node raiz = documento.getFirstChild();
            raiz.appendChild(nbook);
            System.out.println("Libro insertado DOM");
            return 0;

        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }

    }

    public int eliminarLibro(String title) {
        System.out.println("Buscando el Libro" + title + "para borrarlo");

        try {
            Node raiz = documento.getDocumentElement();
            NodeList nl1 = documento.getElementsByTagName("title");
            Node n1;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);

                if (n1.getNodeType() == Node.ELEMENT_NODE) {

                    if (n1.getChildNodes().item(0).getNodeValue().equals(title)) {

                        System.out.println("Borrando el nodo Libro con título: " + title);
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());

                    }
                }
            }
            System.out.println("Libro borrado");

            return 0;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
    }

    private String obtenerUltimoId() {
        String ultimoId = "";

        try {
            NodeList nodeList = documento.getElementsByTagName("book");
            int length = nodeList.getLength();
            Node ultimoLibro = nodeList.item(nodeList.getLength()-1);
            ultimoId =  ((Element) ultimoLibro).getAttribute("id");
            
            /*
            if (length > 0) {
                Node ultimoLibro = nodeList.item(length - 1);
                String idStr = ((Element) ultimoLibro).getAttribute("id");
                ultimoId = idStr;
            }*/
            
        } catch (NumberFormatException e) {
            // Manejar la conversión de String a int si es necesario
            e.printStackTrace();
        }

        return ultimoId;
    }
    public void pedirUsuario(){
        String title, author,  publish_date,
         genre, description;
        Double price;
        Scanner teclado = new Scanner (System.in);
        System.out.println("Introduce los datos del nuevo nodo:" );
        System.out.println("Title:" );
        title = teclado.nextLine();
        System.out.println("Author:" );
        author = teclado.nextLine();
        System.out.println("Publish date:");
        publish_date = teclado.nextLine();
        System.out.println("Genre:");
        genre = teclado.nextLine();
        System.out.println("Description:" );
        description = teclado.nextLine();
        System.out.println("Price: ");
        price = teclado.nextDouble();
        anadirEnDOM(title, author, publish_date, genre, description, price);
      
    }
    
    public void guardarDOMaArchivo (String archivo) {
        try{
            Source source = new DOMSource(documento); //origen
            StreamResult result = new StreamResult (new File(archivo)); //destino
            //declarando el Transformer con el método transform que vamos a usar
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //propiedad para darle una sangría al archivo
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, (javax.xml.transform.Result) result);
            
            System.out.println("Archivo creado con éxito.");
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
            ex.printStackTrace();
        }
    }
}
