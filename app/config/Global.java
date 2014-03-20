package config;

import java.util.*;
import javax.persistence.*;

import play.*;
import play.libs.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public static PlanoDeCurso PLANO_DE_CURSO_GLOBAL;

    @Override
    public void onStart(Application app) {
        configurarDadosIniciais(app);
        configurarPlanoDeCursoGlobal(app);
    }

    private void configurarDadosIniciais(Application app) {
        if (Disciplina.find.all().isEmpty()) {
            @SuppressWarnings("unchecked")
                Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");

            Ebean.save(all.get("usuarios"));

            Ebean.save(all.get("curriculos"));

            for (Object curriculo : all.get("curriculos")) {
                for(Disciplina disciplina : ((Curriculo) curriculo).disciplinas) {
                    Ebean.saveManyToManyAssociations(disciplina, "requisitos");
                }
            }
        }
    }

    private void configurarPlanoDeCursoGlobal(Application app) {
        Ebean.delete(PlanoDeCurso.find.all());
        Global.PLANO_DE_CURSO_GLOBAL = PlanoDeCurso.criarPlanoInicial();
    }
}
