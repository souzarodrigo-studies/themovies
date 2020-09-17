package themovies.services;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import themovies.helpers.exception.ResourceAPIException;
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

    @Transactional
    @Timed(name = "ProcessingListAll", description = "Time needed to process the List of Movies", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter of List All", absolute = true, description = "Number of times listAll of system method is requested")
    public List<MovieModel> listAll() {

        return repository.findAll();
    }

    @Transactional
    @Timed(name = "ProcessingfindByID", description = "Time needed to search movie by id", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter Save Movie", absolute = true, description = "Number of times find movie by id")
    public MovieModel findById(Long id) throws ResourceAPIException {

        return repository.findById(id).orElseThrow(() -> new ResourceAPIException(" Movie not Found "));

    }

    @Transactional
    @Timed(name = "ProcessingfindByNome", description = "Time needed to search movie by name", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter Findbyname method", absolute = true, description = "Number of times find movie by name")
    public boolean validByName(MovieModel entity) {

        return this.repository.findByNome(entity).isPresent();

    }

    @Transactional
    @Timed(name = "ProcessingSaveMovie", description = "Time needed to save a new movie", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter Save Movie", absolute = true, description = "Number of times Save movie")
    public void save(MovieModel entity) {

        this.repository.save(entity);

    }

    @Transactional
    @Timed(name = "ProcessingUpdateMovie", description = "Time needed to update a movie", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter to update Movie", absolute = true, description = "Number of times update movie")
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

    @Transactional
    @Timed(name = "ProcessingDeleteMovie", description = "Time needed to delete a new movie", absolute = true, unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter delete Movie", absolute = true, description = "Number of times delete a movie")
    public void delete(Long id) throws ResourceAPIException {

        MovieModel entity = this.repository.findById(id).orElseThrow(() -> new ResourceAPIException("Movie not Found"));

        this.repository.delete(entity);

    }
}
