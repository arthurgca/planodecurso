package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Uma política que combina PoliticaMinDeCreditos e PoliticaMaxDeCreditos.
 */
@Entity
@DiscriminatorValue("MinMax")
public class PoliticaMinMaxDeCreditos extends PoliticaDeCreditos {

    private int minCreditos;

    private int maxCreditos;

    /**
     * Constrói a política de créditos com os valores dados.
     *
     * @param minCreditos
     * @param maxCreditos
     * @throws IllegalArgumentException se {@code minCreditos < 0} ou {@code maxCreditos < 0}
     */
    public PoliticaMinMaxDeCreditos(int minCreditos, int maxCreditos) {
       Parametro.maiorQueZero("minCreditos", minCreditos);
       Parametro.maiorQueZero("maxCreditos", maxCreditos);

       setMinCreditos(minCreditos);
       setMaxCreditos(maxCreditos);
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

        PoliticaDeCreditos min = new PoliticaMinDeCreditos(getMinCreditos());
        PoliticaDeCreditos max = new PoliticaMaxDeCreditos(getMaxCreditos());

        return min.podeProgramar(disciplina, periodo)
            && max.podeProgramar(disciplina, periodo);
    }

    /**
     * {@inheritDoc}
     */
    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        PoliticaDeCreditos min = new PoliticaMinDeCreditos(getMinCreditos());
        PoliticaDeCreditos max = new PoliticaMaxDeCreditos(getMaxCreditos());

        return min.podeDesprogramar(disciplina, periodo)
            && max.podeDesprogramar(disciplina, periodo);
    }

    /**
     * {@inheritDoc}
     */
    public String validar(Periodo periodo) {
        Parametro.naoNulo("periodo", periodo);

        PoliticaDeCreditos min = new PoliticaMinDeCreditos(getMinCreditos());
        PoliticaDeCreditos max = new PoliticaMaxDeCreditos(getMaxCreditos());

        String erroMin = min.validar(periodo);
        String erroMax = max.validar(periodo);
        return erroMin == null ? erroMax : erroMin;
    }

}
