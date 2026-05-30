package com.github.WatermanMC.EasyTexts;

import com.github.WatermanMC.EasyTexts.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyTexts extends JavaPlugin {
    private ConfigManager configManager;
    private AutomaticBroadcaster ab;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        configManager.loadConfigs();
        registerCommands();
        ab = new AutomaticBroadcaster(this, configManager);
        ab.startBroadcasting();
        getLogger().info("Enabling EasyTexts v" + getPluginMeta().getVersion() + "...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling EasyTexts v" + getPluginMeta().getVersion() + "...");
    }

    public String getDiscordHelp() {
        return "Cant fix it? Join on our fast discord support: https://discord.gg/Scgqfm5EU4";
    }

    private void registerCommands() {
        new EasyTextsCommand(this, configManager, ab);
        new BroadcastCommand(configManager);
        new SendMsgCommand(configManager);
        new SendActionbarCommand(this, configManager);
        new SendTitlesCommand(configManager);
    }
}
