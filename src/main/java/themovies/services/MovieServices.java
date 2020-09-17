package themovies.services;

import themovies.helpers.exception.ResourceAPIException;
import themovies.helpers.log.Log;
import themovies.helpers.metric.Metrica;
import themovies.models.MovieModel;
import themovies.repositories.MovieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class MovieServices {

    @Inject
    MovieRepository repository;

    @Log
    @Metrica
    @Transactional
    public List<MovieModel> listAll() {

        return repository.findAll();
    }

    @Log
    @Metrica
    @Transactional
    public MovieModel findById(Long id) throws ResourceAPIException {

        return repository.findById(id).orElseThrow(() -> new ResourceAPIException(" Movie not Found "));

    }

    @Log
    @Metrica
    @Transactional
    public boolean validByName(MovieModel entity) {

        return this.repository.findByNome(entity).isPresent();

    }

    @Log
    @Metrica
    @Transactional
    public void save(MovieModel entity) {

        this.repository.save(entity);

    }

    @Log
    @Metrica
    @Transactional
    public MovieModel update(MovieModel movie) throws ResourceAPIException {

        MovieModel entity = this.repository.findById(movie.getId())
                .orElseThrow(() -> new ResourceAPIException("Movie not Found"));

        if (Objects.nonNull(movie.getDirector())) {
            entity.setDirector(movie.getDirector());
        }
        if (Objects.nonNull(movie.getTitle())) {
            entity.setTitle(movie.getTitle());
        }
        if (Objects.nonNull(movie.getReleaseDate())) {
            entity.setReleaseDate(movie.getReleaseDate());
        }

        this.repository.update(entity);

        return entity;
    }

    @Log
    @Metrica
    @Transactional
    public void delete(Long id) throws ResourceAPIException {

        MovieModel entity = this.repository.findById(id).orElseThrow(() -> new ResourceAPIException("Movie not Found"));

        this.repository.delete(entity);

    }
}
