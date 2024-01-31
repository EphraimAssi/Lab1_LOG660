import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
   
   
   public void lecturePersonnes(String nomFichier){      
      try {
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
         
         while (eventType != XmlPullParser.END_DOCUMENT) 
         {
            if(eventType == XmlPullParser.START_TAG) 
            {
               tag = parser.getName();
               
               if (tag.equals("personne") && parser.getAttributeCount() == 1)
                  id = Integer.parseInt(parser.getAttributeValue(0));
            } 
            else if (eventType == XmlPullParser.END_TAG) 
            {                              
               tag = null;
               
               if (parser.getName().equals("personne") && id >= 0)
               {
                  insertionPersonne(id,nom,anniversaire,lieu,photo,bio);
                                    
                  id = -1;
                  nom = null;
                  anniversaire = null;
                  lieu = null;
                  photo = null;
                  bio = null;
               }
            }
            else if (eventType == XmlPullParser.TEXT && id >= 0) 
            {
               if (tag != null)
               {                                    
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
      }
      catch (XmlPullParserException e) {
          System.out.println(e);   
       }
       catch (IOException e) {
         System.out.println("IOException while parsing " + nomFichier); 
       }
   }   
   
   public void lectureFilms(String nomFichier){
      try {
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
         
         while (eventType != XmlPullParser.END_DOCUMENT) 
         {
            if(eventType == XmlPullParser.START_TAG) 
            {
               tag = parser.getName();
               
               if (tag.equals("film") && parser.getAttributeCount() == 1)
                  id = Integer.parseInt(parser.getAttributeValue(0));
               else if (tag.equals("realisateur") && parser.getAttributeCount() == 1)
                  realisateurId = Integer.parseInt(parser.getAttributeValue(0));
               else if (tag.equals("acteur") && parser.getAttributeCount() == 1)
                  roleId = Integer.parseInt(parser.getAttributeValue(0));
            } 
            else if (eventType == XmlPullParser.END_TAG) 
            {                              
               tag = null;
               
               if (parser.getName().equals("film") && id >= 0)
               {
                  insertionFilm(id,titre,annee,pays,langue,
                             duree,resume,genres,realisateurNom,
                             realisateurId, scenaristes,
                             roles,poster,annonces);
                                    
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
               if (parser.getName().equals("role") && roleId >= 0) 
               {              
                  roles.add(new Role(roleId, roleNom, rolePersonnage));
                  roleId = -1;
                  roleNom = null;
                  rolePersonnage = null;
               }
            }
            else if (eventType == XmlPullParser.TEXT && id >= 0) 
            {
               if (tag != null)
               {                                    
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
      }
      catch (XmlPullParserException e) {
          System.out.println(e);   
      }
      catch (IOException e) {
         System.out.println("IOException while parsing " + nomFichier); 
      }
   }
   
   public void lectureClients(String nomFichier){
      try {
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
         
         while (eventType != XmlPullParser.END_DOCUMENT) 
         {
            if(eventType == XmlPullParser.START_TAG) 
            {
               tag = parser.getName();
               
               if (tag.equals("client") && parser.getAttributeCount() == 1)
                  id = Integer.parseInt(parser.getAttributeValue(0));
            } 
            else if (eventType == XmlPullParser.END_TAG) 
            {                              
               tag = null;
               
               if (parser.getName().equals("client") && id >= 0)
               {
                  insertionClient(id,nomFamille,prenom,courriel,tel,
                             anniv,adresse,ville,province,
                             codePostal,carte,noCarte, 
                             expMois,expAnnee,motDePasse,forfait);               
                                    
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
            }
            else if (eventType == XmlPullParser.TEXT && id >= 0) 
            {         
               if (tag != null)
               {                                    
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
      }
      catch (XmlPullParserException e) {
          System.out.println(e);   
      }
      catch (IOException e) {
         System.out.println("IOException while parsing " + nomFichier); 
      }
   }   
   
   private void insertionPersonne(int id, String nom, String anniv, String lieu, String photo, String bio) {      
      // On insere la personne dans la BD
      Connection conn = null;
        PreparedStatement pstmt = null;
      String sql = "INSERT INTO personne (id, nom, anniv, lieu, photo, bio) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

       try {
            java.sql.PreparedStatement statement = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nom);
            pstmt.setString(3, anniv); // Make sure this is in the correct date format
            pstmt.setString(4, lieu);
            pstmt.setString(5, photo);
            pstmt.setString(6, bio);
            pstmt.executeUpdate();
            System.out.println("Record inserted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
         
   }
   

   private void insertionForfait(String code, String nom, double prix, int maxlocation, double maxduree) {      
      String sql = "INSERT INTO FORFAIT (codeforfait, nom, prix, maxlocation, maxduree) VALUES (?, ?, ?, ?, ?)";
      
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, nom);
            preparedStatement.setDouble(3, prix);
            preparedStatement.setInt(4, maxlocation)
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
               System.out.println("Insert successful");
            } else {
               System.out.println("Insert failed");
            }
         }
   }

   private void insertionFilm(int id, String titre, int annee,
                           ArrayList<String> pays, String langue, int duree, String resume,
                           ArrayList<String> genres, String realisateurNom, int realisateurId,
                           ArrayList<String> scenaristes,
                           ArrayList<Role> roles, String poster,
                           ArrayList<String> annonces) {         
      // On met les film dans la BD
      String callProcedure = "{call p_ajouter_film(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
      String annoncesString = String.join("###", annonces);
      
      try (CallableStatement cs = connection.prepareCall(callProcedure)) {
         cs.setInt(1, id);
         cs.setString(2, titre);
         cs.setInt(3, annee);
         cs.setString(4, langue);
         cs.setInt(5, duree);
         cs.setString(6, resume);
         cs.setString(7, poster);
         cs.setString(8, annoncesString);
         cs.setInt(9, realisateurId);
         cs.execute();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      for(String scenariste : scenaristes) {
         String callProcedureScenariste = "{call p_ajouter_scenariste(?, ?)}";
         try (CallableStatement cs = connection.prepareCall(callProcedureScenariste)) {
            cs.setString(1, scenariste);
            cs.setInt(2, id);
            cs.execute();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      for(String genre : genres) {
         String callProcedureGenre = "{call p_ajouter_genre(?, ?)}";
         try (CallableStatement cs = connection.prepareCall(callProcedureGenre)) {
            cs.setString(1, genre);
            cs.setInt(2, id);
            cs.execute();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      for(String paysFilm : pays) {
         String callProcedurePays = "{call p_ajouter_pays_production(?, ?)}";
         try (CallableStatement cs = connection.prepareCall(callProcedurePays)) {
            cs.setString(1, paysFilm);
            cs.setInt(2, id);
            cs.execute();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
   
   private void insertionClient(int idPersonne, String nomFamille, String prenom, String courriel,
                                String tel, String anniv,  String numeroCarteCredit, String typeCarteCredit,
                                String cvv, String expMois, String expAnnee,
                                String adresse, String ville, String province,
                               int idAbonnement) {
                              id,nomFamille,prenom,courriel,tel,
                             anniv,adresse,ville,province,
                             codePostal,carte,noCarte, 
                             expMois,expAnnee,motDePasse,forfait
      // On le client dans la BD
    String
    try { 
        connection.createStatement()
                .execute("INSERTE INTO Client VALUES (idPersonne, numeroCarteCredit, typeCarteCredit, cvv, 
                expMois, expAnnee, adresse, ville, province,
                              idAbonnement); ");  
    } catch (SQLException e) {
        logger.debug(e);
    }

    id,nomFamille,prenom,courriel,tel,
                             anniv,adresse,ville,province,
                             codePostal,carte,noCarte, 
                             expMois,expAnnee,motDePasse,forfait

      

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
      
      lecture.lecturePersonnes(args[0]);
      lecture.lectureFilms(args[1]);
      lecture.lectureClients(args[2]);
   }
}
