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

public class ConfigurarPlanoTest extends TestBase {

    Curriculo c1;

    Grade g1;

    @Before
    public void setUp() {
        carregarTestData();

        c1 = Curriculo.find.byId(1);
        g1 = Grade.find.byId(1L);
    }

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.Planos.configurar(c1.id, g1.id, 2),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.Planos.configurar(c1.id, g1.id, 2));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
    }
}
