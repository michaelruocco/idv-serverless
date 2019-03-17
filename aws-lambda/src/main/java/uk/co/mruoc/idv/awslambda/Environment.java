package uk.co.mruoc.idv.awslambda;

public class Environment {

    private Environment() {
        // utility class
    }

    public static String getRegion() {
        return System.getenv("REGION");
    }

    public static String getStage() {
        return System.getenv("STAGE");
    }

}
