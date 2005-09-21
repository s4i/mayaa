/*
 * Copyright (c) 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.maya.impl.engine.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.maya.engine.processor.ProcessorProperty;
import org.seasar.maya.engine.specification.NodeAttribute;
import org.seasar.maya.engine.specification.QNameable;
import org.seasar.maya.engine.specification.SpecificationNode;
import org.seasar.maya.impl.CONST_IMPL;

/**
 * @author Koji Suga (Gluegent, Inc.)
 */
public class EchoProcessor extends ElementProcessor implements CONST_IMPL {

    private static final long serialVersionUID = 3924111635172574833L;

    public void setOriginalNode(SpecificationNode node) {
        super.setOriginalNode(node);
        setupElement(node);
    }

    private void setupElement(SpecificationNode node) {
        super.setName(node);
        for (Iterator it = node.iterateAttribute(); it.hasNext();) {
            NodeAttribute attribute = (NodeAttribute) it.next();
            ProcessorPropertyImpl property =
                new ProcessorPropertyImpl(
                        attribute, attribute.getValue(), String.class);
            super.addInformalProperty(property);
        }
    }

    // MLD property of ElementProcessor
    public void setName(QNameable name) {
        // doNothing
    }

    // MLD method of ElementProcessor
    public void addInformalProperty(ProcessorProperty attr) {
        // doNothing
    }

    // ProcessorTreeWalker implements --------------------------------
    public Map getVariables() {
        if (getInformalProperties() != null) {
            Map attributeMap = new HashMap();
            for (Iterator it = getInformalProperties().iterator(); it.hasNext();) {
                ProcessorProperty prop = (ProcessorProperty) it.next();
                attributeMap.put(
                        prop.getName().getQName().getLocalName(),
                        prop.getValue().execute());
            }
            return attributeMap;
        }
        return null;
    }
}
