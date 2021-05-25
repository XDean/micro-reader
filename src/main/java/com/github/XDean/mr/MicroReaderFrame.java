package com.github.XDean.mr;

import javax.swing.*;
import java.awt.*;

class MicroReaderFrame extends JFrame {
  Point point = new Point();

  public MicroReaderFrame() {

    setType(Type.UTILITY);
    setUndecorated(true);
    setAlwaysOnTop(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    ComponentResizer resizer = new ComponentResizer();
    resizer.setMinimumSize(new Dimension(50, 50));
    resizer.setDragInsets(new Insets(10, 10, 10, 10));
    resizer.registerComponent(this);

    ComponentMover mover = new ComponentMover();
    mover.setDragInsets(new Insets(10, 10, 10, 10));
    mover.registerComponent(this);

    setSize(400, 600);
    setVisible(true);
  }
}