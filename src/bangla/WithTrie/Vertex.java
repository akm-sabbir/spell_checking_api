package bangla.WithTrie;

import java.util.HashMap;

public class Vertex
{
	public Vertex()
	{
		children = new HashMap<>();            
		leaf = false;
		parent = -1;
		suffixLink = -1;
		wordLength = -1;
		endWordLink= -1;
	}

    // Links to the child vertexes in the trie:
    // Key: A single character
    // Value: The ID of vertex
	public HashMap<Character, Integer> children;

    // Flag that some word from the dictionary ends in this vertex
	public boolean leaf;

    // Link to the parent vertex
	public int parent;

    // Char which moves us from the parent vertex to the current vertex
	public char parentChar;

    // Suffix link from current vertex (the equivalent of P[i] from the KMP algorithm)
	public int suffixLink;

    // Link to the leaf vertex of the maximum-length word we can make from the current prefix
	public int endWordLink;

    // If the vertex is the leaf, we store the ID of the word
	public int wordLength;
}