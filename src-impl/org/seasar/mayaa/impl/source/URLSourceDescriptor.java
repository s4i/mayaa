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
import java.net.URL;
import java.util.Date;

import org.seasar.mayaa.impl.util.IOUtil;
import org.seasar.mayaa.source.SourceDescriptor;

/**
 * URLを基準とするSourceDescriptor。
 * URLのConnectionではキャッシュを使わないため、頻繁に使う場合は注意が必要。
 *
 * @author Masataka Kurihara (Gluegent, Inc.)
 * @author Koji Suga (Gluegent Inc.)
 */
public class URLSourceDescriptor implements SourceDescriptor {

    private URL _url;
    private Date _timestamp;
    private String _systemID = "";

    public void setURL(URL url) {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        _url = url;
        _timestamp = new Date(IOUtil.getLastModified(_url));
    }

    public boolean exists() {
        return true;
    }

    public InputStream getInputStream() {
        return IOUtil.openStream(_url);
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException();
        }
        _timestamp = timestamp;
    }

    public Date getTimestamp() {
        if (_timestamp != null) {
            return _timestamp;
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
