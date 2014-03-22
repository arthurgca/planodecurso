package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Curriculo extends Model {

    @Id
    public int id;

    public String nome;

    public int maxPeriodos;

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    public Curriculo(int id, String nome, int numPeriodos) {
        this.id = id;
        this.nome = nome;
        this.numPeriodos = numPeriodos;
    }

    @Transient
    public Disciplina getDisciplina(Long id) {
        for (Disciplina d : disciplinas) {
            if (d.id == id) {
                return d;
            }
        }

        return null;
    }

    @Transient
    public Disciplina getDisciplina(String nome) {
        for (Disciplina d : disciplinas) {
            if (d.nome.equals(nome)) {
                return d;
            }
        }

        return null;
    }

    public static Finder<Integer,Curriculo> find =
        new Finder<Integer,Curriculo>(Integer.class, Curriculo.class);
}
