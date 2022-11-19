package cn.linkot.spigot.login;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static Logger log;
    private final String PLAYER_CONFIG = "players.yml";
    private FileConfiguration config;
    private YamlConfiguration playerConf;
    private File playerFile;

    private EventInterceptor interceptor;

    @Override
    public void onLoad() {
        log = getLogger();
        log.info("开始初始化...");
        loadConfig();
    }
    public void loadConfig(){
        saveDefaultConfig();
        config = getConfig();
        playerConf = new YamlConfiguration();
        playerFile = new File("plugins/LinkotLogin/"+PLAYER_CONFIG);
        if (!playerFile.exists()) saveResource(PLAYER_CONFIG, false);
        try{
            playerConf.load(playerFile);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerEvent(){
        interceptor = new EventInterceptor(config, playerConf);
        Bukkit.getPluginManager().registerEvents(interceptor, this);
    }
    public void registerCommand(){
        if (Bukkit.getPluginCommand("register")!=null) Bukkit.getPluginCommand("register").setExecutor(new Register(playerFile, playerConf, interceptor));
        if (Bukkit.getPluginCommand("login")!=null) Bukkit.getPluginCommand("login").setExecutor(new Login(playerFile, playerConf, interceptor));
    }

    @Override
    public void onEnable() {
        log.info("正在启动...");
        registerEvent();
        registerCommand();
        log.info("§b已启用！");
    }
}
