import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DependencyResolver {

	private List<Package> installedPackages = new ArrayList<Package>();
	private HashMap<String, Package> organizedPackages;

	protected static List<String> tokenize(String input) {
		return new ArrayList<String>(Arrays.asList(input.split(",")));
	}

	protected static String removeBrackets(String testInput) {
		return testInput.replace("[", "").replace("]", "");
	}

	protected static String removeWhiteSpace(String string) {
		return string.trim();
	}

	protected static Package createPackage(String rawString) throws InvalidPackageException {
		if (!rawString.contains(":"))
			throw new InvalidPackageException("Invalid package declaration: must contain semi-colon.");
		rawString = rawString.replaceAll("'", ""); 		// remove single quotes that surround package declaration
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

	protected void connectPackagesViaDependency() {
		for (Package aPackage : organizedPackages.values()) {
			if (!aPackage.getDependency().isEmpty())
				organizedPackages.get(aPackage.getDependency()).setDependent(aPackage);
		}
	}

	protected Package getPackage(String key) {
		return organizedPackages.get(key);
	}

	protected void parsePackagesForInstallability() {
		for (Package aPackage : organizedPackages.values()) {
			if (aPackage.canBeInstalled()) {
				installedPackages.add(aPackage);
				aPackage.makeDependentInstallable();
			}
		}
	}

	protected List<Package> getInstalledPackages() {
		return this.installedPackages;
	}

	public String getOriginalPackageList() {
		StringBuilder packageList = new StringBuilder();
		for (Package aPackage : organizedPackages.values()) {
			packageList.append(aPackage);
			packageList.append("\n");
		}
		return packageList.toString();
	}

	public String getInstallationOrder() {
		StringBuilder packageList = new StringBuilder();
		packageList.append("'");
		
		for (Package aPackage : this.installedPackages) {
			packageList.append(aPackage.getName() + ", ");
		}
		
		packageList.deleteCharAt(packageList.length() - 1); // remove the last comma
		packageList.deleteCharAt(packageList.length() - 1); // remove the last space
		packageList.append("'");
		return packageList.toString();
	}

	public void resolve(String input) throws InvalidPackageException {
		organizeInputIntoHashmap(input);
		connectPackagesViaDependency();
		parsePackagesForInstallability();
	}

}
