import java.util.Stack;

public class TesterClass {

    public static Building yudof = new Building();
    public static Floor floor3 = new Floor();
    public static Section a = new Section();

    public static String decodeInt(int x) {
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

    public static void doStuff() {
        int size = gui2.dimensionOfGrid();
        a.createLayout(size,size);
        a.printLayout();
        floor3.setNumberOfSections(1);
        floor3.addSection(a);
        yudof.setNumberOfFloors(1);
        yudof.addFloor(floor3);
    }

    public static void main(String[] args) {

        gui2.createGUI();

        /*
        Tile one = new Tile("one",1);
        Tile two = new Tile("two",2);
        Tile three = new Tile("three",3);
        Tile four = new Tile("four",4);
        Tile five = new Tile("five",5);
        Tile six = new Tile("six",6);
        Tile seven = new Tile("seven",7);
        Tile eight = new Tile("eight",8);
        Tile nine = new Tile("nine",9);

        Tile[] thing = {one,two,three,four,five,six,seven,eight};
        Section level3 = new Section(thing);

//        level3.setNorthSouth(one,two);
//        level3.setNorthSouth(two,three);
//        level3.setEastWest(three,four);
//        level3.setEastWest(four,five);
//        level3.setEastWest(six,three);
//        level3.setEastWest(seven,six);

        level3.setEastWest(one,two);
        level3.setEastWest(two,three);
        level3.setNorthSouth(three,four);
        level3.setNorthSouth(four,five);
        level3.setEastWest(six,five);
        level3.setEastWest(seven,six);
        level3.setNorthSouth(eight,seven);
        level3.setNorthSouth(one,eight);
        level3.setNorthSouth(nine,six);
        level3.setNorthSouth(two,nine);
        level3.setEastWest(eight,nine);
        level3.setEastWest(nine,four);



        for(Tile x : thing) {
            System.out.println("\n" + x.getName() + " : " + x.getId());
            if(x.getNorth() != null)
                System.out.println("NORTH: " + x.getNorth().getName());
            if(x.getSouth() != null)
                System.out.println("SOUTH: " + x.getSouth().getName());
            if(x.getEast() != null)
                System.out.println("EAST: " + x.getEast().getName());
            if(x.getWest() != null)
                System.out.println("WEST: " + x.getWest().getName());
        }

        System.out.println(level3.findTileByName("five").getId() + "\n\n");

        Stack<Integer> x = level3.findPath(six,one);
        while(!x.empty()) {
            int a = x.pop();
            System.out.println(" : " + a);
            System.out.println(decodeInt(a));
        }*/



    }

}
