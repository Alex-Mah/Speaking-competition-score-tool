package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//displays a background image by using a JLabel
//copied from source below
//https://stackoverflow.com/questions/14353302/displaying-image-in-java
public class DisplayImage {

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: constructs a background with a logo on the given frame
    public DisplayImage(JFrame frame) throws IOException {
        BufferedImage img = ImageIO.read(new File("./data/background.jpg"));
        ImageIcon icon = new ImageIcon(img);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
//        lbl.setBounds(40, 450, 200, 200);
        lbl.setBounds(0,0, 1280, 720);
        frame.add(lbl);
    }
}