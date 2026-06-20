package software.ulpgc.aoc.day08.model;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> {
    private final Map<T, T> parent = new HashMap<>();
    private final Map<T, Integer> size = new HashMap<>();

    public T find(T item) {
        return parent.getOrDefault(item, item).equals(item) 
            ? item 
            : compress(item, find(parent.get(item)));
    }

    private T compress(T item, T root) {
        parent.put(item, root);
        return root;
    }

    public boolean union(T item1, T item2) {
        return unionRoots(find(item1), find(item2));
    }

    private boolean unionRoots(T root1, T root2) {
        return !root1.equals(root2) && performUnion(root1, root2);
    }

    private boolean performUnion(T root1, T root2) {
        return size(root1) < size(root2) 
            ? link(root1, root2) 
            : link(root2, root1);
    }

    private boolean link(T child, T parentNode) {
        return linkWithSizes(child, parentNode, size(child), size(parentNode));
    }

    private boolean linkWithSizes(T child, T parentNode, int childSize, int parentSize) {
        parent.put(child, parentNode);
        size.put(parentNode, parentSize + childSize);
        return true;
    }

    public int size(T item) {
        return size.getOrDefault(find(item), 1);
    }
}
