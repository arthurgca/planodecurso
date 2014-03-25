package models;

public interface Validador {
    public void validar(Plano plano) throws ErroValidacaoException;

    public void validar(Grade grade) throws ErroValidacaoException;

    public void validar(Periodo periodo) throws ErroValidacaoException;

    public void validar(Disciplina disciplina) throws ErroValidacaoException;
}
