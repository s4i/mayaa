/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
package org.seasar.mayaa.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.mayaa.ParameterAware;
import org.seasar.mayaa.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc)
 */
public class NonSerializableParameterAwareImpl implements ParameterAware {

    private transient Map<String, String> _parameters;
    private String _systemID = "";
    private int _lineNumber;
    private boolean _onTemplate;

    // ParameterAware implements --------------------------------------

    public void setParameter(String name, String value) {
        if (StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            throw new IllegalParameterValueException(getClass(), name);
        }
        if (_parameters == null) {
            _parameters = new HashMap<>();
        }
        _parameters.put(name, value);
    }

    public String getParameter(String name) {
        if (StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        if (_parameters == null) {
            return null;
        }
        return (String) _parameters.get(name);
    }

    public Iterator<String> iterateParameterNames() {
        if (_parameters == null) {
            return Collections.emptyIterator();
        }
        return _parameters.keySet().iterator();
    }

    // PositionAware implements ---------------------------------------

    public void setSystemID(String systemID) {
        _systemID = StringUtil.preparePath(systemID);
    }

    public String getSystemID() {
        return _systemID;
    }

    public void setLineNumber(int lineNumber) {
        if (lineNumber < 0) {
            lineNumber = 0;
        }
        _lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return _lineNumber;
    }

    public void setOnTemplate(boolean onTemplate) {
        _onTemplate = onTemplate;
    }

    public boolean isOnTemplate() {
        return _onTemplate;
    }
}
