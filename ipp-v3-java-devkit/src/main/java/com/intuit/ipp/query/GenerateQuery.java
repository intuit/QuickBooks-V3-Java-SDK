/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.query;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.query.expr.BooleanPath;
import com.intuit.ipp.query.expr.CalendarPath;
import com.intuit.ipp.query.expr.EnumPath;
import com.intuit.ipp.query.expr.NumberPath;
import com.intuit.ipp.query.expr.StringPath;
import com.intuit.ipp.util.Logger;
import net.sf.cglib.proxy.NoOp;

/**
 * Class used to generate the query string
 * 
 */
public final class GenerateQuery {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable LEN_3
	 */
	private static final int LEN_3 = 3;
	
	/**
	 * variable path
	 */
	public static ThreadLocal<Path<?>> path = new ThreadLocal<Path<?>>();
	
	/**
	 * varriable message
	 */
	private static QueryMessage message = new QueryMessage();

	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private GenerateQuery() {
	}

	/**
	 * Method to create the query entity for the given class
	 * 
	 * @param cl the class
	 * @return the proxified object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createQueryEntity(Class<T> cl) {
		Enhancer enhancer = new Enhancer();
		if (cl.isInterface()) {
			LOG.debug("The given class is interface");
			//enhancer.setInterfaces(new Class[] { cl });
			//enhancer.setCallback(new MyMethodInterceptor());
		} else {
			enhancer.setSuperclass(cl);
		}
		enhancer.setCallbackFilter(CALLBACK_FILTER);
		enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE, new MyMethodInterceptor()});
		return (T) enhancer.create();
	}

	/**
	 * Method to create the query for the given entity
	 * 
	 * @param entity the entity
	 * @return the proxified object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createQueryEntity(T entity) {
		Class<?> cl = entity.getClass();
		Enhancer enhancer = new Enhancer();
		if (cl.isInterface()) {
			LOG.debug("The given entity is interface");
			//enhancer.setInterfaces(new Class[] { cl });
			//enhancer.setCallback(new MyMethodInterceptor());
		} else {
			enhancer.setSuperclass(cl);
			enhancer.setCallbackFilter(CALLBACK_FILTER);
			enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE, new MyMethodInterceptor()});
		}
		return (T) enhancer.create();
	}

	/**
	 * when no handler for specific return type is defined which means properties of that type cannot be inserted in filter expression but can be
	 * listed in select part, it will return Path
	 * 
	 * @param ret the object
	 * @return path the path
	 */
	public static Path<?> $(Object ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		if (currentPath != null) {
			return new Path<Object>(currentPath.getPathString().concat(".*"), currentPath.getEntity());
		} else {
			String name = ret.getClass().getSimpleName();
			String[] extracted = name.split("\\$\\$");
			return new Path<Object>("*", extracted[0]);
		}
	}

	/**
	 * When return type is Calendar, it will create CalendarPath which will expose filter methods accepting java.util.Calendar, java.util.Date and
	 * java.sql.Date
	 * 
	 * @param ret
	 * @return
	 */
	public static CalendarPath $(Calendar ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		return new CalendarPath(currentPath.getPathString(), currentPath.getEntity());
	}

	/**
	 * Method to get the calendar path
	 * 
	 * @param ret the date
	 * @return CalendarPath the calendar path
	 */
	public static CalendarPath $(Date ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		return new CalendarPath(currentPath.getPathString(), currentPath.getEntity());
	}

	/**
	 * When return type is String, it will create StringPath which will expose filter methods accepting only String
	 * 
	 * @param ret the string
	 * @return StringPath the string path
	 */
	public static StringPath $(String ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		return new StringPath(currentPath.getPathString(), currentPath.getEntity());
	}

	/**
	 * Method to get the number path
	 * 
	 * @param ret the number
	 * @return NumberPath the number path
	 */
	public static NumberPath $(Number ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		return new NumberPath(currentPath.getPathString(), currentPath.getEntity());
	}

