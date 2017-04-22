package fr.plx0wn.AssemblyButton;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import fr.plx0wn.VoteTheLaw;

public class Button {

	public static FileConfiguration config = VoteTheLaw.instance.getConfig();

	public static void spawnVoteStartButton(Player player) {
		Location loc = player.getLocation();
		ArmorStand am = (ArmorStand) player.getWorld().spawn(loc, ArmorStand.class);
		am.setVisible(false);
		am.setCustomName(VoteTheLaw.cs(config.getString("buttons.start.name")));
		am.setCustomNameVisible(true);
		am.setGravity(false);
		am.setCanPickupItems(false);
		am.setArms(false);
		am.setHeadPose(new EulerAngle(0, 0, 0));
		am.setSmall(true);
		am.setBasePlate(false);
		am.setHelmet(new ItemStack(Material.getMaterial(config.getString("buttons.start.block")),
				(byte) config.getInt("buttons.start.id")));
	}

	public static void spawnVoteStopButton(Player player) {
		Location loc = player.getLocation();
		ArmorStand am = (ArmorStand) player.getWorld().spawn(loc, ArmorStand.class);
		am.setVisible(false);
		am.setCustomName(VoteTheLaw.cs(config.getString("buttons.stop.name")));
		am.setCustomNameVisible(true);
		am.setGravity(false);
		am.setCanPickupItems(false);
		am.setArms(false);
		am.setHeadPose(new EulerAngle(0, 0, 0));
		am.setSmall(true);
		am.setBasePlate(false);
		am.setHelmet(new ItemStack(Material.getMaterial(config.getString("buttons.stop.block")),
				(byte) config.getInt("buttons.stop.id")));
	}
}
