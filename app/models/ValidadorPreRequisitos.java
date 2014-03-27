package models;

import java.util.*;

class ValidadorPreRequisitos implements Validador {

    private Curriculo curriculo;

    private Set<Disciplina> acumuladas = new HashSet<Disciplina>();

    private Disciplina alvo;

    public ValidadorPreRequisitos(Curriculo curriculo) {
        this(curriculo, null);
    }

    public ValidadorPreRequisitos(Curriculo curriculo, Disciplina alvo) {
        this.curriculo = curriculo;
        this.alvo = alvo;
    }

    public void validar(Plano plano) throws ErroValidacaoException {
        validar(plano.getGrade());
    }

    private void validar(Grade grade) throws ErroValidacaoException {
        for (Periodo periodo : grade.getPeriodos()) {
            validar(periodo);
            acumuladas.addAll(periodo.getDisciplinas());
        }
    }

    private void validar(Periodo periodo) throws ErroValidacaoException {
        for (Disciplina disciplina : periodo.getDisciplinas()) {
            validar(disciplina);
        }
    }

    private void validar(Disciplina disciplina) throws ErroValidacaoException {
        if (alvo != null && !alvo.equals(disciplina)) {
            return;
        }

        Set<Disciplina> insatisfeitos =
            disciplina.getRequisitosInsatisfeitos(acumuladas);

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
