package models;

import java.util.*;

public class CatalogoDeDisciplinas {

    private static final Map<String, Disciplina> disciplinas = new HashMap<String, Disciplina>();

    static {
        Map<String, Disciplina> map = disciplinas;

        map.put("CALCULO1", new Disciplina("CALCULO1", "Calculo Diferencial e Integral I", 4));
        map.put("VETORIAL", new Disciplina("VETORIAL", "Álgebra Vetorial e Geometria Analítica", 4));
        map.put("LPT", new Disciplina("LPT", "Leitura e Produção de Textos", 4));
        map.put("P1", new Disciplina("P1", "Programação I", 4));
        map.put("IC", new Disciplina("IC", "Introdução à Computação", 4));
        map.put("LP1", new Disciplina("LP1", "Laboratório de Programação I", 4));

        map.put("CALCULO2", new Disciplina("CALCULO2", "Cálculo Diferencial e Integral II", 4));
        map.put("DISCRETA", new Disciplina("DISCRETA", "Matemática Discreta", 4));
        map.put("MC", new Disciplina("MC", "Metodologia Científica", 4));
        map.put("P2", new Disciplina("P2", "Programação II", 4));
        map.put("TG", new Disciplina("TG", "Teoria dos Grafos", 2));
        map.put("FC", new Disciplina("FC", "Fundamentos de Física Clássica", 4));
        map.put("LP2", new Disciplina("LP2", "Laboratório de Programação II", 4));

        map.put("LINEAR", new Disciplina("LINEAR", "Álgebra Linear", 4));
        map.put("PROBABILIDADE", new Disciplina("PROBABILIDADE", "Probabilidade e Estatística", 4));
        map.put("TC", new Disciplina("TC", "Teoria da Computação", 4));
        map.put("EDA", new Disciplina("EDA", "Estruturas de Dados e Algoritmos", 4));
        map.put("FM", new Disciplina("FM", "Fundamentos de Física Moderna", 4));
        map.put("GI", new Disciplina("GI", "Gerência da Informação", 4));
        map.put("LEDA", new Disciplina("LEDA", "Lab de Estruturas de Dados e Algoritmos", 4));
    }

    public Disciplina get(String disciplinaId) {
        if (!disciplinas.containsKey(disciplinaId))
            throw new IllegalArgumentException();
        return disciplinas.get(disciplinaId);
    }

    public List<Disciplina> getAll() {
        return new ArrayList<Disciplina>(disciplinas.values());
    }

}
