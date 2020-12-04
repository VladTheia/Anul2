import javax.print.DocFlavor;
import java.util.*;

public class prob2 {
    public static void main (String[] args) {
        ArrayMap<Integer, String> map = new ArrayMap<>();
        map.put(1, "Denis");
        map.put(4, "Vlad");
        map.put(2, "Minge");
        map.put(5, "Asus");
        System.out.println(map);
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        System.out.println(set);
    }
}

class ArrayMap<K, V> extends AbstractMap<K, V> {
    private Vector<K> keyV;
    private Vector<V> valueV;

    public ArrayMap() {
        keyV = new Vector<>();
        valueV = new Vector<>();
    }

    class ArrayMapEntry<K,V> implements Map.Entry<K,V> {
        private K key;
        private V value;
        public ArrayMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public ArrayMapEntry(K key) {
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value){
            V old = this.value;
            this.value = value;
            return old;
        }

        public String toString(){
            String str = "<" + key + "," + value+">";
            return str;
        }

        public boolean equals(Object o){
            if (o == null)
                return false;
            ArrayMapEntry e = (ArrayMapEntry) o;
            if(this.getKey() != e.getKey() && this.getKey() != null)
                return false;
            if(this.getValue() != e.getValue() && this.getValue() != null)
                return false;
            return true;
        }

        public int hashCode() {
            if(getKey() == null || getValue() == null)
                return 0;
            return getKey().hashCode() ^ getValue().hashCode();
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> set = new HashSet<>();
        Enumeration<V> enumeration_val = valueV.elements();
        Enumeration<K> enumeration_key = keyV.elements();
        while(enumeration_key.hasMoreElements() && enumeration_val.hasMoreElements()) {
            K key = enumeration_key.nextElement();
            V val = enumeration_val.nextElement();
            ArrayMapEntry<K, V> entry = new ArrayMapEntry<>(key, val);
            set.add(entry);
        }
        return set;
    }

    public String toString() {
        String str = "{";
        Enumeration<V> enumeration_val = valueV.elements();
        Enumeration<K> enumeration_key = keyV.elements();
        while(enumeration_key.hasMoreElements() && enumeration_val.hasMoreElements()) {
            K key = enumeration_key.nextElement();
            V val = enumeration_val.nextElement();
            str += "(" + key +"," + val + ")";
            if (enumeration_key.hasMoreElements() && enumeration_val.hasMoreElements()) {
                str += ", ";
            }
        }
        return str;
    }

    public V put(K key, V val){
        V old_val = val;
        if (keyV.contains(key)) {
            int index = keyV.indexOf(key);
            old_val = valueV.get(index);
            valueV.set(index, val);
        } else {
            keyV.add(key);
            valueV.add(val);
        }
        return old_val;
    }

    public V get(Object obj) {
        if(keyV.contains(obj)) {
            int index = keyV.indexOf(obj);
            return valueV.get(index);
        } else {
            return null;
        }
    }

    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>(keyV);
        return set;
    }

    public Collection<V> values() {
        Collection<V> coll;
        coll = valueV.subList(valueV.indexOf(valueV.lastElement()), valueV.indexOf(valueV.lastElement()));
        return coll;
    }
}
