import javax.persistence.*;
import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void showFirst10ExemplaireFilms(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exemplairefilm> result = session.createQuery("select e from Exemplairefilm e").setMaxResults(10).list();
        for ( Exemplairefilm user : result) {
            System.out.println( "Exemplaire :" + user.getCodeexemplaire() + " : " + user.getFilm().getTitre() );
        }
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) throws Exception {
        HibernateConfig hibernateConfig = new HibernateConfig();
        SessionFactory sessionFactory = hibernateConfig.getSessionFactory();

        showFirst10ExemplaireFilms(sessionFactory);
    }
}
