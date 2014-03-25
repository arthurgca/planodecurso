package controllers;

import java.util.*;

import org.junit.*;

import play.libs.*;
import play.test.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import static play.test.Helpers.*;

import models.*;

public abstract class TestBase extends WithApplication {

    @Before
    public void setUpApplication() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
    }

    protected Plano criarPlanoInicial() {
        Usuario bob = Usuario.find.byId("bob@example.com");
        bob.setPlano(Plano.criarPlanoInicial());
        bob.save();
        return bob.plano;
    }

    protected FakeRequest sessaoAutenticada() {
        return fakeRequest().withSession("email", "bob@example.com");
    }

    protected void carregarTestData() {
        @SuppressWarnings("unchecked")
        Map<String,List<Object>> all =
            (Map<String,List<Object>>) Yaml.load("test-data.yml");

        Ebean.save(all.get("usuarios"));

        Ebean.save(all.get("curriculos"));

        for (Object curriculo : all.get("curriculos")) {
            for(Disciplina disciplina : ((Curriculo) curriculo).disciplinas) {
                Ebean.saveManyToManyAssociations(disciplina, "requisitos");
            }
        }

        Ebean.save(all.get("grades"));

        for (Object grade : all.get("grades")) {
            for(Periodo periodo : ((Grade) grade).periodos) {
                Ebean.saveManyToManyAssociations(periodo, "disciplinas");
            }
        }
    }

}
