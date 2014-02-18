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

	private static String CADEIRAS_XML = "conf/cadeiras.xml";

	private static CatalogoDeDisciplinas catalogoDeDisciplinas;

	@Override
	public void onStart(Application app) {
		configuraCatalogoDeDisciplinas();
		configuraDataBinds();
	}

	public static CatalogoDeDisciplinas getCatalogoDeDisciplinas() {
		return catalogoDeDisciplinas;
	}

	private void configuraCatalogoDeDisciplinas() {
		try {
			catalogoDeDisciplinas = new CatalogoDeDisciplinas(new FileReader(
					CADEIRAS_XML));
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
