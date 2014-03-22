package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public final class Curriculo extends Model {

    @Id
    public int id;

    public String nome;

    public int numPeriodos;

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Grade> grades = new HashSet<Grade>();

    public Curriculo(int id, String nome, int numPeriodos) {
        this.id = id;
        this.nome = nome;
        this.numPeriodos = numPeriodos;
    }

    public Disciplina getDisciplina(int id) {
        return Disciplina.find.where()
            .eq("id", id)
            .eq("curriculo_id", this.id).findUnique();
    }

    public Disciplina getDisciplina(String nome) {
        return Disciplina.find.where()
            .eq("nome", nome)
            .eq("curriculo_id", this.id).findUnique();
    }

    public static Finder<Integer,Curriculo> find =
        new Finder<Integer,Curriculo>(Integer.class, Curriculo.class);
}
