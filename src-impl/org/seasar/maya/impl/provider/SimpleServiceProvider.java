/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
 * 
 * Licensed under the Seasar Software License, v1.1 (aka "the License"); you may
 * not use this file except in compliance with the License which accompanies
 * this distribution, and is available at
 * 
 *     http://www.seasar.org/SEASAR-LICENSE.TXT
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.seasar.maya.impl.provider;

import javax.servlet.ServletContext;

import org.seasar.maya.builder.SpecificationBuilder;
import org.seasar.maya.builder.TemplateBuilder;
import org.seasar.maya.el.ExpressionFactory;
import org.seasar.maya.engine.Engine;
import org.seasar.maya.impl.CONST_IMPL;
import org.seasar.maya.provider.ServiceProvider;
import org.seasar.maya.source.factory.SourceFactory;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class SimpleServiceProvider implements ServiceProvider, CONST_IMPL {
    
    private ServletContext _servletContext;
    private Engine _engine;
    private SourceFactory _sourceFactory;
    private ExpressionFactory _expressionFactory;
    private SpecificationBuilder _specificationBuilder;
    private TemplateBuilder _templateBuilder;
	
    public SimpleServiceProvider(ServletContext servletContext) {
        if(servletContext == null) {
            throw new IllegalArgumentException();
        }
        _servletContext = servletContext;
    }
    
    public ServletContext getServletContext() {
        return _servletContext;
    }
    
    public boolean hasSourceFactory() {
        return _sourceFactory != null;
    }
    
    public void setSourceFactory(SourceFactory sourceFactory) {
        if(sourceFactory == null) {
            throw new IllegalArgumentException();
        }
        _sourceFactory = sourceFactory;
    }
    
    public SourceFactory getSourceFactory() {
        if(_sourceFactory == null) {
            throw new IllegalStateException();
        }
    	return _sourceFactory;
    }
    
    public boolean hasEngine() {
        return _engine != null;
    }
    
    public void setEngine(Engine engine) {
        if(engine == null) {
            throw new IllegalArgumentException();
        }
        _engine = engine;
    }
    
    public Engine getEngine() {
    	if(_engine == null) {
    	    throw new IllegalStateException();
    	}
        return _engine;
    }
    
    public boolean hasExpressionFactory() {
        return _expressionFactory != null;
    }
    
    public void setExpressionFactory(ExpressionFactory expressionFactory) {
        if(expressionFactory == null) {
            throw new IllegalArgumentException();
        }
        _expressionFactory = expressionFactory;
    }
    
    public ExpressionFactory getExpressionFactory() {
        if(_expressionFactory == null) {
            throw new IllegalStateException();
        }
        return _expressionFactory;
    }
    
    public boolean hasSpecificationBuilder() {
        return _specificationBuilder != null;
    }
    
    public void setSpecificationBuilder(SpecificationBuilder specificationBuilder) {
        if(specificationBuilder == null) {
            throw new IllegalArgumentException();
        }
        _specificationBuilder = specificationBuilder;
    }
    
    public SpecificationBuilder getSpecificationBuilder() {
    	if(_specificationBuilder == null) {
            throw new IllegalStateException();
    	}
        return _specificationBuilder;
    }
    
    public boolean hasTemplateBuilder() {
        return _templateBuilder != null;
    }
    
    public void setTemplateBuilder(TemplateBuilder templateBuilder) {
        if(templateBuilder == null) {
            throw new IllegalArgumentException();
        }
        _templateBuilder = templateBuilder;
    }
    
    public TemplateBuilder getTemplateBuilder() {
    	if(_templateBuilder == null) {
    	    throw new IllegalStateException();
    	}
        return _templateBuilder;
    }

}
