package de.diemex.sbpopupapi.msgtype;


import org.bukkit.ChatColor;

/**
 * A MsgType that gets constructed from parameters passed in
 *
 * @author Diemex
 */
public class DynamicMsgType implements IMsgType
{
    private final String uniqueIdentifier;
    private final int length;
    private final ChatColor titleColor;
    private ChatColor textColor;


    public DynamicMsgType(String uniqueIdentifier, int length, ChatColor titleColor, ChatColor textColor)
    {
        this.uniqueIdentifier = uniqueIdentifier;
        this.length = length;
        this.titleColor = titleColor;
        this.textColor = textColor;
    }


    @Override
    public boolean hasUniqueIdentifier()
    {
        return uniqueIdentifier != null && uniqueIdentifier.length() > 0;
    }


    @Override
    public String getUniqueIdentifier()
    {
        return uniqueIdentifier;
    }


    @Override
    public int getLength()
    {
        return length;
    }


    @Override
    public ChatColor getTitleColor()
    {
        return titleColor;
    }


    @Override
    public ChatColor getTextColor()
    {
        return textColor;
    }
}
