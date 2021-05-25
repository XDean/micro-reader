package com.github.XDean.mr;

import org.drjekyll.fontchooser.FontDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.XDean.mr.Util.with;

class MicroReaderFrame extends JFrame {

  private final JTextArea text;
  private Color backgroundColor = Color.WHITE;
  private Color fontColor = Color.BLACK;
  private final JPanel root;

  public MicroReaderFrame() {
    Menu menu = new Menu();


    root = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel contentPanel = new JPanel();
    text = new JTextArea();

    root.setLayout(new BorderLayout());
    root.add(scrollPane, BorderLayout.CENTER);
    root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    scrollPane.setViewportView(text);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

    text.setEditable(false);
    text.setLineWrap(true);
    text.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
          menu.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });
    updateColor();

    ComponentResizer resizer = new ComponentResizer();
    resizer.setMinimumSize(new Dimension(50, 50));
    resizer.setDragInsets(new Insets(10, 10, 10, 10));
    resizer.registerComponent(this);

    ComponentMover mover = new ComponentMover(this);
    mover.setDragInsets(new Insets(10, 10, 10, 10));
    mover.registerComponent(contentPanel, text);

    add(root);

    setType(Type.UTILITY);
    setUndecorated(true);
    setAlwaysOnTop(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setSize(400, 600);
    setVisible(true);
  }

  void updateColor() {
    root.setBackground(backgroundColor);
    text.setBackground(backgroundColor);
    text.setForeground(fontColor);
  }


  class Menu extends JPopupMenu {
    public Menu() {
      add(with(new JMenuItem("Open..."), e -> e.addActionListener(v -> {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(MicroReaderFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          text.setText("Loading...");
          new Thread(() -> {
            File file = chooser.getSelectedFile();
            try {
              String content = String.join("\n", Files.readAllLines(file.toPath()));
              SwingUtilities.invokeLater(() -> {
                text.setText(content);
                text.setLocation(0, 0);
              });
            } catch (IOException ex) {
              SwingUtilities.invokeLater(() -> text.setText(String.format("Fail to load file: %s", ex.getMessage())));
            }
          }).start();
        }
      })));
      add(with(new JMenuItem("Font..."), e -> e.addActionListener(v -> {
          FontDialog dialog = new FontDialog(MicroReaderFrame.this, "Select Font", true);
          dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          dialog.setSelectedFont(text.getFont());
          dialog.setVisible(true);
          if (!dialog.isCancelSelected()) {
            ((Component) text).setFont(dialog.getSelectedFont());
          }
        }
      )));
      add(with(new JMenuItem("Background..."), e -> e.addActionListener(v -> {
          backgroundColor = JColorChooser.showDialog(text, "Background", MicroReaderFrame.this.backgroundColor);
          updateColor();
        }
      )));
      add(with(new JMenuItem("Font Color..."), e -> e.addActionListener(v -> {
          fontColor = JColorChooser.showDialog(text, "Font Color", MicroReaderFrame.this.fontColor);
          updateColor();
        }
      )));
      add(new Separator());
      add(with(new JMenuItem("Close"), e -> e.addActionListener(v -> MicroReaderFrame.this.dispose())));
    }
  }
}