-- TEST TRIGGER trg_check_age --
-- Plus de 18 ans --
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('456 Oak St', 'Townsville', 'Provenceville', 'Countryville');
INSERT INTO Adresse (idAdresse, numeroCivique, codePostal) VALUES (1, '456', 'B2C 3D4');

INSERT INTO Personne (dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('2002-01-01', 'YYYY-MM-DD'), 'Smith', 'Jane', 'jane.smith@example.com', '123-456-7890', 'password123', 'PersonneDossier', 1);

INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (1, '2345678901234567', 'MasterCard', '456', 11, 2023);


-- Moins de 18 ans --
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('789 Pine St', 'Villageville', 'Provenceville', 'Countryville');
INSERT INTO Adresse (idAdresse, numeroCivique, codePostal) VALUES (2, '789', 'C3D 4E5');

INSERT INTO Personne (dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('2010-01-01', 'YYYY-MM-DD'), 'Johnson', 'Bob', 'bob.johnson@example.com', '123-456-7890', 'password123', 'PersonneDossier', 2);

INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (2, '3456789012345678', 'Amex', '789', 10, 2024);

SELECT * FROM Personne;
SELECT * FROM Client;

-- TEST TRIGGER trg_check_clientType --
-- PersonneDossier --
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('123 Main St', 'Cityville', 'Provinceville', 'Countryville');
INSERT INTO Adresse (idAdresse, numeroCivique, codePostal) VALUES (1, '123', 'A1B 2C3');

INSERT INTO Personne (dateNaissance, nomComplet, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'Doe, John', 'john.doe@example.com', '123-456-7890', 'password123', 'PersonneDossier', 1);

INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (1, '1234567890123456', 'Visa', '123', 12, 2025);

-- PersonneFilm --
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('456 Oak St', 'Townsville', 'Provenceville', 'Countryville');
INSERT INTO Adresse (idAdresse, numeroCivique, codePostal) VALUES (2, '456', 'B2C 3D4');

