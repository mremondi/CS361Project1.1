/*
 * File: Main.java
 * Names: Alex, Mike, Vivek
 * Class: CS361
 * Project 1
 * Date: September 7, 2016
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * This class creates a small application that asks the user for a starting note and
 * uses a midi player to play that note's scale
 *
 * @author Alex Rinker
 * @author Vivek Sah
 * @author Mike Remondi
 */
public class Main extends Application {
    private MidiPlayer midiPlayer;
    private int VOLUME = 50;
    private int CHANNEL = 0;
    private int BEATS_PER_MINUTE = 100;
    private int WINDOW_WIDTH = 300;
    private int WINDOW_HEIGHT = 250;
    private Menu mainMenu;

    /**
     * Creates a dialog box and returns the input string provided by the user.
     *
     * @param title         the String value for the title of the dialog box
     * @param headerText    the String value for the header prompt
     * @param defaultString the String value for the default text input field
     * @return              user input String
     */
    public String createDialogBox(String title, String headerText, String defaultString) {
        TextInputDialog dialog = new TextInputDialog(defaultString);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

        Optional<String> result = dialog.showAndWait();
        return result.get();
    }

    /**
     * Creates a button with the given text and style and returns it.
     *
     * @param text  A string value for the button text
     * @param style A string value for the button style
     * @return      The button itself
     */
    public Button createButton(String text, String style){
        Button button = new Button();
        button.setText(text);
        button.setStyle(style);
        return button;
    }

    /**
     * Plays midi starting from the given value.
     *
     * @param startNote int value for starting note
     */
    public void playMidi(int startNote){
        stopMidi();
        for (int i = 0; i < 8; i++) {
            midiPlayer.addNote(Integer.valueOf(startNote) + i, VOLUME, i, 1, CHANNEL, 0);
            midiPlayer.addNote(Integer.valueOf(startNote) + 7 - i, VOLUME, 7 + i, 1, CHANNEL, 0);
        }
        midiPlayer.play();
    }

    /**
     * Stops the midi player and clears its sequence
     */
    public void stopMidi(){
        midiPlayer.stop();
        midiPlayer.clear();
    }

    /**
     * Initializes the midiplayer and sets up the stage with primary components
     *
     * @param primaryStage Stage for main application
     */
    @Override
    public void start(Stage primaryStage) {
        this.midiPlayer = new MidiPlayer(1, BEATS_PER_MINUTE);

        this.mainMenu = new Menu("File");
        MenuItem menuItem = new MenuItem("Exit");
        this.mainMenu.getItems().add(menuItem);
        menuItem.setOnAction( event -> { System.exit(0); });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(mainMenu);

        Button playButton = createButton("Play Scale", "-fx-base: #008000");
        playButton.addEventHandler(ActionEvent.ACTION, event -> {
            int startNote = Integer.valueOf(createDialogBox("Starting Note", "Give Me A Starting Note", "60"));
            playMidi(startNote);
        });

        Button stopButton = createButton("Stop Playing", "-fx-base: #FF0000");
        stopButton.addEventHandler(ActionEvent.ACTION, event -> {
            stopMidi();
        });

        HBox hbox = new HBox(8);
        hbox.getChildren().addAll(playButton, stopButton);
        hbox.setAlignment(Pos.CENTER);

        BorderPane masterPane = new BorderPane();
        masterPane.setTop(menuBar);
        masterPane.setCenter(hbox);
        Scene scene = new Scene(masterPane, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {System.exit(0);});
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}