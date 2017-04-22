package fr.plx0wn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.plx0wn.AssemblyButton.Button;
import fr.plx0wn.AssemblyButton.ButtonEvents;
import net.md_5.bungee.api.ChatColor;

public class VoteTheLaw extends JavaPlugin {

	public static boolean vote = false;
	public static ArrayList<Player> vote_yes = new ArrayList<>();
	public static ArrayList<Player> vote_no = new ArrayList<>();
	public static Plugin instance;
	public static File msgfile;
	public static FileConfiguration msgconf;

	public static String cs(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public void onEnable() {
		instance = this;
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ButtonEvents(), instance);
		saveDefaultConfig();
		createFiles();
	}
	
	private void createFiles() {

		msgfile = new File(getDataFolder(), "messages.yml");
		if (!msgfile.exists()) {
			msgfile.getParentFile().mkdirs();
			saveResource("messages.yml", false);
		}
		try {
			msgconf = new YamlConfiguration();
			msgconf.load(msgfile);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// PARTIE JOUEUR & CONSOLE

		if (label.equalsIgnoreCase("vote")) {
			if (args.length == 0) {
				// LISTE DES COMMANDES
				sender.sendMessage("");
				sender.sendMessage(cs("&aListe des commmandes:"));
				if (sender.hasPermission("vote.start")) {
					sender.sendMessage(cs("&a- /vote start"));
				}
				if (sender.hasPermission("vote.stop")) {
					sender.sendMessage(cs("&a- /vote stop"));
				}
				sender.sendMessage(cs("&a- /vote pour"));
				sender.sendMessage(cs("&a- /vote contre"));
				sender.sendMessage("");
			}
			if (args.length == 1) {

				if (args[0].equalsIgnoreCase("start")) {
					if (sender.hasPermission("vote.start")) {
						if (vote == false) {
							// REMISE A ZERO
							vote_yes.clear();
							vote_no.clear();

							// CONTENTS
							vote = !vote;

							// NO WORDS

							Bukkit.getServer().broadcastMessage("");
							Bukkit.getServer().broadcastMessage(cs(msgconf.getString("messages.start")));
							Bukkit.getServer().broadcastMessage("");

						} else {
							sender.sendMessage(cs(msgconf.getString("messages.already-start")));
						}
					} else {
						sender.sendMessage(cs(msgconf.getString("messages.no-perms")));
					}

				}

				if (args[0].equalsIgnoreCase("stop")) {
					if (sender.hasPermission("vote.stop")) {
						if (vote == true) {
							vote = !vote;
							;
							int yes = vote_yes.size();
							int no = vote_no.size();
							Bukkit.getServer().broadcastMessage("");
							Bukkit.getServer().broadcastMessage(cs(msgconf.getString("messages.stop")));
							Bukkit.getServer().broadcastMessage("");
							Bukkit.getServer().broadcastMessage(cs(msgconf.getString("messages.results")
									.replace("[yes]", "" + yes).replace("[no]", "" + no)));
							if (yes > no) {
								Bukkit.getServer().broadcastMessage(cs(msgconf.getString("messages.law-accepted")));
							} else {
								if (yes == no) {
									Bukkit.getServer()
											.broadcastMessage(cs(msgconf.getString("messages.law-equality")));
								} else {
									Bukkit.getServer()
											.broadcastMessage(cs(msgconf.getString("messages.law-refused")));
								}

							}
							Bukkit.getServer().broadcastMessage("");

						} else {
							sender.sendMessage(cs(msgconf.getString("messages.no-vote")));
						}
					} else {
						sender.sendMessage(cs(msgconf.getString("messages.no-perms")));
					}
				}

				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("vote.reload")) {
						try {
							reloadConfig();
							sender.sendMessage(ChatColor.GREEN + "Configs rechargés !");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(ChatColor.RED + "Les configs n'ont pu être rechargés.");
						}
					} else {
						sender.sendMessage(cs(msgconf.getString("messages.no-perms")));
					}
				}

				// PARTIE JOUEUR

				if (sender instanceof Player) {
					Player player = ((Player) sender).getPlayer();
					if (args[0].equalsIgnoreCase("pour")) {
						if (player.hasPermission("vote.vote")) {
							if (vote == true) {
								if (vote_yes.contains(player)) {
									player.sendMessage(cs(msgconf.getString("messages.already-vote")));
								} else {
									vote_yes.add(player);
									player.sendMessage(cs(msgconf.getString("messages.vote-yes")));
									if (vote_no.contains(player)) {
										vote_no.remove(player);
									}
								}
							} else {
								player.sendMessage(cs(msgconf.getString("messages.no-vote")));
							}
						} else {
							player.sendMessage(cs(msgconf.getString("messages.no-perms")));
						}
					}

					if (args[0].equalsIgnoreCase("contre")) {
						if (player.hasPermission("vote.vote")) {
							if (vote == true) {
								if (vote_no.contains(player)) {
									player.sendMessage(cs(msgconf.getString("messages.already-vote")));
								} else {
									vote_no.add(player);
									player.sendMessage(cs(msgconf.getString("messages.vote-no")));
									if (vote_yes.contains(player)) {
										vote_yes.remove(player);
									}
								}
							} else {
								player.sendMessage(cs(msgconf.getString("messages.no-vote")));
							}
						} else {
							player.sendMessage(cs(msgconf.getString("messages.no-perms")));
						}
					}
					
					// SETUP (mise en place des buttons personnalisé.
					
					if(args[0].equalsIgnoreCase("setup")){
						
					}
				}
				
				
			}
			
			
			if (args.length >= 2) {
				
				
				if(sender instanceof Player){
					// SETUP (mise en place des buttons personnalisé.
					Player player = ((Player)sender).getPlayer();
					
					if(args.length == 2){
						if(args[0].equalsIgnoreCase("setup")){
							if(args[1].equalsIgnoreCase("start")){
								//Button.spawnVoteStartButton(player);
								player.sendMessage(ChatColor.RED + "Dans la prochaine version.");
							}
							if(args[1].equalsIgnoreCase("stop")){
								//Button.spawnVoteStopButton(player);
								player.sendMessage(ChatColor.RED + "Dans la prochaine version.");
							}
						}
					}
				}
				
				// VOTE START WITH SENTENCE
				
				if(args[0].equalsIgnoreCase("start")){
					String s = "";
					for (int i = 1; i < args.length; i++) {
						String arg = args[i] + " ";
						s = s + arg;
					}
					
					Bukkit.getServer().broadcastMessage("");
					Bukkit.getServer().broadcastMessage(cs(msgconf.getString("messages.start")));
					Bukkit.getServer().broadcastMessage(cs("&a" + s));
					Bukkit.getServer().broadcastMessage("");
				}
				
			}
		}return false;
}

}
