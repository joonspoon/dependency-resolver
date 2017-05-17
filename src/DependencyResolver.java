import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DependencyResolver {

	private List<Package> installedPackages = new ArrayList<Package>();
	private HashMap<String, Package> organizedPackages;
	
	protected void parsePackagesForInstallability() {
		for (Package aPackage : organizedPackages.values()) {
			if (aPackage.canBeInstalled() && isNotAlreadyInstalled(aPackage)) {
				installedPackages.add(aPackage);
				aPackage.makeDependentsInstallable();
			}
		}
	}

	private boolean isNotAlreadyInstalled(Package aPackage) {
		return !installedPackages.contains(aPackage);
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

	public void resolve(String input) throws InvalidPackageException, CyclicDependencyException {
		InputProcessor inputProcessor = new InputProcessor(input);
		this.organizedPackages = inputProcessor.process();
		
		int numberOfNodesToParse = organizedPackages.size();
		System.out.println(numberOfNodesToParse);
		for (int i = 0; i < numberOfNodesToParse; i++) {
			parsePackagesForInstallability();
		}
		
		/* If all the packages could not be installed, there must be a cyclic dependency. */
		if(installedPackages.size() < organizedPackages.size())	
			throw new CyclicDependencyException("Unable to resolve packages due to cyclic dependency.");
	}

}
