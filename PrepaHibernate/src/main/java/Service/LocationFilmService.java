package Service;

import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.List;

public class LocationFilmService {

    private SessionFactory sessionFactory;
    private Session session;
    public LocationFilmService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = null;
    }

    public void openSession() {
        session = sessionFactory.openSession();
    }
    public void locationFilmPersonneDossier(Client client, Film film) {
        openSession();

        //System.out.println("nomFilm : " + film.getTitre());
        try {
            // Initialise le nombre de film disponible pour le client
            int nombreFilmDispoClient = 0;
            // Recupere le forfait du client
            String forfait = client.getAbonnement().getForfait().getCodeforfait();
            // System.out.println("forfait : " + forfait);
            switch (forfait) {
                case "D":
                    nombreFilmDispoClient = 1;
                    break;
                case "I":
                    nombreFilmDispoClient = 5;
                    break;
                case "A":
                    nombreFilmDispoClient = 10;
                    break;
                default:
                    nombreFilmDispoClient = 0;
                    break;
            }

            BigInteger idabonnementClient =  client.getAbonnement().getIdabonnement();
            //System.out.println("idabonnementClient : " + idabonnementClient);

            // Prepare la requete pour obtenir les locations du client
            Query<Location> queryFilms = session.createQuery("select l from Location l where l.abonnement.idabonnement = :idabonnementClient", Location.class);
            // Initialise les parametres de la requete
            queryFilms.setParameter("idabonnementClient", idabonnementClient);
            // Lance la requete
            List<Location> locations = queryFilms.getResultList();
            // Récupere le nombre de films en location du client
            int nombreEnLocation = locations.size();
            // Calcule le nombre de films actuel disponibles pour le client
            nombreFilmDispoClient -= nombreEnLocation;


            if (nombreFilmDispoClient <= 0) {
                System.out.println("Le client a atteint le nombre maximum de location");
                return;
            }

            CriteriaBuilder cb = session.getCriteriaBuilder();
            // Main query for ExemplaireFilm
            CriteriaQuery<Exemplairefilm> mainQuery = cb.createQuery(Exemplairefilm.class);
            Root<Exemplairefilm> exemplaireFilmRoot = mainQuery.from(Exemplairefilm.class);
            // Subquery for Location
            Subquery<Location> subQuery = mainQuery.subquery(Location.class);
            Root<Location> locationRoot = subQuery.from(Location.class);
            // Correlation entre les deux requetes
            subQuery.select(locationRoot)
                    // Condition de correlation . On s'assure que pour un exemplaire celui-ci n'est pas en location
                    // ou bien que le statut de la location est différent de "retourner".
                    .where(cb.equal( locationRoot.get("film"), exemplaireFilmRoot.get("codeexemplaire")),
                            cb.notEqual(locationRoot.get("statut"), "retourner"));
            // Ajoute la condition de correlation à la requete principale
            mainQuery.select(exemplaireFilmRoot)
                    .where(cb.equal(exemplaireFilmRoot.get("film"), film.getIdfilm()),
                            cb.not(cb.exists(subQuery)));
            // Lance la requete
            Query<Exemplairefilm> query = session.createQuery(mainQuery);
            List<Exemplairefilm> results = query.getResultList();

            // instancialise le nombre d'exemplaire disponible pour louer
            int nombreExemplaireDispo = results.size();
//            for (Exemplairefilm exemplaire : results) {
//                System.out.println("Exemplaire dispo : " + exemplaire.getCodeexemplaire());
//            }

            if (nombreExemplaireDispo == 0) {
                System.out.println("Plus aucun d'exemplaire disponible");
                return;
            }

            if (nombreExemplaireDispo > 0) {
                Exemplairefilm exemplaire = results.get(0);
            //    System.out.println("Exemplaire trouvé : " + exemplaire.getCodeexemplaire());
                session.beginTransaction();
                Location location = new Location();
                location.setFilm(exemplaire);
                location.setDatedebut(new java.sql.Date(System.currentTimeMillis()));
                location.setStatut("louer");
                location.setAbonnement(client.getAbonnement());
                switch (forfait) {
                    case "D":
                        location.setDatemax(new java.sql.Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
                        break;
                    case "I":
                        location.setDatemax(new java.sql.Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000));
                        break;
                    default :
                        location.setDatemax(new java.sql.Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000 * 24 * 10));
                        break;
                }

                session.save(location);
                session.getTransaction().commit();
                System.out.println("Film loué avec succès");
            } else {
                System.out.println("Aucun exemplaire trouvé");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la location du film");
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

}

