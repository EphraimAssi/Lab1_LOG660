import Service.AuthentificationService;
import Service.LocationFilmService;
import entity.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataBroker {
    private final SessionFactory sessionFactory;

    public DataBroker() {
        HibernateConfig hibernateConfig = new HibernateConfig();
        this.sessionFactory = hibernateConfig.getSessionFactory();
    }

    public ArrayList<String> splitToList(String input) {
        if (input == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(input.split(",")));
    }

    public String getFirstItem(List<String> list) {
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    public PersonneDossier connectionUtilisateur(String email, String password) {
        AuthentificationService authentificationService = new AuthentificationService(sessionFactory);
        PersonneDossier utilisateur = authentificationService.connectionUtilisateur(email, password);
        if (utilisateur != null) {
            System.out.println("Utilisateur trouvé : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            System.out.println("Utilisateur non trouvé");
            return null;
        }
        return utilisateur;
    }

    public List<Film> consultationFilms(String titre, String anneeMin, String anneeMax, String pays, String langue, String genre, String realisateur, String acteur) {
        List<Film> films = new ArrayList<>();

        ArrayList<String> titreList = splitToList(titre);
        ArrayList<String> paysList = splitToList(pays);
        ArrayList<String> langueList = splitToList(langue);
        ArrayList<String> genreList = splitToList(genre);
        ArrayList<String> realisateurList = splitToList(realisateur);
        ArrayList<String> acteurList = splitToList(acteur);

        films = consultationFilmsRecursive(titreList, anneeMin, anneeMax, paysList, langueList, genreList, realisateurList, acteurList, films);

        return films;
    }

    public List<Film> consultationFilmsRecursive(List<String> titreList, String anneeMin, String anneeMax, List<String> paysList, List<String> langueList, List<String> genreList, List<String> realisateurList, List<String> acteurList, List<Film> films) {
        if (titreList.isEmpty() && paysList.isEmpty() && langueList.isEmpty() && genreList.isEmpty() && realisateurList.isEmpty() && acteurList.isEmpty()) {
            return films;
        }

        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Film.class);

            if (!films.isEmpty()) {
                List<BigInteger> filmIds = films.stream().map(Film::getIdfilm).collect(Collectors.toList());
                criteria.add(Restrictions.in("id", filmIds));
            }

            String titre = getFirstItem(titreList);
            if (titre != null && !titre.isEmpty()) {
                Conjunction conjunction = Restrictions.conjunction();
                for (char c : titre.toCharArray()) {
                    conjunction.add(Restrictions.ilike("titre", String.valueOf(c), MatchMode.ANYWHERE));
                }
                criteria.add(conjunction);
                titreList.remove(0);
            }

            if (anneeMin == null && anneeMax == null) {
                anneeMin = "0";
                anneeMax = "9999";
            }

            try {
                Integer numericAnneeMin = null;
                Integer numericAnneeMax = null;

                if (anneeMin != "0") {
                    numericAnneeMin = Integer.parseInt(anneeMin);
                }

                if (anneeMax != "9999") {
                    numericAnneeMax = Integer.parseInt(anneeMax);
                }

                if (numericAnneeMin != null && numericAnneeMax != null && numericAnneeMin > numericAnneeMax) {
                    return films;
                }
            } catch (NumberFormatException | NullPointerException e) {
                return films;
            }

            if (anneeMin != "0" && !anneeMin.isEmpty() && StringUtils.isNumeric(anneeMin)) {
                criteria.add(Restrictions.ge("annee", anneeMin));
            }

            if (anneeMax != "9999" && !anneeMax.isEmpty() && StringUtils.isNumeric(anneeMax)) {
                criteria.add(Restrictions.le("annee", anneeMax));
            }

            String pays = getFirstItem(paysList);
            if (pays != null && !pays.isEmpty()) {
                StringBuilder pattern = new StringBuilder();
                for (char c : pays.toCharArray()) {
                    pattern.append(" AND UPPER(nomPays) LIKE UPPER('%").append(c).append("%')");
                }
                criteria.add(Restrictions.sqlRestriction(pattern.toString().substring(5)));
                paysList.remove(0);
            }

            String langue = getFirstItem(langueList);
            if (langue != null && !langue.isEmpty()) {
                Conjunction conjunction = Restrictions.conjunction();
                for (char c : langue.toCharArray()) {
                    conjunction.add(Restrictions.ilike("langue", String.valueOf(c), MatchMode.ANYWHERE));
                }
                criteria.add(conjunction);
                langueList.remove(0);
            }

            String genre = getFirstItem(genreList);
            if (genre != null && !genre.isEmpty()) {
                Conjunction conjunction = Restrictions.conjunction();
                for (char c : genre.toCharArray()) {
                    conjunction.add(Restrictions.ilike("g.nomgenre", String.valueOf(c), MatchMode.ANYWHERE));
                }
                criteria.createAlias("genres", "g").add(conjunction);
                genreList.remove(0);
            }

            String realisateur = getFirstItem(realisateurList);
            if (realisateur != null && !realisateur.isEmpty()) {
                Conjunction conjunction = Restrictions.conjunction();
                for (char c : realisateur.toCharArray()) {
                    conjunction.add(Restrictions.ilike("p.nomcomplet", String.valueOf(c), MatchMode.ANYWHERE));
                }
                criteria.createAlias("realisateurs", "r")
                        .createAlias("r.personne", "p")
                        .add(conjunction);
                realisateurList.remove(0);
            }

            String acteur = getFirstItem(acteurList);
            if (acteur != null && !acteur.isEmpty()) {
                Disjunction disjunction = Restrictions.disjunction();

                for (String a : acteur.split(",")) {
                    Conjunction conjunction = Restrictions.conjunction();
                    for (char c : a.trim().toCharArray()) {
                        conjunction.add(Restrictions.ilike("personneAlias.nomcomplet", String.valueOf(c), MatchMode.ANYWHERE));
                    }
                    disjunction.add(conjunction);
                }

                criteria.createAlias("roles", "rolesAlias")
                        .createAlias("rolesAlias.acteur", "acteurAlias")
                        .createAlias("acteurAlias.personne", "personneAlias")
                        .add(disjunction);
                acteurList.remove(0);
            }

            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Film> filmsSearch = criteria.list();

            return consultationFilmsRecursive(titreList, anneeMin, anneeMax, paysList, langueList, genreList, realisateurList, acteurList, filmsSearch);
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

    public void locationFilmPersonneDossier(BigInteger idpersonne) {
        Client client = retourneClient(idpersonne);
        Film film = retourneFilm();
        LocationFilmService locationFilm = new LocationFilmService(sessionFactory);
        locationFilm.locationFilmPersonneDossier(client, film);
    }

    public Client retourneClient(BigInteger idpersonne) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Client> query = session.createQuery("select p from Client p where idpersonne = :idpersonne", Client.class);
            query.setParameter("idpersonne", idpersonne);
            Client client = query.uniqueResult();
            session.getTransaction().commit();
            if (client != null) {
                session.close();
                return client; // User found and password matches
            } else {
                System.out.println("Client not found");
                session.close();
                return null; // User not found or password does not match
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // L'utilisateur n'est pas encore un client ?
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

    public List<Film> getAllFilms() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Film> result = session.createQuery("select e from Film e").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
