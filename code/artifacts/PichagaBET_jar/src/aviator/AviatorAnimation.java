package aviator;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AviatorAnimation extends JPanel {

    public JPanel animationPanel;
    private double animationProgress = 0.0;
    private int aircraftX = 50;
    private int aircraftY = 300;
    private int aircraftWidth = 60;
    private int aircraftHeight = 40;
    private int flightSpeed = 1;
    private Timer timer;
    private List<Point> pathPoints = new ArrayList<>();
    private BufferedImage planeImage;
    private boolean movingOnYAxis = false;
    private int transitionPoint;

    public AviatorAnimation() {
        loadPlaneImage();
        setupUI();
        setVisible(true);
    }

    private void loadPlaneImage() {
        try {
            URL url = getClass().getResource("plane.png");
            if (url == null) {
                System.out.println("plane.png not found");
            }
            planeImage = ImageIO.read(url);
            if (planeImage != null) {

                Image scaledImage = planeImage.getScaledInstance(aircraftWidth, aircraftHeight, Image.SCALE_SMOOTH);
                planeImage = new BufferedImage(aircraftWidth, aircraftHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = planeImage.createGraphics();
                g2d.drawImage(scaledImage, 0, 0, null);
                g2d.dispose();
            } else {
                System.out.println("Could not load plane image");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupUI() {
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBackground(g);
                drawPath(g);
                drawAircraft(g);
            }
        };
        animationPanel.setBackground(new Color(0x333333));
        setLayout(new BorderLayout());
        add(animationPanel, BorderLayout.CENTER);
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(0x333333));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawAircraft(Graphics g) {
        if (planeImage != null) {
            g.drawImage(planeImage, aircraftX, aircraftY, null);
        }
    }

    private void drawPath(Graphics g) {
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        int startPoint = 0;
        if (movingOnYAxis) {
            startPoint = Math.max(0, pathPoints.size() - getWidth() / flightSpeed);
        }
        for (int i = startPoint; i < pathPoints.size() - 1; i++) {
            Point p1 = pathPoints.get(i);
            Point p2 = pathPoints.get(i + 1);
            if (p1.x > transitionPoint) {
                g2d.draw(new Line2D.Float(p1.x, p1.y, p2.x, p2.y));
            } else {
                int offsetX = movingOnYAxis ? (aircraftX - transitionPoint) : 0;
                g2d.draw(new Line2D.Float(p1.x - offsetX, p1.y, p2.x - offsetX, p2.y));
            }
        }
    }

    private void updateAnimation() {
        int panelWidth = animationPanel.getWidth();
        int panelHeight = animationPanel.getHeight();
        int baseY = panelHeight / 2;

        if (animationProgress >= 0.9) {
            animationProgress = 0.0;
            pathPoints.clear();
            aircraftX = 50;
            aircraftY = baseY;
        }

        int currentAnimationX = (int) (animationProgress * panelWidth);
        aircraftX = currentAnimationX % (panelWidth - aircraftWidth);
        aircraftY = (int) (baseY - Math.sin(currentAnimationX / 15f) * 75);

        pathPoints.clear();
        for (int i = 0; i <= currentAnimationX; i++) {
            int pathY = (int) (baseY - Math.sin(i / 15f) * 75);
            pathPoints.add(new Point(i % (panelWidth - aircraftWidth) + aircraftWidth / 2, pathY + aircraftHeight / 2));
        }
    }

    public void startAnimation() {
        timer = new Timer(20, e -> {
            animationProgress += 0.002;
            transitionPoint = animationPanel.getWidth() - aircraftWidth;
            updateAnimation();
            animationPanel.repaint();
        });
        timer.start();
    }

    public void stopAnimation() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void setAnimationProgress(double progress) {
        this.animationProgress = Math.max(0.0, Math.min(1.0, progress));
    }

    public double getAnimationProgress() {
        return this.animationProgress;
    }
}