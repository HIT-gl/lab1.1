import java.io.File;

public class Proba {
  //private static final int MAX_WEIGHT = 0;
  /** Ö÷º¯Êý. */
  public static void main(String[] args) {
  
    //p.showDirectedGraph();
    //      p.start2();
  }
  /**
    * Construct a DOT graph in memory, convert it
    * to image and store the image in the file system.
    */

  public void showDirectedGraph(Garph g) {
    GraphViz gv = new GraphViz();
    gv.addln(gv.start_graph());
    for (int i = 0;i < g.reallen;i++) {
      for (int j = 0;j < g.reallen;j++) {
        if (g.edge[i][j] > 0) {
          gv.addlnlabel(g.words[i] + "->" + g.words[j],"" + g.edge[i][j]);
        }
      }
    }
   
      
 
    gv.addln(gv.end_graph());
    System.out.println(gv.getDotSource());
     

    String type = "png";

    File out = new File("C:\\Users\\zy\\Desktop\\graph." + type);    // Windows
    gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type),out);
  }
   
  /**
    * Read the DOT source from a file,
    * convert to image and store the image in the file system.
    */
  private void start2() {
    //     String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
  
    String input = "c:/eclipse.ws/graphviz-java-api/sample/simple.dot";    // Windows
    
    GraphViz gv = new GraphViz();
    gv.readSource(input);
    System.out.println(gv.getDotSource());
     
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
    gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
  }
   
}