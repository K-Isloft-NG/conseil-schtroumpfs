package fr.uge.but.schtroumpf.controller;

import fr.uge.but.schtroumpf.model.Difficulty;
import fr.uge.but.schtroumpf.model.SaveManager;
import fr.uge.but.schtroumpf.model.SmurfVillage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML private ToggleButton btnEasy;
    @FXML private ToggleButton btnNormal;
    @FXML private ToggleButton btnHard;
    @FXML private Label diffLabel;
    @FXML private Button btnStart;

    private Difficulty selectedDifficulty = Difficulty.NORMAL;

    @FXML
    public void initialize() {
        applySelection(btnNormal, "Toutes les ressources demarrent a 5/10");
    }

    @FXML
    private void handleEasy() {
        selectedDifficulty = Difficulty.EASY;
        applySelection(btnEasy, "Toutes les ressources demarrent a 7/10");
    }

    @FXML
    private void handleNormal() {
        selectedDifficulty = Difficulty.NORMAL;
        applySelection(btnNormal, "Toutes les ressources demarrent a 5/10");
    }

    @FXML
    private void handleHard() {
        selectedDifficulty = Difficulty.HARD;
        applySelection(btnHard, "Toutes les ressources demarrent a 3/10");
    }

    private void applySelection(ToggleButton selected, String description) {
        btnEasy.getStyleClass().setAll("diff-unselected");
        btnNormal.getStyleClass().setAll("diff-unselected");
        btnHard.getStyleClass().setAll("diff-unselected");
        selected.getStyleClass().setAll("diff-selected");
        diffLabel.setText(description);
    }

    @FXML
    private void handleStart() {
        try {
            // Si une sauvegarde existe, proposer de reprendre
            if (SaveManager.saveExists()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Sauvegarde detectee");
                alert.setHeaderText("Une partie est en cours.");
                alert.setContentText("Voulez-vous reprendre la partie sauvegardee ?");

                var result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    SmurfVillage savedVillage = new SmurfVillage(selectedDifficulty);
                    int savedTurn = SaveManager.load(savedVillage);
                    loadGame(savedVillage, savedTurn);
                    return;
                }
            }

            // Nouvelle partie
            SmurfVillage village = new SmurfVillage(selectedDifficulty);
            loadGame(village, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGame(SmurfVillage village, int turn) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fr/uge/but/schtroumpf/view/GameView.fxml")
        );
        Parent root = loader.load();
        GameController controller = loader.getController();
        controller.initGame(village, turn);

        Stage stage = (Stage) btnStart.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            getClass().getResource(
                "/fr/uge/but/schtroumpf/view/application.css"
            ).toExternalForm()
        );
        stage.setScene(scene);
    }

    @FXML
    private void handleRules() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Regles du jeu");
        alert.setHeaderText("Conseil des Schtroumpfs — Regles");
        alert.setContentText(
            "Le jeu dure 12 tours (1 tour = 1 mois)\n" +
            "7 ressources bornees entre 0 et 10\n" +
            "Chaque tour : production, evenement, 2 actions, consommation, crises\n" +
            "Si une ressource tombe a 0 : crise declenchee\n" +
            "3 crises simultanees : defaite immediate\n" +
            "Score final = somme des ressources x 10 (max 700)"
        );
        alert.showAndWait();
    }
}