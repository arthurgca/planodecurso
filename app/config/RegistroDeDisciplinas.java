package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import models.Disciplina;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class RegistroDeDisciplinas {

	private static Map<Integer, Disciplina> disciplinas = new HashMap<Integer, Disciplina>();

	public static Disciplina get(int i) {
		return disciplinas.get(i);
	}

	public static Collection<Disciplina> getDisciplinas() {
		return Collections.unmodifiableCollection(disciplinas.values());
	}

	public static void registraDisciplina(int id, String nome, int creditos,
			int periodo, int dificuldade, List<Disciplina> dependencias) {
		Disciplina[] dependenciasArray = dependencias
				.toArray(new Disciplina[dependencias.size()]);
		disciplinas.put(id, new Disciplina(id, nome, creditos, periodo,
				dificuldade, dependenciasArray));
	}

	public static void registraDoArquivo(FileReader disciplinasXML)
			throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(disciplinasXML);

		Element root = document.getRootElement();
		List<Element> disciplinaElements = root.getChildren();

		Iterator<Element> it = disciplinaElements.iterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();

			int id = Integer.parseInt(element.getAttributeValue("id"));

			String nome = element.getAttributeValue("nome");

			int creditos = Integer.parseInt(element.getChildText("creditos"));

			int periodo = Integer.parseInt(element.getChildText("periodo"));

			int dificuldade = Integer.parseInt(element
					.getChildText("dificuldade"));

			List<Disciplina> dependencias = new ArrayList<Disciplina>();

			for (Element requisitoIdElement : element.getChild("requisitos")
					.getChildren("id")) {
				dependencias.add(get(Integer.valueOf(requisitoIdElement
						.getValue())));
			}

			registraDisciplina(id, nome, creditos, periodo, dificuldade,
					dependencias);
		}
	}

	private RegistroDeDisciplinas() {
		// TODO Auto-generated constructor stub
	}
}
