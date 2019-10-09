package languageclassifier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class Main extends JFrame {
	
	private static final long serialVersionUID = -1643805129563644273L;
	private JPanel trainPanel;
	private TestFrame testPanel;
	private Color backgroundColor;
	
	private PerceptronsLayer classifier;
	private ArrayList<Text> trainingSet = null;
	private ArrayList<Text> testSet = null;
	private String[] languages;
	
	private JTextField maxErrorField;
	private JTextField learningRateField;
	private JTextField maxLearningStepsField;
	
	public Main() {
		setSize(400, 300);
		setTitle("Language Classifier");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 1));
		
		backgroundColor = new Color(32, 32, 32);
		setBackground(backgroundColor);
		
		languages = DataLoader.loadLanguages("data/trainingData");
		
		try {
			trainingSet = DataLoader.loadData("data/trainingData");
			testSet = DataLoader.loadData("data/testData");
		} catch(IOException e) {
			System.exit(1);
		}
		
		trainPanel = new JPanel();
		prepareTrainPanel();
		add(trainPanel);
		
		centreWindow();
		
		setVisible(true);
	}
	
	private void prepareTrainPanel() {
		
		trainPanel.setBackground(backgroundColor);
		GridLayout layout = new GridLayout(0, 2);
		layout.setVgap(30);
		layout.setHgap(20);
		trainPanel.setLayout(layout);
		trainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
		
		JLabel learningRateLabel = new JLabel("LearningRate: ", JLabel.RIGHT);
		learningRateLabel.setForeground(Color.white);
		trainPanel.add(learningRateLabel);
		
		learningRateField = new JTextField();
		learningRateField.setBackground(new Color(64, 64, 64));
		learningRateField.setForeground(Color.WHITE);
		learningRateField.setText("0.05");
		trainPanel.add(learningRateField);
		
		JLabel maxErrorLabel = new JLabel("Maximal error: ", JLabel.RIGHT);
		maxErrorLabel.setForeground(Color.white);
		trainPanel.add(maxErrorLabel);
		
		maxErrorField = new JTextField();
		maxErrorField.setBackground(new Color(64, 64, 64));
		maxErrorField.setForeground(Color.WHITE);
		maxErrorField.setText("0.1");
		trainPanel.add(maxErrorField);
		
		JLabel maxLearningStepsLabel = new JLabel("Maximal learning steps: ", JLabel.RIGHT);
		maxLearningStepsLabel.setForeground(Color.white);
		trainPanel.add(maxLearningStepsLabel);
		
		maxLearningStepsField = new JTextField();
		maxLearningStepsField.setBackground(new Color(64, 64, 64));
		maxLearningStepsField.setForeground(Color.WHITE);
		maxLearningStepsField.setText("20000");
		trainPanel.add(maxLearningStepsField);
		
		JLabel spacer = new JLabel(" ");
		trainPanel.add(spacer);
		
		JButton trainButton = new JButton("Train");
		trainButton.setBackground(new Color(64, 64, 64));
		trainButton.setForeground(Color.WHITE);
		trainButton.addActionListener((e) -> {
			try {
				double learningRate = Double.parseDouble(learningRateField.getText());
				double maxError = Double.parseDouble(maxErrorField.getText());
				int maxLearningSteps = Integer.parseInt(maxLearningStepsField.getText());
				
				trainClassifier(learningRate, maxError, maxLearningSteps);
				
//				classifier.normalize();
				
				showTestFrame();
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Wrong input.\nRetype parameters.");
				learningRateField.setText("0.05");
				maxErrorField.setText("0.1");
				maxLearningStepsField.setText("20000");
			}
		});
		trainPanel.add(trainButton);
	}
	
	private void trainClassifier(double learningRate, double maxError, int maxLearningSteps) {
		int numberOfLanguages = languages.length;
		classifier = new PerceptronsLayer(TextStatistics.getNumberOfLettersInAlphabet(), numberOfLanguages, learningRate);
		
		int n = 0;
		double error = 1;
		while(error >= maxError && n <= maxLearningSteps) {
			Collections.shuffle(trainingSet);
			for(Text t : trainingSet) {
				double[] target = new double[numberOfLanguages];
				for(int j=0; j<numberOfLanguages; j++) {
					if(t.getLanguage().equals(languages[j])) {
						target[j] = 1;
					}
				}
				classifier.train(t.getLettersStatistics(), target);
			}
			
			double[] errors = new double[testSet.size()];
			for(int i=0; i<testSet.size(); i++) {
				double[] target = new double[numberOfLanguages];
				for(int j=0; j<numberOfLanguages; j++) {
					if(testSet.get(i).getLanguage().equals(languages[j])) {
						target[j] = 1;
					}
				}
				errors[i] = classifier.getError(testSet.get(i).getLettersStatistics(), target);
				
				double maxOfErrors = 0;
				for(int j=0; j<errors.length; j++) {
					if(errors[i] > maxOfErrors) {
						maxOfErrors = errors[j];
					}
				}
				error = maxOfErrors;
			}
			
			n++;
		}
	}
	
	private void showTestFrame() {
		testPanel = new TestFrame(classifier, languages, getLocation().x - 200, getLocation().y - 150);
		setVisible(false);
		testPanel.setVisible(true);
	}
	
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
	}

	public static void main(String[] args) {
		new Main();
	}

}
