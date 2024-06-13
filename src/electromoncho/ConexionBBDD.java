/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electromoncho;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Jorge
 */
public class ConexionBBDD {

    // Informacion de inicio de sesion para la BBDD
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String usuarioBBDD = "root";
    private static final String contraseniaBBDD = "";

    // Recibe nombre de usuario y contraseña y devuelve un true si los credenciales coinciden con un registro de la base de datos
    // en caso contrario recibe false
    public static boolean comprobarInicioSesion(String nombreBaseDatos, String usuarioIntroducido, String contraseniaIntroducida) throws SQLException {
        boolean isCorrecto = false;

        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
        Statement consulta = conexion.createStatement();

        ResultSet resultado = consulta.executeQuery("SELECT MAX(id) FROM empleados");

        int maxId = 0;
        if (resultado.next()) {
            maxId = resultado.getInt(1);
        }

        for (int i = 1; i <= maxId; i++) {
            // Ejecutar la consulta para obtener el usuario y la contraseña del empleado con ID igual a i
            ResultSet empleadoResult = consulta.executeQuery("SELECT usuarioEmpleado, passwordEmpleado FROM empleados WHERE id = " + i);

            // Obtener el resultado y mostrarlo por pantalla
            if (empleadoResult.next()) {
                String usuarioEmpleadoExtraido = empleadoResult.getString("usuarioEmpleado");
                String passwordEmpleadoExtraido = empleadoResult.getString("passwordEmpleado");
                if (usuarioEmpleadoExtraido.equals(usuarioIntroducido) && passwordEmpleadoExtraido.equals(contraseniaIntroducida)) {
                    isCorrecto = true;
                }
            }
            // Cerrar el ResultSet del empleado
            empleadoResult.close();
        }
        // Cerrar recursos
        resultado.close();
        consulta.close();
        conexion.close();

        return isCorrecto;
    }

    // Recibe un nombre de BBDD y el nombre de la tabla y devuelve una matriz bidimensional de cadenas String con la informacion de cada campo de la tabla
    public static String[][] obtenerDatosTabla(String nombreBBDD, String nombreTabla) throws SQLException {
        // Se establece una lista de listas 'datosTabla' en nula
        String[][] datosTabla = null;

        // Consulta SQL para seleccionar todos los campos de todas las filas de la tabla
        String consultaTabla = "SELECT * FROM " + nombreTabla;

        // Establece la conexión mediante el método 'getConnection()'
        Connection conexion = DriverManager.getConnection(URL + nombreBBDD, usuarioBBDD, contraseniaBBDD);
        // Crea una declaración SQL mediante el método 'createStatement()'
        Statement statement = conexion.createStatement();
        // Ejecuta la consulta SQL mediante el método 'executeQuery()' y se obtiene 
        // el conjunto de resultados
        ResultSet resultSet = statement.executeQuery(consultaTabla);

        // Obtiene metadatos de la tabla 
        ResultSetMetaData metaData = resultSet.getMetaData();
        // Mediante el método 'getColumnCount()' se obtiene el número de columnas
        int numColumnas = metaData.getColumnCount();

        // Inicializar la lista para almacenar los datos de la tabla
        List<String[]> listaDatos = new ArrayList<>();

        // Iterar sobre los resultados y almacenar los datos en la lista
        while (resultSet.next()) {
            // Se crea una nueva instancia de la lista en donde se almacena el valor 
            // de la variable 'numColumnas'
            String[] fila = new String[numColumnas];
            // Mediante el bucle for se itera sobre la lista
            for (int i = 1; i <= numColumnas; i++) {
                fila[i - 1] = resultSet.getString(i);
            }
            // Mediante el método 'add()' se añade el valor de la variable 'fila' a 'listaDatos'
            listaDatos.add(fila);
        }

        // Convierte la lista a una matriz de cadenas
        datosTabla = listaDatos.toArray(new String[0][]);

        // Cierra el 'resultSet' 
        resultSet.close();
        // Cierra el 'statement' 
        statement.close();
        // Cierra la 'conn' 
        conexion.close();
        // Se devuelve el valor de la variable 'datosTabla'
        return datosTabla;
    }

