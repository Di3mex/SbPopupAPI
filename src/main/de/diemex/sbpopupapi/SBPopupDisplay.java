package de.diemex.sbpopupapi;


import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * One SBP
 *
 * @author Diemex
 */
public class SBPopupDisplay
{
    /**
     * Title of the Scoreboard
     */
    private final String scoreboardTitle;

    /**
     * Name of the Player
     */
    private final String playerName;

    /**
     * Reference to the Plugin using this
     */
    private final Plugin plugin;

    /**
     * Scoreboard before we have shown our scoreboard
     */
    private Scoreboard previousBoard;

    /**
     * How many messages we are currently displaying
     */
    private int msgCount = 0;

    /**
     * Objective to display messages
     */
    private Objective objective;

    /**
     * Our scoreboard for messages
     */
    private final Scoreboard msgBoard;

    /**
     * All the popups currently shown
     */
    private Map<Integer, SBPopup> notifications = new HashMap<Integer, SBPopup>();

    /**
     * Messages Id RelationShip if available
     */
    private Map<String, Integer> idMap = new HashMap<String, Integer>();


    public SBPopupDisplay(String scoreboardTitle, Plugin plugin, String playerName)
    {
        this.scoreboardTitle = scoreboardTitle;
        this.plugin = plugin;
        this.playerName = playerName;
        msgBoard = Bukkit.getScoreboardManager().getNewScoreboard();
    }


    /**
     * Display the next message
     *
     * @return id of the message to remove later, or -1 if not displayed
     */
    public int displayMessage(SBPopup popup)
    {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null && player.isOnline())
        {
            //Init if no scoreboard active
            if (objective == null)
            {
                objective = msgBoard.registerNewObjective(popup.getTitle(), "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            player.setScoreboard(msgBoard);
        }

        //Remove messages with the same text
        Iterator<Map.Entry<Integer, SBPopup>> iter = notifications.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry<Integer, SBPopup> message = iter.next();
            if (message.equals(popup))
                iter.remove();
        }

        //Find next free index to put the message
        int index = getFreeIndex();

        //Remove messages with the same identifier
        if (popup.getType().hasUniqueIdentifier())
        {
            String id = popup.getType().getUniqueIdentifier();
            if (idMap.containsKey(id))
                notifications.remove(idMap.get(id));
            idMap.put(id, index);
        }

        notifications.put(index, popup);

        updateIndexes();
        return index;
    }


    /**
     * Remove the message with the unique identifier
     *
     * @param id id to remove the message
     */
    public void removeMessage(String id)
    {
        if (idMap.containsKey(id))
        {
            int index = idMap.get(id);
            idMap.remove(id);
            removeMessage(index);
        }
    }


    /**
     * Remove the message by its id
     *
     * @param id id of the message
     */
    public void removeMessage(int id)
    {
        msgCount--;

        for (String line : notifications.get(id).getMsg())
        {
            OfflinePlayer remove = plugin.getServer().getOfflinePlayer(line);
            msgBoard.resetScores(remove);
        }
        notifications.remove(id);
        //Update all the line numbers
        updateIndexes();
    }


    private void updateIndexes()
    {
        int lastLine = lineCount() + notifications.size() - 1; //separators, no separator on the last line
        int separator = 16; //amount of dashes

        //Clear scoreboard
        for (OfflinePlayer player : msgBoard.getPlayers())
            msgBoard.resetScores(player);

        //Update the scores and put separators in between the lines
        boolean updateTitle = true;
        for (int i = getHighestIndex(); i > 0; i--)
        {
            if (notifications.containsKey(i))
            {
                //Use the title and color of the newest message
                if (updateTitle)
                {
                    objective.setDisplayName(notifications.get(i).getTitle());
                    updateTitle = false;
                }

                SBPopup popup = notifications.get(i);
                List<String> msg = popup.getMsg();
                for (String msgLine : msg)
                {
                    OfflinePlayer line = plugin.getServer().getOfflinePlayer(msgLine);
                    Score score = objective.getScore(line);
                    score.setScore(lastLine--);
                }
                OfflinePlayer dash = plugin.getServer().getOfflinePlayer(StringUtils.repeat("-", separator--));
                if (i != 1) //not last line
                    objective.getScore(dash).setScore(lastLine--);
            }
        }
    }


    /**
     * Super duper shit mart
     *
     * @return a free index for uniqueness
     */
    private int getFreeIndex()
    {
        return notifications.isEmpty() ? 1 : getHighestIndex() + 1;
    }


    /**
     * Get the highest index in the Map. Notes: Iterates over the whole Map.
     *
     * @return highest index
     */
    private int getHighestIndex()
    {
        int highestId = 0;
        for (Integer id : notifications.keySet())
            if (id > highestId)
                highestId = id;
        return highestId;
    }


    /**
     * The amount of lines currently displayed in the scoreboard
     */
    private int lineCount()
    {
        int lineCount = 0;
        for (SBPopup popup : notifications.values())
            if (popup != null)
                lineCount += popup.getMsg().size();
        return lineCount;
    }


    /**
     * Are there more messages to be displayed
     *
     * @return if messages are scheduled
     */
    public boolean messagesScheduled()
    {
        return !notifications.isEmpty();
    }
}
