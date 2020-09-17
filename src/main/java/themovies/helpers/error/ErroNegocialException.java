package themovies.helpers.error;

import themovies.helpers.models.EnumMensagens;
import themovies.helpers.models.Erro;

public class ErroNegocialException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static final String LINHA = " - linha: ";
    static final String PONTO = " .";
    private final String[] args;
    private final EnumMensagens enumMensagem;

    protected ErroNegocialException(String message) {
        super(message);
        this.args = new String[] { "" };
        this.enumMensagem = null;
    }

    protected ErroNegocialException(String message, Throwable cause) {
        super(message, cause);
        this.args = new String[] { "" };
        this.enumMensagem = null;
    }

    public ErroNegocialException(EnumMensagens enumMensagem) {
        super(enumMensagem.getMensagem());
        this.enumMensagem = enumMensagem;
        args = new String[] { "" };
    }

    public ErroNegocialException(EnumMensagens enumMensagem, String... args) {
        super(enumMensagem.getMensagem());
        this.args = args;
        this.enumMensagem = enumMensagem;
    }

    public ErroNegocialException(EnumMensagens enumMensagem, Throwable cause) {
        super(enumMensagem.getMensagem(), cause);
        this.enumMensagem = enumMensagem;
        args = new String[] { "" };
    }

    public ErroNegocialException(EnumMensagens enumMensagem, Throwable cause, String... args) {
        super(enumMensagem.getMensagem(), cause);
        this.enumMensagem = enumMensagem;
        this.args = args;
    }

    private String getSourceFromStackTrace() {
        StackTraceElement stackTrace = this.getStackTrace()[0];
        return stackTrace.getClassName() + LINHA + stackTrace.getLineNumber() + PONTO;
    }

    public Erro getErro() {
        if (this.enumMensagem != null) {
            return new Erro.Builder(this.enumMensagem, getSourceFromStackTrace()).withFormatedMessage(this.args)
                    .build();
        } else {
            return new Erro.Builder(this.getMessage(), getSourceFromStackTrace()).withFormatedMessage(this.args)
                    .build();
        }
    }
}
