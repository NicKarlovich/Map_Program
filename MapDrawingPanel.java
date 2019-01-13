import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapDrawingPanel extends JPanel {

    private BufferedImage backgroundImg;
    private BufferedImage originalImg;
    private Point startPt;
    private Point endPt;
    private Point currentPt;
    private int w;
    private int h;
    //private final static String PATH = "H:\\Desktop\\yudof_floor_3.jpg";
    public final static int PIXEL_SIZE_INIT = 20;
    public final static int X_OFF_INIT = 10;
    public final static int Y_OFF_INIT = 10;
    public final static int PADDING = 10;
    public final static int IMAGE_DIMENSION = 1024;
    public final static int NULL_TILE = 0;
    public final static int HALLWAY_TILE = 1;
    public final static int ROOM_TILE = 2;
    public final static int STAIR_TILE = 3;

    public static int counter = 0;
    public static Color currDrawColor = new Color(0,0,0);

    private JPanel linkToOptionPanel;
    public int pixelSize = PIXEL_SIZE_INIT;
    public int x_off = X_OFF_INIT;
    public int y_off = Y_OFF_INIT;
    private int padding = PADDING;


    /*
        - Get Image function that does the actual File to Image object translation.
     */
    public static BufferedImage getImage(String path_name){
        File image = new File(path_name);
        BufferedImage newPic = null;
        try {
            newPic = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newPic;
    }

    /*
        -Constructor
        -when first called it pulls the image file and displays it on the GUI
        - Creates mouseAdapater and MouseListener.
     */
    public MapDrawingPanel(JPanel destPanel, JPanel optPanel, String path) throws IOException {
        originalImg = getImage(path);
        h = originalImg.getHeight();
        w = originalImg.getWidth();
        backgroundImg = new BufferedImage(IMAGE_DIMENSION, IMAGE_DIMENSION, BufferedImage.TYPE_INT_ARGB);
        Graphics g = backgroundImg.getGraphics();
        g.drawImage(originalImg, 0, 0, this);
        g.dispose();
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        addMouseMotionListener(myMouseAdapter);
        addMouseListener(myMouseAdapter);
        getCurrDrawColor();
    }

    /*
        Getter function accross to gui2.
        TODO remove explicit calls as such if possible.
     */
    public static void getCurrDrawColor() {
        currDrawColor = gui2.currColor;
    }

    /*
        Takes a point in (x,y) form and converts it into tile coordinates
     */
    public Point getCurrentTileCoord(Point p) {
        Point x;
        if(p.x > -1 && p.y > -1) {
            p.x = Math.floorDiv(p.x - x_off - padding, pixelSize) + 1;
            p.y = Math.floorDiv(p.y - y_off - padding, pixelSize) + 1;
            x = p;
        } else {
            System.out.println("Current Tile Coords OUT OF BOUNDS\nx: " + p.x + " y: " + p.y);
            x = null;
        }
        return x;
    }

    /*
        Converts coordinates in tile form into a pixel representation
        of the coordinates.  The (x,y) coordinate is placed in the top
        left corner of the square
     */
    public Point tileCoordToPoint(int x, int y) {
        if(x < 0 || y < 0) {
            System.out.println("WARNING: NegativeIndex\nx: " + x + " y: " + y);
        }
        Point p = new Point(x,y);
        p.x = (p.x - 1) * pixelSize;
        p.y = (p.y - 1) * pixelSize;
        p.x = p.x + x_off;
        p.y = p.y + y_off;
        return p;
    }

    /*
    TODO remove explicit call, ie TesterClass.a -> some function
    Updates tile[][] in TesterClass  with correct information from what was drawn on screen.
     */
    public void setTileStatus(int x, int y, int status) {
        //remember 2d array is columns (down) then rows(right)

        //sets tile to null if it is erased
        if(status == 0) {
            TesterClass.a.getLayout()[y][x] = null;

            //if tile doesn't exist / null
        } else if(TesterClass.a.getLayout()[y][x] == null) {
            TesterClass.a.getLayout()[y][x] = new Tile(x,y,3,counter,1,1,y + "x" + x,status);

            //if already created
        } else if(TesterClass.a.getLayout()[y][x].getTileType() != status){
            TesterClass.a.getLayout()[y][x].setTileType(status);
        } else {
            //this means it assigning the status to a room that already has that status
            //we do nothing in this case but this is here for continuity and explanitory
        }
        counter++;
    }

    /*
    Sends update info to panel in GUI
     */
    public void updateTileInfoPanel(Point p){
        Tile x = TesterClass.a.getLayout()[p.y][p.x];
        if(x == null) {
            gui2.tileName.setText("Tile Name: null");
            gui2.buildingTile.setText("Building: null");
            gui2.floorTile.setText("Floor: null");
            gui2.sectionTile.setText("Section: null");
            gui2.northTile.setText("North: null");
            gui2.eastTile.setText("East: null");
            gui2.southTile.setText("South: null");
            gui2.westTile.setText("West: null");
            gui2.tileTypeLabel.setText("Tile Type: null");
        } else {
            gui2.tileName.setText("Tile Name: " + x.getName());
            gui2.buildingTile.setText("Building: " + x.getBuilding());
            gui2.floorTile.setText("Floor: " + x.getFloor());
            gui2.sectionTile.setText("Section: " + x.getSection());
            /*
                If statements are here for protection from local null tiles,
                TODO move this null checking to somewhere more convenient like Tile Class.
             */
            if(x.getNorth() == null) {
                gui2.northTile.setText("North: null");
            } else {
                gui2.northTile.setText("North: " + x.getNorth().getName());
            }
            if(x.getSouth() == null) {
                gui2.southTile.setText("South: null");
            } else {
                gui2.southTile.setText("South: " + x.getSouth().getName());
            }
            if(x.getEast() == null) {
                gui2.eastTile.setText("East: null");
            } else {
                gui2.eastTile.setText("East: " + x.getEast().getName());
            }
            if(x.getWest() == null) {
                gui2.westTile.setText("West: null");
            } else {
                gui2.westTile.setText("West: " + x.getWest().getName());
            }
            gui2.tileTypeLabel.setText("Tile Type: " + x.getTileType());
        }
    }

    /*
        Resets the tile to its original image, a.k.a. "clears paint"
     */
    public void resetTile(int xTileCoord, int yTileCoord) {
        Point p = tileCoordToPoint(xTileCoord, yTileCoord);
        Graphics g = backgroundImg.getGraphics();
        /*
            This loop runs for every pixel in a "tile coordinate" ie 15x15 grid
            and sets its value to whatever the original value was in the original image.
         */
        for(int x = 1; x < pixelSize; x++) {
            for(int y = 1; y < pixelSize; y++) {
                /*
                    Edge case for the grid when only half a square is visible, this way
                    the program doesn't try to pull RGB values from outside the image's bounds
                    ie doesnt try to get pixel at (-1,-1) on original image which obviously doesn't exist
                 */
                if((p.x + x) < 0 || (p.y + y) < 0 || (p.x + x) > 1023 || (p.y + y) > 1023) {
                    //do nothing
                } else {
                    int rgbVer = 0;
                    /*
                        this try-statement shouldn't be necessary because of the previous if statement
                        that should remove this edge case
                     */
                    try {
                        rgbVer = originalImg.getRGB(p.x + x, p.y + y);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("OUT OF BOUNDS: " + p.x + " " + x + " " + p.y + " " + y);
                    }
                    int red = (rgbVer >> 16) & 0xff;
                    int green = (rgbVer >> 8) & 0xff;
                    int blue = rgbVer & 0xff;
                    Color backgroundColor = new Color(red, green, blue, 255);
                    g.setColor(backgroundColor);
                    //Draws a dot with the color from original image on displayed image.
                    g.drawLine(p.x + x, p.y + y, p.x + x, p.y + y);
                }
            }
        }
        setTileStatus(xTileCoord, yTileCoord, NULL_TILE);
        g.dispose();
        repaint();
    }

    /*
        - Checks if a point is inside the bounds inclusively
     */
    public boolean checkIfPointInBounds(int xMin, int yMin, int xMax, int yMax, Point p) {
        return (p.x >= xMin && p.y >= yMin && p.x <= xMax && p.y <= yMax);
    }

    /*
        -Overidden mouse adapter to do special functions
        -Functions Overidden:
        mouseClicked
        mouseMoved
        mouseDragged
        mousePressed
        mouseReleased
     */
    private class MyMouseAdapter extends MouseAdapter {

        /*
            Gets current x & y coords in tile coordinates
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = getCurrentTileCoord(e.getPoint());
            gui2.tileCoord.setText("Current Tile Coord: X: " + p.x + "   Y: " + p.y);
            updateTileInfoPanel(p);
        }

        /*
            -Checks if mouse is moving
            -If mouse is moving it updates the current pos label in GUI
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            currentPt = e.getPoint();
            if(currentPt.x < padding || currentPt.x > padding + w) {
                currentPt.x = -1;
            } else {
                currentPt.x = currentPt.x - padding;
            }
            if(currentPt.y < padding || currentPt.y > padding + h) {
                currentPt.y = -1;
            } else {
                currentPt.y = currentPt.y - padding;
            }
            gui2.xPos.setText("X-Position: " + currentPt.x);
            gui2.yPos.setText("Y-Position: " + currentPt.y);
        }

        /*
            - "Drawing" function, determines which tiles have their value set to
            either one color of a transparent color or to have it reset or to have the
            tile have the colors on top "erased" a.k.a. reset.
            TODO Fix weird measuring and click ability bug, last else line
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            currentPt = e.getPoint();

            /*
            checks if point is in bounds of image on screen
            we include the padding because from 0 -> padding we can't draw so to make sure
            its in bounds of the image grid we add padding.
             */
            if(!checkIfPointInBounds(padding, padding, w + padding, h + padding, currentPt)) {
                //if out of bounds -> do nothing
            } else {
                Point p = getCurrentTileCoord(currentPt);//turns pixel(x,y) into tile coordinates

                //If we are drawing
                if(gui2.drawingStatus) {
                    getCurrDrawColor();
                    Color aColor = currDrawColor;
                /*
                    If the tile that is being drawn on is null (ie. does not exist)
                     then shade that tile and set it's status to the correct one.
                 */
                    if(TesterClass.a.getLayout()[p.y][p.x] == null) {
                        shadeTile(p.x, p.y, aColor);
                        setTileStatus(p.x, p.y, gui2.tileOptionIndex + 1);
                    /*
                    If the tile you're looking at drawing DOES exist and if it's type
                    is different than what the paintbrush is drawing THEN repaint it.
                     */
                    } else if(TesterClass.a.getLayout()[p.y][p.x].getTileType() != gui2.tileOptionIndex + 1) {
                        resetTile(p.x, p.y);
                        shadeTile(p.x, p.y, aColor);
                        setTileStatus(p.x, p.y, gui2.tileOptionIndex + 1);
                    } else {
                    /*
                    Do nothing because the tile the mouse is on has been drawn on
                    and there is nothing to update.
                    This allows for there to be a transparent color on the screen, otherwise
                    it would continually fill in tile causing it to blot out all colors behind it.
                     */
                    }
                    //if erasing and not measuring
                } else if(!gui2.drawingStatus && !(gui2.measuring)) {
                    resetTile(p.x,p.y);
                }
            }
        }

        /*
        Gets the beginning point of where a mouse is pressed down
         */
        @Override
        public void mousePressed(MouseEvent e) {
            startPt = e.getPoint();// in x-y coordinate
            gui2.gridX.setText("Grid Start - x: " + startPt.x + "  y: " + startPt.y);
        }

        /*
            - "Measurement" functionality, or part 2 of it.
            Gets the end location of where the cursor lifts up after being dragged
            This is used to measure the net distance in the X & Y directions.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            endPt = e.getPoint();
            gui2.gridX2.setText("Grid End - x: " + endPt.x + "  y: " + endPt.y);
            int tilesCovered;
            if(gui2.measuring) {
                startPt = getCurrentTileCoord(startPt);
                endPt = getCurrentTileCoord(endPt);
                int netXMeasured = Math.abs(endPt.x - startPt.x) + 1;
                int netYMeasured = Math.abs(endPt.y - startPt.y) + 1;
                boolean a = netXMeasured > netYMeasured;
                if(a) {
                    tilesCovered = netXMeasured;
                } else {
                    tilesCovered = netYMeasured;
                }
                gui2.squaresLabel.setText("Squares in Line: " + tilesCovered);
            }
        }
    }

    /*
        -Resets image
        -Draws a grid on the DrawingPanel with the passed vars
        - x_offset, y_offset, grid_size in pixels.
     */
    public void drawGrid(int xOff, int yOff, int gridSize) {
        Graphics g = backgroundImg.getGraphics();
        g.drawImage(originalImg, 0, 0, this);
        g.setColor(Color.BLUE);
        x_off = xOff;
        y_off = yOff;
        pixelSize = gridSize;
        int xC, yC, x1, x2, y1, y2;
        for(xC = 0; xC < w; xC += pixelSize) {
            x1 = x_off + xC;
            x2 = x_off + xC;
            y1 = 0;
            y2 = h;
            g.drawLine(x1, y1, x2, y2);
        }
        for(yC = 0; yC < h; yC += pixelSize) {
            x1 = 0;
            x2 = w;
            y1 = y_off + yC;
            y2 = y_off + yC;
            g.drawLine(x1,y1,x2,y2);
        }
        g.dispose();
        repaint();
    }

    /*
        -Shades a tile at TileCoordinate, not including boundary lines
     */
    public void shadeTile(int xTileCoord, int yTileCoord, Color color) {
        Point p = tileCoordToPoint(xTileCoord, yTileCoord);
        Graphics g = backgroundImg.getGraphics();
        g.setColor(color);
        g.fillRect(p.x + 1, p.y + 1, pixelSize - 1, pixelSize - 1);
        g.dispose();
        repaint();
    }

    /*
        -If its not instantiated then it puts the background Image up.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundImg != null) {
            g.drawImage(backgroundImg, padding, padding, this);
        }
    }
}
