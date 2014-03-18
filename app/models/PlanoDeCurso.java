package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class PlanoDeCurso extends Model {
    private static final int NUM_PERIODOS = 14;

    @Id
    public Long id;

    @OneToMany
    public List<Periodo> periodos = new ArrayList<Periodo>();

    public PlanoDeCurso() {
        for (int i = 0; i < NUM_PERIODOS; i++) {
            periodos.add(new Periodo(i + 1));
        }
    }

    public Periodo getPeriodo(int semestre) {
        return periodos.get(semestre - 1);
    }

    public Set<Disciplina> getDisciplinas() {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        for (Periodo periodo : periodos) {
            disciplinas.addAll(periodo.disciplinas);
        }
        return disciplinas;
    }

    public Set<Disciplina> getDisciplinas(int semestre) {
        return getPeriodo(semestre).disciplinas;
    }

    public int getTotalCreditos(int semestre) {
        return getPeriodo(semestre).getTotalCreditos();
    }

    public void alocarDisciplina(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        validarRequisitos(semestre, disciplina);
        getPeriodo(semestre).alocarDisciplina(disciplina);
        save();
    }

    public void moverDisciplina(int deSemestre, int paraSemestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        Periodo dePeriodo = getPeriodo(deSemestre);
        dePeriodo.desalocarDisciplina(disciplina);
        Periodo paraPeriodo = getPeriodo(paraSemestre);
        try {
            paraPeriodo.alocarDisciplina(disciplina);
            save();
        } catch (ErroDeAlocacaoException e) {
            dePeriodo.alocarDisciplina(disciplina);
            throw e;
        }
    }

    public void desalocarDisciplina(int semestre, Disciplina disciplina) {
        desalocarDisciplinaRecursivamente(semestre, disciplina);
        save();
    }

    private void desalocarDisciplinaRecursivamente(int semestre, Disciplina disciplina) {
        Periodo periodo = getPeriodo(semestre);

        periodo.desalocarDisciplina(disciplina);

        Map<Integer,List<Disciplina>> remover = new HashMap<Integer,List<Disciplina>>();

        for (int i = semestre; i <= NUM_PERIODOS; i++) {
            remover.put(i, new ArrayList<Disciplina>());

            for (Disciplina outra : getPeriodo(i).disciplinas) {
                if (outra.requisitos.contains(disciplina)) {
                    remover.get(i).add(outra);
                }
            }
        }

        for (Integer i : remover.keySet()) {
            for (Disciplina outra : remover.get(i)) {
                desalocarDisciplinaRecursivamente(i, outra);
            }
        }
    }

    private void validarRequisitos(int semestre, Disciplina disciplina) throws ErroDeAlocacaoException {
        Set<Disciplina> pagas = new HashSet<Disciplina>();

        for (int i = 1; i < semestre; i++) {
            pagas.addAll(getDisciplinas(i));
        }

        Set<Disciplina> devendo = new HashSet<Disciplina>(disciplina.requisitos);

        devendo.removeAll(pagas);

        if (devendo.isEmpty()) {
            return;
        }

        String template = "%s tem requisitos não satisfeitos: %s.";

        StringBuilder builder = new StringBuilder();

        Iterator<Disciplina> it = devendo.iterator();

        while (it.hasNext()) {
            builder.append(it.next().nome);

            if (it.hasNext()) {
                builder.append(", ");
            }
        }

        String message = String.format(template, disciplina.nome, builder.toString());

        throw new ErroDeAlocacaoException(message);
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
