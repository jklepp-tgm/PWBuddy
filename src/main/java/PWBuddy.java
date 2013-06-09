import com.pwbuddy.Frame;

/**
 * Startpunkt des Programmes
 *
 * @author Jakob Klepp
 * @author Andreas Willinger
 * @since  2013-04-09
 */
public class PWBuddy {
    public static void main(String [] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Frame();
            }
        });
    }
}
