package themovies.helpers.models;

public enum EnumMensagens {
    ERRO_APLICACAO("000", "Erro ao executar", "%s", "%s", "%s"),
    ERRO_SQL("999", "Erro no sistema", "Tente novamente mais tarde", "SQL CODE: %s , QUERY SQL: %s",
            "SQL CODE: %s , MOTIVO: %s"),
    INFORME_DATA_NASCIMENTO("001", "Informe a data de nascimento", "", "", ""),
    ERRO_EXCLUSAO_USUARIO("002", "Não foi possivel excluir o usuario do identificador %s.", "", "", ""),
    ERRO_INCLUSAO_USUARIO("003", "O usuario deve ter mais que %s anos.", "", "", ""),
    TEXTO_ENTRADA_NAO_INFORMADO("004", "Texto de entrada nao foi informado", "", "", "");

    String mensagem;
    String codigo;

    String userHelp;
    String developerMessage;
    String moreInfo;

    EnumMensagens(String codigo, String mensagem, String userHelp, String developerMessage, String moreInfo) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.userHelp = userHelp;
        this.developerMessage = developerMessage;
        this.moreInfo = moreInfo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getUserHelp() {
        return userHelp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}
