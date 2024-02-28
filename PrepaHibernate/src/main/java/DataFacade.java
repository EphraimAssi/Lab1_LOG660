import Service.AuthentificationService;
import entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.type.StringType;

import java.util.List;

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

    public List<Film> consultationFilms(String titre, String anneeMin, String anneeMax, String pays, String langue, String genre, String realisateur, String acteur) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Film.class);

            if (titre != null && !titre.isEmpty()) {
                criteria.add(Restrictions.ilike("titre", titre, MatchMode.ANYWHERE));
            }

            if (anneeMin != null && !anneeMin.isEmpty()) {
                criteria.add(Restrictions.ge("annee", anneeMin));
            }

            if (anneeMax != null && !anneeMax.isEmpty()) {
                criteria.add(Restrictions.le("annee", anneeMax));
            }

            if (pays != null && !pays.isEmpty()) {
                criteria.add(Restrictions.sqlRestriction("upper(nomPays) like upper(?)", "%" + pays + "%", StringType.INSTANCE));
            }

            if (langue != null && !langue.isEmpty()) {
                criteria.add(Restrictions.ilike("langue", langue, MatchMode.ANYWHERE));
            }

            if (genre != null && !genre.isEmpty()) {
                criteria.createAlias("genres", "g")
                        .add(Restrictions.ilike("g.nomgenre", "%" + genre + "%"));
            }

            if (realisateur != null && !realisateur.isEmpty()) {
                criteria.createAlias("realisateurs", "r")
                        .createAlias("r.personne", "p")
                        .add(Restrictions.ilike("p.nomcomplet", "%" + realisateur + "%"));
            }

            if (acteur != null && !acteur.isEmpty()) {
                criteria.createAlias("roles", "ro");
                criteria.createAlias("ro.role.personne", "a");
                criteria.add(Restrictions.ilike("a.personne.nomComplet", "%" + acteur + "%"));
            }

            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria.list();
        }
    }

    public void loadExemplaireFilm(Film film) {
        try (Session session = sessionFactory.openSession()) {
            List<Exemplairefilm> exemplairesFilm = session.createCriteria(Exemplairefilm.class)
                    .add(Restrictions.eq("film.idfilm", film.getIdfilm()))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();

            film.setExemplairesFilm(exemplairesFilm);
        }
    }

    public void loadGenres(Film film) {
        try (Session session = sessionFactory.openSession()) {
            List<Genre> genres = session.createQuery(
                            "SELECT g FROM Film f JOIN f.genres g WHERE f.idfilm = :filmId", Genre.class)
                    .setParameter("filmId", film.getIdfilm())
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();

            film.setGenres(genres);
        }
    }

    public void loadRealisateurs(Film film) {
        try (Session session = sessionFactory.openSession()) {
            List<Realisateur> realisateurs = session.createQuery(
                            "SELECT r FROM Film f JOIN f.realisateurs r WHERE f.idfilm = :filmId", Realisateur.class)
                    .setParameter("filmId", film.getIdfilm())
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();

            film.setRealisateurs(realisateurs);
        }
    }

    public void loadRoles(Film film) {
        try (Session session = sessionFactory.openSession()) {
            List<Role> roles = session.createQuery(
                            "SELECT r FROM Film f JOIN f.roles r WHERE f.idfilm = :filmId", Role.class)
                    .setParameter("filmId", film.getIdfilm())
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();

            film.setRoles(roles);
        }
    }

    public void loadAllFilm(Film film) {
        loadExemplaireFilm(film);
        loadGenres(film);
        loadRealisateurs(film);
        loadRoles(film);
    }
}
