package Service;

import entity.PersonneDossier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
public class AuthentificationService {
    private SessionFactory sessionFactory;
    private Session session;
    public AuthentificationService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = null;
    }

    public void openSession() {
        session = sessionFactory.openSession();
    }
    public PersonneDossier connectionUtilisateur(String courriel, String password) {
        openSession();
        System.out.println("Courriel : " + courriel + " Mot de passe : " + password);
        try {

            session.beginTransaction();
            Query<PersonneDossier> query = session.createQuery("select p from PersonneDossier p where courriel = :courriel", PersonneDossier.class);
            query.setParameter("courriel", courriel);
            PersonneDossier utilisateur = query.uniqueResult();

            session.getTransaction().commit();

            System.out.println("Utilisateur mot de passe :" +  utilisateur.getMotdepasse());

            if (utilisateur != null && utilisateur.getMotdepasse().equals(password)) {
                return utilisateur; // User found and password matches
            }
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return null; // Utilisateur n'a pas été trouvé ou le mot de passe ne correspond pas.
    }

}