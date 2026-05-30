package com.github.WatermanMC.EasyTexts.commands;

import com.github.WatermanMC.EasyTexts.ConfigManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendMsgCommand implements CommandExecutor {
    private final ConfigManager configManager;
    private final MiniMessage minimessage;

    public SendMsgCommand(ConfigManager configManager) {
        this.configManager = configManager;
        this.minimessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String @NotNull [] args) {

        if (!sender.hasPermission("easytexts.sendmsg")) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("nopermission");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        if (args.length < 2) {
            String msg = configManager.getMessage("prefix") + "<red>Usage: /sendmsg <player/me> <msg>";
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        CommandSender target;
        String targetArg = args[0];

        if (targetArg.equalsIgnoreCase("me")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(minimessage.deserialize("<red>Only players can use 'me' as a target"));
                return true;
            }
            target = sender;
        } else {
            Player onlinePlayer = Bukkit.getPlayerExact(targetArg);
            if (onlinePlayer == null) {
                String msg = configManager.getMessage("prefix") + configManager.getMessage("sendmsg.target_offline")
                        .replace("%target%", targetArg);
                sender.sendMessage(minimessage.deserialize(msg));
                return true;
            }
            target = onlinePlayer;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String customMsg = messageBuilder.toString().trim();

        target.sendMessage(minimessage.deserialize(customMsg));

        if (target != sender) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("sendmsg.sent")
                    .replace("%target%", sender.getName());
            sender.sendMessage(minimessage.deserialize(msg));
        }
        return true;
    }
}