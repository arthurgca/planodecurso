package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Uma política que não permite um total de créditos menor que o especificado.
 */
@Entity
@DiscriminatorValue("Min")
public class PoliticaMinDeCreditos extends PoliticaDeCreditos {

    private int minCreditos;

    /**
     * Constrói a política de créditos mínimos com o valor dado.
     *
     * @param minCreditos
     * @throws IllegalArgumentException se {@code minCreditos < 1}
     */
    public PoliticaMinDeCreditos(int minCreditos) {
        Parametro.maiorQueZero("minCreditos", minCreditos);

        setMinCreditos(minCreditos);
    }

    /**
     * @return o mínimo de créditos permitido
     */
    public int getMinCreditos() {
        return minCreditos;
    }

    /**
     * @param minCreditos o mínimo de créditos
     * @throws IllegalArgumentException se {@code minCreditos < 1}
     */
    public void setMinCreditos(int minCreditos) {
        Parametro.maiorQueZero("minCreditos", minCreditos);

        this.minCreditos = minCreditos;
    }

    /**
     * {@inheritDoc}
     */
    public boolean podeProgramar(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        if (!periodo.getDisciplinas().contains(disciplina)) {
            return true;
        }

        return periodo.getTotalCreditos() - disciplina.getCreditos() >= minCreditos;
    }

    /**
     * {@inheritDoc}
     */
    public String validar(Periodo periodo) {
        Parametro.naoNulo("periodo", periodo);

        if (periodo.getTotalCreditos() < minCreditos) {
            return "Mínimo de créditos não atingido";
        }

        return null;
    }

}
