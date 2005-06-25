/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
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
package org.seasar.maya.impl.builder;

/**
 * �w��URL�ɑ΂���y�[�W��e���v���[�g��������Ȃ��Ƃ��̗�O�B
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class PageNotFoundException extends RuntimeException {

	private String _pageName;
    
    /**
	 * @param pageName �y�[�W���������́A���ߍ��݃y�[�W���B
	 */
	public PageNotFoundException(String pageName) {
	    super(pageName);
	    _pageName = pageName;
    }

	/**
	 * �y�[�W�����擾����B
	 * @return ���̂���y�[�W�̖��O�B
	 */
	public String getPageName() {
		return _pageName;
	}
	
}