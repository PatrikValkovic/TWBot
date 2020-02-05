package cz.valkovic.java.twbot.modules.core.database.events;

import cz.valkovic.java.twbot.models.BaseEntity;
import cz.valkovic.java.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Event that is called when database entity is registered.
 */
@AllArgsConstructor
public class EntityRegistered implements Event {

    /**
     * Registered entity.
     */
    @Getter
    @Setter
    Class<? extends BaseEntity> entity;

}
