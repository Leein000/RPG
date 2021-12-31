package Atteck;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class DamageCalculator
{

    public Random r = new Random();

    public int CombatMinDamage(Player player, int DefaultDamage, long STR)
    {
        return (int) (STR/5) + DefaultDamage;
    }
    public int CombatMaxDamage(Player player, int DefaultDamage, long STR)
    {
        return (int) (STR/3) + DefaultDamage;
    }
    public int RangeMinDamage(Player player, int DefaultDamage, long DEX)
    {
        return (int) (DEX/5) + DefaultDamage;
    }
    public int SkillMinDamage(Player player, int DefaultDamage, long DEX)
    {
        return (int) (DEX/5) + DefaultDamage;
    }
    public int RangeMaxDamage(Player player, int DefaultDamage, long DEX)
    {
        return (int) (DEX/5) + DefaultDamage;
    }
    public int SkillMaxDamage(Player player, int DefaultDamage, long DEX)
    {
        return (int) (DEX / 5) + DefaultDamage;
    }

    public int Balance(int min, int max, int DEX)
    {
        if(min > max)
        {
            int temp = max;
            max = min;
            min = temp;
        }
        int balance = (int)(DEX/10);
        if(balance > 80) balance = 80;
        if(balance < 0) balance = 0;
        if(Random(1,100) >= balance)
        {
            return Random(Random(Random(min,max),max),max);
        }
        else
        {
            return Random(min, max);
        }
    }

    public int Critical(Player player, Long LUK, Long DEX, long damage)
    {
        int critical = (int)((LUK/20) + (DEX/30));
        if(critical > 90) critical = 90;
        if(critical < 2) critical =2;
        int chance = Random(0, 100);
        if(chance <= critical)
        {
            player.sendMessage(ChatColor.YELLOW + "critical");
            return (int)(damage * 1.5);
        }
        else
        {
            return (int)damage;
        }
    }

    public int Random(int min, int max)
    {
        return r.nextInt(max-min+1)+min;
    }

}
