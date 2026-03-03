package robotTournament;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MoveLoggingSystem implements MoveObserver
{

	private String fileName;
	
	public MoveLoggingSystem(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void updateMove(String move)
	{
		try {
            Path path = Paths.get(fileName);
            Files.writeString(path, move + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
	}

}
