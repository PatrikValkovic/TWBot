package cz.valkovic.twbot.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "server")
public class Server implements BaseEntity{

    @Id
    @GeneratedValue
    @Getter
    @Setter
    int id;

    @Getter
    @Setter
    private String serverName;

    @Getter
    @Setter
    private String host;

    @OneToOne(mappedBy = "server", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    private ServerSetting setting;


    public void setSetting(ServerSetting setting) {
        this.setting = setting;
        setting.setServerName(getServerName());
        setting.setServer(this);
    }
}
