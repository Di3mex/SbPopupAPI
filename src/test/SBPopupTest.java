import de.diemex.sbpopupapi.StringUtil;
import org.bukkit.ChatColor;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Diemex
 */
public class SBPopupTest
{
    @Test
    public void testFormatString()
    {
        assertArrayEquals(new String[]{"Supperisjustaro-", "undthecorner"}, StringUtil.getLines("Supperisjustaroundthecorner").toArray());
        assertArrayEquals(new String[]{"012345678912345-", "601234567891234-", "560123456789123-", "456012345678912-", "3456"}, StringUtil.getLines("0123456789123456012345678912345601234567891234560123456789123456").toArray());

        assertArrayEquals(new String[]{"Hello my name is", "Maximilian"}, StringUtil.getLines("Hello my name is Maximilian").toArray());
        assertArrayEquals(new String[]{"Hello my names", "are Maximilian"}, StringUtil.getLines("Hello my names are Maximilian").toArray());

        assertArrayEquals(new String[]{"Hello my nemesis", "is Maximilian"}, StringUtil.getLines("Hello my nemesis is Maximilian").toArray());
        assertArrayEquals(new String[]{"Hello my name", "isfor Maximilian"}, StringUtil.getLines("Hello my name isfor Maximilian").toArray());

        assertArrayEquals(new String[]{"Hello my name", "isfors", "Maximilian"}, StringUtil.getLines("Hello my name isfors Maximilian").toArray());
        assertArrayEquals(new String[]{"Charged Creeper", "explode instant-", "ly... run!"}, StringUtil.getLines("Charged Creeper explode instantly... run!").toArray());
        assertArrayEquals(new String[]{"0123456789abcdef"}, StringUtil.getLines("0123456789abcdef").toArray());
    }

    @Test
    public void colorFormattedString()
    {
        assertArrayEquals(new String[] {ChatColor.DARK_BLUE + "Hello how is", ChatColor.DARK_BLUE + "it going my", ChatColor.DARK_BLUE + "dear friend?"}, StringUtil.getLines("Hello how is it going my dear friend?", ChatColor.DARK_BLUE).toArray());
    }
}
