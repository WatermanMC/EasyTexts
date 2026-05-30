package com.github.WatermanMC.EasyTexts.commands;

import com.github.WatermanMC.EasyTexts.ConfigManager;
import com.github.WatermanMC.EasyTexts.EasyTexts;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SendActionbarCommand implements CommandExecutor {
    private final EasyTexts plugin;
    private final ConfigManager configManager;
    private final MiniMessage minimessage;

    public SendActionbarCommand(EasyTexts plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.minimessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("easytexts.sendactionbar")) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("nopermission");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        if (args.length < 3) {
            String msg = configManager.getMessage("prefix") + "<red>Usage: /sendactionbar <player/me> <time_secs> <msg>";
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        Player target;
        String targetArg = args[0];

        if (targetArg.equalsIgnoreCase("me")) {
            if (!(sender instanceof Player playerSender)) {
                sender.sendMessage(minimessage.deserialize("<red>Only players can use 'me' as a target"));
                return true;
            }
            target = playerSender;
        } else {
            target = Bukkit.getPlayerExact(targetArg);
            if (target == null) {
                String errorMsg = configManager.getMessage("actionbar.target_offline").replace("%target%", targetArg);
                sender.sendMessage(minimessage.deserialize(configManager.getMessage("prefix") + errorMsg));
                return true;
            }
        }

        int durationSeconds;
        try {
            durationSeconds = Integer.parseInt(args[1]);
            if (durationSeconds <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("actionbar.invalid_duration");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        StringBuilder msgBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            msgBuilder.append(args[i]).append(" ");
        }
        String actionbarText = msgBuilder.toString().trim();

        new BukkitRunnable() {
            int secondsLeft = durationSeconds;

            @Override
            public void run() {
                if (!target.isOnline()) {
                    this.cancel();
                    return;
                }

                target.sendActionBar(minimessage.deserialize(actionbarText));

                secondsLeft--;
                if (secondsLeft <= 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);

        String successMsg = configManager.getMessage("actionbar.sent").replace("%target%", target.getName());
        String finalMsg = configManager.getMessage("prefix") + successMsg;
        sender.sendMessage(minimessage.deserialize(finalMsg));

        return true;
    }
}
