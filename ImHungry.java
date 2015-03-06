import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * This class contains the main method to create the GUI
 * @author Sung Yan Chao
 * CS338 Project
 */
public class ImHungry {
	
	public static void main(String[] args){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {

		}
		
		HungryFrame hungry = new HungryFrame();
		hungry.setSize(1500, 600);
		hungry.setVisible(true);
		hungry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
