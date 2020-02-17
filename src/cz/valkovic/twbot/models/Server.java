package cz.valkovic.twbot.models;

import cz.valkovic.twbot.modules.core.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server")
public class Server implements BaseEntity<Integer> {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    Integer id;

    @Getter
    @Setter
    private String serverName;

    @Getter
    @Setter
    private String host;
}
