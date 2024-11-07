package com.alexamy.nsa2.example.apt.processor;

import com.alexamy.nsa2.example.apt.annotations.GenerateBuilder;
import com.alexamy.nsa2.example.apt.freemarker.ConfigurationSingleton;
import com.alexamy.nsa2.example.apt.jte.model.BuilderModel;
import com.google.auto.service.AutoService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.PrintWriterOutput;
import gg.jte.output.StringOutput;
import gg.jte.output.WriterOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.resolve.ResourceCodeResolver;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.alexamy.nsa2.example.apt.annotations.GenerateBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class BuilderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //System.out.println("processing...");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(GenerateBuilder.class)) {
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

                    Template template = ConfigurationSingleton.getInstance().getTemplate("builder.ftlh");
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
