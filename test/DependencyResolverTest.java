import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

public class DependencyResolverTest extends TestCase {

	@Test
	public void testRemoveBracketsFromInputIfPresent() throws Exception {
		String testInput = "['KittenService: CamelCaser', 'CamelCaser: ']";
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

}
