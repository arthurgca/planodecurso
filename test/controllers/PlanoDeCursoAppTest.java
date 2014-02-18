package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.status;

import java.util.HashMap;
import java.util.Map;

import models.Periodo;
import models.PlanoDeCurso;

import org.junit.Before;
import org.junit.Test;

import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeRequest;
import play.test.WithApplication;

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

	@Test
	public void deveVincularPeriodoCorretamente() {
		Map<String, String> anyData = new HashMap<String, String>();
		anyData.put("semestre", "3");
		anyData.put("disciplinas[0]", "1");
		anyData.put("disciplinas[1]", "7");

		Periodo periodo = Form.form(Periodo.class).bind(anyData).get();

		assertEquals(3, periodo.getSemestre());

		assertEquals(PlanoDeCurso.getDisciplina(1), periodo.getDisciplinas()
				.get(0));
		assertEquals(PlanoDeCurso.getDisciplina(7), periodo.getDisciplinas()
				.get(1));
	}

	@Test
	public void deveVincularPlanoDeCursoCorretamente() {
		Map<String, String> anyData = new HashMap<String, String>();
		anyData.put("periodos[0].semestre", "1");
		anyData.put("periodos[0].disciplinas[0]", "1");
		anyData.put("periodos[0].disciplinas[1]", "5");
		anyData.put("periodos[0].disciplinas[2]", "6");
		anyData.put("periodos[1].semestre", "2");
		anyData.put("periodos[1].disciplinas[0]", "7");

		PlanoDeCurso plano = Form.form(PlanoDeCurso.class).bind(anyData).get();
		Periodo periodo1 = plano.getPeriodo(1);
		Periodo periodo2 = plano.getPeriodo(2);

		assertEquals(1, periodo1.getSemestre());
		assertEquals(2, periodo2.getSemestre());

		assertEquals(PlanoDeCurso.getDisciplina(1), periodo1.getDisciplinas()
				.get(0));
		assertEquals(PlanoDeCurso.getDisciplina(5), periodo1.getDisciplinas()
				.get(1));
		assertEquals(PlanoDeCurso.getDisciplina(6), periodo1.getDisciplinas()
				.get(2));
		assertEquals(PlanoDeCurso.getDisciplina(7), periodo2.getDisciplinas()
				.get(0));
	}
}
