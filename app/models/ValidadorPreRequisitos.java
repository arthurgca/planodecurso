package models;

import java.util.*;

class ValidadorPreRequisitos implements Validador {

    public Curriculo curriculo;

    public Set<Disciplina> acumuladas = new HashSet<Disciplina>();

    public Disciplina alvo;

    public ValidadorPreRequisitos(Curriculo curriculo) {
        this(curriculo, null);
    }

    public ValidadorPreRequisitos(Curriculo curriculo, Disciplina alvo) {
        this.curriculo = curriculo;
        this.alvo = alvo;
    }

    public void validar(PlanoDeCurso planoDeCurso) throws ErroValidacaoException {
        validar(planoDeCurso.grade);
    }

    public void validar(Grade grade) throws ErroValidacaoException {
        for (Periodo p : grade.periodos) {
            validar(p);
            acumuladas.addAll(p.disciplinas);
        }
    }

    public void validar(Periodo periodo) throws ErroValidacaoException {
        for (Disciplina d : periodo.disciplinas) {
            validar(d);
        }
    }

    public void validar(Disciplina disciplina) throws ErroValidacaoException {
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
            b.append(it.next().nome);
            if (it.hasNext()) {
                b.append(", ");
            }
        }

        String template = "%s tem requisitos não satisfeitos: %s.";
        String message = String.format(template, disciplina.nome, b.toString());
        throw new ErroValidacaoException(message);
    }
}
