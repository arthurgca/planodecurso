package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PoliticaMaxDeCreditosTest {

    PoliticaDeCreditos pol = new PoliticaMaxDeCreditos(4);

    Periodo p0;

    Disciplina d0;

    Disciplina d1;

    @Before
    public void setUp() throws Exception {
        p0 = new Periodo(1);
        d0 = new Disciplina("D0", 4);
        d1 = new Disciplina("D1", 4);
    }

    @Test
    public void podeProgramar() throws Exception {
        assertTrue(pol.podeProgramar(d0, p0));
        p0.programar(d0);
        assertFalse(pol.podeProgramar(d1, p0));
    }

    @Test
    public void podeDesprogramar() throws Exception {
        p0.programar(d0);
        p0.programar(d1);
        assertTrue(pol.podeDesprogramar(d0, p0));
        assertTrue(pol.podeDesprogramar(d1, p0));
    }

    @Test
    public void validar() throws Exception {
        assertNull(pol.validar(p0));
        p0.programar(d0);
        p0.programar(d1);
        assertNotNull(pol.validar(p0));
    }

}
