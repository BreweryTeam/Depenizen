package net.gnomeffinway.depenizen.support.plugins;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import net.aufdemrand.denizen.objects.dLocation;
import net.aufdemrand.denizen.objects.dNPC;
import net.aufdemrand.denizen.objects.dPlayer;
import net.aufdemrand.denizencore.tags.TagContext;
import net.aufdemrand.denizencore.objects.dList;
import net.aufdemrand.denizencore.tags.Attribute;
import net.gnomeffinway.depenizen.extensions.factions.FactionsLocationExtension;
import net.gnomeffinway.depenizen.extensions.factions.FactionsPlayerNPCExtension;
import net.gnomeffinway.depenizen.objects.dFaction;
import net.gnomeffinway.depenizen.support.Support;

import java.util.ArrayList;

public class FactionsSupport extends Support {

    public FactionsSupport() {
        registerObjects(dFaction.class);
        registerProperty(FactionsPlayerNPCExtension.class, dPlayer.class, dNPC.class);
        registerProperty(FactionsLocationExtension.class, dLocation.class);
        registerAdditionalTags("factions", "faction");
    }

    @Override
    public String additionalTags(Attribute attribute, TagContext tagContext) {

        if (attribute.startsWith("factions")) {

            attribute = attribute.fulfill(1);

            // <--[tag]
            // @attribute <factions.list_factions>
            // @returns dList(dFaction)
            // @description
            // Returns a list of all current factions.
            // @plugin Depenizen, Factions
            // -->
            if (attribute.startsWith("list_factions")) {
                ArrayList<dFaction> factions = new ArrayList<dFaction>();

                for (Faction f : FactionColl.get().getAll()) {
                    factions.add(new dFaction(f));
                }

                return new dList(factions).getAttribute(attribute.fulfill(1));
            }

        }

        else if (attribute.startsWith("faction")) {

            String nameOrId = attribute.getContext(1);
            Faction f = FactionColl.get().getByName(nameOrId);
            if (f == null && FactionColl.get().containsId(nameOrId)) {
                f = FactionColl.get().get(nameOrId);
            }
            if (f != null) {
                return new dFaction(f).getAttribute(attribute.fulfill(1));
            }

        }

        return null;

    }

}
