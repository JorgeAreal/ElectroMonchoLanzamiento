/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import static electromoncho.InterfazGrafica.frame;
import static electromoncho.InterfazGrafica.panelCentral;
import static electromoncho.InterfazGrafica.panelGeneral;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Jorge
 */
public class ListaMensajes {

    // Este panel mide exactamente igual que el panel central, solo que se pone por delante para que los botones siempre se vean encima del fondo
    private JPanel panelListaMensajes;

    private JLabel fondoMensajeria;
    private JLabel botonRefrescar;

    // La fuente que se utilizará para la lista de mensajes
    private Font fuentePersonalizada;

    // Esta matriz bidimensional va a contener todos los labels de la lista de mensajes
    // en [x][0] estará el ID
    // en [x][1] estará el usuario que puso el mensaje
    // en [x][2] estará la fecha
    // en [x][3] estará la hora
    // en [x][4] estará el mensaje completo
    // en [x][5] estará el titulo
    // en [x][6] estará el concepto (por ejemplo "producto defectuoso...)
    // en [x][7] estará el estado (pendiente, cerrado...)
    public static JLabel[][] mensajes;

    // Matriz de listeners donde se almacenarán cada uno de los mouse listeners de cada boton
    private MouseListener mouseListener[];

    public ListaMensajes() {
        // detectar los cambios de resolucion de ventana
        detectarCambioResolucionVentana();
        // Construir interfaz de la mensajeria
        configurarPaneles(); // configurar paneles
        colocarListaMensajes(true); // Llamar al metodo que coloca la lista de mensajes
        colocarElementosGenerales();

        establecerTamanioComponentes();

        panelGeneral.repaint();
        panelCentral.repaint();
    }

    private void configurarPaneles() {
        panelListaMensajes = new JPanel(null);
        panelListaMensajes.setOpaque(false);
        panelCentral.add(panelListaMensajes);
    }

    // Coloca los elementos que no sean los botones de los mensajes
    private void colocarElementosGenerales() {
        // Label del fondo
        fondoMensajeria = new JLabel();
        // Boton refrescar
        botonRefrescar = new JLabel();

        // Agregar el fondo al panel
        panelCentral.add(botonRefrescar);
        panelCentral.add(fondoMensajeria);

        // Atribuir la funcionalidad a estos elementos
        gestionarFuncionalidadesBasicas();
    }

