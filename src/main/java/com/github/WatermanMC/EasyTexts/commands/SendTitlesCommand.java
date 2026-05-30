package com.github.WatermanMC.EasyTexts.commands;

import com.github.WatermanMC.EasyTexts.ConfigManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class SendTitlesCommand implements CommandExecutor {
    private final ConfigManager configManager;
    private final MiniMessage minimessage;

    public SendTitlesCommand(ConfigManager configManager) {
        this.configManager = configManager;
        this.minimessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("easytexts.sendtitle")) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("nopermission");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        if (args.length < 3) {
            String msg = configManager.getMessage("prefix") + "<red>Usage: /sendtitle <player/me> <time_secs> <title> [subtitle]";
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
                String errorMsg = configManager.getMessage("titles.target_offline").replace("%target%", targetArg);
                sender.sendMessage(minimessage.deserialize(configManager.getMessage("prefix") + errorMsg));
                return true;
            }
        }

        int durationSeconds;
        try {
            durationSeconds = Integer.parseInt(args[1]);
            if (durationSeconds <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            String errorMsg = configManager.getMessage("titles.invalid_duration");
            sender.sendMessage(minimessage.deserialize(configManager.getMessage("prefix") + errorMsg));
            return true;
        }

        StringBuilder rawTextBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            rawTextBuilder.append(args[i]).append(" ");
        }
        String combinedText = rawTextBuilder.toString().trim();

        String titleString;
        String subtitleString = "";

        if (combinedText.contains("|")) {
            String[] splitParts = combinedText.split("\\|", 2);
            titleString = splitParts[0].trim();
            subtitleString = splitParts[1].trim();
        } else {
            titleString = combinedText;
        }

        Component titleComponent = minimessage.deserialize(titleString);
        Component subtitleComponent = subtitleString.isEmpty() ? Component.empty() : minimessage.deserialize(subtitleString);

        Title.Times times = Title.Times.times(
                Duration.ofMillis(500),
                Duration.ofSeconds(durationSeconds),
                Duration.ofMillis(500)
        );

        Title title = Title.title(titleComponent, subtitleComponent, times);
        target.showTitle(title);
        String successMsg = configManager.getMessage("titles.sent").replace("%target%", target.getName());
        sender.sendMessage(minimessage.deserialize(configManager.getMessage("prefix") + successMsg));

        return true;
    }
}