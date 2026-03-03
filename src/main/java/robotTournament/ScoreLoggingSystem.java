package robotTournament;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ScoreLoggingSystem implements ScoreObserver
{

	private String fileName;
	
	public ScoreLoggingSystem(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void updateScore(String score)
	{
		try {
            Path path = Paths.get(fileName);
            Files.writeString(path, score + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
		
	}

}
