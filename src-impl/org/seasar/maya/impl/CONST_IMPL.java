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
package org.seasar.maya.impl;

import org.seasar.maya.engine.specification.QName;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface CONST_IMPL {

    String CHECK_TIMESTAMP = "checkTimestamp";
    String OUTPUT_WHITE_SPACE = "outputWhitespace";
    String REPORT_UNRESOLVED_ID = "reportUnresolvedID";
    String SUFFIX_SEPARATOR = "suffixSeparator";
    String WELCOME_FILE_NAME = "welcomeFileName";
    
    String URI_MAYA = "http://maya.seasar.org";
    String URI_HTML = "http://www.w3.org/TR/html4";
    String URI_XHTML = "http://www.w3.org/1999/xhtml";

    String ATTR_ID = "id";
    String ATTR_INJECT = "inject";

    String PUBLIC_MLD10 = "-//The Seasar Foundation//DTD Maya Library Definition 1.0//EN";
    String PUBLIC_PROVIDER10 = "-//The Seasar Foundation//DTD Maya Provider 1.0//EN";
    
    QName QH_HTML = new QName(URI_HTML, "html");
    QName QH_META = new QName(URI_HTML, "meta");
    QName QH_ID = new QName(URI_HTML, ATTR_ID);
    QName QH_CONTENT = new QName(URI_HTML, "content");
    QName QH_HTTP_EQUIV = new QName(URI_HTML, "http-equiv");    
    
    QName QX_HTML = new QName(URI_XHTML, "html");
    QName QX_META = new QName(URI_XHTML, "meta");
    QName QX_ID = new QName(URI_XHTML, ATTR_ID);
    QName QX_CONTENT = new QName(URI_XHTML, "content");
    QName QX_HTTP_EQUIV = new QName(URI_XHTML, "http-equiv");    

    QName QM_ENGINE = new QName("engine");
	QName QM_PAGE = new QName("page");
	QName QM_TEMPLATE = new QName("template");
    
	QName QM_MAYA = new QName("maya");
	QName QM_CONTENT_TYPE = new QName("contentType");
    QName QM_INJECT = new QName(ATTR_INJECT);
    QName QM_ID = new QName("id");
    QName QM_XPATH = new QName("xpath");
    QName QM_RENDERED = new QName("rendered");
    QName QM_ELEMENT = new QName("element");
    QName QM_NAME = new QName("name");
    QName QM_TEMPLATE_ELEMENT = new QName("templateElement");
    QName QM_DUPLECATED_ELEMENT = new QName("duplecatedElement");
    QName QM_ATTRIBUTE = new QName("attribute");
    QName QM_CHARACTERS = new QName("characters");
    QName QM_TEXT = new QName("text");    
    QName QM_PROCESSING_INSTRUCTION = new QName("processingInstruction");
    QName QM_TARGET = new QName("target");
    QName QM_DATA = new QName("data");
    QName QM_COMMENT = new QName("comment");
    QName QM_CDATA = new QName("cdata");
    QName QM_DOCTYPE = new QName("doctype");
    QName QM_PUBLIC_ID = new QName("publicID");
    QName QM_SYSTEM_ID = new QName("systemID");
    
    QName QM_IGNORE = new QName("ignore");
    
	QName QM_BEFORE_RENDER = new QName("beforeRender");
	QName QM_AFTER_RENDER = new QName("afterRender");

    QName QM_CLASS = new QName("class");
    QName QM_SCOPE = new QName("scope");

    QName QM_TEMPLATE_SUFFIX = new QName("templateSuffix");

    QName QM_PATH = new QName("path");
    
}
