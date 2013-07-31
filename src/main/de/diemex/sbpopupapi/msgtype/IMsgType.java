package de.diemex.sbpopupapi.msgtype;


import org.bukkit.ChatColor;

/**
 * Hold some basic information about a Message
 *
 * @author Diemex
 */
public interface IMsgType
{
    /**
     * Does this message have an identifier with which we can identify this message and remove it later
     *
     * @return if it has an identifier
     */
    public boolean hasUniqueIdentifier();

    /**
     * Get the unique identifier of the message if set
     *
     * @return identifier if set or null, test if its set with {@link IMsgType#hasUniqueIdentifier()}
     */
    public String getUniqueIdentifier();

    /**
     * How long (ticks) should message be displayed. If length <= 0, message can only be removed via its identifier.
     *
     * @return length in ticks
     */
    public int getLength();

    /**
     * Color of the first line or the ScoreBoard
     *
     * @return color as ChatColor
     */
    public ChatColor getTitleColor();

    /**
     * Get the TextColor of the message
     *
     * @return color or null if no color is set
     */
    public ChatColor getTextColor();
}