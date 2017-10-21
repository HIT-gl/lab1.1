import java.io.File;
/**
* 
*
* @author George Bush
*/
public class Proba
{
  // private static final int MAX_WEIGHT = 0;
	/**
	* 
	*
	* ×¢ÊÍ
	*/
public static void main(String[] args)
   {
      Proba p = new Proba();
      //p.showDirectedGraph();
//      p.start2();
   }
/**
    * Construct a DOT graph in memory, convert it
    * to image and store the image in the file system.
    */
   public void showDirectedGraph(Garph g)
   {
      GraphViz gv = new GraphViz();
      gv.addln(gv.startGraph());
      for(int i=0;i<g.reallen;i++)
      {
    	  for(int j=0;j<g.reallen;j++)
    	  {
    		  if(g.edge[i][j]>0)
    		  {
    		  gv.addlnlabel(g.words[i]+"->"+g.words[j],""+g.edge[i][j]);
    		  }
    	  }
      }
   
      
      //gv.addln("A -> C;");
      gv.addln(gv.endGraph());
      System.out.println(gv.getdotSource());
     
      
 //     String type = "gif";
//      String type = "dot";
//      String type = "fig";    // open with xfig
     // String type = "pdf";
//      String type = "ps";
//      String type = "svg";    // open with inkscape
      String type = "png";
//      String type = "plain";
 //     File out = new File("/tmp/out." + type);   // Linux
      File out = new File("C:\\\\Users\\\\zy\\\\Desktop\\\\graph." + type);    // Windows
      gv.writeGraphToFile( gv.getGraph( gv.getdotSource(), type ), out );
   }
   
   /**
    * Read the DOT source from a file,
    * convert to image and store the image in the file system.
    */
   private void start2()
   {
 //     String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
 //     String input = dir + "/sample/simple.dot";
    String input = "c:/eclipse.ws/graphviz-java-api/sample/simple.dot";    // Windows
    
    GraphViz gv = new GraphViz();
    gv.readSource(input);
    System.out.println(gv.getdotSource());
     
      String type = "gif";
//    String type = "dot";
//    String type = "fig";    // open with xfig
//    String type = "pdf";
//    String type = "ps";
//    String type = "svg";    // open with inkscape
//    String type = "png";
//      String type = "plain";
    File out = new File("/tmp/simple." + type);   // Linux
//    File out = new File("c:/eclipse.ws/graphviz-java-api/tmp/simple." + type);   // Windows
    gv.writeGraphToFile( gv.getGraph( gv.getdotSource(), type ), out );
   }
   
}