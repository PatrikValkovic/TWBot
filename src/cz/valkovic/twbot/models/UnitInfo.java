package cz.valkovic.twbot.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unit_info")
public class UnitInfo implements BaseEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="units_settings_id", nullable = false)
    @Getter
    @Setter
    private UnitsSettings unitsSettings;

    @Getter
    @Setter
    UnitTypes type;

    @Getter
    @Setter
    private Integer woodCost;

    @Getter
    @Setter
    private Integer clayCost;

    @Getter
    @Setter
    private Integer ironCost;

    @Getter
    @Setter
    private int populations;

    @Getter
    @Setter
    private double speed;

    @Getter
    @Setter
    private int attack;

    @Getter
    @Setter
    private int defense;

    @Getter
    @Setter
    private int deferenceCavallery;

    @Getter
    @Setter
    private int defenseArchers;

    @Getter
    @Setter
    private int carry;

    @Getter
    @Setter
    private double baseBuildTime;

}
