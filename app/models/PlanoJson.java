package models;

import java.util.*;

import play.libs.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.annotation.*;

public class PlanoJson {

    private Set<Disciplina> disciplinasAcumuladas;

    public PlanoJson() {
        disciplinasAcumuladas = new HashSet<Disciplina>();
    }

    public JsonNode toJson(Plano plano) {
        ObjectNode node = Json.newObject();

        node.put("id", Json.toJson(plano.id));

        List<JsonNode> periodos = new LinkedList<JsonNode>();
        for (Periodo periodo : plano.getPeriodos()) {
            periodos.add(toJson(periodo));
            disciplinasAcumuladas.addAll(periodo.disciplinas);
        }

        node.put("periodos", Json.toJson(periodos));

        return node;
    }

    public JsonNode toJson(Periodo periodo) {
        ObjectNode node = Json.newObject();

        node.put("id", periodo.id);
        node.put("semestre", periodo.semestre);
        node.put("nome", periodo.getNome());
        node.put("totalCreditos", periodo.getTotalCreditos());

        List<JsonNode> disciplinas = new LinkedList<JsonNode>();
        for (Disciplina d : periodo.disciplinas) {
            disciplinas.add(toJson(d));
        }

        node.put("disciplinas", Json.toJson(disciplinas));

        List<JsonNode> ofertadas = new LinkedList<JsonNode>();

        node.put("ofertadas", Json.toJson(ofertadas));

        return node;
    }

    public JsonNode toJson(Disciplina disciplina) {
        ObjectNode node = Json.newObject();

        node.put("id", disciplina.id);
        node.put("nome", disciplina.nome);
        node.put("creditos", disciplina.creditos);
        node.put("categoria", disciplina.categoria);

        List<JsonNode> requisitos = new LinkedList<JsonNode>();
        for (Disciplina requisito : disciplina.requisitos) {
            requisitos.add(requisitoToJson(disciplina, requisito));
        }

        node.put("requisitos", Json.toJson(requisitos));

        return node;
    }

    private JsonNode requisitoToJson(Disciplina disciplina, Disciplina requisito) {
        ObjectNode node = Json.newObject();

        node.put("id", requisito.id);
        node.put("nome", requisito.nome);
        node.put("creditos", requisito.creditos);
        node.put("categoria", requisito.categoria);

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
