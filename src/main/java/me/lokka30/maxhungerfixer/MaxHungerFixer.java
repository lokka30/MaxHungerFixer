package me.lokka30.maxhungerfixer;

import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author lokka30
 * @since v1.0.0-ALPHA
 * Created on 19 September 2020 at 9:54 AM
 */
public class MaxHungerFixer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        new Metrics(this, 8898);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        debugLog(2, "PlayerJoinEvent fired for player " + player.getName() + ".");
        if(player.getFoodLevel() > 20) {
            debugLog(2, "> Requires adjustment.");
            adjustFoodLevel(player);
        }
    }

    @EventHandler
    public void onFoodChange(final FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            debugLog(2, "FoodLevelChangeEvent fired for player " + player.getName() + ".");
            if (event.getFoodLevel() > 20 || player.getFoodLevel() > 20) {
                debugLog(2, "> Requires adjustment.");
                adjustFoodLevel(player);
            }
        }
    }

    private void adjustFoodLevel(final Player player) {
        debugLog(1, "Adjusting food level for user " + player.getName() + "...");

        player.setFoodLevel(20);
        debugLog(2, "> Set food level to 20.");

        if (getConfig().getBoolean("second-attempt")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    debugLog(2, "> Running second attempt.");
                    player.setFoodLevel(20);
                }
            }.runTaskLater(this, 10L);
        }
    }

    private void debugLog(int requiredLevel, String msg) {
        int configuredLevel = getConfig().getInt("debug-level");
        if (requiredLevel <= configuredLevel) {
            getLogger().info("[DEBUG] " + msg);
        }
    }
}
