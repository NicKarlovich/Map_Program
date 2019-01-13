import java.util.ArrayList;
import java.util.Stack;

public class Section {

    private Tile[] list;
    private Tile[][] layout;
    private int floor;
    private int sectionNum;
    private int building;

    //TODO add null checking to getnorth & south and such

    public Section(){
        Tile[][] layout;
    }

    public Section(Tile[][] floor) {
        layout = floor;
    }

    public Section(Tile[] list) {
        this.list = list;
    }

    public Section(int x_size, int y_size) {
        this.layout = new Tile[x_size][y_size];
    }

    public void createLayout(int x_size, int y_size) {
        this.layout = new Tile[x_size][y_size];
    }

    public Tile[][] getLayout(){
        return layout;
    }

    public void setLayout(Tile[][] floor) {
        layout = floor;
    }

    public Tile[] getList() {
        return list;
    }

    public void setList(Tile[] list) {
        this.list = list;
    }

    public void setNorthSouth(Tile top, Tile bottom) {
        if(top == null || bottom == null) {
            System.out.println("setNorthSouthFailed");
        } else {
            top.setSouth(bottom);
            bottom.setNorth(top);
        }
    }

    public void setEastWest(Tile left, Tile right) {
        if(left == null || right == null) {
            System.out.println("setEastWestFailed");
        } else {
            left.setEast(right);
            right.setWest(left);
        }
    }

    public Tile findTileByNameInLayout(String name) {
        for(Tile[] x : layout) {
            for(Tile y : x) {
                if(y != null && y.getName().equals(name)) {
                    return y;
                }

            }
        }
        return null;
    }

    public Tile findTileByName(String name) {
        for(Tile x : list) {
            if(x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    public Stack findPath(Tile start, Tile end){
        Stack<Integer> instructions = new Stack();
        ArrayList<Tile> prevTiles = new ArrayList<Tile>();
        instructions.push(start.findTile(end.getName(),instructions,prevTiles));
        return instructions;
    }

    public void printLayout(){
        for(Tile[] aTile : layout){
            for(Tile bTile: aTile) {
                if(bTile == null) {
                    System.out.print("[nul] ");
                } else {
                    System.out.print("[" + convertTileTypeToIDString(bTile.getTileType()) + "] ");
                }
            }
            System.out.println();
        }
    }

    public String convertTileTypeToIDString(int a) {
        if(a == 0) {
            return "nul";
        } else if(a == 1) {
            return "hal";
        } else if(a == 2) {
            return "rom";
        } else {
            return "str";
        }
    }

    public void connectTilesInLayout(){
        //i is col num, j is row num like [i][j]
        for(int i = 0; i < layout.length; i++) {
            for(int j = 0; j < layout.length; j++) {
                if(i + 1 < layout.length) {
                    setNorthSouth(layout[i][j], layout[i + 1][j]);
                }
                if(j + 1 < layout.length) {
                    setEastWest(layout[i][j], layout[i][j + 1]);
                }
            }
        }
    }

    public void printInstructions(Stack<Integer> instructionStack) {
        while(!instructionStack.empty()) {
            int a = instructionStack.pop();
            System.out.println(decodeInt(a));
        }
    }

    private String decodeInt(int x) {
        switch (x) {
            case 1:
                return "North";
            case 2:
                return "South";
            case 3:
                return "East";
            case 4:
                return "West";
            case 5:
                return "Found It";
            default:
                return "FUCK";
        }
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getSectionNum() {
        return sectionNum;
    }

    public void setSectionNum(int sectionNum) {
        this.sectionNum = sectionNum;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }



}
