package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Uma estratégia para validar o total de créditos em um período.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PoliticaDeCreditos extends Model {

    @Id
    private int id;

    /**
     * @return se é possível programar uma disciplina no período dado
     * @throws NullPointerException se disciplina ou periodo são nulos
     */
    public abstract boolean podeProgramar(Disciplina disciplina, Periodo periodo);

    /**
     * @return se é possível desprogramar uma disciplina no período dado
     * @throws NullPointerException se disciplina ou periodo são nulos
     */
    public abstract boolean podeDesprogramar(Disciplina disciplina, Periodo periodo);

    /**
     * @return null se o total de créditos for válido, uma explicação caso contrário
     * @throws NullPointerException se {@code periodo == null}
     */
    public abstract String validar(Periodo periodo);

    /**
     * @return o id usado por esse entidade
     */
    public int getId() {
        return id;
    }

    /**
     * @param id o id usado para esse entidade
     * @throws IllegalArgumentException se {@code id < 1}
     */
    public void setId(int id) {
        Parametro.maiorQueZero("id", id);

        this.id = id;
    }

}
