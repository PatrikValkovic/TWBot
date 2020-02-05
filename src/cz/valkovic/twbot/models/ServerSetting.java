package cz.valkovic.twbot.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "server_setting")
public class ServerSetting extends BaseEntity {

    @Getter
    @Setter
    private String serverName;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Getter
    @Setter
    private Server server;

    @OneToOne(mappedBy = "settings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    private UnitsSettings units;

    public void setUnits(UnitsSettings units) {
        this.units = units;
        units.setServerName(getServerName());
    }

    @OneToOne(mappedBy = "settings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    private BuildingSettings buildings;

    public void setBuildings(BuildingSettings buildings) {
        this.buildings = buildings;
        buildings.setServerName(getServerName());
    }

    @Getter
    @Setter
    private double speed;

    @Getter
    @Setter
    private double unitSpeedModifier;
    
    @Getter
    @Setter
    private boolean moralActivated;
    
    @Getter
    @Setter
    private boolean buildingDestruction;
    
    @Getter
    @Setter
    private int tradeCancelTime;
    
    @Getter
    @Setter
    private int commandCancelTime;
    
    @Getter
    @Setter
    private int beginerProtection;
    
    @Getter
    @Setter
    private boolean paladinActivated;
    
    @Getter
    @Setter
    private boolean archersActivated;
    
    @Getter
    @Setter
    private boolean goldCoinSystem;
    
    @Getter
    @Setter
    private int maxNobleDistance;

    @Getter
    @Setter
    private int tribeMemberLimit;

}
