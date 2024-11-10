package com.alexamy.nsa2.example.apt.processor;

import com.alexamy.nsa2.example.apt.annotations.Nsa2Builder;
import com.alexamy.nsa2.example.apt.freemarker.FreeMarkerConfigurationSingleton;
import com.google.auto.service.AutoService;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.alexamy.nsa2.example.apt.annotations.Nsa2Builder")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class BuilderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //System.out.println("processing...");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Nsa2Builder.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
                return true; // Exit processing
            }
            TypeElement typeElement = (TypeElement) annotatedElement;


            String builderClassName = typeElement.getSimpleName() + "Builder";
//            String className = typeElement.getSimpleName().toString();
            String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();

            try {
                JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + builderClassName);
                try (Writer writer = builderFile.openWriter()) {

                    List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);

                    List<? extends  Element> allFields = allMembers.stream().filter(element -> element.getKind() == ElementKind.FIELD).toList();



                    Map<String, Object> model = new HashMap<>();
                    model.put("processingEnv", processingEnv);
                    model.put("typeElement", typeElement);
                    model.put("packageName", packageName);
                    model.put("builderClassName", builderClassName);
                    model.put("className", typeElement.getSimpleName());
                    model.put("allFields", allFields);

                    Template template = FreeMarkerConfigurationSingleton.getInstance().getTemplate("builder.ftl");
                    template.process(model, writer);


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Processing complete.");
        return true;
    }
}
