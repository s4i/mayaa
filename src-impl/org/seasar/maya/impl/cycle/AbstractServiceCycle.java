/*
 * Copyright (c) 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Seasar Software License, v1.1 (aka "the License");
 * you may not use this file except in compliance with the License which
 * accompanies this distribution, and is available at
 *
 *     http://www.seasar.org/SEASAR-LICENSE.TXT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.seasar.maya.impl.cycle;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.seasar.maya.cycle.Application;
import org.seasar.maya.cycle.AttributeScope;
import org.seasar.maya.cycle.ServiceCycle;
import org.seasar.maya.cycle.script.CompiledScript;
import org.seasar.maya.cycle.script.ScriptEnvironment;
import org.seasar.maya.engine.specification.SpecificationNode;
import org.seasar.maya.impl.cycle.script.ScriptUtil;
import org.seasar.maya.impl.source.ApplicationSourceDescriptor;
import org.seasar.maya.impl.util.StringUtil;
import org.seasar.maya.provider.ServiceProvider;
import org.seasar.maya.provider.factory.ProviderFactory;
import org.seasar.maya.source.SourceDescriptor;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public abstract class AbstractServiceCycle implements ServiceCycle {

    private AttributeScope _page;
    private SpecificationNode _originalNode;
    private SpecificationNode _injectedNode;

    public Application getApplication() {
        return ProviderFactory.getServiceProvider().getApplication();
    }
    
    public void load(String systemID, String encoding) {
        if(StringUtil.isEmpty(systemID)) {
            throw new ScriptFileNotFoundException("");
        }
        String sid = systemID;
        if (sid.startsWith("/WEB-INF/")) {
            sid = sid.substring(9);
        }
        ApplicationSourceDescriptor appSource = new ApplicationSourceDescriptor();
        if (sid.startsWith("/") == false) {
            appSource.setRoot(ApplicationSourceDescriptor.WEB_INF);
        }
        appSource.setSystemID(sid);
        SourceDescriptor source = null;
        if(appSource.exists()) {
            source = appSource;
        } else {
            ServiceProvider provider = ProviderFactory.getServiceProvider();
            source = provider.getPageSourceDescriptor(sid);
            if(source.exists() == false) {
                source = null;
            }
        }
        if(source == null) {
            throw new ScriptFileNotFoundException(systemID);
        }
        ScriptEnvironment env = ScriptUtil.getScriptEnvironment();
        CompiledScript script = env.compile(source, encoding);
        script.execute();
    }

    public Iterator iterateAttributeScope() {
        Iterator it = ScriptUtil.getScriptEnvironment().iterateAttributeScope();
        return new ScopeIterator(it);
    }

    public boolean hasAttributeScope(String scopeName) {
        if(StringUtil.isEmpty(scopeName)) {
            scopeName = ServiceCycle.SCOPE_PAGE;
        }
        for(Iterator it = getServiceCycle().iterateAttributeScope(); it.hasNext(); ) {
            AttributeScope scope = (AttributeScope)it.next();
            if(scope.getScopeName().equals(scopeName)) {
                return true;
            }
        }
        return false;
    }
    
    public AttributeScope getAttributeScope(String scopeName) {
        if(StringUtil.isEmpty(scopeName)) {
            scopeName = ServiceCycle.SCOPE_PAGE;
        }
        for(Iterator it = getServiceCycle().iterateAttributeScope(); it.hasNext(); ) {
            AttributeScope scope = (AttributeScope)it.next();
            if(scope.getScopeName().equals(scopeName)) {
                return scope;
            }
        }
        throw new ScopeNotFoundException(scopeName);
    }

    public void setPageScope(AttributeScope page) {
        _page = page;
    }
    
    public AttributeScope getPageScope() {
        return _page;
    }

    public void setOriginalNode(SpecificationNode originalNode) {
        if(originalNode == null) {
            throw new IllegalArgumentException();
        }
        _originalNode = originalNode;
    }    

    public SpecificationNode getOriginalNode() {
        return _originalNode;
    }
    
	public void setInjectedNode(SpecificationNode injectedNode) {
		_injectedNode = injectedNode;
	}
    
    public SpecificationNode getInjectedNode() {
		return _injectedNode;
	}

    // support class ---------------------------------------------------
    
    private class ScopeIterator implements Iterator {

        private Iterator _it;
        private String _current;
        
        public ScopeIterator(Iterator it) {
            if(it == null) {
                throw new IllegalArgumentException();
            }
            _it = it;
        }
        
        public boolean hasNext() {
            return SCOPE_APPLICATION.equals(_current) == false; 
        }

        public Object next() {
            AttributeScope scope = null;
            if(_current == null) {
                if(_page != null) {
                    scope = _page;
                    _current = SCOPE_PAGE;
                } else if(_it.hasNext()) {
                    scope = (AttributeScope)_it.next();
                    _current = scope.getScopeName();
                }
            } else if(SCOPE_REQUEST.equals(_current)) {
                scope = getSession();
                _current = SCOPE_SESSION;
            } else if(SCOPE_SESSION.equals(_current)) {
                scope = getApplication();
                _current = SCOPE_APPLICATION;
            } else if(SCOPE_APPLICATION.equals(_current)) {
                throw new NoSuchElementException();
            } else {
                if(_it.hasNext()) {
                    scope = (AttributeScope)_it.next();
                    _current = scope.getScopeName();
                } else {
                    scope = getRequest();
                    _current = SCOPE_REQUEST;
                }
            }
            return scope;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    // static util methods ----------------------------------------------

    public static ServiceCycle getServiceCycle() {
        ServiceProvider provider = ProviderFactory.getServiceProvider();
        return provider.getServiceCycle();
    }

    public static AttributeScope findAttributeScope(String name) {
        if(StringUtil.isEmpty(name)) {
            return null;
        }
        for(Iterator it = getServiceCycle().iterateAttributeScope(); it.hasNext(); ) {
            AttributeScope scope = (AttributeScope)it.next();
            if(scope.hasAttribute(name)) {
                return scope;
            }
        }
        return null;
    }
    
    public static Object getAttribute(String name, String scopeName) {
        ServiceCycle cycle = getServiceCycle();
        AttributeScope scope = cycle.getAttributeScope(scopeName);
        return scope.getAttribute(name);
    }

    public static void setAttribute(String name, Object value, String scopeName) {
        ServiceCycle cycle = getServiceCycle();
        AttributeScope scope = cycle.getAttributeScope(scopeName);
        if(scope.isAttributeWritable()) {
            scope.setAttribute(name, value);
        } else {
            throw new ScopeNotWritableException(scopeName);
        }
    }

    public static void removeAttribute(String name, String scopeName) {
        ServiceCycle cycle = getServiceCycle();
    	AttributeScope scope = cycle.getAttributeScope(scopeName);
        if(scope.isAttributeWritable()) {
            scope.removeAttribute(name);
        } else {
            throw new ScopeNotWritableException(scopeName);
        }
    }
    
}
