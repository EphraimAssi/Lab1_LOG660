-- Devrait retourner 4549
SELECT COUNT(*) FROM Personne WHERE categoriePersonne = 'PersonneFilm';

-- Devrait retourner 631 films, 630 réalisateursFilm, 1093 scénaristesFilm, 6535 RoleActeur
SELECT COUNT(*) FROM Film;
SELECT COUNT(*) FROM RealisateurFilm;
SELECT COUNT(*) FROM ScenaristeFilm;
SELECT COUNT(*) FROM RoleActeur;

-- Devrait être le même nombre de clients, de personnesDossier et d'abonnements
SELECT COUNT(*) FROM Personne WHERE categoriePersonne = 'PersonneDossier';
SELECT COUNT(*) FROM CLIENT;
SELECT COUNT(*) FROM Abonnement;

-- Test Trigger
-- Devrait retourner 0
SELECT *
FROM Personne
WHERE categoriePersonne = 'PersonneDossier' AND MONTHS_BETWEEN(CURRENT_DATE, dateNaissance) / 12 < 18;

-- Test ExemplaireFilm
SELECT * FROM ExemplaireFilm;
SELECT COUNT(DISTINCT f.idFilm) AS num_films_with_exemplaires
FROM Film f
JOIN ExemplaireFilm e ON f.idFilm = e.idFilm;


