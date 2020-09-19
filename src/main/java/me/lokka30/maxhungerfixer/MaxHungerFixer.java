package me.lokka30.maxhungerfixer;

import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author lokka30
 * @since v1.0.0-ALPHA
 * Created on 19 September 2020 at 9:54 AM
 */
public class MaxHungerFixer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        new Metrics(this, 8898);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if(player.getFoodLevel() > 20) {
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onFoodChange(final FoodLevelChangeEvent event) {
        if(event.getFoodLevel() > 20) {
            event.setFoodLevel(20);
        }
    }
}
