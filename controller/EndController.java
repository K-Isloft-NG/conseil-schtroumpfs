package fr.uge.but.schtroumpf.controller;

import fr.uge.but.schtroumpf.model.ResourceType;
import fr.uge.but.schtroumpf.model.SmurfVillage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndController {

    @FXML private Label lblResultat, lblResultatSub;
    @FXML private Label lblScore, lblCommentaire;
    @FXML private Label lblBaies, lblSalse, lblOr, lblOutils, lblMoral, lblDefense, lblSavoir;
    @FXML private VBox bannerBox;

    public void initEnd(SmurfVillage village, boolean victory) {
        if (victory) {
            lblResultat.setText("Victoire !");
            lblResultat.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #9FE1CB;");
            lblResultatSub.setText("Le village a survécu 12 mois. Bravo !");
            lblResultatSub.setStyle("-fx-font-size: 13; -fx-text-fill: #5DCAA5;");
            bannerBox.getStyleClass().setAll("victory-banner");
        } else {
            lblResultat.setText("Défaite !");
            lblResultat.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #F5C4C4;");
            lblResultatSub.setText("3 crises simultanées. Le village est perdu.");
            lblResultatSub.setStyle("-fx-font-size: 13; -fx-text-fill: #E8A0A0;");
            bannerBox.getStyleClass().setAll("defeat-banner");
        }

        int score = village.computeScore();
        lblScore.setText(score + " / 700");
        lblCommentaire.setText(getScoreLabel(score));

        lblBaies.setText(String.valueOf(village.getResource(ResourceType.BERRIES)));
        lblSalse.setText(String.valueOf(village.getResource(ResourceType.SMURFBERRY)));
        lblOr.setText(String.valueOf(village.getResource(ResourceType.GOLD)));
        lblOutils.setText(String.valueOf(village.getResource(ResourceType.TOOLS)));
        lblMoral.setText(String.valueOf(village.getResource(ResourceType.MORALE)));
        lblDefense.setText(String.valueOf(village.getResource(ResourceType.DEFENSE)));
        lblSavoir.setText(String.valueOf(village.getResource(ResourceType.KNOWLEDGE)));
    }

    private String getScoreLabel(int score) {
        if (score > 420) return "Victoire triomphale !";
        if (score >= 280) return "Victoire modeste — bonne gestion.";
        return "Victoire de justesse...";
    }

    @FXML
    private void handleRejouer() {
        try {
            BorderPane root = FXMLLoader.load(
                getClass().getResource("/fr/uge/but/schtroumpf/view/WelcomeView.fxml")
            );
            Stage stage = (Stage) lblResultat.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 750);
            scene.getStylesheets().add(
                getClass().getResource("/fr/uge/but/schtroumpf/view/application.css").toExternalForm()
            );
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleQuitter() {
        Stage stage = (Stage) lblResultat.getScene().getWindow();
        stage.close();
    }
}