    // Método para obtener el ID más alto para un IDEmpleado específico
    public static int obtenerIDMasAltoPorIDEmpleado(int IDEmpleado) throws SQLException {
        int idMasAlto = -1; // Valor por defecto si no se encuentra ningún registro

        // Establecer la conexión a la base de datos
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electromoncho", "root", "");
        // Preparar la consulta SQL para obtener el ID más alto para un IDEmpleado específico
        String consulta = "SELECT MAX(ID) FROM fichaje_empleados WHERE IDEmpleado = ?";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1, IDEmpleado);
        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Verificar si se encontraron resultados
        if (resultSet.next()) {
            idMasAlto = resultSet.getInt(1); // Obtener el valor del primer resultado
        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        conexion.close();

        return idMasAlto;
    }

    public static void insertarFilaCompleta(String nombreBaseDatos, String nombreTabla, String[] listaDatosAInsertar) throws SQLException {
        String[] nombresColumnas;
        // Preparar conexion y statement
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
        Statement sentencia = conexion.createStatement();

        // Rellenar la matriz con los nombres de las columnas de la tabla en donde se hará la insercion de datos
        // para ello se llama al metodo que devuelve una matriz con los nombres de las columnas de la tabla actual
        nombresColumnas = obtenerNombresColumnas(nombreBaseDatos, nombreTabla);

        // Empezar a construir la sentencia sql, y utilizar bucles para llenar la consulta con los nombres de las columnas y sus valores
        String consulta = "INSERT INTO " + nombreTabla + " (";
        for (int i = 0; i < nombresColumnas.length; i++) {
            if (i == nombresColumnas.length - 1) {
                consulta += nombresColumnas[i] + ") ";
            } else {
                consulta += nombresColumnas[i] + ", ";
            }
        }
        // Continuar construyendo la sentencia
        consulta += "VALUES (";
        // Obtener los valores que se quieren insertar e introducirlos en la sentencia
        for (int i = 0; i < listaDatosAInsertar.length; i++) {
            if (i == listaDatosAInsertar.length - 1) {
                consulta += "'" + listaDatosAInsertar[i] + "')";
            } else {
                consulta += "'" + listaDatosAInsertar[i] + "', ";
            }
        }

        //System.out.println(consulta);
        // Ejecutar la consulta
        sentencia.executeUpdate(consulta);
        // Cierra el 'statement'
        sentencia.close();
        // Cierra la 'conn'
        conexion.close();
    }

    public static void actualizarVariosCamposPorID(String nombreBaseDatos, String nombreTabla, int filaID, String[] nombresColumnas, String[] datosAInsertar) throws SQLException {
        // Preparar conexion y statement
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
        Statement sentencia = conexion.createStatement();

        // Variable donde se va a construir la sentencia sql
        String consulta = "UPDATE " + nombreTabla + " SET ";

        for (int i = 0; i < nombresColumnas.length; i++) {
            if (i == nombresColumnas.length - 1) {
                consulta += nombresColumnas[i] + " = '" + datosAInsertar[i] + "' ";
            } else {
                consulta += nombresColumnas[i] + " = '" + datosAInsertar[i] + "', ";
            }
        }

        consulta += "WHERE " + obtenerNombrePrimeraColumna(nombreBaseDatos, nombreTabla) + " = " + filaID;
        //System.out.println(consulta);
        // Ejecutar la consulta
        sentencia.executeUpdate(consulta);
        // Cierra el 'statement'
        sentencia.close();
        // Cierra la 'conn'
        conexion.close();
    }

