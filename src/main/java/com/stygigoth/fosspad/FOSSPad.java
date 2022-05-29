package com.stygigoth.fosspad;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class FOSSPad extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        final File[] openFile = new File[1];

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);

        Menu file = new Menu("File");
        MenuItem m0 = new MenuItem("New");
        MenuItem m1 = new MenuItem("Open");
        MenuItem m2 = new MenuItem("Save");
        MenuItem m3 = new MenuItem("Save As");
        MenuItem m4 = new MenuItem("Exit");
        m0.setOnAction(event -> {
            openFile[0] = null;
            textArea.setText("");
        });
        m1.setOnAction(event -> {
            fileChooser.setTitle("Select file to edit");
            openFile[0] = fileChooser.showOpenDialog(stage);
            if (openFile[0].exists()) openFile(openFile[0], stage);
        });
        m2.setOnAction(event -> {
            if (openFile[0] != null) {
                saveFile(openFile[0], stage, openFile[0].getAbsolutePath());
            } else {
                saveFile(new File("Untitled.txt"), stage, "Untitled.txt");
            }
        });
        m3.setOnAction(event -> {
            fileChooser.setTitle("Save as");
            fileChooser.setInitialFileName("Untitled.txt");
            File file1 = fileChooser.showSaveDialog(stage);
            saveFile(openFile[0], stage, file1.getAbsolutePath());
        });
        m4.setOnAction(event -> stage.close());
        file.getItems().addAll(m0, m1, m2, m3, m4);

        File file1 = new File("Untitled.txt");
        try {
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openFile[0] = file1;


        Menu format = new Menu("Format");
        MenuItem m5 = new MenuItem("Toggle Word Wrapping");
        m5.setOnAction(event -> textArea.setWrapText(!textArea.isWrapText()));
        format.getItems().add(m5);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, format);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar, textArea);

        Scene scene = new Scene(vBox, 1080, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("textarea.css")).toExternalForm());
        textArea.setPrefHeight(scene.getHeight() - menuBar.getHeight());
        textArea.setStyle("-fx-text-fill: white ; -fx-background-color: white ;");

        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                textArea.setPrefHeight(scene.getHeight() - menuBar.getHeight()));

        stage.setTitle("FOSSPad");
        stage.setScene(scene);


        stage.show();
    }

    private void openFile(File file, Stage stage) {
        Scene scene = stage.getScene();
        TextArea textArea = new TextArea();

        for (Node node : scene.getRoot().getChildrenUnmodifiable()) {
            if (node instanceof TextArea) {
                textArea = (TextArea) node;
                break;
            }
        }

        textArea.setText(readFile(file));
    }
    private void saveFile(File file, Stage stage, String title) {
        try {
            if (!(file.getName().equals(title))) {
                file = new File(title);
            }
            FileWriter writer = new FileWriter(file);
            TextArea textArea = (TextArea) stage.getScene().getRoot().getChildrenUnmodifiable().get(1);
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private String readFile(File file) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            StringBuilder out = new StringBuilder();

            while ((st = br.readLine()) != null) {
                out.append(st).append("\n");
            }

            return out.toString();
        } catch (IOException e) {
            System.out.println(e);
        }
        return "";
    }
}