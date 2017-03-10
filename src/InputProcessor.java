import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InputProcessor {

	private HashMap<String, Package> organizedPackages = new HashMap<String, Package>();
	private String input;

	protected InputProcessor(String input) {
		this.input = input;
	}

	protected HashMap<String, Package> process() throws InvalidPackageException {
		organizeInputIntoHashmap();
		connectPackagesViaDependency();
		return this.organizedPackages;
	}

	protected List<String> tokenizeInput() {
		return new ArrayList<String>(Arrays.asList(input.split(",")));
	}

	protected void removeBracketsFromInput() {
		this.input = input.replace("[", "").replace("]", "");
	}

	protected String getInput() {
		return input;
	}

	protected void organizeInputIntoHashmap() throws InvalidPackageException {

		removeBracketsFromInput();
		List<String> rawPackages = tokenizeInput();

		for (String string : rawPackages) {
			Package aPackage = PackageFactory.createPackage(string);
			this.organizedPackages.put(aPackage.getName(), aPackage);
		}

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
