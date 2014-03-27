package models;

import java.util.*;

import play.libs.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.annotation.*;

public class PlanoJson {

    private Set<Disciplina> disciplinasOfertadas;

    private Set<Disciplina> disciplinasAcumuladas;

    private Plano plano;

    public PlanoJson() {
        disciplinasAcumuladas = new HashSet<Disciplina>();
    }

    public JsonNode toJson(Plano plano) {
        this.plano = plano;

        if (plano == null) {
            return Json.toJson(null);
        }

        disciplinasOfertadas =
            new HashSet<Disciplina>(plano.getCurriculo().getDisciplinas());
        disciplinasOfertadas.removeAll(plano.getDisciplinas());

        ObjectNode node = Json.newObject();

        node.put("id", Json.toJson(plano.getId()));

        node.put("periodoAtual", plano.getPeriodoAtual());

        List<JsonNode> periodos = new LinkedList<JsonNode>();
        for (Periodo periodo : plano.getPeriodos()) {
            periodos.add(toJson(periodo));
            disciplinasAcumuladas.addAll(periodo.getDisciplinas());
        }

        node.put("periodos", Json.toJson(periodos));

        return node;
    }

    private JsonNode toJson(Periodo periodo) {
        ObjectNode node = Json.newObject();

        node.put("id", periodo.getId());
        node.put("semestre", periodo.getSemestre());
        node.put("nome", periodo.getNome());
        node.put("totalCreditos", periodo.getTotalCreditos());

        List<JsonNode> disciplinas = new LinkedList<JsonNode>();
        for (Disciplina disciplina : periodo.getDisciplinas()) {
            disciplinas.add(toJson(disciplina));
        }

        node.put("disciplinas", Json.toJson(disciplinas));

        List<JsonNode> ofertadas = new LinkedList<JsonNode>();
        for (Disciplina d : disciplinasOfertadas) {
            ofertadas.add(toJson(d));
        }

        node.put("ofertadas", Json.toJson(ofertadas));

        node.put("isPassado", false);
        node.put("isFuturo", false);
        node.put("isAtual", false);

        if (periodo.getSemestre() < plano.getPeriodoAtual()) {
            node.put("isPassado", true);
        } else if (periodo.getSemestre() > plano.getPeriodoAtual()) {
            node.put("isFuturo", true);
        } else {
            node.put("isAtual", true);
        }

        return node;
    }

    private JsonNode toJson(Disciplina disciplina) {
        ObjectNode node = Json.newObject();

        node.put("id", disciplina.getId());
        node.put("nome", disciplina.getNome());
        node.put("creditos", disciplina.getCreditos());
        node.put("categoria", disciplina.getCategoria());

        List<JsonNode> requisitos = new LinkedList<JsonNode>();
        for (Disciplina requisito : disciplina.getRequisitos()) {
            requisitos.add(requisitoToJson(disciplina, requisito));
        }

        node.put("requisitos", Json.toJson(requisitos));

        return node;
    }

    private JsonNode requisitoToJson(Disciplina disciplina, Disciplina requisito) {
        ObjectNode node = Json.newObject();

        node.put("id", requisito.getId());
        node.put("nome", requisito.getNome());
        node.put("creditos", requisito.getCreditos());
        node.put("categoria", requisito.getCategoria());

        if (disciplina.getRequisitosInsatisfeitos(disciplinasAcumuladas)
            .contains(requisito)) {
            node.put("isSatisfeito", false);
        } else {
            node.put("isSatisfeito", true);
        }

        return node;
    }

    public static JsonNode toJson(Collection<Plano> planos) {
        List<JsonNode> nodes = new LinkedList<JsonNode>();
        for (Plano plano : planos) {
            nodes.add(new PlanoJson().toJson(plano));
        }
        return Json.toJson(nodes);
    }

}
