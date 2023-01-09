package agh.proj.oop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.opencsv.CSVWriter;

import org.json.JSONObject;

public class App extends Application {
    private final BooleanProperty paused = new SimpleBooleanProperty(false);
    private final BooleanProperty showPopular = new SimpleBooleanProperty(false);
    private SimulationEngine engine;
    private AbstractWorldMap map;
    private int width, height, mapVar, grassStart, grassEnergy, grassDaily, grassVar, animalStart, animalStartEnergy;
    private int animalBreedEnergy, mutationMin, mutationMax, genotypeLength, mutationVar;
    private boolean saveData = false;
    private boolean tracking = false;
    private Animal tracked = null;
    private String fileName;

    private void jsonReader(String mapType) {

        String filePath = "src/main/resources/presets.json";
        File file = new File(filePath);
        FileReader reader;
        try { reader = new FileReader(file);
        } catch (FileNotFoundException e) { throw new RuntimeException(e); }
        char[] chars = new char[(int) file.length()];
        try { reader.read(chars);
        } catch (IOException e) { throw new RuntimeException(e); }

        String jsonString = new String(chars);
        JSONObject json_raw = new JSONObject(jsonString);
        JSONObject json = json_raw.getJSONObject(mapType);
        width = json.getInt("width");
        height = json.getInt("height");
        mapVar = json.getInt("mapVar");
        grassStart = json.getInt("grassStart");
        grassEnergy = json.getInt("grassEnergy");
        grassDaily = json.getInt("grassDaily");
        grassVar = json.getInt("grassVar");
        animalStart = json.getInt("animalStart");
        animalStartEnergy = json.getInt("animalStartEnergy");
        animalBreedEnergy = json.getInt("animalBreedEnergy");
        genotypeLength = json.getInt("genotypeLength");
        mutationMin = json.getInt("mutationMin");
        mutationMax = json.getInt("mutationMax");
        mutationVar = json.getInt("mutationVar");

        try { reader.close();
        } catch (IOException e) { throw new RuntimeException(e); }

    }

