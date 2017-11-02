package otus;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private static final int INITIAL_CAPACITY = 10;
    private Object[] array;
    private int size;

    public MyArrayList() {
        size = 0;
        array = new Object[INITIAL_CAPACITY];
    }

    public MyArrayList(int size) {
        this.size = size;
        array = new Object[size];
    }

    public MyArrayList(T[] array) {
        this();
        for (T o : array) {
            add(o);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        throw new NotImplementedException();
    }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator<>();

    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean add(T element) {
        size++;
        int oldCapacity = array.length;

        if (size >= Integer.MAX_VALUE)
            throw new IllegalStateException("maximum size of array has been reached");

        if (size >= oldCapacity) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }

        array[size - 1] = element;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }
        return (T) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }
        T oldElement = (T) array[index];
        array[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        throw new NotImplementedException();
    }

    @Override
    public T remove(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    private class MyListIterator<E> implements ListIterator<E> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < array.length;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (E) array[current++];
        }

        @Override
        public boolean hasPrevious() {
            return current != 0;
        }

        @Override
        public E previous() {
            throw new NotImplementedException();
        }

        @Override
        public int nextIndex() {
            return current;
        }

        @Override
        public int previousIndex() {
            return current - 1;
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }

        @Override
        public void set(E e) {
            MyArrayList.this.set(current - 1, (T) e);
        }

        @Override
        public void add(E e) {
            throw new NotImplementedException();
        }
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
