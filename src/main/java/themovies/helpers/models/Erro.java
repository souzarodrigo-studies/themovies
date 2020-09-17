package themovies.helpers.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Erro", description = "Informações sobre o erro que ocorreu.")
public class Erro {

    private String code;
    private String message;
    private String source;

    private String userHelp;
    private String developerMessage;
    private String moreInfo;

    private String sequential;

    public Erro() {
        this.code = "";
        this.message = "";
        this.source = "";
        this.sequential = "0";
    }

    public Erro(String mensagem, String source) {
        this.code = "";
        this.message = mensagem;
        this.source = source;
        this.sequential = "0";
    }

    public Erro(EnumMensagens msg, String source) {
        this(msg);
        this.source = source;
    }

    public Erro(EnumMensagens msg) {
        this.code = msg.getCodigo();
        this.message = msg.getMensagem();
        this.userHelp = msg.getUserHelp();
        this.moreInfo = msg.getMoreInfo();
        this.developerMessage = msg.getDeveloperMessage();
        this.source = "";
        this.sequential = "0";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserHelp() {
        return userHelp;
    }

    public void setUserHelp(String userHelp) {
        this.userHelp = userHelp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public void setSequential(String sequential) {
        this.sequential = sequential;
    }

    public String getSequential() {
        return sequential;
    }

    public static class Builder {

        private String message;
        private EnumMensagens enumMensagens;
        private String source;
        private int sequential;
        private String[] messageArgs = { "" };
        private String[] moreInfoArgs = { "" };
        private String[] userHelpArgs = { "" };
        private String[] developerMessageArgs = { "" };

        public Builder(EnumMensagens enumErro, String source) {
            this.enumMensagens = enumErro;
            this.source = source;
        }

        public Builder(String message, String source) {
            this.message = message;
            this.source = source;
        }

        public Builder withFormatedMessage(String... args) {
            if (args != null) {
                messageArgs = args;
            }
            return this;
        }

        public Builder withSequential(int sequential) {
            this.sequential = sequential;
            return this;
        }

        public Builder withFormatedMoreInfo(String... args) {
            if (args != null) {
                moreInfoArgs = args;
            }
            return this;
        }

        public Builder withFormatedUserHelp(String... args) {
            if (args != null) {
                userHelpArgs = args;
            }
            return this;
        }

        public Builder withFormatedDeveloperMessage(String... args) {
            if (args != null) {
                developerMessageArgs = args;
            }
            return this;
        }

        public Erro build() {

            Erro erro;
            if (message != null && message.trim().length() > 0) {
                erro = new Erro(this.message, this.source);
            } else {
                erro = new Erro(this.enumMensagens, this.source);
            }

            erro.setMessage(String.format(erro.getMessage(), messageArgs));
            erro.setDeveloperMessage(String.format(erro.getDeveloperMessage(), developerMessageArgs));
            erro.setUserHelp(String.format(erro.getUserHelp(), userHelpArgs));
            erro.setMoreInfo(String.format(erro.getMoreInfo(), moreInfoArgs));
            erro.setSequential(String.valueOf(this.sequential));

            return erro;
        }
    }
}
