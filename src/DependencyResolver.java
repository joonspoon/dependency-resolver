import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DependencyResolver {

	public static List<String> tokenize(String input) {
		return new ArrayList<String>(Arrays.asList(input.split(",")));
	}

	public static String removeBrackets(String testInput) {
		return testInput.replace("[", "").replace("]", "");
	}

	public static String removeWhiteSpace(String string) {
		return string.trim();
	}

	public static String processInput(String input) {
		return removeWhiteSpace(removeBrackets(input));
	}

}
