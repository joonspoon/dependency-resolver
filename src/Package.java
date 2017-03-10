public class Package {
	private String name, dependency;
	private Package dependent = null;
	private boolean canBeInstalled;

	public Package(String name, String dependecy, boolean canBeInstalled) {
		super();
		this.name = name;
		this.dependency = dependecy;
		this.canBeInstalled = canBeInstalled;
	}

	public Package(String name, String dependency) {
		this(name, dependency, false);
	}

	public String getName() {
		return name;
	}

	public Package getDependent() {
		return dependent;
	}
	
	public void setDependent(Package dependant) {
		this.dependent = dependant;
	}
	
	public String getDependency() {
		return dependency;
	}
	
	public boolean canBeInstalled() {
		return this.canBeInstalled;
	}
	
	@Override
	public String toString() {
		String dependencyIfExists = this.dependency.isEmpty() ? "" : " depends on " + this.dependency;
		return this.name + dependencyIfExists;
	}

	public void makeDependentInstallable() {
		if(this.dependent != null)
			this.dependent.canBeInstalled = true;
	}

}
