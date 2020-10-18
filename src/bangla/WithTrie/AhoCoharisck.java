package bangla.WithTrie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

public class AhoCoharisck {

	List<Vertex> trie;
	List<Integer> wordsLength;
	int size = 0;
	int root = 0;

	public AhoCoharisck(){
		trie = new ArrayList<Vertex>();
		wordsLength = new ArrayList<Integer>();
		Init();
	}

	private void Init()
	{
		trie.add(new Vertex());            
		size++;
	}
	public void initiateGlobalDict(String s, int wordID) {
		int curVertex = root;
		for (int i = 0; i < s.length(); ++i) // Iterating over the string's characters
		{
			char c = s.charAt(i);

			// Checking if a vertex with this edge exists in the trie:
			if (!trie.get(curVertex).children.containsKey(c))
			{
				trie.add(new Vertex());
				
				trie.get(size).suffixLink = -1; // If not - add vertex
				trie.get(size).parent = curVertex;
				trie.get(size).parentChar = c;
				trie.get(curVertex).children.put(c, size);
				size++;
			}
			curVertex = (int)trie.get(curVertex).children.get(c); // Move to the new vertex in the trie
		}
		// Mark the end of the word and store its ID
		trie.get(curVertex).leaf = true;
		trie.get(curVertex).wordLength = s.length();

	}
	
	public void PrepareAho()
	{
		Queue<Integer> vertexQueue = new LinkedList<>();
		vertexQueue.add(root);
		while (vertexQueue.size() > 0){
			int curVertex = vertexQueue.remove();
			calcAndBuildSuffix(curVertex);

			for (Entry<Character, Integer> key : trie.get(curVertex).children.entrySet())
			{
				vertexQueue.add((int)trie.get(curVertex).children.get(key.getKey()));
			}
		}
	}
	public void calcAndBuildSuffix(int vertex){
		if (vertex == root)
		{ 
			trie.get(vertex).suffixLink = root;
			trie.get(vertex).endWordLink = root;
			return;
		}
		// Processing children of the root (one character substrings)
		if (trie.get(vertex).parent == root)
		{ 
			trie.get(vertex).suffixLink = root;
			if (trie.get(vertex).leaf) trie.get(vertex).endWordLink = vertex;
			else trie.get(vertex).endWordLink = trie.get(trie.get(vertex).suffixLink).endWordLink;
			return;
		}
		// Cases above are degenerate cases as for prefix function calculation; the
		// value is always 0 and links to the root vertex.

		// To calculate the suffix link for the current vertex, we need the suffix
		// link for the parent of the vertex and the character that moved us to the
		// current vertex.
		int curBetterVertex = trie.get(trie.get(vertex).parent).suffixLink;
		char chVertex = trie.get(vertex).parentChar; 
		// From this vertex and its substring we will start to look for the maximum
		// prefix for the current vertex and its substring.
		
		while (true)
		{
			// If there is an edge with the needed char, we update our suffix link
			// and leave the cycle
			if (trie.get(curBetterVertex).children.containsKey(chVertex))
			{
					trie.get(vertex).suffixLink = (int)trie.get(curBetterVertex).children.get(chVertex);
					break;
			}
			// Otherwise, we are jumping by suffix links until we reach the root
			// (equivalent of k == 0 in prefix function calculation) or we find a
			// better prefix for the current substring.
			if (curBetterVertex == root)
			{ 
					trie.get(vertex).suffixLink = root;
					break;
			}
			curBetterVertex = trie.get(curBetterVertex).suffixLink; // Go back by sufflink
		}
		// When we complete the calculation of the suffix link for the current
		// vertex, we should update the link to the end of the maximum length word
		// that can be produced from the current substring.
		if (trie.get(vertex).leaf) trie.get(vertex).endWordLink = vertex; 
		else trie.get(vertex).endWordLink = trie.get(trie.get(vertex).suffixLink).endWordLink;
	}
	
	public ArrayList<String> ProcessString(String text)
	{
		// Current state value
		int currentState = root;

		// Targeted result value
		ArrayList<String> result = new ArrayList<>();

		for (int j = 0; j < text.length(); j++)
		{
			// Calculating new state in the trie
			while (true)
			{
				// If we have the edge, then use it
				if (trie.get(currentState).children.containsKey(text.charAt(j))){
					currentState = (int)trie.get(currentState).children.get(text.charAt(j));
					break;
				}
				// Otherwise, jump by suffix links and try to find the edge with
				// this char
	            // If there aren't any possible edges we will eventually ascend to
	            // the root, and at this point we stop checking.
				if (currentState == root) break;
				currentState = trie.get(currentState).suffixLink;
			}
			//int checkState = currentState;
			if(trie.get(currentState).leaf == true) {
				result.add(text.substring(j - (trie.get(currentState).wordLength- 1), j + 1));
			}
			// Trying to find all possible words from this prefix
		}

		return result;
	}
	public static void main(String args[]) {
		AhoCoharisck ahocoarisck = new AhoCoharisck();
		ahocoarisck.initiateGlobalDict("সকাল", 0);
		ahocoarisck.initiateGlobalDict("বেলা", 1);
		ahocoarisck.initiateGlobalDict("ছেলেরা", 2);
		ahocoarisck.initiateGlobalDict("পানি", 2);
		ahocoarisck.initiateGlobalDict("তে", 2);
		ahocoarisck.initiateGlobalDict("সাঁতার", 2);
		ahocoarisck.initiateGlobalDict("কাট", 2);
		ahocoarisck.initiateGlobalDict("ছিলো", 2);
		ahocoarisck.PrepareAho();
		ArrayList<String> result = ahocoarisck.ProcessString("সকালবেলাছেলেরাপানিতেসাঁতারকাটছিলো");
		for(String each : result) {
			System.out.println(each);
		}
	}
}
