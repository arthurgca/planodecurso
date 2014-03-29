package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class GradeTest {

    Grade g1;

    Periodo p1;
    Periodo p2;
    Periodo p3;

    Disciplina d1;
    Disciplina d2;
    Disciplina d3;

    @Before
    public void setUp() {
        g1 = new Grade(3);

        p1 = g1.getPeriodo(1);
        p2 = g1.getPeriodo(2);
        p3 = g1.getPeriodo(3);

        d1 = new Disciplina("d1", 4);
        d2 = new Disciplina("d2", 4);
        d3 = new Disciplina("d3", 4);

        d2.setRequisitos(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{d1})));
        d3.setRequisitos(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{d2})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invariantesDoConstrutor() {
        new Grade(0);
    }

    @Test
    public void getSize() {
        assertEquals(3, g1.getSize());
    }

    @Test
    public void getPeriodoPreservaOrdem() {
        assertEquals(1, g1.getPeriodo(1).getSemestre());
        assertEquals(2, g1.getPeriodo(2).getSemestre());
        assertEquals(3, g1.getPeriodo(3).getSemestre());
    }

    @Test
    public void getDisciplinas() throws Exception {
        g1.programar(d1, p1);
        g1.programar(d2, p2);
        g1.programar(d3, p3);

        assertEquals(Arrays.asList(new Disciplina[] {d1,d2,d3}),
                     g1.getDisciplinas());
    }

    @Test
    public void desprogramarRecursivamente() throws Exception {
        g1.programar(d1, p1);
        g1.programar(d2, p2);
        g1.programar(d3, p3);

        g1.desprogramarRecursivamente(d1, p1);

        assertFalse(p1.getDisciplinas().contains(d1));
        assertFalse(p2.getDisciplinas().contains(d2));
        assertFalse(p3.getDisciplinas().contains(d3));
    }

    @Test
    public void toStringOverride() {
        assertEquals("Grade Sem Nome (3 períodos)", g1.toString());
    }

    @Test
    public void copiar() throws Exception {
        g1.programar(d1, p2);
        g1.programar(d2, p3);

        Grade g2 = Grade.copiar(g1);

        assertEquals(3, g2.getSize());

        assertTrue(p1.getDisciplinas().isEmpty());
        assertTrue(p2.getDisciplinas().contains(d1));
        assertTrue(p3.getDisciplinas().contains(d2));
    }

}
