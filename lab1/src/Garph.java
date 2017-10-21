import java.io.*;
import java.util.Random;
/**
* 
*
* @author George Bush
*/
public class Garph {
	/**
	* 此处应有注释
	*/
	int maxWeight = Integer.MAX_VALUE/2;
	/**
	* 此处应有注释
	*/
	static String[] words = new String[100];
	/**
	* 此处应有注释
	*/
	static int edge[][] = new int[100][100];
	/**
	* 此处应有注释
	*/
	static int  reallen;
	/**
	* 此处应有注释
	*/
	int weightIndex;
	public char[] weight_index;
	/**
	* 
	*
	* 注释
	*/
	public Garph() throws IOException
	{
		String[] temp;
		String[] txt = new String[100];
		System.out.println("make some differrance");
		FileReader file = new FileReader("C:\\Users\\zy\\Desktop\\dd.txt");
		BufferedReader input = new BufferedReader(file);
		String line = null;
		int index = 0, i, j, k, len;
		String reg = "[^\\p{Alpha}]+";
		while((line=input.readLine())!=null)
		{
			if (!line.equals("")){
				temp = line.split(reg);
				for (i = 0 ; i < temp.length ; i++)
				{
					txt[index] = temp[i].toLowerCase();
					index++;
				}
			}
		}
		reallen = index;
		len = index;
		index = 0;
		words[index] = txt[0];
		index++;
		for(i = 1 ; i < len ; i++)
		{
			if(match(words,txt[i]) == -1)   //无重复出现
			{
				words[index] = txt[i];
				j = match(words,txt[i-1])==-1?index - 1:match(words,txt[i-1]);
				edge[j][index] = 1;
				index++;
			}
			else
			{
				reallen--;
				j = match(words,txt[i-1])==-1?index - 1:match(words,txt[i-1]);
				k = match(words,txt[i]);
				edge[j][k]++;
			}
		}
		for (i = 0 ; i < reallen ;i++) {
			System.out.printf("%15s",words[i]+"  ");
			for (j = 0 ; j < reallen ; j++)
				System.out.print(edge[i][j]+"   ");
			System.out.print("\n");
		}
		input.close();
	}
	//返回字符串在字符串数组的位置
	static int match(String[] template, String substr) throws IOException
	{
		int i;
		boolean find = false;
		for (i = 0 ; i < template.length ; i++)
		{
			if((template[i] != null)&&(template[i].equals(substr)))
			{
				find = true;
				break;
			}
		}
		if (find)
			return i;
		else
			return -1;
	}
	String queryBridgeWords(String word1, String word2) throws IOException
	{
		String bridgeWord = "";
		int i, j, k; //i,j分别表示word1，word2在图中的位置
		boolean result = false , find1 , find2 ; //标志在图中能否找到输入单词
		find1 = false;
		find2 = false;
		for (i = 0 ; i < reallen ; i++)
			if (word1.equals(words[i]))
			{
				find1 = true;
				break;
			}
		for (j = 0 ; j < reallen ; j++)
			if (word2.equals(words[j]))
			{
				find2 = true;
				break;
			}
		if (find1 == false && find2 == false)
			return "error1";
		else if (find1 == false)
			return "error2";
		else if(find2 == false)
			return "error3";
    	for (k = 0 ; k < reallen ; k++)
			if ((k!=i)&&(k!=j)&&(edge[i][k]>0)&&(edge[k][j]>0))
			{
				bridgeWord = bridgeWord + words[k] + " ,";
				result = true;
			}
		if (result == false)
			return "error4";
		else
			return bridgeWord; 
	}
	String generateNewText(String inputText) throws IOException
	{
		String[] temp, bridgeWord;
		String newText,  result;
		int i, ran;
		String reg = "[^\\p{Alpha}]+";
		temp = inputText.split(reg);
		if (temp.length == 0)
			return "error!";
		newText = temp[0];
		for (i = 1 ; i < temp.length ; i++)
		{
			result = queryBridgeWords(temp[i-1], temp[i]);
			bridgeWord = result.split(reg);
			if (result.equals("error1")||result.equals("error2")||result.equals("error3")||result.equals("error4"))
				newText = newText + " " + temp[i];
			else if(bridgeWord.length == 1)
				newText = newText + " " + bridgeWord[0] + " " + temp[i];
			else
			{
				ran = new Random().nextInt(bridgeWord.length);
				newText = newText + " " + bridgeWord[ran] + " " + temp[i];
			}
		}
		return newText;
	}

