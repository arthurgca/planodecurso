package config;

import java.util.*;
import javax.persistence.*;

import play.*;
import play.libs.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {


    @Override
    public void onStart(Application app) {
        carregarInitialData(app);
    }

    private void carregarInitialData(Application app) {
        if (Disciplina.find.all().isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all =
                (Map<String,List<Object>>) Yaml.load("initial-data.yml");

            Ebean.save(all.get("curriculos"));

            for (Object curriculo : all.get("curriculos")) {
                for(Disciplina disciplina : ((Curriculo) curriculo).disciplinas) {
                    Ebean.saveManyToManyAssociations(disciplina, "requisitos");
                }
            }

            Ebean.save(all.get("grades"));

            for (Object grade : all.get("grades")) {
                for(Periodo periodo : ((Grade) grade).periodos) {
                    Ebean.saveManyToManyAssociations(periodo, "disciplinas");
                }
            }
        }
    }
}
