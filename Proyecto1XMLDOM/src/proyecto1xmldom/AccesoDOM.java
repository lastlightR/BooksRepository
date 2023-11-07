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
            System.out.println("Añadir libro al árbol DOM de título: " + title);
            
            String ultimoId = obtenerUltimoId();
            String [] partes = ultimoId.split("k",3);
            int nuevoNum = Integer.parseInt(partes[1]) +1; //incrementamos el número del ID
            String nuevoId = "bk" + nuevoNum;
            
            //nodo title
            Node ntitle = documento.createElement("title");//crea etiquetas
            Node ntitle_text = documento.createTextNode(title);//crea el nodo texto
            ntitle.appendChild(ntitle_text);//añade el titulo a las etiquetas
            //nodo author
            Node nauthor = documento.createElement("author");
            Node nauthor_text = documento.createTextNode(author);
            nauthor.appendChild(nauthor_text);
            //nodo publish_date
            Node npublish_date = documento.createElement("publish_date");
            Node npublish_date_text = documento.createTextNode(publish_date);
            npublish_date.appendChild(npublish_date_text);
            //nodo genre
            Node ngenre = documento.createElement("genre");
            Node ngenre_text = documento.createTextNode(genre);
            ngenre.appendChild(ngenre_text);
            //nodo description
            Node ndescription = documento.createElement("description");
            Node ndescription_text = documento.createTextNode(description);
            ndescription.appendChild(ndescription_text);
            //nodo price
            Node nprice = documento.createElement("price");
            Node nprice_text = documento.createTextNode(price.toString());
            nprice.appendChild(nprice_text);

            //crea book con atributo ID y los nodos anteriores
            Node nbook = documento.createElement("book");
            ((Element) nbook).setAttribute("id", nuevoId);
            nbook.appendChild(ntitle);
            nbook.appendChild(nauthor);
            nbook.appendChild(npublish_date);
            nbook.appendChild(ngenre);
            nbook.appendChild(ndescription);
            nbook.appendChild(nprice);
            
            //añade el book al árbol DOM
            nbook.appendChild(documento.createTextNode("\n"));
            Node raiz = documento.getFirstChild();
            raiz.appendChild(nbook);
            System.out.println("Libro insertado al DOM");
            return 0;

        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }

    }

    public int eliminarLibro(String title) {
        System.out.println("Buscando el libro de título " + title + " para borrarlo.");

        try {
            Node raiz = documento.getDocumentElement(); //obtenemos la raíz del DOM
            NodeList nodeList = documento.getElementsByTagName("title"); //obtenemos la lista de nodos "title"
            Node node;
            boolean borrado = false;
            for (int i = 0; i < nodeList.getLength(); i++) { //recorremos la lista hasta llegar al título
                node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    if (node.getChildNodes().item(0).getNodeValue().equals(title)) {

                        System.out.println("Borrando el nodo book con título: " + title);
                        //volvemos hacia atrás desde título hasta llegar al libro y borrarlo
                        node.getParentNode().getParentNode().removeChild(node.getParentNode());
                        System.out.println("Libro borrado");
                        borrado = true;
                    }
                }
            }
            if (borrado == false) //comprobamos si no se ha encontrado nada
                System.out.println("No se ha encontrado el libro.");

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
            //int length = nodeList.getLength();
            Node ultimoLibro = nodeList.item(nodeList.getLength()-1); //buscamos el último nodo de la lista
            ultimoId = ((Element) ultimoLibro).getAttribute("id");
            
            /*
            if (length > 0) {
                Node ultimoLibro = nodeList.item(length - 1);
                String idStr = ((Element) ultimoLibro).getAttribute("id");
                ultimoId = idStr;
            }*/
            
        } catch (NumberFormatException ex) {
            //manejar la conversión de String a int si es necesario
            System.out.println("Error del formato del ID: "+ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
            ex.printStackTrace();
        }

        return ultimoId;
    }
    
    public void pedirUsuario(){
        String title, author,  publish_date,
         genre, description;
        Double price;
        Scanner teclado = new Scanner (System.in);
        
        //pedimos por pantalla los datos del nuevo nodo al usuario
        System.out.println("\n-- Introduce los datos del nuevo nodo --" );
        System.out.println("- Title:" );
        title = teclado.nextLine();
        
        System.out.println("- Author:" );
        author = teclado.nextLine();
        
        System.out.println("- Publish date:");
        publish_date = teclado.nextLine();
        
        System.out.println("- Genre:");
        genre = teclado.nextLine();
        
        System.out.println("- Description:" );
        description = teclado.nextLine();
        
        System.out.println("- Price: ");
        try { //comprobamos que se introduce un valor correcto y no un texto al valor price
            price = teclado.nextDouble();
            //llamamos a la función para añadir el nuevo libro al árbol DOM
            anadirEnDOM(title, author, publish_date, genre, description, price);
        } catch (InputMismatchException ex) {
            System.out.println("El precio no tiene un valor numérico Double. Error: "+ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
            ex.printStackTrace();
        }
      
    }
    
    public void guardarArchivo (String archivo) {
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
