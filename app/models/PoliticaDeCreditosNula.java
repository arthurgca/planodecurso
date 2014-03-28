package models;

public class PoliticaDeCreditosNula implements PoliticaDeCreditos {

    public boolean podeProgramar(Disciplina disciplina, Periodo periodo) {
        return true;
    }

    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        return true;
    }

    public String validarPeriodo(Periodo periodo) {
        return null;
    }

}
