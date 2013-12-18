package models;

import java.util.*;
import javax.validation.*;

import play.data.validation.Constraints.*;

public class PlanoDeCurso {

    static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    @Valid
    private List<Periodo> periodos;

    public PlanoDeCurso() {
        this.periodos = new ArrayList<Periodo>();
    }

    public PlanoDeCurso(Periodo... periodos) {
        this.periodos = new ArrayList<Periodo>();

        for (Periodo periodo : periodos) {
            if (periodo != null)
                this.periodos.add(periodo);
        }
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public static PlanoDeCurso criarPlanoInicial() {
        Periodo periodoFera = new Periodo(1,
                                          disciplinas.get("CALCULO1"),
                                          disciplinas.get("VETORIAL"),
                                          disciplinas.get("LPT"),
                                          disciplinas.get("P1"),
                                          disciplinas.get("IC"),
                                          disciplinas.get("LP1"));
        return new PlanoDeCurso(periodoFera);
    }

}
