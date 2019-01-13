import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileHandler {

    static JTree fileSystem;
    static final String ROOT_PATH = "H:\\Desktop"; //\\Map_Program";

    public static String fileSetup(){
        File test = FileSystemView.getFileSystemView().getDefaultDirectory();
        String startDir = test.getAbsolutePath();
        File dir = new File(startDir + "\\Map_Program");
        System.out.println("startdir: " + startDir);
        return startDir;
    }

    public static void main(String[] args) {
        fileSetup();
        System.out.println("ROOT: " + ROOT_PATH);
    }

}
