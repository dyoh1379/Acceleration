package kr.dyoh1379.acceleration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        FileConfiguration config = this.getConfig();
        config.addDefault("Game", false);
        config.addDefault("KeepInfo", false);
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginCommand("Game").setExecutor(new Command());
        getServer().getPluginCommand("Game").setTabCompleter(new Command());

        getServer().getPluginManager().registerEvents(new EventListener(), this);

        Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        if(scoreboard.getObjective("Acceleration") == null) {
            scoreboard.registerNewObjective("Acceleration", "dummy");
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(config.getBoolean("Game")) {
                        Score score = p.getScoreboard().getObjective("Acceleration").getScore(p.getName());

                        p.sendActionBar("당신의 속도: " + ChatColor.AQUA + score.getScore());

                        if(score.getScore() != 0) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2, score.getScore() - 1, true, false, true));
                        }
                    }
                }
            }
        },0 ,1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
