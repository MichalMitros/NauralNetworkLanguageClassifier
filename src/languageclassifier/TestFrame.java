package languageclassifier;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestFrame extends JFrame {
	
	// GUI ELEMENTS
	private static final long serialVersionUID = -4341987521578617417L;
	private JTextArea textArea;
	private JLabel resultLabel;
	private JLabel resultConfidenceLabel;
	private JLabel secondResultLabel;
	private JLabel secondResultConfidenceLabel;
	private TextStatisticsDrawer statisticsDrawer;

	private String[] languages = new String[1];
	private PerceptronsLayer classifier;
	
	public TestFrame(PerceptronsLayer classifier, String[] languages, int x, int y) {
		
		setTitle("Language Classifier");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.classifier = classifier;
		this.languages = languages;
		this.setMinimumSize(new Dimension(800, 600));
		this.setLocation(x, y);
		
		Color backgroundColor = new Color(32, 32, 32);
		
		this.setBackground(backgroundColor);
		this.setForeground(Color.WHITE);
		
		Font font = new Font("serif", Font.PLAIN, 28);
		
		JPanel horizontalPanel = new JPanel();
		JPanel verticalPanel = new JPanel();
		
		horizontalPanel.setLayout(new GridLayout(1,2));
		verticalPanel.setLayout(new GridLayout(3, 1));
		
		horizontalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		horizontalPanel.setBackground(backgroundColor);
		horizontalPanel.setForeground(Color.WHITE);
		verticalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		verticalPanel.setBackground(backgroundColor);
		verticalPanel.setForeground(Color.WHITE);
		
		statisticsDrawer = new TextStatisticsDrawer();
		verticalPanel.add(statisticsDrawer);
		
		textArea = new JTextArea();
		textArea.setBackground(new Color(64, 64, 64));
		textArea.setForeground(Color.WHITE);
		JScrollPane sp = new JScrollPane(textArea); 
		textArea.setLineWrap(true);
		textArea.addKeyListener(new KeyListener() {

		    @Override
		    public void keyTyped(KeyEvent arg0) {
		    	showResults();
		    }

		    @Override
		    public void keyReleased(KeyEvent arg0) {
		    	showResults();
		    }

		    @Override
		    public void keyPressed(KeyEvent arg0) {
		    }
		});
		horizontalPanel.add(sp);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(2, 1));
		resultPanel.setBackground(backgroundColor);
		
		resultLabel = new JLabel("", JLabel.CENTER);
		resultLabel.setFont(font);
		resultLabel.setForeground(Color.WHITE);
		resultPanel.add(resultLabel);
		
		resultConfidenceLabel = new JLabel("", JLabel.CENTER);
		resultConfidenceLabel.setForeground(Color.WHITE);
		resultPanel.add(resultConfidenceLabel);
		
		verticalPanel.add(resultPanel);
		
		JPanel secondResultPanel = new JPanel();
		secondResultPanel.setLayout(new GridLayout(2, 1));
		secondResultPanel.setBackground(backgroundColor);
		
		secondResultLabel = new JLabel("", JLabel.CENTER);
		secondResultLabel.setFont(font);
		secondResultLabel.setForeground(Color.LIGHT_GRAY);
		secondResultPanel.add(secondResultLabel);
		
		secondResultConfidenceLabel = new JLabel("", JLabel.CENTER);
		secondResultConfidenceLabel.setForeground(Color.LIGHT_GRAY);
		secondResultPanel.add(secondResultConfidenceLabel);
		
		verticalPanel.add(secondResultPanel);
		
		horizontalPanel.add(verticalPanel);
		this.add(horizontalPanel);
		
		setVisible(true);
		statisticsDrawer.repaint();
	}
	
	private void showResults() {
		statisticsDrawer.setStatistics(TextStatistics.getLettersProportions(textArea.getText()));
    	statisticsDrawer.repaint();
		if(textArea.getText().length() > 0) {
	    	double[] output = classifier.feedForward(TextStatistics.getLettersStatistics(textArea.getText()));
	    	resultLabel.setText(getLanguageNameFromResult(output));
	    	resultConfidenceLabel.setText("Confidence: " + getResultsConfidence(output));
	    	secondResultLabel.setText(getLanguageNameFromSecondResult(output));
	    	secondResultConfidenceLabel.setText("Confidence: " + getSecondResultsConfidence(output));
		} else {
			resultLabel.setText("");
	    	resultConfidenceLabel.setText("");
	    	secondResultLabel.setText("");
	    	secondResultConfidenceLabel.setText("");
		}
	}
	
	private String getLanguageNameFromResult(double[] resultsArray) {
		int indexOfMaxElement = 0;
		double maxValue = 0;
		for(int i=0; i<resultsArray.length; i++) {
			if(resultsArray[i] > maxValue) {
				maxValue = resultsArray[i];
				indexOfMaxElement = i;
			}
		}
		
		return languages[indexOfMaxElement];
	}
	
	private String getLanguageNameFromSecondResult(double[] resultsArray) {
		int indexOfSecondMaxElement = 0;
		double maxValue = 0;
		double secondMaxValue = 0;
		for(int i=0; i<resultsArray.length; i++) {
			if(resultsArray[i] > maxValue) {
				maxValue = resultsArray[i];
			} else if(resultsArray[i] > secondMaxValue) {
				indexOfSecondMaxElement = i;
				secondMaxValue = resultsArray[i];
			}
		}
		
		return languages[indexOfSecondMaxElement];
	}
	
	private String getResultsConfidence(double[] resultsArray) {
		double maxValue = 0;
		for(int i=0; i<resultsArray.length; i++) {
			if(resultsArray[i] > maxValue) {
				maxValue = resultsArray[i];
			}
		}
		maxValue *= 100;
		
		return String.format("%.0f%%", maxValue);
	}
	
	private String getSecondResultsConfidence(double[] resultsArray) {
		double maxValue = 0;
		double secondMaxValue = 0;
		for(int i=0; i<resultsArray.length; i++) {
			if(resultsArray[i] > maxValue) {
				maxValue = resultsArray[i];
			} else {
				if(resultsArray[i] > secondMaxValue) {
					secondMaxValue = resultsArray[i];
				}
			}
		}
		secondMaxValue *= 100;
		
		return String.format("%.0f%%", secondMaxValue);
	}

}
