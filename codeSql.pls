/*Création des tables*/
CREATE TABLE AdresseBase (
    idAdresse NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rue VARCHAR2(50),
    ville VARCHAR2(50),
    province VARCHAR2(50),
    pays VARCHAR2(50)
);

CREATE TABLE Adresse(
    idAdresse NUMBER NOT NULL PRIMARY KEY,
    numeroCivique VARCHAR2(5) NOT NULL,
    codePostal CHAR(7) NOT NULL CHECK (REGEXP_LIKE(codePostal, '^[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]$')),
    CONSTRAINT fk_idAdresse_AdresseBase FOREIGN KEY (idAdresse) REFERENCES AdresseBase(idAdresse)
);

CREATE TABLE Personne (
    idPersonne NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    dateNaissance DATE,
    photo VARCHAR2(200),
    nomComplet VARCHAR2(100),
    lieuNaissance VARCHAR2(200),
    bio CLOB,
    nom VARCHAR2(50),
    prenom VARCHAR2(50),
    courriel VARCHAR2(50),
    CONSTRAINT check_courriel CHECK (REGEXP_LIKE(courriel, '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$')),
    telephone VARCHAR2(14),
    CONSTRAINT check_telephone CHECK ( REGEXP_LIKE(telephone, '^1-[0-9]{3}-[0-9]{3}-[0-9]{4}$') OR REGEXP_LIKE(telephone, '^[0-9]{3}-[0-9]{3}-[0-9]{4}$') ),
    motDePasse VARCHAR2(20),
    CONSTRAINT check_motDePasse CHECK ( REGEXP_LIKE(motDePasse, '.*[[:alnum:]]{5,}') ),
    categoriePersonne VARCHAR2(15) NOT NULL CHECK (categoriePersonne IN ('PersonneFilm', 'PersonneDossier')),
    idAdresse NUMBER,
    CONSTRAINT fk_idAdresse_Personne FOREIGN KEY (idAdresse) REFERENCES AdresseBase(idAdresse),
    CONSTRAINT unique_constraint_personne UNIQUE (courriel)
);

CREATE TABLE Forfait (
    codeForfait CHAR(1) NOT NULL PRIMARY KEY,
    nom VARCHAR2(15) NOT NULL,
    prix NUMBER(5, 2) NOT NULL,
    maxLocation NUMBER NOT NULL,
    maxDuree BINARY_FLOAT NOT NULL
);

CREATE TABLE Film(
    idFilm NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    titre VARCHAR2(100),
    annee VARCHAR2(4),
    langue VARCHAR2(50),
    duree NUMBER,
    resume CLOB,
    poster VARCHAR(200),
    bandesAnnonces CLOB
);

CREATE TABLE ExemplaireFilm(
    codeExemplaire VARCHAR2(20) NOT NULL PRIMARY KEY,
    idFilm NUMBER NOT NULL,
    CONSTRAINT fk_idFilm_ExemplaireFilm FOREIGN KEY (idFilm) REFERENCES Film(idFilm)
);

CREATE TABLE Abonnement(
    idAbonnement NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codeForfait CHAR(1) NOT NULL,
    CONSTRAINT fk_forfait_abonnement FOREIGN KEY (codeForfait) REFERENCES Forfait(codeForfait)
);

CREATE TABLE Location(
    idLocation NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    statut VARCHAR2(15) CHECK (statut IN ('retourner', 'louer', 'retarder')),
    dateDebut DATE NOT NULL,
    dateMax DATE,
    codeExemplaire VARCHAR2(20) NOT NULL,
    idAbonnement NUMBER NOT NULL,
    CONSTRAINT fk_location_abonnement FOREIGN KEY (idAbonnement) REFERENCES Abonnement(idAbonnement),
    CONSTRAINT fk_exemplaire_location FOREIGN KEY (codeExemplaire) REFERENCES ExemplaireFilm(codeExemplaire)
);

CREATE TABLE Employe(
    idPersonne NOT NULL PRIMARY KEY,
    matricule NUMBER(7) NOT NULL,
    CONSTRAINT fk_idPersonne_employe FOREIGN KEY (idPersonne) REFERENCES Personne(idPersonne)
);

