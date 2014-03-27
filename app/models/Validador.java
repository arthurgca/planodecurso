package models;

public interface Validador {
    public void validar(Plano plano) throws ErroValidacaoException;
}
