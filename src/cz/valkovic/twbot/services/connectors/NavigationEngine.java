package cz.valkovic.twbot.services.connectors;


public interface NavigationEngine {
    String getContent();

    String getLocation();

    void setLocation(String location);
}
