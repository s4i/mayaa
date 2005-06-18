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
package org.seasar.maya.builder.library;

import java.util.Iterator;

import org.seasar.maya.builder.library.scanner.LibraryScanner;
import org.seasar.maya.engine.specification.QName;
import org.seasar.maya.provider.Parameterizable;

/**
 * MLD���f���I�u�W�F�N�g�̃��[�g�B
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface LibraryManager extends Parameterizable {
    
    /**
     * ���[�g�̃��C�u�����X�L���i�̎擾�B
     * @return ���C�u�����X�L���i�B
     */
    LibraryScanner getLibraryScanner();
    
    /**
     * ���C�u�����̒ǉ����s���B
     * @param library ���C�u�����B
     */
    void addLibraryDefinition(LibraryDefinition library);    
    
    /**
     * �SMLD�ݒ�i=<code>LibraryDefinition</code>�j�̃C�e���[�^�擾�B
     * @return MLD�ݒ�̃C�e���[�^�B
     */
    Iterator iterateLibraryDefinition();
    
    /**
     * �w��URI�ŊY������MLD�ݒ�̃C�e���[�^�擾�B
     * @param namespaceURI �擾������MLD�̖��O���URI�B
     * @return MLD�ݒ�̃C�e���[�^�B
     */
    Iterator iterateLibraryDefinition(String namespaceURI);
    
    /**
     * QName�ŊY������v���Z�b�T��`�i=<code>ProcessorDefinition</code>�j����������B
     * ��Ԃ͂��߂Ɍ����������̂�Ԃ��B
     * @param qName �擾�������v���Z�b�T��`�̎w��QName�B
     * @return �w��QName�̃v���Z�b�T��`�B������Ȃ��ꍇ��null�B
     */
    ProcessorDefinition getProcessorDefinition(QName qName);
    
}