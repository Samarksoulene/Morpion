import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class morpion extends JFrame {
    private JButton[][] boutons;
    private boolean tourJoueurX;
    private JLabel etiquetteStatut;

    public morpion() {
        setTitle("Morpion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panneau pour la grille de jeu
        JPanel panneauJeu = new JPanel(new GridLayout(3, 3));
        panneauJeu.setBackground(Color.PINK); // Fond rose pour la grille de jeu
        boutons = new JButton[3][3];
        tourJoueurX = true;

        // Création des boutons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boutons[i][j] = new JButton("");
                boutons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                final int ligne = i;
                final int colonne = j;
                boutons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boutonClique(ligne, colonne);
                    }
                });
                panneauJeu.add(boutons[i][j]);
            }
        }

        // Étiquette pour afficher le statut du jeu
        etiquetteStatut = new JLabel("Au tour du joueur X");
        etiquetteStatut.setHorizontalAlignment(JLabel.CENTER);
        etiquetteStatut.setFont(new Font("Arial", Font.BOLD, 20));

        // Bouton pour recommencer
        JButton boutonRecommencer = new JButton("Nouvelle partie");
        boutonRecommencer.addActionListener(e -> nouvellePartie());

        add(etiquetteStatut, BorderLayout.NORTH);
        add(panneauJeu, BorderLayout.CENTER);
        add(boutonRecommencer, BorderLayout.SOUTH);

        // Fond rose pour la fenêtre principale
        getContentPane().setBackground(Color.PINK);

        setLocationRelativeTo(null);
    }

    private void boutonClique(int ligne, int colonne) {
        if (boutons[ligne][colonne].getText().equals("")) {
            if (tourJoueurX) {
                boutons[ligne][colonne].setText("X");
                etiquetteStatut.setText("Au tour du joueur O");
            } else {
                boutons[ligne][colonne].setText("O");
                etiquetteStatut.setText("Au tour du joueur X");
            }
            
            if (verifierVictoire()) {
                String joueurGagnant = tourJoueurX ? "X" : "O";
                etiquetteStatut.setText("The player " + joueurGagnant + " win!");
                desactiverBoutons();
            } else if (grillePleine()) {
                etiquetteStatut.setText("Match nul!");
            }
            
            tourJoueurX = !tourJoueurX;
        }
    }

    private boolean verifierVictoire() {
        // Vérification des lignes
        for (int i = 0; i < 3; i++) {
            if (!boutons[i][0].getText().equals("") &&  
                boutons[i][0].getText().equals(boutons[i][1].getText()) &&
                boutons[i][0].getText().equals(boutons[i][2].getText())) {
                return true;
            }
        }

        // Vérification des colonnes
        for (int j = 0; j < 3; j++) {
            if (!boutons[0][j].getText().equals("") &&
                boutons[0][j].getText().equals(boutons[1][j].getText()) &&
                boutons[0][j].getText().equals(boutons[2][j].getText())) {
                return true;
            }
        }

        // Vérification des diagonales
        if (!boutons[0][0].getText().equals("") &&
            boutons[0][0].getText().equals(boutons[1][1].getText()) &&
            boutons[0][0].getText().equals(boutons[2][2].getText())) {
            return true;
        }

        if (!boutons[0][2].getText().equals("") &&
            boutons[0][2].getText().equals(boutons[1][1].getText()) &&
            boutons[0][2].getText().equals(boutons[2][0].getText())) {
            return true;
        }

        return false;
    }

    private boolean grillePleine() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boutons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void desactiverBoutons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boutons[i][j].setEnabled(false);
            }
        }
    }

    private void nouvellePartie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boutons[i][j].setText("");
                boutons[i][j].setEnabled(true);
            }
        }
        tourJoueurX = true;
        etiquetteStatut.setText("Au tour du joueur X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            morpion jeu = new morpion();
            jeu.setVisible(true);
        });
    }
}