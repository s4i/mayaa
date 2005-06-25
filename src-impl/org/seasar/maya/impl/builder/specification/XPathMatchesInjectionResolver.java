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
package org.seasar.maya.impl.builder.specification;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.maya.builder.specification.InjectionChain;
import org.seasar.maya.builder.specification.InjectionResolver;
import org.seasar.maya.engine.Template;
import org.seasar.maya.engine.specification.CopyToFilter;
import org.seasar.maya.engine.specification.NodeNamespace;
import org.seasar.maya.engine.specification.NodeObject;
import org.seasar.maya.engine.specification.SpecificationNode;
import org.seasar.maya.impl.CONST_IMPL;
import org.seasar.maya.impl.util.SpecificationUtil;
import org.seasar.maya.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class XPathMatchesInjectionResolver implements InjectionResolver, CONST_IMPL {

    private final CheckXPathCopyToFilter _xpathFilter = new CheckXPathCopyToFilter(); 
    
	private Map getNamespaceMap(SpecificationNode node) {
		Map namespaces  = new HashMap();
        for(Iterator nsit = node.iterateNamespace(); nsit.hasNext(); ) {
        	NodeNamespace ns = (NodeNamespace)nsit.next();
        	namespaces.put(ns.getPrefix(), ns.getNamespaceURI());
        }
        return namespaces;
	}
	
    public SpecificationNode getNode(
            Template template, SpecificationNode original, InjectionChain chain) {
        if(template == null || original == null || chain == null) {
            throw new IllegalArgumentException();
        }
        Map namespaces = new HashMap();
        namespaces.put("m", URI_MAYA);
        String xpathExpr = "/m:maya//*[string-length(@m:xpath) > 0]";
        for(Iterator it = template.selectChildNodes(xpathExpr, namespaces, true); it.hasNext(); ) {
            SpecificationNode injected = (SpecificationNode)it.next();
            String mayaPath = SpecificationUtil.getAttributeValue(injected, QM_XPATH);
            if(original.matches(mayaPath, getNamespaceMap(injected))) {
                if(QM_IGNORE.equals(injected)) {
                    return chain.getNode(template, original);
                }
                return injected.copyTo(_xpathFilter);
            }
        }
        return chain.getNode(template, original);
    }
    
    public void putParameter(String name, String value) {
        throw new UnsupportedOperationException();
    }
    
    private class CheckXPathCopyToFilter implements CopyToFilter {
        
        public boolean accept(NodeObject test) {
            if(test instanceof SpecificationNode) {
                SpecificationNode node = (SpecificationNode)test;
                String xpath = SpecificationUtil.getAttributeValue(node, QM_XPATH);
                return StringUtil.isEmpty(xpath);
            }
            return true;
        }
        
    }
    
}