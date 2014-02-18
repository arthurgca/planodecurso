package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import models.CatalogoDeDisciplinas;
import models.Disciplina;

import org.jdom2.JDOMException;

import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.data.format.Formatters.*;

public class Global extends GlobalSettings {

	private static CatalogoDeDisciplinas catalogoDeDisciplinas;

	@Override
	public void onStart(Application app) {
		configuraCatalogoDeDisciplinas(app);
		configuraDataBinds();
	}

	public static CatalogoDeDisciplinas getCatalogoDeDisciplinas() {
		return catalogoDeDisciplinas;
	}

	private void configuraCatalogoDeDisciplinas(Application app) {
		try {
			catalogoDeDisciplinas = new CatalogoDeDisciplinas(new FileReader(
					app.configuration().getString("planoDeCurso.disciplinasXML")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void configuraDataBinds() {
		Formatters.register(Disciplina.class,
				new SimpleFormatter<Disciplina>() {
					@Override
					public Disciplina parse(String input, Locale locale)
							throws ParseException {
						return getCatalogoDeDisciplinas().get(
								Integer.valueOf(input));
					}

					@Override
					public String print(Disciplina disciplina, Locale locale) {
						return String.valueOf(disciplina.getId());
					}

				});
	}

}
