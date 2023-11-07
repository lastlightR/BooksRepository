/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1xmldom;

import java.io.File;

/**
 *
 * @author Robyn & Jade
 */
public class Main {

    public static void main(String[] args) {
        AccesoDOM acceso = new AccesoDOM();
        File file = new File("./books.xml");
        //añadir aquí llamadas a métodos de AccesoDOM
        acceso.crearDOM(file);
        acceso.eliminarLibro("Paradox Lost");
        acceso.anadirEnDOM("La vida de Jade", "Robyn", "2002-06-11", "Drama", "Una apasionante aventura" , 89);
    }
    
}
