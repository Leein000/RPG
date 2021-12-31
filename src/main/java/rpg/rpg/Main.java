package rpg.rpg;

import Atteck.Attack;
import Event.BlockBreak;
import Event.MonsterKill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import Stat.Stat;
import Stat.StatGUI;

public class Main extends JavaPlugin implements Listener
{
    public Stat s = new Stat();
    public Attack A = new Attack();
    public StatGUI SGUI = new StatGUI();
    public BlockBreak BB = new BlockBreak();
    public MonsterKill MK = new MonsterKill();

    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "RPG 스텟 Plugin 활성화");
    }

    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "RPG 스텟 Plugin 비활성화");
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
        s.CreateNewStat(event.getPlayer().getUniqueId().toString());
        event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.WHITE + "님께서 입장하셨습니다");
    }

    @EventHandler
    public void Chat(PlayerChatEvent event)
    {
        Player player = event.getPlayer();
        long[] stat = new long[9];
        stat = s.getStat(player.getUniqueId().toString());

        player.sendMessage("나의 레벨 : " + stat[0] + "  나의 힘" + stat[5]);
        SGUI.StatusGUI((Player) player);
    }

    public boolean onCommand(CommandSender player, Command command, String string, String[] args)
    {
        if(player instanceof Player)
        {
            if(string.equalsIgnoreCase("스텟"))
            {
                SGUI.StatusGUI((Player) player);
                return true;
            }
        }

        return false;
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event)
    {
        if(ChatColor.stripColor(event.getInventory().getType().name()) == "스텟" )
        {
            SGUI.StatGUIClicked(event);
        }
    }

    @EventHandler
    public void EntityAttack(EntityDamageByEntityEvent event)
    {A.Attack(event);}

    @EventHandler
    public void RangeAttack(EntityShootBowEvent event)
    {A.RangeAttack(event);}

    @EventHandler
    public void BlockB(BlockBreakEvent event)
    {
        BB.BB(event);
    }
    @EventHandler
    public void MK(EntityDeathEvent event)
    {
        MK.MonsterKill(event);
    }
}
