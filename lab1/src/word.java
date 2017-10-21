import java.io.IOException;
import javax.swing.JOptionPane;

public class Word {
  /**Ö÷º¯Êý.*/
  public static void main(String[] args) throws IOException {
    Garph g = new Garph();
    Proba p = new Proba();
    String answer;
    String[] path;
    String[] pathout;
    String pathword;
    int choice;
    System.out.println("show the change");
    String reg = "[^\\p{Alpha}]+";
    do {
      choice = Integer.parseInt(JOptionPane.showInputDialog(
      "Please choose the function(1:showDirectedGraph;2:queryBridgeWords;3:generateNewText"
      + "4:calcShortestPath;5:randomWalk;6:exit)"));
      switch (choice) {
        case 1:
          p.showDirectedGraph(g);
          break;
        case 2:
          String word1 = JOptionPane.showInputDialog("Input word1: ");
          String  word2 = JOptionPane.showInputDialog("Input word2: ");
          answer = g.queryBridgeWords(word1, word2);
          System.out.println(answer);
          if (answer.equals("error1")) {
            System.out.println("No \"" + word1 + "\" and \"" + word2 + "\" in the graph!");
          } else if (answer.equals("error2")) {
            System.out.println("No \"" + word1 + "\" in the graph!");
          } else if (answer.equals("error3")) {
            System.out.println("No \"" + word2 + "\" in the graph!");
          } else if (answer.equals("error4")) {
            System.out.println("No bridge words from\"" + word1 + "\" to \"" + word2 
                + "\"in the graph!");
          } else {
            System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2
                + "\" is:" + answer);
          }
          break;
        case 3:
          String inputText = JOptionPane.showInputDialog("Input text: ");
          System.out.println(g.generateNewText(inputText));
          //Seek to explore new and exciting synergies
          break;
        case 4:
          String inputword = JOptionPane.showInputDialog("Input words: ");
          path = inputword.split(reg);
          if (path.length == 1) {
            pathword = g.dijkstra(path[0]);
            pathout = pathword.split(",");
            for (int i = 0;i < pathout.length;i++) {
              System.out.println(pathout[i]); 
            }
          } else if (path.length == 2) {
            System.out.println(g.dijkstra(path[0], path[1]));
            System.out.println(g.weightindex);
            g.noteDirectedGraph(g,path[0],path[1],g.dijkstra(path[0], path[1]));
          } else { 
            System.out.println("Wrong!");
          }
          break;
        case 5:
          System.out.println(g.randomWalk());
          break;
        case 6:
          break;
        default:
          break;
      }
    } while (choice != 6);
  }
}