package com.github.WatermanMC.EasyTexts.commands;

import com.github.WatermanMC.EasyTexts.ConfigManager;
import com.github.WatermanMC.EasyTexts.EasyTexts;
import com.github.WatermanMC.EasyTexts.AutomaticBroadcaster;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public class EasyTextsCommand implements CommandExecutor {
    private final MiniMessage minimessage;
    private final EasyTexts plugin;
    private final ConfigManager configManager;
    private final AutomaticBroadcaster ab;

    public EasyTextsCommand(
            EasyTexts plugin,
            ConfigManager configManager,
            AutomaticBroadcaster ab) {
        this.plugin = plugin;
        this.minimessage = MiniMessage.miniMessage();
        this.configManager = configManager;
        this.ab = ab;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("easytexts.admin")) {
            String msg = configManager.getMessage("prefix") + configManager.getMessage("nopermission");
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        if (args.length < 1) {
            String msg =  configManager.getMessage("prefix") + "<red>Usage: /easytexts <info/reload>";
            sender.sendMessage(minimessage.deserialize(msg));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                boolean success = configManager.reloadConfigs();

                if (success) {
                    ab.startBroadcasting();
                    String msg = configManager.getMessage("prefix") + configManager.getMessage("reloaded");
                    sender.sendMessage(minimessage.deserialize(msg));
                } else {
                    sender.sendMessage(minimessage.deserialize("<red>Plugin reload failed. Please check your console for errors."));
                }
                return true;
            }
            case "info" -> {
                sender.sendMessage(minimessage.deserialize("<green><b>EasyTexts <reset><white>v" + plugin.getPluginMeta().getVersion()));
                sender.sendMessage(minimessage.deserialize("<gray>Lightweight text utility for your server!"));
                sender.sendMessage(minimessage.deserialize("<gray>Author: <white>" + plugin.getPluginMeta().getAuthors()));
                return true;
            }
            default -> {
                String msg = configManager.getMessage("prefix") + "<red>Usage: /easytexts <info/reload>";
                sender.sendMessage(minimessage.deserialize(msg));
                return true;
            }
        }
    }
}
