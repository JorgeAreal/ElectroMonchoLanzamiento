/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package electromoncho;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jorge
 */
public class IntroducirCuentaTest {
    
    public IntroducirCuentaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testObtenerIdEmpleado() {
        String nombreEmpleado = "Juan";
        String apellidoEmpleado = "Moreno Gomez";
        Integer resultadoPrevisto = 1;
        Integer resultadoReal = IntroducirCuenta.obtenerIdEmpleado(nombreEmpleado, apellidoEmpleado);
        assertEquals(resultadoPrevisto, resultadoReal);
    }

    @Test
    public void testObtenerEstadoFichajeEmpleado() {
        Integer idEmpleado = 1;
        boolean resultadoPrevisto = false;
        boolean resultadoReal = IntroducirCuenta.obtenerEstadoFichajeEmpleado(idEmpleado);
        assertEquals(resultadoPrevisto, resultadoReal);
    }
}
