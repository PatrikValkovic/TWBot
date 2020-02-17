package cz.valkovic.twbot.models;

import cz.valkovic.twbot.modules.core.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server_setting")
public class ServerSetting implements BaseEntity<Integer> {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    Integer id;



}
