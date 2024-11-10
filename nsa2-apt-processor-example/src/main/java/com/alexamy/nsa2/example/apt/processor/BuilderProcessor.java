package com.alexamy.nsa2.example.apt.processor;

import com.alexamy.nsa2.example.apt.annotations.Nsa2Builder;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            String className = typeElement.getSimpleName() + "Builder";
            String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();

            try {
                JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + className);
                try (Writer writer = builderFile.openWriter()) {
                    writer.write("package " + packageName + ";\n");
                    writer.write("public class " + className + " {\n");

                    List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);

                    List<? extends  Element> allFields = allMembers.stream().filter(element -> element.getKind() == ElementKind.FIELD).toList();


                    // Add fields
                    for(Element element : allFields) {
                        writer.write("    private " + element.asType() + " " + element.getSimpleName() + ";\n\n");
                    }

                    // builder method
                    writer.write("    public static " + className + " builder() {\n");
                    writer.write("        return new " + className + "();\n");
                    writer.write("    }\n\n");

                    // setter methods
                    for(Element element : allFields) {
                        writer.write("    public " + className + " " + element.getSimpleName() + "(" + element.asType() + " " + element.getSimpleName() + ") {\n");
                        writer.write("        this." + element.getSimpleName() + " = " + element.getSimpleName() + ";\n");
                        writer.write("        return this;\n");
                        writer.write("    }\n\n");
                    }

                    // username, age
                    String args = allFields.stream().map(Element::getSimpleName).collect(Collectors.joining(", "));

                    // build method
                    writer.write("    public " + typeElement.getSimpleName() + " build() {\n");
                    writer.write("        " + typeElement.getSimpleName() + " " + typeElement.getSimpleName().toString().toLowerCase() + " = new " + typeElement.getSimpleName() + "(" + args + ");\n");
                    writer.write("        return " + typeElement.getSimpleName().toString().toLowerCase() + ";\n");
                    writer.write("    }\n");

                    // End of class
                    writer.write("}\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Processing complete.");
        return true;
    }
}
