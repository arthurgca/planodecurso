package config;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import models.Disciplina;

import org.jdom2.JDOMException;

import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		configuraRegistroDeDisciplinas(app);
		configuraDataBinds();
	}

	private void configuraRegistroDeDisciplinas(Application app) {
		try {
			FileReader disciplinasXML = new FileReader(
					app.configuration().getString("planoDeCurso.disciplinasXML"));
			RegistroDeDisciplinas.registraDoArquivo(disciplinasXML);
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
						return RegistroDeDisciplinas.get(
								Integer.valueOf(input));
					}

					@Override
					public String print(Disciplina disciplina, Locale locale) {
						return String.valueOf(disciplina.getId());
					}

				});
	}

}
