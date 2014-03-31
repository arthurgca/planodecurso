package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

/**
 * Currículo contém as disciplinas e especifíca regras para um curso.
 */
@Entity
public class Curriculo extends Model {

    @Id
    private int id;

    private int maxPeriodos = 14;

    private int minCreditosPeriodo = 14;

    private int maxCreditosPeriodo= 28;

    private int minCreditosObrigatorias = 114;

    private int minDisciplinasObrigatorias = 30;

    private int minCreditosOptativas = 42;

    private int minDisciplinasOptativas = 10;

    private int minCreditosComplementares = 52;

    private int minDisciplinasComplementares = 15;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Grade> grades = new HashSet<Grade>();

    private String nome;

    /**
     * Constrói um currículo com as disciplinas dadas.
     */
    public Curriculo(Set<Disciplina> disciplinas) {
        setDisciplinas(disciplinas);
    }

    /**
     * @return o id do currículo
     */
    public int getId() {
        return id;
    }

    /**
     * @param id o id para o currículo
     * @throws IllegalArgumentException se {@code id < 1}
     */
    public void setId(int id) {
        Parametro.maiorQueZero("id", id);

        this.id = id;
    }

    /**
     * @return o máximo de períodos desse currículo
     */
    public int getMaxPeriodos() {
        return maxPeriodos;
    }

    /**
     * @param maxPeriodos o máximo de períodos para esse currículo
     * @throws IllegalArgumentException se {@code maxPeriodos < 1}
     */
    public void setMaxPeriodos(int maxPeriodos) {
        Parametro.maiorQueZero("maxPeriodos", maxPeriodos);

        this.maxPeriodos = maxPeriodos;
    }

    /**
     * @return o mínimo de créditos para os períodos do curso
     */
    public int getMinCreditosPeriodo() {
        return minCreditosPeriodo;
    }

    /**
     * @param minCreditosPeriodo o mínimo de créditos para os períodos do curso
     * @throws IllegalArgumentException se {@code minCreditosPeriodo < 1}
     */
    public void setMinCreditosPeriodo(int minCreditosPeriodo) {
        Parametro.maiorQueZero("minCreditosPeriodo", minCreditosPeriodo);

        this.minCreditosPeriodo = minCreditosPeriodo;
    }

    /**
     * @return o máximo de créditos dos períodos do curso
     */
    public int getMaxCreditosPeriodo() {
        return maxCreditosPeriodo;
    }

    /**
     * @param maxCreditosPeriodo o máximo de créditos para os períodos do curso
     * @throws IllegalArgumentException se {@code maxCreditosPeriodo < 1}
     */
    public void setMaxCreditosPeriodo(int maxCreditosPeriodo) {
        Parametro.maiorQueZero("maxCreditosPeriodo", maxCreditosPeriodo);

        this.maxCreditosPeriodo = maxCreditosPeriodo;
    }

    /**
     * @return o mínimo de créditos de disciplinas obrigatórias para concluir o curso
     */
    public int getMinCreditosObrigatorias() {
        return minCreditosObrigatorias;
    }

    /**
     * @param minCreditosObrigatorias o mínimo de créditos de disciplinas obrigatórias para concluir o curso
     * @throws IllegalArgumentException se {@code minCreditosObrigatorias < 1}
     */
    public void setMinCreditosObrigatorias(int minCreditosObrigatorias) {
        Parametro.maiorQueZero("minCreditosObrigatorias", minCreditosObrigatorias);

        this.minCreditosObrigatorias = minCreditosObrigatorias;
    }

    /**
     * @return o mínimo de disciplinas obrigatórias para concluir o curso
     */
    public int getMinDisciplinasObrigatorias() {
        return minDisciplinasObrigatorias;
    }

    /**
     * @param minDisciplinasObrigatorias o mínimo de disciplinas obrigatórias para concluir o curso
     * @throws IllegalArgumentException se {@code minDisciplinasObrigatorias < 1}
     */
    public void setMinDisciplinasObrigatorias(int minDisciplinasObrigatorias) {
        Parametro.maiorQueZero("minDisciplinasObrigatorias", minDisciplinasObrigatorias);

        this.minDisciplinasObrigatorias = minDisciplinasObrigatorias;
    }

