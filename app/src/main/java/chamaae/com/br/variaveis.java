package chamaae.com.br;

/**
 * Classe variaveis do APP*
 */

public class variaveis {

    // METODOS API (ADICIONAR AQUI)
    public final String BASE_API = "http://192.168.0.101:5050/datasnap/json";
    public final String RETORNA_USUARIO = "/TUsuarios/RetornaUsuario/";
    public final String INSERE_USUARIO  = "/TUsuarios/InsereUsuario/";
    public final String ALTERA_USUARIO  = "/TUsuarios/AlteraUsuario/";
    public final String LOGIN_USUARIO_GOOGLE   = "/TUsuarios/LoginUsuarioGoogle/";
    public final String LOGIN_USUARIO   = "/TUsuarios/LoginUsuario/";
    public final String LOGIN_USUARIO_FACEBOOK = "/TUsuarios/LoginUsuarioFacebook/";

    public String getLOGIN_USUARIO_FACEBOOK() {
        return LOGIN_USUARIO_FACEBOOK;
    }


    public String getLOGIN_USUARIO_GOOGLE() {
        return LOGIN_USUARIO_GOOGLE;
    }


    public String getLOGIN_USUARIO() {
        return LOGIN_USUARIO;
    }


    public String getBASE_API() {
        return BASE_API;
    }

    public String getRETORNA_USUARIO() {
        return RETORNA_USUARIO;
    }

    public String getINSERE_USUARIO() {
        return INSERE_USUARIO;
    }

    public String getALTERA_USUARIO() {
        return ALTERA_USUARIO;
    }


    // VARIAVEIS INSERE_USUARIO
    public static String ID_USER  = null;
    public static String NOME     = null;
    public static String FANTASIA = null;
    public static String EMAIL = null;
    public static String PWD      = null;
    public static String TEL1     = null;
    public static String TEL2     = null;
    public static String TIPO_LOGIN = null;
    public static String TOKEN    =  null;

    public static String getIdUser() {
        return ID_USER;
    }

    public static void setIdUser(String idUser) {
        ID_USER = idUser;
    }

    public static void setNOME(String NOME) {
        variaveis.NOME = NOME;
    }

    public static void setFANTASIA(String FANTASIA) {
        variaveis.FANTASIA = FANTASIA;
    }

    public static void setEMAIL(String EMAIL) {
        variaveis.EMAIL = EMAIL;
    }

    public static void setPWD(String PWD) {
        variaveis.PWD = PWD;
    }

    public static void setTEL1(String TEL1) {
        variaveis.TEL1 = TEL1;
    }

    public static void setTEL2(String TEL2) {
        variaveis.TEL2 = TEL2;
    }

    public static String getTipoLogin() {
        return TIPO_LOGIN;
    }

    public static void setTipoLogin(String tipoLogin) {
        TIPO_LOGIN = tipoLogin;
    }

    public static void setTOKEN(String TOKEN) {
        variaveis.TOKEN = TOKEN;
    }

    public String getID_USER() {
        return ID_USER;
    }

    public String getNOME() {
        return NOME;
    }

    public String getFANTASIA() {
        return FANTASIA;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPWD() {
        return PWD;
    }

    public String getTEL1() {
        return TEL1;
    }

    public String getTEL2() {
        return TEL2;
    }

    public String getTIPO_LOGIN() {
        return TIPO_LOGIN;
    }

    public String getTOKEN() {
        return TOKEN;
    }


}
