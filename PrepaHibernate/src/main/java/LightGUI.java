import Service.LocationFilmService;
import entity.PersonneDossier;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.stream.Collectors;

import entity.*;

public class LightGUI extends JFrame {
    private JTextField usernameField, passwordField;
    private JButton connectButton;
    private JList<String> movieList;
    private DefaultListModel<String> movieListModel;
    private JTextArea movieDetailsArea;
    private JButton rentButton;
    private HibernateConfig hibernateConfig;
    private SessionFactory sessionFactory;
    private DataBroker dataBroker;
    private PersonneDossier personneDossier;
    private Client client;
    private Map<String, Film> titleToFilm;

    private Map<String, Film> filtreFilm;

    private JTextField searchField;

    private JButton searchButton;


    private void createSearchPopup() {
        JDialog searchDialog = new JDialog(this, "Search Films", true);
        searchDialog.setSize(400, 300);
        searchDialog.setLayout(new GridLayout(9, 2, 10, 10));

        JTextField titreField = new JTextField();
        JTextField anneeMinField = new JTextField();
        JTextField anneeMaxField = new JTextField();
        JTextField paysField = new JTextField();
        JTextField langueField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField realisateurField = new JTextField();
        JTextField acteurField = new JTextField();

        searchDialog.add(new JLabel("Titre:"));
        searchDialog.add(titreField);

        searchDialog.add(new JLabel("Année Min:"));
        searchDialog.add(anneeMinField);

        searchDialog.add(new JLabel("Année Max:"));
        searchDialog.add(anneeMaxField);

        searchDialog.add(new JLabel("Pays:"));
        searchDialog.add(paysField);

        searchDialog.add(new JLabel("Langue:"));
        searchDialog.add(langueField);

        searchDialog.add(new JLabel("Genre:"));
        searchDialog.add(genreField);

        searchDialog.add(new JLabel("Réalisateur:"));
        searchDialog.add(realisateurField);

        searchDialog.add(new JLabel("Acteur:"));
        searchDialog.add(acteurField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = titreField.getText();
                String anneeMin = anneeMinField.getText();
                String anneeMax = anneeMaxField.getText();
                String pays = paysField.getText();
                String langue = langueField.getText();
                String genre = genreField.getText();
                String realisateur = realisateurField.getText();
                String acteur = acteurField.getText();

                List<Film> result = dataBroker.consultationFilms(titre, anneeMin, anneeMax, pays, langue, genre, realisateur, acteur);
                searchDialog.dispose();

                movieListModel.clear();
                for ( Film film : result) {
                    movieListModel.addElement(film.getTitre());
                    titleToFilm.put(film.getTitre(), film);
                }
            }
        });
        searchDialog.getContentPane().add(searchButton);
        searchDialog.setVisible(true);
    }

    public LightGUI() {
        setTitle("Movie Rental App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);
        gbc.gridy++;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);
        gbc.gridy++;
        connectButton = new JButton("Connect");
        loginPanel.add(connectButton, gbc);





        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel moviePanel = new JPanel(new BorderLayout());
        JPanel detailsPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());


        movieListModel = new DefaultListModel<>();
        movieList = new JList<>(movieListModel);
        JScrollPane movieScrollPane = new JScrollPane(movieList);
        moviePanel.add(new JLabel("Movies:"), BorderLayout.NORTH);
        moviePanel.add(movieScrollPane, BorderLayout.CENTER);
        movieDetailsArea = new JTextArea();
        movieDetailsArea.setEditable(false);
        JPanel rentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rentButton = new JButton("Rent");
        rentPanel.add(rentButton);
        rentPanel.add(new JLabel("Number of copies to rent:"));
        detailsPanel.add(rentPanel, BorderLayout.SOUTH);
        detailsPanel.add(new JLabel("Movie Details:"), BorderLayout.NORTH);
        detailsPanel.add(new JScrollPane(movieDetailsArea), BorderLayout.CENTER);
        mainPanel.add(moviePanel, BorderLayout.WEST);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new CardLayout());
        getContentPane().add(loginPanel, "LOGIN");
        getContentPane().add(mainPanel, "MAIN");

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                personneDossier = dataBroker.connectionUtilisateur(username, password);
                if (personneDossier != null) {
                    CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                    cardLayout.show(getContentPane(), "MAIN");
                    populateMovieList();
                } else {
                    JOptionPane.showMessageDialog(LightGUI.this, "Invalid username or password");
                }
                client = dataBroker.retourneClient(personneDossier.getIdpersonne());
                if (client == null) {
                    JOptionPane.showMessageDialog(LightGUI.this, "You are not a client");
                }


            }
        });

        movieList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = movieList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedMovie = movieListModel.getElementAt(selectedIndex);
                    Film film = titleToFilm.get(selectedMovie);
                    dataBroker.loadAllFilm(film);
                    StringBuilder s = new StringBuilder();
                    s.append("Title: ");
                    s.append(selectedMovie);
                    s.append("\n");
                    s.append("Year: ");
                    s.append(film.getAnnee());
                    s.append("\n");
                    s.append("Duration: ");
                    s.append(film.getDuree());
                    s.append("min\n");
                    s.append("Resume: ");
                    s.append(film.getResume());
                    s.append("\n");
                    s.append("Bande annonces: ");
                    s.append(film.getBandesannonces());
                    s.append("\n");
                    s.append("Examplaires: ");
                    dataBroker.loadExemplaireFilm(film);
                    s.append(film.getExemplairesFilm().size());
                    movieDetailsArea.setText(s.toString());

                }
            }
        });

        rentButton.addActionListener(e -> {
            // TODO Check if there is enough movies copies available and change message depending on result
            // TODO Update details for available movie copies if rented
            int selectedIndex = movieList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedMovie = movieListModel.getElementAt(selectedIndex);
                Film film = titleToFilm.get(selectedMovie);
                dataBroker.loadExemplaireFilm(film);
                List<Exemplairefilm> examplaires = film.getExemplairesFilm();

                if(examplaires.isEmpty())
                {
                    JOptionPane.showMessageDialog(LightGUI.this, "No available copies");
                    return;
                }
                LocationFilmService location_service = new LocationFilmService(sessionFactory);
                location_service.locationFilmPersonneDossier(client, film);
                //JOptionPane.showMessageDialog(LightGUI.this, "You rented a copie of " + selectedMovie );
            } else {
                JOptionPane.showMessageDialog(LightGUI.this, "Please select a movie to rent");
            }
        });

        // Creating the search button
        searchButton = new JButton("Search");
        searchPanel.add(searchButton , BorderLayout.EAST);

        mainPanel.add(searchPanel, BorderLayout.NORTH);


        // Adding action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createSearchPopup();
            }
        });

        hibernateConfig = new HibernateConfig();
        sessionFactory = hibernateConfig.getSessionFactory();
        dataBroker = new DataBroker();
    }

    private void populateMovieList() {
//        // Fake movie list
//        String[] movies = {"Movie 1", "Movie 2", "Movie 3", "Movie 4", "Movie 5"};
//        for (String movie : movies) {
//            movieListModel.addElement(movie);
//        }
        titleToFilm = new HashMap<>();
        filtreFilm = new HashMap<>();
        List<Film> result = dataBroker.getAllFilms();
        String[] movies = {};
        movieListModel.clear();
        for ( Film film : result) {
//            dataFacade.loadAllFilm(film);
            movieListModel.addElement(film.getTitre());
            titleToFilm.put(film.getTitre(), film);
        }
    }
}
