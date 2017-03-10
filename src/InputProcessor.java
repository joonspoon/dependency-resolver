import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InputProcessor {

	private HashMap<String, Package> organizedPackages;
	private String input;

	protected InputProcessor(String input) {
		this.input = input;
	}

	protected HashMap<String, Package> process() throws InvalidPackageException {
		organizeInputIntoHashmap();
		connectPackagesViaDependency();
		return this.organizedPackages;
	}

	protected static List<String> tokenize(String input) {
		return new ArrayList<String>(Arrays.asList(input.split(",")));
	}

	protected static String removeBrackets(String testInput) {
		return testInput.replace("[", "").replace("]", "");
	}

	protected static String removeWhiteSpace(String string) {
		return string.trim();
	}

	protected HashMap<String, Package> organizeInputIntoHashmap() throws InvalidPackageException {
		organizedPackages = new HashMap<String, Package>();

		input = InputProcessor.removeBrackets(input);
		List<String> rawPackages = InputProcessor.tokenize(input);

		for (String string : rawPackages) {
			Package aPackage = PackageFactory.createPackage(string);
			organizedPackages.put(aPackage.getName(), aPackage);
		}

		return organizedPackages;
	}

	protected void connectPackagesViaDependency() {
		for (Package aPackage : organizedPackages.values()) {
			if (!aPackage.getDependency().isEmpty())
				organizedPackages.get(aPackage.getDependency()).setDependent(aPackage);
		}
	}

	protected Package getPackage(String key) {
		return organizedPackages.get(key);
	}

}
