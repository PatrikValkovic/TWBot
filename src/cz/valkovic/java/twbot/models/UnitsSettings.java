package cz.valkovic.java.twbot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "units_setting")
public class UnitsSettings {

    @Id
    @Column(name = Server.ID_COLUMN, unique = true)
    @Getter
    @Setter
    private String serverName;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Getter
    @Setter
    private ServerSetting settings;

    @OneToMany(mappedBy="unitsSettings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<UnitInfo> units = new HashSet<>();

    public UnitInfo getInfo(UnitTypes type){
        return units.stream()
                    .filter(unitInfo -> unitInfo.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

}
