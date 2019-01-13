import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class gui {

    static String path_name;
    static JFrame mainFrame;
    static JPanel mainPanel;
    static JPanel imagePanel;
    static JPanel optionsPanel;
    static JLabel picLabel;
    static JLabel xPos;
    static JLabel yPos;
    static JLabel tileCoord;
    static JLabel gridX;
    static JLabel gridX2;
    static boolean determineSize;
    static int numTests = 0;
    static int grid1x = 0;
    static int grid2x = 0;
    static int grid1y = 0;
    static int grid2y = 0;
    static int gridDistTotx = 0;
    static int gridDistToty = 0;
    static JLabel gridDistLabel;
    static JButton gridSize;
    static int gridDistFinal;
    static JButton gridSizeFinalize;
    static JLabel gridSizeFinalLabel;
    static boolean measuring = false;
    static JButton measureButtonStatus;
    static int squaresCovered = 0;
    static JLabel squaresLabel;
    static boolean topLine = false;
    static int topNum;
    static boolean leftLine = false;
    static int leftNum;
    static JLabel topLineButton;
    static JLabel leftLineButton;


    public static int determineXorY() {
        //return 1 if in x direction
        //return 0 if in y direction
        if(gridDistTotx > gridDistToty) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void createGUI() {
        mainFrame = new JFrame("Mapping");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        imagePanel = new JPanel();
        optionsPanel = new JPanel();



        JButton topLineButton = new JButton("Click to set Top Line Y-Coord");
        topLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topLine = !topLine;
                if(topLine) {
                    topLineButton.setText("Setting Y-Coord");
                } else {
                    topLineButton.setText("Click to set Top Line Y-Coord-curr: " + topNum);
                }
            }
        });
        JButton leftLineButton = new JButton("Click to set Left Line X-Coord");
        leftLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftLine = !leftLine;
                if(leftLine){
                    leftLineButton.setText("Setting X-Coord");
                } else {
                    leftLineButton.setText("Click to set Left Line X-Coord-curr: " +leftNum);
                }
            }
        });

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

        JButton gridSizeFinalize = new JButton("Finalize Grid Size");
        gridSizeFinalize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridDistFinal = gridDistTotx;
                gridSizeFinalLabel.setText("Current Official Grid Size: " + gridDistFinal);
            }
        });

        JButton gridSize = new JButton("Zero-Out Grid Size");
        gridSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                determineSize = !determineSize;
                if(numTests != 0) {
                    System.out.println("MAYBE");
                    gridDistTotx = gridDistTotx / numTests;
                    gridDistLabel.setText("Pixels in between each grid: " + gridDistTotx);
                    numTests = 0;
                } else {
                    System.out.println("MAYBE2");
                    gridDistTotx = 0;
                    gridDistLabel.setText("Pixels in between each grid: " + gridDistTotx);
                }
            }
        });

        JLabel xPos = new JLabel("X-Pos: 0");
        JLabel yPos = new JLabel("Y-Pos: 0");
        JLabel gridX = new JLabel("Grid X Pos 1: n/a");
        JLabel gridX2 = new JLabel("Grid X Pos 2: n/a");
        tileCoord = new JLabel("Current Tile Coord: X: N/a   Y: N/a");

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                xPos.setText("X-Pos: " + x);
                yPos.setText("Y-Pos: " + y);
            }

            public void mouseDragged(MouseEvent e) {
                //int x = e.getX();
            }
        });

        imagePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int y = e.getY();
                int x = e.getX();
                if(topLine) {
                    topNum = y;
                }
                if(leftLine) {
                    leftNum = x;
                }
                y += gridDistFinal;
                y = ((y - topNum) / gridDistFinal);
                x += gridDistFinal;
                x = ((x - leftNum) / gridDistFinal);
                tileCoord.setText("Current Tile Coord: X: " + x + "   Y: " + y);

            }
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                gridX.setText("Grid X Pos 1: " + x);
                grid1x = x;
                grid1y = y;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                gridX2.setText("Grid X Pos 2: " + x);
                grid2x = x;
                grid2y = y;
                gridDistTotx += Math.abs(grid2x - grid1x);
                gridDistToty += Math.abs(grid2y - grid1y);
                if(measuring) {
                    int a = determineXorY();
                    grid2x += gridDistFinal;
                    grid2y += gridDistFinal;
                    grid1x += gridDistFinal;
                    grid1y += gridDistFinal;
                    gridDistTotx = Math.abs(((grid2x - leftNum) / gridDistFinal) - ((grid1x - leftNum) / gridDistFinal)) + 1;
                    gridDistToty = Math.abs(((grid2y - topNum) / gridDistFinal) - ((grid1y - topNum) / gridDistFinal)) + 1;
                    if(a == 1) {
                        squaresCovered = gridDistTotx;
                    } else {
                        squaresCovered = gridDistToty;
                    }
                    //System.out.println("toty: "+ gridDistToty + "   totx: " + gridDistTotx + "  gridDistFinal: " + gridDistFinal);
                    squaresLabel.setText("Squares in Line: " + squaresCovered);
                    gridDistTotx = 0;
                    gridDistToty = 0;
                }

                //System.out.println("griddisttot" + gridDistTotx);
                numTests++;
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



        JButton fileDir = new JButton("Choose Filepath");
        fileDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path_name = fileChooser();
                showImage();
                System.out.println("PATH NAME: " + path_name);
            }
        });


        optionsPanel.add(fileDir);
        optionsPanel.add(xPos);
        optionsPanel.add(yPos);
        optionsPanel.add(gridX);
        optionsPanel.add(gridX2);
        optionsPanel.add(gridSize);
        optionsPanel.add(gridDistLabel);
        optionsPanel.add(gridSizeFinalize);
        optionsPanel.add(gridSizeFinalLabel);
        optionsPanel.add(measureButtonStatus);
        optionsPanel.add(squaresLabel);
        optionsPanel.add(topLineButton);
        optionsPanel.add(leftLineButton);
        optionsPanel.add(tileCoord);
        optionsPanel.setBackground(Color.GREEN);
        imagePanel.setBackground(Color.RED);
        optionsPanel.setPreferredSize(new Dimension(300,600));
        mainPanel.add(optionsPanel, BorderLayout.EAST);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
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

    public static Image getImage(){
        File image = new File(path_name);
        Image newPic = null;
        try {
            newPic = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newPic;
    }

    public static void showImage() {
        Image image = getImage();
        int imageW = image.getWidth(new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });

        int imageH = image.getHeight(new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });

        image = image.getScaledInstance(1024,1024, Image.SCALE_REPLICATE);
        picLabel = new JLabel(new ImageIcon(image));
        imagePanel.add(picLabel);
        imagePanel.revalidate();
        imagePanel.repaint();
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    public static void main(String[] args) {
        createGUI();
    }
}
