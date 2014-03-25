package models;

import java.util.*;

import play.libs.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

public class PlanoJson {

    @JsonIgnore
    private final Plano plano;

    private Set<Disciplina> disciplinasAcumuladas = new HashSet<Disciplina>();

    public PlanoJson(Plano plano) {
        this.plano = plano;
    }

    public Long getId() {
        return plano.id;
    }

    public List<PeriodoJson> getPeriodos() {
        List<PeriodoJson> resultado = new LinkedList<PeriodoJson>();
        for (Periodo p : plano.getPeriodos()) {
            resultado.add(new PeriodoJson(p));
            disciplinasAcumuladas.addAll(p.disciplinas);
        }
        return resultado;
    }

    @JsonIgnore
    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public static JsonNode toJson(Collection<Plano> planos) {
        List<PlanoJson> nodes = new LinkedList<PlanoJson>();
        for (Plano plano : planos) {
            nodes.add(new PlanoJson(plano));
        }
        return Json.toJson(nodes);
    }

    private class PeriodoJson {
        @JsonIgnore
        private final Periodo periodo;

        public PeriodoJson(Periodo periodo) {
            this.periodo = periodo;
        }

        public Long getId() {
            return periodo.id;
        }

        public int getSemestre() {
            return periodo.semestre;
        }

        public String getNome() {
            return periodo.getNome();
        }

        public int getTotalCreditos() {
            return periodo.getTotalCreditos();
        }

        public List<DisciplinaJson> getDisciplinas() {
            List<DisciplinaJson> resultado = new LinkedList<DisciplinaJson>();
            for (Disciplina d : periodo.disciplinas) {
                resultado.add(new DisciplinaJson(d));
            }
            return resultado;
        }
    }

    private class DisciplinaJson {
        @JsonIgnore
        private final Disciplina disciplina;

        public DisciplinaJson(Disciplina disciplina) {
            this.disciplina = disciplina;
        }

        public Long getId() {
            return disciplina.id;
        }

        public String getNome() {
            return disciplina.nome;
        }

        public int getCreditos() {
            return disciplina.creditos;
        }

        public String getCategoria() {
            return disciplina.categoria;
        }

        public List<RequisitoJson> getRequisitos() {
            List<RequisitoJson> resultado =
                new LinkedList<RequisitoJson>();

            for (Disciplina d : disciplina.requisitos) {
                resultado.add(
                    new RequisitoJson(d, isSatisfeito(d, disciplina)));
            }

            return resultado;
        }

        private boolean isSatisfeito(Disciplina requisito, Disciplina disciplina) {
            return !disciplina.getRequisitosInsatisfeitos(disciplinasAcumuladas)
                .contains(requisito);
        }
    }

    private class RequisitoJson {
        @JsonIgnore
        private final Disciplina requisito;

        public boolean isSatisfeito;

        public RequisitoJson(Disciplina requisito, boolean isSatisfeito) {
            this.requisito = requisito;
            this.isSatisfeito = isSatisfeito;
        }

        public Long getId() {
            return requisito.id;
        }

        public String getNome() {
            return requisito.nome;
        }

        public int getCreditos() {
            return requisito.creditos;
        }

        public String getCategoria() {
            return requisito.categoria;
        }
    }

}
