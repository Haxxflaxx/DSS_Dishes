package application.controller;

/**
 * Created by Daniel.
 *
 * A parent class for handling the history navigation.
 */
public abstract class NavigationController {

    public NavigationController(){
        VistaNavigator.setActiveController(this);
        VistaNavigator.clearForward();
    }

    /**
     * Method called to get the fxml for the controller.
     * @return String path and name of the fxml file for the controller.
     */
    public abstract String getFxml();
}
