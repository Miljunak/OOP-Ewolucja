package agh.proj.oop;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class World {

    public static void main(String[] args) {
        Application.launch(App.class, args);
        AbstractWorldMap map = new GlobeMap(12,12, false, false);

        SimulationEngine engine = new SimulationEngine(map, 25, 9, 5,
                40, 7, 10, 10, 0, 10);
        engine.run(50);

    }
}
