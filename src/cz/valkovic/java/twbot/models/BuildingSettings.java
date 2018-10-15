package cz.valkovic.java.twbot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buildings_settings")
public class BuildingSettings {

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