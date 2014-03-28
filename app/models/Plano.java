package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Plano extends Model {

    @Id
    private Long id;

    @ManyToOne
    private Curriculo curriculo;

    @OneToOne(cascade = CascadeType.ALL)
    private Grade grade;

    @JsonIgnore
    @OneToOne(mappedBy = "plano")
    private Usuario dono;

    private int periodoAtual;

    public Plano(Curriculo curriculo, Grade grade, int periodoAtual) {
        this.curriculo = curriculo;
        this.grade = Grade.copiar(grade.getNome(), grade);
        setPeriodoAtual(periodoAtual);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario usuario) {
        this.dono = usuario;
    }

    public int getPeriodoAtual() {
        return periodoAtual;
    }

    public void setPeriodoAtual(int periodoAtual) {
        this.periodoAtual = periodoAtual;
    }

    public List<Periodo> getPeriodos() {
        return grade.getPeriodos();
    }

    public Periodo getPeriodo(int periodo) {
        return grade.getPeriodo(periodo);
    }

    public List<Disciplina> getDisciplinas() {
        return grade.getDisciplinas();
    }

    public List<Disciplina> getDisciplinas(int periodo) {
        return grade.getDisciplinas(periodo);
    }

    public void programar(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        validarProgramarDisciplina(disciplina, periodo);
        validarPreRequisitos(disciplina, periodo);
        grade.programar(disciplina, periodo);
    }

    public void programar(Disciplina disciplina, int periodo) throws ErroValidacaoException {
        programar(disciplina, grade.getPeriodo(periodo));
    }

    public void desprogramar(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        validarDesprogramarDisciplina(disciplina, periodo);
        grade.desprogramarRecursivamente(disciplina, periodo);
    }

    public void desprogramar(Disciplina disciplina, int periodo) throws ErroValidacaoException {
        desprogramar(disciplina, grade.getPeriodo(periodo));
    }

    public void mover(Disciplina disciplina, Periodo de, Periodo para) throws ErroValidacaoException  {
        if (!de.equals(para) && de.getDisciplinas().contains(disciplina)) {
            validarDesprogramarDisciplina(disciplina, de);
            validarProgramarDisciplina(disciplina, para);

            grade.desprogramar(disciplina, de);
            grade.programar(disciplina, para);
        }
    }

    public void mover(Disciplina disciplina, int de, int para) throws ErroValidacaoException {
        mover(disciplina, grade.getPeriodo(de), grade.getPeriodo(para));
    }

    private void validarProgramarDisciplina(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        setPoliticaDeCreditos();
        if (!periodo.podeProgramar(disciplina)) {
            String template = "%s ultrapassa o máximo de créditos.";
            String message = String.format(template, periodo.getNome());
            throw new ErroValidacaoException(message);
        }
    }

    private void validarDesprogramarDisciplina(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        setPoliticaDeCreditos();
        if (!periodo.podeDesprogramar(disciplina)) {
            String template = "%s não atinge o mínimo de créditos.";
            String message = String.format(template, periodo.getNome());
            throw new ErroValidacaoException(message);
        }
    }

    private void validarPreRequisitos(Disciplina disciplina, Periodo periodo) throws ErroValidacaoException {
        new ValidadorPreRequisitos(this).validar(disciplina, periodo);
    }

    private void setPoliticaDeCreditos() {
        for (Periodo periodo : getPeriodos()) {
            if (periodo.getSemestre() < periodoAtual) {
                periodo.setPoliticaDeCreditos(new PoliticaMaxDeCreditos(curriculo));
            } else {
                periodo.setPoliticaDeCreditos(new PoliticaMinMaxDeCreditos(curriculo));
            }
        }
    }

    public static Finder<Long,Plano> find =
        new Finder<Long,Plano>(Long.class, Plano.class);
}
