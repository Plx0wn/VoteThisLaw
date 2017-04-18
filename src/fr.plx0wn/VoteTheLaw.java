package fr.plx0wn;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class VoteTheLaw extends JavaPlugin {

	public static boolean vote = false;
	public static ArrayList<Player> vote_yes = new ArrayList<>();
	public static ArrayList<Player> vote_no = new ArrayList<>();

	public String cs(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public void onEnable() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {

			Player player = ((Player) sender).getPlayer();

			if (label.equalsIgnoreCase("vote")) {
				if (args.length == 0) {
					// LISTE DES COMMANDES
					player.sendMessage("");
					player.sendMessage(cs("Liste des commmandes:"));
					if(player.hasPermission("vote.start")){
						player.sendMessage(cs("&a- /vote start"));
					}
					if(player.hasPermission("vote.stop")){
						player.sendMessage(cs("&a- /vote stop"));
					}
					player.sendMessage(cs("&a- /vote pour"));
					player.sendMessage(cs("&a- /vote contre"));
					player.sendMessage("");
				}
				if (args.length == 1) {
					
					
					if (args[0].equalsIgnoreCase("start")) {
						if (player.hasPermission("vote.start")) {
							if (vote == false) {
								// REMISE A ZERO
								vote_yes.clear();
								vote_no.clear();

								// CONTENTS
								vote = !vote;
								Bukkit.getServer().broadcastMessage("");
								Bukkit.getServer().broadcastMessage(cs(getConfig().getString("messages.start")));
								Bukkit.getServer().broadcastMessage("");
							} else {
								player.sendMessage(cs(getConfig().getString("messages.already-start")));
							}
						} else {
							player.sendMessage(cs(getConfig().getString("messages.no-perms")));
						}
					}
					
					
					if (args[0].equalsIgnoreCase("stop")) {
						if (player.hasPermission("vote.stop")) {
							if (vote == true) {
								vote = !vote;
								;
								int yes = vote_yes.size();
								int no = vote_no.size();
								Bukkit.getServer().broadcastMessage("");
								Bukkit.getServer().broadcastMessage(cs(getConfig().getString("messages.stop")));
								Bukkit.getServer().broadcastMessage("");
								Bukkit.getServer().broadcastMessage(cs(getConfig().getString("messages.results")
										.replace("[yes]", "" + yes).replace("[no]", "" + no)));
								if (yes > no) {
									Bukkit.getServer()
											.broadcastMessage(cs(getConfig().getString("messages.law-accepted")));
								} else {
									if (yes == no) {
										Bukkit.getServer()
										.broadcastMessage(cs(getConfig().getString("messages.law-equality")));
									} else {
										Bukkit.getServer()
												.broadcastMessage(cs(getConfig().getString("messages.law-refused")));
									}

								}
								Bukkit.getServer().broadcastMessage("");

							} else {
								player.sendMessage(cs(getConfig().getString("messages.no-vote")));
							}
						} else {
							player.sendMessage(cs(getConfig().getString("messages.no-perms")));
						}
					}
					
					
					if (args[0].equalsIgnoreCase("pour")) {
						if (player.hasPermission("vote.vote")) {
							if (vote == true) {
								if (vote_yes.contains(player)) {
									player.sendMessage(cs(getConfig().getString("messages.already-vote")));
								} else {
									vote_yes.add(player);
									player.sendMessage(cs(getConfig().getString("messages.vote-yes")));
									if (vote_no.contains(player)) {
										vote_no.remove(player);
									}
								}
							} else {
								player.sendMessage(cs(getConfig().getString("messages.no-vote")));
							}
						} else {
							player.sendMessage(cs(getConfig().getString("messages.no-perms")));
						}
					}
					
					
					if (args[0].equalsIgnoreCase("contre")) {
						if (player.hasPermission("vote.vote")) {
							if (vote == true) {
								if (vote_no.contains(player)) {
									player.sendMessage(cs(getConfig().getString("messages.already-vote")));
								} else {
									vote_no.add(player);
									player.sendMessage(cs(getConfig().getString("messages.vote-no")));
									if (vote_yes.contains(player)) {
										vote_yes.remove(player);
									}
								}
							} else {
								player.sendMessage(cs(getConfig().getString("messages.no-vote")));
							}
						} else {
							player.sendMessage(cs(getConfig().getString("messages.no-perms")));
						}
					}
					
					
					if (args[0].equalsIgnoreCase("reload")) {
						if (player.hasPermission("vote.reload")) {
							try {
								reloadConfig();
								player.sendMessage(ChatColor.GREEN + "Configs rechargés !");
							} catch (Exception e) {
								e.printStackTrace();
								player.sendMessage(ChatColor.RED + "Les configs n'ont pu être rechargés.");
							}
						} else {
							player.sendMessage(cs(getConfig().getString("messages.no-perms")));
						}
					}
					

				}
			}
		}
		return false;
	}

}
