package models;

import java.util.*;

class ValidadorMinCreditos implements Validador {

    public Curriculo curriculo;

    public Periodo alvo;

    public ValidadorMinCreditos(Curriculo curriculo) {
        this(curriculo, null);
    }

    public ValidadorMinCreditos(Curriculo curriculo, Periodo alvo) {
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

        if (periodo.getTotalCreditos() >= curriculo.minCreditosPeriodo) {
            return;
        }

        String template = "%s não atinge o mínimo de créditos.";
        String message = String.format(template, periodo.getNome());
        throw new ErroValidacaoException(message);
    }

    public void validar(Disciplina disciplina) throws ErroValidacaoException {

    }
}
