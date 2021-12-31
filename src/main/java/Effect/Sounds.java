package Effect;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sounds
{
    public void SP(Player player, org.bukkit.Sound sound, float volume, float pitch)
    {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
    public void SPL(Player player, Location loc, org.bukkit.Sound sound, float volume, float pitch)
    {
        player.playSound(loc, sound, volume, pitch);
    }
}
