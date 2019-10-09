package languageclassifier;

public class Text {
	
	private String text;
	private String correctLanguage;
	
	public Text(String text, String correctLanguage) {
		this.text = text;
		this.correctLanguage = correctLanguage;
	}
	
	public double[] getLettersStatistics() {
		return TextStatistics.getLettersStatistics(text);
	}
	
	public String getLanguage() {
		return correctLanguage;
	}
}
