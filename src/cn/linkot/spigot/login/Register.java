package cn.linkot.spigot.login;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Register implements CommandExecutor {
    YamlConfiguration players;
    EventInterceptor interceptor;
    File playerFile;

    public Register(File playerFile, YamlConfiguration players, EventInterceptor interceptor) {
        this.playerFile = playerFile;
        this.players = players;
        this.interceptor = interceptor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length!=2) return false;
        String playerName = commandSender.getName().toLowerCase();
        if (players.getString(playerName)!=null) {
            commandSender.sendMessage("你已经注册过辣，如果忘记密码请联系管理员更改！");
        }
        else {
            String password = strings[0];
            String rePassword = strings[1];

            if (password.equals(rePassword)){
                players.set(playerName, password);
                commandSender.sendMessage("§a注册成功！，请输入 /lg 登录");
                try {
                    players.save(playerFile);
                } catch (IOException e) {
                    Main.log.throwing("Register.java", "Execute Save", e);
                }
                return true;
            }else {
                commandSender.sendMessage("§c两次输入密码不同！");
            }
        }
        return false;
    }
}