    // Atribuye la funcionalidad de botones que no sean los de los mensajes
    private void gestionarFuncionalidadesBasicas() {
        // Boton refrescar: su funcion va a ser vaciar la lista de mensajes y volver a llenarla con nuevos datos conectandose de nuevo a la base de datos
        botonRefrescar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_refrescar_pressed.png", botonRefrescar, panelCentral.getWidth() - panelCentral.getWidth() / 5, panelCentral.getHeight() / 80, panelCentral.getWidth() / 7, panelCentral.getHeight() / 12);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_refrescar.png", botonRefrescar, panelCentral.getWidth() - panelCentral.getWidth() / 5, panelCentral.getHeight() / 80, panelCentral.getWidth() / 7, panelCentral.getHeight() / 12);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Vaciar las matrices implicadas en la lista de mensajes
                mensajes = null;
                mouseListener = null;
                // Llamar a la funcion que se encarga de volver a cargar los mensajes desde la base de datos y colocarlos en la lista de mensajes
                colocarListaMensajes(true);
            }
        });
    }

    // Estructura de la lista de labels mensajes[fila][columna]
    // en [x][0] estará el ID primaria de la tabla
    // en [x][1] estará el usuario que puso el mensaje
    // en [x][2] estará la fecha
    // en [x][3] estará la hora
    // en [x][4] estará el mensaje completo
    // en [x][5] estará el titulo
    // en [x][6] estará el concepto (por ejemplo "producto defectuoso...)
    // en [x][7] estará el estado (pendiente, cerrado...)
    // en [x][8] estará la imagen de fondo del boton
    // Este método tiene dos estados:
    // conexionBaseDatos = true: significa que se va a realizar una conexion a la base de datos para extraer los mensajes y los va a colocar
    // conexionBaseDatos = false: significa que se van a volver a colocar los mensajes pero sin comprobar novedades en la base de datos
    // el true se utiliza para el boton refrescar (para que salgan nuevos mensajes en ese caso) y el false se utiliza para cuando se haga un re-size
    private void colocarListaMensajes(boolean conexionBaseDatos) {
        panelListaMensajes.removeAll(); // Eliminar todos los componentes del panel
        panelListaMensajes.revalidate(); // Volver a validar el panel para que los cambios surtan efecto
        if (conexionBaseDatos) {
            // Llamar a la funcion que inicializa el label mensajes[][] con el contenido de la tabla de la base de datos
            // esta matriz de labels tiene todos los labels necesarios con la informacion de cada mensaje
            try {
                ConexionBBDD.crearLabelsListaPreguntas();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Hubo un error :/. Comprueba que tienes conexion a internet :D", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Inicializar matriz de mouse listeners de tamaño igual al numero de botones que va a haber
            // esta lista de mouseListeners almacena un mouseListener para cada boton que haya
            mouseListener = new MouseListener[mensajes.length];
        }
        // Crear una fuente personalizada
        int tamanioFuente = panelCentral.getWidth() / 53 + panelCentral.getHeight() / 80;
        fuentePersonalizada = new Font("Arial", Font.BOLD, tamanioFuente);
        // Esta variable crea un espacion en la coordenada "y" entre cada uno de los botones
        int distanciaEntreBotones = 0;
        for (int i = 0; i < mensajes.length; i++) {
            // Nombre de cliente
            int xNombreCliente = panelCentral.getWidth() / 11;
            int yNombreCliente = panelCentral.getHeight() / 4;
            int widthNombreCliente = panelCentral.getWidth() / 3;
            int heightNombreCliente = panelCentral.getHeight() / 45;
            panelListaMensajes.add(mensajes[i][1]);
            mensajes[i][1].setFont(fuentePersonalizada);
            mensajes[i][1].setBounds(xNombreCliente, yNombreCliente + distanciaEntreBotones, widthNombreCliente, heightNombreCliente);

            // Fecha
            int xFecha = panelCentral.getWidth() / 2 - panelCentral.getWidth() / 10;
            int yFecha = panelCentral.getHeight() / 4;
            int widthFecha = panelCentral.getWidth() / 4;
            int heightFecha = panelCentral.getHeight() / 45;
            panelListaMensajes.add(mensajes[i][2]);
            mensajes[i][2].setFont(fuentePersonalizada);
            mensajes[i][2].setBounds(xFecha, yFecha + distanciaEntreBotones, widthFecha, heightFecha);

            // Hora
            int xHora = panelCentral.getWidth() / 2 + panelCentral.getWidth() / 5;
            int yHora = panelCentral.getHeight() / 4;
            int widthHora = panelCentral.getWidth() / 6;
            int heightHora = panelCentral.getHeight() / 45;
            panelListaMensajes.add(mensajes[i][3]);
            mensajes[i][3].setFont(fuentePersonalizada);
            mensajes[i][3].setBounds(xHora, yHora + distanciaEntreBotones, widthHora, heightHora);

            // Título
            int xTitulo = panelCentral.getWidth() / 10;
            int yTitulo = panelCentral.getHeight() / 6;
            int widthTitulo = panelCentral.getWidth() / 2;
            int heightTitulo = panelCentral.getHeight() / 30;
            panelListaMensajes.add(mensajes[i][5]);
            mensajes[i][5].setFont(fuentePersonalizada);
            mensajes[i][5].setBounds(xTitulo, yTitulo + distanciaEntreBotones, widthTitulo, heightTitulo);

            // Estado
            int xEstado = panelCentral.getWidth() - panelCentral.getWidth() / 3;
            int yEstado = panelCentral.getHeight() / 6;
            int widthEstado = panelCentral.getWidth() / 4;
            int heightEstado = panelCentral.getHeight() / 30;
            panelListaMensajes.add(mensajes[i][7]);
            mensajes[i][7].setFont(fuentePersonalizada);
            mensajes[i][7].setBounds(xEstado, yEstado + distanciaEntreBotones, widthEstado, heightEstado);

            // Imagen boton
            int xBotonChat = (panelCentral.getWidth() - (panelCentral.getWidth() - panelCentral.getWidth() / 9)) / 2;
            int yBotonChat = panelCentral.getHeight() / 8;
            int widthBotonChat = panelCentral.getWidth() - panelCentral.getWidth() / 9;
            int heightBotonChat = panelCentral.getHeight() / 6;
            panelListaMensajes.add(mensajes[i][8]);
            InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo_mensaje2.png", mensajes[i][8], xBotonChat, yBotonChat + distanciaEntreBotones, widthBotonChat, heightBotonChat);
            // Si conexionBaseDatos = false significa que 
            if (!conexionBaseDatos) {
                // Elimino el mouse listener actual para volver a colocarselo despues
                mensajes[i][8].removeMouseListener(mouseListener[i]);
            }
            // Llamo a la funcion que agrega la funcionalidad a cada boton. Para ello le mando el indice "i" actual para colocarle un mouse adapter al label y añadir ese mouse adapter a la lista de adapters, el label de la imagen del boton al que quiero colocar dicha funcionalidad, le mando el ID (primary key) del mensaje para que despues sea leido en la base de datos, y le mando a qué altura está el boton para que al cambiar de imagen se refresque correctamente.
            atribuirFuncionalidadesBotonesMensajes(i, mensajes[i][8], mensajes[i][0], distanciaEntreBotones);

            // Agregar espacio para el siguiente boton
            distanciaEntreBotones += panelCentral.getHeight() / 6;
        }
        // Despues de todo el proceso, hacer repaint por si no se actualiza bien el panel visualmente
        panelListaMensajes.repaint();
        // Forzar la liberacion en memoria, ya que en el bucle for de antes hemos interaccionado con muchas variables que solo son necesarias durante dichas operaciones
        Runtime garbage = Runtime.getRuntime();
        garbage.gc();
    }

    // Este método recibe como parámetros el label de la imagen del mensaje y el label que contiene el valor de la primary key
    // agrega la funcionalidad a cada uno de los botones de mensajes
    private void atribuirFuncionalidadesBotonesMensajes(int i, JLabel labelImagenMensaje, JLabel labelID, int distanciaEntreBotones) {
        // labelID es un label que contiene el valor numero del primary key de la pregunta en la base de datos.
        // lo que hago es exatrer ese texto del label y convertirlo en numero entero para poder trabajar con el
        int ID = Integer.parseInt(labelID.getText());

        // Medidas de la imagen del boton
        int xBotonChat = (panelCentral.getWidth() - (panelCentral.getWidth() - panelCentral.getWidth() / 9)) / 2;
        int yBotonChat = panelCentral.getHeight() / 8;
        int widthBotonChat = panelCentral.getWidth() - panelCentral.getWidth() / 9;
        int heightBotonChat = panelCentral.getHeight() / 6;

        // Agregarle la funcionalidad
        mouseListener[i] = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo_mensaje2pressed.png", labelImagenMensaje, xBotonChat, yBotonChat + distanciaEntreBotones, widthBotonChat, heightBotonChat);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo_mensaje2.png", labelImagenMensaje, xBotonChat, yBotonChat + distanciaEntreBotones, widthBotonChat, heightBotonChat);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("Este mensaje tiene id: " + ID);
                Chat.chatActual = null;
                try {
                    ConexionBBDD.consultarRespuestasPorID(ID);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Hubo un error :/. Comprueba que tienes conexion a internet :D", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        // Agregar el mouseAdapter al label
        labelImagenMensaje.addMouseListener(mouseListener[i]);
    }

    // Restaura el tamaño de los componentes de la lista de mensajes
    private void establecerTamanioComponentes() {
        // Tamaño panel central donde se van a colocar los componentes
        panelListaMensajes.setBounds(0, 0, panelCentral.getWidth(), panelCentral.getHeight());
        // Tamaño del fondo de mensajeria
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo_mensajeria.png", fondoMensajeria, 0, 0, panelCentral.getWidth(), panelCentral.getHeight());
        // Tamaño del boton resfrescar
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_refrescar.png", botonRefrescar, panelCentral.getWidth() - panelCentral.getWidth() / 5, panelCentral.getHeight() / 80, panelCentral.getWidth() / 7, panelCentral.getHeight() / 12);
    }

    // Este método es llamado una vez por el constructor, y lo que hace es hacer que el frame detecte el cambio de resolucion
    // para que cada vez que se cambie la resolucion, los componentes se adapten al nuevo tamaño
    private void detectarCambioResolucionVentana() {
        frame.addComponentListener(new ComponentAdapter() {
            // Declarar variable timer
            private Timer timer;

            @Override
            public void componentResized(ComponentEvent e) {
                //  Cada vez que se dispara el evento componentResized, se detiene el temporizador actual (si está en ejecución) y se inicia uno nuevo
                if (timer != null && timer.isRunning()) {
                    timer.stop(); // Detener el temporizador si ya está en ejecución
                }
                timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        establecerTamanioComponentes(); // Llamada al método de actualización de componentes que no son los mensajes
                        colocarListaMensajes(false); // Llamada al método de actualizacion de los botones de los mensajes
                    }
                });
                timer.setRepeats(false); // Solo una ejecución del temporizador
                timer.start(); // Iniciar el temporizador
            }
        });
    }
}
