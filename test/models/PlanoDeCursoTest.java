import java.util.*;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import org.fluentlenium.adapter.util.SharedDriver;

import models.*;

public class PlanoDeCursoTest {

    static Form<PlanoDeCurso> planoForm = Form.form(PlanoDeCurso.class);

    @Test
    public void deveCriarPlanoInicialComDisciplinasDoPrimeiroPeriodo() {
        PlanoDeCurso plano = PlanoDeCurso.criarPlanoInicial();

        assertThat(plano.getPeriodos().size()).isEqualTo(1);

        assertThat(plano.getPeriodos().get(0).getDisciplinas()).onProperty("id")
            .containsOnly("CALCULO1", "VETORIAL", "LPT", "P1", "IC", "LP1");
    }

    @Test
    @SharedDriver(type = SharedDriver.SharedType.ONCE)
    public void deveValidarPlanoInicial() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                Map<String, String> anyData = new HashMap<String, String>();

                anyData.put("periodos[0].semestre", "1");
                anyData.put("periodos[0].disciplinas[0]", "CALCULO1");
                anyData.put("periodos[0].disciplinas[1]", "VETORIAL");
                anyData.put("periodos[0].disciplinas[2]", "LPT");
                anyData.put("periodos[0].disciplinas[3]", "P1");
                anyData.put("periodos[0].disciplinas[4]", "IC");
                anyData.put("periodos[0].disciplinas[5]", "LP1");

                assertThat(planoForm.bind(anyData).hasErrors()).isFalse();
            }});
    }

    @Test
    @SharedDriver(type = SharedDriver.SharedType.ONCE)
    public void deveValidarMinimoDeCreditos() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                Map<String, String> anyData = new HashMap<String, String>();

                anyData.put("periodos[0].semestre", "1");
                anyData.put("periodos[0].disciplinas[0]", "CALCULO1");

                assertThat(planoForm.bind(anyData).hasErrors()).isTrue();
            }});
    }

    @Test
    @SharedDriver(type = SharedDriver.SharedType.ONCE)
    public void deveValidarMaximoDeCreditos() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                Map<String, String> anyData = new HashMap<String, String>();

                anyData.put("periodos[0].semestre", "1");
                anyData.put("periodos[0].disciplinas[0]", "CALCULO1");
                anyData.put("periodos[0].disciplinas[1]", "VETORIAL");
                anyData.put("periodos[0].disciplinas[2]", "LPT");
                anyData.put("periodos[0].disciplinas[3]", "P1");
                anyData.put("periodos[0].disciplinas[4]", "IC");
                anyData.put("periodos[0].disciplinas[5]", "LP1");
                anyData.put("periodos[0].disciplinas[6]", "TG");
                anyData.put("periodos[0].disciplinas[7]", "FC");

                assertThat(planoForm.bind(anyData).hasErrors()).isTrue();
            }});
    }

}
