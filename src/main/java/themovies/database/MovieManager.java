package themovies.database;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import themovies.helpers.config.Factory;
import themovies.models.MovieModel;
import themovies.repositories.MovieRepository;

public class MovieManager implements MovieRepository {

    private EntityManager em;

    @PostConstruct
    public void init() {

        em = Factory.getEntityManager();

    }

    @Override
    public List<MovieModel> findAll() {

        return em.createQuery(" SELECT p FROM MovieModel p", MovieModel.class).getResultList();

    }

    @Override
    public Optional<MovieModel> findById(Long id) {

        MovieModel entity = em.find(MovieModel.class, id);

        return Objects.nonNull(entity) ? Optional.of(entity) : Optional.empty();

    }

    @Override
    public Optional<MovieModel> findByNome(MovieModel entity) {

        try {

            MovieModel consulta = em.createQuery("select p from MovieModel p where p.title = :title ", MovieModel.class)
                    .setParameter("title", entity.getTitle()).getSingleResult();

            return Optional.of(consulta);

        } catch (NoResultException e) {

            return Optional.empty();

        }

    }

    @Override
    public void save(MovieModel entity) {

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

    }

    @Override
    public void update(MovieModel entity) {

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

    }

    @Override
    public void delete(MovieModel entity) {

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

    }

}
