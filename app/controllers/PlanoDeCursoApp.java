package controllers;

import models.CatalogoDeDisciplinas;
import models.ErroDeAlocacaoException;
import models.PlanoDeCurso;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class PlanoDeCursoApp extends Controller {
	
	public static Result planoInicial() throws ErroDeAlocacaoException {
		return ok(Json.toJson(PlanoDeCurso.getPlanoInicial(getCatalogoDeDisciplinas())));
	}
	
	public static CatalogoDeDisciplinas getCatalogoDeDisciplinas() {
		return config.Global.getCatalogoDeDisciplinas();
	}
	
}