	/**
	* 
	*
	* 注释
	*/
	public String dijkstra(String word1) throws IOException
	{  
		   int n = reallen, degree, degree2;  
		   int minweight =maxWeight;  
		   int minUn = 0; 
		   int [][] edge2 = new int[reallen][reallen];
		   int[] minmatrix = new int[reallen];// 存放当前起始点0到其余各个顶点的距离；  
		   boolean[] isS = new boolean[n];// 判断各个是否被访问过  
		   String[] route = new String[n];// 每个字符串是显示对应终止顶点最短距离的路径；
		   int word1Index = match(words,word1);
		   String sentence = "";
		   if (word1Index == -1)
			   return "ERROR";
		   for (int i = 0 ; i < reallen ;i++) 
		   {
				for (int j = 0 ; j < reallen ; j++)
				{
					edge2[i][j]=edge[i][j];
					if((i!=j)&&(edge2[i][j]==0))
						edge2[i][j]=maxWeight;
				}		
				
			}
		   for (int i = 0; i < n; i++) 
		   if(i!=word1Index){// 初始化  
			   minmatrix[i] = edge2[word1Index][i];  
			   isS[i] = false;  
			   route[i] = words[word1Index] +"->" + words[i];    
		   }  
		   degree2 = word1Index;
		   for (int i = 0; i < n; i++) 
		   {  		     
		   // 选择 当前 和起点 连通的，且值最小的顶点；  
			   degree = word1Index;
			   for (int k = 0; k < n; k++) 
			   {  			     
				   if ((!isS[degree]) &&(degree != word1Index))
				   {  			     
					   if (minmatrix[degree] < minweight) 
					   {  
						   minweight = minmatrix[degree];  						     
						   minUn = degree;  			     
					   }  		     
				   }  
				   degree = (degree + 1)%reallen;
			   }  		     
			   isS[minUn] = true;// 将该点设置为已访问；  	
			   degree2 = word1Index;
			   for (int j = 0; j < n; j++) 
			   {     
				   if (!isS[degree2]) 
				   {// 判断：该顶点还没加入到S中/属于U-S；  				     
					   if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) 
					   {  				     
					   // 通过当下最小值 访问到得其他顶点的距离小于原先的最小值 则进行交换值  				     
						   minmatrix[degree2] = minweight + edge2[minUn][degree2];  				     
						   route[degree2] = route[minUn] + "->" + words[degree2];  		     
					   }  		     
				   }  
				   degree2 = (degree2 + 1)%reallen;
			   }  
			   minweight =maxWeight;// 因为要放到下一个循环中，所以一定要重设置一下，回到最大值  		     
		   }  	     
		   for (int m = 0; m < n; m++) 
		   {  
			   if(m!=word1Index)			   
		   
					if (minmatrix[m] ==maxWeight) 
					{     
						System.out.println("没有到达该点的路径");  		     
					} 
					else 
					{  		     
					   		sentence=sentence+route[m]+",";     
					   //System.out.println(route[m]);  		   		   
					} 
				}  
		     
