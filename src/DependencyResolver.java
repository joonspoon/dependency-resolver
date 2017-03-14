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
				aPackage.makeDependentInstallable();
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

	public void resolve(String input) throws InvalidPackageException {
		InputProcessor inputProcessor = new InputProcessor(input);
		this.organizedPackages = inputProcessor.process();
		
		parsePackagesForInstallability();
		parsePackagesForInstallability(); 
	}

}
