package com.alexamy.nsa2.example.apt.jte.model;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class BuilderModel {

    private ProcessingEnvironment processingEnv;
    private TypeElement typeElement;

    private String packageName;
    private String className;

    public BuilderModel() {
    }

    public ProcessingEnvironment getProcessingEnv() {
        return processingEnv;
    }

    public void setProcessingEnv(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
