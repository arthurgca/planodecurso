package models;

import java.util.*;

import play.data.validation.Constraints.*;

public class Disciplina {

    @Required
    private String id;

    @Required
    private String nome;

    @Required
    private int creditos;

    public Disciplina() { }

    public Disciplina(String id, String nome, int creditos) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (!(obj instanceof Disciplina))
            return false;

        return getId().equals(((Disciplina) obj).getId());
    }

    public int hashCode() {
        return 7 * this.id.hashCode();
    }

}
