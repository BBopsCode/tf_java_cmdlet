public class JavaVersionCheck {
    public static void main(String[] args) {
        String javaVersion = System.getProperty("java.version");
        String jreVersion = System.getProperty("java.runtime.version");
        String jvmVersion = System.getProperty("java.vm.version");

        System.out.println("Java Version: " + javaVersion);
        System.out.println("JRE Version: " + jreVersion);
        System.out.println("JVM Version: " + jvmVersion);
    }
}