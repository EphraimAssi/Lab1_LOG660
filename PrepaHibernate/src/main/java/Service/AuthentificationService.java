package Service;

import entity.Client;
import entity.PersonneDossier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigInteger;

public class AuthentificationService {
    private SessionFactory sessionFactory;

    public AuthentificationService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PersonneDossier connectionUtilisateur(String courriel, String password) {

        System.out.println("Courriel : " + courriel + " Mot de passe : " + password);
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<PersonneDossier> query = session.createQuery("select p from PersonneDossier p where courriel = :courriel", PersonneDossier.class);
            query.setParameter("courriel", courriel);
            PersonneDossier utilisateur = query.uniqueResult();
            session.getTransaction().commit();
            if (utilisateur != null && utilisateur.getMotdepasse().equals(password)) {
                session.close();

                return utilisateur; // User found and password matches
            } else {
                return null; // User not found or password does not match
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Utilisateur n'a pas été trouvé ou le mot de passe ne correspond pas.
    }


}