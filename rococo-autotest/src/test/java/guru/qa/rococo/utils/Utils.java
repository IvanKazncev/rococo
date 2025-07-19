package guru.qa.rococo.utils;

public class Utils {

    public static String getPropertyOrSystemProperty(String nameProperty) {
        var property = System.getProperty(nameProperty);
        return property != null ? property : System.getenv(nameProperty);
    }
}
