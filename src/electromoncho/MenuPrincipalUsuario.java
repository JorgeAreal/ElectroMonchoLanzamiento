/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import static electromoncho.InterfazGrafica.frame;
import static electromoncho.InterfazGrafica.panelGeneral;
import static electromoncho.InterfazGrafica.panelCentral;
import static electromoncho.InterfazGrafica.panelDerecho;
import static electromoncho.InterfazGrafica.panelIzquierdo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Jorge
 */
public class MenuPrincipalUsuario {

    private JLabel fondoPanelIzquierda;
    private JLabel logoElectroMoncho;

    private JLabel botonMensajeria;
    private JLabel fondoBotonMensajeria;

    private JLabel botonGestionStock;
    private JLabel fondoBotonGestionStock;

    private JLabel botonFacturas;
    private JLabel fondoBotonFacturas;

    private JLabel botonRetroceder;
    private JLabel fondoBotonRetroceder;

    private JLabel marcoBotonFichado;
    private JLabel iconoBotonFichado;
    private JLabel fondoBotonFichado;

    private JLabel fondoNadaSeleccionado;
    private JLabel textoNadaSeleccionado;

    private boolean isFichadoPresionado;
    private int IDEmpleado;

    // Variable donde se almacenará el component listener para tener controlado el resize del frame, y poder quitarselo en el momento que yo quiera
    private ComponentListener detectorCambios;

    // Esta variable almacena el numero del dialogo que se va a mostrar cuando el usuario inicie sesion
    // el numero del dialogo será generado aleatoriamente y almacenado en esta variable. Cuando el usuario
    // vuelva al menu de inicio de sesion se generará otro numero aleatorio correspondiente a otro dialogo
    private int numeroDialogoBienvenida;

    // Esta variable tiene la funcion de almacenar qué fondo de boton se debe colocar en cada momento
    // se utiliza para que cuando se haga un resize de la pantalla, se coloque el fondo correcto en los botones correctos
    private String direccionFondoBoton;

    // Variables que controlan si alguno de los apartados esta abierto
    private boolean isMensajeriaAbierto;
    private boolean isGestionStockAbierto;
    private boolean isFacturasAbierto;

    // Objeto de la clase "VentanaJustificacion" por si el empleado tiene que justificar su falta
    VentanaJustificacion justificante;

    // Constructor
    public MenuPrincipalUsuario(boolean isFichadoPresionado, int IDEmpleado) {
        // Se lee de la base de datos si el usuario que acaba de iniciar sesion tiene el fichado activado
        this.isFichadoPresionado = isFichadoPresionado;
        // Se recibe el ID del empleado que ha iniciado sesion
        this.IDEmpleado = IDEmpleado;
        // Almacenar el numero aleatorio
        numeroDialogoBienvenida = generarNumeroAleatorio();
        // Construir la itnerfaz grafica
        detectarCambioResolucionVentana();
        configuraPaneles();
        colocarElementos();

        establecerTamanioComponentes();

        // Colocar de forma predeterminada que todos los apartados esten cerrados
        isMensajeriaAbierto = false;
        isGestionStockAbierto = false;
        isGestionStockAbierto = false;
        System.out.println("Estado del fichado: " + isFichadoPresionado);
    }

    // Inicializa los paneles que se van a colocar en el menu del usuario
    private void configuraPaneles() {
        // Configurar panel de la izquierda
        panelIzquierdo = new JPanel(null);
        panelGeneral.add(panelIzquierdo);

        panelCentral = new JPanel(null);
        panelCentral.setOpaque(false);
        panelGeneral.add(panelCentral);

        panelDerecho = new JPanel(null);
        panelDerecho.setOpaque(false);
        panelGeneral.add(panelDerecho);
    }

