package config;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import models.Disciplina;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        try {
            configuraRegistroDeDisciplinas(app);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuraDataBinds();
    }

    private void configuraRegistroDeDisciplinas(Application app)
        throws JDOMException, IOException {
        FileReader disciplinasXML = new FileReader(app.configuration()
                                                   .getString("planoDeCurso.disciplinasXML"));

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
                dependencias.add(Disciplina.Registro.get(Integer.valueOf(requisitoIdElement.getValue())));
            }

            Disciplina.Registro.registrarDisciplina(id,
                                                    nome,
                                                    creditos,
                                                    periodo,
                                                    dificuldade,
                                                    dependencias);
        }
    }

    private void configuraDataBinds() {
        Formatters.register(Disciplina.class,
                            new SimpleFormatter<Disciplina>() {
                                @Override
                                    public Disciplina parse(String input, Locale locale)
                                    throws ParseException {
                                    return Disciplina.Registro.get(Integer.valueOf(input));
                                }

                                @Override
                                    public String print(Disciplina disciplina, Locale locale) {
                                    return String.valueOf(disciplina.id);
                                }

                            });
    }
}
