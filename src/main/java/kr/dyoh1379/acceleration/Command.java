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

            if(!sender.isOp()) {
                sender.sendMessage(Bukkit.getPermissionMessage().replace("&", "§"));
                return false;
            }

            switch (args.length) {

                case 1:
                    switch (args[0].toLowerCase()) {

                        case "help":
                            sender.sendMessage(ChatColor.BOLD + "/Game start " + ChatColor.RESET + "게임을 시작합니다");
                            sender.sendMessage(ChatColor.BOLD + "/Game stop " + ChatColor.RESET + "게임을 종료합니다");
                            sender.sendMessage(ChatColor.BOLD + "/Game reset " + ChatColor.RESET + "게임 정보를 초기화합니다");
                            sender.sendMessage(ChatColor.BOLD + "/Game KeepInfo <Boolean> " + ChatColor.RESET + "사망 시 정보가 유지되는지 설정합니다");
                            sender.sendMessage("");
                            sender.sendMessage(ChatColor.BOLD + "Made by. " + ChatColor.RESET + "괴다");
                            break;

                        case "start":

                            if (config.getBoolean("Game")) {
                                sender.sendMessage("이미 게임이 시작되었습니다");
                                return false;
                            }

                            sender.sendMessage("게임 시작!");

                            config.set("Game", true);
                            plugin.saveConfig();
                            break;

                        case "stop":
                            if (!config.getBoolean("Game")) {
                                sender.sendMessage("이미 게임이 종료되었습니다");
                                return false;
                            }

                            sender.sendMessage("게임 종료");

                            config.set("Game", false);
                            plugin.saveConfig();
                            break;

                        case "reset":
                            sender.sendMessage("게임 초기화!");

                            for(Player p : Bukkit.getOnlinePlayers()) {
                                Score score = p.getScoreboard().getObjective("Acceleration").getScore(p.getName());
                                score.resetScore();
                            }
                            break;

                        case "keepinfo":
                            sender.sendMessage("KeepInfo= " + config.getBoolean("KeepInfo"));
                            break;

                        default:
                            sender.sendMessage("잘못된 인수 입니다");
                            break;
                    }
                    break;

                case 2:
                    switch (args[0]) {
                        case "keepinfo":
                            if(!isBoolean(args[1])) {
                                sender.sendMessage("잘못된 인수 입니다");
                                return false;
                            }

                            if(config.getBoolean("KeepInfo") == Boolean.parseBoolean(args[1])) {
                                sender.sendMessage("이미 KeepInfo 값은 " + config.get("KeepInfo") + " 입니다");
                                return false;
                            }

                            config.set("KeepInfo", Boolean.parseBoolean(args[1]));
                            sender.sendMessage("KeepInfo 값을 " + config.get("KeepInfo") + " 로 설정했습니다");
                            break;

                        default:
                            sender.sendMessage("잘못된 인수 입니다");
                            break;
                    }
                    break;

                default:
                    sender.sendMessage("Usage: /Game <Arguments>");
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> list = Arrays.asList("help", "start", "stop", "reset", "keepinfo");
        List<String> list2 = Arrays.asList("true", "false");
        String input = args[0].toLowerCase();

        List<String> completions = null;


        switch (args.length) {
            case 1:
                for (String s : list) {
                    if (s.startsWith(input)) {
                        if (completions == null) {
                            completions = new ArrayList();
                        }

                        completions.add(s);
                    }
                }
                break;

            case 2:
                if ("keepinfo".equals(input)) {
                    for (String s : list2) {
                        if (s.startsWith(args[1].toLowerCase())) {
                            if (completions == null) {
                                completions = new ArrayList();
                            }

                            completions.add(s);
                        }
                    }
                }
                break;
        }
        return completions;
    }

    public boolean isBoolean(String s) {

        if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
            return true;
        } else {
            return false;
        }
    }
}
