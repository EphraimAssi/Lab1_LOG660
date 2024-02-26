import javax.persistence.*;
import entity.*;
import entity.utilisateur.model.Personne;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import entity.utilisateur.service.AuthentificationService;
import entity.test.test;

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

    public static void connectionUtilisateur(SessionFactory sessionFactory) {
        AuthentificationService authentificationService = new AuthentificationService(sessionFactory);
        String email = "RobertIHamon15@hotmail.com";
        String password = "AB9Ahj1Quoh";
        Personne utilisateur = authentificationService.connectionUtilisateur(email, password);
        if (utilisateur != null) {
            System.out.println("Utilisateur trouvé : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            System.out.println("Utilisateur non trouvé");
        }


    }

    public static void main(String[] args) throws Exception {
        HibernateConfig hibernateConfig = new HibernateConfig();
        SessionFactory sessionFactory = hibernateConfig.getSessionFactory();

//        test test1 = new test(sessionFactory);
//        test1.verrificationTests();


        connectionUtilisateur(sessionFactory);
        //showFirst10ExemplaireFilms(sessionFactory);
        //showFirst10Films(sessionFactory);
    }
}
