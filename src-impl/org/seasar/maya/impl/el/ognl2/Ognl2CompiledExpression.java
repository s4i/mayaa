/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
 * 
 * Licensed under the Seasar Software License, v1.1 (aka "the License");
 * you may not use this file except in compliance with the License which 
 * accompanies this distribution, and is available at
 * 
 *     http://homepage3.nifty.com/seasar/SEASAR-LICENSE.TXT
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package org.seasar.maya.impl.el.ognl2;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import ognl.Ognl;
import ognl.OgnlException;

import org.seasar.maya.impl.el.AbstractCompiledExpression;
import org.seasar.maya.impl.el.ConversionException;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class Ognl2CompiledExpression 
		extends AbstractCompiledExpression {
    
    public static final String PAGE_CONTEXT = "pageContext";
    public static final Object ROOT = new Object();

    private Object _exp;
    
    public Ognl2CompiledExpression(Object exp, String expression, Class expectedType) {
        super(expression, expectedType);
        if(exp == null) {
            throw new IllegalArgumentException();
        }
        _exp = exp;
    }
    
    public Object getValue(PageContext context) {
        Object ret;
        Class expectedType = getExpectedType();
        try {
            Map map = new HashMap();
            map.put(PAGE_CONTEXT, context);
            ret = Ognl.getValue(_exp, map, ROOT);
        } catch(OgnlException e) {
            throw new IllegalStateException(e.getMessage());
        }
        if(expectedType == Void.class || ret == null) {
            return null;
        } else if(expectedType == String.class) {
            return ret.toString();
        } else if(expectedType.isAssignableFrom(ret.getClass())) {
            return ret;
        }
        throw new ConversionException(expectedType, getExpression());
    }
    
    public void setValue(PageContext context, Object value) {
        Class expectedType = getExpectedType();
        if(expectedType == Void.class || 
                expectedType.isAssignableFrom(value.getClass()) == false) {
            throw new ConversionException(expectedType, getExpression());
        }
        try {
            Map map = new HashMap();
            map.put(PAGE_CONTEXT, context);
            Ognl.setValue(_exp,  map, ROOT, value);
        } catch(OgnlException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

}