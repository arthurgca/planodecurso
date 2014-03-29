package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class CurriculoTest {

    Curriculo c1;

    Disciplina d1;
    Disciplina d2;
    Disciplina d3;

    @Before
    public void setUp() {
        d1 = new Disciplina("d1", 4);
        d1.setId(1L);

        d2 = new Disciplina("d2", 4);
        d2.setId(2L);

        d3 = new Disciplina("d3", 4);
        d3.setId(3L);

        c1 = new Curriculo(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d1, d2, d3
                    })));
    }

    @Test(expected = NullPointerException.class)
    public void invariantesDoConstrutor1() {
        new Curriculo(null);
    }

    @Test
    public void invariantesDoConstrutor2() {
        assertEquals(14, c1.getMaxPeriodos());
        assertEquals(14, c1.getMinCreditosPeriodo());
        assertEquals(28, c1.getMaxCreditosPeriodo());
    }

    @Test
    public void getDisciplina() {
        assertEquals(d1, c1.getDisciplina(1L));
        assertEquals(d2, c1.getDisciplina(2L));
        assertEquals(d3, c1.getDisciplina(3L));
    }

    @Test(expected = NoSuchElementException.class)
    public void getDisciplinaNaoEncontrada() {
        c1.getDisciplina(4L);
    }

    @Test
    public void toStringOverride() {
        assertEquals("Currículo Sem Nome (3 disciplinas)",
                     c1.toString());
    }

}
