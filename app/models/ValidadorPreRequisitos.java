package models;

import java.util.*;

class ValidadorPreRequisitos {

    private final Plano plano;

    private Set<Disciplina> acumuladas = new HashSet<Disciplina>();

    public ValidadorPreRequisitos(Plano plano) {
        this.plano = plano;
    }

    public void validar(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        for (Periodo p : plano.getGrade().getPeriodos()) {
            if (p.equals(periodo)) {
                break;
            }

            acumuladas.addAll(p.getDisciplinas());
        }

        validar(disciplina);
    }

    private void validar(Disciplina disciplina) throws ErroValidacaoException {
        Set<Disciplina> insatisfeitos = disciplina.getRequisitosInsatisfeitos(acumuladas);

        if (insatisfeitos.isEmpty()) {
            return;
        }

        StringBuilder b = new StringBuilder();

        Iterator<Disciplina> it = insatisfeitos.iterator();

        while (it.hasNext()) {
            b.append(it.next().getNome());
            if (it.hasNext()) {
                b.append(", ");
            }
        }

        String template = "%s tem requisitos não satisfeitos: %s.";
        String message = String.format(template, disciplina.getNome(), b.toString());
        throw new ErroValidacaoException(message);
    }
}
