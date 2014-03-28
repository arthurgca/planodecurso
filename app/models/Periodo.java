package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Um Período armazena disciplinas programadas para um semestre.
 */
@Entity
public class Periodo extends Model implements Comparable<Periodo> {

    @Id
    private Long id;

    private int semestre;

    @ManyToMany
    private Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    @ManyToOne(cascade = CascadeType.ALL)
    private PoliticaDeCreditos politicaDeCreditos;

    /**
     * Constrói um Período para o semestre dado.
     * @throws IllegalArgumentException se {@code semestre < 1}
     */
    public Periodo(int semestre) {
        setSemestre(semestre);
        setPoliticaDeCreditos(new PoliticaDeCreditosNula());
    }

    /**
     * @return o id do período.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id um id para o período
     * @throws NullPointerException se {@code id == null}
     * @throws IllegalArgumentException se {@code id < 1}
     */
    public void setId(Long id) {
        Parametro.naoNulo("id", id);
        Parametro.maiorQueZero("id", id);

        this.id = id;
    }

    /**
     * @return o semestre do período
     */
    public int getSemestre() {
        return semestre;
    }

    /**
     * @param semestre semestre para o período
     * @throws IllegalArgumentException se {@code semestre < 1}
     */
    public void setSemestre(int semestre) {
        Parametro.maiorQueZero("semestre", semestre);

        this.semestre = semestre;
    }

    /**
     * @return o conjunto de disciplinas programadas para esse período
     */
    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    /**
     * @param disciplinas conjunto de disciplinas programadas para esse período
     * @throws NullPointerException se {@code disciplinas == null}
     */
    public void setDisciplinas(Set<Disciplina> disciplinas) {
        Parametro.naoNulo("disciplinas", disciplinas);

        this.disciplinas = disciplinas;
    }

    /**
     * @return o nome desse período
     */
    public String getNome() {
        return String.format("%sº Período", getSemestre());
    }

    /**
     * @return a política de créditos usada por esse período
     */
    public PoliticaDeCreditos getPoliticaDeCreditos() {
        return politicaDeCreditos;
    }

    /**
     * @param politicaDeCreditos a política usada para o total de créditos
     * @throws NullPointerException se {@code politicaDeCreditos == null}
     */
    public void setPoliticaDeCreditos(PoliticaDeCreditos politicaDeCreditos) {
        Parametro.naoNulo("politicaDeCreditos", politicaDeCreditos);

        this.politicaDeCreditos = politicaDeCreditos;
    }

    /**
     * @return uma explicação se a política de créditos foi violada ou null caso contrário.
     */
    @JsonIgnore
    @Transient
    public String getErroPoliticaDeCreditos() {
        return politicaDeCreditos.validar(this);
    }

    /**
     * Retorna se é possível programar a disciplina dada nesse período
     *
     * @throws NullPointerException se {@code disciplina == null}
     */
    public boolean podeProgramar(Disciplina disciplina) {
        Parametro.naoNulo("disciplina", disciplina);

        return politicaDeCreditos.podeProgramar(disciplina, this);
    }

    /**
     * Programa uma disciplina nesse período.
     *
     * @param disciplina a disciplina para programar
     * @throws NullPointerException se {@code disciplina == null}
     * @throws PoliticaDeCreditosException se a operação for inválida
     */
    public void programar(Disciplina disciplina) throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);

        if (!politicaDeCreditos.podeProgramar(disciplina, this))
            throw new PoliticaDeCreditosException(politicaDeCreditos.validar(this));

        disciplinas.add(disciplina);
    }

    /**
     * Retorna se é possível desprogramar a disciplina dada nesse período
     *
     * @throws NullPointerException se {@code disciplina == null}
     */
    public boolean podeDesprogramar(Disciplina disciplina) {
        Parametro.naoNulo("disciplina", disciplina);

        return politicaDeCreditos.podeDesprogramar(disciplina, this);
    }

    /**
     * Desprograma uma disciplina desse período.
     *
     * @param disciplina a disciplina para desprogramar
     * @throws NullPointerException se {@code disciplina == null}
     * @throws PoliticaDeCreditosException se a operação for inválida
     */
    public void desprogramar(Disciplina disciplina) throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);

        if (!politicaDeCreditos.podeDesprogramar(disciplina, this))
            throw new PoliticaDeCreditosException(politicaDeCreditos.validar(this));

        disciplinas.remove(disciplina);
    }

    /**
     * @return o total de créditos desse período
     */
    public int getTotalCreditos() {
        int total = 0;
        for (Disciplina disciplina : getDisciplinas()) {
            total += disciplina.getCreditos();
        }
        return total;
    }

    @Override
    public int compareTo(Periodo outro) {
        if (getSemestre() < outro.getSemestre())
            return -1;
        else if (getSemestre() > outro.getSemestre())
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return String.format("%s (%s disciplinas)", getNome(), getDisciplinas().size());
    }

    /** Finder para o Ebean */
    public static Finder<Long,Periodo> find =
        new Finder<Long,Periodo>(Long.class, Periodo.class);

}
