package cz.valkovic.twbot.services.configuration;

import cz.valkovic.twbot.modules.core.settings.instances.CorePublicSetting;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;


public interface PublicConfiguration extends
        Mutable,
        Accessible,
        CorePublicSetting {
}
