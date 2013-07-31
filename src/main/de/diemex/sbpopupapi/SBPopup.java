package de.diemex.sbpopupapi;


import de.diemex.sbpopupapi.msgtype.IMsgType;
import org.apache.commons.lang.Validate;

import java.util.List;

/**
 * Represents a ScoreBoardPopup
 *
 * @author Diemex
 */
public class SBPopup
{
    /**
     * The type of Popup, holding information about the length and the titleColor
     */
    private IMsgType type;

    /**
     * Title of the message
     */
    private String title;

    /**
     * The message separated into new lines
     */
    private List<String> msg;


    /**
     * Construct a new SBPopup. Breaks message into lines and breaks words.
     *
     * @param type  type of this popup
     * @param title title of the message
     * @param msg   msg as String, will be splitted into lines
     */
    public SBPopup(IMsgType type, String title, String msg)
    {
        this(type, title, StringUtil.getLines(msg, type.getTextColor()));
    }


    /**
     * Construct a new SBPopup. Uses the given pre formatted lines.
     *
     * @param type  type of this popup
     * @param title title of the message
     * @param msg   msg as String, will be splitted into lines
     */
    public SBPopup(IMsgType type, String title, List<String> msg)
    {
        for (String line : msg)
            Validate.isTrue(line.length() <= 16, "Scoreboards have a max of 16 characters per line. Given line was "
                    + line.length() + " long. Content: \"" + line + "\"");
        this.type = type;
        this.title = title;
        this.msg = msg;
    }


    /**
     * Get the Type of this Popup. How long to display, titlecolor
     *
     * @return the type
     */
    public IMsgType getType()
    {
        return type;
    }


    /**
     * Get the title of the message
     *
     * @return the title of a message
     */
    public String getTitle()
    {
        return title == null ? null : type.getTitleColor() != null ? type.getTitleColor() + title : title;
    }


    /**
     * Get the lines of text
     *
     * @return the lines of text with a max of 16 chars per line
     */
    public List<String> getMsg()
    {
        return msg;
    }


    /**
     * Compares if the message text is the same
     *
     * @param obj to compare
     * @return true if equal, otherwise false
     */
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof SBPopup && msg.equals(((SBPopup) obj).getMsg());
    }
}