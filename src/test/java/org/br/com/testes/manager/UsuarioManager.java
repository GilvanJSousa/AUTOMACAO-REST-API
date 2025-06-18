package org.br.com.testes.manager;

public class UsuarioManager {

    private static final ThreadLocal<String> email = new ThreadLocal<>();

    private static final ThreadLocal<String> password = new ThreadLocal<>();

    private static final ThreadLocal<String> id = new ThreadLocal<>();


    public static String getEmail() {
        return email.get();
    }

    public static String setPassword() {
        return password.get();
    }

    public static String getId() {
        return id.get();
    }

    public static void setEmail(String tk) {
        email.set(tk);
    }

    public static void setPassword(String tk) {
        password.set(tk);
    }

    public static String setId(String tk) {
        id.set(tk);
        return tk;
    }

    public static void remove() {
        email.remove();
        password.remove();
        id.remove();
    }



}
