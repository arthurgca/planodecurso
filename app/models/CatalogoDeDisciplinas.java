package models;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CatalogoDeDisciplinas {

	private static final Map<Integer, Disciplina> disciplinas = new HashMap<Integer, Disciplina>();

	public CatalogoDeDisciplinas(FileReader disciplinasXML)
			throws JDOMException, IOException {
		importaDisciplinasXML(disciplinasXML);
	}

	private void importaDisciplinasXML(FileReader disciplinasXML)
			throws JDOMException, IOException {
		SAXBuilder sb = new SAXBuilder();
		Document d = sb.build(disciplinasXML);
		Element mural = d.getRootElement();
		List<Element> elements = mural.getChildren();
		Iterator<Element> i = elements.iterator();
		while (i.hasNext()) {
			List<String> requisitosList = new ArrayList<String>();
			Element element = (Element) i.next();
			for (Element e : element.getChild("requisitos").getChildren("id")) {
				requisitosList.add(e.getValue());
			}
			registraDisciplina(
					Integer.parseInt(element.getAttributeValue("id")),
					element.getAttributeValue("nome"),
					Integer.parseInt(element.getChildText("creditos")),
					Integer.parseInt(element.getChildText("periodo")),
					Integer.parseInt(element.getChildText("dificuldade")),
					requisitosList.toArray(new String[requisitosList.size()]));
		}
	}

	private void registraDisciplina(int id, String nome, int creditos,
			int periodo, int dificuldade, String... dependenciaIds) {
		List<Disciplina> dependencias = new ArrayList<Disciplina>();

		for (String dependenciaId : dependenciaIds) {
			dependencias.add(disciplinas.get(Integer.parseInt(dependenciaId)));
		}

		Disciplina[] dependenciasArray = dependencias
				.toArray(new Disciplina[dependencias.size()]);
		disciplinas.put(id, new Disciplina(id, nome, creditos, periodo,
				dificuldade, dependenciasArray));
	}

	public Disciplina get(int i) {
		if (!disciplinas.containsKey(i)) {
			throw new IllegalArgumentException();
		}

		return disciplinas.get(i);
	}

	public Collection<Disciplina> getDisciplinas() {
		return Collections.unmodifiableCollection(disciplinas.values());
	}
}
