package entity.test;
import entity.test.manytomany.GenreFilm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class test {
    private SessionFactory sessionFactory;
    private Session session;
    public test(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = null;
    }
    public void verrificationTests() {
        System.out.println("Test de vérification de genre film");
        verificationGenreFilmTest();

    }

    public void verificationGenreFilmTest() {
        GenreFilm genreFilm = new GenreFilm(sessionFactory);
        genreFilm.verificationGenreFilm();
    }





}

