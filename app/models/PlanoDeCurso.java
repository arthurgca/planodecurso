package models;

import java.util.*;

public class PlanoDeCurso {

    public Map<Integer, Periodo> periodos;

    public PlanoDeCurso() {
        periodos = new HashMap<Integer, Periodo>();

        for (int i = 0; i < 15; i++) {
            periodos.put(i + 1, new Periodo(i + 1));
        }
    }

    public void alocar(int semestre, int disciplina)
        throws ErroDeAlocacaoException {
        alocar(semestre, Disciplina.Registro.get(disciplina));
    }

    public void alocar(int semestre, Disciplina disciplina)
        throws ErroDeAlocacaoException {
        if (!disciplina.requisitos.isEmpty()) {
            for (Disciplina requisito : disciplina.requisitos) {
                if (!getDisciplinasAlocadas().contains(requisito)) {
                    throw new ErroDeAlocacaoException("Pré-requisitos da disciplina não foram satisfeitos.");
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
        desalocar(Disciplina.Registro.get(disciplina));
    }

    public void desalocar(Disciplina disciplina) {
        for (int i : periodos.keySet()) {
            Periodo periodo = periodos.get(i);
            if (periodo.disciplinas.contains(disciplina)) {
                periodo.desalocar(disciplina);
                removerRequisitos(disciplina);
            }
        }
    }

    private void removerRequisitos(Disciplina disciplina) {
        for (int i : periodos.keySet()) {
            Periodo periodo = periodos.get(i);
            List<Disciplina> requisitos = new ArrayList<Disciplina>();
            for (Disciplina disc : periodo.disciplinas) {
                if (disc.requisitos.contains(disciplina)) {
                    requisitos.add(disc);
                }
            }
            for (Disciplina requisito : requisitos) {
                desalocar(requisito);
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
        disciplinasNaoAlocadas.addAll(Disciplina.Registro.getAll());
        disciplinasNaoAlocadas.removeAll(getDisciplinasAlocadas());
        return disciplinasNaoAlocadas;
    }

    public List<Periodo> getPeriodos() {
        return Collections.unmodifiableList(new ArrayList<Periodo>(periodos
                                                                   .values()));
    }

    public static PlanoDeCurso getPlanoInicial() throws ErroDeAlocacaoException {
        PlanoDeCurso planoInicial = new PlanoDeCurso();
        planoInicial.alocar(1, Disciplina.Registro.get("Programação I"));
        planoInicial.alocar(1, Disciplina.Registro.get("Leitura e Prod. de Textos"));
        planoInicial.alocar(1, Disciplina.Registro.get("Cálculo I"));
        planoInicial.alocar(1, Disciplina.Registro.get("Algebra Vetorial"));
        planoInicial.alocar(1, Disciplina.Registro.get("Int. à Computacação"));
        planoInicial.alocar(1, Disciplina.Registro.get("Lab. de Programação I"));
        return planoInicial;
    }
}
