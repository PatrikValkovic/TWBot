package cz.valkovic.java.twbot.services.connectors;


public interface NavigationEngine {
    String getContent();

    String getLocation();

    void setLocation(String location);
}
