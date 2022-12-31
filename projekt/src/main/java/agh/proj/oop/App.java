package agh.proj.oop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class App extends Application {
    private int TILE_SIZE;
    private final BooleanProperty paused = new SimpleBooleanProperty(false);
    private SimulationEngine engine;
    private AbstractWorldMap map;
    private int width, height, mapVar, grassStart, grassEnergy, grassDaily, grassVar, animalStart, animalStartEnergy;
    private int animalBreedEnergy, mutationMin, mutationMax, genotypeLength, mutationVar;

    @Override
    public void start(Stage primaryStage) {
        // Create a button to apply the settings and show the grid scene
        TextField widthField = new TextField();
        TextField heightField = new TextField();
        TextField mapVarField = new TextField();
        TextField grassStartField = new TextField();
        TextField grassEnergyField = new TextField();
        TextField grassDailyField = new TextField();
        TextField grassVarField = new TextField();
        TextField animalStartField = new TextField();
        TextField animalStartEnergyField = new TextField();
        TextField animalBreedEnergyField = new TextField();
        TextField mutationMinField = new TextField();
        TextField mutationMaxField = new TextField();
        TextField mutationVarField = new TextField();
        TextField genotypeLengthField = new TextField();
        // Create a grid pane to hold the text fields
        GridPane gridPane = new GridPane();

        // Add the text fields to the grid pane
        gridPane.add(new Label("Width:"), 0, 0);
        gridPane.add(widthField, 1, 0);
        gridPane.add(new Label("Height:"), 0, 1);
        gridPane.add(heightField, 1, 1);
        gridPane.add(new Label("Map Variance (1 for Portal):"), 0, 2);
        gridPane.add(mapVarField, 1, 2);
        gridPane.add(new Label("Number of grasses at start:"), 0, 3);
        gridPane.add(grassStartField, 1, 3);
        gridPane.add(new Label("Energy from grass:"), 0, 4);
        gridPane.add(grassEnergyField, 1, 4);
        gridPane.add(new Label("Grass growing Daily:"), 0, 5);
        gridPane.add(grassDailyField, 1, 5);
        gridPane.add(new Label("Grass Variance (1 for Toxic):"), 0, 6);
        gridPane.add(grassVarField, 1, 6);
        gridPane.add(new Label("Number of Starting Animals:"), 0, 7);
        gridPane.add(animalStartField, 1, 7);
        gridPane.add(new Label("Animal Start Energy:"), 0, 8);
        gridPane.add(animalStartEnergyField, 1, 8);
        gridPane.add(new Label("Energy used for breeding:"), 0, 9);
        gridPane.add(animalBreedEnergyField, 1, 9);
        gridPane.add(new Label("Genotype Length:"), 0, 10);
        gridPane.add(genotypeLengthField, 1, 10);
        gridPane.add(new Label("Minimum number of mutations:"), 0, 11);
        gridPane.add(mutationMinField, 1, 11);
        gridPane.add(new Label("Maximum number of mutations:"), 0, 12);
        gridPane.add(mutationMaxField, 1, 12);
        gridPane.add(new Label("Mutation Type (1 for completely random):"), 0, 13);
        gridPane.add(mutationVarField, 1, 13);
        Button applyButton = new Button("Apply Manual Settings");
        Button XXLButton = new Button("XXL Map");
        Button LButton = new Button("Large Map");
        Button SButton = new Button("Small Map");
        Button MButton= new Button("Medium Map");
        Button XSButton= new Button("XS Map");
        Button XLButton = new Button("XL Map");
        XSButton.setOnAction(event -> {
            width = 5;
            height = 5;
            mapVar = 0;
            grassStart = 10;
            grassEnergy = 10;
            grassDaily = 5;
            grassVar = 0;
            animalStart = 5;
            animalStartEnergy = 25;
            animalBreedEnergy = 10;
            genotypeLength = 20;
            mutationMin = 0;
            mutationMax = 5;
            mutationVar =  0;
            showGridScene(primaryStage);
        });
        SButton.setOnAction(event -> {
            width = 25;
            height = 25;
            mapVar = 0;
            grassStart = 50;
            grassEnergy = 10;
            grassDaily = 5;
            grassVar = 0;
            animalStart = 15;
            animalStartEnergy = 50;
            animalBreedEnergy = 25;
            genotypeLength = 20;
            mutationMin = 0;
            mutationMax = 10;
            mutationVar =  0;
            showGridScene(primaryStage);
        });
        XXLButton.setOnAction(event -> {
            width = height = 300;
            mapVar = 0;
            grassStart = 150;
            grassEnergy = 30;
            grassDaily = 150;
            grassVar = 0;
            animalStart = 250;
            animalStartEnergy = 100;
            animalBreedEnergy = 50;
            genotypeLength = 20;
            mutationMin = 0;
            mutationMax = 5;
            mutationVar = 0;
            showGridScene(primaryStage);
        });
        XLButton.setOnAction(event -> {
            width = height = 200;
            mapVar = 0;
            grassStart = 100;
            grassEnergy = 30;
            grassDaily = 100;
            grassVar = 0;
            animalStart = 200;
            animalStartEnergy = 100;
            animalBreedEnergy = 50;
            genotypeLength = 20;
            mutationMin = 0;
            mutationMax = 5;
            mutationVar = 0;
            showGridScene(primaryStage);
        });
        LButton.setOnAction(event -> {
            width = height = 100;
            mapVar = 0;
            grassStart = 50;
            grassEnergy = 30;
            grassDaily = 50;
            grassVar = 0;
            animalStart = 100;
            animalStartEnergy = 100;
            animalBreedEnergy = 50;
            genotypeLength = 20;
            mutationMin = 0;
            mutationMax = 5;
            mutationVar = 0;
            showGridScene(primaryStage);
        });
        MButton.setOnAction(event -> {
            width = height = 50;
            mapVar = 0;
            grassStart = 25;
            grassEnergy = 30;
            grassDaily = 25;
            grassVar = 0;
            animalStart = 50;
            animalStartEnergy = 50;
            animalBreedEnergy = 50;
            genotypeLength = 20;
            mutationMin = 0;
            mutationMax = 5;
            mutationVar = 0;
            showGridScene(primaryStage);
        });
        applyButton.setOnAction(event -> {
            width = (!Objects.equals(widthField.getText(), "")) ? Integer.parseInt(widthField.getText()) : 10;
            height = (!Objects.equals(heightField.getText(), "")) ? Integer.parseInt(heightField.getText()) : 10;
            mapVar = (!Objects.equals(mapVarField.getText(), "")) ? Integer.parseInt(mapVarField.getText()) : 0;
            grassStart = (!Objects.equals(grassStartField.getText(), "")) ? Integer.parseInt(grassStartField.getText()) : 10;
            grassEnergy = (!Objects.equals(grassEnergyField.getText(), "")) ? Integer.parseInt(grassEnergyField.getText()) : 10;
            grassDaily = (!Objects.equals(grassDailyField.getText(), "")) ? Integer.parseInt(grassDailyField.getText()) : 5;
            grassVar = (!Objects.equals(grassVarField.getText(), "")) ? Integer.parseInt(grassVarField.getText()) : 0;
            animalStart = (!Objects.equals(animalStartField.getText(), "")) ? Integer.parseInt(animalStartField.getText()) : 10;
            animalStartEnergy = (!Objects.equals(animalStartEnergyField.getText(), "")) ? Integer.parseInt(animalStartEnergyField.getText()) : 40;
            animalBreedEnergy = (!Objects.equals(animalBreedEnergyField.getText(), "")) ? Integer.parseInt(animalBreedEnergyField.getText()) : 25;
            genotypeLength = (!Objects.equals(genotypeLengthField.getText(), "")) ? Integer.parseInt(genotypeLengthField.getText()) : 10;
            mutationMin = (!Objects.equals(mutationMinField.getText(), "")) ? Integer.parseInt(mutationMinField.getText()) : 0;
            mutationMax = (!Objects.equals(mutationMaxField.getText(), "")) ? Integer.parseInt(mutationMaxField.getText()) : 10;
            mutationVar = (!Objects.equals(mutationVarField.getText(), "")) ? Integer.parseInt(mutationVarField.getText()) : 0;
            showGridScene(primaryStage);
        });

        // Set the stage properties and show the setting scene
        // Create a container to hold the GridPane and the button
        VBox container = new VBox();

        // Add the GridPane to the container
        container.getChildren().add(gridPane);

        // Add some space between the GridPane and the button
        VBox.setMargin(applyButton, new Insets(10, 0, 10, 5));

        // Add the button to the container
        GridPane buttonPane= new GridPane();

        //container.getChildren().add(applyButton);
        buttonPane.add(applyButton,0,0);
        buttonPane.add(XSButton, 1,0);
        buttonPane.add(SButton, 2,0);
        buttonPane.add(MButton,3,0 );
        buttonPane.add(LButton,4,0);
        buttonPane.add(XLButton, 5,0);
        buttonPane.add(XXLButton, 6,0);
        container.getChildren().add(buttonPane);
        buttonPane.setMargin(applyButton, new Insets(0, 10, 0, 0));

        // Create a scene to hold the container
        Scene scene = new Scene(container);
        primaryStage.setTitle("Setting Scene");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void showGridScene(Stage primaryStage) {
        // Create a grid pane to hold the tiles
        GridPane grid = new GridPane();
        // Create the HBox container
        HBox hBox = new HBox();

        if (height > 400 || width > 400) return;

        TILE_SIZE = (height < 20 || width < 20) ? 50 : (height < 40 || width < 40) ? 25 : (height < 65 || width < 65) ? 15 :
                (height < 90 || width < 90) ? 10 : (height < 140 || width < 140) ? 7 : (height < 190 || width < 190) ? 5 :
                        (height < 240 || width < 240) ? 4 : (height < 300 || width < 300) ? 3 : 2;

        // Add some padding to the right side of the HBox
        hBox.setPadding(new Insets(0, 0, 0, 20));
        // Add tiles to the grid
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                grid.add(tile, col, row);
            }
        }

        // Create a button to pause the process
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> {
            pauseButton.setText(!paused.get() ? "Start" : "Pause");
            paused.set(!paused.get());
        });

        // Create a container for the grid and the button
        VBox vBox = new VBox(10, grid, pauseButton);
        // Create a VBox container to hold the parameter labels and text fields



        map = (mapVar == 0) ? new GlobeMap(width, height, grassVar, mutationVar) : new PortalMap(width, height, grassVar, mutationVar);
        engine = new SimulationEngine(map, animalBreedEnergy, grassStart, grassDaily, animalStartEnergy,
                animalStart, genotypeLength, grassEnergy, mutationMin, mutationMax);
        // Add some padding to the top of the VBox container
        vBox.setPadding(new Insets(20, 20, 0, 0));

        Label dayLabel = new Label("Day: " + engine.map.day);
        Label animalLabel = new Label("Animals: " + engine.map.animals.size());
        Label grassLabel = new Label("Grass: " + engine.map.grassCount);
        Label energyLabel = new Label("Average energy: " + 0);
        Label emptyFilesLabel = new Label("Empty files: " + engine.map.emptyFiles());
        Label lifeLabel = new Label("Average life: " + "...");
        // Add the labels to the VBox container
        vBox.getChildren().addAll(dayLabel, animalLabel, grassLabel, energyLabel, emptyFilesLabel, lifeLabel);


        // Add the VBox container to the HBox container
        hBox.getChildren().add(vBox);
        Scene scene = new Scene(new BorderPane(grid, null, hBox, null, null), TILE_SIZE * width + 150, TILE_SIZE * height);

        // Set the stage properties
        primaryStage.setTitle("The World");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Calculate and update the grid every second
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (paused.get()) {
                    continue;
                }

                engine.run();

                Platform.runLater(() -> {
                    dayLabel.setText("Day: " + engine.map.day);
                    animalLabel.setText("Animals: " + engine.map.animals.size());
                    grassLabel.setText("Grass: " + engine.map.grassCount);
                    energyLabel.setText("Average energy: " + Math.round(engine.map.avgEnergy()));
                    emptyFilesLabel.setText("Empty files: " + engine.map.emptyFiles());
                    lifeLabel.setText("Average life: " + ((engine.map.avgLife > 0) ? engine.map.avgLife : "..."));
                    for (int row = 0; row < height; row++) {
                        for (int col = 0; col < width; col++) {
                            Rectangle tile = (Rectangle) grid.getChildren().get(row * width + col);
                            Color color = calculateColor(row, col);
                            tile.setFill(color);
                        }
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private Color calculateColor(int row, int col) {
        // Calculate and return the color for the tile at the given row and column
        Vector2d pos = new Vector2d(col, height - row - 1);
        int highestEnergy = Math.min(map.findStrongest(pos), 100);
        int green = (highestEnergy == 0) ? 255 : (highestEnergy == -1) ? 69 : 0;
        int red = (highestEnergy > 0) ? 50 + highestEnergy: 0;
        int blue = 19;
        return Color.rgb(red, green, blue);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
