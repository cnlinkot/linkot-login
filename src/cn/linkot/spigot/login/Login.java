package cn.linkot.spigot.login;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public class Login implements CommandExecutor {
    YamlConfiguration players;
    EventInterceptor interceptor;
    File playerFile;

    public Login(File playerFile, YamlConfiguration players, EventInterceptor interceptor) {
        this.playerFile = playerFile;
        this.players = players;
        this.interceptor = interceptor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length!=1) return false;
        Player player = Bukkit.getPlayer(commandSender.getName());
        String playerName = commandSender.getName().toLowerCase();
        String password = players.getString(playerName);
        if (password==null){
            commandSender.sendMessage("§c请先注册！");
        }else {
            if (password.equals(strings[0])) {
                commandSender.sendMessage("§l§a登陆成功！");
                interceptor.removeDenySet(playerName);
                player.setGameMode(GameMode.SURVIVAL);
                return true;
            }else {
                commandSender.sendMessage("§c登陆失败！密码错误！，如果忘记密码请联系管理员更改！");
            }
        }
        return false;
    }
}
