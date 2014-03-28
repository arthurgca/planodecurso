package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PeriodoTest {

    Periodo p0;
    Disciplina d0;

    @Before
    public void setUp() {
        p0 = new Periodo(1);
        d0 = new Disciplina("D0", 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invariantesDoConstrutor() {
        new Periodo(-1);
    }

    @Test
    public void invariantesDoConstrutor2() {
        assertTrue(new Periodo(1).getDisciplinas().isEmpty());
    }

    @Test
    public void invariantesDoConstrutor3() {
        assertTrue(new Periodo(1).getPoliticaDeCreditos() instanceof PoliticaDeCreditosNula);
    }

    @Test
    public void programar() throws Exception {
        p0.programar(d0);
        assertTrue(p0.getDisciplinas().contains(d0));
    }

    @Test
    public void programarIdempotente() throws Exception {
        p0.programar(d0);
        p0.programar(d0);
        assertTrue(p0.getDisciplinas().contains(d0));
        assertEquals(4, p0.getTotalCreditos());
    }

    @Test
    public void desprogramar() throws Exception {
        p0.programar(d0);
        p0.desprogramar(d0);
        assertFalse(p0.getDisciplinas().contains(d0));
    }

    @Test
    public void desprogramarIdempotente() throws Exception {
        p0.programar(d0);
        p0.desprogramar(d0);
        p0.desprogramar(d0);
        assertFalse(p0.getDisciplinas().contains(d0));
        assertEquals(0, p0.getTotalCreditos());
    }

    @Test
    public void getTotalCreditos() throws Exception {
        assertEquals(0, p0.getTotalCreditos());
        p0.programar(new Disciplina("D0", 4));
        p0.programar(new Disciplina("D1", 4));
        assertEquals(8, p0.getTotalCreditos());
    }

    @Test
    public void toStringOverride() {
        assertEquals("1º Período (0 disciplinas)", p0.toString());
    }

}
