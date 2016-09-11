/*
 * File: Main.java
 * Names: Alex, Mike, Vivek
 * Class: CS361
 * Project 1
 * Date: September 7, 2016
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;


public class Main extends Application {

    private MidiPlayer midiPlayer;
    private int VOLUME = 50;
    private int CHANNEL = 0;

    public String createDialogBox() {
        TextInputDialog dialog = new TextInputDialog("60");
        dialog.setTitle("Starting Note");
        dialog.setHeaderText("Give me a starting note (0-115)");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        else {
            return "";
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.midiPlayer = new MidiPlayer(1, 60);

        Menu mainMenu = new Menu("File");
        MenuItem menuItem = new MenuItem("Exit");
        mainMenu.getItems().add(menuItem);
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(mainMenu);

        Button playButton = new Button();
        playButton.setText("Play Scale");
        playButton.setStyle("-fx-base: #008000");
        playButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                String resultString = createDialogBox();
                for (int i=0; i<8; i++) {
                    midiPlayer.addNote(Integer.valueOf(resultString) + i, VOLUME, i, 1, CHANNEL, 0);
                }
                midiPlayer.play();
            }
        });

        Button stopButton = new Button();
        stopButton.setText("Stop Playing");
        stopButton.setStyle("-fx-base: #FF0000");
        stopButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                midiPlayer.stop();
            }
        });

        HBox hbox = new HBox(8);
        hbox.getChildren().addAll(playButton, stopButton);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, hbox);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 250);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Closing Application");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}