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
package org.seasar.mayaa.impl.cycle.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;

import org.seasar.mayaa.cycle.scope.AttributeScope;
import org.seasar.mayaa.cycle.script.ScriptEnvironment;
import org.seasar.mayaa.impl.cycle.scope.AbstractRequestScope;
import org.seasar.mayaa.impl.engine.EngineUtil;
import org.seasar.mayaa.impl.provider.ProviderUtil;
import org.seasar.mayaa.impl.util.StringUtil;
import org.seasar.mayaa.impl.util.collection.EnumerationIterator;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class RequestScopeImpl extends AbstractRequestScope {

    private static final long serialVersionUID = 8377365781441987529L;

    private HttpServletRequest _httpServletRequest;
    private Locale[] _locales;
    private ParamValuesScope _paramValues;
    private HeaderValuesScope _headerValues;

    protected void check() {
        if (_httpServletRequest == null) {
            throw new IllegalStateException();
        }
    }

    public String getContextPath() {
        check();
        return StringUtil.preparePath(_httpServletRequest.getContextPath());
    }

    public String getRequestedPath() {
        check();
        String path = _httpServletRequest.getPathInfo();
        if (path == null) {
            path = _httpServletRequest.getServletPath();
        }
        if (StringUtil.isEmpty(path) || path.endsWith("/")) {
            String welcome = EngineUtil.getEngineSetting(
                    WELCOME_FILE_NAME, "/index.html");
            path = StringUtil.preparePath(path)
                    + StringUtil.preparePath(welcome);
        }
        return path;
    }

    public Locale[] getLocales() {
        check();
        if (_locales == null) {
            Enumeration<Locale> locales = _httpServletRequest.getLocales();
            if (locales == null) {
                _locales = new Locale[0];
            } else {
                ArrayList<Locale> list = new ArrayList<>();
                while (locales.hasMoreElements()) {
                    list.add(locales.nextElement());
                }
                _locales = (Locale[]) list.toArray(new Locale[list.size()]);
            }
        }
        return _locales;
    }

    public AttributeScope getParamValues() {
        check();
        if (_paramValues == null) {
            _paramValues = new ParamValuesScope(_httpServletRequest);
        }
        return _paramValues;
    }

    public AttributeScope getHeaderValues() {
        check();
        if (_headerValues == null) {
            _headerValues = new HeaderValuesScope(_httpServletRequest);
        }
        return _headerValues;
    }

    /**
     * requestのattribute "jakarta.servlet.forward.servlet_path" の内容が
     * nullではなく、{@link HttpServletRequest#getServletPath()}と異なっている
     * ならば他からforwardされたと判断する。
     *
     * @return 他からforwardされたならtrue
     * @see Servlet 2.4 spec 8.4.2 Forwarded Request Parameters
     */
    public boolean isForwarded() {
        // other attributes
        // "jakarta.servlet.forward.request_uri", "jakarta.servlet.forward.context_path",
        // "jakarta.servlet.forward.servlet_path", "jakarta.servlet.forward.path_info",
        // "jakarta.servlet.forward.query_string"

        Object servletPath =
            _httpServletRequest.getAttribute("jakarta.servlet.forward.servlet_path");
        return (servletPath != null) &&
                (servletPath instanceof String) &&
                (servletPath.equals(_httpServletRequest.getServletPath()) == false);
    }

    // AttributeScope implements -------------------------------------

    public Iterator<String> iterateAttributeNames() {
        check();
        Enumeration<String> e = _httpServletRequest.getAttributeNames();
        return EnumerationIterator.getInstance(e);
    }

    public boolean hasAttribute(String name) {
        check();
        if (StringUtil.isEmpty(name)) {
            return false;
        }
        Enumeration<String> e = _httpServletRequest.getAttributeNames();
        while (e.hasMoreElements()) {
            if (e.nextElement().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Object getAttribute(String name) {
        check();
        if (StringUtil.isEmpty(name)) {
            return null;
        }
        ScriptEnvironment env = ProviderUtil.getScriptEnvironment();
        return env.convertFromScriptObject(
                _httpServletRequest.getAttribute(name));
    }

    public void setAttribute(String name, Object attribute) {
        check();
        if (StringUtil.isEmpty(name)) {
            return;
        }
        _httpServletRequest.setAttribute(name, attribute);
    }

    public void removeAttribute(String name) {
        check();
        if (StringUtil.isEmpty(name)) {
            return;
        }
        _httpServletRequest.removeAttribute(name);
    }

    // ContextAware implemetns --------------------------------------

    public void setUnderlyingContext(Object context) {
        if (context == null
                || context instanceof HttpServletRequest == false) {
            throw new IllegalArgumentException();
        }
        _httpServletRequest = (HttpServletRequest) context;
        _locales = null;
        _paramValues = null;
        _headerValues = null;
        parsePath(getRequestedPath());
    }

    public Object getUnderlyingContext() {
        check();
        return _httpServletRequest;
    }

}
