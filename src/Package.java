import java.util.ArrayList;
import java.util.List;

public class Package {
	private String name, dependendsOn;
	private List<Package> dependents = new ArrayList<Package>();
	private boolean canBeInstalled;

	public Package(String name, String dependecy, boolean canBeInstalled) {
		super();
		this.name = name;
		this.dependendsOn = dependecy;
		this.canBeInstalled = canBeInstalled;
	}

	public Package(String name, String dependency) {
		this(name, dependency, false);
	}

	public String getName() {
		return name;
	}

	public List<Package> getDependents() {
		return dependents;
	}

	public void addDependent(Package newDependent) {
		this.dependents.add(newDependent);
	}

	public String getDependency() {
		return dependendsOn;
	}

	public boolean canBeInstalled() {
		return this.canBeInstalled;
	}

	@Override
	public String toString() {
		String dependencyIfExists = this.dependendsOn.isEmpty() ? "" : " depends on " + this.dependendsOn;
		return this.name + dependencyIfExists;
	}

	public void makeDependentsInstallable() {
		for (Package dependentPackage : dependents) {
			dependentPackage.canBeInstalled = true;
		}
	}

}
