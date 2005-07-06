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
package org.seasar.maya.impl.cycle.web;

import javax.servlet.ServletContext;

import org.seasar.maya.cycle.Application;
import org.seasar.maya.cycle.ServiceCycle;
import org.seasar.maya.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class WebApplication implements Application {

    private ServletContext _servletContext;

    private void check() {
        if(_servletContext == null) {
            throw new IllegalStateException();
        }
    }

    public Object getUnderlyingObject() {
        check();
        return _servletContext;
    }
    
    public void setServletContext(ServletContext servletContext) {
        if(servletContext == null) {
            throw new IllegalArgumentException();
        }
        _servletContext = servletContext;
    }
    
    public String getScopeName() {
        return ServiceCycle.SCOPE_APPLICATION;
    }
    
    public Object getAttribute(String name) {
        check();
        if(StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        return _servletContext.getAttribute(name);
    }

    public void setAttribute(String name, Object attribute) {
        check();
        if(StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        if(attribute != null) {
            _servletContext.setAttribute(name, attribute);
        } else {
            _servletContext.removeAttribute(name);
        }
    }
    
}