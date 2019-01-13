import java.util.ArrayList;
import java.util.Stack;

public class Tile {

    private int x;
    private int y;
    private int floor;
    private Tile north;
    private Tile east;
    private Tile south;
    private Tile west;
    private int id;
    private int section;
    private int building;
    private String name;
    private int tileType;

    public Tile() {

    }

    public Tile (String name,int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public Tile(String name, int id, int x_index, int y_index) {
        super();
        this.name = name;
        this.id = id;
        x = x_index;
        y = y_index;
    }

    public Tile(int x, int y, int floor, int id, int section, int building, String name, int tileType) {
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.id = id;
        this.section = section;
        this.building = building;
        this.name = name;
        this.tileType = tileType;
    }

    public int findTile(String name, Stack<Integer> i, ArrayList<Tile> searched) {
        int n = 0;
        int s = 0;
        int e = 0;
        int w = 0;

        if(name == this.name) {
            return 5;
        } else {
            searched.add(this);
            Tile up = this.getNorth();

            if (up == null || searched.contains(up)) {
                n = 0;
            } else {
                searched.add(up);
                n = up.findTile(name, i, searched);
                if(n == 0) {

                } else {
                    i.push(n);
                    return 1;
                }
            }


            Tile down = this.getSouth();
            if (down == null || searched.contains(down)) {
                s = 0;
            } else {
                searched.add(down);
                s = down.findTile(name, i, searched);
                if(s == 0) {

                } else {
                    i.push(s);
                    return 2;
                }
            }


            Tile right = this.getEast();
            if (right == null || searched.contains(right)) {
                e = 0;
            } else {
                searched.add(right);
                e = right.findTile(name, i, searched);
                if(e == 0) {

                } else {
                    i.push(e);
                    return 3;
                }
            }


            Tile left = this.getWest();
            if (left == null || searched.contains(left)) {
                w = 0;
            } else {
                searched.add(left);
                w = left.findTile(name, i, searched);
                if(w == 0) {

                } else {
                    i.push(w);
                    return 4;
                }
            }

        }
        return 0;
    }



//Getters and Setters


    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getFloor() {
        return this.floor;
    }

    public void setX(int x_input) {
        this.x = x_input;
    }

    public void setY(int y_input) {
        this.y = y_input;
    }

    public void setFloor(int z_input) {
        this.floor = z_input;
    }

    public void setNorth(Tile north) {
        this.north = north;
    }

    public void setEast(Tile east) {
        this.east = east;
    }

    public void setSouth(Tile south) {
        this.south = south;
    }

    public void setWest(Tile west) {
        this.west = west;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tile getNorth() {
        return north;
    }

    public Tile getEast() {
        return east;
    }

    public Tile getSouth() {
        return south;
    }

    public Tile getWest() {
        return west;
    }

    public String getName() {
        if(name == null) {
            return "N/A";
        } else {
            return name;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }






}
