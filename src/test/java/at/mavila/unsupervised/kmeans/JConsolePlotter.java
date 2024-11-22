package at.mavila.unsupervised.kmeans;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JConsolePlotter extends JPanel {

  private final List<List<BigDecimal>> dataSet;

  public JConsolePlotter(List<List<BigDecimal>> dataSet) {
    this.dataSet = dataSet;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Draw axes
    g2d.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50); // X-axis
    g2d.drawLine(50, getHeight() - 50, 50, 50); // Y-axis

    // Draw points
    for (List<BigDecimal> point : dataSet) {
      int x = 50 + point.get(0).multiply(BigDecimal.valueOf(getWidth() - 100)).intValue();
      int y = getHeight() - 50 - point.get(1).multiply(BigDecimal.valueOf(getHeight() - 100)).intValue();
      g2d.fillOval(x - 2, y - 2, 4, 4);
    }
  }

  public static void plot(List<List<BigDecimal>> dataSet) {
    if (GraphicsEnvironment.isHeadless()) {
      System.err.println("Cannot plot graph in a headless environment.");
      return;
    }

    JFrame frame = new JFrame("Data Set Plot");
    JConsolePlotter plotter = new JConsolePlotter(dataSet);
    frame.add(plotter);
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}

