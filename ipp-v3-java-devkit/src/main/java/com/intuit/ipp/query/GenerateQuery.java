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

import java.util.Calendar;
import java.util.Date;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.query.expr.BooleanPath;
import com.intuit.ipp.query.expr.CalendarPath;
import com.intuit.ipp.query.expr.EnumPath;
import com.intuit.ipp.query.expr.NumberPath;
import com.intuit.ipp.query.expr.StringPath;
import com.intuit.ipp.util.Logger;

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
	 * variable CLASSNAME_SPLIT_PATTERN
	 */
	private static final String CLASSNAME_SPLIT_PATTERN = "\\$";

	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private GenerateQuery() {
	}

	/**
	 *
	 * @param cl the class
	 * @return the proxified object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createQueryEntity(Class<T> cl) {
		Class<?> proxied = null;
		if (cl.isInterface()) {
			LOG.debug("The given class is interface");
		} else {
			proxied = new ByteBuddy()
					.subclass(cl)
					.method(ElementMatchers.not(ElementMatchers.isClone().or(ElementMatchers.isFinalizer()).or(ElementMatchers.isEquals()).or(ElementMatchers.isHashCode()).or(ElementMatchers.isToString())))
					.intercept(MethodDelegation.to(new MyMethodInterceptor()))
					.make()
					.load(GenerateQuery.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
					.getLoaded();
		}
		try {
			return (T) proxied.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @param entity the entity
	 * @return the proxified object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createQueryEntity(T entity) {
		Class<?> cl = entity.getClass();
		return (T) createQueryEntity(cl);
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
			String[] extracted = name.split(CLASSNAME_SPLIT_PATTERN);
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
		String extracted[] = name.split(CLASSNAME_SPLIT_PATTERN);
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
}
