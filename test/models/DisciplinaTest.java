package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class DisciplinaTest {

    Disciplina d0;

    @Before
    public void setUp() {
        d0 = new Disciplina("D0", 4);
        d0.setId(1L);
    }

    @Test(expected = NullPointerException.class)
    public void invariantesDoConstrutor() {
        new Disciplina(null, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invariantesDoConstrutor2() {
        new Disciplina("", 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invariantesDoConstrutor3() {
        new Disciplina("D0", -1);
    }

    @Test
    public void toStringOverride() {
        assertEquals("D0 (1)", d0.toString());
    }

}
