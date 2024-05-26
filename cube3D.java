//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Cube3DViewer extends JPanel implements MouseListener, MouseMotionListener {
    private static final int SIZE = 100;
    private static final int HALF_SIZE = 50;
    private static final int CUBE_CENTER_X = 150;
    private static final int CUBE_CENTER_Y = 150;
    private static final int CUBE_CENTER_Z = 150;
    private int prevMouseX;
    private int prevMouseY;
    private double rotationX = 0.0;
    private double rotationY = 0.0;

    public Cube3DViewer() {
        this.setPreferredSize(new Dimension(300, 300));
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private void drawCube(Graphics2D g2d) {
        int[][] vertices = new int[][]{{100, 100, 100}, {200, 100, 100}, {200, 200, 100}, {100, 200, 100}, {100, 100, 200}, {200, 100, 200}, {200, 200, 200}, {100, 200, 200}};
        int[][] edges = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
        double cosX = Math.cos(Math.toRadians(this.rotationX));
        double sinX = Math.sin(Math.toRadians(this.rotationX));
        double cosY = Math.cos(Math.toRadians(this.rotationY));
        double sinY = Math.sin(Math.toRadians(this.rotationY));
        int[][] rotatedVertices = new int[8][3];

        int x;
        for(int i = 0; i < vertices.length; ++i) {
            int[] vertex = vertices[i];
            x = vertex[0] - 150;
            int y = vertex[1] - 150;
            int z = vertex[2] - 150;
            int rotatedY = (int)((double)y * cosX - (double)z * sinX) + 150;
            int rotatedZ = (int)((double)y * sinX + (double)z * cosX) + 150;
            int rotatedX = (int)((double)x * cosY + (double)rotatedZ * sinY) + 150;
            rotatedZ = (int)((double)(-x) * sinY + (double)rotatedZ * cosY) + 150;
            rotatedVertices[i] = new int[]{rotatedX, rotatedY, rotatedZ};
        }

        g2d.setColor(Color.BLACK);
        int[][] var24 = edges;
        int var25 = edges.length;

        for(x = 0; x < var25; ++x) {
            int[] edge = var24[x];
            int[] p1 = rotatedVertices[edge[0]];
            int[] p2 = rotatedVertices[edge[1]];
            boolean visible = true;
            int[][] var30 = rotatedVertices;
            int var21 = rotatedVertices.length;

            for(int var22 = 0; var22 < var21; ++var22) {
                int[] vertex = var30[var22];
                if (vertex[2] < 150) {
                    visible = false;
                    break;
                }
            }

            if (visible) {
                g2d.drawLine(p1[0], p1[1], p2[0], p2[1]);
            }
        }

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawCube(g2d);
    }

    public void mousePressed(MouseEvent e) {
        this.prevMouseX = e.getX();
        this.prevMouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int currentX = e.getX();
        int currentY = e.getY();
        this.rotationY += (double)(currentX - this.prevMouseX) * 0.5;
        this.rotationX += (double)(currentY - this.prevMouseY) * 0.5;
        this.prevMouseX = currentX;
        this.prevMouseY = currentY;
        this.repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("3D Cube Viewer");
            frame.setDefaultCloseOperation(3);
            frame.getContentPane().add(new Cube3DViewer());
            frame.pack();
            frame.setLocationRelativeTo((Component)null);
            frame.setVisible(true);
        });
    }
}
