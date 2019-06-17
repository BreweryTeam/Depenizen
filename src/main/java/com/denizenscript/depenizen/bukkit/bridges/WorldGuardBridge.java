package com.denizenscript.depenizen.bukkit.bridges;

import com.denizenscript.depenizen.bukkit.commands.worldguard.RegionCommand;
import com.denizenscript.depenizen.bukkit.properties.worldguard.WorldGuardCuboidProperties;
import com.denizenscript.depenizen.bukkit.properties.worldguard.WorldGuardWorldProperties;
import com.denizenscript.depenizen.bukkit.Bridge;
import net.aufdemrand.denizen.objects.dCuboid;
import net.aufdemrand.denizen.objects.dLocation;
import net.aufdemrand.denizen.objects.dPlayer;
import net.aufdemrand.denizen.objects.dWorld;
import net.aufdemrand.denizen.utilities.DenizenAPI;
import net.aufdemrand.denizen.utilities.debugging.dB;
import net.aufdemrand.denizencore.objects.ObjectFetcher;
import net.aufdemrand.denizencore.objects.TagRunnable;
import net.aufdemrand.denizencore.objects.properties.PropertyParser;
import net.aufdemrand.denizencore.tags.ReplaceableTagEvent;
import net.aufdemrand.denizencore.tags.Attribute;
import com.denizenscript.depenizen.bukkit.properties.worldguard.WorldGuardLocationProperties;
import com.denizenscript.depenizen.bukkit.properties.worldguard.WorldGuardPlayerProperties;
import com.denizenscript.depenizen.bukkit.objects.worldguard.WorldGuardRegion;
import net.aufdemrand.denizencore.tags.TagManager;

public class WorldGuardBridge extends Bridge {

    public static WorldGuardBridge instance;

    @Override
    public void init() {
        instance = this;
        ObjectFetcher.registerWithObjectFetcher(WorldGuardRegion.class);
        PropertyParser.registerProperty(WorldGuardLocationProperties.class, dLocation.class);
        PropertyParser.registerProperty(WorldGuardPlayerProperties.class, dPlayer.class);
        PropertyParser.registerProperty(WorldGuardCuboidProperties.class, dCuboid.class);
        PropertyParser.registerProperty(WorldGuardWorldProperties.class, dWorld.class);
        DenizenAPI.getCurrentInstance().getCommandRegistry().registerCoreMember(RegionCommand.class,
                "REGION", "region [{add} <cuboid>/remove <world>] [id:<name>]", 2);
        TagManager.registerTagHandler(new TagRunnable.RootForm() {
            @Override
            public void run(ReplaceableTagEvent event) {
                tagEvent(event);
            }
        }, "region");
    }

    public void tagEvent(ReplaceableTagEvent event) {
        Attribute attribute = event.getAttributes();
        if (attribute.startsWith("region") && attribute.hasContext(1)) {
            WorldGuardRegion region = WorldGuardRegion.valueOf(attribute.getContext(1));
            if (region != null) {
                event.setReplacedObject(region.getObjectAttribute(attribute.fulfill(1)));
            }
            else if (!event.hasAlternative()) {
                dB.echoError("Unknown WorldGuard region '" + attribute.getContext(1) + "' for region[] tag.");
            }
        }
    }
}
