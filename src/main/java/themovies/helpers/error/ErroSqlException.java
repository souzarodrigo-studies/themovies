package themovies.helpers.error;

import org.hibernate.JDBCException;

import themovies.helpers.models.EnumMensagens;
import themovies.helpers.models.Erro;


public class ErroSqlException extends ErroNegocialException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String sqlCode;
    private final String motivo;
    private final String query;

    public ErroSqlException(String sqlCode, String motivo, String query) {
        super(EnumMensagens.ERRO_SQL.getMensagem());
        this.sqlCode = sqlCode;
        this.motivo = motivo;
        this.query = query;
    }

    public ErroSqlException(String sqlCode, String motivo, String query, Throwable cause) {
        super(EnumMensagens.ERRO_SQL.getMensagem(), cause);
        this.sqlCode = sqlCode;
        this.motivo = motivo;
        this.query = query;
    }

    public ErroSqlException(Exception e) {
        super(EnumMensagens.ERRO_SQL.getMensagem(), e.getCause());
        Integer code = -1;
        String sqlQuery = "";
        String motivo;

        if (e.getCause() != null && e.getCause() instanceof JDBCException) {
            JDBCException jdbcException = (JDBCException) e.getCause();

            if (jdbcException.getSQLException() != null) {
                code = jdbcException.getSQLException().getErrorCode();
            }

            if (jdbcException.getSQL() != null && jdbcException.getSQL().length() > 0) {
                sqlQuery = jdbcException.getSQL();
            }

            if (jdbcException.getCause() != null) {
                motivo = jdbcException.getCause().getMessage();
            } else {
                motivo = jdbcException.getMessage();
            }
        } else {
            motivo = e.getMessage();
        }
        this.sqlCode = code.toString();
        this.motivo = motivo;
        this.query = sqlQuery;
    }

    private String getSourceFromStackTraceSqlTrace() {
        int index = 0;
        if (this.getStackTrace().length > 1) {
            index = 1;
        }
        StackTraceElement stackTrace = this.getStackTrace()[index];
        return stackTrace.getClassName() + LINHA + stackTrace.getLineNumber() + PONTO;
    }

    @Override
    public Erro getErro() {
        return new Erro.Builder(EnumMensagens.ERRO_SQL, getSourceFromStackTraceSqlTrace())
                .withFormatedMoreInfo(sqlCode, motivo).withFormatedDeveloperMessage(sqlCode, query).build();
    }
}
