package cz.valkovic.twbot.modules.core.controls;

import com.google.inject.Injector;
import cz.valkovic.twbot.Main;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.tabs.TabsRetrieveService;
import java.util.function.Consumer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.inject.Inject;

/**
 * Controller of the main window.
 */
public class MainWindow {

    @Inject
    private TabsRetrieveService tabs;
    @Inject
    private Injector injector;
    @Inject
    private LoggingService log;

    public MainWindow() {
        Main.getInjector().injectMembers(this);
    }

    @FXML
    public TabPane pane;
    @FXML
    public Tab plusTab;
    private Tab lastKnowTab;

    public void tabChanged(Event event) {
        Tab t = (Tab)event.getTarget();
        lastKnowTab = t != plusTab ? t : lastKnowTab;
        if(t == plusTab) {
            this.log.getGUI().debug("Initialized creation of tab");
            pane.getSelectionModel().select(pane.getTabs().indexOf(lastKnowTab));
            String[] names = this.tabs.tabsNames();
            if(names.length > 0)
                this.showTabsBox(this.tabs.tabsNames(), this::createTab);
        }
    }

    private void showTabsBox(String[] possibleNames, Consumer<String> callback) {
        // dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setAlwaysOnTop(true);
        dialog.setX(mouseX);
        dialog.setY(mouseY);
        //items
        VBox box = new VBox();
        Background b = new Background(
                new BackgroundFill(Color.AQUAMARINE, null, null)
        );
        this.log.getGUI().debug("Get " + possibleNames.length + " possible tabs to add.");
        for(String title : possibleNames) {
            Label l = new Label(title);
            l.setFont(Font.font(16));
            l.setOnMouseEntered(e -> ((Label)e.getTarget()).setBackground(b));
            l.setOnMouseExited(e -> ((Label)e.getTarget()).setBackground(Background.EMPTY));
            l.setOnMouseClicked(e -> {
                dialog.close();
                this.log.getGUI().debug("User picked up " + l.getText() + " tab.");
                callback.accept(l.getText());
            });
            box.getChildren().add(l);
        }
        // scene
        Scene dialogScene = new Scene(box);
        dialog.setScene(dialogScene);
        dialogScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE)
                dialog.close();
        });
        dialog.show();
    }

    private void createTab(String tabName) {
        Class<? extends Node> controlClass = this.tabs.getRepresentativeClass(tabName);
        Tab tabToAdd = new Tab(
                tabName,
                this.injector.getInstance(controlClass)
        );
        tabToAdd.setClosable(this.tabs.isClosable(tabName));
        tabToAdd.setOnSelectionChanged(this::tabChanged);
        this.pane.getTabs().add(
                this.pane.getTabs().size() - 1,
                tabToAdd
        );
        this.pane.getSelectionModel().select(this.pane.getTabs().size() - 2);
        this.log.getGUI().info("Tab " + tabToAdd.getText() + " successfully created.");
    }

    private double mouseX;
    private double mouseY;
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getScreenX();
        mouseY = mouseEvent.getScreenY();
    }
}
