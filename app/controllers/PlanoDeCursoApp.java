package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;
import views.html.*;

public class PlanoDeCursoApp extends AreaPrivada {

    public static Result index() {
        return ok(home.render(getUsuarioAtual()));
    }

    public static Result exibirPlanoDeCurso() {
        return ok(Json.toJson(getPlanoDeCurso()));
    }

    public static Result listarDisciplinas() {
        return ok(Json.toJson(getCurriculo().disciplinas));
    }

    public static Result programar(Long disciplinaId, int periodo) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            planoDeCurso.programar(disciplina, periodo);
        } catch (ErroValidacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        planoDeCurso.save();

        String template = "%s foi alocada no %s.";
        String message = String.format(
          template,
          disciplina.nome,
          planoDeCurso.getPeriodo(periodo).getNome());

        result.put("message", message);
        return ok(result);
    }

    public static Result mover(Long disciplinaId, int de, int para) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            planoDeCurso.mover(disciplina, de, para);
        } catch (ErroValidacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        planoDeCurso.save();

        String template = "%s foi movida para o %s.";
        String message = String.format(
          template,
          disciplina.nome,
          planoDeCurso.getPeriodo(para).getNome());

        result.put("message", message);
        return ok(result);
    }

    public static Result desprogramar(Long disciplinaId, int periodo) {
        PlanoDeCurso planoDeCurso = getPlanoDeCurso();
        Disciplina disciplina = getCurriculo().getDisciplina(disciplinaId);

        planoDeCurso.desprogramar(disciplina, periodo);
        planoDeCurso.save();

        String template = "%s foi desalocada do %s.";
        String message = String.format(
          template,
          disciplina.nome,
          planoDeCurso.getPeriodo(periodo).getNome());

        ObjectNode result = Json.newObject();
        result.put("message", message);
        return ok(result);
    }
}
