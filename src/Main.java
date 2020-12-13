import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private final static String SAVE_DIRECTORY = "/Users/anvartdinovtimur/Documents/games/savegames/";

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 3, 15, 123.4);
        GameProgress game2 = new GameProgress(40, 2, 6, 43.2);
        GameProgress game3 = new GameProgress(200, 4, 25, 223.4);

        String pathSaveGame1 = "game1.dat";
        String pathSaveGame2 = "game2.dat";
        String pathSaveGame3 = "game3.dat";

        saveGame(SAVE_DIRECTORY + pathSaveGame1, game1);
        saveGame(SAVE_DIRECTORY + pathSaveGame2, game2);
        saveGame(SAVE_DIRECTORY + pathSaveGame3, game3);

        addFileToArchive(new String[] {pathSaveGame1, pathSaveGame2, pathSaveGame3},
                SAVE_DIRECTORY + "saves.zip");

        Arrays.stream(new String[]{pathSaveGame1, pathSaveGame2, pathSaveGame3})
                .forEach(Main::deleteSave);
    }

    private static void saveGame(String pathToSave, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(pathToSave);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void addFileToArchive(String[] files, String archiveName) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveName))) {
            for (String file : files) {
                FileInputStream fis = new FileInputStream(SAVE_DIRECTORY + file);

                ZipEntry entry = new ZipEntry(file);
                zout.putNextEntry(entry);

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);

                zout.write(buffer);
                zout.closeEntry();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteSave(String pathToFile) {
        File file = new File(SAVE_DIRECTORY + pathToFile);
        file.delete();
    }

}
