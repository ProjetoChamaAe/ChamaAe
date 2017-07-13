package chamaae.com.br;

/**
 * Classe variaveis do APP*
 */

public class variaveis {

    // METODOS API (ADICIONAR AQUI)
    public final String BASE_API = "http://192.168.0.103:5050/datasnap/json";
    public final String RETORNA_USUARIO  = "/TUsuarios/RetornaUsuario/";
    public final String RETORNA_ENDERECO = "/TUsuarios/RetornaEndereco/";
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


    public static String ID_USER  = null;
    public static String NOME     = null;
    public static String FANTASIA = null;
    public static String EMAIL = null;
    public static String PWD      = null;
    public static String TEL1     = null;
    public static String TEL2     = null;
    public static String TIPO_LOGIN = null;
    public static String TOKEN     =  null;
    public static String PRESTADOR = null;

    public static String getPRESTADOR() {
        return PRESTADOR;
    }

    public static void setPRESTADOR(String PRESTADOR) {
        variaveis.PRESTADOR = PRESTADOR;
    }

    public static String getCPFCNPJ() {
        return CPFCNPJ;
    }

    public static void setCPFCNPJ(String CPFCNPJ) {
        variaveis.CPFCNPJ = CPFCNPJ;
    }

    public static String CPFCNPJ  = null;

    public String getRETORNA_ENDERECO() {
        return RETORNA_ENDERECO;
    }

    public static String getIdEndereco() {
        return ID_ENDERECO;
    }

    public static void setIdEndereco(String idEndereco) {
        ID_ENDERECO = idEndereco;
    }

    public static String getRUA() {
        return RUA;
    }

    public static void setRUA(String RUA) {
        variaveis.RUA = RUA;
    }

    public static String getCEP() {
        return CEP;
    }

    public static void setCEP(String CEP) {
        variaveis.CEP = CEP;
    }

    public static String getLOGRAD() {
        return LOGRAD;
    }

    public static void setLOGRAD(String LOGRAD) {
        variaveis.LOGRAD = LOGRAD;
    }

    public static String getENDERECO() {
        return ENDERECO;
    }

    public static void setENDERECO(String ENDERECO) {
        variaveis.ENDERECO = ENDERECO;
    }

    public static String getNUMERO() {
        return NUMERO;
    }

    public static void setNUMERO(String NUMERO) {
        variaveis.NUMERO = NUMERO;
    }

    public static String getCOMPLEMETO() {
        return COMPLEMETO;
    }

    public static void setCOMPLEMETO(String COMPLEMETO) {
        variaveis.COMPLEMETO = COMPLEMETO;
    }

    public static String getBAIRRO() {
        return BAIRRO;
    }

    public static void setBAIRRO(String BAIRRO) {
        variaveis.BAIRRO = BAIRRO;
    }

    public static String getCIDADE() {
        return CIDADE;
    }

    public static void setCIDADE(String CIDADE) {
        variaveis.CIDADE = CIDADE;
    }

    public static String getUF() {
        return UF;
    }

    public static void setUF(String UF) {
        variaveis.UF = UF;
    }

    public static String getPAIS() {
        return PAIS;
    }

    public static void setPAIS(String PAIS) {
        variaveis.PAIS = PAIS;
    }

    // VARIAVEIS ENDEREÇO
    public static String ID_ENDERECO = null;
    public static String RUA         = null;
    public static String CEP         = null;
    public static String LOGRAD      = null;
    public static String ENDERECO    = null;
    public static String NUMERO      = null;
    public static String COMPLEMETO  = null;
    public static String BAIRRO      = null;
    public static String CIDADE      = null;
    public static String UF          = null;
    public static String PAIS        = null;


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
