
public class PackageFactory {

	public static Package createPackage(String rawString) throws InvalidPackageException {
		if (!rawString.contains(":"))
			throw new InvalidPackageException("Invalid package declaration: must contain semi-colon.");
		rawString = rawString.replaceAll("'", ""); 		// remove single quotes that surround package declaration
		String[] packageDetails = rawString.split(":"); // package name and dependency are separated by a semi-colon
		String packageName = packageDetails[0].trim();
		String dependency = packageDetails[1].trim();
		boolean canBeInstalled = dependency.isEmpty();
		return new Package(packageName, dependency, canBeInstalled);
	}

}
