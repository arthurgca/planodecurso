package models;

import java.util.*;

public final class Disciplina {

    public int id;

    public String nome;

    public int creditos;

    public Set<Disciplina> requisitos = new HashSet<Disciplina>();

    public Disciplina() {
    }

    public Disciplina(int id, String nome, int creditos) {
        this(id, nome, creditos, new HashSet<Disciplina>());
    }

    public Disciplina(int id, String nome, int creditos, Set<Disciplina> requisitos) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
        this.requisitos = requisitos;
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

    public static class Registro {
        private static Map<Integer, Disciplina> disciplinas = new HashMap<Integer, Disciplina>();

        public static Disciplina get(int i) {
            return disciplinas.get(i);
        }

        public static Disciplina get(String nome) {
            for (Disciplina disciplina : getAll()) {
                if (disciplina.nome.equals(nome))
                    return disciplina;
            }

            throw new IllegalArgumentException(nome);
        }

        public static Set<Disciplina> getAll() {
            return new HashSet<Disciplina>(disciplinas.values());
        }

        public static void registrarDisciplina(int id, String nome, int creditos) {
            disciplinas.put(id, new Disciplina(id, nome, creditos));
        }

        public static void registrarDisciplina(int id, String nome, int creditos, Set<Disciplina> requisitos) {
            disciplinas.put(id, new Disciplina(id, nome, creditos, requisitos));
        }
    }

}
