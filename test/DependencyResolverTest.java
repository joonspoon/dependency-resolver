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
	
	
}
