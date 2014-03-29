package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PlanoTest {

    Disciplina d1;
    Disciplina d2;
    Disciplina d3;

    Curriculo c1;

    Grade g1;

    Periodo p1;
    Periodo p2;
    Periodo p3;

    Plano plano;

    @Before
    public void setUp() throws Exception {
        d1 = new Disciplina("D1", 4);
        d2 = new Disciplina("D2", 4);
        d2.setRequisitos(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d1
                    })));
        d3 = new Disciplina("D3", 4);

        c1 = new Curriculo(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d1, d2, d3
                    })));
        c1.setMaxPeriodos(3);
        c1.setMinCreditosPeriodo(4);
        c1.setMaxCreditosPeriodo(8);

        g1 = new Grade(3);

        p1 = g1.getPeriodo(1);
        p2 = g1.getPeriodo(2);
        p3 = g1.getPeriodo(3);

        g1.programar(d1, p2);

        plano = new Plano(c1, g1);
    }

    @Test(expected = NullPointerException.class)
    public void invariantesDoConstrutor1() {
        new Plano(null, g1);
    }

    @Test(expected = NullPointerException.class)
    public void invariantesDoConstrutor2() {
        new Plano(c1, null);
    }

    @Test
    public void setPeriodoAtualSetaPoliticaDeCreditos() {
        assertTrue(p1.getPoliticaDeCreditos() instanceof PoliticaDeCreditosNula);
        assertTrue(p2.getPoliticaDeCreditos() instanceof PoliticaDeCreditosNula);
        assertTrue(p3.getPoliticaDeCreditos() instanceof PoliticaDeCreditosNula);

        plano.setPeriodoAtual(p2);
        assertEquals(p2, plano.getPeriodoAtual());

        assertTrue(p1.getPoliticaDeCreditos() instanceof PoliticaMaxDeCreditos);
        assertTrue(p2.getPoliticaDeCreditos() instanceof PoliticaMinMaxDeCreditos);
        assertTrue(p3.getPoliticaDeCreditos() instanceof PoliticaMinMaxDeCreditos);
    }

    @Test
    public void programar() throws Exception {
        plano.programar(d2, p3);
        assertTrue(p3.getDisciplinas().contains(d2));
    }

    @Test
    public void programarIdempotente() throws Exception {
        plano.programar(d2, p3);
        plano.programar(d2, p3);
        plano.programar(d2, p3);
        assertTrue(p3.getDisciplinas().contains(d2));
    }

    @Test(expected = RequisitosException.class)
    public void programarVerificaRequisitos() throws Exception {
        plano.programar(d2, p1);
    }

    @Test(expected = PoliticaDeCreditosException.class)
    public void programarVerificaMaxDeCreditos() throws Exception {
        plano.setPeriodoAtual(p2);
        d3.setCreditos(100);
        plano.programar(d3, p1);
    }

    @Test
    public void desprogramar() throws Exception {
        plano.desprogramar(d1, p2);
        assertFalse(p2.getDisciplinas().contains(d1));
    }

    @Test
    public void desprogramarIdempotente() throws Exception {
        plano.desprogramar(d1, p2);
        plano.desprogramar(d1, p2);
        plano.desprogramar(d1, p2);
        assertFalse(p2.getDisciplinas().contains(d1));
    }

    @Test(expected = PoliticaDeCreditosException.class)
    public void desprogramarVerificaMinDeCreditos() throws Exception {
        plano.setPeriodoAtual(p2);
        plano.desprogramar(d1, p2);
    }

    @Test
    public void desprogramarRecursivo() throws Exception {
        plano.programar(d2, p3);
        plano.desprogramar(d1, p2);
        assertFalse(p2.getDisciplinas().contains(d1));
        assertFalse(p3.getDisciplinas().contains(d2));
    }

    @Test
    public void mover() throws Exception {
        assertTrue(p2.getDisciplinas().contains(d1));
        plano.mover(d1, p2, p3);
        assertFalse(p2.getDisciplinas().contains(d1));
        assertTrue(p3.getDisciplinas().contains(d1));
    }

    @Test
    public void moverNaoVerificaRequisitos() throws Exception {
        plano.programar(d2, p3);
        plano.mover(d2, p3, p1);
    }

    @Test(expected = PoliticaDeCreditosException.class)
    public void moverVerificaMinCreditos() throws Exception {
        plano.setPeriodoAtual(p2);
        plano.mover(d1, p2, p3);
    }

    @Test(expected = PoliticaDeCreditosException.class)
    public void moverVerificaMaxCreditos() throws Exception {
        plano.setPeriodoAtual(p2);
        plano.programar(d3, p2);
        d3.setCreditos(100);
        plano.mover(d3, p2, p3);
    }

    @Test
    public void toStringOverride() {
        assertEquals("Plano (3 períodos)", plano.toString());
    }

}
