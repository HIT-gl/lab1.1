import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Garph {
  int  maxweight = Integer.MAX_VALUE / 2;
  static String[] words = new String[100];
  static int[][] edge = new int[100][100];
  static int  reallen;
  int weightindex;
  
  /**
   *����ͼ.
   */
  
  public Garph() throws IOException {
    String[] temp;
    String[] txt = new String[100];
    System.out.println("make some differrance");
    FileReader file = new FileReader("C:\\Users\\zy\\Desktop\\dd.txt");
    BufferedReader input = new BufferedReader(file);
    String line = null;
    int index = 0;
    int len = 0 ;
    String reg = "[^\\p{Alpha}]+";
    while ((line = input.readLine()) != null) {
      if (!line.equals("")) {
        temp = line.split(reg);
        for (int i = 0 ; i < temp.length ; i++) {
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
    for (int i = 1 ; i < len ; i++) {
      if (match(words,txt[i]) == -1) {
        words[index] = txt[i];
        int j = match(words,txt[i - 1]) == -1 ? index - 1 : match(words,txt[i - 1]);
        edge[j][index] = 1;
        index++;
      } else {
        reallen--;
        int j = match(words,txt[i - 1]) == -1 ? index - 1 : match(words,txt[i - 1]);
        int k = match(words,txt[i]);
        edge[j][k]++;
      }
    }
    for (int i = 0 ; i < reallen ;i++) {
      System.out.printf("%15s",words[i] + "  ");
      for (int j = 0 ; j < reallen ; j++) {
        System.out.print(edge[i][j] + "   ");
      }
      System.out.print("\n");
    }
    input.close();
  }
  
  /**
   *�����ַ������ַ��������λ��.
  */

  static int match(String[] template, String substr) throws IOException {
    int i;
    boolean find = false;
    for (i = 0 ; i < template.length ; i++) {
      if ((template[i] != null) && (template[i].equals(substr))) {
        find = true;
        break;
      }
    }
    if (find) {
      return i;
    } else {
      return -1;
    }
  }
  
  /**
   *��ѯ�ŽӴ�.
  */
  String queryBridgeWords(String word1, String word2) throws IOException {
    String bridgeword = "";
    boolean result = false; //��־��ͼ���ܷ��ҵ����뵥��
    boolean find1 = false;
    boolean find2 = false;
    int i = 0;
    int j = 0;
    for (i = 0 ; i < reallen ; i++) {
      if (word1.equals(words[i])) {
        find1 = true;
        break;
      }
    }
    for (j = 0 ; j < reallen ; j++) {
      if (word2.equals(words[j])) {
        find2 = true;
        break;
      }
    }
    if (find1 == false && find2 == false) {
      return "error1";
    } else if (find1 == false) {
      return "error2";
    } else if (find2 == false) {
      return "error3";
    }
    for (int k = 0 ; k < reallen ; k++) {
      if ((k != i) && (k != j) && (edge[i][k] > 0) && (edge[k][j] > 0)) {
        bridgeword = bridgeword + words[k] + " ,";
        result = true;
      }
    }
    if (result == false) {
      return "error4";
    } else {
      return bridgeword;
    }
  }
  
  /**
   *�������ı�.
  */
  
  String generateNewText(String inputText) throws IOException { 
    String[] temp; 
    String[] bridgeword;
    String newText;
    String result;
    int ran = 0;
    String reg = "[^\\p{Alpha}]+";
    temp = inputText.split(reg);
    if (temp.length == 0) {
      return "error!";
    }
    newText = temp[0];
    for (int i = 1 ; i < temp.length ; i++) {
      result = queryBridgeWords(temp[i - 1], temp[i]);
      bridgeword = result.split(reg);
      if (result.equals("error1") || result.equals("error2") || result.equals("error3")
          || result.equals("error4")) {
        newText = newText + " " + temp[i];
      } else if (bridgeword.length == 1) {
        newText = newText + " " + bridgeword[0] + " " + temp[i];
      } else {
        ran = new Random().nextInt(bridgeword.length);
        newText = newText + " " + bridgeword[ran] + " " + temp[i];
      }
    }
    return newText;
  }
  
  /**
   *���·���㷨,����һ������.
  */
  
  public String dijkstra(String word1) throws IOException {
    int n = reallen;
    int degree = 0;
    int minweight = maxweight;
    int minUn = 0;
    int [][] edge2 = new int[reallen][reallen];
    int[] minmatrix = new int[reallen];// ��ŵ�ǰ��ʼ��0�������������ľ��룻  
    boolean[] isS = new boolean[n];// �жϸ����Ƿ񱻷��ʹ�  
    String[] route = new String[n];// ÿ���ַ�������ʾ��Ӧ��ֹ������̾����·����
    int word1index = match(words,word1);
    String sentence = "";
    if (word1index == -1) {
      return "ERROR";
    }
    for (int i = 0 ; i < reallen ;i++) {
      for (int j = 0 ; j < reallen ; j++) {
        edge2[i][j] = edge[i][j];
        if ((i != j) && (edge2[i][j] == 0)) {
          edge2[i][j] = maxweight;
        }
      }
    }
    for (int i = 0; i < n; i++) {
      if (i != word1index) { // ��ʼ�� 
        minmatrix[i] = edge2[word1index][i];
        isS[i] = false;
        route[i] = words[word1index] + "->" + words[i];
      }
    }
    int degree2 = word1index;
    for (int i = 0; i < n; i++) { // ѡ�� ��ǰ ����� ��ͨ�ģ���ֵ��С�Ķ��㣻  
      degree = word1index;
      for (int k = 0; k < n; k++) {
        if ((! isS[degree]) && (degree != word1index)) {
          if (minmatrix[degree] < minweight) {
            minweight = minmatrix[degree];
          }
          minUn = degree;
        } 
      }
      degree = (degree + 1) % reallen;
    }
    isS[minUn] = true;// ���õ�����Ϊ�ѷ��ʣ� 
    degree2 = word1index;
    for (int j = 0; j < n; j++) {
      if (!isS[degree2]) { // �жϣ��ö��㻹û���뵽S��/����U-S�� 
        if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) {
          // ͨ��������Сֵ ���ʵ�����������ľ���С��ԭ�ȵ���Сֵ ����н���ֵ
          minmatrix[degree2] = minweight + edge2[minUn][degree2];
          route[degree2] = route[minUn] + "->" + words[degree2];
        }
      }
      degree2 = (degree2 + 1) % reallen;
    }  
    minweight = maxweight;// ��ΪҪ�ŵ���һ��ѭ���У�����һ��Ҫ������һ�£��ص����ֵ  
    for (int m = 0; m < n; m++) {
      if (m != word1index) {
        if (minmatrix[m] == maxweight) {
          System.out.println("û�е���õ��·��");
        } 
      } else {
        sentence = sentence + route[m] + ","; 
        //System.out.println(route[m]); 
      } 
    }
    return sentence;
  }
  /**
   *���·���㷨��������������.
  */
  
  public String dijkstra(String word1,String word2) throws IOException {
    int n = reallen;
    int degree;
    int minweight = maxweight;  
    int minUn = 0; 
    int [][] edge2 = new int[reallen][reallen];
    int[] minmatrix = new int[reallen];// ��ŵ�ǰ��ʼ��0�������������ľ��룻  
    boolean[] isS = new boolean[n];// �жϸ����Ƿ񱻷��ʹ� 
    String[] route = new String[n];// ÿ���ַ�������ʾ��Ӧ��ֹ������̾����·����
    int word1index = match(words,word1);
    if (word1index == -1) {
      return "ERROR";
    }
    int word2index = match(words,word2);
    if (word2index == -1) {
      return "ERROR";
    }
    for (int i = 0 ; i < reallen ;i++) {
      for (int j = 0 ; j < reallen ; j++) {
        edge2[i][j] = edge[i][j];
        if ((i != j) && (edge2[i][j] == 0)) {
          edge2[i][j] = maxweight;
        }
      }
    }
    for (int i = 0; i < n; i++) {
      if (i != word1index) { // ��ʼ��
        minmatrix[i] = edge2[word1index][i];
        isS[i] = false;
        route[i] = words[word1index] + "->" + words[i];
      }
    }
    int degree2 = word1index;
    for (int i = 0; i < n; i++) {
      // ѡ�� ��ǰ ����� ��ͨ�ģ���ֵ��С�Ķ��㣻
      degree = word1index;
      for (int k = 0; k < n; k++) {
        if ((! isS[degree]) && (degree != word1index)) {
          if (minmatrix[degree] < minweight) {
            minweight = minmatrix[degree];
            minUn = degree;
          }
        }
        degree = (degree + 1) % reallen;
      }
      isS[minUn] = true;// ���õ�����Ϊ�ѷ���
      degree2 = word1index;
      for (int j = 0; j < n; j++) {
        if (! isS[degree2]) { // �жϣ��ö��㻹û���뵽S��/����U-S��
          if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) {
            minmatrix[degree2] = minweight + edge2[minUn][degree2];
            route[degree2] = route[minUn] + "->" + words[degree2];
          }
        } 
        degree2 = (degree2 + 1) % reallen;
      }
      minweight = maxweight;// ��ΪҪ�ŵ���һ��ѭ���У�����һ��Ҫ������һ�£��ص����ֵ
    }
    weightindex = minmatrix[word2index];
    return  route[word2index];
  }
  /**
   *�������.
  */
  
  public String randomWalk() {
    int[] adj = new int[reallen]; //�����ĳ���
    int [][] markedge = new int[reallen][reallen];//��־���Ƿ񱻷���
    String text = new String();
    int numadj;
    int  i = 0;
    int  j = 0;
    int k = 0;
    int ran = new Random().nextInt(reallen);
    text = words[ran];
    while (true) {
      j = 0;
      for (i = 0 ; i < reallen ; i++) {
        if (edge[ran][i] != 0) { //words[ran]->words[i]������δ������
          adj[j] = i;
          j++;
        }
      }
      numadj = j;
      if (numadj == 0) {
        break;
      } else {
        k = new Random().nextInt(numadj);
        if (markedge[ran][adj[k]] == 0) {
          text = text + " " + words[adj[k]];
          markedge[ran][adj[k]] = 1;
          ran = adj[k];
        } else {
          text = text + " " + words[adj[k]];
          break;
        }
      }
    }
    return text;
  }
  
  /**
   *����ͼ.
  */
  
  public void noteDirectedGraph(Garph g,String wor1,String word2,String word3) throws IOException {
    String [] notepath;
    GraphViz gv1 = new GraphViz();
    gv1.addln(gv1.start_graph());
    
    notepath = word3.split("->");
    for (int i = 0;i < reallen;i++) {
      for (int j = 0;j < reallen;j++) {
        if (edge[i][j] > 0) {
          int a = match(notepath,words[i]);
          int b = match(notepath,words[j]);
          if ((b - a == 1) && (a != -1)) {
            gv1.addlncolor(words[i] + "->" + words[j],"" + edge[i][j]);
          } else {
            gv1.addlnlabel(words[i] + "->" + words[j],"" + edge[i][j]);
          }
        }
      }
    }
    gv1.addln(gv1.end_graph());
    System.out.println(gv1.getDotSource());
    String type = "png";
    File out = new File("E://graph2" + type);    // Windows
    gv1.writeGraphToFile(gv1.getGraph(gv1.getDotSource(), type), out);
  }
}
