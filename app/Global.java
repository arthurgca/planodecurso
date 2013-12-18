import java.lang.*;
import java.util.*;
import java.text.ParseException;

import play.*;
import play.libs.*;
import play.data.*;
import play.data.format.Formatters;
import play.data.format.Formatters.*;

import models.*;

public class Global extends GlobalSettings {

    static final CatalogoDeDisciplinas catalogoDeDisciplinas = new CatalogoDeDisciplinas();

    public void onStart(Application app) {
        Formatters.register(Disciplina.class, new SimpleFormatter<Disciplina>() {
          @Override
          public Disciplina parse(String input, Locale l) throws ParseException {
              Disciplina disciplina = null;

              try {
                  disciplina = catalogoDeDisciplinas.get(input);
              } catch (IllegalArgumentException e) {
                  throw new ParseException("No valid input", 0);
              }

              return disciplina;
          }

          @Override
          public String print(Disciplina disciplina, Locale l) {
              return disciplina.getId();
          }});
    }

}
