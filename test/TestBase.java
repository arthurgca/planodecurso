package test;

import java.util.*;

import org.junit.*;

import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;

import models.*;

public abstract class TestBase extends WithApplication {

    @Before
    public void setUpFixtures() {
        start(fakeApplication(inMemoryDatabase()));
    }

    protected Disciplina disciplina(String nome) {
        return Disciplina.Registro.get(nome);
    }

}
