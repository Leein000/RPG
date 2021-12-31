package Stat;

import Effect.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

public class Stat
{
    public Sounds s = new Sounds();

    public void CreateNewStat(String player) {
        File filename = new File("plugins/RPG/Stat/" + player + ".txt");
        File folder_Location1 = new File("plugins/RPG");
        File folder_Location2 = new File("plugins/RPG/Stat");

        try {
            if (!filename.exists())
            {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            w.append("레벨:1" + "\r\n" + "스텟포인트:0" + "\r\n" + "경험치:0" + "\r\n" + "최대경험치:1" + "\r\n" +
                    "돈:100000" + "\r\n" + "체력:10" + "\r\n" + "민첩:10" + "\r\n" + "힘:10" + "\r\n" + "최종공격시간:0");
            w.flush();
            w.close();
        } catch (IOException localIoException) {
        }
    }

    public long[] getStat(String player) {
        File filename = new File("plugins/RPG/Stat/" + player + ".txt");
        File folder_Location1 = new File("plugins/RPG");
        File folder_Location2 = new File("plugins/RPG/Stat");
        long[] stat = new long[9];
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedReader R = new BufferedReader(new FileReader(filename));
            List list = new ArrayList();
            String s;
            while(!((s = R.readLine()) == null))
            {
                list.add(Long.valueOf(Cutter(s)));
            }
            R.close();
            for (int count = 0; count < 8; count++) {
                stat[count] = ((Long) list.get(count)).longValue();
            }
            return stat;

        } catch (IOException localIoException) {
        }
        return stat;
    }

    public long Cutter(String line) {
        String[] cut = line.split(":");
        return Long.parseLong(cut[1]);
    }

    public void setStat(String player, long[] stat) {
        File filename = new File("plugins/RPG/Stat/" + player + ".txt");
        File folder_Location1 = new File("plugins/RPG");
        File folder_Location2 = new File("plugins/RPG/Stat");
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            w.append("레벨:"+stat[0] + "\r\n" + "스텟포인트:" + stat[1]+ "\r\n" + "경험치:" + stat[2]+ "\r\n" + "최대 경험치:" + stat[3]
                    + "\r\n" + "돈:" + stat[4] + "\r\n" + "체력:" + stat[5]+ "\r\n" + "민첩:" + stat[6]
                    + "\r\n" + "힘:" + stat[7]+"\r\n" + "최대 경험치:" + stat[8]);
            w.flush();
            w.close();
        }
        catch (IOException localIoException){}
    }

    public void StatUp(long[] stat, Player player, int num)
    {
        if(stat[1] > 0)
        {
            stat[num] = stat[5] + 1;
            stat[1] = stat[1] -1;
            setStat((player.getUniqueId().toString()), stat);
            s.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.8F);
            switch(num)
            {
            case 5: player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 체력이 1 상승하셨습니다");break;
            case 6: player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 민첩이 1 상승하셨습니다");break;
            case 7: player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 힘이 1 상승하셨습니다");break;

            }
        }
        else
        {
            s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] 스텟 포인트가 부족합니다");
        }
    }



    public long[] LevelUP(long[] stat, Player player)
    {
        for(;;)
        {
            if(stat[2] < stat[3])
            {
                break;
            }
            else
            {
                stat[2] = stat[2] - stat[3];
                stat[0] = stat[0] + 1;
                stat[3] = (long)(stat[0] * 100)+stat[3];
                stat[1] = stat[1] + 5;
                player.sendMessage(ChatColor.YELLOW + "레벨업");
                s.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 0.8F);
                setStat(player.getUniqueId().toString(), stat);
             }
        }
        return stat;
    }

}

