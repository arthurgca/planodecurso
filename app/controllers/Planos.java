package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;

public class Planos extends AreaPrivada {

    public static Result listar() {
        return ok(Json.toJson(PlanoDeCurso.find.all()));
    }

    public static Result exibir(Long planoId) {
        return ok(Json.toJson(PlanoDeCurso.find.byId(planoId)));
    }

    public static Result criar(int curriculoId, Long gradeId) {
        Curriculo curriculo = Curriculo.find.byId(curriculoId);
        Grade grade = Grade.find.byId(gradeId);
        PlanoDeCurso plano = new PlanoDeCurso(curriculo, grade);
        plano.save();

        Usuario usuario = getUsuarioAtual();
        usuario.setPlanoDeCurso(plano);
        usuario.save();

        return ok(Json.toJson(plano));
    }

    public static Result programar(Long planoId, Long disciplinaId, int periodo) {
        PlanoDeCurso plano = PlanoDeCurso.find.byId(planoId);
        Disciplina disciplina = plano.curriculo.getDisciplina(disciplinaId);
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

    public static Result mover(Long planoId, Long disciplinaId, int de, int para) {
        PlanoDeCurso plano = PlanoDeCurso.find.byId(planoId);
        Disciplina disciplina = plano.curriculo.getDisciplina(disciplinaId);
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

    public static Result desprogramar(Long planoId, Long disciplinaId, int periodo) {
        PlanoDeCurso plano = PlanoDeCurso.find.byId(planoId);
        Disciplina disciplina = plano.curriculo.getDisciplina(disciplinaId);
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
