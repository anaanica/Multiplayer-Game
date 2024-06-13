package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Author(name = "Ana")
public final class ReflectionUtils {
    private ReflectionUtils() {
        throw new RuntimeException();
    }

    public static void generateCompleteDocumentation() {
        try {
            List<Path> filesList = Files.walk(Path.of("."))
                    .filter(p ->
                            (p.getFileName().toString().endsWith(".class")
                                    && !p.getFileName().toString().contains("module-info")))
                    .collect(Collectors.toList());

            StringBuilder documentationString = new StringBuilder();

            for (Path path : filesList) {
                String fullQualifiedClassName = getFullQualifiedClassName(path);
                Class<?> clazz = Class.forName(fullQualifiedClassName);

                documentationString.append("<div class=\"col-12 col-md-6 col-lg-6 mb-4 my-col\">");

                readClassInfo(clazz, documentationString);

                documentationString.append("<h4> List of member variables: </h4>\n");
                appendFields(clazz, documentationString);

                documentationString.append("<h4> List of methods: </h4>\n");
                appendMethods(clazz, documentationString);

                documentationString.append("<h4> List of constructors: </h4>\n");
                appendConstructors(clazz, documentationString);

                documentationString.append("</div>");
            }

            String content = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>Class documentation</title>\n" +
                    "<script src=\"../src/main/resources/hr/algebra/threerp3/tictactoe3rp3/view/js/jquery-3.3.1.slim.min.js\"></script>\n" +
                    "<script src=\"../src/main/resources/hr/algebra/threerp3/tictactoe3rp3/view/js/bootstrap.bundle.min.js\"></script>\n" +
                    "<link rel=\"stylesheet\" href=\"../src/main/resources/hr/algebra/threerp3/tictactoe3rp3/view/style/bootstrap.min.css\">\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"../src/main/resources/hr/algebra/threerp3/tictactoe3rp3/view/style/documentation.css\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"container-fluid\">" +
                    "\n" +
                    "<h1>List of classes</h1>\n<hr>\n" +
                    "<div class=\"row\">" +
                    documentationString.toString() +
                    "</div>" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            Files.writeString(Path.of(FileUtils.DOCUMENTATION_FILE_NAME), content, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFullQualifiedClassName(Path path) {
        String fullQualifiedClassName = "";

        //windows
        //String[] paths = path.toString().split("\\\\");
        //mac
        String[] paths = path.toString().split(File.separator);

        boolean startJoining = false;

        for (String segment : paths) {
            if ("classes".equals(segment)) {
                startJoining = true;
                continue;
            }

            if (startJoining) {
                if (segment.endsWith(".class")) {
                    fullQualifiedClassName += segment.substring(0, segment.lastIndexOf("."));
                } else {
                    fullQualifiedClassName += segment + ".";
                }
            }
        }

        return fullQualifiedClassName;
    }

    private static void readClassInfo(Class<?> clazz, StringBuilder documentationString) {
        documentationString.append("<h3>");
        appendAnnotations(clazz, documentationString);
        documentationString.append("</h3>\n");
        documentationString.append("<h3>");
        appendPackages(clazz, documentationString);
        documentationString.append("</h3>\n");
        documentationString.append("<h2>");
        appendModifiers(clazz, documentationString);
        documentationString.append(clazz.getSimpleName()).append("</h2>\n");
        documentationString.append("<h3>");
        appendParent(clazz, documentationString, true);
        appendInterfaces(clazz, documentationString);
        documentationString.append("</h3>\n");
    }

    private static void appendPackages(Class<?> clazz, StringBuilder documentationString) {
        documentationString
                .append(clazz.getPackage())
                .append("<br />\n");
    }

    private static void appendModifiers(Class<?> clazz, StringBuilder documentationString) {
        documentationString.append(Modifier.toString(clazz.getModifiers())).append(" ");
    }

    private static void appendParent(Class<?> clazz, StringBuilder documentationString, boolean first) {
        Class<?> parent = clazz.getSuperclass();
        if(parent == null) {
            return;
        }
        documentationString
                .append(first ? " extends " : " -> ")
                .append(parent.getSimpleName());
        appendParent(parent, documentationString, false);
    }
    private static void appendInterfaces(Class<?> clazz, StringBuilder documentationString) {
        if (clazz.getInterfaces().length > 0) {
            documentationString
                    .append(" implements ")
                    .append(Stream.of(clazz.getInterfaces())
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(", ")));
        }
    }

    private static void appendAnnotations(Class<?> clazz, StringBuilder documentationString) {
        documentationString.append(
                Stream.of(clazz.getAnnotations())
                        .map(annotation -> {
                            String annotationStr = annotation.toString();
                            int lastDotIndex = annotationStr.lastIndexOf(".");
                            if (lastDotIndex != -1) {
                                annotationStr = annotationStr.substring(lastDotIndex + 1);
                            }
                            return "@" + annotationStr;
                        })
                        .collect(Collectors.joining("<br />\n")));
    }

    private static void appendFields(Class<?> clazz, StringBuilder documentationString) {
        Field[] fields = clazz.getDeclaredFields();
        documentationString.append(
                Stream.of(fields)
                        .map(field -> {
                            String fieldName = field.getName();
                            String fieldType = field.getType().getSimpleName();
                            String modifiers = Modifier.toString(field.getModifiers());
                            return modifiers + " " + fieldType + " " + fieldName;
                        })
                        .collect(Collectors.joining("<br />\n"))
        );
    }

    private static void appendMethods(Class<?> clazz, StringBuilder documentationString) {
        for (Method method : clazz.getDeclaredMethods()) {
            appendAnnotations(method, documentationString);
            appendModifiers(method.getDeclaringClass(), documentationString);
            documentationString
                    .append(method.getReturnType().getSimpleName())
                    .append(" ")
                    .append(method.getName());
            appendParameters(method, documentationString);
            appendExceptions(method, documentationString);
            documentationString.append("<br />\n");
        }
    }

    private static void appendConstructors(Class<?> clazz, StringBuilder documentationString) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            appendAnnotations(constructor, documentationString);
            documentationString
                    .append(Modifier.toString(constructor.getModifiers()))
                    .append(" ")
                    .append(constructor.getDeclaringClass().getSimpleName());
            appendParameters(constructor, documentationString);
            appendExceptions(constructor, documentationString);
            documentationString.append("<br />\n");
        }
    }


    private static void appendAnnotations(Executable executable, StringBuilder documentationString) {
        documentationString.append(
                Stream.of(executable.getAnnotations())
                        .map(Objects::toString)
                        .collect(Collectors.joining(" ")));
    }

    private static void appendParameters(Executable executable, StringBuilder documentationString) {
        documentationString.append(
                Stream.of(executable.getParameters())
                        .map(param -> param.getType().getSimpleName() + " " + param.getName())
                        .collect(Collectors.joining(", ", "(", ")"))
        );
    }

    private static void appendExceptions(Executable executable, StringBuilder documentationString) {
        if (executable.getExceptionTypes().length > 0) {
            documentationString.append(
                    Stream.of(executable.getExceptionTypes())
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(", ", " throws ", ""))); // no String.empty like in C#
        }
    }

}
