import java.util.*;

public class SimpleTree {
    public class Node{
	private int number;
	private int character;
	private final List<Node> children = new ArrayList<>();
	
	public Node(int num, int value)
	{
	    number = num;
	    character = value;
	}
	public Node addChild(Node child)
	{
	    children.add(child);
	    return child;
	}
	public int getValue()
	{
	    return character;
	}
	public int getNumber()
	{
	    return number;
	}
	    
	public List<Node> getChildren()
	{
	    return children;
	}
	public boolean hasChildren()
	{
	    return children.size() != 0;
	}
	public boolean hasChild(int ch)
	{
	    for(Node n : children)
		if(ch == n.getValue())
		    return true;
	    return false;
	}
	public Node getChild(int ch)
	{
	    for(Node n : children)
		if(ch == n.getValue())
		    return n;
	    return this;
	}
	public void printNode()
	{
	    System.out.print("Node("+number+"): "+character);
	}
    }
    
    int count ;
    Node treeRootNode;
    public SimpleTree()
    {
	count = -1;
	treeRootNode = new Node(count, 0);
    }
    
    public void addSequence(List<Integer> sequence)
    {
	Node cur = treeRootNode;

	for(Integer i : sequence)
	    {
		if( cur.hasChild(i) )
		    cur = cur.getChild(i);
		else{
		    ++count;
		    Node newNode = new Node(count, i);
		    cur = cur.addChild(newNode);
		}
	    }
    }
    
    public boolean findSequence(List<Integer> sequence)
    {
	Node cur = treeRootNode;
	for(Integer i : sequence)
	    {
		if( cur.hasChild(i) )
		    cur = cur.getChild(i);
		else
		    return false;
	    }
	if( cur.hasChildren() == true)
	    return false;
	return true;
    }

    public void printTree()
    {
	Node cur;
	Queue<Node> queue = new LinkedList<Node>();
	queue.add(treeRootNode);
	while( !queue.isEmpty() )
	    {
		cur = queue.element();
		cur.printNode();
		if(cur.hasChildren())
		    {
			System.out.print( " -> Child: ");
			for(Node n: cur.getChildren())
			    {
				System.out.print(n.getValue() +" ");
				queue.offer(n);
			    }

		    }
		System.out.println("");					
		queue.poll();
	    }
    }

}
 


