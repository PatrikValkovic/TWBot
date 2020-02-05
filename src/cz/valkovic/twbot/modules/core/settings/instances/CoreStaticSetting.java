package cz.valkovic.twbot.modules.core.settings.instances;

public class CoreStaticSetting {

    public int majorVersion() {
        return 0;
    }

    public int minorVersion() {
        return 1;
    }

    public int patchVersion() {
        return 0;
    }

    public String version() {
        return majorVersion() + "." + minorVersion() + "." + patchVersion();
    }

}
