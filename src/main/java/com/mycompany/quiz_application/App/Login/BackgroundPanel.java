package com.mycompany.quiz_application.App.Login;

import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        java.net.URL imgURL = QuizApp_Login.class.getResource(imagePath);

        if (imgURL == null) {
            System.out.println("Image not found: " + imagePath);
            return;
        }

        backgroundImage = new ImageIcon(imgURL).getImage();
        setOpaque(false);
    }
    
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Mild low opacity (0.0f = invisible, 1.0f = full)
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.45f));

        // Draw image stretched to panel size
        g2d.drawImage(backgroundImage, 0, 0,
                getWidth(), getHeight(), this);

        g2d.dispose();
    }
}
