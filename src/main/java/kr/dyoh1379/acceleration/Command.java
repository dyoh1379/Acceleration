package kr.dyoh1379.acceleration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Acceleration");
    private FileConfiguration config = plugin.getConfig();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if("Game".equalsIgnoreCase(command.getName())) {
            if (args.length == 1) {
                if(sender.isOp()) {

                    if("help".equalsIgnoreCase(args[0])) {
                        sender.sendMessage(ChatColor.BOLD + "/Game start " + ChatColor.RESET + "게임을 시작합니다");
                        sender.sendMessage(ChatColor.BOLD + "/Game stop " + ChatColor.RESET + "게임을 종료합니다");
                        sender.sendMessage(ChatColor.BOLD + "/Game reset " + ChatColor.RESET + "게임 정보를 초기화합니다");
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.BOLD + "Made by. " + ChatColor.RESET + "괴다");

                    }

                    if("start".equalsIgnoreCase(args[0])) {
                        sender.sendMessage("게임 시작!");

                        config.set("Game", true);
                        plugin.saveConfig();
                    }

                    else if("stop".equalsIgnoreCase(args[0])) {
                        sender.sendMessage("게임 종료");

                        config.set("Game", false);
                        plugin.saveConfig();
                    }

                    else if("reset".equalsIgnoreCase(args[0])) {
                        sender.sendMessage("게임 초기화!");

                        for(Player p : Bukkit.getOnlinePlayers()) {
                            Score score = p.getScoreboard().getObjective("Acceleration").getScore(p.getName());
                            score.resetScore();
                        }
                    }

                    else {
                        sender.sendMessage("잘못된 인수 입니다");
                    }

                }

                else {
                    sender.sendMessage(Bukkit.getPermissionMessage().replace("&", "§"));
                }
            }

            /*
            else if(args.length == 2) {
                TODO: 'KeepSpeed <True/False>' 인수를 추가하여 사망 시 기존 속도가 유지되는지 결정 할 수 있도록 해야 함.
                    -> config 에 'KeepSpeed' (Boolean) 항목을 추가해야 함.
                    -> 'KeepSpeed' 가 'False' 일 때, 해당 유저의 Scoreboard 를 초기화 시킴으로써 속도가 초기화 되도록 할 수 있다.
                    ->
                    ->
                    -> * 만들기 귀찮다. 시험 끝나고 할까
            }
            */

            else {
                sender.sendMessage("Usage: /Game <Argument>");
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> list = Arrays.asList("start", "stop", "reset");
        String input = args[0].toLowerCase();

        List<String> completions = null;

        if(args.length == 1) {
            for(String s : list) {
                if(s.startsWith(input)) {
                    if(completions == null) {
                        completions = new ArrayList();
                    }

                    completions.add(s);
                }
            }

            if(completions != null) {
                Collections.sort(completions);
            }
        }

        return completions;
    }
}
