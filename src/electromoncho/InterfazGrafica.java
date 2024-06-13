/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jorge
 */
public class InterfazGrafica {
    // Variables Interfaz Grafica generales
    public static JFrame frame;
    public static JPanel panelGeneral;
    
    // Variables Interfaz Grafica del menu de usuario
    public static JPanel panelIzquierdo;
    public static JPanel panelCentral;
    public static JPanel panelDerecho;

    public static JLabel crearImagenLabelSinEscalar(String direccionGif) {
        Icon icon = new ImageIcon(direccionGif);
        JLabel label = new JLabel(icon);
        return label;
    }
    
    // Este método es llamado para cambiar el tamaño a cualquier imagen que le llegue por parametros
    /* Recibe urlImagen, label que se quiere adaptar, posicion y tamaño del label en cuestion */
    public static void calcularNuevoTamanioImagen(String urlImagen, JLabel labelAVonvertir, int x, int y, int width, int height) {
        // Actualizar el tamaño del label proporcionalmente al tamaño de la ventana
        ImageIcon icono = new ImageIcon(urlImagen);
        Image imagen = icono.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        labelAVonvertir.setIcon(new ImageIcon(imagen));
        labelAVonvertir.setBounds(x, y, width, height);
    }
}
