package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Helper functions for writing JSuperMario.
 */
public class Util{

    /**
     * Given a filePath (to a picture file with multiple sprites), as well as a
     * width and a height for the tiles therein, split the picture into
     * all of the quads by simply dividing it evenly.
     *
     * @param filePath      path to texture
     * @param tileWidth     width of quads
     * @param tileHeight    height of quads
     * @return              1-dimensional Imagearray containing the quads within the texture
     */
    public static BufferedImage[] GenerateQuads(String filePath, int tileWidth, int tileHeight){
        try {
            // load file
            BufferedImage atlas = ImageIO.read(new File(filePath));

            // determine the number of quads in width and height
            int numOfTilesX = atlas.getWidth() / tileWidth;
            int numOfTilesY = atlas.getHeight() / tileHeight;

            // create 1-dimensional Imagearray by processing from row to row
            BufferedImage spritesheet[] = new BufferedImage[numOfTilesX * numOfTilesY];
            for(int y = 0; y < numOfTilesY; y++){
                for(int x = 0; x < numOfTilesX; x++){
                    spritesheet[y * numOfTilesX + x]
                            = atlas.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                }
            }
            return spritesheet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given an 1-dimensional Imagearray, as well as a
     * number of tilesets in width and a number of tilesets in height
     * and the width and height of the tiles therein, split the images into
     * all of the quads by simply dividing it evenly.
     *
     * @param quads         1-dimensional Imagearray
     * @param setsX         number of tilesets in width
     * @param setsY         number of tilesets in height
     * @param sizeX         width of quads
     * @param sizeY         height of quads
     * @return              2-dimensional Imagearray containing the quads within each tileset
     */
    public static BufferedImage[][] GenerateTileSets(BufferedImage[] quads,
                                                     int setsX,
                                                     int setsY,
                                                     int sizeX,
                                                     int sizeY) {
        // create 2-dimensional Imagearray
        BufferedImage[][] tilesets =
                new BufferedImage[/**num of tilesets*/ setsX * setsY][/**num of quads in each tileset*/ sizeX * sizeY];
        int sheetWidth = setsX * sizeX;

        // loop through each tileset by processing from row to row
        for (int tilesetY = 0; tilesetY < setsY; tilesetY++) {
            for (int tilesetX = 0; tilesetX < setsX; tilesetX++) {
                // loop through each quad within related tileset from row to row
                for (int y = sizeY * tilesetY; y < sizeY * tilesetY + sizeY; y++) {
                    for (int x = sizeX * tilesetX; x < sizeX * tilesetX + sizeX; x++) {
                        tilesets[tilesetY * setsX + tilesetX][(x - sizeX * tilesetX) + (y - sizeY * tilesetY) * sizeX]
                                = quads[sheetWidth * y + x];
                    }
                }
            }
        }
        return tilesets;
    }

    /**
     * Creates a Sound-Clip given a filePath
     *
     * @param filePath      path to sound file
     * @return              Clip containing the data of referenced file
     */
    public static Clip getClip(String filePath){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            return clip;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param fontPath
     * @param size
     * @return
     */
    public static Font getFont(String fontPath, float size){
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(size);

            // Font im System registrieren
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font;
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}