package be.helmo.manager;

import be.helmo.main.screen.Screen;

import java.io.*;
import java.util.Properties;

/**
 * SaveSet
 * <p>
 * Option file load and save
 *
 * @author IncroyablePix
 */
public class SaveSet {

    public static String FOLDER_NAME = "jtlc";
    public static String FILE_NAME = "jtlc.set";

    private int xRes;
    private int yRes;
    private boolean fullscreen;
    private boolean fpsLimit;

    /**
     *
     */
    public SaveSet() {
        this.setDefaults();

        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(System.getProperty("user.home") + "/.silhouette/" + FOLDER_NAME + "/" + FILE_NAME)) {
            prop.load(input);

            getData(prop);
        }
        catch (IOException io) {//File failed to load
            System.err.println("File could not be loaded...");
            try {
                createNewFile();
                System.out.println("=> ...file was successfully created.");
            }
            catch (IOException e) {
                System.err.println("=> ...file could not have been created.");
            }
        }
    }

    public void setDefaults() {
        xRes = Screen.WIN_WIDTH;
        yRes = Screen.WIN_HEIGHT;

        fullscreen = false;
    }

    public void setFullScreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean getFullscreen() {
        return fullscreen;
    }

    public void setFPSLimit(boolean fpsLimit) {
        this.fpsLimit = fpsLimit;
    }

    public boolean getFPSLimit() {
        return fpsLimit;
    }

    public void setResolution(int x, int y) {
        xRes = x;
        yRes = y;
    }

    public int getResX() {
        return xRes;
    }

    public int getResY() {
        return yRes;
    }

    public void saveFile() {
        Properties prop;
        prop = new Properties();
        System.out.println("Overwriting properties...");

        try (OutputStream output = new FileOutputStream(System.getProperty("user.home") + "/.silhouette/" + FOLDER_NAME + "/" + FILE_NAME)) {
            writeData(prop);

            prop.store(output, "");
            System.out.println("File successfully overwritten!");
        }
        catch (IOException io) {
            System.err.println("File could not be opened...");
        }
    }

    public void writeData(Properties prop) {
        prop.setProperty("xres", "" + xRes);//Résolution X
        prop.setProperty("yres", "" + yRes);//Résolution Y
        prop.setProperty("fs", "" + fullscreen);//Fullscreen
        prop.setProperty("fps", "" + fpsLimit);//FPS Limiter
    }

    private void getData(Properties prop) {
        xRes = Integer.parseInt(parseProperty(prop, "xres", "" + Screen.WIN_WIDTH));
        yRes = Integer.parseInt(parseProperty(prop, "yres", "" + Screen.WIN_HEIGHT));
        fullscreen = Boolean.parseBoolean(parseProperty(prop, "fs", "false"));
        fpsLimit = Boolean.parseBoolean(parseProperty(prop, "fps", "true"));
    }

    private void createNewFile() throws IOException {
        new File(System.getProperty("user.home") + "/.silhouette/" + FOLDER_NAME).mkdirs();
        new File(System.getProperty("user.home") + "/.silhouette/" + FOLDER_NAME + "/" + FILE_NAME);
    }

    private String parseProperty(Properties prop, String key, String defaultValue) {
        String property = prop.getProperty(key);

        return (property == null ? (defaultValue == null ? "" : defaultValue) : property);
    }


}
