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
package org.seasar.mayaa.impl.source;

import java.io.InputStream;
import java.util.Date;

import org.seasar.mayaa.source.SourceDescriptor;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class DelaySourceDescriptor implements SourceDescriptor {

    private SourceDescriptor _source;
    private String _systemID = "";

    public boolean exists() {
        if (_source == null) {
            _source = SourceUtil.getSourceDescriptor(getSystemID());
        }
        return _source.exists();
    }

    public InputStream getInputStream() {
        if (exists()) {
            return _source.getInputStream();
        }
        return null;
    }

    public Date getTimestamp() {
        if (exists()) {
            return _source.getTimestamp();
        }
        return new Date(0);
    }

    @Override
    public void setSystemID(String systemID) {
        _systemID = systemID;
    }

    @Override
    public String getSystemID() {
        return _systemID;
    }

}
