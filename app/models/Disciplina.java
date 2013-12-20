package models;

import java.util.List;
import java.util.ArrayList;

public class Disciplina {

    private String id;

    private String nome;

    private int creditos;

    private List<Disciplina> dependencias;

    public Disciplina() {
        this.dependencias = new ArrayList<Disciplina>();
    }

    public Disciplina(String id, String nome, int creditos, Disciplina... dependencias) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
        this.dependencias = new ArrayList<Disciplina>();

        for (Disciplina dependencia : dependencias) {
            this.dependencias.add(dependencia);
        }
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

    public List<Disciplina> getDependencias() {
        return dependencias;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Disciplina)) {
            return false;
        }

        return getId().equals(((Disciplina) obj).getId());
    }

    public int hashCode() {
        return 7 * this.id.hashCode();
    }

}
