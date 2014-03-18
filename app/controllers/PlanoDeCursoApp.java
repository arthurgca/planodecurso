package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;
import views.html.*;

public class PlanoDeCursoApp extends AreaPrivada {

    public static Result index() {
        return ok(home.render(Usuario.find.byId(request().username())));
    }

    public static Result exibirPlanoDeCurso() {
        return ok(Json.toJson(getPlanoDeCurso()));
    }

    public static Result listarDisciplinas() {
        return ok(Json.toJson(Disciplina.getAll()));
    }

    public static Result alocarDisciplina(int semestre, int disciplinaId) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = Disciplina.get(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            planoDeCurso.alocarDisciplina(semestre, disciplina);
        } catch (ErroDeAlocacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        String template = "<b>%s</b> foi alocada no <b>%sº período.</b>";
        String message = String.format(template, disciplina.nome, semestre);
        result.put("message", message);
        return ok(result);
    }

    public static Result moverDisciplina(int paraSemestre, int disciplinaId, int deSemestre) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = Disciplina.get(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            planoDeCurso.moverDisciplina(deSemestre, paraSemestre, disciplina);
        } catch (ErroDeAlocacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        String template = "<b>%s</b> foi movida para o <b>%sº período.</b>";
        String message = String.format(template, disciplina.nome, paraSemestre);
        result.put("message", message);
        return ok(result);
    }

    public static Result desalocarDisciplina(int semestre, int disciplinaId) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = Disciplina.get(disciplinaId);
        ObjectNode result = Json.newObject();

        planoDeCurso.desalocarDisciplina(semestre, disciplina);

        String template = "<b>%s</b> foi desalocada do <b>%sº período.</b>";
        String message = String.format(template, disciplina.nome, semestre);
        result.put("message", message);
        return ok(result);
    }

}
