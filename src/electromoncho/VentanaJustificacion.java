/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Jorge Areal Alberich
 */
public class VentanaJustificacion extends JDialog {
    // Panel donde se colocaran todos los elementos
    private JPanel panelGeneralJustificante;
    // Elementos que se colocaran dentro del panel
    private JLabel letreroLlegarTarde;
    private JLabel letreroJustificaFalta;
    private JLabel resetti;
    private JTextArea areaJustificacion;
    private JLabel botonEnviar;
    // Variable que guardará la justificacion
    private String justificacion;
    
    // Variable para saber si el fichado ya estaba o no pulsado
    private boolean isFichadoPresionado;
    // Variable que almacena el texto que se va a poner segun si llega tarde o si sale temprano
    String pregunta;

    // Getter para obtener desde otra clase el texto de justificacion introducido por el usuario
    public String getJustificacion() {
        return justificacion;
    }
    
    // Constructor
    public VentanaJustificacion(boolean isFichadoPresionado) {
        super(InterfazGrafica.frame, "Justificante", true); // 'true' para hacerlo modal
        this.isFichadoPresionado = isFichadoPresionado;
        // Construir la interfaz grafica
        configurarFrame();
        configurarPaneles();
        colocarElementos();
        // Inicializar la variable con una cadena de texto vacia
        justificacion = "";
        setVisible(true);
    }

    private void configurarFrame() {
        setSize(500, 400); // Tamaño del JFrame
        setLayout(null);
        setLocationRelativeTo(InterfazGrafica.frame); // Centro la ventana
        setResizable(false);
        // Inicializo la variable que contiene la imagen del icono de la base de datos
        ImageIcon iconoPrincipal = new ImageIcon("recursos\\logo-empresa.png");
        // Aplico la variable que contiene la imagen del icono y la pongo como icono del frame
        setIconImage(iconoPrincipal.getImage()); // Colocar logo utilizando la variable icono creada anteriormente
    }

    private void configurarPaneles() {
        panelGeneralJustificante = new JPanel(null);
        panelGeneralJustificante.setBackground(Color.RED);
        panelGeneralJustificante.setBounds(0, 0, getWidth(), getHeight());
        add(panelGeneralJustificante);
    }

    private void colocarElementos() {
        // Elementos graficos del panel
        letreroLlegarTarde = new JLabel();
        // Si el fichado estaba pulsado, mostrar un texto distinto a si no estuviera pulsado
        if (isFichadoPresionado){
            pregunta = "recursos/texto/salir_temprano.png";
        } else {
            pregunta = "recursos/texto/llegar_tarde.png";
        }
        // Colocar las dimensiones y posicion para el label utilizando la variable que hemos gestionado
        InterfazGrafica.calcularNuevoTamanioImagen(pregunta, letreroLlegarTarde, 180, 10, 300, 140);
        // Resetti
        resetti = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/resetti_sinfondo.png", resetti, 0, 0, 200, 150);
        // Letrero que dice "justifica tu falta"
        letreroJustificaFalta = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/texto/justifica_tu_falta.png", letreroJustificaFalta, centrarElemento(270), 140, 270, 80);
        // Crear área de texto para justificación
        areaJustificacion = new JTextArea();
        areaJustificacion.setLineWrap(true);
        areaJustificacion.setWrapStyleWord(true);
        // Crear un JScrollPane para el JTextArea
        JScrollPane scrollPane = new JScrollPane(areaJustificacion);
        scrollPane.setBounds(centrarElemento(400), 210, 400, 80);
        // Boton enviar
        botonEnviar = new JLabel();
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/enviar_justificante.png", botonEnviar, centrarElemento(200), 305, 200, 50);

        // Agregar elementos al panel
        panelGeneralJustificante.add(letreroLlegarTarde);
        panelGeneralJustificante.add(resetti);
        panelGeneralJustificante.add(letreroJustificaFalta);
        panelGeneralJustificante.add(scrollPane);
        panelGeneralJustificante.add(botonEnviar);
        // Llamar a la funcion que se encarga de darle las funciones correspondientes a los botones
        agregarFuncionalidadesBotones();
    }

    // Recibe el ancho del label que necesitas y devuelve la cantidad exacta de pixeles que necesita ese label para estar en el centro del frame, aplicando una formula matematica
    private int centrarElemento(int anchoElemento) {
        int x = (getWidth() - anchoElemento) / 2;
        return x;
    }

    private void agregarFuncionalidadesBotones() {
        // BOTON ENVIAR
        botonEnviar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/enviar_justificante_pressed.png", botonEnviar, centrarElemento(200), 305, 200, 50);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/enviar_justificante.png", botonEnviar, centrarElemento(200), 305, 200, 50);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Aquí puedes manejar la justificación, por ejemplo, guardarla en la base de datos
                //System.out.println("Justificación enviada: " + areaJustificacion.getText());
                justificacion = areaJustificacion.getText();
                // Cerrar la ventana después de enviar la justificación
                dispose();
            }
        });
    }
}
