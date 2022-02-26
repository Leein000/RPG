package Atteck;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import Stat.Stat;
import Effect.Sounds;
import org.bukkit.event.entity.EntityShootBowEvent;

public class Attack
{

    public Stat s = new Stat();
    public DamageCalculator DC = new DamageCalculator();
    public Sounds S = new Sounds();

    @EventHandler
    public void RangeAttack(EntityShootBowEvent event)
    {
        if(event.getEntityType() == EntityType.PLAYER)
        {
            Player player = (Player) event.getEntity();
            long[] stat = new long[9];
            stat = s.getStat(player.getUniqueId().toString());
        }
    }

    public void Attack(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() != null && event.getDamager() instanceof Projectile)
        {
            Projectile p = (Projectile) event.getDamager();
            if(p.getType() == EntityType.ARROW)
            {
                if(p.getShooter() != null && p.getShooter() instanceof Player && event.getEntity() instanceof Player)
                {
                    PlayerDamageByPlayer(event, (Player)p.getShooter(), (Player) event.getEntity(), (int)event.getDamage(), "활공격");
                }
                if(p.getShooter() != null && p.getShooter() instanceof Player && !(event.getEntity() instanceof Player))
                {
                    EntityDamageByPlayer(event, (Player)p.getShooter(), (int)event.getDamage(), "활공격");
                }
                if(p.getShooter() != null && !(p.getShooter() instanceof Player) && !(event.getEntity() instanceof Player))
                {
                    PlayerDamageByPlayer(event, (Player) event.getEntity());
                }
            }
        }
        else
        {
            if(event.getDamager() !=null && event.getDamager() instanceof Entity)
            {


                if(event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player)
                {
                    PlayerDamageByPlayer(event, (Player)event.getDamager(), (Player) event.getEntity(), (int)event.getDamage(), "근접공격");
                }
                if(event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player)
                {
                    EntityDamageByPlayer(event, (Player)event.getDamager(), (int)event.getDamage(), "활공격");
                }
                if(event.getDamager() != null && !(event.getDamager() instanceof Player) && event.getEntity() instanceof Player)
                {
                    PlayerDamageByEntity(event, (Player)event.getEntity());
                }
            }
        }
    }

    private void PlayerDamageByPlayer(EntityDamageByEntityEvent event, Player entity) {
    }

    public void PlayerDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, Player defenser, int DefaultDamage, String AttackType)
    {
        long[] Astat = new long[9];
        Astat = s.getStat(attacker.getUniqueId().toString());
        long[] Dstat = new long[9];
        Dstat = s.getStat(defenser.getUniqueId().toString());
        if(AttackType == "활공격")
        {
            long[] AStat;
            long Damage = DC.Balance(DC.RangeMinDamage(attacker, DefaultDamage, Astat[6]), DC.RangeMaxDamage(attacker, DefaultDamage, Astat[6]),(int) Astat[6]);
            Damage = DC.Critical(attacker, Astat[9], Astat[6], Damage);
            Damage = (Damage*Astat[10])/100;
            Damage = Damage-Dstat[8];
            event.setDamage(Damage);
            if(Damage <= 0)
            {
                SendMessage(attacker);
            }
            else
            {
                attacker.sendMessage(event.getDamage() + "데미지");
            }
        }
        if(AttackType == "근접공격")
        {
            long Damage = DC.Balance(DC.RangeMinDamage(attacker, DefaultDamage, Astat[5]), DC.RangeMinDamage(attacker, DefaultDamage, Astat[5]), (int) Astat[6]);
            Damage = DC.Critical(attacker, Astat[9], Astat[6], Damage);
            Damage = Damage-Dstat[8];
            event.setDamage(Damage);
            if(Damage <= 0)
            {
                SendMessage(attacker);
            }
            else
            {
                attacker.sendMessage(event.getDamage() + "데미지");
            }
        }
    }

    public void EntityDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, int DefaultDamage, String AttackType)
    {
        long[] Astat = new long[9];
        Astat = s.getStat(attacker.getUniqueId().toString());
        if(AttackType == "활공격")
        {
            long Damage = DC.Balance(DC.RangeMinDamage(attacker, DefaultDamage, Astat[6]), DC.RangeMaxDamage(attacker, DefaultDamage, Astat[6]), (int) Astat[6]);
            Damage = DC.Critical(attacker, Astat[9], Astat[6], Damage);
            Damage = (Damage*Astat[10])/100;
            event.setDamage(Damage);
            if(Damage <= 0)
            {
                SendMessage(attacker);
            }
            else
            {
                attacker.sendMessage(event.getDamage() + "데미지");
            }
        }
        if(AttackType == "근접공격")
        {
            long Damage = DC.Balance(DC.RangeMinDamage(attacker, DefaultDamage, Astat[5]), DC.RangeMinDamage(attacker, DefaultDamage, Astat[5]), (int) Astat[6]);
            Damage = DC.Critical(attacker, Astat[9], Astat[6], Damage);
            event.setDamage(Damage);
            if(Damage <= 0)
            {
                SendMessage(attacker);
            }
            else
            {
                attacker.sendMessage(event.getDamage() + "데미지");
            }
        }
    }

    public void PlayerDamageByEntity(EntityDamageByEntityEvent event, Player defenser)
    {
        long[] stat = new long[9];
        stat = s.getStat(defenser.getUniqueId().toString());
        if(event.getDamage() - stat[8] <= 0)
        {
            event.setDamage(0.5);
        }
        else
        {
            event.setDamage(event.getDamage() - stat[8]);
        }
    }


    public void SendMessage(Player player)
    {
        int a = DC.Random(1, 5);
        S.SP(player, Sound.ENTITY_PLAYER_DEATH, 2.0F, 0.7F);
        switch(a)
        {
            case 1: player.sendMessage(ChatColor.RED + "이 공격은 통하지 않습니다");
            case 2: player.sendMessage(ChatColor.RED + "자세를 흐트릴 수 없습니다");
            case 3: player.sendMessage(ChatColor.RED + "충격이 분산되었습니다");
            case 4: player.sendMessage(ChatColor.RED + "이 공격으로는 쓰러트릴 수 없을 것 같습니다");
            case 5: player.sendMessage(ChatColor.RED + "적의 자세를 흐트릴 수 없습니다");
        }
    }
}
