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

    public static Result exibirPlano() {
        return ok(new PlanoJson(getPlano()).toJson());
    }

    public static Result listarDisciplinas() {
        return ok(Json.toJson(getCurriculo().disciplinas));
    }

    public static Result programar(Long disciplinaId, int periodo) {
        Plano plano = getPlano();
        Disciplina disciplina = getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            plano.programar(disciplina, periodo);
        } catch (ErroValidacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        plano.save();

        String template = "%s foi alocada no %s.";
        String message = String.format(
          template,
          disciplina.nome,
          plano.getPeriodo(periodo).getNome());

        result.put("message", message);
        return ok(result);
    }

    public static Result mover(Long disciplinaId, int de, int para) {
        Plano plano = getPlano();
        Disciplina disciplina = getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            plano.mover(disciplina, de, para);
        } catch (ErroValidacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        plano.save();

        String template = "%s foi movida para o %s.";
        String message = String.format(
          template,
          disciplina.nome,
          plano.getPeriodo(para).getNome());

        result.put("message", message);
        return ok(result);
    }

    public static Result desprogramar(Long disciplinaId, int periodo) throws ErroValidacaoException {
        Plano plano = getPlano();
        Disciplina disciplina = getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            plano.desprogramar(disciplina, periodo);
        } catch (ErroValidacaoException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        plano.save();

        String template = "%s foi desalocada do %s.";
        String message = String.format(
          template,
          disciplina.nome,
          plano.getPeriodo(periodo).getNome());

        result.put("message", message);
        return ok(result);
    }
}