INSERT INTO Personne (dateNaissance, nomComplet, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1995-01-01', 'YYYY-MM-DD'), 'Smith, Jane', 'jane.smith@example.com', '123-456-7890', 'password123', 'PersonneFilm', 2);

INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (2, '2345678901234567', 'MasterCard', '456', 11, 2025);


SELECT * FROM Personne;
SELECT * FROM Client;

-- TEST TRIGGER trg_check_carteCredit_exp --
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('123 Main St', 'Cityville', 'Provinceville', 'Countryland');
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('456 Oak Ave', 'Townsville', 'Stateville', 'Countryland');
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('456 Oak Ave', 'Townsville', 'Stateville', 'Countryland');

INSERT INTO Personne (dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'Doe', 'John', 'john.doe@example.com', '123-456-7890', 'password123', 'PersonneDossier', 1);

INSERT INTO Personne (dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1985-05-20', 'YYYY-MM-DD'), 'Smith', 'Jane', 'jane.smith@example.com', '987-654-3210', 'securepass321', 'PersonneDossier', 2);

INSERT INTO Personne (dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1988-09-15', 'YYYY-MM-DD'), 'Brown', 'Robert', 'robert.brown@example.com', '555-123-4567', 'pass456', 'PersonneDossier', 3);

-- Pas Expiré --
INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (1, '1234567890123456', 'Visa', '123', 12, 2025);

-- Expiré --
INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (2, '9876543210123456', 'MasterCard', '456', 01, 2022);

-- Expiré --
INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (3, '5678901234123456', 'Amex', '789', 01, 2023);

SELECT * FROM Personne;
SELECT * FROM Client;

-- TEST TRIGGER trg_check_valeurs_vide_client --
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('123 Main St', 'Cityville', 'Provinceville', 'Countryland');
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('456 Oak Ave', 'Townsville', 'Stateville', 'Countryland');
INSERT INTO AdresseBase (rue, ville, province, pays) VALUES ('789 Pine Blvd', 'Villagetown', 'Regionville', 'Countryland');

INSERT INTO Personne (dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'Doe', 'John', 'john.doe@example.com', '123-456-7890', 'password123', 'PersonneDossier', 1);

INSERT INTO Personne (dateNaissance, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1985-05-20', 'YYYY-MM-DD'), 'jane.smith@example.com', '987-654-3210', 'securepass321', 'PersonneDossier', 2);

INSERT INTO Personne (dateNaissance, nom, prenom, telephone, motDePasse, categoriePersonne, idAdresse)
VALUES (TO_DATE('1988-09-15', 'YYYY-MM-DD'), 'Brown', 'Robert', '555-123-4567', 'pass456', 'PersonneDossier', 3);

-- Valide
INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (1, '1234567890123456', 'Visa', '123', 12, 2025);

-- Invalid
INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (2, '9876543210987654', 'MasterCard', '456', 11, 2024);

-- Invalid
INSERT INTO Client (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee)
VALUES (3, '1111222233334444', 'Amex', '789', 10, 2023);


SELECT * FROM Personne;
SELECT * FROM Client;

-- TEST TRIGGER trg_check_max_film --
-- Inserting data for a subscription with a limit of 5 films
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('A', 'ForfaitA', 20.00, 5, 30);
INSERT INTO Abonnement (codeForfait) VALUES ('A');

-- Inserting a film
INSERT INTO Film (titre, annee, langue, duree, resume, poster, bandesAnnonces)
VALUES ('FilmA', '2022', 'French', 120, 'Description of FilmA', 'PosterLink', 'TrailersLink');

-- Inserting exemplaires for the film
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire1', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire2', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire3', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire4', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire5', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire6', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire7', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire8', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));

-- Inserting 5 locations for the subscription, which is the maximum limit
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('louer', SYSDATE, 'Exemplaire1', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('louer', SYSDATE, 'Exemplaire2', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('louer', SYSDATE, 'Exemplaire3', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('louer', SYSDATE, 'Exemplaire4', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('louer', SYSDATE, 'Exemplaire5', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('retarder', SYSDATE, 'Exemplaire6', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('retourner', SYSDATE, 'Exemplaire7', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));


-- This will trigger the trg_check_max_film trigger and should not raise an error

-- Attempting to insert another location for the same subscription should raise an error
INSERT INTO Location (statut, dateDebut, codeExemplaire, idAbonnement) VALUES ('louer', SYSDATE, 'Exemplaire8', (SELECT idAbonnement FROM Abonnement WHERE codeForfait = 'A'));

SELECT * FROM Forfait;
SELECT * FROM Abonnement;
SELECT * FROM Film;
SELECT * FROM ExemplaireFilm;
SELECT * FROM Location;

-- TEST Procédure p_ajouter_location --
-- Inserting data for a subscription with a limit of 5 films
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('A', 'ForfaitA', 20.00, 5, 30);
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('B', 'ForfaitA', 20.00, 5, binary_float_infinity );
INSERT INTO Abonnement (codeForfait) VALUES ('A');
INSERT INTO Abonnement (codeForfait) VALUES ('B');

-- Inserting a film
INSERT INTO Film (titre, annee, langue, duree, resume, poster, bandesAnnonces)
VALUES ('FilmA', '2022', 'French', 120, 'Description of FilmA', 'PosterLink', 'TrailersLink');

-- Inserting exemplaires for the film
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire1', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire2', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire3', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire4', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));
INSERT INTO ExemplaireFilm (codeExemplaire, idFilm) VALUES ('Exemplaire5', (SELECT idFilm FROM Film WHERE titre = 'FilmA'));


-- Inserting 5 locations for the subscription, which is the maximum limit
BEGIN
    p_ajouter_location('louer', SYSDATE, 'Exemplaire1', 1);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire2', 1);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire3', 1);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire4', 1);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire5', 1);
END;
/

BEGIN
    p_ajouter_location('louer', SYSDATE, 'Exemplaire1', 2);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire2', 2);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire3', 2);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire4', 2);
    p_ajouter_location('louer', SYSDATE, 'Exemplaire5', 2);
END;
/

SELECT * FROM Forfait;
SELECT * FROM Abonnement;
SELECT * FROM Film;
SELECT * FROM ExemplaireFilm;
SELECT * FROM Location;

/* TEST Procédure p_ajouter_client_sans_id */
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('A', 'Forfait A', 10.00, 5, 30.0);
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('B', 'Forfait B', 15.00, 7, 45.0);

-- Test Case 1: Valid data
BEGIN
p_ajouter_client_sans_id(
    TO_DATE('1990-05-15', 'YYYY-MM-DD'),
    'Smith',
    'Alice',
    'alice.smith@example.com',
    '123-456-7890',
    'password456',
    '1111222233334444',
    'MasterCard',
    '789',
    6,
    2025,
    'A',
    '456 Oak Street',
    'Townsville',
    'Provinceville',
    'Countryland',
    '789',
    'A8B 2K3'
);

-- Test Case 2: Invalid credit card details
p_ajouter_client_sans_id(
    TO_DATE('1985-03-28', 'YYYY-MM-DD'),
    'Johnson',
    'Bob',
    'bob.johnson@example.com',
    '555-123-4567',
    'pass123',
    '1234123412341234',
    'Discover',
    '456',
    11,
    2023,
    'B',
    '789 Elm Street',
    'Villagetown',
    'Stateville',
    'Countryland',
    '456',
    'C3D 4E5'
);


-- Test Case 3: Null values for some parameters
p_ajouter_client_sans_id(
    TO_DATE('1988-11-02', 'YYYY-MM-DD'),
    'Miller',
    'Charlie',
    'charlie.miller@example.com',
    '123-456-7890',
    'pass789',
    NULL,
    'VISA',
    '321',
    10,
    2025,
    'C',
    '123 Pine Street',
    'Citytown',
    'Stateville',
    'Countryland',
    '123',
    'A1B 2C3'
);
END;
/

SELECT * FROM AdresseBase;
SELECT * FROM Adresse;
SELECT * FROM Forfait;
SELECT * FROM Personne;
SELECT * FROM Client;
SELECT * FROM Abonnement;


-- Test p_ajouter_client_avec_id --
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('A', 'Forfait A', 10.00, 5, 30.0);
INSERT INTO Forfait (codeForfait, nom, prix, maxLocation, maxDuree) VALUES ('B', 'Forfait B', 15.00, 7, 45.0);

-- Test Case 1: Valid data
BEGIN
p_ajouter_client_avec_id(
    515973,
    TO_DATE('1990-05-15', 'YYYY-MM-DD'),
    'Smith',
    'Alice',
    'alice.smith@example.com',
    '123-456-7890',
    'password456',
    '1111222233334444',
    'MasterCard',
    '789',
    6,
    2025,
    'A',
    '456 Oak Street',
    'Townsville',
    'Provinceville',
    'Countryland',
    '789',
    'A8B 2K3'
);

-- Test Case 2: Invalid credit card details
p_ajouter_client_avec_id(
    5148,
    TO_DATE('1985-03-28', 'YYYY-MM-DD'),
    'Johnson',
    'Bob',
    'bob.johnson@example.com',
    '555-123-4567',
    'pass123',
    '1234123412341234',
    'Discover',
    '456',
    11,
    2023,
    'B',
    '789 Elm Street',
    'Villagetown',
    'Stateville',
    'Countryland',
    '456',
    'C3D 4E5'
);


-- Test Case 3: Null values for some parameters
p_ajouter_client_avec_id(
    385,
    TO_DATE('1988-11-02', 'YYYY-MM-DD'),
    'Miller',
    'Charlie',
    'charlie.miller@example.com',
    '123-456-7890',
    'pass789',
    NULL,
    'VISA',
    '321',
    10,
    2025,
    'C',
    '123 Pine Street',
    'Citytown',
    'Stateville',
    'Countryland',
    '123',
    'A1B 2C3'
);
END;
/

SELECT * FROM AdresseBase;
SELECT * FROM Adresse;
SELECT * FROM Forfait;
SELECT * FROM Personne;
SELECT * FROM Client;
SELECT * FROM Abonnement;

-- p_ajouter_acteur --
BEGIN
p_ajouter_acteur(238105, TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'sample_photo.jpg', 'Sample bio', 'John Doe', 'Sample City', 'Sample Province', 'Sample Country');
p_ajouter_acteur(2, TO_DATE('1985-05-15', 'YYYY-MM-DD'), 'another_photo.jpg', 'Another bio', 'Jane Doe', 'Another City', 'Another Province', 'Another Country');
END;
/

SELECT * FROM Personne;
SELECT * FROM Acteur;

-- p_ajouter_film --
BEGIN
p_ajouter_realisateur(416, TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'sample_photo.jpg', 'Sample bio', 'John Doe', 'Sample City', 'Sample Province', 'Sample Country');
END;
/

BEGIN
p_ajouter_film (
    120755,
    'Mission: Impossible II',
    '2000',
    'English',
    123,
    'A secret agent is sent to Sydney, to find and destroy a genetically modified disease called "Chimera"',
    'http://ia.media-imdb.com/images/M/MV5BMTQ3MDczODk0NV5BMl5BanBnXkFtZTcwMzUxNTAyMQ@@._V1._SX98_SY140_.jpg',
    'TrailersLink#2#3',
    416
);
END;
/

BEGIN  
 p_ajouter_scenariste
(
    'Graham Chapman',
    120755
);
END;
/

SELECT * FROM Personne;
SELECT * FROM Film;
SELECT * FROM Realisateur;
SELECT * FROM RealisateurFilm;
SELECT * FROM Scenariste;
SELECT * FROM ScenaristeFilm;
