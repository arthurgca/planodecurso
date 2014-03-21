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

    public Set<Disciplina> getDisciplinas() {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        for (Periodo periodo : periodos) {
            disciplinas.addAll(periodo.disciplinas);
        }
        return disciplinas;
    }

    public Set<Disciplina> getDisciplinas(int semestre) {
        return getPeriodo(semestre).disciplinas;
    }

    public int getTotalCreditos(int semestre) {
        return getPeriodo(semestre).getTotalCreditos();
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        validarRequisitos(semestre, disciplina);
        getPeriodo(semestre).alocarDisciplina(disciplina);
    }

    public void moverDisciplina(int deSemestre, int paraSemestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        Periodo dePeriodo = getPeriodo(deSemestre);
        dePeriodo.desalocarDisciplina(disciplina);
        Periodo paraPeriodo = getPeriodo(paraSemestre);
        try {
            paraPeriodo.alocarDisciplina(disciplina);
        } catch (ErroDeAlocacaoException e) {
            dePeriodo.alocarDisciplina(disciplina);
            throw e;
        }
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

    private void validarRequisitos(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        Set<Disciplina> pagas = new HashSet<Disciplina>();

        for (int i = 1; i < semestre; i++) {
            pagas.addAll(getDisciplinas(i));
        }

        Set<Disciplina> devendo = new HashSet<Disciplina>(disciplina.requisitos);

        devendo.removeAll(pagas);

        if (devendo.isEmpty()) {
            return;
        }

        String template = "%s tem requisitos não satisfeitos: %s.";

        StringBuilder builder = new StringBuilder();

        Iterator<Disciplina> it = devendo.iterator();

        while (it.hasNext()) {
            builder.append(it.next().nome);

            if (it.hasNext()) {
                builder.append(", ");
            }
        }

        String message = String.format(template, disciplina.nome, builder.toString());

        throw new ErroDeAlocacaoException(message);
    }

    public static Finder<Long,Grade> find =
        new Finder<Long,Grade>(Long.class, Grade.class);
}
