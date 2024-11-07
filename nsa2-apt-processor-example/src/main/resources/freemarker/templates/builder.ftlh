package ${packageName};

public class ${builderClassName} {

<#list allFields as field>
    private ${field.asType()} ${field.simpleName};
</#list>

    private ${builderClassName}() {
    }

    public static ${builderClassName} builder() {
        return new ${builderClassName}();
    }

<#list allFields as field>
    public ${builderClassName} ${field.simpleName}(${field.asType()} ${field.simpleName}) {
        this.${field.simpleName} = ${field.simpleName};
        return this;
    }
</#list>

    public ${className} build() {
        return new ${className}(
        <#list allFields as field>
            ${field.simpleName}<#if field_has_next>,</#if>
        </#list>
        );
    }

}