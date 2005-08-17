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
package org.seasar.maya.impl.builder.library;

import java.io.InputStream;

import org.seasar.maya.builder.library.LibraryDefinition;
import org.seasar.maya.builder.library.DefinitionBuilder;
import org.seasar.maya.impl.CONST_IMPL;
import org.seasar.maya.impl.builder.library.mld.MLDHandler;
import org.seasar.maya.impl.util.StringUtil;
import org.seasar.maya.impl.util.XmlUtil;
import org.seasar.maya.source.SourceDescriptor;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class MLDLibraryDefinitionBuilder implements DefinitionBuilder, CONST_IMPL {

    public void putParameter(String name, String value) {
        throw new UnsupportedOperationException();
    }

    private boolean isMLD(String path) {
        if(StringUtil.isEmpty(path)) {
            throw new IllegalArgumentException();
        }
        return path.toLowerCase().endsWith(".mld");
    }
    
    public LibraryDefinition build(SourceDescriptor source) {
        if(source == null) {
            throw new IllegalArgumentException();
        }
        if(source.exists() && isMLD(source.getPath())) {
            MLDHandler handler = new MLDHandler();
            InputStream stream = source.getInputStream();
            String systemID = source.getSystemID();
            XmlUtil.parse(handler, stream, PUBLIC_MLD10, systemID, true, true, false);
            return handler.getLibraryDefinition();
        }
        return null;
    }
    
}
