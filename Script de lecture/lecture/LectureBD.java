import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class LectureBD {
   Connection connection;

   public class Role {
      public Role(int i, String n, String p) {
         id = i;
         nom = n;
         personnage = p;
      }

      protected int id;
      protected String nom;
      protected String personnage;
   }

   public LectureBD() {
      connectionBD();
   }

   public void lecturePersonnes(String nomFichier) {
      try {
         System.out.println("Début de lecture personnes");
         String callProcedure = "{call p_ajouter_personne_film(?, ?, ?, ?, ?, ?)}";
         PreparedStatement ps = connection.prepareStatement(callProcedure);

         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
         XmlPullParser parser = factory.newPullParser();

         InputStream is = new FileInputStream(nomFichier);
         parser.setInput(is, null);

         int eventType = parser.getEventType();

         String tag = null,
               nom = null,
               anniversaire = null,
               lieu = null,
               photo = null,
               bio = null;

         int id = -1;

         while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
               tag = parser.getName();

               if (tag.equals("personne") && parser.getAttributeCount() == 1)
                  id = Integer.parseInt(parser.getAttributeValue(0));
            } else if (eventType == XmlPullParser.END_TAG) {
               tag = null;

               if (parser.getName().equals("personne") && id >= 0) {
                  insertionPersonne(id, nom, anniversaire, lieu, photo, bio, ps);

                  id = -1;
                  nom = null;
                  anniversaire = null;
                  lieu = null;
                  photo = null;
                  bio = null;
               }
            } else if (eventType == XmlPullParser.TEXT && id >= 0) {
               if (tag != null) {
                  if (tag.equals("nom"))
                     nom = parser.getText();
                  else if (tag.equals("anniversaire"))
                     anniversaire = parser.getText();
                  else if (tag.equals("lieu"))
                     lieu = parser.getText();
                  else if (tag.equals("photo"))
                     photo = parser.getText();
                  else if (tag.equals("bio"))
                     bio = parser.getText();
               }
            }

            eventType = parser.next();
         }

         ps.executeBatch();
         System.out.println("Fin de lecture");
      } catch (XmlPullParserException e) {
         System.out.println(e);
      } catch (IOException e) {
         System.out.println("IOException while parsing " + nomFichier);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public void lectureFilms(String nomFichier) {
      try {
         System.out.println("Début de lecture films");

         String callProcedureAjouterFilm = "{call p_ajouter_film(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
         PreparedStatement psAjouterFilm = connection.prepareStatement(callProcedureAjouterFilm);

         String callProcedureAjouterScenariste = "{call p_ajouter_scenariste(?, ?)}";
         PreparedStatement psAjouterScenariste = connection.prepareStatement(callProcedureAjouterScenariste);

         String callProcedureAjouterGenre = "{call p_ajouter_genre(?, ?)}";
         PreparedStatement psAjouterGenre = connection.prepareStatement(callProcedureAjouterGenre);

         String callProcedureAjouterPays = "{call p_ajouter_pays_production(?, ?)}";
         PreparedStatement psAjouterPays = connection.prepareStatement(callProcedureAjouterPays);

         String callProcedureAjouterRole = "{call p_ajouter_role_acteur(?, ?, ?)}";
         PreparedStatement psAjouterRole = connection.prepareStatement(callProcedureAjouterRole);

         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
         XmlPullParser parser = factory.newPullParser();

         InputStream is = new FileInputStream(nomFichier);
         parser.setInput(is, null);

         int eventType = parser.getEventType();

         String tag = null,
               titre = null,
               langue = null,
               poster = null,
               roleNom = null,
               rolePersonnage = null,
               realisateurNom = null,
               resume = null;

         ArrayList<String> pays = new ArrayList<String>();
         ArrayList<String> genres = new ArrayList<String>();
         ArrayList<String> scenaristes = new ArrayList<String>();
         ArrayList<Role> roles = new ArrayList<Role>();
         ArrayList<String> annonces = new ArrayList<String>();

         int id = -1,
               annee = -1,
               duree = -1,
               roleId = -1,
               realisateurId = -1;

         while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
               tag = parser.getName();

               if (tag.equals("film") && parser.getAttributeCount() == 1)
                  id = Integer.parseInt(parser.getAttributeValue(0));
               else if (tag.equals("realisateur") && parser.getAttributeCount() == 1)
                  realisateurId = Integer.parseInt(parser.getAttributeValue(0));
               else if (tag.equals("acteur") && parser.getAttributeCount() == 1)
                  roleId = Integer.parseInt(parser.getAttributeValue(0));
            } else if (eventType == XmlPullParser.END_TAG) {
               tag = null;

               if (parser.getName().equals("film") && id >= 0) {
                  insertionFilm(id, titre, annee, pays, langue,
                        duree, resume, genres, realisateurNom,
                        realisateurId, scenaristes,
                        roles, poster, annonces, psAjouterFilm, psAjouterScenariste, psAjouterGenre, psAjouterPays,
                        psAjouterRole);

                  id = -1;
                  annee = -1;
                  duree = -1;
                  titre = null;
                  langue = null;
                  poster = null;
                  resume = null;
                  realisateurNom = null;
                  roleNom = null;
                  rolePersonnage = null;
                  realisateurId = -1;
                  roleId = -1;

                  genres.clear();
                  scenaristes.clear();
                  roles.clear();
                  annonces.clear();
                  pays.clear();
               }
               if (parser.getName().equals("role") && roleId >= 0) {
                  roles.add(new Role(roleId, roleNom, rolePersonnage));
                  roleId = -1;
                  roleNom = null;
                  rolePersonnage = null;
               }
            } else if (eventType == XmlPullParser.TEXT && id >= 0) {
               if (tag != null) {
                  if (tag.equals("titre"))
                     titre = parser.getText();
                  else if (tag.equals("annee"))
                     annee = Integer.parseInt(parser.getText());
                  else if (tag.equals("pays"))
                     pays.add(parser.getText());
                  else if (tag.equals("langue"))
                     langue = parser.getText();
                  else if (tag.equals("duree"))
                     duree = Integer.parseInt(parser.getText());
                  else if (tag.equals("resume"))
                     resume = parser.getText();
                  else if (tag.equals("genre"))
                     genres.add(parser.getText());
                  else if (tag.equals("realisateur"))
                     realisateurNom = parser.getText();
                  else if (tag.equals("scenariste"))
                     scenaristes.add(parser.getText());
                  else if (tag.equals("acteur"))
                     roleNom = parser.getText();
                  else if (tag.equals("personnage"))
                     rolePersonnage = parser.getText();
                  else if (tag.equals("poster"))
                     poster = parser.getText();
                  else if (tag.equals("annonce"))
                     annonces.add(parser.getText());
               }
            }

            eventType = parser.next();
         }

         psAjouterFilm.executeBatch();
         psAjouterScenariste.executeBatch();
         psAjouterGenre.executeBatch();
         psAjouterPays.executeBatch();
         psAjouterRole.executeBatch();

         System.out.println("Fin de lecture");
      } catch (XmlPullParserException e) {
         System.out.println(e);
      } catch (IOException e) {
         System.out.println("IOException while parsing " + nomFichier);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public void lectureClients(String nomFichier) {
      try {
         System.out.println("Début de lecture clients");

         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
         XmlPullParser parser = factory.newPullParser();

         InputStream is = new FileInputStream(nomFichier);
         parser.setInput(is, null);

         int eventType = parser.getEventType();

         String tag = null,
               nomFamille = null,
               prenom = null,
               courriel = null,
               tel = null,
               anniv = null,
               adresse = null,
               ville = null,
               province = null,
               codePostal = null,
               carte = null,
               noCarte = null,
               motDePasse = null,
               forfait = null;

         int id = -1,
               expMois = -1,
               expAnnee = -1;

         while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
               tag = parser.getName();

               if (tag.equals("client") && parser.getAttributeCount() == 1)
                  id = Integer.parseInt(parser.getAttributeValue(0));
            } else if (eventType == XmlPullParser.END_TAG) {
               tag = null;

               if (parser.getName().equals("client") && id >= 0) {
                  insertionClient(id, nomFamille, prenom, courriel, tel,
                        anniv, adresse, ville, province,
                        codePostal, carte, noCarte,
                        expMois, expAnnee, motDePasse, forfait);

                  nomFamille = null;
                  prenom = null;
                  courriel = null;
                  tel = null;
                  anniv = null;
                  adresse = null;
                  ville = null;
                  province = null;
                  codePostal = null;
                  carte = null;
                  noCarte = null;
                  motDePasse = null;
                  forfait = null;

                  id = -1;
                  expMois = -1;
                  expAnnee = -1;
               }
            } else if (eventType == XmlPullParser.TEXT && id >= 0) {
               if (tag != null) {
                  if (tag.equals("nom-famille"))
                     nomFamille = parser.getText();
                  else if (tag.equals("prenom"))
                     prenom = parser.getText();
                  else if (tag.equals("courriel"))
                     courriel = parser.getText();
                  else if (tag.equals("tel"))
                     tel = parser.getText();
                  else if (tag.equals("anniversaire"))
                     anniv = parser.getText();
                  else if (tag.equals("adresse"))
                     adresse = parser.getText();
                  else if (tag.equals("ville"))
                     ville = parser.getText();
                  else if (tag.equals("province"))
                     province = parser.getText();
                  else if (tag.equals("code-postal"))
                     codePostal = parser.getText();
                  else if (tag.equals("carte"))
                     carte = parser.getText();
                  else if (tag.equals("no"))
                     noCarte = parser.getText();
                  else if (tag.equals("exp-mois"))
                     expMois = Integer.parseInt(parser.getText());
                  else if (tag.equals("exp-annee"))
                     expAnnee = Integer.parseInt(parser.getText());
                  else if (tag.equals("mot-de-passe"))
                     motDePasse = parser.getText();
                  else if (tag.equals("forfait"))
                     forfait = parser.getText();
               }
            }

            eventType = parser.next();
         }

         System.out.println("Fin de lecture");
      } catch (XmlPullParserException e) {
         System.out.println(e);
      } catch (IOException e) {
         System.out.println("IOException while parsing " + nomFichier);
      }
   }

   private void insertionPersonne(int id, String nom, String anniv, String lieu, String photo, String bio,
         PreparedStatement ps) throws SQLException {
      // On insere la personne dans la BD
      ps.setInt(1, id);
      ps.setString(2, anniv);
      ps.setString(3, photo);
      ps.setString(4, bio);
      ps.setString(5, nom);
      ps.setString(6, lieu);
      ps.addBatch();
   }

   private void insertionForfait(String code, String nom, double prix, int maxlocation, Float maxduree) {
      String sql = "INSERT INTO FORFAIT (codeforfait, nom, prix, maxlocation, maxduree) VALUES (?, ?, ?, ?, ?)";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setString(1, code);
         preparedStatement.setString(2, nom);
         preparedStatement.setDouble(3, prix);
         preparedStatement.setInt(4, maxlocation);
         preparedStatement.setFloat(5, maxduree);

         int rowsAffected = preparedStatement.executeUpdate();

         if (rowsAffected > 0) {
            System.out.println("Insert successful");
         } else {
            System.out.println("Insert failed");
         }
      } catch (Exception e) {
         System.out.println("Exception occured while insertionForfait: " + e.toString());
      }
   }

   private void insertionFilm(int id, String titre, int annee,
         ArrayList<String> pays, String langue, int duree, String resume,
         ArrayList<String> genres, String realisateurNom, int realisateurId,
         ArrayList<String> scenaristes,
         ArrayList<Role> roles, String poster,
         ArrayList<String> annonces, PreparedStatement psAjouterFilm, PreparedStatement psAjouterScenariste,
         PreparedStatement psAjouterGenre, PreparedStatement psAjouterPays, PreparedStatement psAjouterRole)
         throws SQLException {
      // On met les film dans la BD
      String annoncesString = String.join("###", annonces);

      psAjouterFilm.setInt(1, id);
      psAjouterFilm.setString(2, titre);
      psAjouterFilm.setInt(3, annee);
      psAjouterFilm.setString(4, langue);
      psAjouterFilm.setInt(5, duree);
      psAjouterFilm.setString(6, resume);
      psAjouterFilm.setString(7, poster);
      psAjouterFilm.setString(8, annoncesString);
      psAjouterFilm.setInt(9, realisateurId);
      psAjouterFilm.addBatch();

      if (scenaristes != null) {
         for (String scenariste : scenaristes) {
            psAjouterScenariste.setString(1, scenariste);
            psAjouterScenariste.setInt(2, id);
            psAjouterScenariste.addBatch();
         }
      }

      if (genres != null) {
         for (String genre : genres) {
            psAjouterGenre.setString(1, genre);
            psAjouterGenre.setInt(2, id);
            psAjouterGenre.addBatch();
         }
      }

      if (pays != null) {
         for (String paysFilm : pays) {
            psAjouterPays.setString(1, paysFilm);
            psAjouterPays.setInt(2, id);
            psAjouterPays.addBatch();
         }
      }

      if (roles != null) {
         for (Role role : roles) {
            psAjouterRole.setInt(1, role.id);
            psAjouterRole.setInt(2, id);
            psAjouterRole.setString(3, role.personnage);
            psAjouterRole.addBatch();
         }
      }
   }

   private void insertionClient(int idPersonne, String nomFamille, String prenom, String courriel,
         String tel, String anniv, String adresse, String ville, String province,
         String codePostal, String typeCarte, String noCarte,
         int expMois, int expAnnee, String motDePasse, String forfait) {

      int indexEspace = adresse.indexOf(' ');
      String numeroCivique = "";
      String rue = "";

      if (indexEspace != -1) {
         numeroCivique = adresse.substring(0, indexEspace);
         rue = adresse.substring(indexEspace + 1);
      }
      String callProcedure = "{call p_ajouter_client_avec_id(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
      try (PreparedStatement ps = connection.prepareStatement(callProcedure)) {
         ps.setInt(1, idPersonne);
         ps.setString(2, anniv);
         ps.setString(3, nomFamille);
         ps.setString(4, prenom);
         ps.setString(5, courriel);
         ps.setString(6, tel);
         ps.setString(7, motDePasse);
         ps.setString(8, noCarte);
         ps.setString(9, typeCarte);
         ps.setNull(10, Types.VARCHAR);
         ps.setInt(11, expMois);
         ps.setInt(12, expAnnee);
         ps.setString(13, forfait);
         ps.setString(14, rue);
         ps.setString(15, ville);
         ps.setString(16, province);
         ps.setNull(17, Types.VARCHAR);
         ps.setString(18, numeroCivique);
         ps.setString(19, codePostal);
         ps.execute();
      } catch (Exception e) {
         System.out.println("Exception occured while insertionClient: " + e.toString());
      }
   }

   private void connectionBD() {
      String jdbcUrl = "jdbc:oracle:thin:@log660ora12c.logti.etsmtl.ca:1521:LOG660";
      String username = "EQUIPE202";
      String password = "GcCQJNkg";

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         connection = DriverManager.getConnection(jdbcUrl, username, password);

         if (connection != null) {
            System.out.println("Connected");
         } else {
            System.out.println("Failed to connect");
         }
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      LectureBD lecture = new LectureBD();

      // Forfait création pour abonnement

      lecture.insertionForfait("D", "Débutant", 5.00, 1, 10f);
      lecture.insertionForfait("I", "Intermédiaire", 10.00, 5, 30f);
      lecture.insertionForfait("A", "Avancé", 15.0, 10, Float.POSITIVE_INFINITY);

      String pathPersonnes = new File("Donnees\\personnes_latin1.xml").getAbsolutePath();
      lecture.lecturePersonnes(pathPersonnes);

      String pathFilms = new File("Donnees\\films_latin1.xml").getAbsolutePath();
      lecture.lectureFilms(pathFilms);

      /*
       * Pas de batch insert pour les clients car plusieurs clients n'ont pas des
       * champs valides
       */
      String pathClients = new File("Donnees\\clients_latin1.xml").getAbsolutePath();
      lecture.lectureClients(pathClients);

   }
}
