package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class PlanoDeCurso extends Model {

    @Id
    public Long id;

    @ManyToOne
    public Curriculo curriculo;

    @OneToOne(cascade = CascadeType.ALL)
    public Grade grade;

    public PlanoDeCurso(Curriculo curriculo, Grade grade) {
        this.curriculo = curriculo;
        this.grade = grade;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public Periodo getPeriodo(int semestre) {
        return grade.getPeriodo(semestre);
    }

    public List<Periodo> getPeriodos() {
        return grade.periodos;
    }

    public Set<Disciplina> getDisciplinas() {
        return grade.getDisciplinas();
    }

    public Set<Disciplina> getDisciplinas(int semestre) {
        return grade.getDisciplinas(semestre);
    }

    public int getTotalCreditos(int semestre) {
        return grade.getTotalCreditos(semestre);
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        grade.alocarDisciplina(semestre, disciplina);
        save();
    }

    public void moverDisciplina(int deSemestre, int paraSemestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        grade.moverDisciplina(deSemestre, paraSemestre, disciplina);
        save();
    }

    public void desalocarDisciplina(int semestre, Disciplina disciplina) {
        grade.desalocarDisciplina(semestre, disciplina);
        save();
    }

    public static PlanoDeCurso criarPlanoInicial() {
        Curriculo curriculo = Curriculo.find.byId(1);
        Grade grade = new ArrayList<Grade>(curriculo.grades).get(0);
        PlanoDeCurso plano = new PlanoDeCurso(curriculo, grade);

        plano.save();

        return plano;
    }

    public static Finder<Long,PlanoDeCurso> find =
        new Finder<Long,PlanoDeCurso>(Long.class, PlanoDeCurso.class);
}
