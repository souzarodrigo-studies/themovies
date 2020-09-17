package themovies.helpers.error;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import themovies.helpers.models.Erro;


@Schema(name = "Lista de Erros", description = "Lista com os Erros.")
public class ListaErro {

    private List<Erro> errors;

    public ListaErro() {
        this.errors = new ArrayList<>();
    }

    public ListaErro(List<Erro> errors) {
        this.errors = errors;
    }

    public ListaErro(Erro error) {
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public List<Erro> getErrors() {
        return errors;
    }

    public void setErrors(List<Erro> errors) {
        this.errors = errors;
    }
}
