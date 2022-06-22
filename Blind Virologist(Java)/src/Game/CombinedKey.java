package Game;

import java.util.Objects;

/**
 * Generikus párt tároló objektum.
 *
 * @param <K1> generikus osztály 1
 * @param <K2> generikus osztály 2
 */
public class CombinedKey<K1, K2> {
	public K1 key1;
	public K2 key2;

	public CombinedKey() {
	}

	public CombinedKey(K1 key1, K2 key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	@Override
	public boolean equals(Object o) {
		CombinedKey key = (CombinedKey) o;
		return Objects.equals(key1, key.key1) && Objects.equals(key2, key.key2);
	}

	@Override
	public int hashCode() {
		int result = key1 != null ? key1.hashCode() : 0;
		result = 31 * result + (key2 != null ? key2.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "[" + key1 + ", " + key2 + "]";
	}
}
