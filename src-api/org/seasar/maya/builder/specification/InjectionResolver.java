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
package org.seasar.maya.builder.specification;

import org.seasar.maya.engine.Template;
import org.seasar.maya.engine.specification.SpecificationNode;
import org.seasar.maya.provider.Parameterizable;

/**
 * �e���v���[�g�ɋL�q���ꂽHTML�^�O�ɁA�ǉ��I�ȏ���ێ�����m�[�h��
 * �C���W�F�N�V�������郌�]���o�B���̃C���^�[�t�F�C�X���������ăG���W���̋���
 * ���J�X�^�}�C�Y���邱�Ƃ��ł���B
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface InjectionResolver extends Parameterizable {

    /**
     * �e���v���[�g��̃I���W�i���ȃm�[�h�ɃC���W�F�N�V��������m�[�h�����肷��B
     * @param template �e���v���[�g�B
     * @param original �e���v���[�g��̃I���W�i���ȃm�[�h�B
     * @param chain ���̃��]���o�֏������Ϗ�����`�F�[���B
     * @return �C���W�F�N�V��������m�[�h��������null�B
     */
    SpecificationNode getNode(
            Template template, SpecificationNode original, InjectionChain chain);
    
}