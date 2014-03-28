package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PoliticaMinMaxDeCreditosTest {

    PoliticaDeCreditos pol = new PoliticaMinMaxDeCreditos(4, 8);

    Periodo p0;

    Disciplina d0;

    Disciplina d1;

    Disciplina d2;

    @Before
    public void setUp() throws Exception {
        p0 = new Periodo(1);
        d0 = new Disciplina("D0", 4);
        d1 = new Disciplina("D1", 4);
        d2 = new Disciplina("D2", 4);
        p0.programar(d0);
        p0.programar(d1);
    }

    @Test
    public void podeProgramar() throws Exception {
        assertFalse(pol.podeProgramar(d2, p0));
        p0.desprogramar(d1);
        assertTrue(pol.podeProgramar(d2, p0));
    }

    @Test
    public void podeDesprogramar() throws Exception {
        assertTrue(pol.podeDesprogramar(d1, p0));
        p0.desprogramar(d1);
        assertFalse(pol.podeDesprogramar(d0, p0));
    }

    @Test
    public void validar() throws Exception {
        assertNull(pol.validar(p0));
        p0.programar(d2);
        assertNotNull(pol.validar(p0));
        p0.desprogramar(d2);
        p0.desprogramar(d1);
        assertNull(pol.validar(p0));
        p0.desprogramar(d0);
        assertNotNull(pol.validar(p0));
    }

}
