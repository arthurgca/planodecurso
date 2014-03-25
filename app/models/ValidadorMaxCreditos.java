package models;

import java.util.*;

class ValidadorMaxCreditos implements Validador {

    public Curriculo curriculo;

    public Periodo alvo;

    public ValidadorMaxCreditos(Curriculo curriculo) {
        this(curriculo, null);
    }

    public ValidadorMaxCreditos(Curriculo curriculo, Periodo alvo) {
        this.curriculo = curriculo;
        this.alvo = alvo;
    }

    public void validar(Plano plano) throws ErroValidacaoException {
        validar(plano.grade);
    }

    public void validar(Grade grade) throws ErroValidacaoException {
        for (Periodo p : grade.periodos) {
            validar(p);
        }
    }

    public void validar(Periodo periodo) throws ErroValidacaoException {
        if (alvo != null && !alvo.equals(periodo)) {
            return;
        }

        if (periodo.getTotalCreditos() <= curriculo.maxCreditosPeriodo) {
            return;
        }

        String template = "%s ultrapassa o máximo de créditos.";
        String message = String.format(template, periodo.getNome());
        throw new ErroValidacaoException(message);
    }

    public void validar(Disciplina disciplina) throws ErroValidacaoException {

    }
}
