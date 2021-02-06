package gelf.view.composites;

import gelf.view.components.*;

import java.awt.Color;

import javax.swing.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainWindow
 */
public class MainWindow extends Window {
    MenuBar mainMenu;
    InfoBar infoBar;
    Outliner outliner;
    SubWindowArea subWindowArea;
    String version = "0.0.0";
    Color cBackground = new Color(0.1f, 0.1f, 0.1f);
    Image icon = Toolkit.getDefaultToolkit().getImage("/Images/AppIcon.png");

    public MainWindow(String name, int width, int height) {
        // MainWindow setup
        super(name, width, height);
        this.setSize(width, height);
        this.getContentPane().setBackground(cBackground);
        this.setIconImage(icon);
        this.setVisible(true);

        // //test panel
        // Panel p = new Panel(50, 50);
        // p.setBounds(0, height - 50, width, 50);
        // p.setVisible(true);

        // this.add(p);
        // Resizer panelResizer = new Resizer(ResizeMode.ABSOLUTE_BOTTOM_RIGHT,
        // ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT,
        // ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        // this.setResizer(p, panelResizer);

        // InfoBar setup
        infoBar = new InfoBar(width, 30);
        infoBar.setLocation(0, this.getContentPane().getHeight() - infoBar.getHeight());
        infoBar.setVisible(true);
        infoBar.setText(InfoBarID.VERSION, version);
        infoBar.setText(InfoBarID.SELECTED, "none");
        this.add(infoBar);
        Resizer infoBarResizer = new Resizer(ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_TOP_LEFT,
                ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        this.setResizer(infoBar, infoBarResizer);

        // MainMenu setup
        mainMenu = new MenuBar();
        Resizer mainMenuResizer = new Resizer(ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_TOP_LEFT,
                ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        mainMenu.setBounds(0, 0, width, 30);
        mainMenu.setBackground(new Color(.3f, .3f, .3f));
        mainMenu.setVisible(true);

        MenuItem item1 = new MenuItem("Item 1");
        item1.setVisible(true);
        MenuItem item2 = new MenuItem("Item 2");
        item2.setVisible(true);
        MenuItem item3 = new MenuItem("Item 3");
        item3.setVisible(true);

        Menu menu1 = new Menu("Menu 1");
        menu1.add(item1);
        menu1.add(item3);
        menu1.setVisible(true);
        menu1.revalidate();
        Menu menu2 = new Menu("Menu 2");
        menu2.add(item2);
        menu2.setVisible(true);
        menu2.revalidate();
        Menu menu3 = new Menu("Menu 3");
        menu3.setVisible(true);

        mainMenu.add(menu1);
        mainMenu.add(menu2);
        mainMenu.add(menu3);
        mainMenu.revalidate();
        mainMenu.repaint();

        this.add(mainMenu);
        this.setResizer(mainMenu, mainMenuResizer);

        
        //Outliner setup
        //outliner = new Outliner();
        
        //SubWindowArea setup
        Resizer subWindowAreaResizer = new Resizer(ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        SubWindowArea subWindowArea = new SubWindowArea(this.getContentPane().getWidth()-200, this.getContentPane().getHeight()-mainMenu.getHeight()-infoBar.getHeight());
        subWindowArea.setLocation(200, mainMenu.getHeight());
        subWindowArea.setVisible(true);
        this.add(subWindowArea);
        this.setResizer(subWindowArea, subWindowAreaResizer);

        //testing adding SubWindows
        SubWindow sub1 = new SubWindow(100, 100);
        sub1.setVisible(true);
        subWindowArea.addSubWindow(sub1);
        SubWindow sub2 = new SubWindow(100, 100);
        sub2.setBackground(new Color(.2f, .4f, .2f));
        sub2.setVisible(true);
        subWindowArea.addSubWindow(sub2);

        this.revalidate();
        this.repaint();
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("GELF - Graphical Editor for Liberty Files", 1200, 800);
    }
}