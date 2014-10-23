/*
 * Copyright (c) IntellectualCrafters - 2014. You are not allowed to distribute
 * and/or monetize any of our intellectual property. IntellectualCrafters is not
 * affiliated with Mojang AB. Minecraft is a trademark of Mojang AB.
 * 
 * >> File = Clear.java >> Generated by: Citymonstret at 2014-08-09 01:41
 */

package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.*;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Citymonstret on 2014-08-01.
 */
public class Swap extends SubCommand {

	public Swap() {
		super(Command.SWAP, "Swap two plots", "copy", CommandCategory.ACTIONS, true);
	}

	@Override
	public boolean execute(Player plr, String... args) {
	    if (args.length < 1) {
	        PlayerFunctions.sendMessage(plr, C.NEED_PLOT_ID);
	        PlayerFunctions.sendMessage(plr, C.SWAP_SYNTAX);
            return false;
        }
		if (!PlayerFunctions.isInPlot(plr)) {
		    PlayerFunctions.sendMessage(plr, C.NOT_IN_PLOT);
			return false;
		}
		Plot plot = PlayerFunctions.getCurrentPlot(plr);
		if (((plot == null) || !plot.hasOwner() || !plot.getOwner().equals(plr.getUniqueId()))
				&& !PlotMain.hasPermission(plr,"plots.admin")) {
			PlayerFunctions.sendMessage(plr, C.NO_PLOT_PERMS);
			return false;
		}
		if (plot!=null && plot.settings.isMerged()) {
            PlayerFunctions.sendMessage(plr, C.UNLINK_REQUIRED);
            return false;
        }
		String id = args[0];
		PlotId plotid;
		World world = plr.getWorld();
		try {
            plotid = new PlotId(Integer.parseInt(id.split(";")[0]), Integer.parseInt(id.split(";")[1]));
            Plot plot2 = PlotMain.getPlots(world).get(plotid);
            if ((plot2==null || !plot2.hasOwner() || plot2.owner!=plr.getUniqueId()) && !PlotMain.hasPermission(plr,"plots.admin")) {
                PlayerFunctions.sendMessage(plr, C.NO_PERM_MERGE, plotid.toString());
                return false;
            }
        }
        catch (Exception e) {
            PlayerFunctions.sendMessage(plr, C.NOT_VALID_PLOT_ID);
            PlayerFunctions.sendMessage(plr, C.SWAP_SYNTAX);
            return false;
        }
        PlotSelection.swap(world, plot.id, plotid);
        PlayerFunctions.sendMessage(plr, C.SWAP_SUCCESS);
		return true;
	}
}