    /**
     * @return o mínimo de créditos de disciplinas optativas para concluir o curso
     */
    public int getMinCreditosOptativas() {
        return minCreditosOptativas;
    }

    /**
     * @param minCreditosOptativas o mínimo de créditos de disciplinas optativas para concluir o curso
     * @throws IllegalArgumentException se {@code minCreditosOptativas < 1}
     */
    public void setMinCreditosOptativas(int minCreditosOptativas) {
        Parametro.maiorQueZero("minCreditosOptativas", minCreditosOptativas);

        this.minCreditosOptativas = minCreditosOptativas;
    }

    /**
     * @return o mínimo de disciplinas optativas para concluir o curso
     */
    public int getMinDisciplinasOptativas() {
        return minDisciplinasOptativas;
    }

    /**
     * @param minDisciplinasOptativas o mínimo de disciplinas optativas para concluir o curso
     * @throws IllegalArgumentException se {@code minDisciplinasOptativas < 1}
     */
    public void setMinDisciplinasOptativas(int minDisciplinasOptativas) {
        Parametro.maiorQueZero("minDisciplinasOptativas", minDisciplinasOptativas);

        this.minDisciplinasOptativas = minDisciplinasOptativas;
    }

    /**
     * @return o mínimo de créditos de disciplinas complementares para concluir o curso
     */
    public int getMinCreditosComplementares() {
        return minCreditosComplementares;
    }

    /**
     * @param minCreditosComplementares o mínimo de créditos de disciplinas complementares para concluir o curso
     * @throws IllegalArgumentException se {@code minCreditosComplementares < 1}
     */
    public void setMinCreditosComplementares(int minCreditosComplementares) {
        Parametro.maiorQueZero("minCreditosComplementares", minCreditosComplementares);

        this.minCreditosComplementares = minCreditosComplementares;
    }

    /**
     * @return o mínimo de disciplinas complementares para concluir o curso
     */
    public int getMinDisciplinasComplementares() {
        return minDisciplinasComplementares;
    }

    /**
     * @param minDisciplinasComplementares o mínimo de disciplinas complementares para concluir o curso
     * @throws IllegalArgumentException se {@code minDisciplinasComplementares < 1}
     */
    public void setMinDisciplinasComplementares(int minDisciplinasComplementares) {
        Parametro.maiorQueZero("minDisciplinasComplementares", minDisciplinasComplementares);

        this.minDisciplinasComplementares = minDisciplinasComplementares;
    }

    /**
     * @return o conjunto de disciplinas do currículo
     */
    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    /**
     * @param disciplinas um conjunto de disciplinas para o curso
     * @throws NullPointerException se {@code disciplinas == null}
     */
    public void setDisciplinas(Set<Disciplina> disciplinas) {
        Parametro.naoNulo("disciplinas", disciplinas);

        this.disciplinas = disciplinas;
    }

    /**
     * @return o conjunto de grades do currículo
     */
    public Set<Grade> getGrades() {
        return grades;
    }

    /**
     * @param grades o conjunto de grades do currículo
     * @throws NullPointerException se {@code grades == null}
     */
    public void setGrades(Set<Grade> grades) {
        Parametro.naoNulo("grades", grades);

        this.grades = grades;
    }

    /**
     * @return o nome do currículo
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome o nome para o currículo
     * @throws NullPointerException se {@nome == null}
     * @throws IllegalArgumentException se {@nome == ""}
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a disciplina com o id dado.
     *
     * @param id o id da disciplina para encontrar
     * @throws NullPointerException se {@code id == null}
     * @throws IllegalArgumentException se {@code id < 1}
     * @throws NoSuchElementException se a disciplina não for encontrada
     */
    public Disciplina getDisciplina(Long id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId().equals(id)) {
                return disciplina;
            }
        }

        throw new NoSuchElementException(id.toString());
    }

    @Override
    public String toString() {
        return String.format("%s (%s disciplinas)",
                             getNome() == null ? "Currículo Sem Nome" : getNome(),
                             getDisciplinas().size());
    }

    /** Finder para o Ebean */
    public static Finder<Integer, Curriculo> find = new Finder<Integer,Curriculo>(Integer.class, Curriculo.class);

}
