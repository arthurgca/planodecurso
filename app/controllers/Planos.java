package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;

public class Planos extends AreaPrivada {

    public static Result listar() {
        return ok(PlanoJson.toJson(Plano.find.all()));
    }

    public static Result exibir(Long planoId) {
        return ok(new PlanoJson(Plano.find.byId(planoId)).toJson());
    }

    public static Result criar(int curriculoId, Long gradeId) {
        Curriculo curriculo = Curriculo.find.byId(curriculoId);
        Grade grade = Grade.find.byId(gradeId);
        Plano plano = new Plano(curriculo, grade);
        plano.save();

        Usuario usuario = getUsuarioAtual();
        usuario.setPlano(plano);
        usuario.save();

        return ok(new PlanoJson(plano).toJson());
    }

    public static Result programar(Long planoId, Long disciplinaId, int periodo) {
        Plano plano = Plano.find.byId(planoId);
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
        Plano plano = Plano.find.byId(planoId);
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
        Plano plano = Plano.find.byId(planoId);
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
