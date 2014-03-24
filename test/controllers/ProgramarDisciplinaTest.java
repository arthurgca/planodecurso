package controllers;

import java.util.*;

import models.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;

public class ProgramarDisciplinaTest extends TestBase {

    PlanoDeCurso p1;

    @Before
    public void setUp() {
        carregarTestData();
        p1 = criarPlanoInicial();
    }

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.Planos.programar(p1.id, 4L, 2),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroMaximoDeCreditosExcedido() {
        Result result = callAction(
            controllers.routes.ref.Planos.programar(p1.id, 4L, 1),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroRequisitosInsatisfeitos() {
        Result result = callAction(
            controllers.routes.ref.Planos.programar(p1.id, 6L, 2),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.Planos.programar(p1.id, 4L, 2));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
    }
}
