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
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * @author Hisayoshi Sasaki (Gluegent, Inc.)
 */
public class DynamicAttributeNotSupportTag extends TagSupport {

	private static final long serialVersionUID = -4372307022820644857L;

	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print("not support");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
}
