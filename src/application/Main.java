package application;

import application.controller.LoginNavigator;
import application.controller.MainController;
import application.controller.VistaNavigator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Main application class.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        /**
         * StageStyle set to UNDECORETED to remove system window
         */
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("DSS Dishes");

        stage.setScene(
                createScene(
                        loadMainPane(stage)
                )
        );
        stage.setResizable(true);
        stage.show();

        /*
        transfer stage to mainController
         */

    }

    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */

    private static double xOffset = 0;
    private static double yOffset = 0;

    private Pane loadMainPane(final Stage stage) throws IOException {
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("../icons/AppIcon.png"))); /** custom taskbar icon*/
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        VistaNavigator.MAIN
                )
        );

        MainController loginController = loader.getController();
        LoginNavigator.setLoginController(loginController);
        LoginNavigator.loadLogin(
                LoginNavigator.LOGIN
        );

        MainController mainController = loader.getController();
        mainController.setStage(stage); //IoannisGkikas,making stage ready for extraction

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(
                VistaNavigator.SEARCH
        );

        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }

        });

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {

        Scene scene = new Scene(
                mainPane
        );
        scene.getStylesheets().add("theme.css");

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}