CREATE TABLE Client(
    idPersonne NUMBER NOT NULL PRIMARY KEY,
    numeroCarteCredit VARCHAR2(19) NOT NULL,
    typeCarteCredit VARCHAR2(10) NOT NULL CHECK (typeCarteCredit IN ('Visa', 'MasterCard', 'Amex')),
    cvv VARCHAR2(4),
    expMois NUMBER NOT NULL,
    expAnnee NUMBER NOT NULL,
    idAbonnement NUMBER,
    CONSTRAINT fk_abonnement FOREIGN KEY (idAbonnement) REFERENCES Abonnement(idAbonnement),
    CONSTRAINT fk_idPersonne_client FOREIGN KEY (idPersonne) REFERENCES Personne(idPersonne)
);

CREATE TABLE Acteur(
    idPersonne NUMBER NOT NULL PRIMARY KEY,
    CONSTRAINT fk_idPersonne_acteur FOREIGN KEY (idPersonne) REFERENCES Personne(idPersonne)
);

CREATE TABLE RoleActeur(
    idPersonne NUMBER NOT NULL,
    idFilm NUMBER NOT NULL,
    personnage VARCHAR2(150) NOT NULL,
    CONSTRAINT fk_roleActeur PRIMARY KEY(idFilm, idPersonne),
    CONSTRAINT fk_idActeur FOREIGN KEY (idPersonne) REFERENCES Acteur(idPersonne),
    CONSTRAINT fk_idFilm_RoleActeur FOREIGN KEY (idFilm) REFERENCES Film(idFilm)
);

CREATE TABLE Realisateur(
    idPersonne NUMBER NOT NULL PRIMARY KEY,
    CONSTRAINT fk_idPersonne_realisateur FOREIGN KEY (idPersonne) REFERENCES Personne(idPersonne)
);

CREATE TABLE RealisateurFilm(
    idPersonne NUMBER NOT NULL,
    idFilm NUMBER NOT NULL,
    CONSTRAINT fk_realisateurFilm PRIMARY KEY(idFilm, idPersonne),
    CONSTRAINT fk_idRealisateur FOREIGN KEY (idPersonne) REFERENCES Realisateur(idPersonne),
    CONSTRAINT fk_idFilm_realisateurFilm FOREIGN KEY (idFilm) REFERENCES Film(idFilm)
);

CREATE TABLE Genre(
    nomGenre VARCHAR2(50) NOT NULL PRIMARY KEY
);

CREATE TABLE GenreFilm(
    nomGenre VARCHAR2(50) NOT NULL,
    idFilm NUMBER NOT NULL,
    CONSTRAINT fk_genreFilm PRIMARY KEY(idFilm, nomGenre),
    CONSTRAINT fk_nomGenre FOREIGN KEY (nomGenre) REFERENCES Genre(nomGenre),
    CONSTRAINT fk_idFilm_genreFilm FOREIGN KEY (idFilm) REFERENCES Film(idFilm)
);

CREATE TABLE PaysProduction(
    nomPays VARCHAR2(50) NOT NULL PRIMARY KEY
);

CREATE TABLE PaysProductionFilm(
    nomPays VARCHAR2(50) NOT NULL,
    idFilm NUMBER NOT NULL,
    CONSTRAINT fk_PaysProductionFilm PRIMARY KEY(idFilm, nomPays),
    CONSTRAINT fk_nomPays FOREIGN KEY (nomPays) REFERENCES PaysProduction(nomPays),
    CONSTRAINT fk_idFilm_paysProductionFilm FOREIGN KEY (idFilm) REFERENCES Film(idFilm)
);

CREATE TABLE Scenariste(
    nomScenariste VARCHAR2(50) NOT NULL PRIMARY KEY
);

CREATE TABLE ScenaristeFilm(
    nomScenariste VARCHAR2(50) NOT NULL,
    idFilm NUMBER NOT NULL,
    CONSTRAINT fk_ScenaristeFilm PRIMARY KEY(idFilm, nomScenariste),
    CONSTRAINT fk_nomScenariste FOREIGN KEY (nomScenariste) REFERENCES Scenariste(nomScenariste),
    CONSTRAINT fk_idFilm_scenaristeFilm FOREIGN KEY (idFilm) REFERENCES Film(idFilm)
);

