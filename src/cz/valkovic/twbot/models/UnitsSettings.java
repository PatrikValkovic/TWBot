package cz.valkovic.twbot.models;

import cz.valkovic.twbot.modules.core.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "units_setting")
public class UnitsSettings implements BaseEntity<Integer> {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    Integer id;

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