	/**
	 * Method to get the boolean path
	 * 
	 * @param ret the boolean
	 * @return BooleanPath the boolean path
	 */
	public static BooleanPath $(Boolean ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		return new BooleanPath(currentPath.getPathString(), currentPath.getEntity());
	}

	/**
	 * Method to get the Enum path
	 * 
	 * @param ret the enum
	 * @return EnumPath the enum path
	 */
	public static EnumPath $(Enum<?> ret) {
		Path<?> currentPath = path.get();
		path.set(null);
		return new EnumPath(currentPath.getPathString(), currentPath.getEntity());
	}

	/**
	 * Method to get the optional syntax
	 * 
	 * @param path the path
	 * @param pathlist the path list
	 * @return OptionalSyntax the optional syntax
	 */
	public static <T extends IEntity> OptionalSyntax select(Path<?> path, Path<?>... pathlist) {
		resetQueryMessage();
		getMessage().setSQL("SELECT");
		getMessage().setEntity(path.getEntity());
		getMessage().getProjection().add(path.toString());

		if (pathlist.length != 0) {
			for (Path<?> singlePath : pathlist) {
				getMessage().getProjection().add(singlePath.toString());
			}
		}
		return new OptionalSyntax(getMessage());
	}

	/**
	 * Method to get the optional syntax for the given entity
	 * 
	 * @param entity the entity
	 * @return OptionalSyntax the optional syntax
	 */
	public static <T extends IEntity> OptionalSyntax selectCount(T entity) {
		resetQueryMessage();
		getMessage().setSQL("SELECT");
		String name = entity.getClass().getSimpleName();
		String extracted[] = name.split("\\$\\$");
		getMessage().setCount(true);
		if (extracted.length == LEN_3) {
			getMessage().setEntity(extracted[0]);
		}
		return new OptionalSyntax(getMessage());
	}

	/**
	 * Method to get the query message
	 * 
	 * @return QueryMessage the query message
	 */
	public static QueryMessage getMessage() {
		return message;
	}

	/**
	 * Method to set the query message
	 * 
	 * @param mess the query message
	 */
	public static void setMessage(QueryMessage mess) {
		message = mess;
	}

	/**
	 * Method to reset the query message
	 */
	public static void resetQueryMessage() {
		setMessage(new QueryMessage());
	}

	/**
	 * Callback filter which will filter out callback triggers for several {@link Object} methods.
	 */
	private static final CallbackFilter CALLBACK_FILTER = new CallbackFilter() {

		@Override
		public int accept(Method method) {
			if (isFinalizeMethod(method) || isCloneMethod(method) || isEqualsMethod(method)
					|| isHashCodeMethod(method) || isToStringMethod(method)) {
				return 0;
			}
			return 1;
		}

		/**
		 * Determine whether the given method is a "finalize" method.
		 * @see java.lang.Object#finalize()
		 */
		private boolean isFinalizeMethod(Method method) {
			return (method != null && method.getName().equals("finalize") &&
					method.getParameterTypes().length == 0);
		}

		/**
		 * Determine whether the given method is a "finalize" method.
		 * @see java.lang.Object#finalize()
		 */
		private boolean isCloneMethod(Method method) {
			return (method != null && method.getName().equals("clone") &&
					method.getParameterTypes().length == 0);
		}

		/**
		 * Determine whether the given method is an "equals" method.
		 * @see java.lang.Object#equals(Object)
		 */
		private boolean isEqualsMethod(Method method) {
			if (method == null || !method.getName().equals("equals")) {
				return false;
			}
			Class<?>[] paramTypes = method.getParameterTypes();
			return (paramTypes.length == 1 && paramTypes[0] == Object.class);
		}

		/**
		 * Determine whether the given method is a "hashCode" method.
		 * @see java.lang.Object#hashCode()
		 */
		private boolean isHashCodeMethod(Method method) {
			return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
		}

		/**
		 * Determine whether the given method is a "toString" method.
		 * @see java.lang.Object#toString()
		 */
		private boolean isToStringMethod(Method method) {
			return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
		}
	};

}
