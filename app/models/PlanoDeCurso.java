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

    public Set<Disciplina> getDisciplinas() {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        for (int i : periodos.keySet()) {
            Periodo periodo = periodos.get(i);
            for (Disciplina disciplina : periodo.disciplinas) {
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }

   public Set<Disciplina> getDisciplinas(int semestre) {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        for (Disciplina disciplina : periodos.get(semestre).disciplinas) {
            disciplinas.add(disciplina);
        }
        return disciplinas;
    }

    public int getTotalCreditos(int semestre) {
        int totalCreditos = 0;
        for (Disciplina disciplina : getDisciplinas(semestre)) {
            totalCreditos += disciplina.creditos;
        }
        return totalCreditos;
    }

    public void alocarDisciplina(int semestre, int disciplina)
        throws ErroDeAlocacaoException {
        alocarDisciplina(semestre, Disciplina.Registro.get(disciplina));
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina)
        throws ErroDeAlocacaoException {
        if (!disciplina.requisitos.isEmpty()) {
            for (Disciplina requisito : disciplina.requisitos) {
                if (!getDisciplinas().contains(requisito)) {
                    throw new ErroDeAlocacaoException("Pré-requisitos da disciplina não foram satisfeitos.");
                }
            }
        }
        if ((getTotalCreditos(semestre) + disciplina.creditos) > 28) {
            throw new ErroDeAlocacaoException("Período deve ter menos de 28 créditos.");
        }
        for (int i : periodos.keySet()) {
            if (periodos.get(i).disciplinas.contains(disciplina)) {
                throw new ErroDeAlocacaoException("Disciplina já alocada.");
            }
        }
        periodos.get(semestre).alocar(disciplina);
    }

    public void desalocarDisciplina(int disciplina) {
        desalocarDisciplina(Disciplina.Registro.get(disciplina));
    }

    public void desalocarDisciplina(Disciplina disciplina) {
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
                desalocarDisciplina(requisito);
            }
        }
    }

    public Periodo getPeriodo(int semestre) {
        if (semestre < 1) {
            throw new IllegalArgumentException("o semestre deve ser >= 1");
        }
        return periodos.get(semestre);
    }

    public List<Periodo> getPeriodos() {
        return Collections.unmodifiableList(new ArrayList<Periodo>(periodos
                                                                   .values()));
    }

    public static PlanoDeCurso getPlanoInicial() throws ErroDeAlocacaoException {
        PlanoDeCurso planoInicial = new PlanoDeCurso();
        planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Programação I"));
        planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Leitura e Prod. de Textos"));
        planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Cálculo I"));
        planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Álgebra Vetorial"));
        planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Int. à Computação"));
        planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Lab. de Programação I"));
        return planoInicial;
    }
}
