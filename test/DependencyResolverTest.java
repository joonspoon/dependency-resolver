import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Test;

public class DependencyResolverTest extends TestCase {

	String testInput = "['KittenService: CamelCaser', 'CamelCaser: ']";
	String testInput2 = "['KittenService: CamelCaser', 'CamelCaser: ', 'DogeParser: KittenService']";
	
	@Test
	public void testRemoveBracketsFromInputIfPresent() throws Exception {
		InputProcessor ip = new InputProcessor(testInput);
		ip.process();
		assertEquals("'KittenService: CamelCaser', 'CamelCaser: '", ip.getInput());
	}

	@Test
	public void testProcessInput() throws Exception {
		String testInput = "'KittenService: CamelCaser', 'CamelCaser: '";
		assertEquals("'KittenService: CamelCaser'", new InputProcessor(testInput).tokenizeInput().get(0));
		assertEquals(" 'CamelCaser: '", new InputProcessor(testInput).tokenizeInput().get(1));
	}

	@Test
	public void testPackageConstructor() throws Exception {
		Package myPackage = new Package("Kitteh Service", "CamelCaser");
		assertEquals("CamelCaser", myPackage.getDependency());
		assertEquals(false, myPackage.canBeInstalled());
	}

	@Test
	public void testObjectificiation() throws Exception {
		Package firstPackage = PackageFactory.createPackage("'KittenService: CamelCaser'");
		assertEquals("KittenService", firstPackage.getName());
		assertEquals("CamelCaser", firstPackage.getDependency());
		assertEquals(false, firstPackage.canBeInstalled());

		Package secondPackage = PackageFactory.createPackage(" 'CamelCaser: '");
		assertEquals("CamelCaser", secondPackage.getName());
		assertEquals("", secondPackage.getDependency());
		assertEquals(true, secondPackage.canBeInstalled());
	}
	
	@Test
	public void testInvalidInputFailsGracefully() throws Exception {
		
		try {
			Package invalidPackage = PackageFactory.createPackage("Kitteh Kitteh");
			fail();
		} catch (InvalidPackageException e) {
			assertTrue(e.getMessage().contains("Invalid"));
		}
		
	}
	
	@Test
	public void testHashmapOfPackages() throws Exception {
		InputProcessor ip = new InputProcessor(testInput);
		ip.organizeInputIntoHashmap();
		Package kittenPackage = ip.getPackage("KittenService");
		Package camelPackage = ip.getPackage("CamelCaser");
		
		assertEquals("KittenService", kittenPackage.getName());
		assertEquals("CamelCaser", camelPackage.getName());
	}
	
	@Test
	public void testPackagesAreConnectedViaDependency() throws Exception {
		InputProcessor ip = new InputProcessor(testInput2);
		ip.organizeInputIntoHashmap();
		ip.connectPackagesViaDependency();
		
		Package kittenPackage = ip.getPackage("KittenService");
		Package camelPackage = ip.getPackage("CamelCaser");
		Package dogePackage = ip.getPackage("DogeParser");
		assertEquals(kittenPackage, camelPackage.getDependent());
		assertEquals(dogePackage, kittenPackage.getDependent());
		assertEquals(null, dogePackage.getDependent());
	}
	
	@Test
	public void testOrderPackagesBasedOnDependencies() throws Exception {
		DependencyResolver dr = new DependencyResolver();
		dr.resolve(testInput);
		dr.parsePackagesForInstallability();
		assertEquals("CamelCaser", dr.getInstalledPackages().get(0).getName());
		assertEquals("KittenService", dr.getInstalledPackages().get(1).getName());
		
		DependencyResolver dr2 = new DependencyResolver();
		dr2.resolve("['DogeParser: KittenService', 'KittenService: CamelCaser', 'CamelCaser: ']");
		dr2.parsePackagesForInstallability();
		dr2.parsePackagesForInstallability();
		assertEquals("CamelCaser", dr2.getInstalledPackages().get(0).getName());
		assertEquals("KittenService", dr2.getInstalledPackages().get(1).getName());
		assertEquals("DogeParser", dr2.getInstalledPackages().get(2).getName());
	}
	
	@Test
	public void testGetInstallationOrderInSpecifiedFormat() throws Exception {
		DependencyResolver dr = new DependencyResolver();
		dr.resolve(this.testInput);
		assertEquals("'CamelCaser, KittenService'", dr.getInstallationOrder());
	}

}
