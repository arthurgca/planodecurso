package models;

import java.util.*;
import javax.validation.*;

import play.data.validation.ValidationError;

public class PlanoDeCurso {

    private static final int MINIMO_CREDITOS = 14;

    private static final int MAXIMO_CREDITOS = 28;

    private static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    @Valid
    private List<Periodo> periodos;

    public PlanoDeCurso() {
        this.periodos = new ArrayList<Periodo>();
    }

    public PlanoDeCurso(Periodo... periodos) {
        this.periodos = new ArrayList<Periodo>();

        for (Periodo periodo : periodos) {
            if (periodo != null) {
                this.periodos.add(periodo);
            }
        }
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public boolean isEmpty() {
        return getPeriodos().isEmpty();
    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();

        validateMinimoCreditos(errors);
        validateMaximoCreditos(errors);

        return errors.isEmpty() ? null : errors;
    }

    private void validateMinimoCreditos(List<ValidationError> errors) {
        String template = "%sº Período deve ter um mínimo de %s créditos.";
        for (Periodo periodo : periodos) {
            if (!periodo.isEmpty() && periodo.getTotalCreditos() < MINIMO_CREDITOS) {
                String message = String.format(template, periodo.getSemestre(), MINIMO_CREDITOS);
                errors.add(new ValidationError("", message));
            }
        }
    }

    private void validateMaximoCreditos(List<ValidationError> errors) {
        String template = "%sº Período deve ter um máximo de %s créditos.";
        for (Periodo periodo : periodos) {
            if (!periodo.isEmpty() && periodo.getTotalCreditos() > MAXIMO_CREDITOS) {
                String message = String.format(template, periodo.getSemestre(), MAXIMO_CREDITOS);
                errors.add(new ValidationError("", message));
            }
        }
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
