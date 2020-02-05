package cz.valkovic.java.twbot.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "units_setting")
public class UnitsSettings extends BaseEntity {

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
