package kr.dyoh1379.acceleration;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Score;

public class EventListener implements Listener {

    private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Acceleration");
    private FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if(config.getBoolean("Game")) {
            Score score = p.getScoreboard().getObjective("Acceleration").getScore(p.getName());
            score.setScore(score.getScore() + 1);

        }
    }
}
