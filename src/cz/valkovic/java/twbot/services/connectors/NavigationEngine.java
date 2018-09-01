package cz.valkovic.java.twbot.services.connectors;

import javafx.beans.property.ReadOnlyStringProperty;

public interface NavigationEngine {

    ReadOnlyStringProperty loadedPageProperty();

    String getContent();

    String getLocation();
}
