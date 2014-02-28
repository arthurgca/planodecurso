package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

public class PlanoDeCurso {

    private static Map<Integer, Disciplina> disciplinas = new HashMap<Integer, Disciplina>();

    public Map<Integer, Periodo> periodos;

    public PlanoDeCurso() {
        periodos = new HashMap<Integer, Periodo>();
        inicializaMapPeriodos();
    }

    private void inicializaMapPeriodos() {
        for (int i = 0; i < 15; i++) {
            periodos.put(i + 1, new Periodo(i + 1));
        }
    }

    public void alocar(int semestre, int disciplina)
        throws ErroDeAlocacaoException {
        alocar(semestre, getDisciplina(disciplina));
    }

    public void alocar(int semestre, Disciplina disciplina)
        throws ErroDeAlocacaoException {
        if (!disciplina.dependencias.isEmpty()) {
            for (Disciplina dependencia : disciplina.dependencias) {
                if (!getDisciplinasAlocadas().contains(dependencia)) {
                    throw new ErroDeAlocacaoException(
                                                      "Pré-requisitos da disciplina não foram satisfeitos.");
                }
            }
        }
        if ((periodos.get(semestre).getTotalCreditos() + disciplina.creditos) > 28) {
            throw new ErroDeAlocacaoException(
                                              "Período deve ter menos de 28 créditos.");
        }
        for (int i : periodos.keySet()) {
            if (periodos.get(i).disciplinas.contains(disciplina)) {
                throw new ErroDeAlocacaoException("Disciplina já alocada.");
            }
        }
        periodos.get(semestre).alocar(disciplina);
    }

    public void desalocar(int disciplina) {
        desalocar(getDisciplina(disciplina));
    }

    public void desalocar(Disciplina disciplina) {
        for (int i : periodos.keySet()) {
            Periodo periodo = periodos.get(i);
            if (periodo.disciplinas.contains(disciplina)) {
                periodo.desalocar(disciplina);
                removerDependencias(disciplina);
            }
        }
    }

    private void removerDependencias(Disciplina disciplina) {
        for (int i : periodos.keySet()) {
            Periodo periodo = periodos.get(i);
            List<Disciplina> dependencias = new ArrayList<Disciplina>();
            for (Disciplina disc : periodo.disciplinas) {
                if (disc.dependencias.contains(disciplina)) {
                    dependencias.add(disc);
                }
            }
            for (Disciplina dependencia : dependencias) {
                desalocar(dependencia);
            }
        }
    }

    public Periodo getPeriodo(int semestre) {
        if (semestre < 1) {
            throw new IllegalArgumentException("o semestre deve ser >= 1");
        }
        return periodos.get(semestre);
    }

    public Set<Disciplina> getDisciplinasAlocadas() {
        Set<Disciplina> disciplinasAlocadas = new HashSet<Disciplina>();
        ;
        for (int i : periodos.keySet()) {
            Periodo periodo = periodos.get(i);
            for (Disciplina disciplina : periodo.disciplinas) {
                disciplinasAlocadas.add(disciplina);
            }
        }
        return disciplinasAlocadas;
    }

    public Set<Disciplina> getDisciplinasNaoAlocadas() {
        Set<Disciplina> disciplinasNaoAlocadas = new HashSet<Disciplina>();
        disciplinasNaoAlocadas.addAll(getDisciplinas());
        disciplinasNaoAlocadas.removeAll(getDisciplinasAlocadas());
        return disciplinasNaoAlocadas;
    }

    public List<Periodo> getPeriodos() {
        return Collections.unmodifiableList(new ArrayList<Periodo>(periodos
                                                                   .values()));
    }

    public static PlanoDeCurso getPlanoInicial() throws ErroDeAlocacaoException {
        PlanoDeCurso planoInicial = new PlanoDeCurso();
        for (Disciplina disciplina : getDisciplinas()) {
            if (disciplina.periodo == 1) {
                planoInicial.alocar(1, disciplina);
            }
        }
        return planoInicial;
    }

    public static Disciplina getDisciplina(int i) {
        return disciplinas.get(i);
    }

    public static Collection<Disciplina> getDisciplinas() {
        return Collections.unmodifiableCollection(disciplinas.values());
    }

    public static void registraDisciplina(int id, String nome, int creditos,
                                          int periodo, int dificuldade) {
        disciplinas.put(id, new Disciplina(id, nome, creditos, periodo,
                                           dificuldade));
    }

    public static void registraDisciplina(int id, String nome, int creditos,
                                          int periodo, int dificuldade, List<Disciplina> dependencias) {
        disciplinas.put(id, new Disciplina(id, nome, creditos, periodo,
                                           dificuldade, dependencias));
    }
}
