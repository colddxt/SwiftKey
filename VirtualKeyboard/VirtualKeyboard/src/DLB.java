import java.util.*;
import java.lang.*;


public class DLB
{
    private DLBnode root;
    private static int number = 0;
	
    public int returnNumber(){
	return number;
    }
	
    private class DLBnode
    {
	private char val;
	private DLBnode rightSib;
	private DLBnode child;
	private ArrayList<String> words;
	private ArrayList<Integer> frequency;
		
	public DLBnode(char ch)
	{
	    val = ch;
	    rightSib = null;
	    child = null;
	    number++;
	    words = new ArrayList();
	    frequency = new ArrayList();
	}
		
	public void addWF(String s, int f){
	    if(words.size() == 0){
		words.add(s);
		frequency.add(f);
	    }
	    else{
		int i = 0;
		while( i < frequency.size() && frequency.get(i) > f)
		    i++;
		if(i < frequency.size()){
		    words.add(i,s);
		    frequency.add(i,f);
		}
		else{
		    words.add(s);
		    frequency.add(f);
		}
	    }
	}
    }
    
    public void storeWords(ArrayList<String> lists)
    {
	Queue<DLBnode> queue = new LinkedList<DLBnode>();
	ArrayList<String> words = new ArrayList<String>();
	ArrayList<Integer> freq = new ArrayList<Integer>();
	DLBnode cur;
	queue.add(root);
	while(!queue.isEmpty()){
	    cur = queue.element();
	    

	    words.addAll(cur.words);
	    freq.addAll(cur.frequency);

	    
	    if(cur.child != null)
		queue.offer(cur.child);
	    if(cur.rightSib != null)
		queue.offer(cur.rightSib);
	    queue.poll();
	}
	
	System.out.println("");
	for(int i = 0; i < words.size(); i++){
	    System.out.println(words.get(i)+" : "+freq.get(i));
	}
	
 
    }
    
    public DLB()
    {
	root = new DLBnode('a');
    }
    
    public boolean add(String s, int frequen)
    {
	String origin = s;
	String changed = "";        //Store the string that eliminate duplicate letters
	int num = 0;
	while(num < s.length()-1){
	    if(s.charAt(num) != s.charAt(num+1))
		changed = changed + s.charAt(num);
	    num++;
	}
	changed = changed + s.charAt(s.length()-1);
	s = changed;
		
	DLBnode check = root;
	check = root;
	for ( int i = 0; i < s.length(); i++)
	    {
		while (s.charAt(i) != check.val)  //Check ith character in sib
		    {
			if (check.rightSib == null)
			    {
				DLBnode ncheck = new DLBnode(s.charAt(i));
				check.rightSib = ncheck;
				check = ncheck;
				for ( int j = i+1; j < s.length(); j++)
				    {
					DLBnode newcheck = new DLBnode(s.charAt(j));
					check.child = newcheck;
					check = newcheck;
				    }
				DLBnode newcheck = new DLBnode('$');
				newcheck.addWF(origin, frequen);
				check.child = newcheck;
				return true;
			    }
			else check = check.rightSib;
		    }
		if(s.charAt(i) == check.val) //If the character match, check child and iterate next character
		    if(check.child == null)    //If 'check' doesn't have a child
			{
			    for ( int j = i+1; j < s.length(); j++)
				{
				    DLBnode newcheck = new DLBnode(s.charAt(j));
				    check.child = newcheck;
				    check = newcheck;
				}
			    DLBnode newcheck = new DLBnode('$');
			    newcheck.addWF(origin, frequen);
			    check.child = newcheck;
			    return true;
			}
		    else check = check.child;   //If 'check' has a child
	    }
	while (check.val != '$')
	    if(check.rightSib != null)
		check = check.rightSib;
	    else
		break;
	if(check.val == '$'){
	    check.addWF(origin, frequen);
	}else{
	    DLBnode newcheck = new DLBnode('$');
	    newcheck.addWF(origin, frequen);
	    check.rightSib = newcheck;
	}
	return true;
    }
	
	
    public int searchPrefix(String s)
    {
	String changed = "";        //Store the string that eliminate duplicate letters
	int num = 0;
	while(num < s.length()-1){
	    if(s.charAt(num) != s.charAt(num+1))
		changed = changed + s.charAt(num);
	    num++;
	}
	changed = changed + s.charAt(s.length()-1);
	s = changed;
		
	int status = 0;
	boolean doneOut, currTest, prefix, word;
	doneOut = false;
	prefix = false;
	word = false;
	DLBnode check;
	check = root;
		
	for (int i = 0; i < s.length() && !doneOut; i++) //Check all the characters
	    {
		while (s.charAt(i) != check.val)
		    {
			if (check.rightSib == null)
			    {
				doneOut = true;
				break;
			    }
			else check = check.rightSib;
		    }
		if(s.charAt(i) == check.val)
		    {
			if (check.child == null)
			    {
				doneOut = true;
				break;
			    }
			check = check.child;
		    }
	    }
		
	if (!doneOut)                   //If all the characters are found, check if it is prefix and word
	    {
		if (check.val == '$')
		    word = true;
		else prefix = true;
		while (check.rightSib != null)
		    {
			check = check.rightSib;
			if (check.val == '$')
			    word = true;
			else prefix = true;
		    }
	    }
		
	if (prefix && word) return 3;
	else if (word) return 2;
	else if (prefix) return 1;
	else return 0;
    }
	
    public Pair<ArrayList<String>, ArrayList<Integer>>  getWords(String s)
    {
	//assume the input is a word
	String changed = "";        //Store the string that eliminate duplicate letters
	int num = 0;
	while(num < s.length()-1){
	    if(s.charAt(num) != s.charAt(num+1))
		changed = changed + s.charAt(num);
	    num++;
	}
	changed = changed + s.charAt(s.length()-1);
	s = changed;
		
	int status = 0;
	boolean doneOut, currTest, prefix, word;
	doneOut = false;
	prefix = false;
	word = false;
	DLBnode check;
	check = root;
		
	for (int i = 0; i < s.length() && !doneOut; i++) //Check all the characters
	    {
		while (s.charAt(i) != check.val)
		    {
			if (check.rightSib == null)
			    {
				doneOut = true;
				break;
			    }
			else check = check.rightSib;
		    }
		if(s.charAt(i) == check.val)
		    {
			if (check.child == null)
			    {
				doneOut = true;
				break;
			    }
			check = check.child;
		    }
	    }
		
	if (!doneOut)                   //If all the characters are found, check if it is prefix and word
	    {
		if (check.val == '$'){
		    //System.out.println("In tree: " + check.words.size());
		    return new Pair(check.words, check.frequency);		    
		}

		while (check.rightSib != null)
		    {
			check = check.rightSib;
			if (check.val == '$'){
			    //System.out.println("In tree: " + check.words.size());
			    return new Pair(check.words, check.frequency);
			}
		    }
	    }
	return null;
    }
}