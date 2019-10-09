package languageclassifier;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class TextStatisticsDrawer extends JComponent {
	
	private static final long serialVersionUID = -4600567608775859231L;
	private double[] lettersStatistics;
	
	public void setStatistics(double[] lettersStatistics) {
		this.lettersStatistics = lettersStatistics;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(32, 32, 32));
		g.fillRect(0,  0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		int hGap = getWidth()/TextStatistics.getNumberOfLettersInAlphabet();
		for(int i=0; i<TextStatistics.getNumberOfLettersInAlphabet(); i++) {
			g.drawString("" + (char)('a' + i), i*hGap+3, getHeight()-5);
		}
		
		if(lettersStatistics != null) {
			for(int i=0; i<TextStatistics.getNumberOfLettersInAlphabet(); i++) {
				int columnHeight = (int)((getHeight()-25) * lettersStatistics[i]);
				g.fillRect(i*hGap, getHeight()-20-columnHeight, hGap, columnHeight);
			}
		}
		
	}

}
