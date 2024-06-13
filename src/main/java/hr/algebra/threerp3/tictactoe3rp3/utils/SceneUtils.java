package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.CCVRApplication;
import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

@Author(name = "Ana")
public final class SceneUtils {
    private SceneUtils() {
    }

    public static void setNewSceneToStage(String name, String countryCityVillageRiverScreenTitle) {
        FXMLLoader fxmlLoader = new FXMLLoader(CCVRApplication.class.getResource(name));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CCVRApplication.getMainStage().setTitle(countryCityVillageRiverScreenTitle);
        CCVRApplication.getMainStage().setScene(scene);
        CCVRApplication.getMainStage().show();
    }
}
