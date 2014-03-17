package controllers;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;
import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;

public class ListarDisciplinasTest extends test.TestBase {
    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.listarDisciplinas(),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.listarDisciplinas());
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
    }
}
