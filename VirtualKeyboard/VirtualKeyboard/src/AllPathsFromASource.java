//import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class AllPathsFromASource {
    Object[][] allRes = new Object[26][26];
    Object[][] allLetterTree = new Object[26][26];
    Object[][] allLetterList = new Object[26][26];
    Graph g;
    Map<String, List<String> > replaceCross;
    DLB tree;
    int range;
    //public static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    public List<Integer> stringToNumber(String alphabet) {
	List<Integer> result = new ArrayList<Integer>();
	for(int i= 0; i < alphabet.length(); i++) {
	    int c = alphabet.charAt(i) - 'a'; //!!
	    result.add(c);
	}
	return result;
    }
    
    public AllPathsFromASource(int r) throws FileNotFoundException
    {
	range = r;
	buildAllPath(range);
	buildDict();
	buildCross();
    }
    
    public void storeWords(ArrayList<String> lists)
    {

        tree.storeWords(lists);

    }
    public boolean isPath(String s)
    {
	
	List<Integer> input= stringToNumber(s);
	/*
	  if(input.get(0) != input.get(input.size()-1))
	  System.out.println(input.get(0) + " " + input.get(input.size()-1));
	*/
	if(input.get(0) == input.get(input.size()-1))
	    return false;
	if (input.size() == 0 || input.size() == 1){
	    //System.out.println("wei shen me yao pi lei 2 ge yi xia de a !!!!!!");
	    return true;
	}
	if(input.size() == 2){
	    //System.out.println("wei shen me yao pi lei 2 ge yi xia de a !!!!!!");
	    boolean is = g.hasEdge(input.get(0),input.get(1));
	    if(is == false)
		System.out.println("wo cao ni zen me da chu lai de !!!!");		
	    return is;
	}
	
	return ((SimpleTree)allLetterTree[input.get(0)][input.get(input.size()-1)])
	    .findSequence(input);
    }

    public void printListFromTo(int a, int b)
    {
	System.out.println("Path List from: " + ""+ a +" to "+ "" + b + "is:");
	List<List<Integer>> results =((List<List<Integer>>)allLetterList[a][b]);
	for (List<Integer> t: results){
	    System.out.println(t);
	}
	System.out.println();
	
    }
    
    public void printTreeFromTo(int a, int b)
    {
	System.out.println("Path Tree from: " + ""+ a +" to "+ "" + b + "is:");
	((SimpleTree)allLetterTree[a][b]).printTree();
	System.out.println();	
    }
    
    public void buildDict() throws FileNotFoundException
    {
	tree = new DLB();
	Scanner input = new Scanner(new File("word_freq.txt"));
	String buffer1 = new String("");
	int buffer2;
	int i = 0;
	while(input.hasNext()){	
	    buffer1 = input.next();
	    buffer2 = new Integer(input.next());
	    tree.add(buffer1, buffer2);
	}
    }
    
    public void buildAllPath(int range)
    {
	g = new Graph(26);
	g.addEdge(16, 22);
	g.addEdge(22, 4);
	g.addEdge(4, 17);
	g.addEdge(17, 19);
	g.addEdge(19, 24);
	g.addEdge(24, 20);
	g.addEdge(20, 8);
	g.addEdge(8, 14);
	g.addEdge(14, 15);
	g.addEdge(0, 18);
	g.addEdge(18, 3);
	g.addEdge(3, 5);
	g.addEdge(5, 6);
	g.addEdge(6, 7);
	g.addEdge(7, 9);
	g.addEdge(9, 10);
	g.addEdge(10, 11);
	g.addEdge(25, 23);
	g.addEdge(23, 2);
	g.addEdge(2, 21);
	g.addEdge(21, 1);
	g.addEdge(1, 13);
	g.addEdge(13, 12);
	g.addEdge(16, 0);
	g.addEdge(22, 18);
	g.addEdge(4, 3);
	g.addEdge(17, 5);
	g.addEdge(19, 6);
	g.addEdge(24, 7);
	g.addEdge(20, 9);
	g.addEdge(8, 10);
	g.addEdge(14, 11);
	g.addEdge(18, 25);
	g.addEdge(3, 23);
	g.addEdge(5, 2);
	g.addEdge(6, 21);
	g.addEdge(7, 1);
	g.addEdge(9, 13);
	g.addEdge(10, 12);
	
	List<List<Integer>> results;
	SimpleTree tree;
	for (int i = 0; i < 26; i++) {
	    for (int j = 0; j < 26; j++) {
		if(i == j) continue;
		tree = new SimpleTree();
		results = g.getMinPath(g.getAllPaths(i, j), range);
		for (List<Integer> t: results){
		    tree.addSequence(t);
		}
		allLetterList[i][j] = results;
		allLetterTree[i][j] = tree;
	    }
	}
	//((SimpleTree)allLetterTree[16][23]).printTree();	
    }

    public void buildCross()
    {
	replaceCross = new HashMap<String, List<String> >();

	List<String> tempL = new ArrayList<String>();

	tempL.clear();
	tempL.add("qas");
	tempL.add("qws");
	replaceCross.put("qs", new ArrayList(tempL));

	tempL.clear();
	tempL.add("saq");
	tempL.add("swq");
	replaceCross.put("sq", new ArrayList(tempL));

	//System.out.println(replaceCross.get("qs").get(0));
	//System.out.println(replaceCross.get("qs").get(1));
	//System.out.println(replaceCross.get("sq").get(0));
	//System.out.println(replaceCross.get("sq").get(1));
	tempL.clear();
	tempL.add("wed");
	tempL.add("wsd");
	replaceCross.put("wd", new ArrayList(tempL));

	tempL.clear();
	tempL.add("dew");
	tempL.add("dsw");
	replaceCross.put("dw", new ArrayList(tempL));

	
	tempL.clear();
	tempL.add("edf");
	tempL.add("erf");
	replaceCross.put("ef", new ArrayList(tempL));

	tempL.clear();
	tempL.add("fre");
	tempL.add("fde");
	replaceCross.put("fe", new ArrayList(tempL));

	
	tempL.clear();
	tempL.add("rfg");
	tempL.add("rtg");
	replaceCross.put("rg", new ArrayList(tempL));

	tempL.clear();
	tempL.add("gfr");
	tempL.add("gtr");
	replaceCross.put("gr", new ArrayList(tempL));



	
	tempL.clear();
	tempL.add("tgh");
	tempL.add("tyh");
	replaceCross.put("th", new ArrayList(tempL));

	tempL.clear();
	tempL.add("hgt");
	tempL.add("hyt");
	replaceCross.put("ht", new ArrayList(tempL));



	tempL.clear();
	tempL.add("yhj");
	tempL.add("yuj");
	replaceCross.put("yj", new ArrayList(tempL));

	tempL.clear();
	tempL.add("jhy");
	tempL.add("juy");
	replaceCross.put("jy", new ArrayList(tempL));



	tempL.clear();
	tempL.add("uik");
	tempL.add("ujk");
	replaceCross.put("uk", new ArrayList(tempL));

	tempL.clear();
	tempL.add("kju");
	tempL.add("kiu");
	replaceCross.put("ku", new ArrayList(tempL));



	tempL.clear();
	tempL.add("iol");
	tempL.add("ikl");
	replaceCross.put("il", new ArrayList(tempL));

	tempL.clear();
	tempL.add("loi");
	tempL.add("lki");
	replaceCross.put("li", new ArrayList(tempL));



	tempL.clear();
	tempL.add("asz");
	replaceCross.put("az", new ArrayList(tempL));

	tempL.clear();
	tempL.add("zsa");   //
	replaceCross.put("za", new ArrayList(tempL));



	tempL.clear();
	tempL.add("sdx");
	tempL.add("szx");
	replaceCross.put("sx", new ArrayList(tempL));

	tempL.clear();
	tempL.add("xzs");
	tempL.add("xds");
	replaceCross.put("xs", new ArrayList(tempL));



	tempL.clear();
	tempL.add("dxc");
	tempL.add("dfc");
	replaceCross.put("dc", new ArrayList(tempL));

	tempL.clear();
	tempL.add("cfd");
	tempL.add("cxd");
	replaceCross.put("cd", new ArrayList(tempL));



	tempL.clear();
	tempL.add("fgv");
	tempL.add("fcv");
	replaceCross.put("fv", new ArrayList(tempL));

	tempL.clear();
	tempL.add("vgf");
	tempL.add("vcf");
	replaceCross.put("vf", new ArrayList(tempL));



	tempL.clear();
	tempL.add("gvb");
	tempL.add("ghb");
	replaceCross.put("gb", new ArrayList(tempL));

	tempL.clear();
	tempL.add("bvg");
	tempL.add("bhg");
	replaceCross.put("bg", new ArrayList(tempL));



	tempL.clear();
	tempL.add("hbn");
	tempL.add("hjn");
	replaceCross.put("hn", new ArrayList(tempL));

	tempL.clear();
	tempL.add("nbh");
	tempL.add("njh");
	replaceCross.put("nh", new ArrayList(tempL));



	tempL.clear();
	tempL.add("jnm");
	tempL.add("jkm");
	replaceCross.put("jm", new ArrayList(tempL));

	tempL.clear();
	tempL.add("mnj");
	tempL.add("mkj");
	replaceCross.put("mj", new ArrayList(tempL));



	tempL.clear();
	tempL.add("wsa");//
	tempL.add("wqa");
	replaceCross.put("wa", new ArrayList(tempL));

	tempL.clear();
	tempL.add("aqw");
	tempL.add("asw");
	replaceCross.put("aw", new ArrayList(tempL));



	tempL.clear();
	tempL.add("swe");
	tempL.add("sde");
	replaceCross.put("se", new ArrayList(tempL));

	tempL.clear();
	tempL.add("ews");
	tempL.add("eds");
	replaceCross.put("es", new ArrayList(tempL));



	tempL.clear();
	tempL.add("red");
	tempL.add("rfd");
	replaceCross.put("rd", new ArrayList(tempL));

	tempL.clear();
	tempL.add("dfr");
	tempL.add("der");
	replaceCross.put("dr", new ArrayList(tempL));



	tempL.clear();
	tempL.add("fgt");
	tempL.add("frt");
	replaceCross.put("ft", new ArrayList(tempL));

	tempL.clear();
	tempL.add("tgf");
	tempL.add("trf");
	replaceCross.put("tf", new ArrayList(tempL));



	tempL.clear();
	tempL.add("gty");
	tempL.add("ghy");
	replaceCross.put("gy", new ArrayList(tempL));

	tempL.clear();
	tempL.add("yhg");
	tempL.add("ytg");
	replaceCross.put("yg", new ArrayList(tempL));



	tempL.clear();
	tempL.add("ujh");
	tempL.add("uyh");
	replaceCross.put("uh", new ArrayList(tempL));

	tempL.clear();
	tempL.add("hyu");
	tempL.add("hju");
	replaceCross.put("hu", new ArrayList(tempL));



	tempL.clear();
	tempL.add("jki");
	tempL.add("jui");
	replaceCross.put("ji", new ArrayList(tempL));

	tempL.clear();
	tempL.add("ikj");
	tempL.add("iuj");
	replaceCross.put("ij", new ArrayList(tempL));



	tempL.clear();
	tempL.add("olk");
	tempL.add("oik");
	replaceCross.put("ok", new ArrayList(tempL));

	tempL.clear();
	tempL.add("kio");
	tempL.add("klo");
	replaceCross.put("ko", new ArrayList(tempL));



	tempL.clear();
	tempL.add("lop");
	replaceCross.put("lp", new ArrayList(tempL));

	tempL.clear();
	tempL.add("pol");
	replaceCross.put("pl", new ArrayList(tempL));



	tempL.clear();
	tempL.add("zxd");
	tempL.add("zsd");
	replaceCross.put("zd", new ArrayList(tempL));

	tempL.clear();
	tempL.add("dxz");
	tempL.add("dsz");
	replaceCross.put("dz", new ArrayList(tempL));



	tempL.clear();
	tempL.add("xdf");
	tempL.add("xcf");
	replaceCross.put("xf", new ArrayList(tempL));

	tempL.clear();
	tempL.add("fdx");
	tempL.add("fcx");
	replaceCross.put("fx", new ArrayList(tempL));



	tempL.clear();
	tempL.add("cvg");
	tempL.add("cfg");
	replaceCross.put("cg", new ArrayList(tempL));

	tempL.clear();
	tempL.add("gfc");
	tempL.add("gvc");
	replaceCross.put("gc", new ArrayList(tempL));



	tempL.clear();
	tempL.add("hbv");
	tempL.add("hgv");
	replaceCross.put("hv", new ArrayList(tempL));

	tempL.clear();
	tempL.add("vbh");
	tempL.add("vgh");
	replaceCross.put("vh", new ArrayList(tempL));



	tempL.clear();
	tempL.add("bnj");
	tempL.add("bhj");
	replaceCross.put("bj", new ArrayList(tempL));

	tempL.clear();
	tempL.add("jhb");
	tempL.add("jnb");
	replaceCross.put("jb", new ArrayList(tempL));



	tempL.clear();
	tempL.add("kjn");
	tempL.add("kmn");
	replaceCross.put("kn", new ArrayList(tempL));

	tempL.clear();
	tempL.add("njk");
	tempL.add("nmk");
	replaceCross.put("nk", new ArrayList(tempL));



	tempL.clear();
	tempL.add("mkl");
	replaceCross.put("ml", new ArrayList(tempL));

	tempL.clear();
	tempL.add("lkm");
	replaceCross.put("lm", new ArrayList(tempL));


	tempL.clear();
	tempL.add("polkm");
	replaceCross.put("pm", new ArrayList(tempL));

	tempL.clear();
	tempL.add("mklop");
	replaceCross.put("mp", new ArrayList(tempL));
	
	
    }
    //public Pair<ArrayList<String>,ArrayList<Integer> > processInput(String wordC)
    public ArrayList<String> processInput(String wordC,boolean allowQWQ)
    {
	String word = wordC.toLowerCase();


	ArrayList<String> wordsWithCross = new ArrayList<String>();
	ArrayList<String> wordsWithCrossAndDelDup = new ArrayList<String>();
	
	ArrayList<String> words = new ArrayList<String>();
	
	ArrayList<String> allWords = new ArrayList<String>();
     
	ArrayList<Integer> allFreq = new ArrayList<Integer>();
	
	ArrayList<String> allWordst = new ArrayList<String>();
     
	ArrayList<Integer> allFreqt = new ArrayList<Integer>();
	
	Pair<ArrayList<String>,ArrayList<Integer> > result =
	    new Pair<ArrayList<String>,ArrayList<Integer> >(allWords,allFreq);

	Pair<ArrayList<String>,ArrayList<Integer> > resultEach =
	    new Pair<ArrayList<String>,ArrayList<Integer> >(allWordst,allFreqt);
	
	List<String> temp;
	wordsWithCross.add(word);

	for(int i = 0; i < wordsWithCross.get(0).length()-1; i++){
	    //System.out.println(wordsWithCross.get(0).substring( i, i+2 ));
	    
	    if( replaceCross.containsKey( wordsWithCross.get(0).substring( i, i+2 ) ) ){
		
		temp = replaceCross.get(wordsWithCross.get(0).substring( i, i+2 ));
		
		ArrayList<String> newWords = new ArrayList<String>();
		for(String s : wordsWithCross){
		    for(String s2 : temp){
			//System.out.println("s: " + s);
			//System.out.println("s2: " + s2);
			newWords.add( s.substring(0,i) + s2 + s.substring(i+2,s.length()) );
		    }
		}
		wordsWithCross = newWords;
	    }
	    
	}
	
	/*
	  for(String s : wordsWithCross){
	  for(int i = 0; i < s.length()-2 ; i++){
	  if(s.charAt(i) == s.charAt(i+2))
	  }			       
	  }
	*/
	removeDuplicate(wordsWithCross);
	System.out.println("With Cross: "+ wordsWithCross.size());

	if(allowQWQ){
	    
	
	    List<Integer> cordin = new ArrayList<Integer> ();
	    List<Integer> disFromBack = new ArrayList<Integer> ();
	    List<String> sForMore = new ArrayList<String> ();
	    int tempSize;
	    String tempString;
	    for(String s : wordsWithCross){
		sForMore.clear();
		for(int i = 0; i < s.length() - 2; i++){
		    if(s.charAt(i) == s.charAt(i+2)){
			cordin.add(i);
			cordin.add(i+2);
			disFromBack.add(s.length()-i-1);
			disFromBack.add(s.length()-i-3);
			//System.out.println("Cordin add: " + i + " " + (i+2));
			i = i+1;
		    }
		}
		//System.out.println("!");
		//System.out.println(s);
		sForMore.add(s);
		for(int i = 0; i < cordin.size()/2; i++ ){
		    tempSize = sForMore.size();
		    for(int j = 0; j < tempSize; j ++){
			tempString = sForMore.get(j);
			if(disFromBack.get(i*2)  >= tempString.length()){
			    continue;			
			}
		    
			sForMore.add(new String(tempString.substring(0,
								     tempString.length()-disFromBack.get(i*2)-1) +
						tempString.substring(tempString.length() - disFromBack.get(i*2)-1,
								     tempString.length() - disFromBack.get(i*2)) +
						tempString.substring(tempString.length()-disFromBack.get(i*2+1),
								     tempString.length())));
			removeDuplicate(sForMore);
		    }
	

		}
		//System.out.println("Turn: " + i);
		//for(String k : sForMore)
		//System.out.println("Adding: " + k);
		
		wordsWithCrossAndDelDup.addAll(sForMore);
		if(wordsWithCrossAndDelDup.size()>1000)
		    break;
	    }
	    wordsWithCrossAndDelDup.addAll(wordsWithCross);
	    removeDuplicate(wordsWithCrossAndDelDup);
	    System.out.println("Allow Dup: "+ wordsWithCrossAndDelDup.size());
	    
	
	    //words = wordsWithCross;  //!!!!!!
	    words = wordsWithCrossAndDelDup;
	}
	else
	    words = wordsWithCross;
	/*
	  System.out.println("words to process: ");
	  for(String s : words){
	  System.out.println(s);
	  }
	*/
	
	for(String s: words){
	    //result.addAll(findWords(s));
	    resultEach = findWords(s);
	    // System.out.println("Adding: " + resultEach.getKey().size() + " " + resultEach.getValue().size());
	    result.getKey().addAll(resultEach.getKey());
	    result.getValue().addAll(resultEach.getValue());
	}
	//System.out.println(allWords.size());
	//System.out.println(allFreq.size());
	/*
	  System.out.println("");	
	  System.out.println("Befor erase words find: " + result.getKey().size());
	  allWords = result.getKey();
	  allFreq = result.getValue();
	
	  for (int i = 0; i < result.getKey().size(); i++){
	  System.out.println(allWords.get(i) + " " + allFreq.get(i));
	  }
	  System.out.println("");
	*/
	removeDuplicate(result);
	//System.out.println(allWords.size());
	//System.out.println(allFreq.size());
	
	int tempi;
	String temps;
	for (int i = 0; i < result.getValue().size() - 1; i++) {   
	    for (int j = i + 1; j < result.getValue().size(); j++) {   
		if (result.getValue().get(i) < result.getValue().get(j)) {
		    tempi = result.getValue().get(i);   
		    result.getValue().set(i, result.getValue().get(j));   
		    result.getValue().set(j, tempi);

		    temps = result.getKey().get(i);   
		    result.getKey().set(i, result.getKey().get(j));   
		    result.getKey().set(j, temps);   
		}   
	    }   
	}   
	System.out.println("words find: ");
	//for(String s : result)
	//  System.out.println(s+" ");
	//System.out.println("");
	allWords = result.getKey();
	allFreq = result.getValue();
	//System.out.println(allWords.size());
	//System.out.println(allFreq.size());
	for (int i = 0; i < result.getKey().size(); i++){
	    System.out.println(allWords.get(i) + " " + allFreq.get(i));
	}
	    
	
	return  result.getKey();
    }

    public static void removeDuplicate(Pair<ArrayList<String>,ArrayList<Integer> > list){
	
	for (int i = 0; i < list.getKey().size() - 1; i++) {
            for (int j = list.getKey().size() - 1; j > i; j--) {
                if (list.getKey().get(j).equals(list.getKey().get(i))) {
                    list.getKey().remove(j);
		    list.getValue().remove(j);
                }
            }
        }

    }

    public static void removeDuplicate(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
	
    }
    
    public Pair<ArrayList<String>, ArrayList<Integer> > findWords(String testForInput){

	ArrayList<String> allWords = new ArrayList<String>();
     
	ArrayList<Integer> allFreq = new ArrayList<Integer>();
	
	Pair<ArrayList<String>, ArrayList<Integer> > allWordsAndFreq =
	    new Pair<ArrayList<String>, ArrayList<Integer> >(allWords,allFreq);

	if(testForInput.length() == 1){
	    allWords.add(testForInput);
	    // System.out.println("words find: ");
	    //for(String words : allWords)
	    //	System.out.println(words+" ");
	    allFreq.add(1);
	    return allWordsAndFreq;
	}
	
	    
	Stack<Character> stackc = new Stack<Character>();
	Stack<Integer> stacki = new Stack<Integer>();
	int pre = 0;
	int cur = 1;
	String temps;
	stackc.add(testForInput.charAt(0));
	stacki.add(0);


	
	while(true) {

	    /*
	    System.out.println("pre: "+pre+", cur: "+cur);
	    System.out.print("Char stack: ");
	    for(Character element : stackc)
		System.out.print(element + " ");
	    System.out.println("");
	    System.out.print("Int stack: ");
	    for(Integer element : stacki)
		System.out.print(element + " ");
	    System.out.println("");

	    */	
	    temps = "";
	    for(Character element : stackc)
		temps = temps + element;
	    temps = temps + testForInput.charAt(cur);

	    //System.out.println("Dict: "+ temps);
	    
	    if( tree.searchPrefix( temps ) != 0){

		//System.out.println("Find in Dict!");
		//System.out.println("Path: "+ testForInput.substring(pre, cur+1));
		if( isPath( testForInput.substring(pre, cur+1)) == true){
		    stackc.push(testForInput.charAt(cur));
		    stacki.push(cur);
		
		    pre = cur;
		    cur++;
		    // System.out.println("Find in Path!");
		}
		else{
		    //System.out.println("Can't find in Path!");
		    cur++;
		}

	    }
	    else{
		
		//System.out.println("Can't find in Dict!");
		cur++;
	    }

	    if(cur == testForInput.length()){
		if( stacki.peek() == testForInput.length()-1){
		    if(tree.searchPrefix( temps ) == 3 ||
		       tree.searchPrefix( temps ) == 2 )
			{
			    //System.out.println("\n!!! Word !!!: " + tostring(stackc));
			    allWords.addAll(tree.getWords(tostring(stackc)).getKey());
			    allFreq.addAll(tree.getWords(tostring(stackc)).getValue());
			    //System.out.println("In find: " + allWords.size());

			}
		    if(stacki.peek() != 0){
			cur = stacki.pop() + 1;
			stackc.pop();
			pre = stacki.peek();			
		    }
		    else{
			//System.out.println("Break from cur == lentgh");
			//System.out.println("");
			break;
			
		    }
		    
		}		
	        if(stacki.peek() != 0){		    
		    cur = stacki.pop() + 1;
		    stackc.pop();
		    pre = stacki.peek();
		} else {
		    //System.out.println("Break else");
		    //System.out.println("");
		    break;
		}
	    }
		
	    //System.out.println("\n");
	}
	/*
	System.out.println("words find: ");
	for(String words : allWords)
	    System.out.println(words+" ");
	*/
	//System.out.println("Find return: " + allWordsAndFreq.getKey().size());
	return allWordsAndFreq;
    }


    public static String tostring(Stack<Character> charS)
    {
	String s = "";
	for(Character element : charS)
	    s = s + element;
	return s;
    }    



 

    public static void main(String[] args) throws FileNotFoundException{

	
		
	AllPathsFromASource a = new AllPathsFromASource(5);

	//List<String> temps = a.processInput("qwdfvb");
	//List<String> temps = a.processInput("nmlpopm");
	//for(String s : temps)
	//System.out.println(s);
	
	//String testForInput = "BGRE";
	//String testForInput = "tytgtyuiopo";
	//String testForInput = "IKMPOIJHGFDSASDFTYUIOIUJNBHJKLIUYTRE";
	//String testForInput = "IKMPOIUYTREWSDFGHJKIUYHBNMLKJHGFE";
	//String testForInput = "IKMLPOIUYGFDSDFGHJKLOIUYHBNMLKJHGFDE";//!
	//String testForInput = "SASDFGVGTREWE";
	String testForInput = "IKMLPOIUYTREWSDFGHJKILOIUJNBHJKLKJHGFDERE";//!
	//String testForInput = "BHYTRE";
	/*
	  3 possible: tyt tgt opo
	  000
	  100
	  010
	  110
	  001
	  101
	  011
	  111

	 */
	a.processInput(testForInput, true);

	//ArrayList<String> words = new ArrayList<String>();
	//a.storeWords(words);

	/*
	String testForInput2 = "OIUYT";
	a.processInput(testForInput2);
	
	String testForInput3 = "FGYUJBVCVBNK";
	a.processInput(testForInput3);
	
	String testForInput4 = "TRESDFVGT";
	a.processInput(testForInput4);
		
	String testForInput5 = "ASDCVHJKMNBVCXSAZSWE";
	a.processInput(testForInput5);
	//a.findWords(testForInput);
	*/




    }

}