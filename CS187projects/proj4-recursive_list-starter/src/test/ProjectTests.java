package test;

import static org.junit.Assert.*;

import app.*;
import org.junit.Before;
import org.junit.Test;


public class ProjectTests {

	private ListInterface<String> list;

	@Before
	public void setup() {
		list = new RecursiveList<String>();
	}


	@Test
	public void testInsertFirstIsEmptySizeAndGetFirst1() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		list.insertFirst("inserted");
		list.insertFirst("new");
		assertEquals(list.getFirst(), "new");
	}


	@Test
	public void testInsertFirstIsEmptySizeAndGetFirst3() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		list.insertFirst("hello");
		assertEquals("Inserted and then got an element at the first position", "hello", list.getFirst());
	}


	@Test
	public void testinsertAndindexOf() {
		list.insertFirst("inserted");
		list.insertLast("new");
		list.insertLast("stuff");
		list.insertFirst("and then more");
		System.out.println(list.size());
		list.insertAt(3, "foo");
		list.insertLast("last");
		assertEquals(list.indexOf("last") , 5);
	}

	@Test
	public void testremove() {
		list.insertFirst("inserted");
		list.insertLast("new");
		list.insertLast("stuff");
		list.remove("new");
		assertEquals(list.indexOf("new") , -1 );
	}

	@Test (expected = IndexOutOfBoundsException.class)
	public void testremoveAt1() {
		list.insertFirst("inserted");
		list.insertLast("new");
		list.insertLast("stuff");
		list.removeAt(0);
		list.removeAt(0);
		list.removeAt(0);
		list.removeAt(0);
		/*try {
			list.removeAt(0);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			//exception should be thrown here
		}*/
		//assertEquals(list.indexOf("new") , 0);
	}

	
	@Test
	public void testremoveLastsize1() {
		list.insertFirst("inserted");
		list.removeLast();
		assertEquals(list.isEmpty() , true );
	}

	@Test
	public void testremoveFirst() {
		list.insertFirst("inserted");
		list.removeFirst();
		assertEquals(list.isEmpty() , true );
	}

	@Test
	public void testremoveFirst2() {
		list.insertFirst("inserted");
		list.insertAt(0, "erase");
		list.insertAt(1, "answer");
		list.removeFirst();
		assertEquals( list.getFirst(), "answer");
	}

	@Test
	public void testremoveLast() {
		list.insertFirst("inserted");
		list.insertLast("new");
		list.insertLast("stuff");
		list.insertLast("and");
		list.removeLast();
		list.removeLast();
		list.removeLast();
		list.removeLast();
		assertEquals(list.getLast() , null );
	}

	@Test
	public void testremove2() {
		list.insertFirst("inserted");
		list.insertLast("new");
		list.insertLast("stuff");
		list.insertLast("and");
		list.insertLast("this");
		list.insertLast("and");
		list.insertLast("repeats exist");
		list.remove("and");
		assertEquals(list.indexOf("this") , 3 );
	}

	@Test
	public void testremoveNewHead() {
		list.insertFirst("inserted");
		list.insertLast("new");
		list.insertLast("stuff");
		list.insertLast("and");
		list.insertLast("this");
		list.insertLast("and");
		list.insertLast("repeats exist");
		list.remove("inserted");
		assertEquals(list.getFirst(), "new" );
	}

	@Test(timeout = 500, expected = IllegalStateException.class)
	public void testExceptionOnEmptyGetLast() {
		list.getLast();
	}

	@Test( expected = ItemNotFoundException.class)
	public void testItemNotFoundExceptionOnRemove() {
		list.insertFirst("hello");
		list.remove("there");
	}

	@Test
	public void testInsertsGetsRemovesSize() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		list.insertLast("Hello");
		list.insertLast("World!");
		list.insertAt(1, "There");

		assertEquals("Checking position 1.", "There", list.get(1));

		assertEquals("Size should be 3", 3, list.size());
		assertEquals("0th element should .equals \"Hello\"", "Hello", list.get(0));
		assertEquals("Last element should .equals \"World!\"", "World!", list.getLast());
		list.insertAt(0, "foo");
		list.insertAt(4, "bar");
		assertEquals("foo", list.get(0));
		assertEquals("bar", list.get(4));
		assertEquals("Size should be 5", 5, list.size());
		assertEquals("The third element should have been \"World!\"", "World!", list.removeAt(3));
		assertEquals("Size should be 4", 4, list.size());
		assertEquals("Last element should be \"bar\"", "bar", list.getLast());
	}

}
