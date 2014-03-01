package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.data.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;

import models.*;

public class PlanoDeCursoAppTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(new config.Global()));
    }

    @Test
    public void deveRetornarPlanoIncial() {
        Result result = callAction(controllers.routes.ref.PlanoDeCursoApp
                                   .planoInicial());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void deveAlocarDisciplinas() {
        HashMap<String, String> anyData = new HashMap<String, String>();
        anyData.put("periodos[0].semestre", "1");
        anyData.put("periodos[0].disciplinas[0]", "1");
        anyData.put("periodos[0].disciplinas[1]", "5");
        anyData.put("periodos[0].disciplinas[2]", "6");
        Result result = callAction(
                                   controllers.routes.ref.PlanoDeCursoApp.alocarDisciplina(2, 7),
                                   new FakeRequest().withFormUrlEncodedBody(anyData));
        assertEquals(OK, status(result));
        assertEquals("application/json", contentType(result));
        assertEquals("utf-8", charset(result));
    }

    @Test
    public void naoDeveAlocarDisciplinasSemRequisitos() {
        HashMap<String, String> anyData = new HashMap<String, String>();
        anyData.put("periodos[0].semestre", "1");
        anyData.put("periodos[0].disciplinas[0]", "1");
        anyData.put("periodos[0].disciplinas[1]", "5");
        Result result = callAction(
                                   controllers.routes.ref.PlanoDeCursoApp.alocarDisciplina(2, 7),
                                   new FakeRequest().withFormUrlEncodedBody(anyData));
        assertEquals(Http.Status.BAD_REQUEST, status(result));
        assertEquals("application/json", contentType(result));
        assertEquals("utf-8", charset(result));
    }

    @Test
    public void deveDesalocarDisciplinas() {
        HashMap<String, String> anyData = new HashMap<String, String>();
        anyData.put("periodos[0].semestre", "1");
        anyData.put("periodos[0].disciplinas[0]", "1");
        anyData.put("periodos[0].disciplinas[1]", "5");
        Result result = callAction(
                                   controllers.routes.ref.PlanoDeCursoApp.desalocarDisciplina(5),
                                   new FakeRequest().withFormUrlEncodedBody(anyData));
        assertEquals(Http.Status.OK, status(result));
        assertEquals("application/json", contentType(result));
        assertEquals("utf-8", charset(result));
    }
}
