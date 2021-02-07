package gelf.view.composites;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.view.components.Menu;
import gelf.view.components.MenuBar;
import gelf.view.components.MenuItem;
import gelf.view.components.ResizeMode;
import gelf.view.components.Resizer;
import gelf.view.components.Window;

/**
 * MainWindow
 */
public class MainWindow extends Window {
    // GUI subparts
    MenuBar mainMenu;
    Menu menuFile;
    MenuItem itemNew;
    MenuItem itemOpen;
    MenuItem itemSave;
    MenuItem itemSaveAll;
    MenuItem itemSaveAs;
    MenuItem itemClose;
    Menu menuEdit;
    MenuItem itemUndo;
    MenuItem itemRedo;
    MenuItem itemMergeSelected;
    MenuItem itemMerge;
    MenuItem itemSettings;
    Menu menuInfo;
    MenuItem itemManual;
    MenuItem itemGithub;
    MenuItem itemVersion;

    InfoBar infoBar;
    Outliner outliner;
    SubWindowArea subWindowArea;
    // colors/graphics
    Color cBackground = new Color(0.1f, 0.1f, 0.1f);
    Image icon = Toolkit.getDefaultToolkit().getImage("/Images/AppIcon.png");
    // other
    String version = "0.0.0";

    public MainWindow(String name, int width, int height) {
        // MainWindow setup
        super(name, width, height);
        this.setSize(width, height);
        this.getContentPane().setBackground(cBackground);
        this.setIconImage(icon);
        this.setVisible(true);

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
        setupMainMenu(this.getContentPane().getWidth(), 30);

        // Outliner setup
        // outliner = new Outliner();

        // SubWindowArea setup
        Resizer subWindowAreaResizer = new Resizer(ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_TOP_LEFT,
                ResizeMode.ABSOLUTE_BOTTOM_RIGHT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        SubWindowArea subWindowArea = new SubWindowArea(this.getContentPane().getWidth() - 200,
                this.getContentPane().getHeight() - mainMenu.getHeight() - infoBar.getHeight());
        subWindowArea.setLocation(200, mainMenu.getHeight());
        subWindowArea.setVisible(true);
        this.add(subWindowArea);
        this.setResizer(subWindowArea, subWindowAreaResizer);

        // testing adding SubWindows
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

    private void setupMainMenu(int width, int height) {
        // make menu bar
        mainMenu = new MenuBar();
        mainMenu.setBounds(0, 0, width, height);
        mainMenu.setBackground(new Color(.3f, .3f, .3f));
        mainMenu.setVisible(true);
        // set resize bahaviour
        Resizer mainMenuResizer = new Resizer(ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_TOP_LEFT,
                ResizeMode.ABSOLUTE_TOP_LEFT, ResizeMode.ABSOLUTE_BOTTOM_RIGHT);
        this.setResizer(mainMenu, mainMenuResizer);

        // initialize menu items 
        //stub for items with no implemented action
        ActionListener noAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//does nothing
			}
        };

        //file menu items
        this.itemNew = initMenuItem("New", noAction);
        this.itemOpen = initMenuItem("Open", noAction);
        this.itemSave = initMenuItem("Save", noAction);
        this.itemSaveAll = initMenuItem("Save All", noAction);
        this.itemSaveAs = initMenuItem("Save As", noAction);
        this.itemClose = initMenuItem("Close", noAction);
        
        //edit menu items
        this.itemUndo = initMenuItem("Undo", noAction);
        this.itemRedo = initMenuItem("Redo", noAction);
        this.itemMergeSelected = initMenuItem("Merge Selected", noAction);
        this.itemMerge = initMenuItem("Merge", noAction);
        this.itemSettings = initMenuItem("Settings", noAction);
        
        //info menu items
        this.itemManual = initMenuItem("Manual", noAction);
        this.itemGithub = initMenuItem("Github", noAction);
        this.itemVersion = initMenuItem("Version", noAction);

        //initialize menus
        //file menu
        this.menuFile = new Menu("File");
        this.menuFile.add(this.itemNew);
        this.menuFile.add(this.itemOpen);
        this.menuFile.add(this.itemSave);
        this.menuFile.add(this.itemSaveAll);
        this.menuFile.add(this.itemSaveAs);
        this.menuFile.add(this.itemClose);
        this.menuFile.setVisible(true);
        this.mainMenu.add(menuFile);
        //edit menu
        this.menuEdit = new Menu("Edit");
        this.menuEdit.add(this.itemUndo);
        this.menuEdit.add(this.itemRedo);
        this.menuEdit.add(this.itemMergeSelected);
        this.menuEdit.add(this.itemMerge);
        this.menuEdit.add(this.itemSettings);
        this.menuEdit.setVisible(true);
        this.mainMenu.add(menuEdit);
        //info menu
        this.menuInfo = new Menu("Info");
        this.menuInfo.add(this.itemManual);
        this.menuInfo.add(this.itemGithub);
        this.menuInfo.add(this.itemVersion);
        this.menuInfo.setVisible(true);
        this.mainMenu.add(menuInfo);
        
        //make swing update and add to window
        mainMenu.revalidate();
        mainMenu.repaint();
        this.add(mainMenu);
    }

    private MenuItem initMenuItem(String name, ActionListener action) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.addActionListener(action);
        menuItem.setVisible(true);
        return menuItem;
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("GELF - Graphical Editor for Liberty Files", 1200, 800);
    }
}