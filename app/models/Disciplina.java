package models;

import java.util.*;

public final class Disciplina {

    public int id;

    public String nome;

    public int creditos;

    public int periodo;

    public List<Disciplina> dependencias;

    public Disciplina(int id, String nome, int creditos, int periodo) {
        this(id, nome, creditos, periodo, new ArrayList<Disciplina>());
    }

    public Disciplina(int id, String nome, int creditos, int periodo, List<Disciplina> dependencias) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
        this.periodo = periodo;

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

    public static class Registro {
        private static Map<Integer, Disciplina> disciplinas = new HashMap<Integer, Disciplina>();

        public static Disciplina get(int i) {
            return disciplinas.get(i);
        }

        public static Collection<Disciplina> getAll() {
            return Collections.unmodifiableCollection(disciplinas.values());
        }

        public static void registrarDisciplina(int id, String nome, int creditos, int periodo) {
            disciplinas.put(id, new Disciplina(id, nome, creditos, periodo));
        }

        public static void registrarDisciplina(int id, String nome, int creditos, int periodo, List<Disciplina> dependencias) {
            disciplinas.put(id, new Disciplina(id, nome, creditos, periodo, dependencias));
        }
    }

}
