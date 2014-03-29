package models;

import java.util.*;

/**
 * Sinaliza que existem requisitos insatisfeitos numa disciplina.
 */
public class RequisitosException extends Exception {

    public RequisitosException(String message) {
        super(message);
    }

    public RequisitosException(Disciplina disciplina, Set<Disciplina> requisitos) {
        this(criarMensagem(disciplina, requisitos));
    }

    private static String criarMensagem(Disciplina disciplina, Set<Disciplina> requisitos) {
        StringBuilder builder = new StringBuilder();

        Iterator<Disciplina> it = requisitos.iterator();

        while (it.hasNext()) {
            builder.append(it.next().getNome());

            if (it.hasNext())
                builder.append(", ");
        }

        return String.format("%s tem requisitos não satisfeitos: %s.",
                             disciplina.getNome(),
                             builder.toString());
    }

}
