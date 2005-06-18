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
package org.seasar.maya.engine.processor;

import javax.servlet.jsp.PageContext;

/**
 * TemplateProcessor�̊g���C���^�[�t�F�C�X�B�����̃C�e���[�g�@�\�B
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface IterationProcessor extends TemplateProcessor {

    /**
     * �C�e���[�g���s���邩�ǂ�����Ԃ��BJSP��IterationTag��BodyTag���z�X�g
     * ���Ă���ꍇ�ɗ��p����B�f�t�H���g�ł�false��Ԃ��Btrue���ƁA�q�v���Z�b�T
     * �̎��s���doAfterChildProcess()���\�b�h���R���e�i���Ăяo�����B
     * @param context �v���Z�X���̃X�e�[�g�t���ȏ���ێ�����R���e�L�X�g�B
     * @return �C�e���[�g���s����ꍇ�Atrue�B���ʂ�false�B
     */
    boolean isIteration(PageContext context);

    /**
     * �C�e���[�g���s����ꍇ�A�q�v���Z�b�T�̎��s��ɃR���e�i���Ăяo�����B
     * @param context �v���Z�X���̃X�e�[�g�t���ȏ���ێ�����R���e�L�X�g�B
     * @return javax.servlet.jsp.tagext.IterationTag#doAfterBody()�̕Ԓl�Ɠ����d�l�B
     */
    int doAfterChildProcess(PageContext context);

}