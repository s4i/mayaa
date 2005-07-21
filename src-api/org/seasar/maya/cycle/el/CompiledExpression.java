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
package org.seasar.maya.cycle.el;

import java.io.Serializable;

import org.seasar.maya.cycle.ServiceCycle;

/**
 * �R���p�C���ς݂̎�����I�u�W�F�N�g�B
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface CompiledExpression extends Serializable {

    /**
     * �������s���Ēl���擾����B
     * @param cycle �T�[�r�X�T�C�N���R���e�L�X�g�B
     * @return ���s���ʂ̒l�B
     */
    Object getValue(ServiceCycle cycle);
    
    /**
     * ���ɂ��A�l��ݒ肷��
     * @param cycle �T�[�r�X�T�C�N���R���e�L�X�g�B
     * @param value �ݒ肷��l�B
     */
    void setValue(ServiceCycle cycle, Object value);

    /**
     * ���̎擾�B
     * @return ��������B
     */
    String getExpression();
    
    /**
     * �����s���ʂւ̊��Ҍ^�B
     * @return ���҂����N���X�^�B
     */
    Class getExpectedType();
 
    /**
     * ���e�����e�L�X�g���ǂ����B
     * @return �R���p�C�����ʂ��A���e������������true�B
     */
    boolean isLiteralText();
    
}