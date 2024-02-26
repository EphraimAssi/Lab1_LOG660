package entity.test.manytomany;

import entity.utilisateur.model.Personne;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import entity.Film;
import entity.Genre;

import java.util.HashSet;
import java.util.Set;

public class GenreFilm {

    private SessionFactory sessionFactory;
    private Session session;
    public GenreFilm(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = null;
    }

    public void openSession() {
        session = sessionFactory.openSession();
    }
    public void verificationGenreFilm() {

        System.out.println("Test de vérification de genre film");
        openSession();

        Film film = new Film();
        film.setTitre("The Dark Knight");
        Film film2 = new Film();
        film2.setTitre("The Dark Knight Rises");
        Set<Film> films = new HashSet<Film>();
        films.add(film);
        films.add(film2);


       Genre genre = new Genre();
       genre.setNomgenre("Action");
       Genre genre2 = new Genre();
       genre2.setNomgenre("Drame");

       Set<Genre> genres = new HashSet<Genre>();
       genres.add(genre);

       genre.setGenres(films);
       genre2.setGenres(films);


    }

}
