package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class EstatisticasTest {

    Estatisticas e0;

    @Before
    public void setUp() throws Exception {
        Curriculo curriculo = new Curriculo(new HashSet<Disciplina>());
        curriculo.setMinCreditosObrigatorias(5);
        curriculo.setMinDisciplinasObrigatorias(2);
        curriculo.setMinCreditosOptativas(5);
        curriculo.setMinDisciplinasOptativas(2);
        curriculo.setMinCreditosComplementares(5);
        curriculo.setMinDisciplinasComplementares(2);

        e0 = new Estatisticas(curriculo);

        Disciplina d0 = new Disciplina("D0", 2);
        d0.setTipo(Disciplina.Tipo.OBRIGATORIA);
        e0.add(d0);

        Disciplina d1 = new Disciplina("D1", 3);
        d1.setTipo(Disciplina.Tipo.OBRIGATORIA);
        e0.add(d1);

        Disciplina d2 = new Disciplina("D2", 2);
        d2.setTipo(Disciplina.Tipo.OPTATIVA);
        e0.add(d2);

        Disciplina d3 = new Disciplina("D3", 3);
        d3.setTipo(Disciplina.Tipo.OPTATIVA);
        e0.add(d3);

        Disciplina d4 = new Disciplina("D4", 2);
        d4.setTipo(Disciplina.Tipo.COMPLEMENTAR);
        e0.add(d4);

        Disciplina d5 = new Disciplina("D5", 3);
        d5.setTipo(Disciplina.Tipo.COMPLEMENTAR);
        e0.add(d5);
    }

    @Test
    public void estatisticasObrigatorias() {
        Map<String,Integer> rv = e0.getEstatisticasObrigatorias();
        assertEquals((Integer) 2, rv.get("totalDisciplinas"));
        assertEquals((Integer) 2, rv.get("minDisciplinas"));
        assertEquals((Integer) 5, rv.get("totalCreditos"));
        assertEquals((Integer) 5, rv.get("minCreditos"));
    }

    @Test
    public void estatisticasOptativas() {
        Map<String,Integer> rv = e0.getEstatisticasOptativas();
        assertEquals((Integer) 2, rv.get("totalDisciplinas"));
        assertEquals((Integer) 2, rv.get("minDisciplinas"));
        assertEquals((Integer) 5, rv.get("totalCreditos"));
        assertEquals((Integer) 5, rv.get("minCreditos"));
    }

    @Test
    public void estatisticasComplementares() {
        Map<String,Integer> rv = e0.getEstatisticasComplementares();
        assertEquals((Integer) 2, rv.get("totalDisciplinas"));
        assertEquals((Integer) 2, rv.get("minDisciplinas"));
        assertEquals((Integer) 5, rv.get("totalCreditos"));
        assertEquals((Integer) 5, rv.get("minCreditos"));
    }

}
