package first_lesson.home_work;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{

	/** Test 1 */
	@Test
	public void testGetLocalNumber()
	{
		Assert.assertEquals("Local number is not 14", 14, getLocalNumber());
	}

	/** Test 2 */
	@Test
	public void testGetClassNumber()
	{
		Assert.assertTrue("Class number no more than 45", getClassNumber() > 45);
	}

	/** Test 3 */
	@Test
	public void testGetClassString()
	{
		Assert.assertTrue("There are no matching substrings in the class string",
				getClassString().contains("hello") | getClassString().contains("Hello"));
	}

}
