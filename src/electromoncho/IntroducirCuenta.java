/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import static electromoncho.InterfazGrafica.frame;
import static electromoncho.InterfazGrafica.panelGeneral;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Jorge
 */
public class IntroducirCuenta {

    private JPanel panelFormulario;

    private JLabel fondoInicioSesion;
    private JLabel textoIniciarSesion;
    private JLabel cuadroBlanco;
    private JLabel botonRetroceder;

    // elementos para los campos de usuario y contraseña
    private JTextField campoUsuario;
    private JPasswordField campoContrasenia;
    private JLabel labelUsuario;
    private JLabel labelContrasenia;
    private JLabel botonIniciarSesion;

    // Fuente de las palabras "empleado" y "contraseña" que se puede ver en la ventana de inicio de sesion
    private Font fuenteInicioSesion;

    private ComponentListener detectorCambios;

    public IntroducirCuenta() {
        configurarPaneles();
        colocarElementosMenu();

        detectarCambioResolucionVentana();
        establecerTamanioComponentes();
        panelGeneral.repaint();

        // ELIMINAR ESTTO PARA EL LANZAMIENTO: ES PARA PASAR RAPIDO LA PAGINA
        // Eliminar el contenido de los paneles actuales para despues meterle un nuevo contenido
        /*panelFormulario.removeAll();
        panelGeneral.removeAll();
        panelGeneral.repaint();
        // Llamar a la clase que se encarga de pintar el nuevo menú
        MenuPrincipalUsuario menuPrincipal = new MenuPrincipalUsuario(true, 1);
        panelGeneral.repaint();*/
    }

    private void configurarPaneles() {
        panelFormulario = new JPanel(null);
        panelFormulario.setOpaque(false);
        panelFormulario.setVisible(true);
        panelGeneral.add(panelFormulario);
    }

    private void colocarElementosMenu() {
        fondoInicioSesion = new JLabel();
        cuadroBlanco = new JLabel();
        textoIniciarSesion = new JLabel();
        botonRetroceder = new JLabel();

        // Crear los campos de usuario y contraseña, así como las etiquetas correspondientes
        //fuenteInicioSesion = new Font("Arial", Font.BOLD, panelGeneral.getWidth()/142 + panelGeneral.getHeight()/80);
        labelUsuario = new JLabel("Empleado:");
        labelUsuario.setForeground(Color.BLACK);

        campoUsuario = new JTextField();

        labelContrasenia = new JLabel("Contraseña:");
        labelContrasenia.setForeground(Color.BLACK);

        campoContrasenia = new JPasswordField();

        botonIniciarSesion = new JLabel();

        // Agregar los renglones de inicio de sesion al panel del formulario
        panelFormulario.add(labelUsuario);
        panelFormulario.add(campoUsuario);
        panelFormulario.add(labelContrasenia);
        panelFormulario.add(campoContrasenia);
        panelFormulario.add(botonIniciarSesion);

        // Agregar los demás elementos al panel general
        panelGeneral.add(botonRetroceder);
        panelFormulario.add(cuadroBlanco);
        panelGeneral.add(textoIniciarSesion);
        panelGeneral.add(fondoInicioSesion);

        // Llamar a la funcion que le agrega funcionalidades a los botones
        gestionarFuncionalidadesBotones();
    }

