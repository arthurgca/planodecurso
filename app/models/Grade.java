package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Grade extends Model {

    @Id
    public Long id;

    public String nome;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Periodo> periodos = new LinkedList<Periodo>();

    public Grade(String nome, int maxPeriodos) {
        this.nome = nome;

        for (int i = 0; i < maxPeriodos; i++) {
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

    public static Grade copiar(String nome, Grade grade) {
        Grade copia = new Grade(nome, grade.getMaxPeriodos());

        for (Periodo p : grade.periodos) {
            for (Disciplina d : p.disciplinas) {
                copia.programar(d, p.semestre);
            }
        }

        return copia;
    }
}
