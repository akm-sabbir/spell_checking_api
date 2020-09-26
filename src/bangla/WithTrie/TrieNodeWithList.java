package bangla.WithTrie;

import java.util.ArrayList;
import java.util.List;

public class TrieNodeWithList implements Comparable<TrieNodeWithList> {
	public char c;
	public boolean isWord;
	public int hitCount;
	public long createTime;
	public int reference_table;
	public long reference_id;
	public TrieNodeWithList parent;
	public int freq ;
	public List<TrieNodeWithList> children = new ArrayList<>();
	public TrieNodeWithList() {
		parent=null;
	}
	public TrieNodeWithList(char c) {
		this.c = c;
		hitCount = 0;
		createTime = System.currentTimeMillis();
		parent = null;
		this.isWord = false;
		this.reference_id = -1;
		this.reference_table = -1;
		this.freq = 0;
	}
	public long getCreateTime() {
		return this.createTime;
	}
	public int getHitCount() {
		return this.hitCount;
	}
	public char getChar() {
		return this.c;
	}
	public long getReferenceId() {
		return this.reference_id;
	}
	public int getReferenceTable() {
		return this.reference_table;
	}
	public TrieNodeWithList getParent() {
		return parent;
	}
	public List<TrieNodeWithList> getChildren(){
		return children;
	}
	@Override
	public int compareTo(TrieNodeWithList target) {
		// TODO Auto-generated method stub
		if(this.createTime > target.createTime)
			return 1;
		else if( this.createTime < target.createTime)
			return -1;
		else
			return 0;
	}
}