    private void gestionarFuncionalidadesBotones() {
        // BOTON RETROCEDER
        botonRetroceder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/retroceder2_pressed.png", botonRetroceder, panelGeneral.getWidth() / 190, panelGeneral.getHeight() / 100, panelGeneral.getWidth() / 16, panelGeneral.getHeight() / 9);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/retroceder2.png", botonRetroceder, panelGeneral.getWidth() / 190, panelGeneral.getHeight() / 100, panelGeneral.getWidth() / 16, panelGeneral.getHeight() / 9);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Volver al menú de inicio
                // elimino todos los elementos de este menú y llamo a la clase que genera la pantalla de inicio
                panelGeneral.removeAll();
                // Eliminar el resize de la pantalla de inicio de sesion (porque no es necesario reescalar elementos que no se ven)
                frame.removeComponentListener(detectorCambios);
                // le mando como parámetro "false" para que no se llamen a las funciones de configuracion de frame, porque ya está configurado desde la primera vez que se cargó
                InicioSesion inicioSesion = new InicioSesion(false);
                panelGeneral.repaint();
            }
        });

        // Hacer que con pulsar enter se inicie la sesion
        campoUsuario.addActionListener((ActionEvent e) -> {
            iniciarSesion();
        });
        campoContrasenia.addActionListener((ActionEvent e) -> {
            iniciarSesion();
        });

        // BOTON INICIAR SESION
        botonIniciarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar la imagen cuando el cursor está encima
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/iniciarSesion_pressed.jpg", botonIniciarSesion, (panelFormulario.getWidth() - panelFormulario.getWidth() / 3) / 2, panelFormulario.getHeight() / 2 + panelFormulario.getHeight() / 5, panelFormulario.getWidth() / 3, (panelFormulario.getHeight() / 8));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar la imagen original cuando el cursor sale
                InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/iniciarSesion.jpg", botonIniciarSesion, (panelFormulario.getWidth() - panelFormulario.getWidth() / 3) / 2, panelFormulario.getHeight() / 2 + panelFormulario.getHeight() / 5, panelFormulario.getWidth() / 3, (panelFormulario.getHeight() / 8));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                iniciarSesion();
            }
        });
    }

    // Método que gestiona el inicio de sesion de la aplicacion. Es llamado tras pulsar el boton "iniciar sesion" o enter cuando está alguno de los campos de texto seleccionado
    private void iniciarSesion() {
        // Comprobar los credenciales de inicio de sesion obteniendo el usuario y contraseña introducidos por el usuario, y mandandolos como
        // parametro a la funcion que se encarga de comprobar la equivalencia entre los datos introducidos y los almacenados en la base de datos
        String usuario = campoUsuario.getText();
        String contrasenia = new String(campoContrasenia.getPassword());
        // Abrir try porque en esta ocasion hay una conexion a base de datos muy facilmente susceptible a errores
        try {
            // Compruebo los credenciales de inicio de sesion con el método "comprobarInicioSesion()", el cual devuelve true si las credenciales son correctas
            if (ConexionBBDD.comprobarInicioSesion("electromoncho", usuario, contrasenia)) {
                // Mostrar mensaje de bienvenida
                JOptionPane.showMessageDialog(panelGeneral, "Bienvenido " + usuario + "!", "Exito!", JOptionPane.INFORMATION_MESSAGE);
                // Eliminar el contenido de los paneles actuales para despues meterle un nuevo contenido
                panelFormulario.removeAll();
                panelGeneral.removeAll();
                panelGeneral.repaint();
                // Eliminar el resize de la pantalla de inicio de sesion (porque no es necesario reescalar elementos que no se ven)
                frame.removeComponentListener(detectorCambios);

                // Separar el nombre del apellido dentro de un mismo string
                String[] partes = usuario.split(" ");  // partes[0] es el nombre, partes[1] en adelante son los apellidos

                // Consultar el id del empleado que acaba de iniciar sesion
                int IDEmpleado = obtenerIdEmpleado(partes[0], partes[1] + " " + partes[2]);

                // Llamar a la clase que se encarga de pintar el nuevo menú
                // le paso como parametros "true" o "false" segun si el empleado que acaba de iniciar sesion esta fichado, y le paso el id del empleado de la base de datos
                MenuPrincipalUsuario menuPrincipal = new MenuPrincipalUsuario(obtenerEstadoFichajeEmpleado(IDEmpleado), IDEmpleado);
            } else {
                JOptionPane.showMessageDialog(panelGeneral, "Credenciales incorrectas :(", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(panelGeneral, "Error de conexion. Comprueba tu conexion a internet :/", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void establecerTamanioComponentes() {
        // Panel del formulario
        panelFormulario.setBounds(((panelGeneral.getWidth() - panelGeneral.getWidth() / 3) / 2), ((panelGeneral.getHeight() - panelGeneral.getHeight() / 2) / 2), panelGeneral.getWidth() / 3, panelGeneral.getHeight() * 10 / 17);

        // Fondo de pantalla
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/fondos/fondoLoopHorizontal.gif", fondoInicioSesion, 0, 0, frame.getWidth(), frame.getHeight());

        // Cuadro blanco donde se va a colocar el formulario
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/cuadroBlanco.png", cuadroBlanco, 0, 0, panelFormulario.getWidth(), panelFormulario.getHeight());

        // Texto INICIAR SESION
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/texto/INICIARSESION.png", textoIniciarSesion, (panelGeneral.getWidth() - (panelGeneral.getWidth() / 3)) / 2, panelGeneral.getHeight() / 40, panelGeneral.getWidth() / 3, panelGeneral.getHeight() / 6);

        // Boton retroceder
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/retroceder2.png", botonRetroceder, panelGeneral.getWidth() / 190, panelGeneral.getHeight() / 100, panelGeneral.getWidth() / 16, panelGeneral.getHeight() / 9);

        // Volver a colocar las letras con las nuevas posiciones y tamaños de las palabras "empleado" y "contraseña"
        // tamaño de fuente
        fuenteInicioSesion = new Font("Arial", Font.BOLD, panelGeneral.getWidth() / 142 + panelGeneral.getHeight() / 80);
        // colocar la fuente a los labels
        labelUsuario.setFont(fuenteInicioSesion);
        labelContrasenia.setFont(fuenteInicioSesion);
        // establecer los tamaños y posiciones
        labelUsuario.setBounds(panelFormulario.getWidth() / 3, panelFormulario.getHeight() / 3 - panelFormulario.getHeight() / 14, (panelFormulario.getWidth() / 2) + (panelFormulario.getWidth() / 2), 30);
        labelContrasenia.setBounds(panelFormulario.getWidth() / 3, (panelFormulario.getHeight() * 30) / 65, (panelFormulario.getWidth() / 2) + (panelFormulario.getWidth() / 2), 30);

        // Establecer las posiciones y tamaños de los renglones de inicio de sesion
        campoUsuario.setBounds(panelFormulario.getWidth() / 3, panelFormulario.getHeight() / 3, panelFormulario.getWidth() / 3, panelFormulario.getHeight() / 14);
        campoContrasenia.setBounds(panelFormulario.getWidth() / 3, panelFormulario.getHeight() / 3 + panelFormulario.getHeight() / 5, panelFormulario.getWidth() / 3, panelFormulario.getHeight() / 14);

        // Boton iniciar sesion
        InterfazGrafica.calcularNuevoTamanioImagen("recursos/botones/iniciarSesion.jpg", botonIniciarSesion, (panelFormulario.getWidth() - panelFormulario.getWidth() / 3) / 2, panelFormulario.getHeight() / 2 + panelFormulario.getHeight() / 5, panelFormulario.getWidth() / 3, (panelFormulario.getHeight() / 8));
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
                timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        establecerTamanioComponentes(); // Llamada al método de actualización
                        System.out.println("HOLA");
                    }
                });
                timer.setRepeats(false); // Solo una ejecución del temporizador
                timer.start(); // Iniciar el temporizador
            }
        };
        frame.addComponentListener(detectorCambios);
    }

    public static Integer obtenerIdEmpleado(String nombreEmpleado, String apellidoEmpleado) {
        Integer idEmpleado = null;
        try {
            // Establecer conexión con la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electromoncho", "root", "");
            Statement statement = conexion.createStatement();

            // Consulta SQL para obtener el ID del empleado
            String consulta = "SELECT id FROM empleados WHERE nombreEmpleado = '" + nombreEmpleado + "' AND apellidosEmpleado = '" + apellidoEmpleado + "'";

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery(consulta);

            // Obtener el ID si hay un resultado
            if (resultSet.next()) {
                idEmpleado = resultSet.getInt("id");
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idEmpleado;
    }

    public static boolean obtenerEstadoFichajeEmpleado(Integer idEmpleado) {
        boolean fichado = false;
        try {
            // Establecer conexión con la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electromoncho", "root", "");
            // Consulta SQL para obtener el estado de fichaje del empleado
            String consulta = "SELECT fecha_salida FROM fichaje_empleados WHERE IDEmpleado = ? AND ID = (SELECT MAX(ID) FROM fichaje_empleados WHERE IDEmpleado = ?)";

            // Utilizar PreparedStatement para evitar inyección SQL
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, idEmpleado);
            statement.setInt(2, idEmpleado);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el estado si hay un resultado   
            if (resultSet.next()) {
                // Comparar la cadena correctamente usando equals
                // Si en el ultimo registro de fichaje de este empleado pone "0", significa que tiene un fichaje pendiente de cerrar, y por tanto, está fichado
                if ("0".equals(resultSet.getString("fecha_salida"))) {
                    fichado = true;
                    // Si no pone un "0", pone la fecha donde se cerro el ultimo fichaje, por lo tanto no tiene ningun fichaje activo
                } else {
                    fichado = false;
                }
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fichado;
    }

}
