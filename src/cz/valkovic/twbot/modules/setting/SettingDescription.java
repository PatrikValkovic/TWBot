package cz.valkovic.twbot.modules.setting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to declare setting option description as annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingDescription {

    /**
     * Description of the setting option.
     * @return Description.
     */
    String value();
}
