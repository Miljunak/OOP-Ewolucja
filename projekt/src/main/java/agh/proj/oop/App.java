package agh.proj.oop;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

public class App extends Application {
    private static final int TILE_SIZE = 50;
    private static final int GRID_SIZE = 10;
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

        Button applyButton = new Button("Apply");
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

        // Create the setting scene
        Scene settingScene = new Scene(new VBox(5, new Label("Width:"), widthField,
                new Label("Height: "), heightField, new Label("Map type (1 for portal)"), mapVarField,
                new Label("Amount of starting grass:"), grassStartField, new Label("Energy from eating grass"),
                grassEnergyField, new Label("Amount of daily grass"), grassDailyField, new Label("Grass variant (1 for toxic)"),
                grassVarField, new Label("Amount of starting animals"), animalStartField, new Label("Starting energy for animals"),
                animalStartEnergyField, new Label("Energy needed to reproduce"), animalBreedEnergyField,
                new Label("Length of genotype"), genotypeLengthField, new Label("Minimum mutations"),
                mutationMinField, new Label("Maximum mutations"), mutationMaxField, new Label("Mutation type (1 for random"),
                mutationVarField, applyButton), 300, 800);

        // Set the stage properties and show the setting scene
        primaryStage.setTitle("Setting Scene");
        primaryStage.setScene(settingScene);
        primaryStage.show();
    }


    public void showGridScene(Stage primaryStage) {
        // Create a grid pane to hold the tiles
        GridPane grid = new GridPane();

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
        VBox root = new VBox(10, grid, pauseButton);

        map = new GlobeMap(width, height, grassVar, mutationVar);
        engine = new SimulationEngine(map, animalBreedEnergy, grassStart, grassDaily, animalStartEnergy,
                animalStart, genotypeLength, grassEnergy, mutationMin, mutationMax);

        Scene scene = new Scene(root, TILE_SIZE * width, TILE_SIZE * height + 50);

        // Set the stage properties
        primaryStage.setTitle("The World");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Calculate and update the grid every second
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (paused.get()) {
                    continue;
                }

                engine.run();

                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        Rectangle tile = (Rectangle) grid.getChildren().get(row * width + col);
                        Color color = calculateColor(row, col); // Calculate the color based on the row and column
                        tile.setFill(color);
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private Color calculateColor(int row, int col) {
        // Calculate and return the color for the tile at the given row and column
        int res = (map.objectsAt(new Vector2d(col, height - row - 1)) != null) ? 255 : 0;
        return Color.rgb(res, 0, 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
