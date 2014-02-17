package controllers;

import org.junit.*;

import play.mvc.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class PlanoDeCursoAppTest {
	@Test
	public void deveRetornarPlanoIncial() {
		Result result = callAction(controllers.routes.ref.PlanoDeCursoApp.planoInicial());
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		assertThat(charset(result)).isEqualTo("utf-8");
	}
}
