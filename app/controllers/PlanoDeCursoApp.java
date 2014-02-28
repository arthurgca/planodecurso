package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.ErroDeAlocacaoException;
import models.PlanoDeCurso;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class PlanoDeCursoApp extends Controller {

    private static Form<PlanoDeCurso> form = Form.form(PlanoDeCurso.class);

    public static Result planoInicial() throws ErroDeAlocacaoException {
        return ok(Json.toJson(PlanoDeCurso.getPlanoInicial()));
    }

    public static Result alocarDisciplina(int semestre, int disciplinaId) {
        PlanoDeCurso planoDeCurso = form.bindFromRequest().get();
        try {
            planoDeCurso.alocar(semestre, disciplinaId);
            return ok(Json.toJson(planoDeCurso));
        } catch (ErroDeAlocacaoException e) {
            ObjectNode result = Json.newObject();
            result.put("message", e.getMessage());
            return badRequest(result);
        }
    }

    public static Result desalocarDisciplina(int disciplinaId) {
        PlanoDeCurso planoDeCurso = form.bindFromRequest().get();
        planoDeCurso.desalocar(disciplinaId);
        return ok(Json.toJson(planoDeCurso));
    }

}
