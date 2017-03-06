public class Package {
	String name, dependency;
	Package dependent = null;


	public Package(String name, String dependecy) {
		super();
		this.name = name;
		this.dependency = dependecy;
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
	
	@Override
	public String toString() {
		return "Name: " + this.name + " Dependency: " + this.dependency;
	}

}
