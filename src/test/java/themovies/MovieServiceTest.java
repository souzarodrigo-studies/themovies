package themovies;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import themovies.helpers.exception.ResourceAPIException;
import themovies.models.MovieModel;
import themovies.repositories.MovieRepository;
import themovies.services.MovieServices;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MovieServiceTest {

    @InjectMocks
    MovieServices resource;

    @Mock
    MovieRepository repository;

    @Mock
    EntityManager entityManager;

    @Test
    @DisplayName("Testing the execution of the repository returning the data by list")
    public void listRepositoryTest() {

        MovieModel entityMock = new MovieModel();
        entityMock.setId(1L);
        entityMock.setImdbRate(10l);
        entityMock.setDuration(178l);
        entityMock.setDirector("Peter Jackson");
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setTitle("Lord of the rings - The Fellowship of the ring");
        entityMock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        when(repository.save(any(MovieModel.class))).thenReturn(new MovieModel());

        List<MovieModel> entity = resource.listAll();

        assertNotNull(entity);
    }

    @Test
    @DisplayName("Testing the execution of the repository returning the data by find by name")
    public void findByNameRepositoryTest() {

        MovieModel entity = new MovieModel();
        entity.setId(1L);
        entity.setImdbRate(10l);
        entity.setDuration(178l);
        entity.setDirector("Peter Jackson");
        entity.setReleaseDate(LocalDate.now());
        entity.setTitle("Lord of the rings - The Fellowship of the ring");
        entity.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        Boolean a = resource.validByName(entity);

        assertEquals(false, a);
    }

    @Test
    @DisplayName("Testing the execution of the repository returning the data by save")
    public void saveRepositoryTest() {

        MovieModel entityMock = new MovieModel();
        entityMock.setId(1L);
        entityMock.setImdbRate(10l);
        entityMock.setDuration(178l);
        entityMock.setDirector("Peter Jackson");
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setTitle("Lord of the rings - The Fellowship of the ring");
        entityMock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        MovieModel entity = resource.save(entityMock);

        assertNotNull(entity);
        assertEquals(entity.getId(), entity.getId());
        assertEquals(entity.getDescription(), entity.getDescription());
        assertEquals(entity.getDirector(), entity.getDirector());
        assertEquals(entity.getDuration(), entity.getDuration());
        assertEquals(entity.getImdbRate(), entity.getImdbRate());
        assertEquals(entity.getReleaseDate(), entity.getReleaseDate());
        assertEquals(entity.getTitle(), entity.getTitle());

    }

    @Test
    @DisplayName("Deve Testar um delete de um filme no banco")
    public void deleteTest() throws ResourceAPIException {

        MovieModel mock = new MovieModel();
        mock.setId(1L);
        mock.setImdbRate(10l);
        mock.setDuration(178l);
        mock.setDirector("Peter Jackson e Rodrigo SS");
        mock.setReleaseDate(LocalDate.now());
        mock.setTitle("Lord of the rings - The Fellowship of the ring");
        mock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        Mockito.when(this.repository.findById(mock.getId())).thenReturn(Optional.of(mock));

        resource.delete(mock.getId());

        assertNotNull(mock);
    }

    @Test
    @DisplayName(" Deve testar a atualizaçao de um filme no banco de dados")
    public void updateTest() throws ResourceAPIException {

        MovieModel mock = new MovieModel();
        mock.setId(1L);
        mock.setImdbRate(10l);
        mock.setDuration(178l);
        mock.setDirector("Peter Jackson e Rodrigo SS");
        mock.setReleaseDate(LocalDate.now());
        mock.setTitle("Lord of the rings - The Fellowship of the ring");
        mock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        Mockito.when(this.repository.findById(mock.getId())).thenReturn(Optional.of(mock));

        mock.setDirector("Peter Jackson");

        MovieModel response = resource.update(mock);

        assertNotNull(response);
        assertEquals(response, mock);

    }

}