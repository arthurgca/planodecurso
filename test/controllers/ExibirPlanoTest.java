package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import static play.mvc.Http.Status.*;
import play.test.*;
import static play.test.Helpers.*;

import models.*;

public class ExibirPlanoTest extends TestBase {

    Plano p1;

    @Before
    public void setUp() {
        carregarTestData();
        p1 = criarPlanoInicial();
    }

    @Test
    public void sucesso() {
        Result result = callAction(
                                   controllers.routes.ref.Planos.exibir(p1.getId()),
          sessaoAutenticada());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(controllers.routes.ref.Planos.exibir(p1.getId()));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
    }
}
