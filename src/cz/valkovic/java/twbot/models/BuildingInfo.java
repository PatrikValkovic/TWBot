package cz.valkovic.java.twbot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "building_info")
public class BuildingInfo {

    public static final String ID = "buildinginfo_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ID, nullable = false, unique = true)
    @Getter
    @Setter
    private int Id;

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
