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
        PlanoDeCurso plano = new PlanoDeCurso();
        try {
            plano.alocarDisciplina(1, Disciplina.Registro.get("Programação I"));
            plano.alocarDisciplina(1, Disciplina.Registro.get("Leitura e Prod. de Textos"));
            plano.alocarDisciplina(1, Disciplina.Registro.get("Cálculo I"));
            plano.alocarDisciplina(1, Disciplina.Registro.get("Álgebra Vetorial"));
            plano.alocarDisciplina(1, Disciplina.Registro.get("Int. à Computação"));
            plano.alocarDisciplina(1, Disciplina.Registro.get("Lab. de Programação I"));

            plano.alocarDisciplina(2, Disciplina.Registro.get("Programação II"));
            plano.alocarDisciplina(2, Disciplina.Registro.get("Lab. de Programação II"));
            plano.alocarDisciplina(2, Disciplina.Registro.get("Matemática Discreta"));
            plano.alocarDisciplina(2, Disciplina.Registro.get("Teoria dos Grafos"));
            plano.alocarDisciplina(2, Disciplina.Registro.get("Fund. de Física Clássica"));
            plano.alocarDisciplina(2, Disciplina.Registro.get("Cálculo II"));
            plano.alocarDisciplina(2, Disciplina.Registro.get("Metodologia Científica"));

            plano.alocarDisciplina(3, Disciplina.Registro.get("Álgebra Linear"));
            plano.alocarDisciplina(3, Disciplina.Registro.get("Probabilidade e Est."));
            plano.alocarDisciplina(3, Disciplina.Registro.get("Teoria da Computação"));
            plano.alocarDisciplina(3, Disciplina.Registro.get("Estrutura de Dados"));
            plano.alocarDisciplina(3, Disciplina.Registro.get("Fund. de Física Moderna"));
            plano.alocarDisciplina(3, Disciplina.Registro.get("Gerência da Informação"));
            plano.alocarDisciplina(3, Disciplina.Registro.get("Lab. de Estrutura de Dados"));

            plano.alocarDisciplina(4, Disciplina.Registro.get("Métodos Estatísticos"));
            plano.alocarDisciplina(4, Disciplina.Registro.get("Paradigmas de Linguagens de Programação"));
            plano.alocarDisciplina(4, Disciplina.Registro.get("Lógica Matemática"));
            plano.alocarDisciplina(4, Disciplina.Registro.get("Org. e Arquitetura de Computadores I"));
            plano.alocarDisciplina(4, Disciplina.Registro.get("Engenharia de Software I"));
            plano.alocarDisciplina(4, Disciplina.Registro.get("Sistemas de Informação I"));
            plano.alocarDisciplina(4, Disciplina.Registro.get("Lab. de Org. e Arquitetura de Computadores"));

            plano.alocarDisciplina(5, Disciplina.Registro.get("Informática e Sociedade"));
            plano.alocarDisciplina(5, Disciplina.Registro.get("Análise e Técnicas de Algoritmos"));
            plano.alocarDisciplina(5, Disciplina.Registro.get("Compiladores"));
            plano.alocarDisciplina(5, Disciplina.Registro.get("Redes de Computadores"));
            plano.alocarDisciplina(5, Disciplina.Registro.get("Banco de Dados I"));
            plano.alocarDisciplina(5, Disciplina.Registro.get("Sistemas de Informação II"));
            plano.alocarDisciplina(5, Disciplina.Registro.get("Laboratório de Engenharia de Software"));

            plano.alocarDisciplina(6, Disciplina.Registro.get("Direito e Cidadania"));
            plano.alocarDisciplina(6, Disciplina.Registro.get("Sistemas Operacionais"));
            plano.alocarDisciplina(6, Disciplina.Registro.get("Interconexão de Redes de Computadores"));
            plano.alocarDisciplina(6, Disciplina.Registro.get("Banco de Dados II"));
            plano.alocarDisciplina(6, Disciplina.Registro.get("Inteligência Artificial I"));
            plano.alocarDisciplina(6, Disciplina.Registro.get("Lab. de Interconexão de Redes de Computadores"));

            plano.alocarDisciplina(7, Disciplina.Registro.get("Métodos e Software Numéricos"));
            plano.alocarDisciplina(7, Disciplina.Registro.get("Aval. de Desempenho de Sist. Discretos"));
            plano.alocarDisciplina(7, Disciplina.Registro.get("Projeto em Computação I"));

            plano.alocarDisciplina(8, Disciplina.Registro.get("Projeto em Computação II"));
        } catch (ErroDeAlocacaoException e) {
            assert false; // nota: apenas um bug pode causar essa exceção
        }
        return plano;
    }
}
