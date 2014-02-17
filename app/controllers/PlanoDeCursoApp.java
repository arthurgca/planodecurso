package controllers;

import java.io.FileReader;
import java.io.IOException;

import models.CatalogoDeDisciplinas;
import models.ErroDeAlocacaoException;
import models.PlanoDeCurso;

import org.jdom2.JDOMException;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class PlanoDeCursoApp extends Controller {

	public static Result planoInicial() throws ErroDeAlocacaoException, JDOMException, IOException {
		return ok(Json.toJson(PlanoDeCurso.getPlanoInicial(getCatalogo())));
	}
	
	private static CatalogoDeDisciplinas getCatalogo() throws JDOMException, IOException {
		FileReader disciplinasXML = new FileReader("conf/cadeiras.xml");
		return new CatalogoDeDisciplinas(disciplinasXML);
	}

}
