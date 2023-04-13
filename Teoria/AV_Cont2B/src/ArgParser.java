import com.beust.jcommander.Parameter;

public class ArgParser {
	
	//////////////////////////////////////
	// DEFINITION OF ACCEPTED ARGS
	//////////////////////////////////////
	
	@Parameter(names = { "--input", "-i" }, 
			description = "Input file")
	private String input;
	
	
	////////////////////////////////////
	// ARGS VALUES GETTER METHODS	
	////////////////////////////////////
	
	public String getInput() {
		return input;
	}
	
}