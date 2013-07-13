package de.diemex.sbpopupapi;


import org.bukkit.ChatColor;

import java.util.ArrayList;

/**
 * @author Diemex
 */
public class StringUtil
{
    /**
     * Wrap words in an intelligent way to display in a scoreboard
     *
     * @param message to wrap
     *
     * @return the wrapped lines
     */
    public static ArrayList<String> getLines(String message)
    {
        return getLines(message, null);
    }


    /**
     * Wrap words in an intelligent way to display in a scoreboard
     *
     * @param message   to wrap
     * @param lineColor colors of the lines, reduces the line size by 2
     *
     * @return the wrapped lines
     */
    public static ArrayList<String> getLines(String message, ChatColor lineColor)
    {
        if (message == null) throw new IllegalArgumentException("Null string");
        ArrayList<String> lines = new ArrayList<String>();

        String[] pieces;
        if (message.contains(" ")) pieces = message.split(" ");
        else pieces = new String[]{message};

        String line = "";
        int offset = 0;
        int len = lineColor != null ? 14 : 16; //Reserve 2 chars for the color code
        for (int i = 0; i < pieces.length; i++)
        {
            if (line.length() == 0)
            {
                if (lineColor != null) //need color code for every line
                    line += lineColor;
                if (pieces[i].length() - offset > len)
                {
                    lines.add(pieces[i].substring(offset, len - 1 + offset) + "-");
                    offset += len - 1;
                    i--;
                } else
                {
                    line += pieces[i].substring(offset);
                    offset = 0;
                }
            } else
            {
                if (pieces[i].length() + line.length() > len - 1)
                {
                    if (line.length() > len - 4 || line.length() + pieces[i].length() < len + 2)
                    {
                        lines.add(line);
                        line = "";
                        i--;
                    } else
                    {
                        offset = len - 2 - line.length();
                        line += " " + pieces[i].substring(0, len - 2 - line.length()) + "-";
                        lines.add(line);
                        line = "";
                        i--;
                    }
                } else
                {
                    offset = 0;
                    line += " " + pieces[i].substring(offset);
                }
            }
        }
        lines.add(line);
        return lines;
    }
}
