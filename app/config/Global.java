package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import models.CatalogoDeDisciplinas;

import org.jdom2.JDOMException;

import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
	
	private static String CADEIRAS_XML = "conf/cadeiras.xml";
	
	private static CatalogoDeDisciplinas catalogoDeDisciplinas;
	
	@Override
	public void onStart(Application app) {
		configuraCatalogoDeDisciplinas();
	}

	public static CatalogoDeDisciplinas getCatalogoDeDisciplinas() {
		return catalogoDeDisciplinas;
	}

	private void configuraCatalogoDeDisciplinas() {
		try {
			catalogoDeDisciplinas = new CatalogoDeDisciplinas(new FileReader(CADEIRAS_XML));
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
	
}
