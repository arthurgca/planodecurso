package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class PlanoDeCurso extends Model {
    private static final int MAX_NUMERO_SEMESTRES = 14;
    private static final int MAX_CREDITOS_SEMESTRE = 28;

    @Id
    public Long id;

    @OneToMany(cascade = CascadeType.ALL)
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
        validaAlocacao(semestre, disciplina, false);
        alocacoes.add(new Alocacao(semestre, disciplina));
        save();
    }

    public void moverDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        Alocacao alocacao = getAlocacao(disciplina);
        if (alocacao == null) {
            return;
        }

        validaAlocacao(semestre, disciplina, true);
        alocacao.semestre = semestre;
        save();
    }

    public void desalocarDisciplina(Disciplina disciplina) {
        Alocacao alocacao = getAlocacao(disciplina);
        if (alocacao != null) {
            desalocarDisciplinaRecursivamente(alocacao);
            save();
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

    private void validaAlocacao(
          int semestre,
          Disciplina disciplina,
          boolean isMovimento) throws ErroDeAlocacaoException {

        String template;
        String message;

        if (semestre < 1 || semestre > MAX_NUMERO_SEMESTRES) {
            template = "O período precisa estar entre %s e %s.";
            message = String.format(template, 1, MAX_NUMERO_SEMESTRES);
            throw new ErroDeAlocacaoException(message);
        }

        int totalCreditos = getTotalCreditos(semestre) + disciplina.creditos;

        if (totalCreditos > MAX_CREDITOS_SEMESTRE) {
            template = "<b>%s</b> ultrapassa o limite do <b>%sº período.</b>";
            message = String.format(template, disciplina.nome, semestre);
            throw new ErroDeAlocacaoException(message);
        }

        if (isMovimento) {
            return;
        }

        Alocacao alocacaoAnterior = getAlocacao(disciplina);

        if (alocacaoAnterior != null) {
            template = "<b>%s</b> já foi alocada no <b>%sº período.</b>";
            message = String.format(template, disciplina.nome, semestre);
            throw new ErroDeAlocacaoException(message);
        }

        Set<Disciplina> pagas = new HashSet<Disciplina>();

        for (int i = 1; i < semestre; i++) {
            pagas.addAll(getDisciplinas(i));
        }

        Set<Disciplina> devendo = new HashSet<Disciplina>(disciplina.requisitos);
        devendo.removeAll(pagas);

        if (!devendo.isEmpty()) {
            template = "<b>%s</b> tem <b>requisitos não satisfeitos</b>: %s.";

            StringBuilder sb = new StringBuilder();
            Iterator<Disciplina> it = devendo.iterator();
            while (it.hasNext()) {
                Disciplina requisito = it.next();
                sb.append(requisito.nome);
                if (it.hasNext())
                    sb.append(", ");
            }
            String joined = sb.toString();

            message = String.format(template, disciplina.nome, joined);
            throw new ErroDeAlocacaoException(message);
        }
    }

    public static PlanoDeCurso criarPlanoInicial() {
        PlanoDeCurso plano = new PlanoDeCurso();

        try {
            plano.alocarDisciplina(1, Disciplina.get("Programação I"));
            plano.alocarDisciplina(1, Disciplina.get("Leitura e Prod. de Textos"));
            plano.alocarDisciplina(1, Disciplina.get("Cálculo I"));
            plano.alocarDisciplina(1, Disciplina.get("Álgebra Vetorial"));
            plano.alocarDisciplina(1, Disciplina.get("Int. à Computação"));
            plano.alocarDisciplina(1, Disciplina.get("Lab. de Programação I"));

            plano.alocarDisciplina(2, Disciplina.get("Programação II"));
            plano.alocarDisciplina(2, Disciplina.get("Lab. de Programação II"));
            plano.alocarDisciplina(2, Disciplina.get("Matemática Discreta"));
            plano.alocarDisciplina(2, Disciplina.get("Teoria dos Grafos"));
            plano.alocarDisciplina(2, Disciplina.get("Fund. de Física Clássica"));
            plano.alocarDisciplina(2, Disciplina.get("Cálculo II"));
            plano.alocarDisciplina(2, Disciplina.get("Metodologia Científica"));

            plano.alocarDisciplina(3, Disciplina.get("Álgebra Linear"));
            plano.alocarDisciplina(3, Disciplina.get("Probabilidade e Est."));
            plano.alocarDisciplina(3, Disciplina.get("Teoria da Computação"));
            plano.alocarDisciplina(3, Disciplina.get("Estrutura de Dados"));
            plano.alocarDisciplina(3, Disciplina.get("Fund. de Física Moderna"));
            plano.alocarDisciplina(3, Disciplina.get("Gerência da Informação"));
            plano.alocarDisciplina(3, Disciplina.get("Lab. de Estrutura de Dados"));

            plano.alocarDisciplina(4, Disciplina.get("Métodos Estatísticos"));
            plano.alocarDisciplina(4, Disciplina.get("Paradigmas de Linguagens de Programação"));
            plano.alocarDisciplina(4, Disciplina.get("Lógica Matemática"));
            plano.alocarDisciplina(4, Disciplina.get("Org. e Arquitetura de Computadores I"));
            plano.alocarDisciplina(4, Disciplina.get("Engenharia de Software I"));
            plano.alocarDisciplina(4, Disciplina.get("Sistemas de Informação I"));
            plano.alocarDisciplina(4, Disciplina.get("Lab. de Org. e Arquitetura de Computadores"));

            plano.alocarDisciplina(5, Disciplina.get("Informática e Sociedade"));
            plano.alocarDisciplina(5, Disciplina.get("Análise e Técnicas de Algoritmos"));
            plano.alocarDisciplina(5, Disciplina.get("Compiladores"));
            plano.alocarDisciplina(5, Disciplina.get("Redes de Computadores"));
            plano.alocarDisciplina(5, Disciplina.get("Banco de Dados I"));
            plano.alocarDisciplina(5, Disciplina.get("Sistemas de Informação II"));
            plano.alocarDisciplina(5, Disciplina.get("Laboratório de Engenharia de Software"));

            plano.alocarDisciplina(6, Disciplina.get("Direito e Cidadania"));
            plano.alocarDisciplina(6, Disciplina.get("Sistemas Operacionais"));
            plano.alocarDisciplina(6, Disciplina.get("Interconexão de Redes de Computadores"));
            plano.alocarDisciplina(6, Disciplina.get("Banco de Dados II"));
            plano.alocarDisciplina(6, Disciplina.get("Inteligência Artificial I"));
            plano.alocarDisciplina(6, Disciplina.get("Lab. de Interconexão de Redes de Computadores"));

            plano.alocarDisciplina(7, Disciplina.get("Métodos e Software Numéricos"));
            plano.alocarDisciplina(7, Disciplina.get("Aval. de Desempenho de Sist. Discretos"));
            plano.alocarDisciplina(7, Disciplina.get("Projeto em Computação I"));

            plano.alocarDisciplina(8, Disciplina.get("Projeto em Computação II"));
        } catch (ErroDeAlocacaoException e) {
            assert false; // nota: apenas um bug pode causar essa exceção
        }

        plano.save();

        return plano;
    }

    public static Finder<Long,PlanoDeCurso> find =
        new Finder<Long,PlanoDeCurso>(Long.class, PlanoDeCurso.class);
}
