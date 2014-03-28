package models;

public class PoliticaMaxDeCreditos implements PoliticaDeCreditos {

    private final int maxCreditosPeriodo;

    public PoliticaMaxDeCreditos(Curriculo curriculo) {
        this.maxCreditosPeriodo = curriculo.getMaxCreditosPeriodo();
    }

    public boolean podeProgramar(Disciplina disciplina, Periodo periodo) {
        return periodo.getTotalCreditos() + disciplina.getCreditos() <= maxCreditosPeriodo;
    }

    public boolean podeDesprogramar(Disciplina disciplina, Periodo periodo) {
        return true;
    }

    public String validarPeriodo(Periodo periodo) {
        if (periodo.getTotalCreditos() > maxCreditosPeriodo) {
            return "Máximo de créditos excedido";
        } else {
            return null;
        }
    }

}
