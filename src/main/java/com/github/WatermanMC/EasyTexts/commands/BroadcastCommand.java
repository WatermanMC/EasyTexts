package com.github.WatermanMC.EasyTexts.commands;

import com.github.WatermanMC.EasyTexts.ConfigManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {
    private final ConfigManager configManager;
    private final MiniMessage minimessage;

    public BroadcastCommand(ConfigManager configManager) {
        this.configManager = configManager;
        this.minimessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("easytexts.broadcast")) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("nopermission");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        if (args.length < 1) {
            String msg = configManager.getMessage("prefix") + "<red>Usage: /broadcast <msg> [showauthor: bool]";
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        boolean showAuthor = false;
        int messageWordsCount = args.length;
        String lastArg = args[args.length - 1];

        if (lastArg.equalsIgnoreCase("true") || lastArg.equalsIgnoreCase("false")) {
            showAuthor = Boolean.parseBoolean(lastArg);
            messageWordsCount--;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < messageWordsCount; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String broadcastMsg = messageBuilder.toString().trim();

        if (broadcastMsg.isEmpty()) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("broadcast_nomsg");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        String template;
        if (showAuthor) {
            template = configManager.getMessage("broadcast_author")
                    .replace("%author%", sender.getName())
                    .replace("%msg%", broadcastMsg);
        } else {
            template = configManager.getMessage("broadcast")
                    .replace("%msg%", broadcastMsg);
        }

        Bukkit.broadcast(minimessage.deserialize(template));
        return true;
    }
}