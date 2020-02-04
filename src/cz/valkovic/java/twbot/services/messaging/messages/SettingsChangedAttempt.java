package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.modules.core.events.Event;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SettingsChangedAttempt implements Event {

    public SettingsChangedAttempt(boolean fullscreen,
                                  String homepage,
                                  boolean maximalized,
                                  int navigationTimeMax,
                                  int navigationTimeMin,
                                  int parseTime,
                                  String password,
                                  int reloadPageMax,
                                  int reloadPageMin,
                                  String serverName,
                                  String userAgent,
                                  String username,
                                  int windowHeight,
                                  int windowWidth) {
        this.props.put("fullscreen", fullscreen ? "true" : "false");
        this.props.put("homepage", homepage);
        this.props.put("maximalized", maximalized ? "true" : "false");
        this.props.put("navigationTimeMax", Integer.toString(navigationTimeMax));
        this.props.put("navigationTimeMin", Integer.toString(navigationTimeMin));
        this.props.put("parseTime", Integer.toString(parseTime));
        this.props.put("password", password);
        this.props.put("reloadPageMax", Integer.toString(reloadPageMax));
        this.props.put("reloadPageMin", Integer.toString(reloadPageMin));
        this.props.put("serverName", serverName);
        this.props.put("userAgent", userAgent);
        this.props.put("username", username);
        this.props.put("windowHeight", Integer.toString(windowHeight));
        this.props.put("windowWidth", Integer.toString(windowWidth));
    }

    @Getter
    private Map<String, String> props = new HashMap<>();

}
