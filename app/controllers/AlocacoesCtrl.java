package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;

public class AlocacoesCtrl extends ControllerBase {

    public static Result criar(int semestre, int disciplinaId) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = getDisciplina(disciplinaId);
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

    public static Result mover(int semestre, int disciplinaId) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            planoDeCurso.moverDisciplina(semestre, disciplina);
        } catch (ErroDeAlocacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        String template = "<b>%s</b> foi movida para o <b>%sº período.</b>";
        String message = String.format(template, disciplina.nome, semestre);
        result.put("message", message);
        return ok(result);
    }

    public static Result deletar(int semestre, int disciplinaId) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        planoDeCurso.desalocarDisciplina(disciplina);

        String template = "<b>%s</b> foi desalocada do <b>%sº período.</b>";
        String message = String.format(template, disciplina.nome, semestre);
        result.put("message", message);
        return ok(result);
    }

}
