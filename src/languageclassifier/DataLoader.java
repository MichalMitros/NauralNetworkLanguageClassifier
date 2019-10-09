package languageclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class DataLoader {
	
	public static ArrayList<Text> loadData(String directory) throws IOException{
		
		Path startPath = Paths.get(directory);
		ArrayList<Text> data = new ArrayList<>();
		
		Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
			
			private String language = "";
			
			@Override
			public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attr) throws IOException {
				
				language = file.getFileName().toString();
				
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
				
				BufferedReader input = new BufferedReader(new FileReader(file.toFile()));
				StringBuilder text = new StringBuilder();
				String line;
				
				while((line = input.readLine()) != null) {
					text.append(line);
				}
				
				input.close();
				
				data.add(new Text(TextStatistics.deleteUnnecessaryCharacters(text.toString()), language));
				
				return FileVisitResult.CONTINUE;
			}
		});
		
		return data;
	}
	
	public static String[] loadLanguages(String directory) {
		String[] languages;
		File file = new File(directory);
		
		File[] files = file.listFiles(new FileFilter() {
		    @Override
		    public boolean accept(File f) {
		        return f.isDirectory();
		    }
		});
		
		languages = new String[files.length];
		
		files = file.listFiles(new FileFilter() {
			
			private int n = 0;
			
		    @Override
		    public boolean accept(File f) {
		    	if(f.isDirectory()) {
		    		languages[n] = f.getName();
		    		n++;
		    	}
		        return false;
		    }
		});
		
		return languages;
	}

}
