/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
package org.seasar.mayaa.impl.builder.injection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.mayaa.builder.injection.InjectionChain;
import org.seasar.mayaa.builder.injection.InjectionResolver;
import org.seasar.mayaa.engine.specification.QName;
import org.seasar.mayaa.engine.specification.PrefixAwareName;
import org.seasar.mayaa.engine.specification.SpecificationNode;
import org.seasar.mayaa.engine.specification.URI;
import org.seasar.mayaa.impl.CONST_IMPL;
import org.seasar.mayaa.impl.NonSerializableParameterAwareImpl;
import org.seasar.mayaa.impl.builder.BuilderUtil;
import org.seasar.mayaa.impl.engine.specification.SpecificationUtil;
import org.seasar.mayaa.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class InjectAttributeInjectionResolver extends NonSerializableParameterAwareImpl
        implements InjectionResolver {

    private static final Log LOG =
        LogFactory.getLog(InjectAttributeInjectionResolver.class);

    public SpecificationNode getNode(
            SpecificationNode original, InjectionChain chain) {
        if (original == null || chain == null) {
            throw new IllegalArgumentException();
        }
        String injectName =
            SpecificationUtil.getAttributeValue(original, CONST_IMPL.QM_INJECT);
        if (StringUtil.hasValue(injectName)) {
            PrefixAwareName prefixAwareName =
                BuilderUtil.parseName(original, injectName);
            QName qName = prefixAwareName.getQName();
            if (CONST_IMPL.QM_IGNORE.equals(qName) == false) {
                URI uri = qName.getNamespaceURI();
                if (CONST_IMPL.URI_HTML.equals(uri)
                        || CONST_IMPL.URI_XHTML.equals(uri) || CONST_IMPL.URI_XML.equals(uri)) {
                    LOG.error("inject=\""+ injectName +"\" is not processor");
                }
                return BuilderUtil.createInjectedNode(
                        qName, uri, original, true);
            }
        }
        return chain.getNode(original);
    }

}
