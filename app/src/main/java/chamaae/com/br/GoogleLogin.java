package chamaae.com.br;

import android.net.Uri;

import java.net.URI;

/**
 * CLASSE PARA PASSAR INFOS DO LOGIN PELO GOOGLE (VAI AJUDAR PARA K7 NA HORA D EPASSAR PARA O BANCO)
 */



public class GoogleLogin {
    public static String Nome;
    public static String Email;
    public static String Token;
    public static Uri     Foto;

    public static Uri getFoto() {
        return Foto;
    }

    public static void setFoto(Uri foto) {
        Foto = foto;
    }

    public static String getToken() {
        return Token;
    }

    public static void setToken(String token) {
        Token = token;
    }

    public static String getNome() {
        return Nome;
    }

    public static void setNome(String nome) {
        Nome = nome;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }
}
