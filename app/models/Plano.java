package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Plano extends Model {

    @Id
    public Long id;

    @ManyToOne
    public Curriculo curriculo;

    @OneToOne(cascade = CascadeType.ALL)
    public Grade grade;

    @JsonIgnore
    @OneToOne(mappedBy = "plano")
    public Usuario dono;

    public Plano(Curriculo curriculo, Grade grade) {
        this.curriculo = curriculo;
        this.grade = Grade.copiar(grade.nome, grade);
    }

    public List<Periodo> getPeriodos() {
        return grade.periodos;
    }

    public Periodo getPeriodo(int periodo) {
        return grade.getPeriodo(periodo);
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

    public void desprogramar(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        grade.desprogramarRecursivamente(disciplina, periodo);
        validarDesprogramarDisciplina(disciplina, periodo);
    }

    public void desprogramar(Disciplina disciplina, int periodo) throws ErroValidacaoException {
        desprogramar(disciplina, grade.getPeriodo(periodo));
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

    private void validarProgramarDisciplina(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        new ValidadorPreRequisitos(curriculo, disciplina).validar(this);
        new ValidadorMaxCreditos(curriculo, periodo).validar(this);
    }

    private void validarDesprogramarDisciplina(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        new ValidadorMinCreditos(curriculo, periodo).validar(this);
    }

    private void validarMoverDisciplina(Disciplina disciplina, Periodo de, Periodo para) throws ErroValidacaoException {
        new ValidadorMinCreditos(curriculo, de).validar(this);
        new ValidadorMaxCreditos(curriculo, para).validar(this);
    }

    public static Plano criarPlanoInicial() {
        Curriculo curriculo = Curriculo.find.all().get(0);
        Grade grade = Grade.find.all().get(0);
        Plano plano = new Plano(curriculo, grade);
        plano.save();
        return plano;
    }

    public static Finder<Long,Plano> find =
        new Finder<Long,Plano>(Long.class, Plano.class);
}