    private void colocarElementos() {
        // Logo y fondo
        fondoPanelIzquierda = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondoPanelIzquierda.jpg");
        logoElectroMoncho = InterfazGrafica.crearImagenLabelSinEscalar("recursos/logo-empresa.png");

        // Boton mensajeria
        botonMensajeria = InterfazGrafica.crearImagenLabelSinEscalar("recursos/botones/mensajeria_button.png");
        fondoBotonMensajeria = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondoBotones.jpg");
        // Boton Gestion de Stock
        botonGestionStock = InterfazGrafica.crearImagenLabelSinEscalar("recursos/botones/gestionStock_button.png");
        fondoBotonGestionStock = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondoBotones.jpg");
        // Boton Facturas
        botonFacturas = InterfazGrafica.crearImagenLabelSinEscalar("recursos/botones/facturas_button.png");
        fondoBotonFacturas = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondoBotones.jpg");
        // Boton Retroceder
        botonRetroceder = InterfazGrafica.crearImagenLabelSinEscalar("recursos/botones/retroceder_button.png");
        fondoBotonRetroceder = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondoBotones.jpg");
        // Boton fichaje
        marcoBotonFichado = new JLabel();
        iconoBotonFichado = new JLabel();
        fondoBotonFichado = new JLabel();

        // Fondo que se mostrará si no hay nada seleccionado
        fondoNadaSeleccionado = InterfazGrafica.crearImagenLabelSinEscalar("recursos/fondos/fondo_sin_seleccion.jpg");
        // Texto que se mostrará si no hay nada seleccionado
        textoNadaSeleccionado = InterfazGrafica.crearImagenLabelSinEscalar("recursos/texto/saludos/" + numeroDialogoBienvenida + ".png");

        // Añadir los componentes de la parte izquierda al panel izquierdo
        panelIzquierdo.add(botonMensajeria);
        panelIzquierdo.add(fondoBotonMensajeria);
        panelIzquierdo.add(botonGestionStock);
        panelIzquierdo.add(fondoBotonGestionStock);
        panelIzquierdo.add(botonFacturas);
        panelIzquierdo.add(fondoBotonFacturas);
        panelIzquierdo.add(botonRetroceder);
        panelIzquierdo.add(marcoBotonFichado);
        panelIzquierdo.add(fondoBotonRetroceder);
        panelIzquierdo.add(iconoBotonFichado);
        panelIzquierdo.add(fondoBotonFichado);
        panelIzquierdo.add(logoElectroMoncho);
        panelIzquierdo.add(fondoPanelIzquierda);

        // Añadir componentes al panel general
        panelGeneral.add(textoNadaSeleccionado);
        panelGeneral.add(fondoNadaSeleccionado);

        gestionarFuncionalidadesBotones();
    }

