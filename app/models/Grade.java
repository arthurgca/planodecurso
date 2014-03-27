package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Grade extends Model {

    @Id
    private Long id;

    private String nome;

    private boolean original = false;

    @JsonIgnore
    @ManyToOne
    private Curriculo curriculo;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("semestre ASC")
    private List<Periodo> periodos = new LinkedList<Periodo>();

    public Grade(String nome, Curriculo curriculo) {
        this.nome = nome;
        this.curriculo = curriculo;

        for (int i = 0; i < curriculo.getMaxPeriodos(); i++) {
            periodos.add(new Periodo(i + 1));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isOriginal() {
        return this.original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public int getMaxPeriodos() {
        return periodos.size();
    }

    public Periodo getPeriodo(int semestre) {
        for (Periodo periodo : periodos) {
            if (periodo.getSemestre() == semestre) {
                return periodo;
            }
        }

        return null;
    }

    @JsonIgnore
    public List<Disciplina> getDisciplinas() {
        List<Disciplina> resultado = new LinkedList<Disciplina>();
        for (Periodo periodo : periodos) {
            resultado.addAll(periodo.getDisciplinas());
        }
        return resultado;
    }

    public List<Disciplina> getDisciplinas(int periodo) {
        return getPeriodo(periodo).getDisciplinas();
    }

    public void programar(Disciplina disciplina, Periodo periodo) {
        periodo.programar(disciplina);
    }

    public void programar(Disciplina disciplina, int periodo) {
        programar(disciplina, getPeriodo(periodo));
    }

    public void desprogramar(Disciplina disciplina, Periodo periodo) {
        periodo.desprogramar(disciplina);
    }

    public void desprogramar(Disciplina disciplina, int periodo) {
        desprogramar(disciplina, getPeriodo(periodo));
    }

    public void desprogramarRecursivamente(Disciplina disciplina, Periodo periodo) {
        desprogramar(disciplina, periodo);

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
                desprogramarRecursivamente(d, p);
            }
        }
    }

    public void desprogramarRecursivamente(Disciplina disciplina, int periodo) {
        desprogramarRecursivamente(disciplina, getPeriodo(periodo));
    }

    public static Finder<Long,Grade> find =
        new Finder<Long,Grade>(Long.class, Grade.class);

    public static List<Grade> originais() {
        return find.where().eq("original", true).findList();
    }

    public static Grade copiar(String nome, Grade grade) {
        Grade copia = new Grade(nome, grade.curriculo);

        for (Periodo periodo : grade.periodos) {
            for (Disciplina disciplina : periodo.getDisciplinas()) {
                copia.programar(disciplina, periodo.getSemestre());
            }
        }

        return copia;
    }
}
