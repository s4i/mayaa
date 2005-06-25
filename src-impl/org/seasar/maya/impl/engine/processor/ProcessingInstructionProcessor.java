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
package org.seasar.maya.impl.engine.processor;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.maya.engine.processor.TemplateProcessorSupport;
import org.seasar.maya.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ProcessingInstructionProcessor extends TemplateProcessorSupport {
    
    private String _target;
    private String _data;
    
    public void setTarget(String target) {
        if(StringUtil.isEmpty(target)) {
        	throw new IllegalArgumentException();
        }
        _target = target;
    }
    
    public String getTarget(){
        return _target;
    }

    public void setData(String data) {
        _data = data;
    }
    
    public String getData() {
        return _data;
    }
    
    public int doStartProcess(PageContext context) {
    	if(context == null) {
    		throw new IllegalArgumentException();
    	}
        Writer out = context.getOut();
        try {
	        StringBuffer processingInstruction = new StringBuffer(128);
	        processingInstruction.append("<?").append(_target);
	        if(StringUtil.hasValue(_data)) {
	            processingInstruction.append(" ").append(_data);
	        }
	        processingInstruction.append("?>");
            out.write(processingInstruction.toString());
        } catch(IOException e) {
        	throw new RuntimeException(e);
        }
        return Tag.SKIP_BODY;
    }

}