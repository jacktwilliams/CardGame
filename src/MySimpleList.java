public interface MySimpleList<AnyType> extends Iterable<AnyType> {
	int size();
	boolean isEmpty();
	void clear();
	boolean contains(AnyType x);
	void add(int index, AnyType x);
	AnyType remove(int index);
	java.util.Iterator<AnyType> iterator();
}
