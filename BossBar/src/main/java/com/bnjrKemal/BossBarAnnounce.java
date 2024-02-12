package com.bnjrKemal;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BossBarAnnounce extends JavaPlugin implements Listener {

    private BossBar bossBar;
    private int number = 0;
    private int delay;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        bossBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_10);

        getServer().getPluginManager().registerEvents(this, this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        delay = getConfig().getInt("delay");
        getServer().getScheduler().runTaskTimer(this, this::updateBossBar, 0, 20 * delay);
    }

    @Override
    public void onDisable() {
        bossBar.removeAll();
    }

    private void updateBossBar() {
        reloadConfig();
        delay = getConfig().getInt("delay");
        List<String> messages = getConfig().getStringList("messages");
        ++number;
        if(messages.size() == number) number = 0;
        bossBar.setTitle(messages.get(number));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        bossBar.addPlayer(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        bossBar.removePlayer(player);
    }

}
