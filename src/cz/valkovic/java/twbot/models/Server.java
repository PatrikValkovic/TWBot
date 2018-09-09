package cz.valkovic.java.twbot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "server")
public class Server {

    static final String ID_COLUMN = "server_name";

    @Id
    @Column(name = ID_COLUMN)
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
