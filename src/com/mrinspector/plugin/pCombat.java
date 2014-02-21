package com.mrinspector.plugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class pCombat extends JavaPlugin implements Listener {

	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	 
		@EventHandler
		  public void onAntiLogQuit(PlayerQuitEvent event)
		  {
		    Player p = event.getPlayer();
		    if (this.antilog.contains(p.getName())) {
		      Bukkit.getServer().broadcastMessage(ChatColor.GRAY + p.getName() + " has combat logged and a villager has been spawned!");
		      p.damage(20.0);
		      Bukkit.getServer().getWorld("world").spawnEntity(p.getLocation(), EntityType.VILLAGER);

		    }
		  }
		  
	 
	  @EventHandler
	  public void onAntiLogDmg(EntityDamageByEntityEvent event) {
	    if (((event.getDamager() instanceof Player)) && 
	      ((event.getEntity() instanceof Player))) {
	      final Player player = (Player)event.getEntity();
	      final Player target = (Player)event.getDamager();

	      if ((!this.antilog.contains(player.getName())) && 
	        (!this.antilog.contains(target.getName()))) {
	        this.antilog.add(player.getName());
	        this.antilog.add(target.getName());
	        player.sendMessage(ChatColor.GOLD + "You're now in " + ChatColor.UNDERLINE + "Combat!");
	        target.sendMessage(ChatColor.GOLD + "You're now in " + ChatColor.UNDERLINE + "Combat!");
	        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	          public void run() {
	            if ((antilog.contains(player.getName())) && 
	              (antilog.contains(target.getName()))) {
	             antilog.remove(player.getName());
	             antilog.remove(target.getName());
	             target.sendMessage(ChatColor.GREEN + "You can now log out safely.");
	             player.sendMessage(ChatColor.GREEN + "You can now log out safely.");
	            }
	          }
	        }
	        , 1000L);
	      }
	    }
	  }





public ArrayList<String> antilog = new ArrayList<String>();

}
