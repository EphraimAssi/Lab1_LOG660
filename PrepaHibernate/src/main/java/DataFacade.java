import Service.AuthentificationService;
import entity.PersonneDossier;
import org.hibernate.SessionFactory;

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
}
