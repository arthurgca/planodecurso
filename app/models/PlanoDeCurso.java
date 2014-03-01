package models;

import java.util.*;

public class PlanoDeCurso {

    public Set<Alocacao> alocacoes = new HashSet<Alocacao>();

    public PlanoDeCurso() {
    }

    public Set<Disciplina> getDisciplinas() {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        for (Alocacao alocacao : alocacoes) {
            disciplinas.add(alocacao.disciplina);
        }
        return disciplinas;
    }

   public Set<Disciplina> getDisciplinas(int semestre) {
       Set<Disciplina> disciplinas = new HashSet<Disciplina>();
       for (Alocacao alocacao : alocacoes) {
           if (alocacao.semestre == semestre) {
               disciplinas.add(alocacao.disciplina);
           }
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

    public void alocarDisciplina(int semestre, int disciplina) throws ErroDeAlocacaoException {
        alocarDisciplina(semestre, Disciplina.Registro.get(disciplina));
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
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
        if (getAlocacao(disciplina) != null) {
            throw new ErroDeAlocacaoException("Disciplina já alocada.");
        }

        alocacoes.add(new Alocacao(semestre, disciplina));
    }

    public void desalocarDisciplina(int disciplina) {
        desalocarDisciplina(Disciplina.Registro.get(disciplina));
    }

    public void desalocarDisciplina(Disciplina disciplina) {
        Alocacao alocacao = getAlocacao(disciplina);
        if (alocacao != null) {
            desalocarDisciplinaRecursivamente(alocacao);
        }
    }

    private void desalocarDisciplinaRecursivamente(Alocacao alocacao) {
        alocacoes.remove(alocacao);

        List<Alocacao> filaRemocao = new ArrayList<Alocacao>();

        for (Alocacao i : alocacoes) {
            if (i.disciplina.requisitos.contains(alocacao.disciplina)) {
                filaRemocao.add(i);
            }
        }

        for (Alocacao i : filaRemocao) {
            desalocarDisciplinaRecursivamente(i);
        }
    }

    private Alocacao getAlocacao(Disciplina disciplina) {
        for (Alocacao alocacao : alocacoes) {
            if (alocacao.disciplina.equals(disciplina)) {
                return alocacao;
            }
        }
        return null;
    }

    public static PlanoDeCurso getPlanoInicial() {
        PlanoDeCurso planoInicial = new PlanoDeCurso();
        try {
            planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Programação I"));
            planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Leitura e Prod. de Textos"));
            planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Cálculo I"));
            planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Álgebra Vetorial"));
            planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Int. à Computação"));
            planoInicial.alocarDisciplina(1, Disciplina.Registro.get("Lab. de Programação I"));
        } catch (ErroDeAlocacaoException e) {
            assert false; // nota: apenas um bug pode causar essa exceção
        }
        return planoInicial;
    }
}
