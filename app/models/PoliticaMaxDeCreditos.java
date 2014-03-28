package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Uma política que não permite um total de créditos maior que o especificado.
 */
@Entity
@DiscriminatorValue("Max")
public class PoliticaMaxDeCreditos extends PoliticaDeCreditos {

    private int maxCreditos;

    /**
     * Constrói a política de créditos máximo com o valor dado.
     *
     * @param maxCreditos
     * @throws IllegalArgumentException se {@code maxCreditos < 1}
     */
    public PoliticaMaxDeCreditos(int maxCreditos) {
        Parametro.maiorQueZero("maxCreditos", maxCreditos);

        setMaxCreditos(maxCreditos);
    }

    /**
     * @return o máximo de créditos permitido
     */
    public int getMaxCreditos() {
        return maxCreditos;
    }

    /**
     * @param maxCreditos o máximo de créditos
     * @throws IllegalArgumentException se {@code maxCreditos < 1}
     */
    public void setMaxCreditos(int maxCreditos) {
        Parametro.maiorQueZero("maxCreditos", maxCreditos);

        this.maxCreditos = maxCreditos;
    }

    /**
     * {@inheritDoc}
     */
    public boolean podeProgramar(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        if (periodo.getDisciplinas().contains(disciplina)) {
            return true;
        }

        return periodo.getTotalCreditos() + disciplina.getCreditos() <= maxCreditos;
    }

    /**
     * {@inheritDoc}
     */
    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public String validar(Periodo periodo) {
        Parametro.naoNulo("periodo", periodo);

        if (periodo.getTotalCreditos() > maxCreditos) {
            return "Máximo de créditos excedido";
        }

        return null;
    }

}
