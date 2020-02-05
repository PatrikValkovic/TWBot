package cz.valkovic.twbot.services.configuration;

import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;

interface InterConfiguration extends
        Mutable,
        Accessible,
        CorePrivateSetting {
}
