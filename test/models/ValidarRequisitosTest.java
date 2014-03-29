package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class ValidarRequisitosTest {

    Disciplina d1;

    Disciplina d2;

    Grade g1;

    Plano plano;

    @Before
    public void setUp() throws Exception {
        d1 = new Disciplina("D1", 4);
        d2 = new Disciplina("D2", 4);

        d2.setRequisitos(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d1
                    })));

        Curriculo c1 = new Curriculo(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d1, d2
                    })));

        c1.setMaxPeriodos(3);

        g1 = new Grade(3);

        g1.programar(d1, g1.getPeriodo(2));

        plano = new Plano(c1, g1);
    }

    @Test(expected = RequisitosException.class)
    public void validarRequisitos1() throws Exception {
        new ValidarRequisitos().validar(plano, d2, g1.getPeriodo(1));
    }

    @Test(expected = RequisitosException.class)
    public void validarRequisitos2() throws Exception {
        new ValidarRequisitos().validar(plano, d2, g1.getPeriodo(2));
    }

    public void validarRequisitos3() throws Exception {
        new ValidarRequisitos().validar(plano, d2, g1.getPeriodo(3));
    }

}
