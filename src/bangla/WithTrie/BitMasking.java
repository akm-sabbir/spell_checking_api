package bangla.WithTrie;

public class BitMasking {

	public int setBitAt(int source , int location) {
		source |= (1 << (location-1));
		return source;
	}
	public int resetBitAt(int source, int location) {
		source &= ~(1 << (location-1));
		return source;
	}
	public int extractNthBit(int source, int location) {
		if((source & (1 << (location-1)) )!=0) return 1;
		return 0;
	}
	public int compareNthBit(int source, int dest, int location) {
		if(this.extractNthBit(source, location) != 0 && this.extractNthBit(dest, location) != 0) {
			return 1;
		}
		if(this.extractNthBit(source, location) == 0 && this.extractNthBit(dest, location) == 0) {
			return 1;
		}
			
		return 0;
	}
	
	public  static void main(String[] args) {
		int number = 0;
		BitMasking bitM = new BitMasking();
		System.out.println(bitM.setBitAt(number, 2));
		System.out.println(bitM.setBitAt(number, 1));
		System.out.println(bitM.setBitAt(number, 3));
		System.out.println(bitM.setBitAt(number, 4));
		System.out.println(bitM.setBitAt(number, 5));
		System.out.println(bitM.resetBitAt(4, 3));
		System.out.println(bitM.extractNthBit(8, 3));
		return;
	}
}