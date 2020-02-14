package cz.valkovic.twbot.modules.parsing;

public enum LOCATIONS {

    /**
     * When the application (www.divokekmeny.cz) is loaded.
     */
    APPLICATION("https://www.divokekmeny.cz/"),
    /**
     * When user needs to log in.
     */
    LOGIN,
    /**
     * When the user should pick up the server to log in.
     */
    SERVER_PICKUP,

    /**
     * When the user is in game at the specific word.
     */
    INGAME("https://cs69.divokekmeny.cz/game.php?screen=overview&intro"),

    /**
     * Visiting interface.php script.
     */
    INTERFACE,
    /**
     * Config about the word.
     */
    INTERFACE_CONFIG("https://cs69.divokekmeny.cz/interface.php?func=get_config"),
    /**
     * Config about the buildings.
     */
    INTERFACE_BUILDINGS("https://cs69.divokekmeny.cz/interface.php?func=get_building_info"),
    /**
     * Config about the units.
     */
    INTERFACE_UNITS("https://cs69.divokekmeny.cz/interface.php?func=get_unit_info"),

    /**
     * Current status of the villages, players etc.
     * Wrap all the urls /map/*
     */
    STATS,
    /**
     * Information about villages.
     */
    STATS_VILLAGES("https://cs69.divokekmeny.cz/map/village.txt"),
    /**
     * Information about players.
     */
    STATS_PLAYERS("https://cs69.divokekmeny.cz/map/player.txt"),
    /**
     * Information about tribes.
     */
    STATS_TRIBES("https://cs69.divokekmeny.cz/map/ally.txt"),
    /**
     * Statistics about opponent defeated in defence and offense by players.
     */
    STATS_KILLALL("https://cs69.divokekmeny.cz/map/kill_all.txt"),
    /**
     * Statistics about opponent defeated in offense by players.
     */
    STATS_ATT("https://cs69.divokekmeny.cz/map/kill_att.txt"),
    /**
     * Statistics about opponent defeated in defence by players.
     */
    STATS_DEF("https://cs69.divokekmeny.cz/map/kill_def.txt"),
    /**
     * Statistics about opponent defeated in defence and offense by tribes.
     */
    STATS_TRIBE_ALL("https://cs69.divokekmeny.cz/map/kill_all_tribe.txt"),
    /**
     * Statistics about opponent defeated in offense by tribes.
     */
    STATS_TRIBE_ATT("https://cs69.divokekmeny.cz/map/kill_att_tribe.txt"),
    /**
     * Statistics about opponent defeated in defence by tribes.
     */
    STATS_TRIBE_DEF("https://cs69.divokekmeny.cz/map/kill_def_tribe.txt"),
    /**
     * History about conquering.
     */
    STATS_CONQUER("https://cs69.divokekmeny.cz/map/conquer.txt"),

    /**
     * When the dashboard is shown.
     */
    DASHBOARD("https://cs69.divokekmeny.cz/game.php?screen=welcome"),

    /**
     * When list of all messages is shown.
     */
    MESSAGES("https://cs69.divokekmeny.cz/game.php?village=13176&screen=mail"),
    /**
     * When concrete message is shown.
     */
    MESSAGE_READ("https://cs69.divokekmeny.cz/game.php?village=13176&screen=mail&mode=view&view=212590&group_id=0"),

