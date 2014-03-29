package models;

import java.util.*;

/**
 * Verifica se uma disciplina tem requisitos insatisfeitos.
 */
class ValidarRequisitos {

    /**
     * Verifica os requisitos da disciplina dada até o período dado.
     *
     * @param plano o plano de curso
     * @param disciplina a disciplina que tem requisitos para verificar
     * @param periodo o ultimo periodo para parar de verificar (não incluído)
     * @throws RequisitosException se os requisitos não forem satisfeitos
     */
    public void validar(Plano plano, Disciplina disciplina, Periodo periodo)
        throws RequisitosException {
        if (disciplina.getRequisitos().isEmpty()) {
            return;
        }

        Set<Disciplina> requisitos = new HashSet<Disciplina>(disciplina.getRequisitos());

        Set<Disciplina> acumuladas = disciplinasAcumuladasAte(plano, periodo);

        requisitos.removeAll(acumuladas);

        if (requisitos.isEmpty()) {
            return;
        }

        throw new RequisitosException(disciplina, requisitos);
    }

    private Set<Disciplina> disciplinasAcumuladasAte(Plano plano, Periodo periodo) {
        Set<Disciplina> acumuladas = new HashSet<Disciplina>();

        for (Periodo p : plano.getGrade().getPeriodos()) {
            if (p.equals(periodo)) {
                break;
            }

            acumuladas.addAll(p.getDisciplinas());
        }

        return acumuladas;
    }

}
