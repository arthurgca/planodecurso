package models;

public class PoliticaMinDeCreditos implements PoliticaDeCreditos {

    private final int minCreditosPeriodo;

    public PoliticaMinDeCreditos(Curriculo curriculo) {
        this.minCreditosPeriodo = curriculo.getMinCreditosPeriodo();
    }

    public boolean podeProgramar(Disciplina disciplina, Periodo periodo) {
        return true;
    }

    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        return periodo.getTotalCreditos() - disciplina.getCreditos() >= minCreditosPeriodo;
    }

    public String validarPeriodo(Periodo periodo) {
        if (periodo.getTotalCreditos() < minCreditosPeriodo) {
            return "Minimo de créditos não atingido";
        } else {
            return null;
        }
    }

}
