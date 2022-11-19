package cn.linkot.spigot.login;

import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class EventInterceptor implements Listener {

    private FileConfiguration config;
    private YamlConfiguration players;

    Set<String> denySet = new HashSet<>();

    public void addDenySet(String playerName){
        denySet.add(playerName.toLowerCase());
    }

    public void removeDenySet(String playerName){
        denySet.remove(playerName.toLowerCase());
    }

    public EventInterceptor(FileConfiguration config, YamlConfiguration players) {
        this.config = config;
        this.players = players;
    }

    public void cancelIfDeny(Cancellable e){
        if (e instanceof PlayerCommandPreprocessEvent){
            PlayerCommandPreprocessEvent event = (PlayerCommandPreprocessEvent) e;
            String command = event.getMessage();
            if (command.startsWith("/login")||command.startsWith("/lg")||command.startsWith("/register")||command.startsWith("/rg")){
            }else if (denySet.contains(event.getPlayer().getName().toLowerCase())) e.setCancelled(true);
        }
        else if (e instanceof PlayerEvent){
            Player player = ((PlayerEvent)e).getPlayer();
            if (denySet.contains(player.getName().toLowerCase())){
                player.sendRawMessage("§c请输入 /rg 注册，或 /lg 登录！");
                e.setCancelled(true);
            }
        }
        else if (e instanceof CraftItemEvent){
            HumanEntity player = ((CraftItemEvent)e).getWhoClicked();
            if (denySet.contains(player.getName().toLowerCase())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void login(PlayerJoinEvent event){
        Player player = event.getPlayer();
        denySet.add(player.getName().toLowerCase());
        player.setGameMode(GameMode.SPECTATOR);
    }
    @EventHandler
    public void playerMove(PlayerMoveEvent event){
        cancelIfDeny(event);
    }

    @EventHandler
    public void inventory(CraftItemEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void dropItem(PlayerDropItemEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void teleport(PlayerTeleportEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void defInter(PlayerInteractEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void entity(PlayerInteractAtEntityEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void editBook(PlayerEditBookEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void itemDamage(PlayerItemDamageEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void swapItem(PlayerSwapHandItemsEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void heldItem(PlayerItemHeldEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void consumeItem(PlayerItemConsumeEvent event){
        cancelIfDeny(event);
    }
    @EventHandler
    public void command(PlayerCommandPreprocessEvent event){
        cancelIfDeny(event);
    }
}
