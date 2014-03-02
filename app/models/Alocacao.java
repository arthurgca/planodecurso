package models;

import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Alocacao extends Model {

    @Id
    public Long id;

    public int semestre;

    @ManyToOne
    public Disciplina disciplina;

    public Alocacao(int semestre, Disciplina disciplina) {
        if (semestre < 1) {
            throw new IllegalArgumentException("semestre deve ser >= 1");
        }

        this.semestre = semestre;
        this.disciplina = disciplina;
    }

    public static Finder<Long,Alocacao> find =
        new Finder<Long,Alocacao>(Long.class, Alocacao.class);
}
