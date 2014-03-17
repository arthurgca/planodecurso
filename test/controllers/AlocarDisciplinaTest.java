package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;

public class AlocarDisciplinaTest extends test.TestBase {

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.alocarDisciplina(
                7,
                disciplina("Futsal").id),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroMaximoDeCreditosExcedido() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.alocarDisciplina(
                3,
                disciplina("Futsal").id),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.alocarDisciplina(
                7,
                disciplina("Futsal").id));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
    }

}
