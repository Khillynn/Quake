package com.khillynn.quake;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Quake extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Quake is Enabled! =D");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        e.getPlayer().setExp(1F);
        if(!p.getInventory().contains(Material.WOOD_HOE))
        {
            p.getPlayer().getInventory().setItemInHand(new ItemStack(Material.WOOD_HOE, 1));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        e.getPlayer().setExp(1F);
        if(!p.getInventory().contains(Material.WOOD_HOE))
        {
            p.getPlayer().getInventory().setItemInHand(new ItemStack(Material.WOOD_HOE, 1));
        }
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        Location loc = e.getPlayer().getEyeLocation();

        if((e.getAction() == Action.RIGHT_CLICK_AIR) && (e.getPlayer().getItemInHand().getType() == Material.WOOD_HOE) && (e.getPlayer().getExp() == 1F)) {

            double pitch = ((loc.getPitch() + 90) * Math.PI) / 180;
            double yaw  = ((loc.getYaw() + 90)  * Math.PI) / 180;

            double x = Math.sin(pitch) * Math.cos(yaw);
            double y = Math.sin(pitch) * Math.sin(yaw);
            double z = Math.cos(pitch);

            Vector vector = new Vector(x, z, y).multiply(1.5);

            e.getPlayer().launchProjectile(WitherSkull.class).setVelocity(vector);
            e.getPlayer().playSound(loc, Sound.GHAST_FIREBALL, 100, 0);
            e.getPlayer().setExp(0F);
        }

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
            public void run() {
                e.getPlayer().setExp(1F);
            }
        }, 40L);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        event.blockList().clear();
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof WitherSkull)
        {
            if((e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) && (e.getDamager() instanceof WitherSkull) && (e.getEntity() instanceof Player))
            {
                        e.setDamage(30.0);
            }
        }
    }
}
