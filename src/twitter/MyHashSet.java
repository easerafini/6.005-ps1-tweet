package twitter;

import java.util.HashSet;

public class MyHashSet<E> extends HashSet<E> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {

		return super.add((E) e.toString().toLowerCase());
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof String) {
			o = ((String)o).toLowerCase();
		}
		return super.contains(o);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		return super.remove((E)o.toString().toLowerCase());
	}
}
