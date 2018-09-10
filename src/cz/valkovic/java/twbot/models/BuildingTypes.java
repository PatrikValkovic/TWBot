package cz.valkovic.java.twbot.models;

import lombok.Getter;

public enum BuildingTypes {

    Headquarters("Headquarters"),
    Barracks("Barracks"),
    Stable("Stable"),
    Workshop("Workshop"),
    Academy("Academy"),
    Smithy("Smithy"),
    RallyPoint("Rally Point"),
    Statue("Statue"),
    Market("Market"),
    TimberCamp("Timber Camp"),
    ClayPit("Clay Pit"),
    IronMine("Iron Mine"),
    Farm("Farm"),
    Warehouse("Warehouse"),
    HidingPlace("Hiding Place"),
    Wall("Wall");

    @Getter
    private String name;

    BuildingTypes(String name){
        this.name = name;
    }

}















