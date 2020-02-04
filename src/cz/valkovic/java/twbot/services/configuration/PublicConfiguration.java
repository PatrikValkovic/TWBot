package cz.valkovic.java.twbot.services.configuration;

import cz.valkovic.java.twbot.modules.core.settings.instances.CorePublicSetting;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;


public interface PublicConfiguration extends
        Mutable,
        Accessible,
        CorePublicSetting {
}
