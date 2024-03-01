import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void showFirst10ExemplaireFilms(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exemplairefilm> result = session.createQuery("select e from Exemplairefilm e").setMaxResults(10).list();
        for (Exemplairefilm exemplaire : result) {
            System.out.println("Exemplaire :" + exemplaire.getCodeexemplaire() + " : " + exemplaire.getFilm().getTitre());
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Films(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Film> result = session.createQuery("select e from Film e").setMaxResults(10).list();
        for (Film film : result) {
            System.out.println("Film :" + film.getExemplairesFilm().stream().count() + " : " + film.getTitre());
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Clients(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Client> result = session.createQuery("select e from Client e").setMaxResults(10).list();
        for (Client client : result) {
            System.out.println("Client :" + client.getPersonne().getNom());
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Acteur(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Acteur> result = session.createQuery("select e from Acteur e").setMaxResults(10).list();
        for (Acteur i : result) {
            System.out.println("Acteur :" + i.getPersonne().getNomcomplet());
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Realisateur(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Realisateur> result = session.createQuery("select e from Realisateur e").setMaxResults(10).list();
        for (Realisateur i : result) {
            System.out.println("Realisateur :" + i.getPersonne().getNomcomplet());
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10PersonneFilm(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<PersonneFilm> result = session.createQuery("select e from PersonneFilm e").setMaxResults(10).list();
        for (PersonneFilm i : result) {
            System.out.println("Personne :" + i.getNomcomplet());
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        //HibernateConfig hibernateConfig = new HibernateConfig();
        //SessionFactory sessionFactory = hibernateConfig.getSessionFactory();
        //showFirst10ExemplaireFilms(sessionFactory);
        //showFirst10Films(sessionFactory);
        //showFirst10Clients(sessionFactory);
        //showFirst10Acteur(sessionFactory);
        //showFirst10Realisateur(sessionFactory);
        //showFirst10PersonneFilm(sessionFactory);


        // Test facade connection utilisateur
        DataFacade dataFacade = new DataFacade();

        // CAS 2
        dataFacade.connectionUtilisateur("AutumnBMedley94@hotmail.com", "aneito4Lee");

        // CAS 3
        dataFacade.locationFilmPersonneDossier();

        /*
        List<Film> result = dataFacade.consultationFilms("The", "2000", "2022", "UK", "English", "Action", "Brett", "Jackman");
        for (Film i : result) {
            dataFacade.loadAllFilm(i);
        }*/

        /*
        // Search doit fonctionner même si les lettre ne sont pas dans le bon ordre
        List<Film> result2 = dataFacade.consultationFilms("mnex", "2000", "2022", "KU", "hengl", "Action", "Brett", "Jackman");
        for (Film i : result2) {
            dataFacade.loadAllFilm(i);
        }
        // Devrait retourner tout les films ayant USA comme pays
        List<Film> resultPays = dataFacade.consultationFilms(null, null, null, "aus", null, null, null, null);
        // Devrait retourner tout les films ayant Action comme genre
        List<Film> resultGenre = dataFacade.consultationFilms(null, null, null, null, null, "ticno", null, null);
        for (Film i : resultGenre) {
            dataFacade.loadGenres(i);
        }
        // Devrait retourner tous les films ayant B r e t t faisant partie du nom de réalisateur
        List<Film> resultRealisateur = dataFacade.consultationFilms(null, null, null, null, null, null, "bttre", null);
        for (Film i : resultRealisateur) {
            dataFacade.loadRealisateurs(i);
        }
        // Devrait retourner tous les films ayant J A C K M A N faisant partie du nom de l'acteur
        List<Film> resultActeur = dataFacade.consultationFilms(null, null, null, null, null, null, null, "najckam");
        for (Film i : resultActeur) {
            dataFacade.loadRoles(i);
        }*/

        // Exemple avec plusieurs critères
        // CAS 4
        List<Film> resultCriteresMultiple = dataFacade.consultationFilms("The", null, null, null, null, "action,adventure", null, null);
        for (Film i : resultCriteresMultiple) {
            dataFacade.loadGenres(i);
        }
        System.out.println("Debug");
    }
}
