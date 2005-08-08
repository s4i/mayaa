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
package org.seasar.maya.component.engine.processor;

import org.seasar.maya.engine.processor.TemplateProcessor;
import org.seasar.maya.impl.engine.processor.DoBodyProcessor;

/**
 * 「p:endComponent」プロセッサ。他のテンプレートに埋め込まれている
 * 時には、所属テンプレートのルートとなるp:componentPageの子を描画。
 * 所属テンプレートがダイレクトに利用されている場合には、自分の子を描画する。
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class EndComponentProcessor extends DoBodyProcessor {
    
	private static final long serialVersionUID = -6288170474466334557L;

	/**
     * 親をさかのぼって、p:componentPageを探す。
     * @return 見つかったp:componentPageもしくはnull。
     */
    protected ComponentPageProcessor findComponentPage() {
        for(TemplateProcessor current = this;
        		current != null; current = current.getParentProcessor()) {
            if(current instanceof ComponentPageProcessor) {
                return (ComponentPageProcessor)current;
            }
        }
        return null;
    }
    
    public ProcessStatus doStartProcess() {
    	ComponentPageProcessor componentPage = findComponentPage();
        if(componentPage != null) {
            return componentPage.renderChildren();
        }
        return EVAL_BODY_INCLUDE;
    }

}
