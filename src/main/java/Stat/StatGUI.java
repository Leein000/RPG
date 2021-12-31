package Stat;

import Atteck.DamageCalculator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StatGUI
{
    public Stat s = new Stat();
    public DamageCalculator DC = new DamageCalculator();

    public void Stack(String Display, Material mate, int STACK, List<String> lore, int loc, Inventory inv)
    {
        ItemStack item = new ItemStack(mate, (byte) STACK);
        ItemMeta item_Meta = item.getItemMeta();
        item_Meta.setDisplayName(Display);
        item_Meta.setLore(lore);
        item.setItemMeta(item_Meta);
        inv.setItem(loc, item);
    }

    public void StatusGUI(Player player)
    {
        long[] i = new long[9];
        i = s.getStat(player.getUniqueId().toString());

        Inventory inv = Bukkit.createInventory(null, 50, ChatColor.BLUE + "스텟");

        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),1,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),7,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),10,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),16,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),25,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),28,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),34,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),37,inv);
        Stack(ChatColor.RED + "", Material.GLASS_PANE,1, Arrays.asList(ChatColor.RED + ""),43,inv);

        Stack(ChatColor.WHITE + "[상태]", 397,3,1,Arrays.asList(ChatColor.AQUA+"레벨: " + ChatColor.WHITE + i[0],
                ChatColor.YELLOW + "경험치: " + ChatColor.WHITE + i[2] + " / " + i[3],
                ChatColor.GREEN + "스텟 포인트: " + i[1]),13,inv);

        Stack(ChatColor.DARK_GREEN + "[체력]", 267,0,1,Arrays.asList(ChatColor.WHITE + "     " + i[5], ChatColor.GRAY+"체력은 플레이어의",
                ChatColor.GRAY + "체력을" + ChatColor.GRAY+"상슴시켜 줍니다.",
                ChatColor.RED + "[추가 원거리 공격력]", ChatColor.WHITE + "     " + DC.RangeMinDamage(player, 0, i[5]) + "~" + DC.RangeMaxDamage(player, 0, i[5])),20,inv);

        Stack(ChatColor.GRAY + "[민첩]", 261,0,1,Arrays.asList(ChatColor.WHITE + "     " + i[6], ChatColor.GRAY+"민첩은 플레이어의",
                ChatColor.GRAY + "움직임 속도를" + ChatColor.GRAY+"상슴시켜 줍니다.",
                ChatColor.RED + "[추가 원거리 공격력]", ChatColor.WHITE + "     " + DC.RangeMinDamage(player, 0, i[6]) + "~" + DC.RangeMaxDamage(player, 0, i[6])),21,inv);

        Stack(ChatColor.DARK_RED + "[힘]", 307,0,1,Arrays.asList(ChatColor.WHITE + "     " + i[7], ChatColor.GRAY+"힘은 플레이어의",
                ChatColor.GRAY + "공격력을을" + ChatColor.GRAY+"상슴시켜 줍니다.",
                ChatColor.RED + "[공격력]", ChatColor.WHITE + "     " + DC.RangeMinDamage(player, 0, i[7])),22,inv);

        Stack(ChatColor.BLUE + "[체력 상승]", 403,0,1,Arrays.asList(ChatColor.GRAY+"현재 스텟 포인트 : " + i[1]),29,inv);
        Stack(ChatColor.BLUE + "[민첩 상승]", 403,0,1,Arrays.asList(ChatColor.GRAY+"현재 스텟 포인트 : " + i[1]),30,inv);
        Stack(ChatColor.BLUE + "[힘 상승]", 403,0,1,Arrays.asList(ChatColor.GRAY+"현재 스텟 포인트 : " + i[1]),31,inv);

        player.openInventory(inv);
    }

    private <T> void Stack(String s, int i, int i1, int i2, List<T> asList, int i3, Inventory inv) {
    }


    public void StatGUIClicked(InventoryClickEvent event)
    {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.stripColor("스텟")))
        {
            if(event.getCurrentItem() == null  || event.getCurrentItem().getType() == Material.AIR ||
                    ! event.getCurrentItem().hasItemMeta())
            {
                return;
            }
            else
            {
                long[] i = new long[9];
                i = s.getStat(player.getUniqueId().toString());

                switch((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
                {
                    case "[체력 상승]" : s.StatUp(i, player, 5); StatusGUI(player);break;
                    case "[민첩 상승]" : s.StatUp(i, player, 6); StatusGUI(player);break;
                    case "[힘 상승]" : s.StatUp(i, player, 7); StatusGUI(player);break;
                }
            }
        }
    }
}

