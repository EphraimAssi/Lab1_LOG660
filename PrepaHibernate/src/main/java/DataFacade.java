import Service.AuthentificationService;
import Service.LocationFilmService;
import entity.Client;
import entity.Film;
import entity.PersonneDossier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigInteger;

public class DataFacade {
    private final SessionFactory sessionFactory;

    public DataFacade() {
        HibernateConfig hibernateConfig = new HibernateConfig();
        this.sessionFactory = hibernateConfig.getSessionFactory();
    }

    public void connectionUtilisateur(String email, String password) {
        AuthentificationService authentificationService = new AuthentificationService(sessionFactory);
        PersonneDossier utilisateur = authentificationService.connectionUtilisateur(email, password);
        if (utilisateur != null) {
            System.out.println("Utilisateur trouvé : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            System.out.println("Utilisateur non trouvé");
        }
    }

    public void locationFilmPersonneDossier() {
        Client client = retourneClient();
        Film film = retourneFilm();
        LocationFilmService locationFilm = new LocationFilmService(sessionFactory);
        locationFilm.locationFilmPersonneDossier(client, film);
    }

    public Client retourneClient() {
        Session session = sessionFactory.openSession();
        BigInteger idclient = BigInteger.valueOf(2372494);
        session.beginTransaction();
        Query<Client> query = session.createQuery("select e from Client e where idpersonne = :idclient", Client.class);
        query.setParameter("idclient", idclient);
        Client client = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return client;
    }
    public Film retourneFilm() {
        Session session = sessionFactory.openSession();
        //id du film
        BigInteger idfilm = BigInteger.valueOf(77766);
        session.beginTransaction();
        Query<Film> queryFilm = session.createQuery("select f from Film f where idfilm = :idfilm", Film.class);
        queryFilm.setParameter("idfilm", idfilm);
        Film film = queryFilm.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return film;
    }
}
