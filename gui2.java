import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Stack;

public class gui2 {

    static String path_name;
    static JFrame mainFrame;
    static JPanel mainPanel;
    static JPanel imagePanel;
    static JPanel optionsPanel;
    static JLabel xPos;
    static JLabel yPos;
    static JLabel tileCoord;
    static JLabel gridX;
    static JLabel gridX2;
    static int gridDistTotx = 0;
    static int gridDistToty = 0;
    static JLabel gridDistLabel;
    static JLabel gridSizeFinalLabel;
    static boolean measuring = false;
    static JButton measureButtonStatus;
    static JLabel squaresLabel;
    static JPanel directoryTab;
    static JSlider pixelSize;
    static int pixelDim = 15;
    static JLabel pixelLabel;
    static JPanel pixPanel;
    static JSlider xOffset;
    static JPanel xPanel;
    static int xNum;
    static JLabel xOffsetLabel;
    static JSlider yOffset;
    static JPanel yPanel;
    static int yNum;
    static JLabel yOffsetLabel;
    static JButton applyGrid;
    static JButton shade;
    static int counter = 0;
    static JPanel drawingControlPanel;
    static JComboBox tileOptions;
    static final String[] tileOptionsStrings = {"hallway", "room", "stairs"};
    static int tileOptionIndex = 0;
    static JButton drawingOption;
    static boolean drawingStatus = false;
    static Color currColor;
    static int alpha = 150;
    static final Color hallwayColor = new Color(135,204,255,alpha);
    static final Color roomColor = new Color(102,255,102,alpha);
    static final Color stairColor = new Color(255,153,51,alpha);
    static JButton connectTiles;
    static JPanel tileInfo;
    static JLabel tileName;
    static JLabel locInfo;
    static JLabel buildingTile;
    static JLabel floorTile;
    static JLabel sectionTile;
    static JLabel nextToTiles;
    static JLabel northTile;
    static JLabel southTile;
    static JLabel eastTile;
    static JLabel westTile;
    static JLabel tileTypeLabel;
    static JTextField startTile;
    static JTextField endTile;
    static JButton computePath;
    static JPanel computePathPanel;
    static JButton abc;
    static JButton fileDir;
    static JTree mainFileSystem;
    static JLabel treeLabel;

