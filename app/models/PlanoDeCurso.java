package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class PlanoDeCurso extends Model {

    @Id
    public Long id;

    public PlanoDeCurso() {
    }

    public Set<Disciplina> getDisciplinas() {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        return disciplinas;
    }

   public Set<Disciplina> getDisciplinas(int semestre) {
       Set<Disciplina> disciplinas = new HashSet<Disciplina>();
       return disciplinas;
    }

    public int getTotalCreditos(int semestre) {
        int totalCreditos = 0;
        return totalCreditos;
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        save();
    }

    public void moverDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        save();
    }

    public void desalocarDisciplina(Disciplina disciplina) {
    }

    private void desalocarDisciplinaRecursivamente(int semestre, Disciplina disciplina) {

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
