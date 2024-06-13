/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package electromoncho;

/**
 *
 * @author Jorge
 */
public class ElectroMoncho {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Lo inicio en true porque es la primera vez que se carga la aplicacion
        // true: inicializa el frame y el panel general, ademas del resto de componentes de la pantalla inicial
        // false:  no inicializa el frame y panel general, pero si todo lo demas
        InicioSesion inicioSesion = new InicioSesion(true);
    }
}