package models;

public interface PoliticaDeCreditos {

    public boolean podeProgramar(Disciplina disciplina, Periodo periodo);

    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo);

    public String validarPeriodo(Periodo periodo);

}
