package cz.valkovic.twbot.modules.setting;

import cz.valkovic.twbot.Main;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.PublicSettings;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingController {

    @Inject
    SettingsProviderService setting;
    @Inject
    LoggingService log;

    @FXML
    protected void initialize() {
        Main.getInjector().injectMembers(this);
        this.setting.observePublicSettingsInRender((settings) -> {
            this.settings = settings;
            this.createEntries();
        });
    }

    @FXML
    public VBox mainContainer;

    @FXML
    public void save(ActionEvent e) {
        System.out.printf("save");
    }

    @FXML
    public void reload(ActionEvent e) {
        this.createEntries();
    }

    private List<PublicSettings> settings;

    private void createEntries() {
        log.getGUI().debug("Creating entries in the Setting view");
        mainContainer.getChildren().clear();

        if(settings == null){
            log.getGUI().warn("No public setting to show");
            return;
        }

        for(PublicSettings setting : settings) {
            Set<String> properties = new HashSet<>(16, 0.5f);
            Class<? extends PublicSettings> settingClass = null;
            //TODO so ugly, accessing private properties
            try {
                InvocationHandler handler = Proxy.getInvocationHandler(setting);
                Field f = handler.getClass().getDeclaredField("jmxSupport");
                f.setAccessible(true);
                var jmxsupport = f.get(handler);
                Field clazzField = jmxsupport.getClass().getDeclaredField("clazz");
                clazzField.setAccessible(true);
                //noinspection unchecked
                settingClass = (Class<? extends PublicSettings>) clazzField.get(jmxsupport);
                List<String> declared = Stream.of(settingClass.getDeclaredMethods())
                                              .map(Method::getName)
                                              .collect(Collectors.toList());
                properties.addAll(declared);
                properties.addAll(setting.propertyNames());
            }
            catch (Exception ignored) {}


            for(String prop : properties){
                Label title = new Label(prop);
                Label description = new Label("No description provided");
                TextField content = new TextField(setting.getProperty(prop));
                try {
                        var annotation = settingClass.getDeclaredMethod(prop)
                                                     .getAnnotation(SettingDescription.class);
                        description.setText(annotation.value());
                }
                catch(Exception ignored) {}
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
