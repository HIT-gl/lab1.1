
import java.io.*;

import javax.swing.JOptionPane;

public class word
 {
	public static void main(String[] args) throws IOException
	{
		garph G = new garph();
		Proba p = new Proba();
		String answer, word1, word2;
		String[] path;
		String[] pathout;
		String pathword;
		int choice;
		System.out.println("show the change");
		String reg = "[^\\p{Alpha}]+";
		do
		{
		choice = Integer.parseInt(JOptionPane.showInputDialog("Please choose the function(1:showDirectedGraph;2:queryBridgeWords;3:generateNewText"
				+"4:calcShortestPath;5:randomWalk;6:exit)"));
		switch (choice)
		{
		 	case 1:
		 		p.showDirectedGraph(G);
		 		break;
		 	case 2:
				word1 = JOptionPane.showInputDialog("Input word1: ");
				word2 = JOptionPane.showInputDialog("Input word2: ");
				answer = G.queryBridgeWords(word1, word2);
				System.out.println(answer);
				if (answer.equals("error1"))
					System.out.println("No \"" + word1 +"\" and \"" + word2 + "\" in the graph!");
				else if (answer.equals("error2"))
					System.out.println("No \"" + word1 + "\" in the graph!");
				else if (answer.equals("error3"))
					System.out.println("No \"" + word2 + "\" in the graph!");
				else if (answer.equals("error4"))
					System.out.println("No bridge words from\"" + word1 + "\" to \"" + word2 + "\"in the graph!");
				else
					System.out.println("The bridge words from \"" + word1 +"\" to \"" + word2 + "\" is:"+answer);
		 		break;
		 	case 3:
		 		String inputText = JOptionPane.showInputDialog("Input text: ");
		 		System.out.println(G.generateNewText(inputText));
		 		//Seek to explore new and exciting synergies
		 		break;
		 	case 4:
		 		String inputword = JOptionPane.showInputDialog("Input words: ");
		 		path = inputword.split(reg);
		 		if(path.length==1)
		 		{
		 			pathword = G.Dijkstra(path[0]);
		 			pathout = pathword.split(",");
		 			for(int i = 0;i < pathout.length;i++)
		 				System.out.println(pathout[i]);
		 		}
		 		
		 		else if(path.length==2)
		 		{
		 			
		 			System.out.println(G.Dijkstra(path[0], path[1]));
		 		    System.out.println(G.weight_index);
		 		    G.noteDirectedGraph(G,path[0],path[1],G.Dijkstra(path[0], path[1]));
		 		}
		 		else
		 			System.out.println("Wrong!");	
		 		break;
		 	case 5:
		 		System.out.println(G.randomWalk());
		 		break;
		 	case 6:
		 		break;
		}
	}while(choice!=6);
	}
	
 }
