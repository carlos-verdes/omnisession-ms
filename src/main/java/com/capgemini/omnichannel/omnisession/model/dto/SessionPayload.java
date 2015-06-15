package com.capgemini.omnichannel.omnisession.model.dto;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Special bean to merge beans in a ConcurrentHashMap.
 * <p>
 * The information could be obtained back in a concrete bean class or Json or XML
 * 
 * @author cverdesm
 *
 */
public class SessionPayload implements Serializable {
	private static final long serialVersionUID = -7563767652539918555L;

	SessionPayload dataRoot = new SessionPayloadBranch();

	public SessionPayload() {
		super();
	}

	/**
	 * Merge source data with current data
	 * 
	 * @param source
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void mergeData(String key, Object source) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IntrospectionException {
		// if source is not null
		if (source != null) {
			// insert in the root branch
			this.dataRoot.mergeData(key, source);

		}
	}

	/**
	 * <p>
	 * Return the data with no concrete class.
	 * 
	 * @param key
	 * @return If the value is simple (String, Integer, ...) the value, if is complex a HashMap<String,Object>
	 */
	public Object getData(String key) {
		Object data = this.dataRoot.getData(key);
		return data;
	}

	public <T> T getData(String key, Class<? extends T> clazz) throws InstantiationException, IllegalAccessException,
			ClassCastException, IntrospectionException, IllegalArgumentException, InvocationTargetException {
		T data = this.dataRoot.getData(key, clazz);
		return data;
	}

	public boolean contains(String key) {
		return dataRoot.contains(key);
	}

	protected static class SessionPayloadLeaf extends SessionPayload {
		private static final long serialVersionUID = 2683467494414769273L;

		@NotNull
		private String key;
		private Object value;

		public SessionPayloadLeaf(String key, Object value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public void mergeData(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public Object getData(String key) {
			return this.contains(key) ? this.value : null;
		}

		@Override
		public boolean contains(String key) {
			return this.key.equals(key);
		}

		@SuppressWarnings("unchecked")
		public <T> T getData(String key, Class<? extends T> clazz) throws ClassCastException {
			return (T) this.value;
		}

	}

	protected static class SessionPayloadBranch extends SessionPayload {
		private static final long serialVersionUID = -1509758587829560114L;
		ConcurrentHashMap<String, SessionPayload> dataMap = new ConcurrentHashMap<String, SessionPayload>();

		public SessionPayloadBranch() {

		}

		public void mergeData(String key, Object source) throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException, IntrospectionException {
			// get source bean info
			BeanInfo sourceBeanInfo = Introspector.getBeanInfo(source.getClass());

			// Iterate over all the attributes
			if (sourceBeanInfo.getPropertyDescriptors().length > 0) {

				// init new branch
				dataMap.putIfAbsent(key, new SessionPayloadBranch());

				SessionPayload newBranch = dataMap.get(key);

				for (PropertyDescriptor descriptor : sourceBeanInfo.getPropertyDescriptors()) {
					String propertyName = descriptor.getDisplayName();

					// if source property is readable
					Method method;
					if ((method = descriptor.getReadMethod()) != null) {
						// get property value
						Object propertyValue = method.invoke(source);

						// merge with branch
						newBranch.mergeData(propertyName, propertyValue);

					}

				}

			} else {
				// put simple data
				dataMap.put(key, new SessionPayloadLeaf(key, source));
			}

		}

		public Object getData(String key) {

			HashMap<String, Object> values = new HashMap<String, Object>();

			for (Entry<String, SessionPayload> entry : this.dataMap.entrySet()) {
				values.put(entry.getKey(), entry.getValue().getData(key));
			}

			return values;
		}

		public boolean contains(String key) {

			return this.dataMap.containsKey(key);
		}

		public <T> T getData(String key, Class<? extends T> clazz) throws InstantiationException,
				IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException {
			SessionPayload data = this.dataMap.getOrDefault(key, null);

			T result = clazz.newInstance();

			// get result bean info
			BeanInfo resultBeanInfo = Introspector.getBeanInfo(clazz);

			// for each writable properties
			for (PropertyDescriptor descriptor : resultBeanInfo.getPropertyDescriptors()) {
				String propertyName = descriptor.getDisplayName();

				// if source property is readable
				Method method;
				if ((method = descriptor.getWriteMethod()) != null) {
					Object propertyValue = null;

					if (data.contains(propertyName)) {
						propertyValue = data.getData(propertyName);
					}

					if (propertyValue != null
							&& propertyValue.getClass().isAssignableFrom(descriptor.getPropertyType())) {
						method.invoke(result, propertyValue);
					}

				}
			}

			return data.getData(key, clazz);
		}
	}

}
