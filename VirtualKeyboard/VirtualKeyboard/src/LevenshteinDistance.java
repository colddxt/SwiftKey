import java.util.*;
import java.io.*;

public class LevenshteinDistance {
    public static class info{
	public Integer i;
	public ArrayList<Integer> op; //0 no change, 1 add, 2 del, 3 replace
	public ArrayList<Character> ch;
	public info(){	    
	    op = new ArrayList<Integer>();
	    ch = new ArrayList<Character>();
	}
	
    }
    public static Pair<ArrayList<Integer>, ArrayList<Character> >
	calDis(String s1, String s2){
	info [][] Comp = new info[s1.length()+1][s2.length()+1];

	for(int i = 0; i <= s1.length(); i ++)
	    for(int j = 0; j <= s2.length(); j++)
		Comp[i][j] = new info();
	
	Comp[0][0].i = 0;
	for(int i = 1; i <= s1.length(); i++){
	    Comp[i][0].i = i;
	    Comp[i][0].op.add(1);
	    for(int j = 0; j<i;  j++)
			       Comp[i][0].ch.add(s1.charAt(j));
	    //System.out.println(i);
	    
	}
	for(int j = 1; j <= s2.length(); j++){
	    Comp[0][j].i = j;
	    Comp[0][j].op.add(2);
	    for(int i = 0; i<j; i++)
		Comp[0][j].ch.add(s2.charAt(i));
	    //System.out.println(j);
	    
	}
	int min;
	boolean isTL;
	int tl;
	for(int i = 1; i <=s1.length(); i++){
	    for(int j = 1; j <= s2.length(); j++){
		if(s1.charAt(i-1) == s2.charAt(j-1))
		    isTL = true;
		else
		    isTL = false;
		if(isTL)
		    tl = Comp[i-1][j-1].i;
		else
		    tl = Comp[i-1][j-1].i+1;
		min = min(Comp[i-1][j].i+1,Comp[i][j-1].i+1,tl);
		if(min == tl && isTL == true){
		    Comp[i][j].i = min;
		    Comp[i][j].op.addAll(Comp[i-1][j-1].op);
		    Comp[i][j].op.add(0);
		    Comp[i][j].ch.addAll(Comp[i-1][j-1].ch);
		    Comp[i][j].ch.add(s1.charAt(i-1));
		    continue;
		}
		if(min == tl && isTL == false){
		    Comp[i][j].i = min;
		    Comp[i][j].op.addAll(Comp[i-1][j-1].op);
		    Comp[i][j].op.add(3);
		    Comp[i][j].ch.addAll(Comp[i-1][j-1].ch);
		    Comp[i][j].ch.add(s2.charAt(j-1));
		    Comp[i][j].ch.add(s1.charAt(i-1));
		    continue;
		}
		if(min == Comp[i-1][j].i + 1){
		    Comp[i][j].i = min;
		    Comp[i][j].op.addAll(Comp[i-1][j].op);
		    Comp[i][j].op.add(1);
		    Comp[i][j].ch.addAll(Comp[i-1][j].ch);
		    Comp[i][j].ch.add(s1.charAt(i-1));
		    continue;
		}
		if(min == Comp[i][j-1].i + 1){
		    Comp[i][j].i = min;
		    Comp[i][j].op.addAll(Comp[i][j-1].op);
		    Comp[i][j].op.add(2);
		    Comp[i][j].ch.addAll(Comp[i][j-1].ch);
		    Comp[i][j].ch.add(s2.charAt(j-1));
		    continue;
		}
		    
	    }
	}
	System.out.println("Distance: "+Comp[s1.length()][s2.length()].i);
	return new Pair<ArrayList<Integer>, ArrayList<Character> >
	    (Comp[s1.length()][s2.length()].op,Comp[s1.length()][s2.length()].ch);
	
    }
	public static int min(int a, int b, int c){
	    if(a<=b && a<=c)
		return a;
	    if(b<=a && b<=c)
		return b;
	    return c;
	}
    public static void main(String[] args){
	//String s1 = "cafe";
	//String s2 = "coffee";
	String s2 = "cafe";
	String s1 = "";
	System.out.println("s1: "+s1);
	System.out.println("s2: "+s2);
	Pair<ArrayList<Integer>, ArrayList<Character> > result = calDis(s1,s2);
	for(Integer i : result.getKey())
	    System.out.print(i);
	System.out.println();
	for(Character c : result.getValue())
	    System.out.print(c);
	System.out.println();	
    }
}
 
