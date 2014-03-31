package models;

import java.util.*;

/**
 * Coleta estatísticas para um plano de curso.
 */
public class Estatisticas {

    private int numObrigatorias = 0;

    private int creditosObrigatorias = 0;

    private int numOptativas = 0;

    private int creditosOptativas = 0;

    private int numComplementares = 0;

    private int creditosComplementares = 0;

    private Curriculo curriculo;

    /**
     * Construtor
     *
     * @param curriculo um curriculo para comparação
     * @throws NullPointerException se {@code curriculo == null}
     */
    public Estatisticas(Curriculo curriculo) {
        Parametro.naoNulo("curriculo", curriculo);

        this.curriculo = curriculo;
    }

    /**
     * Acumula uma disciplina aos totais.
     *
     * @param disciplina uma disciplina para adicionar
     * @throws NullPointerException se {@code disciplina == null}
     */
    public void add(Disciplina disciplina) {
        Parametro.naoNulo("disciplina", disciplina);

        if (disciplina.getTipo().equals(Disciplina.Tipo.OPTATIVA)) {
            numOptativas += 1;
            creditosOptativas += disciplina.getCreditos();
        } else if (disciplina.getTipo().equals(Disciplina.Tipo.COMPLEMENTAR)) {
            numComplementares += 1;
            creditosComplementares += disciplina.getCreditos();
        } else {
            numObrigatorias += 1;
            creditosObrigatorias += disciplina.getCreditos();
        }
    }

    /**
     * Acumula um periodo aos totais.
     *
     * @param periodo um periodo para adicionar
     * @throws NullPointerException se {@code periodo == null}
     */
    public void add(Periodo periodo) {
        Parametro.naoNulo("periodo", periodo);

        for (Disciplina disciplina : periodo.getDisciplinas()) {
            add(disciplina);
        }
    }

    /**
     * @return um mapa de estatísticas para disciplinas obrigatória
     */
    public Map<String,Integer> getEstatisticasObrigatorias() {
        HashMap<String,Integer> resultados = new HashMap<String,Integer>();
        resultados.put("totalDisciplinas", numObrigatorias);
        resultados.put("minDisciplinas", curriculo.getMinDisciplinasObrigatorias());
        resultados.put("totalCreditos", creditosObrigatorias);
        resultados.put("minCreditos", curriculo.getMinCreditosObrigatorias());
        return resultados;
    }

    /**
     * @return um mapa de estatísticas para disciplinas optativas
     */
    public Map<String,Integer> getEstatisticasOptativas() {
        HashMap<String,Integer> resultados = new HashMap<String,Integer>();
        resultados.put("totalDisciplinas", numOptativas);
        resultados.put("minDisciplinas", curriculo.getMinDisciplinasOptativas());
        resultados.put("totalCreditos", creditosOptativas);
        resultados.put("minCreditos", curriculo.getMinCreditosOptativas());
        return resultados;
    }

    /**
     * @return um mapa de estatísticas para disciplinas complementares
     */
    public Map<String,Integer> getEstatisticasComplementares() {
        HashMap<String,Integer> resultados = new HashMap<String,Integer>();
        resultados.put("totalDisciplinas", numComplementares);
        resultados.put("minDisciplinas", curriculo.getMinDisciplinasComplementares());
        resultados.put("totalCreditos", creditosComplementares);
        resultados.put("minCreditos", curriculo.getMinCreditosComplementares());
        return resultados;
    }

}
