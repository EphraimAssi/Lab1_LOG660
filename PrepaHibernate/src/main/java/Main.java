import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void showFirst10ExemplaireFilms(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exemplairefilm> result = session.createQuery("select e from Exemplairefilm e").setMaxResults(10).list();
        for ( Exemplairefilm exemplaire : result) {
            System.out.println( "Exemplaire :" + exemplaire.getCodeexemplaire() + " : " + exemplaire.getFilm().getTitre() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Films(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Film> result = session.createQuery("select e from Film e").setMaxResults(10).list();
        for ( Film film : result) {
            System.out.println( "Film :" + film.getExemplairesFilm().stream().count() + " : " + film.getTitre() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Clients(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Client> result = session.createQuery("select e from Client e").setMaxResults(10).list();
        for ( Client client : result) {
            System.out.println( "Client :" + client.getPersonne().getNom() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Acteur(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Acteur> result = session.createQuery("select e from Acteur e").setMaxResults(10).list();
        for ( Acteur i : result) {
            System.out.println( "Acteur :" + i.getPersonne().getNomcomplet() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10Realisateur(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Realisateur> result = session.createQuery("select e from Realisateur e").setMaxResults(10).list();
        for ( Realisateur i : result) {
            System.out.println( "Realisateur :" + i.getPersonne().getNomcomplet() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void showFirst10PersonneFilm(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<PersonneFilm> result = session.createQuery("select e from PersonneFilm e").setMaxResults(10).list();
        for ( PersonneFilm i : result) {
            System.out.println( "Personne :" + i.getNomcomplet() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateConfig hibernateConfig = new HibernateConfig();

        SessionFactory sessionFactory = hibernateConfig.getSessionFactory();

        //showFirst10ExemplaireFilms(sessionFactory);
        //showFirst10Films(sessionFactory);
        //showFirst10Clients(sessionFactory);
        //showFirst10Acteur(sessionFactory);
        //showFirst10Realisateur(sessionFactory);
        //showFirst10PersonneFilm(sessionFactory);


        // Test facade connection utilisateur
       DataFacade dataFacade = new DataFacade();
       PersonneDossier personneDossier = dataFacade.connectionUtilisateur("AaronAColquitt98@hotmail.com", "lim3Le7Ji");
       if (personneDossier != null) {
           BigInteger idpersonne = personneDossier.getIdpersonne();
           dataFacade.locationFilmPersonneDossier(idpersonne);
       } else {
           System.out.println("Utilisateur non trouvé");
       }



    }
}
