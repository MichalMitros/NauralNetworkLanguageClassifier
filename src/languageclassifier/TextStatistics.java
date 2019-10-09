package languageclassifier;

public class TextStatistics {
	
	public static double[] getLettersStatistics(String text) {
		
		int numberOfLettersInAlphabet = getNumberOfLettersInAlphabet();
		double[] lettersStatistics = new double[numberOfLettersInAlphabet];
		
		text = text.toLowerCase();
		text = TextStatistics.deleteUnnecessaryCharacters(text);
		
		for(int i=0; i<text.length(); i++) {
			int index = (int)text.charAt(i) - 'a';
			lettersStatistics[index]++;
		}
		
		for(int i=0; i<numberOfLettersInAlphabet; i++) {
			lettersStatistics[i] /= text.length();
		}
		
		return lettersStatistics;
	}
	
	public static double[] getLettersProportions(String text) {
		
		int numberOfLettersInAlphabet = getNumberOfLettersInAlphabet();
		double[] lettersStatistics = new double[numberOfLettersInAlphabet];
		
		text = text.toLowerCase();
		text = TextStatistics.deleteUnnecessaryCharacters(text);
		
		for(int i=0; i<text.length(); i++) {
			int index = (int)text.charAt(i) - 'a';
			lettersStatistics[index]++;
		}
		
		double maxValue = -1;
		for(int i=0; i<numberOfLettersInAlphabet; i++) {
			if(lettersStatistics[i] > maxValue) {
				maxValue = lettersStatistics[i];
			}
		}
		
		for(int i=0; i<numberOfLettersInAlphabet; i++) {
			lettersStatistics[i] /= maxValue;
		}
		
		return lettersStatistics;
	}
	
	public static String deleteUnnecessaryCharacters(String text) {
		
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<text.length(); i++) {
			char character = text.charAt(i);
			if(character >= 'a' && character <= 'z') {
				result.append(character);
			}
		}
		
		return result.toString();
	}
	
	public static int getNumberOfLettersInAlphabet() {
		return (int)('z' - 'a' + 1);
	}

}
