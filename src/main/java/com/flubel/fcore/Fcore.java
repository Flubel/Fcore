package com.flubel.fcore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Fcore extends JavaPlugin implements Listener {

    private File kdFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Create plugin folder if missing
        if (!getDataFolder().exists()) getDataFolder().mkdirs();

        // Initialize file
        kdFile = new File(getDataFolder(), "KD.yml");

        // If the file doesn't exist, create an empty one
        if (!kdFile.exists()) {
            try {
                kdFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Load configuration
        config = YamlConfiguration.loadConfiguration(kdFile);

        // Register events
        getServer().getPluginManager().registerEvents(this, this);



        getLogger().info("\u001B[38;2;23;138;214m================================================\u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ███████╗ ██████╗ ██████╗ ██████╗ ███████╗\u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔════╝\u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   █████╗  ██║     ██║   ██║██████╔╝█████╗  \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ██╔══╝  ██║     ██║   ██║██╔══██╗██╔══╝  \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ██║     ╚██████╗╚██████╔╝██║  ██║███████╗\u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ╚═╝      ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝\u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;225;215;0m                 Version: 1.0.0               \u001B[0m");
        getLogger().info("\u001B[38;2;0;255;0m                 Plugin Started               \u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m                (Made by Flubel)              \u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m================================================\u001B[0m");

    }



    @Override
    public void onDisable() {
        saveKDR();


        getLogger().info("\u001B[38;2;23;138;214m================================================\u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ███████╗ ██████╗ ██████╗ ██████╗ ███████╗\u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔════╝\u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   █████╗  ██║     ██║   ██║██████╔╝█████╗  \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ██╔══╝  ██║     ██║   ██║██╔══██╗██╔══╝  \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ██║     ╚██████╗╚██████╔╝██║  ██║███████╗\u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m   ╚═╝      ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝\u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;225;215;0m                 Version: 1.0.0               \u001B[0m");
        getLogger().info("\u001B[38;2;0;255;0m                 Plugin Started               \u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m                (Made by Flubel)              \u001B[0m");
        getLogger().info(" \u001B[0m");
        getLogger().info("\u001B[38;2;23;138;214m================================================\u001B[0m");
    }

    public void saveKDR() {
        if (config == null) return;
        try {
            config.save(kdFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (!config.contains(uuid)) {
            config.set(uuid + ".kills", 0);
            config.set(uuid + ".deaths", 0);
            saveKDR();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        addDeath(victim);

        if (killer != null && killer != victim) {
            addKill(killer);
        }
    }


    public int getKills(Player player) {
        return config.getInt(player.getUniqueId().toString() + ".kills");
    }

    public int getDeaths(Player player) {
        return config.getInt(player.getUniqueId().toString() + ".deaths");
    }

    public void addKill(Player player) {
        String uuid = player.getUniqueId().toString();
        config.set(uuid + ".kills", getKills(player) + 1);
        saveKDR();
    }

    public void addDeath(Player player) {
        String uuid = player.getUniqueId().toString();
        config.set(uuid + ".deaths", getDeaths(player) + 1);
        saveKDR();
    }

}
