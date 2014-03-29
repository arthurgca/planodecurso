package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;
import com.fasterxml.jackson.annotation.*;

/**
 * Um plano de curso para um currículo.
 */
@Entity
public class Plano extends Model {

    @Id
    private Long id;

    @ManyToOne
    private Curriculo curriculo;

    @OneToOne(cascade = CascadeType.ALL)
    private Grade grade;

    @OneToOne(cascade = CascadeType.ALL)
    private Periodo periodoAtual;

    /**
     * Constrói um plano para o curriculo e grade dados.
     *
     * @param curriculo o curriculo para o plano de curso
     * @param grade a grade para o plano de curso
     * @throws NullPointerException se algum parâmetro for nulo
     */
    public Plano(Curriculo curriculo, Grade grade) {
        setCurriculo(curriculo);
        setGrade(grade);
    }

    /**
     * @return o id do plano
     */
    public Long getId() {
        return id;
    }

    /**
     * @param o id para o curso
     * @throws NullPointerException se {@code id == null}
     * @throws IllegalArgumentException se {@code id < 1}
     */
    public void setId(Long id) {
        Parametro.naoNulo("id", id);
        Parametro.maiorQueZero("id", id);

        this.id = id;
    }

    /**
     * @return o curriculo do plano de curso
     */
    public Curriculo getCurriculo() {
        return curriculo;
    }

    /**
     * @param o curriculo para o plano de curso
     * @throws NullPointerException se {@code curriculo == null}
     */
    public void setCurriculo(Curriculo curriculo) {
        Parametro.naoNulo("curriculo", curriculo);

        this.curriculo = curriculo;
    }

    /**
     * @return a grade do plano de curso
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * @param grade a grade para o plano de curso
     * @throws NullPointerException se {@code grade == null}
     */
    public void setGrade(Grade grade) {
        Parametro.naoNulo("grade", grade);

        this.grade = grade;
    }

    /**
     * @return o periodo atual do plano de curso
     */
    public Periodo getPeriodoAtual() {
        return periodoAtual;
    }

    /**
     * Seta o período atual.
     *
     * Este método configura as políticas de crédito corretas para os
     * períodos do passado e do futuro.
     *
     * @param periodoAtual o periodo atual no plano de curso
     * @throws NullPointerException se {@code periodoAtual == null}
     * @throws NoSuchElementException se o período não está na grade
     */
    public void setPeriodoAtual(Periodo periodoAtual) {
        Parametro.naoNulo("periodoAtual", periodoAtual);

        if (!grade.getPeriodos().contains(periodoAtual))
            throw new NoSuchElementException(periodoAtual.toString());

        this.periodoAtual = periodoAtual;

        PoliticaDeCreditos max =
            new PoliticaMaxDeCreditos(getCurriculo().getMaxCreditosPeriodo());

        PoliticaDeCreditos minMax =
            new PoliticaMinMaxDeCreditos(getCurriculo().getMinCreditosPeriodo(),
                                         getCurriculo().getMaxCreditosPeriodo());

        for (Periodo periodo : grade.getPeriodos()) {
            if (periodo.compareTo(periodoAtual) < 0) {
                periodo.setPoliticaDeCreditos(max);
            } else {
                periodo.setPoliticaDeCreditos(minMax);
            }
        }
    }

    /**
     * @return a lista de períodos nesse plano
     */
    @Transient
    public List<Periodo> getPeriodos() {
        return grade.getPeriodos();
    }

    /**
     * Retorna um período pelo semestre.
     *
     * @param semestre o semestre correspondente ao período
     * @return o periodo correspondente ao semestre ou null se não houver
     * @throws IllegalArgumentException se {@code periodo < 1}
     * @throws NoSuchElementException se o período não for encontrado
     */
    @Transient
    public Periodo getPeriodo(int semestre) {
        Parametro.maiorQueZero("semestre", semestre);

        return grade.getPeriodo(semestre);
    }

    /**
     * @return a lista de disciplinas do plano
     */
    @Transient
    public List<Disciplina> getDisciplinas() {
        List<Disciplina> todas = new LinkedList<Disciplina>();
        for (Periodo periodo : getPeriodos()) {
            todas.addAll(periodo.getDisciplinas());
        }
        return todas;
    }

    /**
     * Programa uma disciplina para o período dado.
     *
     * @param disciplina a disciplina para ser programada
     * @param periodo o periodo para programar a disciplina
     * @throws NullPointerException se algum parâmetro for nulo
     * @throws PoliticaDeCreditosException se exceder o máximo de créditos
     * @throws RequisitosException se a disciplina tem requisitos insatisfeitos
     */
    public void programar(Disciplina disciplina, Periodo periodo)
        throws PoliticaDeCreditosException, RequisitosException {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        validarRequisitos(disciplina, periodo);

        grade.programar(disciplina, periodo);
    }

    /**
     * Desprograma uma disciplina no período dado.
     *
     * @param disciplina a disciplina para ser programada
     * @param periodo o periodo para programar a disciplina
     * @throws NullPointerException se algum parâmetro for nulo
     * @throws PoliticaDeCreditosException se exceder o máximo de créditos
     * @throws RequisitosException se a disciplina tem requisitos insatisfeitos
     */
    public void desprogramar(Disciplina disciplina, Periodo periodo)
        throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        grade.desprogramarRecursivamente(disciplina, periodo);
    }

    /**
     * Move a programação de uma disciplina entre períodos.
     *
     * @param disciplina a disciplina para ser programada
     * @param de o periodo de origem
     * @param para o periodo de destino
     * @throws NullPointerException se algum parâmetro for nulo
     * @throws PoliticaDeCreditosException se exceder o máximo de créditos
     */
    public void mover(Disciplina disciplina, Periodo de, Periodo para)
        throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("de", de);
        Parametro.naoNulo("para", para);

        if (de.equals(para))
            return;
        else if (!de.getDisciplinas().contains(disciplina))
            return;

        grade.desprogramar(disciplina, de);
        grade.programar(disciplina, para);
    }

    @Override
    public String toString() {
        return String.format("Plano (%s períodos)", getPeriodos().size());
    }

    private void validarRequisitos(Disciplina disciplina, Periodo periodo)
        throws RequisitosException {
        new ValidarRequisitos().validar(this, disciplina, periodo);
    }

    /** Finder para o Ebean */
    public static Finder<Long,Plano> find = new Finder<Long,Plano>(Long.class, Plano.class);
}
