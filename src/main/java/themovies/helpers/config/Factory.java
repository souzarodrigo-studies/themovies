package themovies.helpers.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {

    private Factory() {
    }

    private static EntityManagerFactory fac = Persistence.createEntityManagerFactory("prod");

    public static EntityManager getEntityManager() {
        return fac.createEntityManager();
    }

}
