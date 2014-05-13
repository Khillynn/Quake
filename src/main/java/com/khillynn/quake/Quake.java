package com.khillynn.quake;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Quake extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Quake is Enabled! =D");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!p.getInventory().contains(Material.WOOD_HOE))
        {
            p.getPlayer().getInventory().setItemInHand(new ItemStack(Material.WOOD_HOE, 1));
        }
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        Location loc = e.getPlayer().getLocation();

        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
            if(e.getPlayer().getItemInHand().getType() == Material.WOOD_HOE) {
                if (e.getPlayer().getExp() == 1F)
                {
                    WitherSkull bullet = (WitherSkull) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.WITHER_SKULL);
                    bullet.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5));
                    bullet.getWorld().playEffect(loc, Effect.SMOKE, 0, 50);
                    e.getPlayer().playSound(loc, Sound.GHAST_FIREBALL, 100, 0);
                    e.getPlayer().setExp(0F);
                }
            }
        }

        //this should have its own section but ill figure that out later
        Runnable runnable = new Runnable()
        {
            @Override
            public void run(){
                if (!(e.getPlayer().getExp() == 1F))
                {
                    e.getPlayer().setExp(e.getPlayer().getExp() + (1F / (10 * 20)));
                }
            }
        };
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof WitherSkull)
        {
            if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
            {
                if (e.getDamager() instanceof WitherSkull)
                {
                    if (e.getEntity() instanceof Player)
                    {
                        e.setDamage(20.0);
                    }
                }
            }
        }
    }
}
