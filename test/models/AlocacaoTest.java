package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class AlocacaoTest extends test.TestBase {
    @Test(expected = IllegalArgumentException.class)
    public void semestreInvalido() {
        new Alocacao(0, disciplina("Programação I"));
    }
}
