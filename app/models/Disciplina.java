package models;

import java.util.*;

public class Disciplina {

    private String nome;

    private int creditos;

    public Disciplina(String nome, int creditos) {
        this.nome = nome;
        this.creditos = creditos;
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

        return getNome().equals(((Disciplina) obj).getNome());
    }

    public String toString() {
        return this.nome;
    }

}
