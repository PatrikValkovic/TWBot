package cz.valkovic.twbot.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "building_info")
public class BuildingInfo implements BaseEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="building_settings_id", nullable = false)
    @Getter
    @Setter
    private BuildingSettings buildingSettings;

    @Getter
    @Setter
    BuildingTypes type;


    @Getter
    @Setter
    int maxLevel;

    @Getter
    @Setter
    int minLevel;

    @Getter
    @Setter
    int wood;

    @Getter
    @Setter
    int clay;

    @Getter
    @Setter
    int iron;

    @Getter
    @Setter
    int pop;

    @Getter
    @Setter
    double woodFactor;

    @Getter
    @Setter
    double clayFactor;

    @Getter
    @Setter
    double ironFactor;

    @Getter
    @Setter
    double popFactor;

    @Getter
    @Setter
    int baseBuildTime;

    @Getter
    @Setter
    double buildTimeFactor;

}
