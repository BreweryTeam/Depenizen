package com.denizenscript.depenizen.bukkit.objects.breweryx;

import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.dre.brewery.BPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// TODO: OfflinePlayer support
public class BPlayerTag implements ObjectTag {

    // <--[ObjectType]
    // @name BplayerTag
    // @prefix bplayer
    // @base ElementTag
    // @format
    // The identity format for brewery players is <player_name>
    // For example, 'bplayer@my_player'.
    //
    // @plugin Depenizen, BreweryX
    // @description
    // A BPlayerTag represents a Brewery player.
    //
    // -->

    @Fetchable("bplayer")
    public static BPlayerTag valueOf(String string, TagContext context) {
        if (string.startsWith("bplayer@")) {
            string = string.substring("bplayer@".length());
        }
        Player player = Bukkit.getPlayerExact(string);
        if (player == null) {
            return null;
        }
        return new BPlayerTag(player);
    }

    public static boolean matches(String arg) {
        arg = arg.replace("bplayer@", "");
        Player player = Bukkit.getPlayerExact(arg);
        if (player == null) {
            return false;
        }
        return BPlayer.hasPlayer(player);
    }

    public static BPlayerTag forPlayer(PlayerTag player) {
        return new BPlayerTag(player.getPlayerEntity());
    }

    BPlayer bPlayer = null;

    public BPlayerTag(Player player) {
        if (BPlayer.hasPlayer(player)) {
            bPlayer = BPlayer.get(player);
        }
        else {
            Debug.echoError("BPlayer referenced is null!");
        }
    }


    String prefix = "bplayer";

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String identify() {
        return "bplayer@" + bPlayer.getName();
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public ObjectTag setPrefix(String aString) {
        this.prefix = aString;
        return this;
    }


    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {
        if (attribute == null) {
            return null;
        }

        // <--[tag]
        // @attribute <BPlayerTag.name>
        // @returns ElementTag
        // @plugin Depenizen, BreweryX
        // @description
        // Returns the name of the brewery player.
        // -->
        if (attribute.startsWith("name")) {
            return new ElementTag(bPlayer.getName()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <BPlayerTag.drunkenness>
        // @returns ElementTag
        // @plugin Depenizen, BreweryX
        // @description
        // Returns the drunkness of the brewery player.
        // -->
        else if (attribute.startsWith("drunkenness")) {
            return new ElementTag(bPlayer.getDrunkeness()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <BPlayerTag.quality>
        // @returns ElementTag
        // @plugin Depenizen, BreweryX
        // @description
        // Returns the quality of the brewery player's drunkenness (drunkeness * drunkeness).
        // -->
        else if (attribute.startsWith("quality")) {
            return new ElementTag(bPlayer.getQuality()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <BPlayerTag.alcrecovery>
        // @returns ElementTag
        // @plugin Depenizen, BreweryX
        // @description
        // Returns the drunkenness reduction per minute.
        // -->
        else if (attribute.startsWith("alcrecovery")) {
            return new ElementTag(bPlayer.getQuality()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <BPlayerTag.uuid>
        // @returns ElementTag
        // @plugin Depenizen, BreweryX
        // @description
        // Returns the Brewery player's UUID.
        // -->
        else if (attribute.startsWith("uuid")) {
            return new ElementTag(bPlayer.getUuid()).getObjectAttribute(attribute.fulfill(1));
        }


        return new ElementTag(identify()).getObjectAttribute(attribute);
    }
}