		//   }  
		   return sentence;
		     
	}
	/**
	* 
	*
	* 注释
	*/
	public String dijkstra(String word1,String word2) throws IOException
	{  
		   int n = reallen, degree, degree2;  
		   int minweight =maxWeight;  
		   int minUn = 0; 
		   int [][] edge2 = new int[reallen][reallen];
		   int[] minmatrix = new int[reallen];// 存放当前起始点0到其余各个顶点的距离；  
		   boolean[] isS = new boolean[n];// 判断各个是否被访问过  
		   String[] route = new String[n];// 每个字符串是显示对应终止顶点最短距离的路径；
		   int word1Index = match(words,word1);
		   if (word1Index == -1)
			   return "ERROR";
		   int word2Index = match(words,word2);
		   if (word1Index == -1)
			   return "ERROR";
		   for (int i = 0 ; i < reallen ;i++) 
		   {
				for (int j = 0 ; j < reallen ; j++)
				{
					edge2[i][j]=edge[i][j];
					if((i!=j)&&(edge2[i][j]==0))
						edge2[i][j]=maxWeight;
				}		
					
			}
		   for (int i = 0; i < n; i++) 
		   if(i!=word1Index){// 初始化  
			   minmatrix[i] = edge2[word1Index][i];  
			   isS[i] = false;  
			   route[i] = words[word1Index] +"->" + words[i];    
		   }  
		   degree2 = word1Index;
		   for (int i = 0; i < n; i++) 
		   {  		     
		   // 选择 当前 和起点 连通的，且值最小的顶点；  
			   degree = word1Index;
			   for (int k = 0; k < n; k++) 
			   {  			     
				   if ((!isS[degree]) &&(degree != word1Index))
				   {  			     
					   if (minmatrix[degree] < minweight) 
					   {  
						   minweight = minmatrix[degree];  						     
						   minUn = degree;  			     
					   }  		     
				   }  
				   degree = (degree + 1)%reallen;
			   }  		     
			   isS[minUn] = true;// 将该点设置为已访问；  	
			   degree2 = word1Index;
			   for (int j = 0; j < n; j++) 
			   {     
				   if (!isS[degree2]) 
				   {// 判断：该顶点还没加入到S中/属于U-S；  				     
					   if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) 
					   {  				     
					   // 通过当下最小值 访问到得其他顶点的距离小于原先的最小值 则进行交换值  				     
						   minmatrix[degree2] = minweight + edge2[minUn][degree2];  				     
						   route[degree2] = route[minUn] + "->" + words[degree2];  		     
					   }  		     
				   }  
				   degree2 = (degree2 + 1)%reallen;
			   }  
			   minweight =maxWeight;// 因为要放到下一个循环中，所以一定要重设置一下，回到最大值
			   
		   }
		   weightIndex=minmatrix[word2Index];
		 return  route[word2Index];
		 
		     
	}  

	String randomWalk()
	{
		int[] adj = new int[reallen]; //储存点的出点
		int [][] markEdge = new int[reallen][reallen];//标志边是否被访问 // NOPMD by zy on 17-10-19 下午8:08
		//String text = new String();
		int numAdj, ran, i, j, k;
		ran = new Random().nextInt(reallen);
		String text = words[ran];
		while (true) {
			j = 0;
			for (i = 0 ; i < reallen ; i++)
			{
				if (edge[ran][i]!=0) //words[ran]->words[i]存在且未被访问
				{
					adj[j] = i;
					j++;
				}
			}
			numAdj = j;
			if (numAdj == 0)
				
				break;
			else
			{
				k = new Random().nextInt(numAdj);
				if (markEdge[ran][adj[k]] == 0)
				{
					text = text + " " + words[adj[k]];
					markEdge[ran][adj[k]] = 1;
					ran = adj[k];
				}
			
				
				else
				{
					text = text + " " + words[adj[k]];
					break;
				}
			}
		}
		return text;
	}
	/**
	* 
	*
	* 注释
	*/
	public void noteDirectedGraph(Garph g,String wor1,String word2,String word3) throws IOException
	   {
	     String [] notepath; 
		GraphViz gv1 = new GraphViz();
	      gv1.addln(gv1.startGraph());
	      int a,b,len;
	      notepath = word3.split("->");
	      for(int i=0;i<reallen;i++)
	      {
	    	  for(int j=0;j<reallen;j++)
	    	  {
	    		  
	    		  if(edge[i][j]>0) 
	    		  {
	    			  a=match(notepath,words[i]);
	    			  b=match(notepath,words[j]);
	    			  if((b - a == 1)&&(a != -1))
	    			  {
	    				  gv1.addlncolor(words[i]+"->"+words[j],""+edge[i][j]);
	    			  }
	    			  else
	    				  gv1.addlnlabel(words[i]+"->"+words[j],""+edge[i][j]);
	    		  }
	    	  }
	      }
	      
	      gv1.addln(gv1.endGraph());
	      System.out.println(gv1.getdotSource());
	     
	      
	 //     String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
	     // String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
	      String type = "png";
//	      String type = "plain";
	 //     File out = new File("/tmp/out." + type);   // Linux
	      File out = new File("E://graph2" + type);    // Windows
	      gv1.writeGraphToFile( gv1.getGraph( gv1.getdotSource(), type ), out );
	   }
}