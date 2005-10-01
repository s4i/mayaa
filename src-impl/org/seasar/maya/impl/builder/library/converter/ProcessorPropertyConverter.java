/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.maya.impl.builder.library.converter;

import org.seasar.maya.builder.library.converter.PropertyConverter;
import org.seasar.maya.engine.processor.ProcessorProperty;
import org.seasar.maya.engine.specification.NodeAttribute;
import org.seasar.maya.impl.engine.processor.ProcessorPropertyImpl;
import org.seasar.maya.impl.provider.UnsupportedParameterException;

/**
 * @author Masataka Kurihara (Gluegent, Inc)
 */
public class ProcessorPropertyConverter implements PropertyConverter {

	public Class getPropetyType() {
		return ProcessorProperty.class;
	}

	public Object convert(
			NodeAttribute attribute, String value, Class expectedType) {
        return new ProcessorPropertyImpl(
                attribute, value, expectedType);
	}

	public void setParameter(String name, String value) {
		throw new UnsupportedParameterException(getClass(), name);
	}
	
}