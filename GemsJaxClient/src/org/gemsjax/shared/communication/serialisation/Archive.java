package org.gemsjax.shared.communication.serialisation;

public interface Archive {
	
	public class Holder<T> {
		public T value;

		public Holder(T value) {
			this.value = value;
		}
	}

	public <T> Holder<T> serialize(String key, T value) throws Exception;
}
