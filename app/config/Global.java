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
        criaUsuariosIniciais(app);
    }

    private void carregarInitialData(Application app) {
        if (Disciplina.find.all().isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all =
                (Map<String,List<Object>>) Yaml.load("initial-data.yml");

            Ebean.save(all.get("curriculos"));

            for (Object curriculo : all.get("curriculos")) {
                for(Disciplina disciplina : ((Curriculo) curriculo).getDisciplinas()) {
                    Ebean.saveManyToManyAssociations(disciplina, "requisitos");
                }
            }
        }
    }

    private void criaUsuariosIniciais(Application app) {
        if (Estudante.find.all().isEmpty()) {
            for (int i = 0; i <= 30; i++) {
                String email = String.format("usuario%d@example.com", i);
                String nome = String.format("Usuario %d", i);
                String senha = String.format("senha%d", i);
                Estudante estudante = new Estudante(email, nome, senha);
                Ebean.save(estudante);
            }
        }
    }
}
