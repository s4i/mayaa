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
package org.seasar.maya.impl.provider.factory;

import org.seasar.maya.ParameterAware;
import org.seasar.maya.builder.library.LibraryManager;
import org.seasar.maya.impl.MarshallUtil;
import org.seasar.maya.impl.util.XMLUtil;
import org.seasar.maya.provider.ServiceProvider;
import org.xml.sax.Attributes;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class LibraryManagerTagHandler
		extends AbstractParameterAwareTagHandler {
    
    private ProviderTagHandler _parent;
    private LibraryManager _beforeManager;
    private LibraryManager _currentManager;
    
    public LibraryManagerTagHandler(
            ProviderTagHandler parent, ServiceProvider beforeProvider) {
        super("libraryManager");
        if(parent == null) {
            throw new IllegalArgumentException();
        }
        _parent = parent;
        if(beforeProvider != null) {
            _beforeManager = beforeProvider.getLibraryManager();
        }
        putHandler(new ConverterTagHandler(this));
        putHandler(new SourceTagHandler(this));
        putHandler(new BuilderTagHandler(this));
    }
    
    protected void start(
    		Attributes attributes, String systemID, int lineNumber) {
        Class managerClass = XMLUtil.getClassValue(
                attributes, "class", null);
    	_currentManager = (LibraryManager)MarshallUtil.marshall(
                managerClass, LibraryManager.class, _beforeManager);
        _parent.getServiceProvider().setLibraryManager(_currentManager);
    }
    
    protected void end(String body) {
        _currentManager = null;
    }
    
    public LibraryManager getLibraryManager() {
        if(_currentManager == null) {
            throw new IllegalStateException();
        }
        return _currentManager;
    }
    
    public ParameterAware getParameterAware() {
        return getLibraryManager();
    }

}
