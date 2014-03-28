package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PoliticaDeCreditosNulaTest {

    PoliticaDeCreditos pol = new PoliticaDeCreditosNula();

    Periodo p0;

    Disciplina d0;

    @Before
    public void setUp() {
        p0 = new Periodo(1);
        d0 = new Disciplina("D0", 4);
    }

    @Test
    public void podeProgramar() {
        assertTrue(pol.podeProgramar(d0, p0));
    }

    @Test
    public void podeDesrogramar() {
        assertTrue(pol.podeDesprogramar(d0, p0));
    }

    @Test
    public void validar() {
        assertNull(pol.validar(p0));
    }

}
