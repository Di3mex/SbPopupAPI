package de.diemex.sbpopupapi;


import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to show announcements via the Scoreboard
 *
 * @author Diemex
 */
public class SBPopupManager implements Listener
{
    final Plugin plugin;

    /**
     * Every Player has his own ScoreBoard to show messages on
     */
    Map<String, SBPopupDisplay> managerScoreboards;

    /**
     * List of SBPopups to show to the Player
     */
    Map<String, List<SBPopup>> scheduledPopups;


    /**
     * Constructor
     */
    public SBPopupManager(Plugin plugin)
    {
        this.plugin = plugin;
        managerScoreboards = new HashMap<String, SBPopupDisplay>();
    }


    /**
     * Called when the Player leaves the server. remove player's scoreboard.
     */
    private void onPlayerQuit(PlayerQuitEvent event)
    {
        managerScoreboards.remove(event.getPlayer().getName());
    }


    /**
     * Shows a popup in the scoreboard. Breaks msg into lines automatically, keep in mind Color codes reduce line size.
     *
     * @param player          player for whom to show it
     * @param type            type defines color and length
     * @param scoreboardTitle scoreboard title is used
     * @param msg             message text will be wrapped to fit the scoreboard limitations
     *
     * @return a boolean for whatever reason I cant remember
     */
    public boolean showPopup(final String player, final MsgType type, final String scoreboardTitle, final List<String> msg)
    {
        SBPopup popup = new SBPopup(type, scoreboardTitle, msg);

        final SBPopupDisplay store = getStore(player, scoreboardTitle);

        final int id = store.displayMessage(popup);

        if (!type.hasUniqueIdentifier())
            removeAfter(id, store, type.getLength());

        return true;
    }


    /**
     * Shows a popup in the scoreboard. Breaks msg into lines automatically, keep in mind Color codes reduce line size.
     *
     * @param player          player for whom to show it
     * @param type            type defines color and length
     * @param scoreboardTitle scoreboard title is used
     * @param msg             message text will be wrapped to fit the scoreboard limitations
     *
     * @return a boolean for whatever reason I cant remember
     */
    public boolean showPopup(final String player, final MsgType type, final String scoreboardTitle, final String msg)
    {
        return showPopup(player, type, scoreboardTitle, StringUtil.getLines(msg, type.getTextColor()));
    }


    /**
     * Broadcast a message to all players on the server. Breaks msg into lines automatically, keep in mind Color codes
     * reduce line size.
     *
     * @param type            type of the message
     * @param scoreboardTitle title of the scoreboard
     * @param msg             text of the message
     *
     * @return if successful
     */
    public boolean broadcastPopup(MsgType type, final String scoreboardTitle, final List<String> msg)
    {
        SBPopup popup = new SBPopup(type, scoreboardTitle, msg);

        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            final SBPopupDisplay store = getStore(player.getName(), scoreboardTitle);

            final int id = store.displayMessage(popup);

            if (!type.hasUniqueIdentifier())
                removeAfter(id, store, type.getLength());
        }
        return true;
    }


    /**
     * Broadcast a message to all players on the server. Breaks msg into lines automatically, keep in mind Color codes
     * reduce line size.
     *
     * @param type            type of the message
     * @param scoreboardTitle title of the scoreboard
     * @param msg             text of the message
     *
     * @return if successful
     */
    public boolean broadcastPopup(MsgType type, final String scoreboardTitle, final String msg)
    {
        return broadcastPopup(type, scoreboardTitle, StringUtil.getLines(msg, type.getTextColor()));
    }


    /**
     * Remove a message with the given (plugin set) identifier
     *
     * @param player     name of the player
     * @param identifier identifier for example: "de.myplugin.deathmsg"
     */
    public void removePopup(String player, final String identifier)
    {
        getStore(player, "SBPopupAPI").removeMessage(identifier);
    }


    /**
     * Gets the Store/Manager Object for a given Player and constructs a new one if needed
     *
     * @param player          player
     * @param scoreboardTitle title of the scoreboard
     *
     * @return storage object
     */
    private SBPopupDisplay getStore(String player, String scoreboardTitle)
    {
        final SBPopupDisplay store;

        if (managerScoreboards.containsKey(player))
            store = managerScoreboards.get(player);
        else
        {
            store = new SBPopupDisplay(scoreboardTitle, plugin, player);
            managerScoreboards.put(player, store);
        }

        return store;
    }


    /**
     * Schedule a task to remove a Popup after a given time
     *
     * @param id     id of the message to remove
     * @param store  message object
     * @param length after how many ticks shall we remove the message
     */
    private void removeAfter(final int id, final SBPopupDisplay store, final int length)
    {
        //Remove a message after a given time
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                store.removeMessage(id);
            }
        }, length);
    }
}