    @Override
    public void start(Stage primaryStage) {

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
        TextField fileNameField = new TextField("data");
        fileNameField.setPrefWidth(50);

        GridPane gridPane = new GridPane();
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
        Button applyButton = new Button("Custom");
        Button XXLButton = new Button("XXL Map");
        Button LButton = new Button("Large Map");
        Button SButton = new Button("Small Map");
        Button MButton= new Button("Medium Map");
        Button XSButton= new Button("XS Map");
        Button XLButton = new Button("XL Map");
        Button InsaneButton = new Button("Insanity");
        Button CrazyButton = new Button("Craziness");
        Button ClaustrophobiaButton = new Button("Claustrophobia");
        Button EdenGardenButton = new Button("Eden Garden");
        Button LongButton = new Button("Long Map");
        Button ShortGenesButton = new Button("Short Genes");
        Button SaveButton = new Button("Save Data");
        XSButton.setOnAction(event -> {
            jsonReader("XSMap");
            showGridScene(primaryStage);
        });
        SButton.setOnAction(event -> {
            jsonReader("SMap");
            showGridScene(primaryStage);
        });
        MButton.setOnAction(event -> {
            jsonReader("MMap");
            showGridScene(primaryStage);
        });
        LButton.setOnAction(event -> {
            jsonReader("LMap");
            showGridScene(primaryStage);
        });
        XLButton.setOnAction(event -> {
            jsonReader("XLMap");
            showGridScene(primaryStage);
        });
        XXLButton.setOnAction(event -> {
            jsonReader("XXLMap");
            showGridScene(primaryStage);
        });
        InsaneButton.setOnAction(event -> {
            jsonReader("IMap");
            showGridScene(primaryStage);
        });
        CrazyButton.setOnAction(event -> {
            jsonReader("CMap");
            showGridScene(primaryStage);
        });
        ClaustrophobiaButton.setOnAction(event -> {
            jsonReader("C2Map");
            showGridScene(primaryStage);
        });
        EdenGardenButton.setOnAction(event -> {
            jsonReader("EMap");
            showGridScene(primaryStage);
        });
        LongButton.setOnAction(event -> {
            jsonReader("L2Map");
            showGridScene(primaryStage);
        });
        ShortGenesButton.setOnAction(event -> {
            jsonReader("S2Map");
            showGridScene(primaryStage);
        });
        SaveButton.setOnAction(event -> {
            saveData = !saveData;
            fileName = fileNameField.getText();
        });
        applyButton.setOnAction(event -> {
            width = (!Objects.equals(widthField.getText(), "")) ? Integer.parseInt(widthField.getText()) : 10;
            height = (!Objects.equals(heightField.getText(), "")) ? Integer.parseInt(heightField.getText()) : 10;
            mapVar = (!Objects.equals(mapVarField.getText(), "")) ? Integer.parseInt(mapVarField.getText()) : 0;
            grassStart = (!Objects.equals(grassStartField.getText(), "")) ? Integer.parseInt(grassStartField.getText()) : 10;
            grassEnergy = (!Objects.equals(grassEnergyField.getText(), "")) ? Integer.parseInt(grassEnergyField.getText()) : 10;
            grassDaily = (!Objects.equals(grassDailyField.getText(), "")) ? Integer.parseInt(grassDailyField.getText()) : 5;
            grassVar = (!Objects.equals(grassVarField.getText(), "")) ? Integer.parseInt(grassVarField.getText()) : 0;
            animalStart = (!Objects.equals(animalStartField.getText(), "")) ? Integer.parseInt(animalStartField.getText()) : 0;
            animalStartEnergy = (!Objects.equals(animalStartEnergyField.getText(), "")) ? Integer.parseInt(animalStartEnergyField.getText()) : 40;
            animalBreedEnergy = (!Objects.equals(animalBreedEnergyField.getText(), "")) ? Integer.parseInt(animalBreedEnergyField.getText()) : 25;
            genotypeLength = (!Objects.equals(genotypeLengthField.getText(), "")) ? Integer.parseInt(genotypeLengthField.getText()) : 10;
            mutationMin = (!Objects.equals(mutationMinField.getText(), "")) ? Integer.parseInt(mutationMinField.getText()) : 0;
            mutationMax = (!Objects.equals(mutationMaxField.getText(), "")) ? Integer.parseInt(mutationMaxField.getText()) : 10;
            mutationVar = (!Objects.equals(mutationVarField.getText(), "")) ? Integer.parseInt(mutationVarField.getText()) : 0;
            showGridScene(primaryStage);
        });

        VBox container = new VBox();
        container.getChildren().add(gridPane);

        GridPane buttonPane = new GridPane();
        buttonPane.add(applyButton,0,0);
        buttonPane.add(SaveButton, 1, 0);
        buttonPane.add(fileNameField, 2, 0);
        buttonPane.add(XSButton, 0,1);
        buttonPane.add(SButton, 1,1);
        buttonPane.add(MButton,2,1);
        buttonPane.add(LButton,3,1);
        buttonPane.add(XLButton, 4,1);
        buttonPane.add(XXLButton, 5,1);
        buttonPane.add(InsaneButton, 0, 2);
        buttonPane.add(CrazyButton, 1 ,2);
        buttonPane.add(ClaustrophobiaButton, 2, 2);
        buttonPane.add(EdenGardenButton, 3, 2);
        buttonPane.add(LongButton, 4, 2);
        buttonPane.add(ShortGenesButton, 5, 2);
        container.getChildren().add(buttonPane);

        for (Node node : buttonPane.getChildren()) {
            GridPane.setMargin(node, new Insets(10, 5, 1 ,5));
        }

        Scene scene = new Scene(container);
        primaryStage.setTitle("Setting Scene");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showGridScene(Stage primaryStage) {

        GridPane grid = new GridPane();
        VBox hBox = new VBox();
        VBox animalStatsBox = new VBox();
        if (height > 400 || width > 400) return;

        int TILE_SIZE = (height < 5 || width < 5) ? 75 : (height < 20 || width < 20) ? 50 :
                (height < 40 || width < 40) ? 25 : (height < 65 || width < 65) ? 15 :
                        (height < 90 || width < 90) ? 10 : (height < 140 || width < 140) ? 7 :
                                (height < 190 || width < 190) ? 5 : (height < 240 || width < 240) ? 4 :
                                        (height < 300 || width < 300) ? 3 : 2;
        //TILE_SIZE=TILE_SIZE/2;
        hBox.setPadding(new Insets(0, 0, 0, 20));
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                grid.add(tile, col, row);
            }
        }
        Button popularGenotypeButton = new Button("Popular\nGenotype");
        popularGenotypeButton.setVisible(false);
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> {
            pauseButton.setText(!paused.get() ? "Start" : "Pause");
            popularGenotypeButton.setVisible(!paused.get());
            paused.set(!paused.get());
        });
        popularGenotypeButton.setOnAction(event -> showPopular.set(!showPopular.get()));
        VBox vBox = new VBox(10, grid, pauseButton, popularGenotypeButton);



        map = (mapVar == 0) ? new GlobeMap(width, height, grassVar, mutationVar) : new PortalMap(width, height, grassVar, mutationVar);
        engine = new SimulationEngine(map, animalBreedEnergy, grassStart, grassDaily, animalStartEnergy,
                animalStart, genotypeLength, grassEnergy, mutationMin, mutationMax);

        vBox.setPadding(new Insets(20, 20, 0, 0));

        Label dayLabel = new Label("Day: " + engine.map.day);
        Label animalLabel = new Label("Animals: " + engine.map.animals.size());
        Label grassLabel = new Label("Grass: " + engine.map.grassCount);
        Label energyLabel = new Label("Average energy: " + 0);
        Label emptyFilesLabel = new Label("Empty files: " + engine.map.emptyFiles());
        Label lifeLabel = new Label("Average life: " + "...");
        Label popularGenotypeLabel = new Label("Popular genotypes: ...");
        vBox.getChildren().addAll(dayLabel, animalLabel, grassLabel, energyLabel, emptyFilesLabel, lifeLabel, popularGenotypeLabel);
        Label animalDeath= new Label("Date of death: not dead ");
        animalStatsBox.getChildren().add(new Label(""));
        animalStatsBox.getChildren().add(new Label("Tracked animal stats:"));
        animalStatsBox.getChildren().add(new Label("Currently not tracking"));
        hBox.getChildren().add(vBox);
        Scene scene = new Scene(new BorderPane(grid, null, hBox, animalStatsBox, null), TILE_SIZE * width + 200, TILE_SIZE * height + 150);

        primaryStage.setTitle("The World");
        primaryStage.setScene(scene);
        primaryStage.show();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (paused.get()) {
                    if (showPopular.get()) {
                        for (int row = 0; row < height; row++) {
                            for (int col = 0; col < width; col++) {
                                Rectangle tile = (Rectangle) grid.getChildren().get(row * width + col);
                                if (isPopular(row, col)) tile.setFill(Color.rgb(200, 0, 255));
                            }
                        }
                        showPopular.set(false);
                    }
                    //tracking
                    for (int row = 0; row < height; row++) {
                        for (int col = 0; col < width; col++) {
                            Rectangle tile = (Rectangle) grid.getChildren().get(row * width + col);

                            Vector2d here=new Vector2d(col, height- row -1);
                            tile.setOnMouseClicked(event -> {

                                tracked=map.findStrongestAnimal(here);
                                if (tracked == null) {
                                    animalStatsBox.getChildren().clear();
                                    animalStatsBox.getChildren().add(new Label(""));
                                    animalStatsBox.getChildren().add(new Label("Tracked animal stats:"));
                                    animalStatsBox.getChildren().add(new Label("Currently not tracking"));
                                    animalStatsBox.getChildren().add(new Label(""));
                                    animalStatsBox.getChildren().add(new Label(""));
                                    animalStatsBox.getChildren().add(new Label(""));
                                    animalStatsBox.getChildren().add(new Label(""));
                                    tracking=false;
                                    tracked=null;
                                }
                                else{
                                    tracking=true;
                                    animalStatsBox.getChildren().clear();
                                    animalStatsBox.getChildren().add(new Label(""));
                                    animalStatsBox.getChildren().add(new Label("Tracked animal stats:"));
                                    animalStatsBox.getChildren().add(new Label("Energy: " + tracked.energy));
                                    animalStatsBox.getChildren().add(new Label("Day of birth: " + tracked.birth));
                                    animalStatsBox.getChildren().add(new Label("Genotype " + tracked.genotype));
                                    animalStatsBox.getChildren().add(new Label("Number of children " + tracked.numberOfChildren));
                                    animalStatsBox.getChildren().add(new Label("Day of birth: " + tracked.birth));
                                    animalDeath.setText("Day of death: not dead");
                                    animalStatsBox.getChildren().add(animalDeath);
                                }


                            });
                        }
                    }
                    continue;
                }




                Platform.runLater(() -> {

                    dayLabel.setText("Day: " + engine.map.day);
                    animalLabel.setText("Animals: " + engine.map.animals.size());
                    grassLabel.setText("Grass: " + engine.map.grassCount);
                    energyLabel.setText("Average energy: " + Math.round(engine.map.avgEnergy()));
                    emptyFilesLabel.setText("Empty files: " + engine.map.emptyFiles());
                    lifeLabel.setText("Average life: " + ((engine.map.avgLife > 0) ? engine.map.avgLife : "..."));
                    ArrayList<Integer> indexes = engine.map.getHighestValueIndexes();
                    popularGenotypeLabel.setText("Popular genotypes: " + ((indexes.size() < 4) ? indexes : "too long..."));

                    if (saveData) {
                        try {
                            saveData();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    for (int row = 0; row < height; row++) {
                        for (int col = 0; col < width; col++) {
                            Rectangle tile = (Rectangle) grid.getChildren().get(row * width + col);
                            Color color = calculateColor(row, col);
                            tile.setFill(color);
                        }
                    }
                    if(tracking){
                        animalStatsBox.getChildren().clear();
                        animalStatsBox.getChildren().add(new Label(""));
                        animalStatsBox.getChildren().add(new Label("Tracked animal stats:"));
                        animalStatsBox.getChildren().add(new Label("Energy: " + tracked.energy));
                        animalStatsBox.getChildren().add(new Label("Grass eaten: " + tracked.munched));
                        animalStatsBox.getChildren().add(new Label("Genotype " + tracked.genotype));
                        animalStatsBox.getChildren().add(new Label("Number of children " + tracked.numberOfChildren));
                        animalStatsBox.getChildren().add(new Label("Day of birth: " + tracked.birth));
                        animalStatsBox.getChildren().add(animalDeath);
                        if(tracked.death!=-1) animalDeath.setText("Day of death: " + tracked.death);

                    }
                    else{
                        animalStatsBox.getChildren().clear();
                        animalStatsBox.getChildren().add(new Label(""));
                        animalStatsBox.getChildren().add(new Label("Tracked animal stats:"));
                        animalStatsBox.getChildren().add(new Label("Currently not tracking"));
                        animalStatsBox.getChildren().add(new Label(""));
                        animalStatsBox.getChildren().add(new Label(""));
                        animalStatsBox.getChildren().add(new Label(""));
                        animalStatsBox.getChildren().add(new Label(""));
                    }

                });
                engine.run();
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    private Color calculateColor(int row, int col) {
        Vector2d pos = new Vector2d(col, height - row - 1);
        int highestEnergy = Math.min(map.findStrongest(pos), 100);
        int green = (highestEnergy == 0) ? 255 : (highestEnergy == -1) ? 69 : 0;
        int red = (highestEnergy > 0) ? 50 + highestEnergy: 0;
        int blue = 19;
        return Color.rgb(red, green, blue);
    }
    private boolean isPopular(int row, int col) {
        Vector2d pos = new Vector2d(col, height - row - 1);
        return engine.map.hasPopularAnimal(pos);
    }
    private void saveData() throws IOException {
        String csvFile = fileName + ".csv";
        FileWriter writer = new FileWriter(csvFile, true); // Open the file in append mode
        CSVWriter csvWriter = new CSVWriter(writer);
        List<String[]> data = new ArrayList<>();
        if (engine.map.day == 1) data.add(new String[] {
                "Day: ", "Animal Count: ", "Grass Count: ", "Avg Energy: ", "Empty Tiles: ", "Avg Life:"
        });
        data.add(new String[] {
                Integer.toString(engine.map.day),
                Integer.toString(engine.map.animals.size()),
                Integer.toString(engine.map.grassCount),
                Integer.toString((int) Math.round(engine.map.avgEnergy())),
                Integer.toString(engine.map.emptyFiles()),
                (engine.map.avgLife > 0) ? Integer.toString(engine.map.avgLife) : "..."
        });
        csvWriter.writeAll(data);
        csvWriter.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
