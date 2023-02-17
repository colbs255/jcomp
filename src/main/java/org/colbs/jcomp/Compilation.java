package org.colbs.jcomp;

import javax.tools.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

public class Compilation {

    static String qualifiedClassName = "org.example.X";
    static String sourceCode = """
            package org.example;
            public class X implements Runnable {
                public void run() {
                    System.out.println("Hello world");
                }
            }
            """;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        List<JavaFileObject> sourceFiles = Collections.singletonList(new StringSource(qualifiedClassName, sourceCode));

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        if (!task.call()) {
            System.err.println("Compilation Failed");
            diagnostics.getDiagnostics().forEach(System.err::println);
            return;
        }

        ClassLoader classLoader = manager.getClassLoader(null);
        Class<?> clazz = classLoader.loadClass(qualifiedClassName);
        Runnable instanceOfClass = (Runnable) clazz.getDeclaredConstructor().newInstance();
        System.out.println("Running Program");
        instanceOfClass.run();
    }

}
