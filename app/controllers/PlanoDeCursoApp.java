package controllers;

import models.CatalogoDeDisciplinas;
import models.Disciplina;
import models.ErroDeAlocacaoException;
import models.PlanoDeCurso;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class PlanoDeCursoApp extends Controller {

	private static Form<PlanoDeCurso> planoDeCursoForm = Form
			.form(PlanoDeCurso.class);

	public static Result planoInicial() throws ErroDeAlocacaoException {
		return ok(Json.toJson(PlanoDeCurso
				.getPlanoInicial(getCatalogoDeDisciplinas())));
	}

	public static Result alocarDisciplina(int semestre, int disciplinaId) {
		Disciplina disciplina = getCatalogoDeDisciplinas().get(disciplinaId);
		PlanoDeCurso planoDeCurso = planoDeCursoForm.bindFromRequest().get();
		try {
			planoDeCurso.alocar(semestre, disciplina);
			return ok(Json.toJson(planoDeCurso));
		} catch (ErroDeAlocacaoException e) {
			return badRequest(Json.toJson(e.getMessage()));
		}
	}

	public static Result desalocarDisciplina(int disciplinaId) {
		Disciplina disciplina = getCatalogoDeDisciplinas().get(disciplinaId);
		PlanoDeCurso planoDeCurso = planoDeCursoForm.bindFromRequest().get();
		planoDeCurso.desalocar(disciplina);
		return ok(Json.toJson(disciplina));
	}

	public static CatalogoDeDisciplinas getCatalogoDeDisciplinas() {
		return config.Global.getCatalogoDeDisciplinas();
	}

}
