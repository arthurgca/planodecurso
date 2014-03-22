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

public class ProgramarDisciplinaTest extends test.TestBase {

    Curriculo c1;

    @Before
    public void setUp() {
        carregarTestData();
        criarPlanoInicial();

        c1 = Curriculo.find.all().get(0);
    }

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.programar(
              c1.getDisciplina("Disciplina Introdutória I").id, 1),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroMaximoDeCreditosExcedido() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.programar(
              c1.getDisciplina("Disciplina Eletiva Introdutória").id, 4),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroRequisitosInsatisfeitos() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.programar(
              c1.getDisciplina("Disciplina Eletiva Avançada").id, 1),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.programar(
              c1.getDisciplina("Disciplina Introdutória I").id, 1));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
    }
}
