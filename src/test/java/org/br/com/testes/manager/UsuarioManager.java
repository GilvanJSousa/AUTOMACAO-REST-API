package org.br.com.testes.manager;

public class UsuarioManager {

    private static ThreadLocal<String> emailUsuario = new ThreadLocal<>();

    private static ThreadLocal<String> senhaUsuario = new ThreadLocal<>();

    private static ThreadLocal<String> idUsuario = new ThreadLocal<>();

    public static String getEmailUsuario() {
        return emailUsuario.get();
    }

    public static void setEmailUsuario(String tk) {
        emailUsuario.set(tk);
    }

    public static String getSenhaUsuario() {
        return senhaUsuario.get();
    }

    public static void setSenhaUsuario(String tk) {
        senhaUsuario.set(tk);
    }

    public static String getIdUsuario() {
        return idUsuario.get();
    }

    public static void setIdUsuario(String tk) {
        idUsuario.set(tk);
    }

    public static void remove() {
        emailUsuario.remove();
        senhaUsuario.remove();
        idUsuario.remove();
    }


}
