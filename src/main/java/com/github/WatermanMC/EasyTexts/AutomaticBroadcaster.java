package com.github.WatermanMC.EasyTexts;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AutomaticBroadcaster {
    private final EasyTexts plugin;
    private final ConfigManager configManager;
    private BukkitTask currentTask;

    public AutomaticBroadcaster(EasyTexts plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void startBroadcasting() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }

        if (!configManager.getConfig().getBoolean("random-broadcasts.enabled")) return;

        long intervalSeconds = configManager.getConfig().getLong("random-broadcasts.interval", 600);
        long intervalTicks = intervalSeconds * 20;

        if (intervalTicks < 20) {
            plugin.getLogger().warning("'random-broadcasts.interval' is so low, disabling Auto broadcaster");
            return;
        }

        this.currentTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            List<String> messages = configManager.getConfigList("random-broadcasts.texts");
            if (messages == null || messages.isEmpty()) return;

            int randomIndex = ThreadLocalRandom.current().nextInt(messages.size());
            String randomMessage = messages.get(randomIndex);
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize(randomMessage));
        }, intervalTicks, intervalTicks);
    }
}
