package Event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.block.Block;

import Stat.Stat;

public class BlockBreak
{
    public Stat s = new Stat();

    public void BB(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType() == Material.STONE)
        {
            long[] stat = new long[9];
            stat = s.getStat(player.getUniqueId().toString());
            stat[2] = stat[2] +1;
            s.setStat(player.getUniqueId().toString(), stat);
            player.sendMessage(ChatColor.GRAY + "돌" + ChatColor.WHITE + "을 채집하여 경험치 1을 획득하였습니다");
            s.LevelUP(stat, player);
        }
        if(block.getType() == Material.DIAMOND_ORE)
        {
            long[] stat = new long[9];
            stat = s.getStat(player.getUniqueId().toString());
            stat[2] = stat[2] +10;
            player.sendMessage(ChatColor.GRAY + "돌" + ChatColor.WHITE + "을 채집하여 경험치 10을 획득하였습니다");
            s.LevelUP(stat, player);
        }
    }
}
