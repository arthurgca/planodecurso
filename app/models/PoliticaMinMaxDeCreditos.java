package models;

public class PoliticaMinMaxDeCreditos implements PoliticaDeCreditos {

    private final PoliticaMinDeCreditos politicaMinDeCreditos;

    private final PoliticaMaxDeCreditos politicaMaxDeCreditos;

    public PoliticaMinMaxDeCreditos(Curriculo curriculo) {
        politicaMinDeCreditos = new PoliticaMinDeCreditos(curriculo);
        politicaMaxDeCreditos = new PoliticaMaxDeCreditos(curriculo);
    }

    public boolean podeProgramar(Disciplina disciplina, Periodo periodo) {
        return politicaMaxDeCreditos.podeProgramar(disciplina, periodo);
    }

    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        return politicaMinDeCreditos.podeDesprogramar(disciplina, periodo);
    }

    public String validarPeriodo(Periodo periodo) {
        String erroMin = politicaMinDeCreditos.validarPeriodo(periodo);
        String erroMax = politicaMaxDeCreditos.validarPeriodo(periodo);
        return erroMin == null ? erroMax : erroMin;
    }

}
