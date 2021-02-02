package gelf.view.composites;

import gelf.view.components.*;

import java.awt.Color;

import javax.swing.*;

/**
 * MainWindow
 */
public class MainWindow extends Window {
    MenuBar mainMenu;
    InfoBar infoBar;
    Outliner outliner;
    SubWindowArea subWindowArea;
    String version = "0.0.0";

    public MainWindow(String name, int width, int height) {
        //MainWindow setup
        super(name, width, height);
        this.setVisible(true);
        this.setSize(width, height);

        // //test panel
        // Panel p = new Panel(50, 50);
        // p.setBounds(0, height - 50, width, 50);
        // p.setVisible(true);

        // this.add(p);
        // Resizer panelResizer = new Resizer(ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        // this.setResizer(p, panelResizer);

        //InfoBar setup
        infoBar = new InfoBar(width, 30);
        infoBar.setLocation(0, this.getContentPane().getHeight() - infoBar.getHeight());
        infoBar.setVisible(true);
        infoBar.setText(InfoBarID.VERSION, version);
        infoBar.setText(InfoBarID.SELECTED, "none");
        infoBar.setBackground(Color.red);
        this.add(infoBar);
        Resizer infoBarResizer = new Resizer(ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        this.setResizer(infoBar, infoBarResizer);

        // //MainMenu setup
        // mainMenu = new MenuBar();
        // Resizer mainMenuResizer = new Resizer(ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        // mainMenu.setBounds(0, 0, width, 60);
        // mainMenu.setVisible(true);

        // this.add(mainMenu);
        // this.setResizer(mainMenu, mainMenuResizer);
        
        //Outliner setup
        //outliner = new Outliner();

        //SubWindowArea setup
        //subWindowArea = new SubWindowArea();

        this.repaint();
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("GELF - Graphical Editor for Liberty Files", 1200, 800);
    }
}