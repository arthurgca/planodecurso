package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

/**
 * Uma grade é uma coleção de semestres.
 */
@Entity
public class Grade extends Model {

    @Id
    private Long id;

    private String nome;

    @ManyToOne(optional = true)
    private Curriculo curriculo;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("semestre ASC")
    private List<Periodo> periodos = new LinkedList<Periodo>();

    /**
     * Constrói uma grade com o número de períodos dado.
     * @param numPeriodos o número de períodos que a grade deve ter
     * @throws IllegalArgumentException se {@numPeriodos < 1}
     */
    public Grade(int numPeriodos) {
        Parametro.maiorQueZero("numPeriodos", numPeriodos);

        for (int i = 0; i < numPeriodos; i++) {
            periodos.add(new Periodo(i + 1));
        }
    }

    /**
     * @return o id da grade
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id o id para a grade
     * @throws NullPointerException se {@id == null}
     * @throws IllegalArgumentException se {@code id < 1}
     */
    public void setId(Long id) {
        Parametro.naoNulo("id", id);
        Parametro.maiorQueZero("id", id);

        this.id = id;
    }

    /**
     * @return o nome da grade
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * @param nome o nome para a grade
     * @throws NullPointerException se {@nome == null}
     * @throws IllegalArgumentException se {@nome == ""}
     */
    public void setNome(String nome) {
        Parametro.naoNulo("nome", nome);
        Parametro.naoVazio("nome", nome);

        this.nome = nome;
    }

    /**
     * @return a lista de periodos da grade
     */
    public List<Periodo> getPeriodos() {
        Parametro.naoNulo("periodos", periodos);
        return periodos;
    }

    /**
     * @param periodos a lista de periodos para a grade
     * @throws NullPointerException se {@code periodos == null}
     */
    public void setPeriodos(List<Periodo> periodos) {
        Parametro.naoNulo("periodos", periodos);

        this.periodos = periodos;
    }

    /**
     * @return o número de periodos na grade
     */
    public int getSize() {
        return periodos.size();
    }

    /**
     * Retorna um período pelo semestre.
     *
     * @param semestre o semestre correspondente ao período
     * @return o periodo correspondente ao semestre ou null se não houver
     * @throws IllegalArgumentException se {@code periodo < 1}
     * @throws NoSuchElementException se o período não for encontrado
     */
    public Periodo getPeriodo(int semestre) {
        Parametro.maiorQueZero("semestre", semestre);

        for (Periodo periodo : getPeriodos()) {
            if (periodo.getSemestre() == semestre) {
                return periodo;
            }
        }

        throw new NoSuchElementException(String.valueOf(semestre));
    }

    /**
     * Retorna todas as disciplinas programadas na grade.
     *
     * @return a lista de disciplinas da grade
     */
    public List<Disciplina> getDisciplinas() {
        List<Disciplina> todas = new LinkedList<Disciplina>();
        for (Periodo periodo : periodos) {
            todas.addAll(periodo.getDisciplinas());
        }
        return todas;
    }

    /**
     * Programa uma disciplina para o período dado.
     *
     * @param disciplina a disciplina para programar
     * @param periodo o periodo em que a disciplina vai ser programada
     * @throws NullPointerException se disciplina ou periodo for nulo
     * @throws PoliticaDeCreditosException se {@code !periodo.podeProgramar(disciplina, periodo)}
     */
    public void programar(Disciplina disciplina, Periodo periodo) throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        periodo.programar(disciplina);
    }

    /**
     * Desprograma uma disciplina do período dado.
     *
     * @param disciplina a disciplina para desprogramar
     * @param periodo o periodo em que a disciplina vai ser desprogramada
     * @throws NullPointerException se disciplina ou periodo for nulo
     * @throws PoliticaDeCreditosException se {@code !periodo.podeDesprogramar(disciplina, periodo)}
     */
    public void desprogramar(Disciplina disciplina, Periodo periodo) throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        periodo.desprogramar(disciplina);
    }

    /**
     * Desprograma uma disciplina e todos os requisitos no período dado.
     *
     * A política de créditos do período é ignorada.
     *
     * @param disciplina a disciplina para desprogramar
     * @param periodo o periodo em que a disciplina vai ser desprogramada
     * @throws NullPointerException se disciplina ou periodo for nulo
     */
    public void desprogramarRecursivamente(Disciplina disciplina, Periodo periodo) throws PoliticaDeCreditosException {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        if (!periodo.podeDesprogramar(disciplina))
            throw new PoliticaDeCreditosException("Mínimo de créditos não atingido");

        desprogramarRecursivamente2(disciplina, periodo);
    }

    @Override
    public String toString() {
        return String.format("%s (%s períodos)",
                             getNome() != null ? getNome() : "Grade Sem Nome",
                             getSize());
    }

    private void desprogramarRecursivamente2(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        desprogramarForcado(disciplina, periodo);

        Map<Periodo,List<Disciplina>> remover = new HashMap<Periodo,List<Disciplina>>();

        for (Periodo p : periodos) {
            remover.put(p, new LinkedList<Disciplina>());
            for (Disciplina d : p.getDisciplinas()) {
                if (d.getRequisitos().contains(disciplina)) {
                    remover.get(p).add(d);
                }
            }
        }

        for (Periodo p : remover.keySet()) {
            for (Disciplina d : remover.get(p)) {
                desprogramarRecursivamente2(d, p);
            }
        }
    }

    private void desprogramarForcado(Disciplina disciplina, Periodo periodo) {
        Parametro.naoNulo("disciplina", disciplina);
        Parametro.naoNulo("periodo", periodo);

        PoliticaDeCreditos temp = periodo.getPoliticaDeCreditos();
        periodo.setPoliticaDeCreditos(new PoliticaDeCreditosNula());

        try {
            desprogramar(disciplina, periodo);
        } catch (PoliticaDeCreditosException e) {
            assert false;
            e.printStackTrace();
        } finally {
            periodo.setPoliticaDeCreditos(temp);
        }
    }

    /** Finder para o Ebean */
    public static Finder<Long,Grade> find = new Finder<Long,Grade>(Long.class, Grade.class);

    /**
     * Retorna uma grade com a mesma programação da grade dada.
     *
     * @param grade a grade para copiar
     * @return uma cópia da grade dada
     * @throws NullPointerException se {@code grade == null}
     */
    public static Grade copiar(Grade grade) {
        Parametro.naoNulo("grade", grade);

        Grade copia = new Grade(grade.getSize());

        for (Periodo periodo : grade.getPeriodos()) {
            for (Disciplina disciplina : periodo.getDisciplinas()) {
                try {
                    copia.programar(disciplina, copia.getPeriodo(periodo.getSemestre()));
                } catch (PoliticaDeCreditosException e) {
                    assert false;
                    e.printStackTrace();
                }
            }
        }

        return copia;
    }
}
