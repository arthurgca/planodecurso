package models;

import java.util.*;

class ValidadorMaxCreditos implements Validador {

    private Curriculo curriculo;

    private Periodo alvo;

    public ValidadorMaxCreditos(Curriculo curriculo) {
        this(curriculo, null);
    }

    public ValidadorMaxCreditos(Curriculo curriculo, Periodo alvo) {
        this.curriculo = curriculo;
        this.alvo = alvo;
    }

    public void validar(Plano plano) throws ErroValidacaoException {
        validar(plano.getGrade());
    }

    private void validar(Grade grade) throws ErroValidacaoException {
        for (Periodo p : grade.getPeriodos()) {
            validar(p);
        }
    }

    private void validar(Periodo periodo) throws ErroValidacaoException {
        if (alvo != null && !alvo.equals(periodo)) {
            return;
        }

        if (periodo.getTotalCreditos() <= curriculo.getMaxCreditosPeriodo()) {
            return;
        }

        String template = "%s ultrapassa o máximo de créditos.";
        String message = String.format(template, periodo.getNome());
        throw new ErroValidacaoException(message);
    }

    private void validar(Disciplina disciplina) throws ErroValidacaoException {

    }
}
