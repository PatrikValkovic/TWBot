package cz.valkovic.twbot.modules.core.settings;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Mutable;

/**
 * Interface that needs to be implemented by settings, that can be saved into file.
 * Basically all private and public settings needs to implement this interface.
 */
public interface StorableSettings extends Accessible, Mutable {
}
