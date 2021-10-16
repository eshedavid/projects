package com.craftinginterpreters.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * This class generates the different classes used by the Interpreter when
 * building the AST
 * @param writer the writer to the output stream.
 * @param baseName the base name of the abstract class.
 * @param className the class name being generated.
 * @param fieldList the list of fields related to the class being rendered.
 */
public class GenerateAst {
    
    public static void main (String[] args) throws IOException {

        if(args.length != 1) {

            System.err.println("Usage: generage_ast <output directory>");
            System.exit(64);
        }

        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary   : Expr left, Token operator, Expr right",
            "Grouping : Expr expression",
            "Literal  : Object value",
            "Unary    : Token operator, Expr right"
        ));
    }

    /**
     * Defines each class for the specified schema.
     * @param outputDir the directory where the output file will reside.
     * @param baseName the abstract class base name.
     * @param types the class definitions.
     * @throws IOException
     */
    private static void defineAst(
        String outputDir, String baseName, List<String> types
    ) throws IOException {

        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.craftinginterpreters.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();

        // define the base abstract class.
        writer.println("abstract class " + baseName + " {");

        defineVisitor(writer, baseName, types);

        writer.println("    }");
        writer.println();

        for(String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            // define each class and its fields.
            defineType(writer, baseName, className, fields);
            writer.println();
        }

        // base accept() method.
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    public static void defineVisitor(
        PrintWriter writer, String baseName, List<String> types
    ) {
        writer.println("    interface Visitor<R> {");

        for(String type: types) {
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" +
                typeName + " " + baseName.toLowerCase() + ");");
        }
        
    }

    /**
     * Given a class, it generates the code with its constructor and its fields.
     * @param writer the output file writer.
     * @param baseName the abstract class name.
     * @param className the name of the class being generated.
     * @param fieldList the list of fields defined for the class.
     */
    private static void defineType(
        PrintWriter writer, String baseName, String className, String fieldList
    ) {
        // class definition
        writer.println("    static class " + className + " extends " +
            baseName + " {");

        // constructor
        writer.println("        "  + className + "(" + fieldList + ") {");

        // constructor population.
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println("            this." + name + " = " + name + ";");
        }

        writer.println("        }");

        writer.println();

        // field definitions.
        for (String field : fields) {
            writer.println("        final " + field + ";");
        }

        // Visitor pattern.
        writer.println("        @Override");
        writer.println("        <R> R accept(Visitor<R> visitor) {");
        writer.println(
            "            return visitor.visit" + className + baseName + "(this);"
        );
        writer.println("        }");

        writer.println("    }");

    }
}
