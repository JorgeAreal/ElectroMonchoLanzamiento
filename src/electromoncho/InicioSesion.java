/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import static electromoncho.InterfazGrafica.frame;
import static electromoncho.InterfazGrafica.panelGeneral;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Jorge
 */
public class InicioSesion {

    private JLabel fondoInicioSesion;
    private JLabel textoQuienEres;

    private JLabel botonCerrar;
    private JLabel botonCrearUsuario;

    // Constructor
    public InicioSesion(boolean primeraVez) {
        // Cuando se carga la aplicacion por primera vez, llamar a las funciones que abren el frame y configuran el panel principal
        // si se vuelve al menú desde otro punto del programa, no llamar a las funciones que configuran el frame y el panel
        if (primeraVez) {
            configurarFrame();
            configurarPaneles();
        }
        colocarElementosMenu();

        establecerTamanioComponentes();
        detectarCambioResolucionVentana();
        panelGeneral.add(fondoInicioSesion);
        frame.setVisible(true);
    }

    private void configurarFrame() {
        frame = new JFrame("ElectroMoncho S.A");
        frame.setSize(1280, 720); // Tamaño del JFrame
        frame.setLayout(null);
        frame.setLocationRelativeTo(null); // Centro la ventana

        // Inicializo la variable que contiene la imagen del icono de la base de datos
        ImageIcon iconoPrincipal = new ImageIcon("recursos\\logo-empresa.png");
        // Aplico la variable que contiene la imagen del icono y la pongo como icono del frame
        frame.setIconImage(iconoPrincipal.getImage()); // Colocar logo utilizando la variable icono creada anteriormente
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configurarPaneles() {
        panelGeneral = new JPanel(null);
        panelGeneral.setVisible(true);
        frame.add(InterfazGrafica.panelGeneral);
    }

    private void colocarElementosMenu() {
        fondoInicioSesion = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondoLoopHorizontal.gif");
        textoQuienEres = InterfazGrafica.crearImagenLabelSinEscalar("recursos/texto/bienvenido.png");
        botonCerrar = InterfazGrafica.crearImagenLabelSinEscalar("recursos/botones/cruz.png");

        botonCrearUsuario = InterfazGrafica.crearImagenLabelSinEscalar("recursos/botones/botonAgregarUsuario.png");

        // Agregar todos los elementos a su panel correspondiente
        panelGeneral.add(textoQuienEres);
        panelGeneral.add(botonCerrar);
        panelGeneral.add(botonCrearUsuario);

        // Llamar a la funcion de gestionar lo que van a hacer los botones
        gestionarFuncionalidadesBotones();
    }

    private void gestionarFuncionalidadesBotones() {
        // BOTON CERRAR
        botonCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cruz2_pressed.png", botonCerrar, 0, panelGeneral.getHeight() / 500, panelGeneral.getWidth() / 16, panelGeneral.getHeight() / 9);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cruz2.png", botonCerrar, 0, panelGeneral.getHeight() / 500, panelGeneral.getWidth() / 16, panelGeneral.getHeight() / 9);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Cerrar aplicación
                System.exit(0);
            }
        });
        // BOTON CREAR USUARIO
        botonCrearUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/botonAgregarUsuario_pressed.png", botonCrearUsuario, (panelGeneral.getWidth() - (panelGeneral.getWidth() / 16 + panelGeneral.getWidth() / 20)) / 2, (panelGeneral.getHeight() - (panelGeneral.getHeight() / 9 + panelGeneral.getWidth() / 20)) / 2, panelGeneral.getWidth() / 16 + panelGeneral.getWidth() / 20, panelGeneral.getHeight() / 9 + panelGeneral.getWidth() / 20);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/botonAgregarUsuario.png", botonCrearUsuario, (panelGeneral.getWidth() - (panelGeneral.getWidth() / 16 + panelGeneral.getWidth() / 20)) / 2, (panelGeneral.getHeight() - (panelGeneral.getHeight() / 9 + panelGeneral.getWidth() / 20)) / 2, panelGeneral.getWidth() / 16 + panelGeneral.getWidth() / 20, panelGeneral.getHeight() / 9 + panelGeneral.getWidth() / 20);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Eliminar los componentes que no necesito para la siguiente pantalla
                panelGeneral.removeAll();
                //panelGeneral.remove(botonCerrar);
                //panelGeneral.remove(botonCrearUsuario);
                //panelGeneral.remove(textoQuienEres);
                // Llamar a la clase que muestre la pantalla de introduccion de datos
                IntroducirCuenta introducirCuenta = new IntroducirCuenta();
            }
        });
    }

    private void establecerTamanioComponentes() {
        // Panel general
        panelGeneral.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // Fondo de pantalla
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoLoopHorizontal.gif", fondoInicioSesion, 0, 0, frame.getWidth(), frame.getHeight());
        // Texto "Quien Eres"?
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/texto/bienvenido.png", textoQuienEres, (panelGeneral.getWidth() - (panelGeneral.getWidth() / 3)) / 2, panelGeneral.getHeight() / 20, panelGeneral.getWidth() / 3, panelGeneral.getHeight() / 4);
        // Boton Cerrar
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cruz2.png", botonCerrar, 0, panelGeneral.getHeight() / 500, panelGeneral.getWidth() / 16, panelGeneral.getHeight() / 9);

        // Boton crear usuario
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/botonAgregarUsuario.png", botonCrearUsuario, (panelGeneral.getWidth() - (panelGeneral.getWidth() / 16 + panelGeneral.getWidth() / 20)) / 2, (panelGeneral.getHeight() - (panelGeneral.getHeight() / 9 + panelGeneral.getWidth() / 20)) / 2, panelGeneral.getWidth() / 16 + panelGeneral.getWidth() / 20, panelGeneral.getHeight() / 9 + panelGeneral.getWidth() / 20);
    }
    
    // Este método es llamado una vez por el constructor, y lo que hace es hacer que el frame detecte el cambio de resolucion
    // para que cada vez que se cambie la resolucion, los componentes se adapten al nuevo tamaño
    private void detectarCambioResolucionVentana(){
        // Agregar un ComponentListener para escuchar los cambios en el tamaño de la ventana
        // Se utiliza un timer para controlar el flujo de llamamientos al metodo que gestiona el tamaño de los elemtntos
        // Este es el encargado de llamar al método que realice los calculos necesarios para reestablecer el tamaño de los componentes a partir del tamaño de la ventana
        frame.addComponentListener(new ComponentAdapter() {
            // Declarar variable timer
            private Timer timer;

            @Override
            public void componentResized(ComponentEvent e) {
                //  Cada vez que se dispara el evento componentResized, se detiene el temporizador actual (si está en ejecución) y se inicia uno nuevo
                if (timer != null && timer.isRunning()) {
                    timer.stop(); // Detener el temporizador si ya está en ejecución
                }
                timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        establecerTamanioComponentes(); // Llamada al método de actualización
                    }
                });
                timer.setRepeats(false); // Solo una ejecución del temporizador
                timer.start(); // Iniciar el temporizador
            }
        });
    }
}