    public static void actualizarCampoPorID(String nombreBaseDatos, String nombreTabla, int filaID, String nombreColumna, String datoAInsertar) {
        try {
            // Preparar conexion y statement
            Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
            Statement sentencia = conexion.createStatement();
            // Variable que contiene la consulta que se va a realizar
            String consulta = "UPDATE " + nombreTabla + " SET " + nombreColumna + " = '" + datoAInsertar + "' WHERE " + obtenerNombrePrimeraColumna(nombreBaseDatos, nombreTabla) + " = " + filaID;
            //System.out.println(consulta);
            // Ejecutar la consulta
            sentencia.executeUpdate(consulta);
            // Cierra el 'statement'
            sentencia.close();
            // Cierra la 'conn'
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String[] obtenerNombresColumnas(String nombreBaseDatos, String nombreTabla) {
        String[] nombresColumnas = null;
        try {
            // Preparar conexion y statement
            Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
            Statement sentencia = conexion.createStatement();
            DatabaseMetaData metaData = conexion.getMetaData();
            // Obtener el nombre de las columnas
            ResultSet resultado = metaData.getColumns(null, null, nombreTabla, null);

            // Declarar las dimensiones de la matriz con un numero equivalente al numero de columnas que hay en la tabla
            nombresColumnas = new String[obtenerNumeroColumnasTabla(nombreBaseDatos, nombreTabla)];
            // Declarar contador
            int i = 0;
            // Iterar sobre el resultado y guardarlo en la matriz
            while (resultado.next()) {
                nombresColumnas[i] = resultado.getString(4);
                //System.out.println(resultado.getString(4));
                i++;
            }

            // Cierra el 'resultset'
            resultado.close();
            // Cierra el 'statement' 
            sentencia.close();
            // Cierra la 'conn' 
            conexion.close();

            return nombresColumnas;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBBDD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Devuelve un numero entero correspondiente al numero de columnas de una
     * tabla
     *
     * @param nombreBaseDatos Nombre de la base de datos
     * @param nombreTabla Nombre de la tabla donde se van a contar las columnas
     * @return Numero entero correspondiente al numero de columnas
     */
    private static int obtenerNumeroColumnasTabla(String nombreBaseDatos, String nombreTabla) {
        try {
            // Preparar conexión y statement
            Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
            Statement sentencia = conexion.createStatement();
            // Obtener metadatos de la base de datos
            DatabaseMetaData metaData = conexion.getMetaData();
            // Obtener el nombre de las columnas
            ResultSet resultado = metaData.getColumns(null, null, nombreTabla, null);

            // Contar el número de columnas en el resultado
            int numeroColumnas = 0;
            while (resultado.next()) {
                numeroColumnas++;
            }

            // Cierra el 'resultset'
            resultado.close();
            // Cierra el 'statement' 
            sentencia.close();
            // Cierra la 'conn' 
            conexion.close();

            return numeroColumnas;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Guarda todos los datos de la tabla "preguntas" de la BBDD "electromoncho_tickets" y crea una matriz bidimensional de labels con los textos de cada uno de los
    // campos de la tabla. Esta matriz se utiliza despues para mostrar una lista con todos los mensajes recibidos en la clase "ListaMensajes"
    public static void crearLabelsListaPreguntas() throws SQLException {
        // Consulta SQL para seleccionar todos los campos de todas las filas de la tabla
        String consultaTabla = "SELECT * FROM preguntas";

        // Establece la conexión mediante el método 'getConnection()'
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electromoncho_tickets", usuarioBBDD, contraseniaBBDD);
        // Crea una declaración SQL mediante el método 'createStatement()'
        Statement statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Ejecuta la consulta SQL mediante el método 'executeQuery()' y se obtiene 
        // el conjunto de resultados
        ResultSet resultSet = statement.executeQuery(consultaTabla);

        // Obtiene metadatos de la tabla 
        ResultSetMetaData metaData = resultSet.getMetaData();
        // Mediante el método 'getColumnCount()' se obtiene el número de columnas
        int numColumnas = metaData.getColumnCount();
        // Contar el numero de filas que hay para inicializar la lista JLabel correctamente
        int numFilas = 0;
        // Mover el cursor al final para obtener el número de filas
        if (resultSet.last()) {
            numFilas = resultSet.getRow();
            // Mover el cursor al principio
            resultSet.beforeFirst();
        }
        // Inicializar la matriz mensajes con el mismo número de filas y columans que la consulta SQL
        // le agrego +1 al numero de columnas porque va a haber un label extra para la imagen de fondo del boton del mensaje
        ListaMensajes.mensajes = new JLabel[numFilas][numColumnas + 1];
        // Iterar sobre los resultados y almacenar los datos en la lista
        int j = 0;
        while (resultSet.next()) {
            // Mediante el bucle for se itera sobre la lista
            for (int i = 1; i <= numColumnas; i++) {
                // i == 2 esta cogiendo la columna que es clave foranea, por lo que en lugar de obtener el valor numerico
                // que simplemente hace referencia a un ID, obtengo el valor real que es referenciado
                if (i == 2) {
                    //System.out.print(ConexionBBDD.obtenerNombreApellidoCliente(Integer.parseInt(resultSet.getString(i)), "electromoncho", "clientes") + "\t");
                    ListaMensajes.mensajes[j][i - 1] = new JLabel(ConexionBBDD.obtenerNombreApellidoCliente(Integer.parseInt(resultSet.getString(i)), "electromoncho", "clientes"));
                } else {
                    //System.out.print(resultSet.getString(i) + "\t");
                    ListaMensajes.mensajes[j][i - 1] = new JLabel(resultSet.getString(i));
                }
            }
            // crear el label donde se pondrá la imagen del boton
            ListaMensajes.mensajes[j][numColumnas] = new JLabel();
            j++;
            //System.out.println(); // Agregar un salto de línea después de cada fila
        }
        // Cierra el 'resultSet' 
        resultSet.close();
        // Cierra el 'statement' 
        statement.close();
        // Cierra la 'conn' 
        conexion.close();
    }

    public static void consultarRespuestasPorID(int idPregunta) throws SQLException {
        // Consulta SQL para seleccionar todos los campos de todas las filas de la tabla
        String consultaTabla = "SELECT * FROM respuestas WHERE IDPregunta = " + idPregunta;

        // Establece la conexión mediante el método 'getConnection()'
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electromoncho_tickets", usuarioBBDD, contraseniaBBDD);
        // Crea una declaración SQL mediante el método 'createStatement()'
        Statement statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Ejecuta la consulta SQL mediante el método 'executeQuery()' y se obtiene 
        // el conjunto de resultados
        ResultSet resultSet = statement.executeQuery(consultaTabla);

        // Obtiene metadatos de la tabla 
        ResultSetMetaData metaData = resultSet.getMetaData();
        // Mediante el método 'getColumnCount()' se obtiene el número de columnas
        int numColumnas = metaData.getColumnCount();
        // Contar el numero de filas que hay para inicializar la lista JLabel correctamente
        int numFilas = 0;
        // Mover el cursor al final para obtener el número de filas
        if (resultSet.last()) {
            numFilas = resultSet.getRow();
            // Mover el cursor al principio
            resultSet.beforeFirst();
        }
        // Inicializar la matriz mensajes con el mismo número de filas y columans que la consulta SQL
        // le agrego +1 al numero de columnas porque va a haber un label extra para la imagen de fondo del boton del mensaje
        Chat.chatActual = new JLabel[numFilas][numColumnas + 1];
        // Iterar sobre los resultados y almacenar los datos en la lista
        int j = 0;
        while (resultSet.next()) {
            // Mediante el bucle for se itera sobre el resgistro, y se va añadiendo campo a campo a la lista
            for (int i = 1; i <= numColumnas; i++) {
                // Cuando se consulten las columnas IDUsuario e IDEmpleado, al ser claves foraneas lo que se hace es extraer el valor numerico que referencie la clave foranea y consultar el valor real. De ahi la distincion de i = 2 e i = 3
                if (i == 2 && resultSet.getString(i) != null) {
                    System.out.print(obtenerNombreApellidoCliente(Integer.parseInt(resultSet.getString(i)), "electromoncho", "clientes") + "\t");
                } else if (i == 3 && resultSet.getString(i) != null) {
                    System.out.print(obtenerNombreApellidoEmpleado(Integer.parseInt(resultSet.getString(i)), "electromoncho", "empleados") + "\t");
                } else {
                    System.out.print(resultSet.getString(i) + "\t");
                }
            }
            // crear el label donde se pondrá la imagen de fondo del mensaje
            //InterfazGrafica.mensajes[j][numColumnas] = new JLabel();
            j++;
            System.out.println(); // Agregar un salto de línea después de cada fila
        }
        // Cierra el 'resultSet' 
        resultSet.close();
        // Cierra el 'statement' 
        statement.close();
        // Cierra la 'conn' 
        conexion.close();
    }

    // Método para obtener el nombre y apellido de la tabla a partir de un ID
    private static String obtenerNombreApellidoCliente(int id, String nombreBaseDatos, String nombreTabla) {
        String nombreApellido = null;

        // Declaración de la conexión, la declaración y el resultado
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;

        try {
            // Establecer conexión con la base de datos
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);

            // Consulta SQL para obtener el nombre y apellido a partir del ID
            String sql = "SELECT nombre, apellido1 FROM " + nombreTabla + " WHERE NCliente = ?";

            // Preparar la consulta
            consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, id);

            // Ejecutar la consulta y obtener el resultado
            resultado = consulta.executeQuery();

            // Verificar si se obtuvieron resultados
            if (resultado.next()) {
                // Obtener el nombre y apellido de la fila actual
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido1");

                // Concatenar nombre y apellido en una sola cadena
                nombreApellido = nombre + " " + apellido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión, la declaración y el resultado
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Devolver el nombre y apellido concatenados o null si no se encontraron resultados
        return nombreApellido;
    }

    // Devuelve una matriz unidimensional correspondiente a todos los datos de una fila de una tabla
    public static String[] obtenerFila(int id, String nombreBaseDatos, String nombreTabla) throws SQLException {
        // Se crea una lista 'fila' y se instancia en nula
        String[] fila = null;

        // Consulta SQL para seleccionar todos los datos de la fila basada en el ID
        String consultaFila = "SELECT * FROM " + nombreTabla + " WHERE " + obtenerNombrePrimeraColumna(nombreBaseDatos, nombreTabla) + " = ?";

        // Establece la conexión mediante el método 'getConnection()'
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
        // Crea una declaración SQL mediante el método 'prepareStatement()'
        PreparedStatement statement = conexion.prepareStatement(consultaFila);
        // Se establece mediante el método 'setInt()' la posición 1 y el id
        statement.setInt(1, id);
        // Ejecutar la consulta SQL y obtener el conjunto de resultados
        ResultSet resultSet = statement.executeQuery();

        // Obtiene los metadatos de la fila para obtener el número de columnas
        ResultSetMetaData metaData = resultSet.getMetaData();
        // Mediante el método 'getColumnCount()' se obtiene el número de columnas
        int numColumnas = metaData.getColumnCount();

        // Si hay resultados, inicializar el array de fila
        if (resultSet.next()) {
            // Crea una nueva instancia de la lista en la que se almacena el valor de 'numColumnas'
            fila = new String[numColumnas];
            // Obtener los valores de cada columna y guardarlos en el arreglo
            for (int i = 1; i <= numColumnas; i++) {
                fila[i - 1] = resultSet.getString(i);
            }
        }

        // Cierra el 'resultSet' 
        resultSet.close();
        // Cierra el 'statement' 
        statement.close();
        // Cierra la 'conn' 
        conexion.close();
        // Devuelve el valor de 'fila'
        return fila;
    }

    // Devuelve el valor de una celda individual de una tabla de la base de datos a partir de darle un id y un nombre de columna
    public static String obtenerValorIndividual(int id, String nombreColumna, String nombreBaseDatos, String nombreTabla) throws SQLException {
        String valor = null;
        // Establece la conexión mediante el método 'getConnection()'
        Connection conexion = DriverManager.getConnection(URL + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);
        // Crea una declaración SQL mediante el método 'createStatement()'
        Statement statement = conexion.createStatement();
        // Consulta
        String consultaTabla = "SELECT " + nombreColumna + " FROM " + nombreTabla + " WHERE " + obtenerNombrePrimeraColumna(nombreBaseDatos, nombreTabla) + " = " + id;
        // Ejecuta la consulta SQL mediante el método 'executeQuery()' y se obtiene  el resultado
        ResultSet resultado = statement.executeQuery(consultaTabla);

        if (resultado.next()) {
            valor = resultado.getString(1);
        }
        return valor;
    }

    // Método para obtener el nombre y apellido de la tabla a partir de un ID
    private static String obtenerNombreApellidoEmpleado(int id, String nombreBaseDatos, String nombreTabla) {
        String nombreApellido = null;

        // Declaración de la conexión, la declaración y el resultado
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;

        try {
            // Establecer conexión con la base de datos
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBaseDatos, usuarioBBDD, contraseniaBBDD);

            // Consulta SQL para obtener el nombre y apellido a partir del ID
            String sql = "SELECT nombreEmpleado, apellidosEmpleado FROM " + nombreTabla + " WHERE id = ?";

            // Preparar la consulta
            consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, id);

            // Ejecutar la consulta y obtener el resultado
            resultado = consulta.executeQuery();

            // Verificar si se obtuvieron resultados
            if (resultado.next()) {
                // Obtener el nombre y apellido de la fila actual
                String nombre = resultado.getString("nombreEmpleado");
                String apellido = resultado.getString("apellidosEmpleado");

                // Concatenar nombre y apellido en una sola cadena
                nombreApellido = nombre + " " + apellido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión, la declaración y el resultado
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Devolver el nombre y apellido concatenados o null si no se encontraron resultados
        return nombreApellido;
    }

    private static String obtenerNombrePrimeraColumna(String BBDD, String nombreTabla) {
        // Se instancia la variable 'nombrePrimeraColumna' en nula
        String nombrePrimeraColumna = null;

        // Consulta SQL para obtener el nombre de la primera columna de la tabla
        String consultaColumna = "SELECT COLUMN_NAME "
                + "FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_NAME = ? "
                + "ORDER BY ORDINAL_POSITION LIMIT 1";

        try {
            // Establece la conexión mediante el método 'getConnection()'
            Connection conn = DriverManager.getConnection(URL + BBDD, usuarioBBDD, contraseniaBBDD);

            // Crear una declaración SQL mediante el método 'prepareStatement()'
            PreparedStatement statement = conn.prepareStatement(consultaColumna);
            // Mediante el 'setString' se establecen como parámetros la posición 1 y la variable 'nombreTabla'
            statement.setString(1, nombreTabla);

            // Ejecutar la consulta SQL mediante el método 'executeQuery()'
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Mediante el 'getString' se establece como parámetro la variable 'COLUMN_NAME'
                nombrePrimeraColumna = resultSet.getString("COLUMN_NAME");
            }

            // Cierra el 'resultSet' 
            resultSet.close();
            // Cierra el 'statement' 
            statement.close();
            // Cierra la 'conn' 
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se devuelve el valor de la variable 'nombrePrimeraColumna'
        return nombrePrimeraColumna;
    }
}
