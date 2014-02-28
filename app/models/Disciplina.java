package models;

import java.util.List;
import java.util.ArrayList;

public final class Disciplina {

    public int id;

    public String nome;

    public int creditos;

    public int periodo;

    public int dificuldade;

    public List<Disciplina> dependencias;

    public Disciplina(int id, String nome, int creditos, int periodo,
                      int dificuldade) {
        this(id, nome, creditos, periodo, dificuldade,
             new ArrayList<Disciplina>());
    }

    public Disciplina(int id, String nome, int creditos, int periodo,
                      int dificuldade, List<Disciplina> dependencias) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
        this.periodo = periodo;
        this.dificuldade = dificuldade;
        this.dependencias = new ArrayList<Disciplina>();

        for (Disciplina dependencia : dependencias) {
            this.dependencias.add(dependencia);
        }
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

        return this.id == ((Disciplina) obj).id;
    }

    public int hashCode() {
        return 7 * this.id;
    }

}
