import java.io.IOException;
import java.io.PrintWriter;

class test
{
	public static void main(String[] args) throws IOException{
		PrintWriter writer = new PrintWriter("a.txt", "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
	}
}