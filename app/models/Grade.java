package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Grade extends Model {

    @Id
    public Long id;

    public String nome;

    public boolean original = false;

    @JsonIgnore
    @ManyToOne
    public Curriculo curriculo;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("semestre ASC")
    public List<Periodo> periodos = new LinkedList<Periodo>();

    public Grade(String nome, Curriculo curriculo) {
        this.nome = nome;
        this.curriculo = curriculo;

        for (int i = 0; i < curriculo.maxPeriodos; i++) {
            periodos.add(new Periodo(i + 1));
        }
    }

    public int getMaxPeriodos() {
        return periodos.size();
    }

    public Periodo getPeriodo(int semestre) {
        for (Periodo periodo : periodos) {
            if (periodo.semestre == semestre) {
                return periodo;
            }
        }

        return null;
    }

    @JsonIgnore
    public List<Disciplina> getDisciplinas() {
        List<Disciplina> resultado = new LinkedList<Disciplina>();
        for (Periodo periodo : periodos) {
            resultado.addAll(periodo.disciplinas);
        }
        return resultado;
    }

    public List<Disciplina> getDisciplinas(int periodo) {
        return getPeriodo(periodo).disciplinas;
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
            for (Disciplina d : p.disciplinas) {
                if (d.requisitos.contains(disciplina)) {
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

        for (Periodo p : grade.periodos) {
            for (Disciplina d : p.disciplinas) {
                copia.programar(d, p.semestre);
            }
        }

        return copia;
    }
}
