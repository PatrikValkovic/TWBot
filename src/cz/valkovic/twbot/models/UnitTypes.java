package cz.valkovic.twbot.models;

import lombok.Getter;

public enum UnitTypes {

    SpearFighter("Spear fighter"),
    SwordFighter("Sword fighter"),
    AxeFighter("Axe fighter"),
    Archer("Archer"),
    Scout("Scout"),
    LightCavalry	("Light Cavalry"),
    MountedArcher("Mounted archer"),
    HeavyCavalry("Heavy Cavalry"),
    Ram("Ram"),
    Catapult("Catapult"),
    Paladin("Paladin"),
    Nobleman("Nobleman");

    @Getter
    private String name;

    UnitTypes(String name){
        this.name = name;
    }

}















