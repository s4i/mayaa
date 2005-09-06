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
package org.seasar.maya.impl.cycle.scope;

import java.util.Iterator;

import org.seasar.maya.cycle.AttributeScope;
import org.seasar.maya.impl.cycle.AbstractReadOnlyAttributeScope;
import org.seasar.maya.impl.util.CycleUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc)
 */
public class HeaderScope extends AbstractReadOnlyAttributeScope {
	
	public String getScopeName() {
		return "header";
	}

    public Iterator iterateAttributeNames() {
        AttributeScope values = CycleUtil.getRequest().getHeaderValues();
        return values.iterateAttributeNames();
    }

    public boolean hasAttribute(String name) {
        AttributeScope values = CycleUtil.getRequest().getHeaderValues();
        return values.hasAttribute(name);
    }

    public Object getAttribute(String name) {
        if(hasAttribute(name)) {
            AttributeScope values = CycleUtil.getRequest().getHeaderValues();
            String[] headers = (String[])values.getAttribute(name);
            if(headers.length == 0) {
                return "";
            }
            return headers[0];
        }
        return null;
    }

}