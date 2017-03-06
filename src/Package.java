public class Package {
	String name, dependency;

	public String getDependency() {
		return dependency;
	}

	public Package(String name, String dependecy) {
		super();
		this.name = name;
		this.dependency = dependecy;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Name: " + this.name + " Dependency: " + this.dependency;
	}
}
