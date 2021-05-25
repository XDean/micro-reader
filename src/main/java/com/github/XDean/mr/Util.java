package com.github.XDean.mr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Consumer;

public interface Util {
  static <T> T with(T t, Consumer<T> c) {
    c.accept(t);
    return t;
  }

  static void dispatchEventToParent(Component from, JFrame to) {
    from.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        to.dispatchEvent(e);
      }

      @Override
      public void mousePressed(MouseEvent e) {
        to.dispatchEvent(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        to.dispatchEvent(e);
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        to.dispatchEvent(e);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        to.dispatchEvent(e);
      }
    });
    from.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {
        to.dispatchEvent(e);
      }

      @Override
      public void mouseMoved(MouseEvent e) {
        to.dispatchEvent(e);
      }
    });
  }
}
