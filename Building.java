import java.util.ArrayList;

public class Building {

    private ArrayList<Floor> list;
    private int buildingNum;

    public Building() {

    }

    public Building(ArrayList<Floor> list, int buildingNum) {
        this.list = list;
        this.buildingNum = buildingNum;
    }

    public ArrayList<Floor> getList() {
        return list;
    }

    public void setList(ArrayList<Floor> list) {
        this.list = list;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public void setNumberOfFloors(int num) {
        list = new ArrayList<Floor>(num);
    }

    public void addFloor(Floor f) {
        list.add(f);
    }
}
