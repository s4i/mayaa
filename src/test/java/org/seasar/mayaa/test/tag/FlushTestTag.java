/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.mayaa.test.tag;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Koji Suga (Gluegent, Inc.)
 */
public class FlushTestTag extends BodyTagSupport {

    private static final long serialVersionUID = 9192585525665925369L;

    public int doStartTag() throws JspException {
        pageContext.setAttribute("foo", "bar", PageContext.PAGE_SCOPE);
        try {
			pageContext.getOut().flush();
		} catch (IOException e) {
			throw new RuntimeException("pageContext.getOut().flush()", e);
		}
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
