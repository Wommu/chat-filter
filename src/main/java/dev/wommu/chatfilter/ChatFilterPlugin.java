package dev.wommu.chatfilter;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class ChatFilterPlugin extends JavaPlugin implements Listener {

    private Set<String> words;
    private String      message;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        words   = new HashSet<>(config.getStringList("words"));
        message = config.getString("message", "&cYour message has been blocked.");
        if(message != null)
            message = ChatColor.translateAlternateColorCodes('&', message);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        for(String word : words) {
            if(message.contains(word)) {
                event.getPlayer().sendMessage(message);
                event.setCancelled(true);
                break;
            }
        }
    }
}
