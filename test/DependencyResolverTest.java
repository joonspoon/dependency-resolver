import static org.junit.Assert.*;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Test;

public class DependencyResolverTest extends TestCase {

	String testInput = "['KittenService: CamelCaser', 'CamelCaser: ']";
	String testInput2 = "['KittenService: CamelCaser', 'CamelCaser: ', 'DogeParser: KittenService']";
	
	@Test
	public void testRemoveBracketsFromInputIfPresent() throws Exception {
		assertEquals("'KittenService: CamelCaser', 'CamelCaser: '", DependencyResolver.removeBrackets(testInput));
	}

	@Test
	public void testProcessInput() throws Exception {
		String testInput = "'KittenService: CamelCaser', 'CamelCaser: '";
		assertEquals("'KittenService: CamelCaser'", DependencyResolver.tokenize(testInput).get(0));
		assertEquals(" 'CamelCaser: '", DependencyResolver.tokenize(testInput).get(1));
	}

	@Test
	public void testPackageConstructor() throws Exception {
		Package myPackage = new Package("Kitteh Service", "CamelCaser");
		assertEquals("CamelCaser", myPackage.getDependency());
	}

	@Test
	public void testObjectificiation() throws Exception {
		Package firstPackage = DependencyResolver.createPackage("'KittenService: CamelCaser'");
		assertEquals("KittenService", firstPackage.getName());
		assertEquals("CamelCaser", firstPackage.getDependency());

		Package secondPackage = DependencyResolver.createPackage(" 'CamelCaser: '");
		assertEquals("CamelCaser", secondPackage.getName());
		assertEquals("", secondPackage.getDependency());
	}
	
	@Test
	public void testInvalidInputFailsGracefully() throws Exception {
		
		try {
			Package invalidPackage = DependencyResolver.createPackage("Kitteh Kitteh");
			fail();
		} catch (InvalidPackageException e) {
			assertTrue(e.getMessage().contains("Invalid"));
		}
		
	}
	
	@Test
	public void testHashmapOfPackages() throws Exception {
		HashMap<String, Package> packageMap = new DependencyResolver().organizeInputIntoHashmap(testInput);
		Package kittenPackage = packageMap.get("KittenService");
		Package camelPackage = packageMap.get("CamelCaser");
		
		assertEquals("KittenService", kittenPackage.getName());
		assertEquals("CamelCaser", camelPackage.getName());
	}
	
	@Test
	public void testPackagesAreConnectedViaDependency() throws Exception {
		DependencyResolver dr = new DependencyResolver();
		dr.organizeInputIntoHashmap(testInput2);
		dr.connectPackagesViaDependency();
		
		Package kittenPackage = dr.getPackage("KittenService");
		Package camelPackage = dr.getPackage("CamelCaser");
		Package dogePackage = dr.getPackage("DogeParser");
		assertEquals(kittenPackage, camelPackage.getDependent());
		assertEquals(dogePackage, kittenPackage.getDependent());
		assertEquals(null, dogePackage.getDependent());
	}
	
//	@Test
//	public void testOrderPackagesBasedOnDependencies() throws Exception {
//		assertEquals("CamelCaser, KittenService", DependencyResolver.getOrderOfInstallation(testInput));
//	}

}
