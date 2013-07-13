package de.diemex.sbpopupapi;


import org.bukkit.ChatColor;

/**
 * @author Diemex
 */
public enum MessageType implements MsgType
{
    /**
     * Notifications are shown shortly
     */
    NOTFICATION(200, ChatColor.YELLOW),
    /**
     * Warnings are shown long
     */
    WARNING(500, ChatColor.RED);

    private final int length;

    private final ChatColor titleColor;

    private ChatColor textColor;


    /**
     * Constructor
     *
     * @param length     how long to show the msg in ticks
     * @param titleColor which color should the title have
     */
    private MessageType(int length, ChatColor titleColor)
    {
        this.length = length;
        this.titleColor = titleColor;
    }


    @Override
    public boolean hasUniqueIdentifier()
    {
        return false;
    }


    @Override
    public String getUniqueIdentifier()
    {
        return null;
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
    public void setTextColor(ChatColor textColor)
    {
        this.textColor = textColor;
    }


    @Override
    public ChatColor getTextColor()
    {
        return textColor;
    }
}