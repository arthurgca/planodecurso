package models;

import java.util.*;

public class CatalogoDeDisciplinas {

    private static final Map<String, Disciplina> disciplinas = new HashMap<String, Disciplina>();

    public Disciplina get(String disciplinaId) {
        if (!disciplinas.containsKey(disciplinaId))
            throw new IllegalArgumentException();
        return disciplinas.get(disciplinaId);
    }

    public List<Disciplina> getAll() {
        return new ArrayList<Disciplina>(disciplinas.values());
    }

    public static void register(String id, String nome, int creditos, String... dependenciaIds) {
        List<Disciplina> dependencias = new ArrayList<Disciplina>();

        for (String dependenciaId : dependenciaIds) {
            dependencias.add(disciplinas.get(dependenciaId));
        }

        Disciplina[] dependenciasArray = dependencias.toArray(new Disciplina[dependencias.size()]);
        disciplinas.put(id, new Disciplina(id, nome, creditos, dependenciasArray));
    }

}
