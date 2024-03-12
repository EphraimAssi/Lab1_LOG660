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
    private DataFacade dataFacade;
    private PersonneDossier personneDossier;
    private Client client;
    private Map<String, Film> titleToFilm;

    private Map<String, Film> filtreFilm;

    private JTextField searchField;

    private JButton searchButton;

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
                personneDossier = dataFacade.connectionUtilisateur(username, password);
                if (personneDossier != null) {
                    CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                    cardLayout.show(getContentPane(), "MAIN");
                    populateMovieList();
                } else {
                    JOptionPane.showMessageDialog(LightGUI.this, "Invalid username or password");
                }
                client = dataFacade.retourneClient(personneDossier.getIdpersonne());
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
                    dataFacade.loadAllFilm(film);
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
                    dataFacade.loadExemplaireFilm(film);
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
                dataFacade.loadExemplaireFilm(film);
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



        searchField = new JTextField(20);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Creating the search button
        searchButton = new JButton("Search");
        searchPanel.add(searchButton , BorderLayout.EAST);

        mainPanel.add(searchPanel, BorderLayout.NORTH);


        // Adding action listener to the search button
        searchField.addKeyListener(new KeyAdapter() {
            private int spaceCount = 0;
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spaceCount++; // Increment space count on space key release
                    String currentText = searchField.getText();
                    int textLength = currentText.length();
                    System.out.println("Text:" + currentText);

                    // Check if the last two characters are spaces (indicating two consecutive spaces)
                    if (spaceCount >= 2 && textLength >= 2 && currentText.substring(textLength - 2).equals("  ")) {
                        if (!currentText.contains("titre: ")) {
                            searchField.setText("titre:" + currentText.trim());
                        } else if (!currentText.contains("date:")) {
                            // Remove the last two spaces before adding "releaseDate:"
                            searchField.setText(currentText.trim() + "date:");
                        }
                        spaceCount = 0; // Reset space count
                        System.out.println("spacecount: " + spaceCount);
                    }
                } else {
                    spaceCount = 0; // Reset space count if any other key is released
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                String[] parts = searchText.split(":", 2);
                List<Film> films = titleToFilm.values().stream().collect(Collectors.toList());

                if (!searchText.isEmpty()) {
                    movieListModel.clear();
                    filtreFilm.clear();
                }
                if (searchText.isEmpty()) {
                    for ( Film film : titleToFilm.values()) {
                        movieListModel.addElement(film.getTitre());
                    }
                }

                if (parts.length == 2) {
                    String attribute = parts[0];
                    String value = parts[1];

                    switch (attribute) {
                        case "titre":
                            for (Film film : films) {
                                System.out.println(film.getTitre());
                                System.out.println("value: titre :" + value);
                                if (film.getTitre().toLowerCase().contains(value.toLowerCase())) {
                                    System.out.println("Titre qui match : ");
                                    movieListModel.addElement(film.getTitre());
                                    filtreFilm.put(film.getTitre(), film);
                                }
                            }
                            break;
                        case "date":
                            for (Film film : films) {
                                System.out.println("Date :" + film.getAnnee());
                                System.out.println("value: date :" + value);
                                if (film.getAnnee().toString().contains(value)){
                                    System.out.println("Date qui match : ");
                                    movieListModel.addElement(film.getTitre());
                                    filtreFilm.put(film.getTitre(), film);
                                }
                            }
                            break;
                        default:
                            System.out.println("Unknown attribute");
                    }
                } else {
                    System.out.println("Invalid search format. Use attribute:value.");
                }

                for (Film filmfiltre : filtreFilm.values()) {
                    System.out.println("film apres filtre : " + filtreFilm.get(filmfiltre.getTitre()));
                }
            }
        });




        hibernateConfig = new HibernateConfig();
        sessionFactory = hibernateConfig.getSessionFactory();
        dataFacade = new DataFacade();
    }

    private void populateMovieList() {
//        // Fake movie list
//        String[] movies = {"Movie 1", "Movie 2", "Movie 3", "Movie 4", "Movie 5"};
//        for (String movie : movies) {
//            movieListModel.addElement(movie);
//        }
        titleToFilm = new HashMap<>();
        filtreFilm = new HashMap<>();
        List<Film> result = dataFacade.getAllFilms();
        String[] movies = {};
        movieListModel.clear();
        for ( Film film : result) {
//            dataFacade.loadAllFilm(film);
            movieListModel.addElement(film.getTitre());
            titleToFilm.put(film.getTitre(), film);
        }
    }
}
