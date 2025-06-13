package org.br.com.testes.manager;

public class BoletoManager {

    public static final ThreadLocal<String> barCodeNumber = new ThreadLocal<>();
    public static final ThreadLocal<String> digitableLine = new ThreadLocal<>();

    public static String getBarCodeNumber() {
        return barCodeNumber.get();
    }

    public static String getDigitableLine() {
        return digitableLine.get();
    }

    public static void setBarCodeNumber(String id) {
        barCodeNumber.set(id);
    }

    public static void setDigitableLine(String id) {
        digitableLine.set(id);
    }

    public static void remove() {
        barCodeNumber.remove();
        digitableLine.remove();
    }

}
