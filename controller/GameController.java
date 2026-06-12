package fr.uge.but.schtroumpf.controller;

import fr.uge.but.schtroumpf.model.Event;
import fr.uge.but.schtroumpf.model.EventHistory;
import fr.uge.but.schtroumpf.model.ResourceType;
import fr.uge.but.schtroumpf.model.SaveManager;
import fr.uge.but.schtroumpf.model.SmurfVillage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameController {

    @FXML private Label lblBaies, lblSalse, lblOr, lblOutils, lblMoral, lblDefense, lblSavoir;
    @FXML private ProgressBar pbBaies, pbSalse, pbOr, pbOutils, pbMoral, pbDefense, pbSavoir;
    @FXML private Label lblTour, lblEventName, lblEventDesc, lblEventEffects;
    @FXML private Label lblActionInfo, lblActionCount, lblSauvegarde;
    @FXML private VBox vboxActions, vboxJournal, eventCard;
    @FXML private Button btnValider;
    @FXML private Label lblPhase1, lblPhase2, lblPhase3, lblPhase4, lblPhase5;

    private SmurfVillage village;
    private EventHistory history;
    private int currentTurn = 1;
    private int actionsLeft = 2;
    private int actionsSelected = 0;
    private fr.uge.but.schtroumpf.model.Character selectedCharacter;

    public void initGame(SmurfVillage village, int startTurn) {
        this.village = village;
        this.history = new EventHistory();
        this.currentTurn = startTurn;
        startTurn();
    }

    private void startTurn() {
        actionsLeft = 2;
        actionsSelected = 0;
        lblTour.setText("Tour " + currentTurn + " / 12");
        lblActionCount.setText("0 action sélectionnée sur 2");

        // Phase 1
        village.produceResources();
        updateResources();

        // Phase 2
        Event event = Event.createRandom();
        event.applyEffects(village);
        history.addEvent(event);
        updateResources();
        showEvent(event);

        // Phase 3
        showCharacters();
        updatePhaseIndicator(3);
    }

    private void updateResources() {
        updateBar(lblBaies, pbBaies, ResourceType.BERRIES);
        updateBar(lblSalse, pbSalse, ResourceType.SMURFBERRY);
        updateBar(lblOr, pbOr, ResourceType.GOLD);
        updateBar(lblOutils, pbOutils, ResourceType.TOOLS);
        updateBar(lblMoral, pbMoral, ResourceType.MORALE);
        updateBar(lblDefense, pbDefense, ResourceType.DEFENSE);
        updateBar(lblSavoir, pbSavoir, ResourceType.KNOWLEDGE);
    }

    private void updateBar(Label lbl, ProgressBar pb, ResourceType type) {
        int val = village.getResource(type);
        lbl.setText(val + "/10");
        pb.setProgress(val / 10.0);
    }

    private void showEvent(Event event) {
        lblEventName.setText(event.name());
        lblEventDesc.setText(event.description());

        StringBuilder effects = new StringBuilder();
        event.effects().forEach((r, v) ->
            effects.append((v > 0 ? "+" : "").concat(v + " " + translateResource(r) + "  "))
        );
        lblEventEffects.setText("Effets : " + effects.toString());

        // Couleur carte selon type événement
        boolean negative = event.effects().values().stream().anyMatch(v -> v < 0);
        boolean positive = event.effects().values().stream().anyMatch(v -> v > 0);
        boolean mixed = negative && positive;

        eventCard.getStyleClass().clear();
        if (event.effects().isEmpty()) {
            eventCard.getStyleClass().add("event-card-neutral");
            lblEventName.setStyle("-fx-text-fill: #FAEEDA;");
            lblEventDesc.setStyle("-fx-text-fill: #D4B896; -fx-wrap-text: true;");
            lblEventEffects.setStyle("-fx-text-fill: #B5A68E;");
        } else if (negative && !positive) {
            eventCard.getStyleClass().add("event-card-danger");
            lblEventName.setStyle("-fx-text-fill: #F5C4C4;");
            lblEventDesc.setStyle("-fx-text-fill: #E8A0A0; -fx-wrap-text: true;");
            lblEventEffects.setStyle("-fx-text-fill: #F5C4C4;");
        } else {
            eventCard.getStyleClass().add("event-card-positive");
            lblEventName.setStyle("-fx-text-fill: #9FE1CB;");
            lblEventDesc.setStyle("-fx-text-fill: #5DCAA5; -fx-wrap-text: true;");
            lblEventEffects.setStyle("-fx-text-fill: #9FE1CB;");
        }

        // Journal
        addJournalEntry("Tour " + currentTurn, event.name(),
            effects.toString().trim(), negative);
    }

    /** Affiche les personnages et leurs actions. */
    private void showCharacters() {
        vboxActions.getChildren().clear();
        lblActionInfo.setText("ACTIONS DU CONSEIL — CHOISISSEZ 2 PERSONNAGES");
        btnValider.setVisible(false);

        // Grille 2x2
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        java.util.List<fr.uge.but.schtroumpf.model.Character> council = village.getCouncil();
        String[] imageFiles = {"GrandSmurf.png", "Handyman.png",
                               "Gluttonous.png", "Smurfette.png"};
        String[] roles = {"Sagesse · Leadership", "Outils · Défense",
                          "Baies · Moral", "Diplomatie · Moral"};

        for (int i = 0; i < council.size(); i++) {
            fr.uge.but.schtroumpf.model.Character c = council.get(i);
            VBox card = new VBox(6);
            card.getStyleClass().add("char-card");
            card.setPrefWidth(430);

            // En-tête : avatar + nom + rôle
            HBox header = new HBox(8);
            header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Image img = new Image(
                getClass().getResourceAsStream(
                    "/fr/uge/but/schtroumpf/view/" + imageFiles[i]
                )
            );
            ImageView avatar = new ImageView(img);
            avatar.setFitHeight(40);
            avatar.setPreserveRatio(true);
            avatar.setSmooth(true);
            Rectangle clip = new Rectangle(72, 40);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            avatar.setClip(clip);

            VBox nameBox = new VBox(1);
            Label name = new Label(c.getName());
            name.getStyleClass().add("char-name");
            Label role = new Label(roles[i]);
            role.getStyleClass().add("char-role");
            nameBox.getChildren().addAll(name, role);

            header.getChildren().addAll(avatar, nameBox);
            card.getChildren().add(header);

            // Actions
            String[] actions = c.getAvailableActions(village);
            for (int j = 0; j < actions.length; j++) {
                final int actionIndex = j;
                final fr.uge.but.schtroumpf.model.Character character = c;
                Button btn = new Button(actions[j]);
                btn.setPrefWidth(410);
                btn.setWrapText(true);

                if (actions[j].contains("INDISPONIBLE")) {
                    btn.getStyleClass().add("action-button-disabled");
                    btn.setDisable(true);
                } else {
                    btn.getStyleClass().add("action-button");
                    btn.setOnAction(e -> {
                        selectedCharacter = character;
                        executeAction(actionIndex);
                    });
                }
                card.getChildren().add(btn);
            }

            grid.add(card, i % 2, i / 2);
        }

        vboxActions.getChildren().add(grid);
    }

    private void showActions(fr.uge.but.schtroumpf.model.Character character) {
        this.selectedCharacter = character;
        vboxActions.getChildren().clear();
        lblActionInfo.setText("Actions de " + character.getName() +
            " — choisissez une action");

        String[] actions = character.getAvailableActions(village);
        for (int i = 0; i < actions.length; i++) {
            final int index = i;
            Button btn = new Button(actions[i]);
            btn.setPrefWidth(410);
            btn.setWrapText(true);

            if (actions[i].contains("INDISPONIBLE")) {
                btn.getStyleClass().add("action-button-disabled");
                btn.setDisable(true);
            } else {
                btn.getStyleClass().add("action-button");
                btn.setOnAction(e -> executeAction(index));
            }
            vboxActions.getChildren().add(btn);
        }

        // Bouton retour
        Button btnBack = new Button("← Retour au choix de personnage");
        btnBack.getStyleClass().add("btn-secondary");
        btnBack.setStyle("-fx-font-size: 11;");
        btnBack.setOnAction(e -> showCharacters());
        vboxActions.getChildren().add(btnBack);
    }

    private void executeAction(int actionIndex) {
        String actionName = selectedCharacter.getAvailableActions(village)[actionIndex];
        selectedCharacter.performAction(actionIndex, village);
        updateResources();
        actionsLeft--;
        actionsSelected++;
        lblActionCount.setText(actionsSelected + " action(s) sélectionnée(s) sur 2");

        addJournalEntry("Tour " + currentTurn,
            selectedCharacter.getName(), actionName, false);

        if (actionsLeft > 0) {
            showCharacters();
        } else {
            vboxActions.getChildren().clear();
            lblActionInfo.setText("Toutes les actions sont effectuées — validez le tour");
            btnValider.setVisible(true);
        }
    }

    @FXML
    private void handleValider() {
        // Phase 4
        updatePhaseIndicator(4);
        village.consumeResources();
        updateResources();

        // Phase 5
        updatePhaseIndicator(5);
        village.checkCrises(currentTurn);
        updateResources();

        // Affichage crises
        if (!village.getActiveCrises().isEmpty()) {
            for (var crisis : village.getActiveCrises()) {
                addJournalEntry("Tour " + currentTurn,
                    "CRISE", crisis.getDescription(), true);
            }
        }

        // Défaite ?
        if (village.getActiveCrisesCount() >= 3) {
            showEnd(false);
            return;
        }

        SaveManager.save(village, currentTurn);
        lblSauvegarde.setText("Sauvegarde au tour " + currentTurn);
        currentTurn++;
        if (currentTurn > 12) {
            showEnd(true);
            return;
        }

        startTurn();
    }

    private void addJournalEntry(String turn, String title, String detail, boolean negative) {
        VBox entry = new VBox(2);
        Label lblTurn = new Label(turn);
        lblTurn.getStyleClass().add("journal-turn");

        Label lblContent = new Label(title + "\n" + detail);
        lblContent.getStyleClass().add("journal-entry");
        if (negative) {
            lblContent.setStyle("-fx-text-fill: #F0997B;");
        } else {
            lblContent.setStyle("-fx-text-fill: #9FE1CB;");
        }
        lblContent.setWrapText(true);

        entry.getChildren().addAll(lblTurn, lblContent);
        vboxJournal.getChildren().add(0, entry);
    }

    private void updatePhaseIndicator(int activePhase) {
        Label[] phases = {lblPhase1, lblPhase2, lblPhase3, lblPhase4, lblPhase5};
        String[] names = {"1. Production", "2. Événement", "3. Actions ◀",
                          "4. Consomm.", "5. Crises"};
        for (int i = 0; i < 5; i++) {
            phases[i].setText(names[i]);
            if (i + 1 == activePhase) {
                phases[i].getStyleClass().setAll("phase-step-active");
            } else {
                phases[i].getStyleClass().setAll("phase-step");
            }
        }
    }

    private void showEnd(boolean victory) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                    "/fr/uge/but/schtroumpf/view/EndView.fxml"
                )
            );
            SaveManager.deleteSave();
            BorderPane root = loader.load();
            EndController controller = loader.getController();
            controller.initEnd(village, victory);

            Stage stage = (Stage) btnValider.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 750);
            scene.getStylesheets().add(
                getClass().getResource(
                    "/fr/uge/but/schtroumpf/view/application.css"
                ).toExternalForm()
            );
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Traduction des noms de ressources. */
    private String translateResource(ResourceType type) {
        return switch (type) {
            case BERRIES    -> "Baies";
            case SMURFBERRY -> "Salsepareille";
            case GOLD       -> "Or";
            case TOOLS      -> "Outils";
            case MORALE     -> "Moral";
            case DEFENSE    -> "Defense";
            case KNOWLEDGE  -> "Savoir";
        };
    }

}