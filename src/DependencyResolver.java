import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DependencyResolver {

	private HashMap<String, Package> organizedPackages;

	public static List<String> tokenize(String input) {
		return new ArrayList<String>(Arrays.asList(input.split(",")));
	}

	public static String removeBrackets(String testInput) {
		return testInput.replace("[", "").replace("]", "");
	}

	public static String removeWhiteSpace(String string) {
		return string.trim();
	}

	public static Package createPackage(String rawString) throws InvalidPackageException {
		if (!rawString.contains(":"))
			throw new InvalidPackageException("Invalid package declaration: must contain semi-colon.");
		rawString = rawString.replaceAll("'", ""); // remove single quotes that surround package declaration
		String[] packageDetails = rawString.split(":"); // package name and dependency are separated by a semi-colon
		String packageName = packageDetails[0].trim();
		String dependency = packageDetails[1].trim();
		boolean canBeInstalled = dependency.isEmpty();
		return new Package(packageName, dependency, canBeInstalled);
	}

	protected HashMap<String, Package> organizeInputIntoHashmap(String input) throws InvalidPackageException {
		organizedPackages = new HashMap<String, Package>();

		input = DependencyResolver.removeBrackets(input);
		List<String> rawPackages = DependencyResolver.tokenize(input);

		for (String string : rawPackages) {
			Package aPackage = DependencyResolver.createPackage(string);
			organizedPackages.put(aPackage.getName(), aPackage);
		}

		return organizedPackages;
	}

	public void connectPackagesViaDependency() {
		for (Package aPackage : organizedPackages.values()) {
			if (!aPackage.getDependency().isEmpty())
				organizedPackages.get(aPackage.getDependency()).setDependent(aPackage);
		}
	}

	protected Package getPackage(String key) {
		return organizedPackages.get(key);
	}

	public String getOrderOfInstallation(String testInput) {
		//List<Package> installedPackages = new ArrayList<Package>();
		for (Package aPackage : organizedPackages.values()) {
			if(aPackage.canBeInstalled()){
				//install it
				//set it's dependents to installable
			}
		}
		return "";
	}

}
