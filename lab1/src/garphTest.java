import java.io.IOException;

import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class garphTest extends TestCase {

    /**
     * Test method for {@link garph#Dijkstra(java.lang.String)}.
     * @throws IOException 
     */
    public void testDijkstraString() throws IOException {
    //    fail("Not yet implemented");
        garph G = new garph(); 
        //用例1
        assertSame("ERROR",G.Dijkstra(""));
        //用例4
        assertEquals("it->has,it->has->been,it->has->been->a,it->has->been->a->long,it->has->been->a->long->way,",G.Dijkstra("it"));
        //用例6
        assertEquals("ERROR",G.Dijkstra("itSSSSlong"));
    }

    /**
     * Test method for {@link garph#Dijkstra(java.lang.String, java.lang.String)}.
     * @throws IOException 
     */
    public void testDijkstraStringString() throws IOException {
     //   fail("Not yet implemented");
     //   garph.Dijkstra("it","long");
   //     assertEquals("it->has->been->a->long",garph.Dijkstra("it","long"));
        garph G = new garph(); 
        //用例2
        assertEquals("it->has->been->a->long",G.Dijkstra("it","long"));
        //用例3
        assertSame("ERROR",G.Dijkstra("it","nolong"));
    }

}
