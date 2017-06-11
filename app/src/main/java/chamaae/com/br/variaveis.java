package chamaae.com.br;

/**
 * Classe variaveis do APP*
 */

public class variaveis {

    // METODOS API (ADICIONAR AQUI)
    public final String BASE_API = "http://192.168.0.105:5050/datasnap/json";
    public final String RETORNA_USUARIO = "/TUsuarios/RetornaUsuario/";
    public final String INSERE_USUARIO  = "/TUsuarios/InsereUsuario/";
    public final String ALTERA_USUARIO  = "/TUsuarios/AlteraUsuario/";

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

    public final String ID_USER         = "";
    // VARIAVEIS INSERE_USUARIO
    public  String getID_USER() {
        return ID_USER;
    }public String getNOME() {
        return NOME;
    }public String getFANTASIA() {
        return FANTASIA;
    }public String getEMAIL() {
        return EMAIL;
    }public String getPWD() {
        return PWD;
    }public String getTEL1() {
        return TEL1;
    }public String getTEL2() {
        return TEL2;
    }public String getTIPO_LOGIN() {
        return TIPO_LOGIN;
    }public String getTOKEN() {
        return TOKEN;
    }public final String NOME     = "";
    public final String FANTASIA = "";
    public final String EMAIL    = "";
    public final String PWD      = "";
    public final String TEL1     = "";
    public final String TEL2     = "";
    public final String TIPO_LOGIN = "";
    public final String TOKEN    =  "";




}
