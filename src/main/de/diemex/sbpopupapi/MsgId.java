package de.diemex.sbpopupapi;


/**
 * Extend this interface if you would like to be able to remove messages by this id
 *
 * @author Diemex
 */
public interface MsgId
{
    /**
     * Set unique id of this message
     */
    public void setId(String id);

    /**
     * Get the unique identifier of this message
     *
     * @return message
     */
    public String getId();
}
