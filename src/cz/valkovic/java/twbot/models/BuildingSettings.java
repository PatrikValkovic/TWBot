package cz.valkovic.java.twbot.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "buildings_settings")
public class BuildingSettings extends BaseEntity{

    @Getter
    @Setter
    private String serverName;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Getter
    @Setter
    private ServerSetting settings;

    @OneToMany(mappedBy="buildingSettings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<BuildingInfo> buildings = new HashSet<>();

    public BuildingInfo getInfo(BuildingTypes type){
        return buildings.stream()
                    .filter(buildingInfo -> buildingInfo.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

}
