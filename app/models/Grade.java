package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Grade extends Model {
    private static final int NUM_PERIODOS = 14;

    @Id
    public Long id;

    public String nome;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Periodo> periodos = new ArrayList<Periodo>();

    public Grade() {

    }

    public Periodo getPeriodo(int semestre) {
        return periodos.get(semestre - 1);
    }

    public Set<Disciplina> getDisciplinas(int semestre) {
        return getPeriodo(semestre).disciplinas;
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        validarRequisitos(semestre, disciplina);
        getPeriodo(semestre).alocarDisciplina(disciplina);
    }

    public void desalocarDisciplina(int semestre, Disciplina disciplina) {
        desalocarDisciplinaRecursivamente(semestre, disciplina);
    }

    private void desalocarDisciplinaRecursivamente(int semestre, Disciplina disciplina) {
        Periodo periodo = getPeriodo(semestre);

        periodo.desalocarDisciplina(disciplina);

        Map<Integer,List<Disciplina>> remover = new HashMap<Integer,List<Disciplina>>();

        for (int i = semestre; i <= NUM_PERIODOS; i++) {
            remover.put(i, new ArrayList<Disciplina>());

            for (Disciplina outra : getPeriodo(i).disciplinas) {
                if (outra.requisitos.contains(disciplina)) {
                    remover.get(i).add(outra);
                }
            }
        }

        for (Integer i : remover.keySet()) {
            for (Disciplina outra : remover.get(i)) {
                desalocarDisciplinaRecursivamente(i, outra);
            }
        }
    }

    public static Finder<Long,Grade> find =
        new Finder<Long,Grade>(Long.class, Grade.class);
}
