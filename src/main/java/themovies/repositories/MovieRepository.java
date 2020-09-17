package themovies.repositories;

import java.util.List;
import java.util.Optional;

import themovies.models.MovieModel;

public interface MovieRepository {

    List<MovieModel> findAll();

    Optional<MovieModel> findById(Long id);

    Optional<MovieModel> findByNome(MovieModel entity);

    void save(MovieModel entity);

    void update(MovieModel entity);

    void delete(MovieModel entity);

}