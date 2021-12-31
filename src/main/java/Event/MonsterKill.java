package Event;

import Stat.Stat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class MonsterKill
{

    public Stat s = new Stat();

    public void MonsterKill(EntityDeathEvent event)
    {
        if(event.getEntity().getLastDamageCause().getCause().toString() == "ENTITY_ATTACK")
        {
            if(event.getEntity().getKiller() != null)
            {
                if(Bukkit.getServer().getPlayer((event.getEntity().getKiller().getName())).isOnline() == true)
                {
                    Player player = event.getEntity().getKiller();
                    if(event.getEntityType() == EntityType.CREEPER)
                    {
                        long[] stat = new long[9];
                        stat = s.getStat(player.getUniqueId().toString());
                        stat[2] = stat[2] +50;
                        s.setStat(player.getUniqueId().toString(), stat);
                        player.sendMessage(ChatColor.GREEN + "크리퍼" + ChatColor.WHITE + "를 사냥하여 경험치 50을 획득하였습니다");
                        s.LevelUP(stat, player);
                    }
                    if(event.getEntityType() == EntityType.ZOMBIE)
                    {
                        long[] stat = new long[9];
                        stat = s.getStat(player.getUniqueId().toString());
                        stat[2] = stat[2] +50;
                        s.setStat(player.getUniqueId().toString(), stat);
                        player.sendMessage(ChatColor.DARK_GREEN + "좀비" + ChatColor.WHITE + "를 사냥하여 경험치 50을 획득하였습니다");
                        s.LevelUP(stat, player);
                    }
                    if(event.getEntityType() == EntityType.SKELETON)
                    {
                        long[] stat = new long[9];
                        stat = s.getStat(player.getUniqueId().toString());
                        stat[2] = stat[2] + 50;
                        s.setStat(player.getUniqueId().toString(), stat);
                        player.sendMessage(ChatColor.GRAY + "스켈레톤" + ChatColor.WHITE + "을 사냥하여 경험치 50을 획득하였습니다");
                        s.LevelUP(stat, player);
                    }
                    if(event.getEntityType() == EntityType.SLIME) {
                        long[] stat = new long[9];
                        stat = s.getStat(player.getUniqueId().toString());
                        stat[2] = stat[2] + 70;
                        s.setStat(player.getUniqueId().toString(), stat);
                        player.sendMessage(ChatColor.GREEN + "슬라임" + ChatColor.WHITE + "을 사냥하여 경험치 70을 획득하였습니다");
                        s.LevelUP(stat, player);
                    }
                }
            }
        }
    }
}
