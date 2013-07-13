package de.diemex.sbpopupapi;


import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Diemex
 */
public class SBPopupAPI extends JavaPlugin
{
    private static SBPopupManager manager;


    @Override
    public void onEnable()
    {
        super.onEnable();
        manager = new SBPopupManager(this);
    }


    @Override
    public void onDisable()
    {
        super.onDisable();
        manager = null;
    }


    /**
     * Get an instance of a PopupManager
     *
     * @return SBPopupManager
     */
    public SBPopupManager getSBManager()
    {
        return manager;
    }
}