    public static void initalizeTree() {
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        //create the child nodes
        DefaultMutableTreeNode campus = new DefaultMutableTreeNode("University Of Minnesota");
        DefaultMutableTreeNode building = new DefaultMutableTreeNode("Yudof Hall");
        DefaultMutableTreeNode floor3 = new DefaultMutableTreeNode("3rd Floor");
        DefaultMutableTreeNode floor4 = new DefaultMutableTreeNode("4th Floor");

        building.add(floor3);
        building.add(floor4);
        campus.add(building);
        root.add(campus);
        treeLabel = new JLabel("");

        mainFileSystem = new JTree(root);
        mainFileSystem.setPreferredSize(new Dimension(250,600));
        mainFileSystem.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainFileSystem.getLastSelectedPathComponent();
                treeLabel.setText(e.getPath().toString());
            }
        });
        //directoryTab.add(new JScrollPane(mainFileSystem));

        directoryTab.add(mainFileSystem);
        directoryTab.add(treeLabel, BorderLayout.SOUTH);
    }

    public static void initializeMainPanels(){
        mainFrame = new JFrame("Mapping");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        imagePanel = new JPanel();
        optionsPanel = new JPanel();
        directoryTab = new JPanel();
        pixPanel = new JPanel();
        xPanel = new JPanel();
        yPanel = new JPanel();
        computePathPanel = new JPanel();
    }

    public static void createMappingPanels() {
        directoryTab.add(new JButton("Usless but placeholder"));
    }

    public static void createTileInfoLabels() {
        tileInfo = new JPanel();
        tileInfo.setPreferredSize(new Dimension(200, 300));
        tileInfo.setLayout(new BoxLayout(tileInfo, BoxLayout.PAGE_AXIS));
        tileName = new JLabel("Tile Name: n/a");
        locInfo = new JLabel(" -- Location Information -- ");
        buildingTile = new JLabel("Building: ");
        floorTile = new JLabel("Floor: ");
        sectionTile = new JLabel("Section: ");
        nextToTiles = new JLabel(" -- Directions --");
        northTile = new JLabel("North: ");
        eastTile = new JLabel("East: ");
        southTile = new JLabel("South: ");
        westTile = new JLabel("West: ");
        tileTypeLabel = new JLabel("Tile Type: n/a");

        tileInfo.add(tileName);
        tileInfo.add(locInfo);
        tileInfo.add(buildingTile);
        tileInfo.add(floorTile);
        tileInfo.add(sectionTile);
        tileInfo.add(nextToTiles);
        tileInfo.add(northTile);
        tileInfo.add(eastTile);
        tileInfo.add(southTile);
        tileInfo.add(westTile);
        tileInfo.add(tileTypeLabel);
    }

    public static void shadeLocationButton() {
        shade = new JButton("Shade Location");

        shade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color shadeColor = new Color(255, 231, 252, 150);
                ((MapDrawingPanel)imagePanel).shadeTile(counter, counter,shadeColor);
                counter++;
            }
        });
    }

    public static void createXandYOffsetSliders() {

        pixelSize = new JSlider(JSlider.HORIZONTAL,2,30,15);
        pixelDim = 15;
        pixelLabel = new JLabel("Grid Width (In Pixels): " + pixelDim);
        pixelSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //xOffset.setMaximum();
                pixelDim = pixelSize.getValue();
                System.out.println("Pixel Dim: " + pixelDim);
                xOffset.setMaximum(pixelDim);
                yOffset.setMaximum(pixelDim);
                pixelLabel.setText("Grid Width (In Pixels): " + pixelDim);
                System.out.println("PixelDIM SET TO : " + pixelDim);
            }
        });
        pixelSize.setMajorTickSpacing(3);
        pixelSize.setMinorTickSpacing(1);
        pixelSize.setPaintTicks(true);

        pixPanel.add(pixelSize);
        pixPanel.add(pixelLabel);

        xOffset = new JSlider(JSlider.HORIZONTAL,0,30,MapDrawingPanel.X_OFF_INIT);
        xOffsetLabel = new JLabel("X-Offset (In Pixels): " + xNum);
        xOffset.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                xNum = xOffset.getValue();
                System.out.println("X-Offset: " + xNum);
                xOffsetLabel.setText("X-Offset (In Pixels): " + xNum);
            }
        });
        xOffset.setMajorTickSpacing(3);
        xOffset.setMinorTickSpacing(1);
        xOffset.setPaintTicks(true);


        yOffset = new JSlider(JSlider.HORIZONTAL,0,30,MapDrawingPanel.Y_OFF_INIT);
        yOffsetLabel = new JLabel("Y-Offset (In Pixels): " + yNum);
        yOffset.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                yNum = yOffset.getValue();
                System.out.println("Y-Offset: " + yNum);
                yOffsetLabel.setText("Y-Offset (In Pixels): " + yNum);
            }
        });
        yOffset.setMajorTickSpacing(3);
        yOffset.setMinorTickSpacing(1);
        yOffset.setPaintTicks(true);

        Hashtable pixels = pixelSize.createStandardLabels(3,3);
        Hashtable offsets = xOffset.createStandardLabels(3,0);
        pixelSize.setLabelTable(pixels);
        pixelSize.setPaintLabels(true);
        xOffset.setLabelTable(offsets);
        xOffset.setPaintLabels(true);
        yOffset.setLabelTable(offsets);
        yOffset.setPaintLabels(true);
        xOffset.setMaximum(pixelDim);
        yOffset.setMaximum(pixelDim);

        xPanel.add(xOffset);
        xPanel.add(xOffsetLabel);
        yPanel.add(yOffset);
        yPanel.add(yOffsetLabel);

    }

    public static void initializeComputePath() {
        startTile = new JTextField("Start Tile Name: (#x#)");
        startTile.setColumns(11);
        endTile = new JTextField("End Tile Name: (#x#)");
        endTile.setColumns(11);
        computePath = new JButton("Compute Path");
        computePath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = startTile.getText();
                System.out.println("CATS: " + start);
                String end = endTile.getText();
                Tile begin = TesterClass.a.findTileByNameInLayout(start);
                Tile ending = TesterClass.a.findTileByNameInLayout(end);
                Stack<Integer> instruct = TesterClass.a.findPath(begin,ending);
                TesterClass.a.printInstructions(instruct);
            }
        });
        computePathPanel.add(startTile);
        computePathPanel.add(endTile);
        computePathPanel.add(computePath);
    }

    public static void initializeOptionDrawingSubPanel() {
        connectTiles = new JButton("Connect Tiles");
        connectTiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TesterClass.a.connectTilesInLayout();
            }
        });
        drawingControlPanel = new JPanel();
        drawingOption = new JButton("Drawing Status: " + drawingStatus);
        drawingOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingStatus = !drawingStatus;
                if(drawingStatus) {
                    drawingOption.setText("Drawing Status: drawing");
                } else {
                    drawingOption.setText("Drawing Status: erasing");
                }

            }
        });
        tileOptions = new JComboBox(tileOptionsStrings);
        tileOptions.setSelectedIndex(0);
        currColor = hallwayColor;
        tileOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                tileOptionIndex = cb.getSelectedIndex();
                if(tileOptionIndex == 0) {
                    currColor = hallwayColor;
                } else if(tileOptionIndex == 1) {
                    currColor = roomColor;
                } else {
                    currColor = stairColor;
                }
            }
        });
    }

    public static void initializeMeasureAndTrackingInfo() {
        squaresLabel = new JLabel("Squares in Line: ?");

        gridDistLabel = new JLabel("Pixels in between each grid: ?");
        gridSizeFinalLabel = new JLabel("Current Official Grid Size: N/a");

        measureButtonStatus = new JButton("Measuring Status: OFF");
        measureButtonStatus.setBackground(Color.RED);
        measureButtonStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridDistTotx = 0;
                gridDistToty = 0;
                if(measuring) {
                    measureButtonStatus.setBackground(Color.RED);
                    measureButtonStatus.setText("Measuring Status: OFF");
                } else {
                    measureButtonStatus.setBackground(Color.YELLOW);
                    measureButtonStatus.setText("Measuring Status: ON");
                }
                measuring = !measuring;
            }
        });

        xPos = new JLabel("X-Position: 0");
        yPos = new JLabel("Y-Position: 0");
        gridX  = new JLabel("Grid X Pos 1: n/a");
        gridX2 = new JLabel("Grid X Pos 2: n/a");
        tileCoord = new JLabel("Current Tile Coord: X: N/a   Y: N/a");
    }

    public static void initializeDebugStuff() {
        abc = new JButton("DrawMapToConsole");
        abc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(dimensionOfGrid());
                TesterClass.a.printLayout();
            }
        });
    }

    public static void initializeFilepath() {
        fileDir = new JButton("Choose Filepath");
        fileDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path_name = fileChooser();
                try {
                    mainPanel.remove(imagePanel);
                    imagePanel = new MapDrawingPanel(imagePanel, optionsPanel, path_name);
                    mainPanel.add(imagePanel,BorderLayout.CENTER);
                    imagePanel.repaint();
                    imagePanel.revalidate();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void createGUI() {

        initializeMainPanels();

        initalizeTree();

        //createMappingPanels();

        initializeComputePath();

        createTileInfoLabels();

        initializeOptionDrawingSubPanel();

        createXandYOffsetSliders();

        applyGrid = new JButton("Apply Grid");
        applyGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CATS");
                ((MapDrawingPanel)imagePanel).drawGrid(xNum,yNum,pixelDim);
                TesterClass.doStuff();
            }
        });

        initializeMeasureAndTrackingInfo();

        initializeFilepath();

        initializeDebugStuff();

        tileInfo.add(tileCoord);
        optionsPanel.add(fileDir);
        optionsPanel.add(xPos);
        optionsPanel.add(yPos);
        optionsPanel.add(gridX);
        optionsPanel.add(gridX2);
        optionsPanel.add(measureButtonStatus);
        optionsPanel.add(squaresLabel);
        optionsPanel.add(tileCoord);

        /*optionsPanel.add(pixelSize);
        optionsPanel.add(pixelLabel);
        optionsPanel.add(xOffset);
        optionsPanel.add(xOffsetLabel);
        optionsPanel.add(yOffset);
        optionsPanel.add(yOffsetLabel);*/

        optionsPanel.add(pixPanel);
        optionsPanel.add(xPanel);
        optionsPanel.add(yPanel);

        optionsPanel.add(applyGrid);
        //optionsPanel.add(shade);
        optionsPanel.add(drawingOption);
        optionsPanel.add(abc);
        optionsPanel.add(tileOptions);
        optionsPanel.add(connectTiles);
        optionsPanel.add(tileInfo);


        /*optionsPanel.add(startTile);
        optionsPanel.add(endTile);
        optionsPanel.add(computePath);*/

        optionsPanel.add(computePathPanel);

        optionsPanel.setBackground(Color.GREEN);
        imagePanel.setBackground(Color.RED);
        optionsPanel.setPreferredSize(new Dimension(400,600));
        directoryTab.setBackground(Color.CYAN);
        mainPanel.add(optionsPanel, BorderLayout.EAST);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(directoryTab,BorderLayout.WEST);
        mainFrame.add(mainPanel);
        mainFrame.setSize(1200,1000);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);

    }

    public static String fileChooser() {
        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        } else {
            return "FILE FIND ERROR";
        }
    }

    public static BufferedImage getImage(){
        File image = new File(path_name);
        BufferedImage newPic = null;
        try {
            newPic = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newPic;
    }

    public static int dimensionOfGrid() {
        return Math.floorDiv((MapDrawingPanel.IMAGE_DIMENSION - xNum),pixelDim) + 2;
    }

    public static void main(String[] args) {
        createGUI();
        System.out.println(dimensionOfGrid());
    }
}


