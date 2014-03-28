package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
@DiscriminatorValue("None")
public class PoliticaDeCreditosNula extends PoliticaDeCreditos {

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

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public String validar(Periodo periodo) {
        Parametro.naoNulo("periodo", periodo);

        return null;
    }

}