/
/*Triggers*/
/*Trigger pour vérifier que toutes les entrées (Vérification seulement de ceux qui peuvent être null à cause de PersonneFilm) 
sont présent dans personne quand on rentre un client*/
CREATE OR REPLACE TRIGGER trg_check_valeurs_vide_client
BEFORE INSERT OR UPDATE ON Client 
FOR EACH ROW
DECLARE
    nomFamille PERSONNE.nom%TYPE;
    prenomClient PERSONNE.prenom%TYPE;
    courrielClient PERSONNE.courriel%TYPE;
    telephoneClient PERSONNE.telephone%TYPE;
    motDePasseClient PERSONNE.motDePasse%TYPE;
    categorie PERSONNE.categoriePersonne%TYPE;
BEGIN
    SELECT categoriePersonne, nom, prenom, courriel, telephone, motDePasse
    INTO categorie, nomFamille, prenomClient, courrielClient, telephoneClient, motDePasseClient
    FROM Personne
    WHERE idPersonne = :NEW.idPersonne;
    IF categorie != 'PersonneDossier' THEN
        RAISE_APPLICATION_ERROR(-20001, 'La personne doit être de type PersonneDossier.');
    END IF;
    IF nomFamille IS NULL OR prenomClient IS NULL OR courrielClient IS NULL OR telephoneClient IS NULL OR motDePasseClient IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Toutes les valeurs doivent être présentes.');
    END IF;
END;
/

--Trigger pour s'assurer que Employe est de type PersonneDossier--
CREATE OR REPLACE TRIGGER trg_check_employeType 
BEFORE INSERT OR UPDATE ON Employe 
FOR EACH ROW
DECLARE
    nomFamille PERSONNE.nom%TYPE;
    prenomEmploye PERSONNE.prenom%TYPE;
    courrielEmploye PERSONNE.courriel%TYPE;
    telephoneEmploye PERSONNE.telephone%TYPE;
    motDePasseEmploye PERSONNE.motDePasse%TYPE;
    categorie PERSONNE.categoriePersonne%TYPE;
BEGIN
    SELECT categoriePersonne, nom, prenom, courriel, telephone, motDePasse
    INTO categorie, nomFamille, prenomEmploye, courrielEmploye, telephoneEmploye, motDePasseEmploye
    FROM Personne
    WHERE idPersonne = :NEW.idPersonne;
    IF categorie != 'PersonneDossier' THEN
        RAISE_APPLICATION_ERROR(-20001, 'La personne doit être de type PersonneDossier.');
    END IF;
    IF nomFamille IS NULL OR prenomEmploye IS NULL OR courrielEmploye IS NULL OR telephoneEmploye IS NULL OR motDePasseEmploye IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Toutes les valeurs doivent être présentes.');
    END IF;
END;
/

--Trigger pour s'assurer que Acteur est de type PersonneFilm--
CREATE OR REPLACE TRIGGER trg_check_acteurType 
BEFORE INSERT OR UPDATE ON Acteur 
FOR EACH ROW
DECLARE
    categorie PERSONNE.categoriePersonne%TYPE;
BEGIN
    SELECT categoriePersonne
    INTO categorie
    FROM Personne
    WHERE idPersonne = :NEW.idPersonne;
    IF categorie != 'PersonneFilm' THEN
        RAISE_APPLICATION_ERROR(-20001, 'La personne doit être de type PersonneFilm.');
    END IF;
END;
/

--Trigger pour s'assurer que Realisateur est de type PersonneFilm--
CREATE OR REPLACE TRIGGER trg_check_realisateurType 
BEFORE INSERT OR UPDATE ON Realisateur 
FOR EACH ROW
DECLARE
    categorie PERSONNE.categoriePersonne%TYPE;
BEGIN
    SELECT categoriePersonne
    INTO categorie
    FROM Personne
    WHERE idPersonne = :NEW.idPersonne;
    IF categorie != 'PersonneFilm' THEN
        RAISE_APPLICATION_ERROR(-20001, 'La personne doit être de type PersonneFilm.');
    END IF;
END;
/

--Trigger pour vérifier si le Client à 18 ans--
CREATE OR REPLACE TRIGGER trg_check_age 
BEFORE INSERT OR UPDATE ON Client 
FOR EACH ROW
DECLARE
    birth_date DATE;
