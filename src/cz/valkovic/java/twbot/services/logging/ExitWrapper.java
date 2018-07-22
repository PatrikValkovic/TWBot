package cz.valkovic.java.twbot.services.logging;

import javafx.application.Platform;

public class ExitWrapper {

    public void exit(){
        Platform.exit();
        System.exit(1);
    }

    public void andExit(){
        this.exit();
    }

}
