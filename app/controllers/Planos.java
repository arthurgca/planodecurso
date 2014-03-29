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
        return ok(new PlanoJson().toJson(Plano.find.byId(planoId)));
    }

    public static Result configurar(int curriculoId, Long gradeId, int periodo) {
        Curriculo curriculo = Curriculo.find.byId(curriculoId);
        Grade grade = Grade.copiar(Grade.find.byId(gradeId));
        Plano plano = new Plano(curriculo, grade);

        plano.setPeriodoAtual(grade.getPeriodo(periodo));

        plano.save();

        Estudante estudante = getUsuarioAtual();
        estudante.setPlano(plano);
        estudante.save();

        return ok(new PlanoJson().toJson(plano));
    }

    public static Result programar(Long planoId, Long disciplinaId, int periodo) {
        Plano plano = Plano.find.byId(planoId);
        Disciplina disciplina = plano.getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            plano.programar(disciplina, plano.getPeriodo(periodo));
        } catch (PoliticaDeCreditosException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        } catch (RequisitosException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        plano.save();

        String template = "%s foi alocada no %s.";
        String message = String.format(
          template,
          disciplina.getNome(),
          plano.getPeriodo(periodo).getNome());

        result.put("message", message);
        return ok(result);
    }

    public static Result mover(Long planoId, Long disciplinaId, int de, int para) {
        Plano plano = Plano.find.byId(planoId);
        Disciplina disciplina = plano.getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            plano.mover(disciplina, plano.getPeriodo(de), plano.getPeriodo(para));
        } catch (PoliticaDeCreditosException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        plano.save();

        String template = "%s foi movida para o %s.";
        String message = String.format(
          template,
          disciplina.getNome(),
          plano.getPeriodo(para).getNome());

        result.put("message", message);
        return ok(result);
    }

    public static Result desprogramar(Long planoId, Long disciplinaId, int periodo) {
        Plano plano = Plano.find.byId(planoId);
        Disciplina disciplina = plano.getCurriculo().getDisciplina(disciplinaId);
        ObjectNode result = Json.newObject();

        try {
            plano.desprogramar(disciplina, plano.getPeriodo(periodo));
        } catch (PoliticaDeCreditosException e) {
            result.put("message", e.getMessage());
            return badRequest(result);
        }

        plano.save();

        String template = "%s foi desalocada do %s.";
        String message = String.format(
          template,
          disciplina.getNome(),
          plano.getPeriodo(periodo).getNome());

        result.put("message", message);
        return ok(result);
    }
}