BEGIN
    SELECT dateNaissance INTO birth_date
    FROM Personne
    WHERE idPersonne = :NEW.idPersonne;
    IF birth_date > SYSDATE - INTERVAL '18' YEAR THEN
        RAISE_APPLICATION_ERROR(-20001, 'Le client doit avoir 18 ans ou plus.');
    END IF;
END;
/


--Trigger pour vérifier que la carte de crédit n'est pas expiré--
CREATE OR REPLACE TRIGGER trg_check_carteCredit_exp
BEFORE INSERT OR UPDATE ON Client 
FOR EACH ROW
DECLARE
    current_month Client.expMois%TYPE;
    current_year Client.expAnnee%TYPE;
BEGIN
    SELECT TO_NUMBER(TO_CHAR(SYSDATE, 'MM')), TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY'))
    INTO current_month, current_year
    FROM dual;

    IF :NEW.expAnnee < current_year OR 
      (:NEW.expAnnee = current_year AND :NEW.expMois < current_month) THEN
        RAISE_APPLICATION_ERROR(-20001, 'La carte de crédit est expirée.');
    END IF;
END;
/

--Trigger pour vérifier que la somme max de film loué n'est pas dépassé--
CREATE OR REPLACE TRIGGER trg_check_max_film
BEFORE INSERT OR UPDATE ON Location 
FOR EACH ROW
DECLARE
    code Abonnement.codeForfait%TYPE;
    location_max Forfait.maxLocation%TYPE;
    nombres_locations NUMBER;
BEGIN
    SELECT codeForfait INTO code
    FROM Abonnement
    WHERE idAbonnement = :NEW.idAbonnement;

    SELECT maxLocation INTO location_max
    FROM Forfait
    WHERE codeForfait = code;

    SELECT COUNT(*) INTO nombres_locations
    FROM Location
    WHERE idAbonnement = :NEW.idAbonnement AND (statut = 'louer' OR statut = 'retarder');

    IF nombres_locations >= location_max THEN
        RAISE_APPLICATION_ERROR(-20001, 'Le nombre maximum de films loués est dépassé pour ce forfait.');
    END IF;
END;
/



/*Procédure stockée*/
--Procédure pour l'ajout d'une location, calculer automatique dateMax--
CREATE OR REPLACE PROCEDURE p_ajouter_location
(
    v_statut Location.statut%TYPE,
    v_dateDebut Location.dateDebut%TYPE,
    v_codeExemplaire Location.codeExemplaire%TYPE,
    v_idAbonnement Location.idAbonnement%TYPE
) AS
    v_dateMax Location.dateMax%TYPE;
    v_forfaitMaxDuree Forfait.maxDuree%TYPE;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    SELECT f.maxDuree
    INTO v_forfaitMaxDuree
    FROM Abonnement a
    JOIN Forfait f ON a.codeForfait = f.codeForfait
    WHERE a.idAbonnement = v_idAbonnement;

    IF v_forfaitMaxDuree IS NULL OR v_forfaitMaxDuree = BINARY_FLOAT_INFINITY THEN
        v_dateMax := TO_DATE('9999-12-31', 'YYYY-MM-DD');
    ELSE
        v_dateMax := v_dateDebut + v_forfaitMaxDuree;
    END IF;

    INSERT INTO Location (statut, dateDebut, dateMax, codeExemplaire, idAbonnement)
    VALUES (v_statut, v_dateDebut, v_dateMax, v_codeExemplaire, v_idAbonnement);
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Location ajoutée!');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_location;
/

