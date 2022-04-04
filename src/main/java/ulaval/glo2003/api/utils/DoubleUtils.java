package ulaval.glo2003.api.utils;

public class DoubleUtils {
    public static double roundToTwoDecimals(double number) {
        return Math.round(number * 100) / 100d;
    }
}
