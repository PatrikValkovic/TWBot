package cz.valkovic.twbot.modules.core.importing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark modules for dependency injection.
 * Used for automatic module installation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TWModule {
    int value() default Integer.MAX_VALUE;
}
