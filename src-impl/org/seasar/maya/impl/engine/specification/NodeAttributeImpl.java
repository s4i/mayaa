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
package org.seasar.maya.impl.engine.specification;

import java.util.Iterator;

import org.seasar.maya.engine.specification.NodeAttribute;
import org.seasar.maya.engine.specification.QName;
import org.seasar.maya.engine.specification.SpecificationNode;

/**
 * NodeAttribute�̎����N���X�B
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class NodeAttributeImpl extends QNameableImpl implements NodeAttribute {
    
    private SpecificationNode _node;
    private String _value;

    /**
     * @param qName ������QName�B
     * @param value �����̒l�B�󔒕�����͂悢�B
     */
	public NodeAttributeImpl(QName qName, String value) {
	    super(qName);
        if(value == null) {
            throw new IllegalArgumentException();
        }
        _value = value;
    }
	
	public void setNode(SpecificationNode node) {
	    if(node == null) {
	        throw new IllegalArgumentException();
	    }
	    _node = node;
	}
	
    public SpecificationNode getNode() {
        return _node;
    }

    public String getValue() {
        return _value;
    }

    public String toString() {
        return getQName().toString() + "='" + _value + "'";
    }

    public Iterator iterateNamespace() {
        if(_node != null) {
            _node.iterateNamespace();
        }
        return super.iterateNamespace();
    }
    
}