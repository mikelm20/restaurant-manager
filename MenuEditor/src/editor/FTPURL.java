package editor;

/**
 * Locations of the shared FTP folder the menu editor writes to.
 *
 * The restaurant's display devices read the generated menu.xml and the
 * category/dish images from this folder. Override the defaults with the
 * system property -Dmenu.ftp.root=/path (or the MENU_FTP_ROOT environment
 * variable) when launching the application.
 */
public class FTPURL {

    private static final String DEFAULT_ROOT = "/srv/ftp/restaurant";

    private static String root() {
        String prop = System.getProperty("menu.ftp.root");
        if (prop != null && !prop.isEmpty()) {
            return prop;
        }
        String env = System.getenv("MENU_FTP_ROOT");
        if (env != null && !env.isEmpty()) {
            return env;
        }
        return DEFAULT_ROOT;
    }

    /** file: URL of the images folder, used to load previews in the editor. */
    public static String getImageURL() {
        return "file:" + root() + "/images";
    }

    /** Folder where menu.xml is written. */
    public static String getMenuURL() {
        return root() + "/menu";
    }

    /** Folder where uploaded images are copied to. */
    public static String getImgDest() {
        return root() + "/images";
    }

    /** Relative image path written into menu.xml for the display devices. */
    public static String getFTP() {
        return "ftp/restaurant/images";
    }
}
