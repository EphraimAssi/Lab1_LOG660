import javax.persistence.*;
import entity.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Genre nouveaugenre = new Genre();
            nouveaugenre.setNomgenre("test");
            entityManager.persist(nouveaugenre);

            entityTransaction.commit();


        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();

            }
            entityManager.close();
            entityManagerFactory.close();
        }

    }
}
