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
        Curriculo curriculo = Curriculo.find.byId(1);
        return curriculo.getDisciplina(nome);
    }

    protected FakeRequest sessaoAutenticada() {
        return fakeRequest().withSession("email", "bob@example.com");
    }

}
