import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TestFrame extends JFrame{
    public TestFrame() {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.append("name: "+JarTool.getJarName()+"\n");
        ta.append("dir: "+JarTool.getJarDir()+"\n");
        ta.append("path: "+JarTool.getJarPath()+"\n");
        add(ta);
        pack();
        setTitle("动态获取Jar路径信息");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new TestFrame();
    }
}  