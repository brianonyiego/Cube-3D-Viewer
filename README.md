
# Cube3DViewer

## Overview

The `Cube3DViewer` is a Java Swing application that visualizes a 3D cube and allows the user to rotate it using mouse interactions. This program demonstrates basic 3D graphics rendering and user interaction handling in a Swing GUI.

## Features

- Renders a 3D cube using 2D graphics.
- Allows rotation of the cube around the X and Y axes using mouse dragging.
- Uses double buffering for smooth rendering.

## Code Description

### Main Class: `Cube3DViewer`

The `Cube3DViewer` class extends `JPanel` and implements `MouseListener` and `MouseMotionListener` for handling mouse events.

#### Fields

- `private static final int SIZE`: The size of the cube.
- `private static final int HALF_SIZE`: Half the size of the cube.
- `private static final int CUBE_CENTER_X`, `CUBE_CENTER_Y`, `CUBE_CENTER_Z`: The coordinates of the cube's center.
- `private int prevMouseX`, `prevMouseY`: The previous mouse coordinates for tracking drag movements.
- `private double rotationX`, `rotationY`: The rotation angles around the X and Y axes.

#### Constructor

The constructor sets the preferred size and background color of the panel and adds the necessary mouse listeners.

```java
public Cube3DViewer() {
    this.setPreferredSize(new Dimension(300, 300));
    this.setBackground(Color.WHITE);
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
}
```

#### Drawing the Cube

The `drawCube` method calculates the rotated positions of the cube vertices and draws the edges if they are visible.

```java
private void drawCube(Graphics2D g2d) {
    // Define vertices and edges
    int[][] vertices = new int[][]{{100, 100, 100}, {200, 100, 100}, {200, 200, 100}, {100, 200, 100}, {100, 100, 200}, {200, 100, 200}, {200, 200, 200}, {100, 200, 200}};
    int[][] edges = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
    
    // Calculate rotation matrices
    double cosX = Math.cos(Math.toRadians(this.rotationX));
    double sinX = Math.sin(Math.toRadians(this.rotationX));
    double cosY = Math.cos(Math.toRadians(this.rotationY));
    double sinY = Math.sin(Math.toRadians(this.rotationY));
    
    // Rotate vertices
    int[][] rotatedVertices = new int[8][3];
    for (int i = 0; i < vertices.length; ++i) {
        int[] vertex = vertices[i];
        int x = vertex[0] - 150;
        int y = vertex[1] - 150;
        int z = vertex[2] - 150;
        int rotatedY = (int)((double)y * cosX - (double)z * sinX) + 150;
        int rotatedZ = (int)((double)y * sinX + (double)z * cosX) + 150;
        int rotatedX = (int)((double)x * cosY + (double)rotatedZ * sinY) + 150;
        rotatedZ = (int)((double)(-x) * sinY + (double)rotatedZ * cosY) + 150;
        rotatedVertices[i] = new int[]{rotatedX, rotatedY, rotatedZ};
    }

    // Draw edges
    g2d.setColor(Color.BLACK);
    for (int[] edge : edges) {
        int[] p1 = rotatedVertices[edge[0]];
        int[] p2 = rotatedVertices[edge[1]];
        boolean visible = true;
        for (int[] vertex : rotatedVertices) {
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
```

#### Overriding `paintComponent`

The `paintComponent` method is overridden to enable custom painting of the component. It sets rendering hints for anti-aliasing and calls the `drawCube` method.

```java
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    this.drawCube(g2d);
}
```

#### Mouse Event Handling

The class implements methods to handle mouse events for rotation:

```java
public void mousePressed(MouseEvent e) {
    this.prevMouseX = e.getX();
    this.prevMouseY = e.getY();
}

public void mouseDragged(MouseEvent e) {
    int currentX = e.getX();
    int currentY = e.getY();
    this.rotationY += (double) (currentX - this.prevMouseX) * 0.5;
    this.rotationX += (double) (currentY - this.prevMouseY) * 0.5;
    this.prevMouseX = currentX;
    this.prevMouseY = currentY;
    this.repaint();
}

public void mouseClicked(MouseEvent e) { }
public void mouseReleased(MouseEvent e) { }
public void mouseEntered(MouseEvent e) { }
public void mouseExited(MouseEvent e) { }
public void mouseMoved(MouseEvent e) { }
```

### Main Method

The `main` method sets up the JFrame and adds an instance of `Cube3DViewer` to it.

```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("3D Cube Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Cube3DViewer());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    });
}
```

## How to Run

1. Ensure you have Java installed on your machine.
2. Copy the code into a file named `Cube3DViewer.java`.
3. Compile the program using the command:
   ```bash
   javac Cube3DViewer.java
   ```
4. Run the program using the command:
   ```bash
   java Cube3DViewer
   ```

## Sample Output

When you run the program, a window will appear displaying a 3D cube. You can rotate the cube by clicking and dragging the mouse.

## License

This code is provided for educational purposes and is free to use and modify.

---