--Procédure pour l'ajout d'un client, créer Personne, Abonnement et Adresse automatiquement--
CREATE OR REPLACE PROCEDURE p_ajouter_client_sans_id
(
    v_dateNaissance Personne.dateNaissance%TYPE,
    v_nom Personne.nom%TYPE,
    v_prenom Personne.prenom%TYPE,
    v_courriel Personne.courriel%TYPE,
    v_telephone Personne.telephone%TYPE,
    v_motDePasse Personne.motDePasse%TYPE,
    v_numeroCarteCredit Client.numeroCarteCredit%TYPE,
    v_typeCarteCredit Client.typeCarteCredit%TYPE,
    v_cvv Client.cvv%TYPE,
    v_expMois Client.expMois%TYPE,
    v_expAnnee Client.expAnnee%TYPE,
    v_codeForfait Abonnement.codeForfait%TYPE,
    v_rue AdresseBase.rue%TYPE,
    v_ville AdresseBase.ville%TYPE,
    v_province AdresseBase.province%TYPE,
    v_pays AdresseBase.pays%TYPE,
    v_numeroCivique Adresse.numeroCivique%TYPE,
    v_codePostal Adresse.codePostal%TYPE
) AS
    v_idPersonne Personne.idPersonne%TYPE;
    v_idAdresse AdresseBase.idAdresse%TYPE;
    v_idAbonnement Abonnement.idAbonnement%TYPE;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    INSERT INTO AdresseBase(rue, ville, province, pays)
    VALUES (v_rue, v_ville, v_province, v_pays)
    RETURNING idAdresse INTO v_idAdresse;

    INSERT INTO Adresse(idAdresse, numeroCivique, codePostal)
    VALUES (v_idAdresse, v_numeroCivique, v_codePostal);

    INSERT INTO Personne(dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
    VALUES (v_dateNaissance, v_nom, v_prenom, v_courriel, v_telephone, v_motDePasse, 'PersonneDossier', v_idAdresse)
    RETURNING idPersonne INTO v_idPersonne;

    INSERT INTO Abonnement(codeForfait)
    VALUES (v_codeForfait)
    RETURNING idAbonnement INTO v_idAbonnement;

    INSERT INTO Client(idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee, idAbonnement)
    VALUES (v_idPersonne, v_numeroCarteCredit, v_typeCarteCredit, v_cvv, v_expMois, v_expAnnee, v_idAbonnement);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_client_sans_id;
/
 --Utile pour entrées des données du xml directement dans la base de données--
CREATE OR REPLACE PROCEDURE p_ajouter_client_avec_id
(
    v_idPersonne Personne.idPersonne%TYPE,
    v_dateNaissance Personne.dateNaissance%TYPE,
    v_nom Personne.nom%TYPE,
    v_prenom Personne.prenom%TYPE,
    v_courriel Personne.courriel%TYPE,
    v_telephone Personne.telephone%TYPE,
    v_motDePasse Personne.motDePasse%TYPE,
    v_numeroCarteCredit Client.numeroCarteCredit%TYPE,
    v_typeCarteCredit Client.typeCarteCredit%TYPE,
    v_cvv Client.cvv%TYPE,
    v_expMois Client.expMois%TYPE,
    v_expAnnee Client.expAnnee%TYPE,
    v_codeForfait Abonnement.codeForfait%TYPE,
    v_rue AdresseBase.rue%TYPE,
    v_ville AdresseBase.ville%TYPE,
    v_province AdresseBase.province%TYPE,
    v_pays AdresseBase.pays%TYPE,
    v_numeroCivique Adresse.numeroCivique%TYPE,
    v_codePostal Adresse.codePostal%TYPE
) AS
    v_idAdresse AdresseBase.idAdresse%TYPE;
    v_idAbonnement Abonnement.idAbonnement%TYPE;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    INSERT INTO AdresseBase(rue, ville, province, pays)
    VALUES (v_rue, v_ville, v_province, v_pays)
    RETURNING idAdresse INTO v_idAdresse;

    INSERT INTO Adresse(idAdresse, numeroCivique, codePostal)
    VALUES (v_idAdresse, v_numeroCivique, v_codePostal);

    INSERT INTO Personne(idPersonne, dateNaissance, nom, prenom, courriel, telephone, motDePasse, categoriePersonne, idAdresse)
    VALUES (v_idPersonne, v_dateNaissance, v_nom, v_prenom, v_courriel, v_telephone, v_motDePasse, 'PersonneDossier', v_idAdresse);

    INSERT INTO Abonnement(codeForfait)
    VALUES (v_codeForfait)
    RETURNING idAbonnement INTO v_idAbonnement;

    INSERT INTO Client(idPersonne, numeroCarteCredit, typeCarteCredit, cvv, expMois, expAnnee, idAbonnement)
    VALUES (v_idPersonne, v_numeroCarteCredit, v_typeCarteCredit, v_cvv, v_expMois, v_expAnnee, v_idAbonnement);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_client_avec_id;
/

--Procédure pour l'ajout d'une personne dans un film simple--
CREATE OR REPLACE PROCEDURE p_ajouter_personne_film
(
    v_idPersonne Personne.idPersonne%TYPE,
    v_dateNaissance Personne.dateNaissance%TYPE,
    v_photo Personne.photo%TYPE,
    v_bio Personne.bio%TYPE,
    v_nomComplet Personne.nomComplet%TYPE,
    v_lieuNaissance Personne.lieuNaissance%TYPE
) AS
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    INSERT INTO Personne(idPersonne, dateNaissance, nomComplet, photo, bio, categoriePersonne, lieuNaissance)
    VALUES (v_idPersonne, v_dateNaissance, v_nomComplet, v_photo, v_bio, 'PersonneFilm', v_lieuNaissance);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_personne_film;
/

--Procédure pour l'ajout d'un acteur, créer Personne et AdresseBase automatiquement--
CREATE OR REPLACE PROCEDURE p_ajouter_acteur
(
    v_idPersonne Personne.idPersonne%TYPE,
    v_dateNaissance Personne.dateNaissance%TYPE,
    v_photo Personne.photo%TYPE,
    v_bio Personne.bio%TYPE,
    v_nom_complet Personne.nomComplet%TYPE,
    v_lieuNaissance Personne.lieuNaissance%TYPE
) AS
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    INSERT INTO Personne(idPersonne, dateNaissance, nomComplet, photo, bio, categoriePersonne, lieuNaissance)
    VALUES (v_idPersonne, v_dateNaissance, v_nom_complet, v_photo, v_bio, 'PersonneFilm', v_lieuNaissance);

    INSERT INTO Acteur(idPersonne)
    VALUES (v_idPersonne);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_acteur;
/

--Procédure pour l'ajout d'un réalisateur, créer Personne et AdresseBase automatiquement--
CREATE OR REPLACE PROCEDURE p_ajouter_realisateur
(
    v_idPersonne Personne.idPersonne%TYPE,
    v_dateNaissance Personne.dateNaissance%TYPE,
    v_photo Personne.photo%TYPE,
    v_bio Personne.bio%TYPE,
    v_nom_complet Personne.nomComplet%TYPE,
    v_lieuNaissance Personne.lieuNaissance%TYPE
) AS
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    INSERT INTO Personne(idPersonne, dateNaissance, nomComplet, photo, bio, categoriePersonne, lieuNaissance)
    VALUES (v_idPersonne, v_dateNaissance, v_nom_complet, v_photo, v_bio, 'PersonneFilm', v_lieuNaissance);

    INSERT INTO Realisateur(idPersonne)
    VALUES (v_idPersonne);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_realisateur;
/

--Procédure pour l'ajout d'un film --
CREATE OR REPLACE PROCEDURE p_ajouter_film
(
    v_idFilm Film.idFilm%TYPE,
    v_titre Film.titre%TYPE,
    v_annee Film.annee%TYPE,
    v_langue Film.langue%TYPE,
    v_duree Film.duree%TYPE,
    v_resume Film.resume%TYPE,
    v_poster Film.poster%TYPE,
    v_bandesAnnonces Film.bandesAnnonces%TYPE,
    v_idRealisateur Realisateur.idPersonne%TYPE
)
AS
    v_count NUMBER;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    INSERT INTO Film(idFilm, titre, annee, langue, duree, resume, poster, bandesAnnonces)
    VALUES (v_idFilm, v_titre, v_annee, v_langue, v_duree, v_resume, v_poster, v_bandesAnnonces);

    IF v_idRealisateur != -1 THEN
        SELECT COUNT(*) INTO v_count FROM Realisateur WHERE idPersonne = v_idRealisateur;
            
        IF v_count = 0 THEN
            INSERT INTO Realisateur(idPersonne) VALUES (v_idRealisateur);
        END IF;

        INSERT INTO RealisateurFilm(idFilm, idPersonne)
        VALUES (v_idFilm, v_idRealisateur);

    END IF;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_film;
/

--Procédure pour l'ajout d'un scénariste--
CREATE OR REPLACE PROCEDURE p_ajouter_scenariste
(
    v_nomScenariste Scenariste.nomScenariste%TYPE,
    v_idFilm Film.idFilm%TYPE
)
AS
    v_count NUMBER;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    SELECT COUNT(*) INTO v_count FROM Scenariste WHERE nomScenariste = v_nomScenariste;

    IF v_count = 0 THEN
        INSERT INTO Scenariste(nomScenariste) VALUES (v_nomScenariste);
    END IF;

    INSERT INTO ScenaristeFilm(idFilm, nomScenariste) VALUES (v_idFilm, v_nomScenariste);
    DBMS_OUTPUT.PUT_LINE('Scenariste ajouté!');
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_scenariste;
/

--Procédure pour ajouter un PaysProduction--
CREATE OR REPLACE PROCEDURE p_ajouter_pays_production
(
    v_nomPays PaysProduction.nomPays%TYPE,
    v_idFilm Film.idFilm%TYPE
)
AS
    v_count NUMBER;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    SELECT COUNT(*) INTO v_count FROM PaysProduction WHERE nomPays = v_nomPays;

    IF v_count = 0 THEN
        INSERT INTO PaysProduction(nomPays) VALUES (v_nomPays);
    END IF;

    INSERT INTO PaysProductionFilm(idFilm, nomPays) VALUES (v_idFilm, v_nomPays);
    DBMS_OUTPUT.PUT_LINE('Pays ajouté!');
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_pays_production;
/

--Procédure pour ajouter un Genre--
CREATE OR REPLACE PROCEDURE p_ajouter_genre
(
    v_nomGenre Genre.nomGenre%TYPE,
    v_idFilm Film.idFilm%TYPE
)
AS
    v_count NUMBER;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    SELECT COUNT(*) INTO v_count FROM Genre WHERE nomGenre = v_nomGenre;

    IF v_count = 0 THEN
        INSERT INTO Genre(nomGenre) VALUES (v_nomGenre);
    END IF;

    INSERT INTO GenreFilm(idFilm, nomGenre) VALUES (v_idFilm, v_nomGenre);
    DBMS_OUTPUT.PUT_LINE('Genre ajouté!');
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_genre;
/

--Procédure pour ajouter un RoleActeur--
CREATE OR REPLACE PROCEDURE p_ajouter_role_acteur
(
    v_idPersonne Acteur.idPersonne%TYPE,
    v_idFilm Film.idFilm%TYPE,
    v_personnage RoleActeur.personnage%TYPE
)
AS
    v_count NUMBER;
    v_error_code NUMBER := -20001;
    v_error_message VARCHAR2(4000);
BEGIN
    SELECT COUNT(*) INTO v_count FROM Acteur WHERE idPersonne = v_idPersonne;

    IF v_count = 0 THEN
        INSERT INTO Acteur(idPersonne) VALUES (v_idPersonne);
    END IF;

    INSERT INTO RoleActeur(idPersonne, idFilm, personnage) VALUES (v_idPersonne, v_idFilm, v_personnage);
    DBMS_OUTPUT.PUT_LINE('Role ajouté!');
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE(v_error_message);
        ROLLBACK;
        RAISE_APPLICATION_ERROR(v_error_code, v_error_message, TRUE);
END p_ajouter_role_acteur;
/

--Procédure pour ajuster automatiquement le statut d'une location chaque jour--
CREATE OR REPLACE PROCEDURE p_ajuster_statut_location AS
BEGIN
    UPDATE Location
    SET statut = 'retarder'
    WHERE statut = 'louer' AND TRUNC(SYSDATE) > dateMax;

    COMMIT;
END p_ajuster_statut_location;
/

-- Job pour ajuster automatiquement le statut d'une location chaque jour--
BEGIN
    DBMS_SCHEDULER.create_job (
        job_name        => 'AJUSTER_STATUT_JOB',
        job_type        => 'PLSQL_BLOCK',
        job_action      => 'BEGIN p_ajuster_statut_location; END;',
        start_date      => SYSTIMESTAMP,
        repeat_interval => 'FREQ=DAILY; BYHOUR=0; BYMINUTE=0; BYSECOND=0',
        enabled         => TRUE
    );
END;
/
