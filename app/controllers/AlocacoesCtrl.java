package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;

public class AlocacoesCtrl extends ControllerBase {

    public static Result criar(int semestre, int disciplina) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        try {
            planoDeCurso.alocarDisciplina(semestre, getDisciplina(disciplina));
            return ok(Json.toJson(planoDeCurso));
        } catch (ErroDeAlocacaoException e) {
            ObjectNode result = Json.newObject();
            result.put("message", e.getMessage());
            return badRequest(result);
        }
    }

    public static Result deletar(int semestre, int disciplina) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        planoDeCurso.desalocarDisciplina(getDisciplina(disciplina));
        return ok(Json.toJson(planoDeCurso));
    }

}
