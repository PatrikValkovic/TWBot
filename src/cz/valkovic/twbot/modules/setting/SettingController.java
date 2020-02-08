package cz.valkovic.twbot.modules.setting;

import cz.valkovic.twbot.Main;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.core.settings.SettingStorageService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingController {

    @Inject
    private SettingsProviderService setting;
    @Inject
    private SettingStorageService storage;
    @Inject
    private LoggingService log;

    @SuppressWarnings("FieldCanBeLocal")
    private Object settingObserverLock = null;

    @FXML
    protected void initialize() {
        Main.getInjector().injectMembers(this);
        settingObserverLock = this.setting.observePublicSettingsInRender((settings) -> {
            this.settings = settings;
            this.createEntries();
        });
    }

    @FXML
    public VBox mainContainer;

    @FXML
    public void save(ActionEvent ignore) {
        this.log.getSettings().debug("Attempt to store setting");
        String changesDebugString = changesCache.values()
                .stream()
                .flatMap(entry -> entry.entrySet().stream().map(e -> e.getKey() + "-" + e.getValue()))
                .collect(Collectors.joining("\n"));
        this.log.getSettings().debug("Changes in the setting:\n" + changesDebugString);

        changesCache.forEach((setting, changes) -> {
            for (Map.Entry<String, String> ch : changes.entrySet())
                setting.setProperty(ch.getKey(), ch.getValue());
        });
        this.storage.store();
        this.log.getSettings().info("Setting stored");
        changesCache.clear();
    }

    @FXML
    public void reload(ActionEvent e) {
        this.createEntries();
    }


    private List<Map.Entry<Class<PublicSettings>, PublicSettings>> settings;
    private Map<PublicSettings, Map<String, String>> changesCache = new HashMap<>(16, 0.5f);

    private void createEntries() {
        log.getGUI().debug("Creating entries in the Setting view");
        mainContainer.getChildren().clear();

        if(settings == null){
            log.getGUI().warn("No public setting to show");
            return;
        }

        for(Map.Entry<Class<PublicSettings>, PublicSettings> setting : settings) {
            Set<String> properties = new HashSet<>(16, 0.5f);
            Class<? extends PublicSettings> settingClass = setting.getKey();
            PublicSettings settingInstance = setting.getValue();
            List<String> declared = Stream.of(settingClass.getDeclaredMethods())
                                          .map(Method::getName)
                                          .collect(Collectors.toList());
            properties.addAll(declared);
            properties.addAll(settingInstance.propertyNames());

            for(String prop : properties){
                // create content
                Label title = new Label(prop);
                Label description = new Label("No description provided");
                TextField content = new TextField(settingInstance.getProperty(prop));
                // get annotations
                try {
                        var annotation = settingClass.getDeclaredMethod(prop)
                                                     .getAnnotation(SettingDescription.class);
                        description.setText(annotation.value());
                }
                catch(Exception ignored) {}
                // handle text field changes
                content.textProperty().addListener((observable, old_value, new_value) -> {
                    if(!changesCache.containsKey(settingInstance))
                        changesCache.put(settingInstance, new HashMap<>(16, 0.5f));
                    changesCache.get(settingInstance).put(prop, new_value);
                });
                // create containers
                VBox box = new VBox(
                        new HBox(
                            content,
                            title
                        ),
                        description
                );
                mainContainer.getChildren().add(0, box);
            }
        }
    }

}
