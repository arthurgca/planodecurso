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
        this.grade = Grade.copiar(grade.nome, grade);
    }

    public Periodo getPeriodo(int periodo) {
        return grade.getPeriodo(periodo);
    }

    public List<Periodo> getPeriodos() {
        return grade.periodos;
    }

    public List<Disciplina> getDisciplinas(int periodo) {
        return grade.getDisciplinas(periodo);
    }

    public void programar(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        grade.programar(disciplina, periodo);
        validarProgramarDisciplina(disciplina, periodo);
    }

    public void programar(Disciplina disciplina, int periodo) throws ErroValidacaoException {
        programar(disciplina, grade.getPeriodo(periodo));
    }

    public void mover(Disciplina disciplina, Periodo de, Periodo para) throws ErroValidacaoException  {
        if (de.disciplinas.contains(disciplina)) {
            grade.desprogramar(disciplina, de);
            grade.programar(disciplina, para);
            validarMoverDisciplina(disciplina, de, para);
        }
    }

    public void mover(Disciplina disciplina, int de, int para) throws ErroValidacaoException {
        mover(disciplina, grade.getPeriodo(de), grade.getPeriodo(para));
    }

    public void desprogramar(Disciplina disciplina, Periodo periodo) {
        grade.desprogramarRecursivamente(disciplina, periodo);
    }

    public void desprogramar(Disciplina disciplina, int periodo) {
        desprogramar(disciplina, grade.getPeriodo(periodo));
    }

    private void validarProgramarDisciplina(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        new ValidadorPreRequisitos(curriculo, disciplina).validar(this);
        new ValidadorMaxCreditos(curriculo, periodo).validar(this);
    }

    private void validarMoverDisciplina(Disciplina disciplina, Periodo de, Periodo para) throws ErroValidacaoException {
        new ValidadorMaxCreditos(curriculo, para).validar(this);
    }

    public static PlanoDeCurso criarPlanoInicial() {
        Curriculo curriculo = Curriculo.find.all().get(0);
        Grade grade = Grade.find.all().get(0);
        PlanoDeCurso plano = new PlanoDeCurso(curriculo, grade);
        plano.save();
        return plano;
    }

    public static Finder<Long,PlanoDeCurso> find =
        new Finder<Long,PlanoDeCurso>(Long.class, PlanoDeCurso.class);
}