    // Para cada uno de los botones se agrega un mouse adapter que abra la clase que le da la funcion a cada boton, un fondo del           boton cuando el cursor no esta encima, y un fondo del boton cuando el cursor esta encima.
    private void gestionarFuncionalidadesBotones() {
        // BOTON MENSAJERÍA
        botonMensajeria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima si mensajeria no esta seleccionado
                if (!isMensajeriaAbierto) {
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonMensajeria, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, panelIzquierdo.getHeight() / 3, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                if (!isMensajeriaAbierto) {
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonMensajeria, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, panelIzquierdo.getHeight() / 3, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMensajeriaAbierto) {
                    // Poner en true que mensajeria está abierto pero poner en false todo el resto
                    isMensajeriaAbierto = true;
                    isGestionStockAbierto = false;
                    isFacturasAbierto = false;

                    // Refrescar propiedades y tamaño de los elementos de la aplicacion (esto soluciona bugs)
                    establecerTamanioComponentes();

                    // Eliminar todo el contenido de los paneles y llamar a la clase que se encarga de abrir la seccion pertinente
                    panelDerecho.removeAll();
                    panelCentral.removeAll();
                    panelGeneral.remove(textoNadaSeleccionado);
                    ListaMensajes listaMensajes = new ListaMensajes();
                }
            }
        });
        // BOTON GESTION STOCK
        botonGestionStock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                if (!isGestionStockAbierto) {
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonGestionStock, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7), panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                if (!isGestionStockAbierto) {
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonGestionStock, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7), panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isGestionStockAbierto) {
                    // Poner en true que gestion de stock está abierto pero poner en false todo el resto
                    isMensajeriaAbierto = false;
                    isGestionStockAbierto = true;
                    isFacturasAbierto = false;

                    // Refrescar propiedades y tamaño de los elementos de la aplicacion (esto soluciona bugs)
                    establecerTamanioComponentes();

                    // Eliminar todo el contenido de los paneles y llamar a la clase que se encarga de abrir la seccion pertinente
                    panelDerecho.removeAll();
                    panelCentral.removeAll();
                    panelGeneral.remove(textoNadaSeleccionado);
                    System.out.println("No disponible por el momento :(");
                }
            }
        });
        // BOTON FACTURAS
        botonFacturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                if (!isFacturasAbierto) {
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonFacturas, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7) * 2, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                if (!isFacturasAbierto) {
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonFacturas, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7) * 2, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isFacturasAbierto) {
                    // Poner en true que facturas está abierto pero poner en false todo el resto
                    isMensajeriaAbierto = false;
                    isGestionStockAbierto = false;
                    isFacturasAbierto = true;

                    // Refrescar propiedades y tamaño de los elementos de la aplicacion (esto soluciona bugs)
                    establecerTamanioComponentes();

                    // Eliminar todo el contenido de los paneles y llamar a la clase que se encarga de abrir la seccion pertinente
                    panelDerecho.removeAll();
                    panelCentral.removeAll();
                    panelGeneral.remove(textoNadaSeleccionado);
                    System.out.println("No disponible por el momento :(");
                }
            }
        });
        // BOTON RETROCEDER
        botonRetroceder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonRetroceder, panelIzquierdo.getWidth() / 7, panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 3, panelIzquierdo.getHeight() / 12);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonRetroceder, panelIzquierdo.getWidth() / 7, panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 3, panelIzquierdo.getHeight() / 12);
            }

            // Para cuando se haga click en el boton
            @Override
            public void mouseClicked(MouseEvent e) {
                // Eliminar todo el contenido de cada panel
                panelDerecho.removeAll();
                panelCentral.removeAll();
                panelIzquierdo.removeAll();
                panelGeneral.removeAll();
                // Eliminar el resize de la pantalla de inicio de sesion (porque no es necesario reescalar elementos que no se ven)
                frame.removeComponentListener(detectorCambios);
                // Llamar a la clase que pinta la pantalla de login
                InicioSesion inicioSesion = new InicioSesion(false);
            }
        });
        // Boton fichado
        iconoBotonFichado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                if (isFichadoPresionado) {
                    // icono del boton fichaje
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_reverse.gif", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                } else {
                    // fondo del boton
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                    // Icono del boton
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje.gif", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                if (isFichadoPresionado) {
                    // icono del boton fichaje
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_pressed.png", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                    // fondo del boton
                    //InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                } else {
                    // icono del boton fichaje
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_inicial.png", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                    // fondo del boton
                    InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                }
            }

            // Para cuando se haga click en el boton
            @Override
            public void mouseClicked(MouseEvent e) {
                // Llamar al metodo que gestiona todo el fichaje del empleado
                comprobarPuntualidadFichaje();
            }
        });
    }

    // Se encarga de leer en la base de datos el tipo de horario que tiene el empleado que ha iniciado sesion y muestra una caja de texto en caso de que el empleado haya llegado tarde o salga temprano
    private void comprobarPuntualidadFichaje() {
        // Variables que definen el margen de tardanza de un empleado
        final int MARGEN_TEMPRANO = 5;
        final int MARGEN_TARDE = 15;

        // Matriz donde se va a almacenar el horario del empleado extraido de la base de datos
        String[] horarioEmpleado = null;
        try {
            // Primero obtengo el id del tipo de horario que tiene el empleado que se va a fichar
            int tipoHorarioEmpleado = Integer.parseInt(ConexionBBDD.obtenerValorIndividual(IDEmpleado, "horario_establecido", "electromoncho", "empleados"));
            // Utilizando el id del horario, se va a obtener exactamente las horas de entradas y salidas que deberia cumplir el empleado
            // esta matriz guarda la fila de la tabla "horarios" que el empleado debe cumplir
            horarioEmpleado = ConexionBBDD.obtenerFila(tipoHorarioEmpleado, "electromoncho", "horarios");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Comprueba que tienes conexion a internet :D", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error con la información extraída de la base de datos, puede que esta esté corrupta. Échale la culpa al de bases de datos :/", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error inesperado D:", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Obtener la hora y minuto actual
        Calendar calendario = Calendar.getInstance();
        int obtenerHoraActual = calendario.get(Calendar.HOUR_OF_DAY);
        int obtenerMinutoActual = calendario.get(Calendar.MINUTE);
        // Utilizo la hora y el minuto actual obtenidos anteriormente, y los convierto a solo minutos para poder operar bien con ellos
        int minutosActuales = convertirHorasAMinutos(obtenerHoraActual, obtenerMinutoActual);

        // Cojo la hora de comienzo y fin de la jornada de la base de datos y hago el cambio en minutos utilizando la funcion convertirHoraMinutoAMinutos()
        int minutosComienzoJornada = convertirHoraMinutoAMinutos(horarioEmpleado[1]);
        int minutosFinalizacionJornada = convertirHoraMinutoAMinutos(horarioEmpleado[4]);

        // Si horarioEmpleado[2] significa que el horario de este empleado no tiene descansos intermedios, por lo que la comparacion va a ser mas sencilla
        // En caso contrario, si que tiene descansos
        if (horarioEmpleado[2] == null) {
            // No tiene descansos
            if (minutosActuales <= minutosComienzoJornada + MARGEN_TEMPRANO) {
                // Para cuando llega temprano
                //System.out.println("Llegó temprano!");
                ficharEmpleado("Llegada en hora");
            } else if ((minutosComienzoJornada + MARGEN_TEMPRANO) < minutosActuales && (minutosFinalizacionJornada - MARGEN_TARDE) > minutosActuales) {
                // Para cuando llega tarde o salga antes abrir dialogo para que el empleado justifique
                //System.out.println("Llegó tarde!");
                justificante = new VentanaJustificacion(isFichadoPresionado);
                ficharEmpleado(justificante.getJustificacion());
            } else if ((minutosFinalizacionJornada - MARGEN_TARDE) <= minutosActuales && minutosFinalizacionJornada > minutosActuales) {
                // Para cuando llega tan tarde que no puede fichar
                //System.out.println("No puede fichar!");
                JOptionPane.showMessageDialog(frame, "No se puede fichar 15 minutos antes de la hora de salida :/");
            } else {
                ficharEmpleado("Fichaje fuera de horario");
            }
        } else {
            // Tiene descansos
            int minutosComienzoDescanso = convertirHoraMinutoAMinutos(horarioEmpleado[2]);
            int minutosFinalizacionDescanso = convertirHoraMinutoAMinutos(horarioEmpleado[3]);

            if (minutosActuales <= minutosComienzoJornada + MARGEN_TEMPRANO || (minutosActuales >= minutosComienzoDescanso && minutosActuales <= minutosFinalizacionDescanso + MARGEN_TEMPRANO)) {
                // Para cuando llega temprano
                //System.out.println("Llegó temprano!");
                ficharEmpleado("Llegada en hora");
            } else if ((minutosActuales > minutosComienzoJornada + MARGEN_TEMPRANO && minutosActuales <= minutosComienzoDescanso - MARGEN_TARDE) || (minutosActuales > minutosFinalizacionDescanso + MARGEN_TEMPRANO && minutosActuales <= minutosFinalizacionJornada - MARGEN_TARDE)) {
                // Para cuando llega tarde o salga antes abrir dialogo para que el empleado justifique
                //System.out.println("Llegó tarde!");
                justificante = new VentanaJustificacion(isFichadoPresionado);
                ficharEmpleado(justificante.getJustificacion());
            } else if ((minutosActuales > minutosComienzoDescanso - MARGEN_TARDE && minutosActuales <= minutosComienzoDescanso) || (minutosActuales > minutosFinalizacionJornada - MARGEN_TARDE && minutosActuales <= minutosFinalizacionJornada)) {
                // Para cuando llega tan tarde que no puede fichar
                //System.out.println("No puede fichar!");
                JOptionPane.showMessageDialog(frame, "No se puede fichar 15 minutos antes de la hora de salida :/");
            } else {
                ficharEmpleado("Fichaje fuera de horario");
            }
        }
    }

    // Recibe una cadena en formato "hora:minuto" y lo convierte a minutos directamente con la ayuda de la funcion convertirHorasAMinutos()
    private int convertirHoraMinutoAMinutos(String horaMinuto) {
        String[] partes = horaMinuto.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        return convertirHorasAMinutos(horas, minutos);
    }

    // Recibiendo hora y minuto, convierte a solo minutos para poder operar bien con ellos
    private int convertirHorasAMinutos(int horas, int minutos) {
        int horasAMinutos = (horas * 60) + minutos; // Cada hora son 60 minutos, por tanto se hace esa conversion, y al final se le suman los miuntos
        return horasAMinutos;
    }

    //private String convertirMinutosAHoras(int minutos){
    //}
    // Se encarga de gestionar la funcionalidad del boton fichaje en la aplicacion:
    // !isFichadoPresionado: Si el fichaje no esta activo, inserta en la base de datos un registro nuevo con la hora y fecha a la que se comenzo el fichaje, pero las columnnas de fecha y hora salidas quedan en blanco hasta que se vuelva a pulsar el fichaje
    // isFichadoPresionado: Se actualiza el registro creado al pulsar por primera vez el boton fichaje, y se rellenan las columnas "fecha_salida" y "hora_salida" en la base de datos con la fecha y hora a la que se elimina el fichaje
    public void ficharEmpleado(String justificacion) {
        // Se abre un try-catch debido a que se hacen repetidas llamadas a una base de datos
        try {
            // Si el fichado no está pulsado...
            if (!isFichadoPresionado) {
                // Hacer matriz con los datos de una insercion de un nuevo registro (nuevo fichaje)
                String[] listaDatosAInsertar = {"0", IDEmpleado + "", null, null, "0", "0", justificacion, ""};
                // Ejecutar la consulta para insertar el nuevo registro con el metodo que se encarga de ello
                ConexionBBDD.insertarFilaCompleta("electromoncho", "fichaje_empleados", listaDatosAInsertar);

                // Cambiar la apariencia del boton para que el usuario tenga una referencia visual de cual es el estado del fichaje
                // icono del boton fichaje
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_pressed.png", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);

                // Refrescar el estado de la variable a true para que la proxima vez que se pulse, quitar el fichado
                isFichadoPresionado = true;

                // Si no hay justificacion, se mostrara un mensaje de "¡Fichaje enviado correctamente!"
                // Si hay justificacion, se mostrara un mensaje de "¡Fichaje y justificacion enviado correctamente correctamente!"
                if ("".equals(justificacion) || "Llegada en hora".equals(justificacion) || "Fichaje fuera de horario".equals(justificacion)) {
                    JOptionPane.showMessageDialog(frame, "¡Fichaje enviado correctamente!");
                } else {
                    JOptionPane.showMessageDialog(frame, "¡Fichaje y justificacion enviados correctamente!");
                }
                // Si el fichado está pulsado...
            } else if (isFichadoPresionado) {
                // Cuando el fichaje se retira, se va a actualizar el registro de la base de datos para mostrar la hora y fecha a la que se retiró el fichaje y dar asi por finalizado el fichaje
                // Preparar la lista de datos a actualizar en el registro del fichado de la base de datos
                String[] nombresColumnas = {"fecha_salida", "hora_salida", "justificacion_salida"};
                // Esta matriz va a contener los datos que se van a enviar al registro de la base de datos, en este caso la fecha y hora, las cuales se obtienen a partir de las funciones obtenerFechaActual() y obtenerHoraActual()
                String[] datosAInsertar = {obtenerFechaActual(), obtenerHoraActual(), justificacion};
                // Ejecutar la actualizacion con el metodo que lo permite
                ConexionBBDD.actualizarVariosCamposPorID("electromoncho", "fichaje_empleados", ConexionBBDD.obtenerIDMasAltoPorIDEmpleado(IDEmpleado), nombresColumnas, datosAInsertar);

                // Cambiar la apariencia del boton para que el usuario tenga una referencia visual de cual es el estado del fichaje
                // icono del boton fichaje
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_inicial.png", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                // fondo del boton
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
                // Refrescar el estado de la variable a false para que la proxima vez que se pulse, comience un nuevo fichado
                isFichadoPresionado = false;

                // Si no hay justificacion, se mostrara un mensaje de "¡Fichaje enviado correctamente!"
                // Si hay justificacion, se mostrara un mensaje de "¡Fichaje y justificacion enviado correctamente correctamente!"
                if ("".equals(justificacion) || "Llegada en hora".equals(justificacion) || "Fichaje fuera de horario".equals(justificacion)) {
                    JOptionPane.showMessageDialog(frame, "¡Fichaje cerrado correctamente!");
                } else {
                    JOptionPane.showMessageDialog(frame, "¡Fichaje cerrado y justificacion enviada correctamente!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Hubo un error :/. Comprueba que tienes conexion a internet :D", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Devuelve un String con la fecha actual del equipo
    private String obtenerFechaActual() {
        // Obtener la fecha actual utilizando la clase java.util.Calendar
        Calendar calendario = Calendar.getInstance();
        int obtenerAnio = calendario.get(Calendar.YEAR);
        int obtenerMes = calendario.get(Calendar.MONTH) + 1; // Los meses comienzan desde 0, así que se suma 1
        int obtenerDia = calendario.get(Calendar.DAY_OF_MONTH);
        // Variable que junta la fecha completa y la que va a devolver la funcion
        // En algunos valores llamo al método formatearNumero() que sirve para poner un 0 delante a aquellos numeros inferiores a 10
        String fecha = obtenerAnio + "-" + formatearNumero(obtenerMes) + "-" + formatearNumero(obtenerDia);
        return fecha;
    }

    // Devuelve un String con la hora actual del equipo
    private String obtenerHoraActual() {
        // Obtener la hora actual utilizando la clase java.util.Calendar
        Calendar calendario = Calendar.getInstance();
        int obtenerHora = calendario.get(Calendar.HOUR_OF_DAY); // Formato de 24 horas
        int obtenerMinuto = calendario.get(Calendar.MINUTE);
        int obtenerSegundo = calendario.get(Calendar.SECOND);
        // Variable que junta la hora completa y la que va a devolver la funcion
        // En algunos valores llamo al método formatearNumero() que sirve para poner un 0 delante a aquellos numeros inferiores a 10
        String hora = formatearNumero(obtenerHora) + ":" + formatearNumero(obtenerMinuto) + ":" + formatearNumero(obtenerSegundo);
        return hora;
    }

    // Se utiliza para coger cualquier numero menor a 10 y ponerle un 0 delante, para que se vea 05 en lugar de 5 (por ejemplo) en las fechas
    // Es llamado por obtenerFechaActual() y obtenerHoraActual() para convertir los segundos, minutos, horas, dias y mes
    private String formatearNumero(int textoATransformar) {
        String textoTransformado = textoATransformar + "";
        if (textoATransformar < 10) {
            textoTransformado = "0" + textoATransformar;
        }
        return textoTransformado;
    }

    private void establecerTamanioComponentes() {
        // Configuracion de tamaño de los paneles principales de este menu
        panelIzquierdo.setBounds(0, 0, panelGeneral.getWidth() / 4, panelGeneral.getHeight());
        panelCentral.setBounds(panelGeneral.getWidth() / 4, 0, (panelGeneral.getWidth() - (panelGeneral.getWidth() / 4)) / 2, panelGeneral.getHeight());
        panelDerecho.setBounds((panelGeneral.getWidth() / 4) + (panelGeneral.getWidth() / 3), 0, ((panelGeneral.getWidth() - (panelGeneral.getWidth() / 4)) / 2) + (panelGeneral.getWidth() / 12), panelGeneral.getHeight());

        // Configuracion tamaño del fondo de cuando no hay nada seleccionado
        // Imagen
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondo_sin_seleccion.jpg", fondoNadaSeleccionado, 0, 0, panelGeneral.getWidth(), panelGeneral.getHeight());
        // Texto
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/texto/saludos/" + numeroDialogoBienvenida + ".png", textoNadaSeleccionado, panelGeneral.getWidth() / 2 - panelGeneral.getWidth() / 8, (panelGeneral.getHeight() - (panelGeneral.getHeight() / 3)) / 2, panelGeneral.getWidth() / 2, panelGeneral.getHeight() / 3);

        // Configuracion de fondo panel izquiero y logo
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoPanelIzquierdaBordeNegro.jpg", fondoPanelIzquierda, 0, 0, panelIzquierdo.getWidth(), panelIzquierdo.getHeight());
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/logo-empresa.png", logoElectroMoncho, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 2)) / 2, panelIzquierdo.getHeight() / 8, panelIzquierdo.getWidth() / 2, panelIzquierdo.getHeight() / 6);

        // Boton de mensajeria
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/mensajeria_button.png", botonMensajeria, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, panelIzquierdo.getHeight() / 3, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
        // Este if comprueba si el boton esta pulsado. Si esta pulsado, la url del fondo del boton va a estar animado
        // si no esta pulsado, se pondrá el fondo estático
        if (isMensajeriaAbierto) {
            direccionFondoBoton = "recursos/fondos/fondoBotones4.gif";
        } else {
            direccionFondoBoton = "recursos/fondos/fondoBotones.jpg";
        }
        // Colocar el fondo del boton con la direccion del if
        InterfazGrafica.calcularNuevoTamanioImagen(direccionFondoBoton, fondoBotonMensajeria, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, panelIzquierdo.getHeight() / 3, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);

        // Boton de Gestion de Stock
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/gestionStock_button.png", botonGestionStock, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7), panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
        // Este if comprueba si el boton esta pulsado. Si esta pulsado, la url del fondo del boton va a estar animado
        // si no esta pulsado, se pondrá el fondo estatico
        if (isGestionStockAbierto) {
            direccionFondoBoton = "recursos/fondos/fondoBotones4.gif";
        } else {
            direccionFondoBoton = "recursos/fondos/fondoBotones.jpg";
        }
        // Colocar el fondo del boton con la direccion del if
        InterfazGrafica.calcularNuevoTamanioImagen(direccionFondoBoton, fondoBotonGestionStock, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7), panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);

        // Boton de facturas
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/facturas_button.png", botonFacturas, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7) * 2, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);
        // Este if comprueba si el boton esta pulsado. Si esta pulsado, la url del fondo del boton va a estar animado
        // si no esta pulsado, se pondrá el fondo estatico
        if (isFacturasAbierto) {
            direccionFondoBoton = "recursos/fondos/fondoBotones4.gif";
        } else {
            direccionFondoBoton = "recursos/fondos/fondoBotones.jpg";
        }
        // Colocar el fondo del boton con la direccion del if
        InterfazGrafica.calcularNuevoTamanioImagen(direccionFondoBoton, fondoBotonFacturas, (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5))) / 2, (panelIzquierdo.getHeight() / 3) + (panelIzquierdo.getHeight() / 7) * 2, panelIzquierdo.getWidth() - (panelIzquierdo.getWidth() / 5), panelIzquierdo.getHeight() / 11);

        // Boton de Retroceder
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/retroceder_button.png", botonRetroceder, panelIzquierdo.getWidth() / 7, panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 3, panelIzquierdo.getHeight() / 12);
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonRetroceder, panelIzquierdo.getWidth() / 7, panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 3, panelIzquierdo.getHeight() / 12);

        // Boton de Fichaje
        // marco del boton
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_marco.png", marcoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
        // Segun si el empleado está fichado o no, el boton se mostrara de formas distintas, por ello la existencia de este if
        if (isFichadoPresionado) {
            // icono del boton fichaje
            InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_pressed.png", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
            // fondo del boton
            InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones4.gif", fondoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
        } else {
            // icono del boton fichaje
            InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/boton_fichaje_inicial.png", iconoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
            // fondo del boton
            InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoBotones.jpg", fondoBotonFichado, (panelIzquierdo.getWidth() / 2) + (panelIzquierdo.getWidth() / 10), panelIzquierdo.getHeight() - (panelIzquierdo.getHeight() / 5), panelIzquierdo.getWidth() / 4, panelIzquierdo.getHeight() / 12);
        }
    }

    // Este método es llamado una vez por el constructor, y lo que hace es hacer que el frame detecte el cambio de resolucion
    // para que cada vez que se cambie la resolucion, los componentes se adapten al nuevo tamaño
    private void detectarCambioResolucionVentana() {
        detectorCambios = new ComponentAdapter() {
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
                        establecerTamanioComponentes(); // Llamada al método de actualización
                        System.out.println("Cambio en el menu del usuario");
                    }
                });
                timer.setRepeats(false); // Solo una ejecución del temporizador
                timer.start(); // Iniciar el temporizador
            }
        };
        frame.addComponentListener(detectorCambios);
    }

    // Método que genera un numero aleatorio del 1 al 7
    private int generarNumeroAleatorio() {
        // Generar un número aleatorio entre 1 y 7
        int numeroAleatorio = (int) (Math.random() * 7) + 1;
        return numeroAleatorio;
    }
}
