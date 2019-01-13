import java.util.ArrayList;

public class Floor {

    private ArrayList<Section> list;
    private int building;
    private int floorNum;

    public Floor() {
        ArrayList<Section> list;
    }

    public Floor(ArrayList<Section> list, int buildling, int floorNum) {
        this.list = list;
        this.building = building;
        this.floorNum = floorNum;
    }

    public ArrayList<Section> getList() {
        return list;
    }

    public void setList(ArrayList<Section> list) {
        this.list = list;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public void setNumberOfSections(int num) {
        list = new ArrayList<Section>(num);
    }

    public void addSection(Section s) {
        list.add(s);
    }
}