    /**
     * When village is shown.
     */
    VILLAGE("https://cs69.divokekmeny.cz/game.php?village=13176"),
    VILLAGE_OVERVIEW("https://cs69.divokekmeny.cz/game.php?village=13176&screen=overview"),
    VILLAGE_HEADQUARTER("https://cs69.divokekmeny.cz/game.php?village=13176&screen=main"), //TODO maybe exists separate view for destroying buildings?
    VILLAGE_BARRACKS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=barracks"),
    VILLAGE_STABLE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=stable"),
    VILLAGE_WORKSHOP("https://cs69.divokekmeny.cz/game.php?village=13176&screen=garage"),
    VILLAGE_CHURCH, //TODO unknown so far
    VILLAGE_ACADEMY("https://cs69.divokekmeny.cz/game.php?village=13176&screen=snob"),
    VILLAGE_SMITHY("https://cs69.divokekmeny.cz/game.php?village=13176&screen=smith"),
    VILLAGE_RALLYPOINT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place"),
    VILLAGE_RALLYPOINT_COMMANDS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place[&mode=command]"),
    VILLAGE_RALLYPOINT_UNITS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place&mode=units&display=units"),
    VILLAGE_RALLYPOINT_SCAVENGE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place&mode=scavenge"),
    VILLAGE_RALLYPOINT_MASSSCAVENGE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place&mode=scavenge_mass"), // Needs to have premium that I don't have
    VILLAGE_RALLYPOINT_SIMULATOR("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place&mode=sim"),
    VILLAGE_RALLYPOINT_TEMPLATES("https://cs69.divokekmeny.cz/game.php?village=13176&screen=place&mode=templates"),
    VILLAGE_STATUE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=statue"),
    VILLAGE_STATUE_CURRENT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=statue[&mode=resident]"),
    VILLAGE_STATUE_OVERVIEW("https://cs69.divokekmeny.cz/game.php?village=13176&screen=statue&mode=overview"),
    VILLAGE_MARKET("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market"),
    VILLAGE_MARKET_EXCHANGE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market[&mode=other_offer]"),
    VILLAGE_MARKET_PREMIUMEXCHANGE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market&mode=exchange"),
    VILLAGE_MARKET_OFFERCREATE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market&mode=own_offer"),
    VILLAGE_MARKET_SEND("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market&mode=send"),
    VILLAGE_MARKET_TRANSPORTS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market&mode=transports"),
    VILLAGE_MARKET_STATUS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market&mode=traders"),
    VILLAGE_MARKET_OFFERS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=market&mode=all_own_offer"),
    VILLAGE_WOOD("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wood"),
    VILLAGE_CLAY("https://cs69.divokekmeny.cz/game.php?village=13176&screen=stone"),
    VILLAGE_IRON("https://cs69.divokekmeny.cz/game.php?village=13176&screen=iron"),
    VILLAGE_FARM("https://cs69.divokekmeny.cz/game.php?village=13176&screen=farm"),
    VILLAGE_WAREHOUSE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=storage"),
    VILLAGE_HIDEOUT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=hide"),
    VILLAGE_WALL("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wall"),

    REPORTS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report"),
    REPORTS_ALL("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report[&mode=all]"),
    REPORTS_ATT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=attack"),
    REPORTS_DEF("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=defense"),
    REPORTS_SUPPORT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=support"),
    REPORTS_TRADE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=trade"),
    REPORTS_EVENTS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=event"),
    REPORTS_OTHER("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=other"),
    REPORTS_FORWARD("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=forwarded"),
    REPORTS_PUBLIC("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=public"),
    REPORTS_FILTER("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=filter"),
    REPORT_READ("https://cs69.divokekmeny.cz/game.php?village=13176&screen=report&mode=attack&group_id=0&view=5326629"),

    MAP("https://cs69.divokekmeny.cz/game.php?village=13176&screen=map#388;512"),

    TRIBE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally"),
    TRIBE_OVERVIEW("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally[&mode=overview]"),
    TRIBE_PROPERTIES("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally&mode=properties"),
    TRIBE_LEVEL("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally&mode=level"),
    TRIBE_MEMBERS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally&mode=members"),
    TRIBE_DIPLOMACY("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally&mode=contracts"),
    TRIBE_WARS("https://cs69.divokekmeny.cz/game.php?screen=wars&village=13176"),
    TRIBE_WARS_CURRENT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wars[&mode=running]"),
    TRIBE_WARS_INVITES("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wars&mode=invites"),
    TRIBE_WARS_DECLARATION("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wars&mode=declare"),
    TRIBE_WARS_ARCHIVE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wars&mode=archive"),
    TRIBE_WARS_SCORE("https://cs69.divokekmeny.cz/game.php?village=13176&screen=wars&mode=score"),
    TRIBE_RESERVATIONS("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally&mode=reservations"),
    TRIBE_RECRUIT("https://cs69.divokekmeny.cz/game.php?village=13176&screen=ally&mode=invite"),

    FORUM("https://cs69.divokekmeny.cz/game.php?village=13176&screen=forum"),
    FORUM_SECTION("https://cs69.divokekmeny.cz/game.php?screen=forum[&forum_id=2123]"),
    FORUM_THREAD("https://cs69.divokekmeny.cz/game.php?screen=forum&screenmode=view_thread[&forum_id=2123]&thread_id=5684");


    LOCATIONS() {}
    LOCATIONS(String example_url){}
}
