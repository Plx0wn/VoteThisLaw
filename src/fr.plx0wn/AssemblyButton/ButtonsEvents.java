package fr.plx0wn.AssemblyButton;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import fr.plx0wn.VoteTheLaw;

public class ButtonEvents implements Listener {

	@EventHandler
	public void onClickButton(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		Entity entity = e.getRightClicked();
		if (entity.getType() == EntityType.ARMOR_STAND) {
//			if (entity.getCustomName().equals(VoteTheLaw.cs("&aDébuter le vote."))) {
//				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "vote start");
//			}
//			if (entity.getUniqueId().toString() == "votethislaw-button-start") {
//				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "vote start");
//				
//			}
			player.sendMessage("" + entity.getUniqueId());

		}
	}

}
