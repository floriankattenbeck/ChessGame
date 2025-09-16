package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NamedImage {
    private String path;
    private BufferedImage image;

    public NamedImage(String path) {
        this.path = path;
        this.image = LoadImage();
    }


    public String getPath() { return path; }
    public BufferedImage getImage() { return image; }
    public BufferedImage LoadImage(){
        try{
            return ImageIO.read(new File("./res/"+ path +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
