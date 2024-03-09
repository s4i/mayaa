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
package org.seasar.mayaa.impl.cycle.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRegistration.Dynamic;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;

import org.seasar.mayaa.impl.util.IOUtil;

/**
 * Webコンテナ外で動作させるためのServletContextのモック。
 * コンストラクタでコンテキストパス、コンテキストルートのパスを渡す。
 *
 * @author Koji Suga (Gluegent Inc.)
 */
public class MockServletContext implements ServletContext {

    private String _contextPath;
    private String _contextRoot;
    private Map<String, Object> _attributes = new HashMap<>(10);

    /**
     * コンテキストパスとコンテキストルートを引数に取るコンストラクタ。
     * コンテキストパスはパスの自動解決やrequest.contextPathで利用されます。
     * コンテキストルートはディレクトリパスをフルパスで指定してください。
     *
     * @param basePath コンテキストルート
     * @param contextPath コンテキストパス
     */
    public MockServletContext(String basePath, String contextPath) {
        _contextRoot = basePath;
        _contextPath = contextPath;
    }

    /* (non-Javadoc)
     * @see jakarta.servlet.ServletContext#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        return _attributes.get(name);
    }

    /* (non-Javadoc)
     * @see jakarta.servlet.ServletContext#getAttributeNames()
     */
    public Enumeration<String> getAttributeNames() {
        final Iterator<String> keyIterator = _attributes.keySet().iterator();
        return new Enumeration<String>() {
            public boolean hasMoreElements() {
                return keyIterator.hasNext();
            }

            public String nextElement() {
                return keyIterator.next();
            }
        };
    }

    /* (non-Javadoc)
     * @see jakarta.servlet.ServletContext#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {
        if (_attributes.containsKey(name)) {
            _attributes.remove(name);
        }
    }

    /* (non-Javadoc)
     * @see jakarta.servlet.ServletContext#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object object) {
        _attributes.put(name, object);
    }

    /**
     * nullを返します。
     *
     * @param uripath 他Webアプリケーションのコンテキストパス
     * @return null
     * @see jakarta.servlet.ServletContext#getContext(java.lang.String)
     */
    public ServletContext getContext(String uripath) {
        return null;
    }

    /**
     * nullを返します。
     *
     * @param name 初期化パラメータ名
     * @return null
     * @see jakarta.servlet.ServletContext#getInitParameter(java.lang.String)
     */
    public String getInitParameter(String name) {
        return null;
    }

    /**
     * 要素を持たないEnumerationを返します。
     *
     * @return 要素を持たないEnumeration
     * @see jakarta.servlet.ServletContext#getInitParameterNames()
     */
    public Enumeration<String> getInitParameterNames() {
        return Collections.emptyEnumeration();
    }

    /**
     * Servlet API のメジャーバージョン番号として 2 を返します。
     *
     * @return 2
     * @see jakarta.servlet.ServletContext#getMajorVersion()
     */
    public int getMajorVersion() {
        return 2;
    }

    /**
     * Servlet API のマイナーバージョン番号として 3 を返します。
     *
     * @return 3
     * @see jakarta.servlet.ServletContext#getMinorVersion()
     */
    public int getMinorVersion() {
        return 3;
    }

    /**
     * {@link java.net.URLConnection#guessContentTypeFromName(java.lang.String)}
     * を使ってContentTypeを取得して返します。
     * ただし正確とは限りません。
     *
     * @param file MimeTypeを判定するファイル名
     * @return fileに対応するMimeType
     * @see jakarta.servlet.ServletContext#getMimeType(java.lang.String)
     */
    public String getMimeType(String file) {
        return URLConnection.guessContentTypeFromName(file);
    }

    /**
     * nullを返します。
     *
     * @see jakarta.servlet.ServletContext#getNamedDispatcher(java.lang.String)
     */
    public RequestDispatcher getNamedDispatcher(String name) {
        return null;
    }

    protected File getFile(String path) {
        if (path == null) {
            return null;
        }
        if (path.startsWith("/") == false) {
            path = "/" + path;
        }
        File file = new File(_contextRoot + path);
        try {
            return new File(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Illegal path : " + path);
        }
    }

    /**
     * pathをコンテキストルートからの相対パスと見なして、ファイルとしての
     * 絶対パスを返します。
     * セキュリティ的な考慮はしていないため、コンテキストルートより上の
     * 階層へ辿ることもできるので注意してください。
     *
     * @param path RealPathを取得するパス
     * @return ファイルとしての絶対パス
     * @see jakarta.servlet.ServletContext#getRealPath(java.lang.String)
     */
    public String getRealPath(String path) {
        File file = getFile(path);
        if (file != null && file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * コンテキストルートからファイルとして見つけられる場合はそのファイルの
     * URLを返します。見つけられない場合は現在スレッドのコンテキストクラスローダー
     * を使ってgetReasourceの結果を返します。
     *
     * @param path URLを取得するリソース名
     * @return pathに対応するURL。存在しない場合はnull
     * @see jakarta.servlet.ServletContext#getResource(java.lang.String)
     */
    public URL getResource(String path) throws MalformedURLException {
        File file = getFile(path);
        if (file != null && file.exists()) {
            return file.toURI().toURL();
        }
        return IOUtil.getResource(path);
    }

    /**
     * コンテキストルートからファイルとして見つけられる場合はそのファイルの
     * InputStreamを返します。見つけられない場合は現在スレッドの
     * コンテキストクラスローダーを使ってgetReasourceの結果URLをopenして返します。
     *
     * @param path URLを取得するリソース名
     * @return pathに対応するリソースのInputStream。存在しない場合はnull
     * @see jakarta.servlet.ServletContext#getResourceAsStream(java.lang.String)
     */
    public InputStream getResourceAsStream(String path) {
        try {
            return IOUtil.openStream(getResource(path));
        } catch (MalformedURLException e) {
            log(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 空のSetを返します。
     *
     * @param path ディレクトリのパス
     * @return 空のSet
     * @see jakarta.servlet.ServletContext#getResourcePaths(java.lang.String)
     */
    public Set<String> getResourcePaths(String path) {
        return Collections.emptySet();
    }

    /**
     * nullを返します。
     *
     * @param path 対象のパス
     * @return null
     * @see jakarta.servlet.ServletContext#getRequestDispatcher(java.lang.String)
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    /**
     * "Mayaa MockServletContext"という文字列を返します。
     *
     * @return "Mayaa MockServletContext"
     * @see jakarta.servlet.ServletContext#getServerInfo()
     */
    public String getServerInfo() {
        return "Mayaa MockServletContext";
    }

    /**
     * nullを返します。
     *
     * @param name 対象のServlet名
     * @return null
     * @see jakarta.servlet.ServletContext#getServlet(java.lang.String)
     * @deprecated
     */
    public Servlet getServlet(String name) {
        return null;
    }

    /**
     * contextPathを返します。
     *
     * @return contextPath
     * @see jakarta.servlet.ServletContext#getServletContextName()
     */
    public String getServletContextName() {
        return _contextPath;
    }

    /**
     * 要素を持たないEnumerationを返します。
     *
     * @return 要素を持たないEnumeration
     * @see jakarta.servlet.ServletContext#getServletNames()
     * @deprecated
     */
    public Enumeration<String> getServletNames() {
        return Collections.emptyEnumeration();
    }

    /**
     * 要素を持たないEnumerationを返します。
     *
     * @return 要素を持たないEnumeration
     * @see jakarta.servlet.ServletContext#getServlets()
     * @deprecated
     */
    public Enumeration<?> getServlets() {
        return Collections.emptyEnumeration();
    }

    /**
     * java.lang.System.out.printlnを利用してmessageを出力する。
     *
     * @param message メッセージ
     * @see jakarta.servlet.ServletContext#log(java.lang.String)
     */
    public void log(String message) {
        System.out.println(message);
    }

    /**
     * java.lang.System.out.printlnを利用してmessageを出力する。
     * その後、exceptionのprintStackTrace()を実行する。
     *
     * @param message メッセージ
     * @param exception 例外
     * @see jakarta.servlet.ServletContext#log(java.lang.Exception, java.lang.String)
     * @deprecated
     */
    public void log(Exception exception, String message) {
        System.out.println(message);
        if (exception != null) {
            exception.printStackTrace();
        }
    }

    /**
     * java.lang.System.out.printlnを利用してmessageを出力する。
     * その後、throwableのprintStackTrace()を実行する。
     *
     * @param message メッセージ
     * @see jakarta.servlet.ServletContext#log(java.lang.String, java.lang.Throwable)
     */
    public void log(String message, Throwable throwable) {
        System.out.println(message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    @Override
    public String getContextPath() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getContextPath'");
    }

    @Override
    public int getEffectiveMajorVersion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEffectiveMajorVersion'");
    }

    @Override
    public int getEffectiveMinorVersion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEffectiveMinorVersion'");
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInitParameter'");
    }

    @Override
    public Dynamic addServlet(String servletName, String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addServlet'");
    }

    @Override
    public Dynamic addServlet(String servletName, Servlet servlet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addServlet'");
    }

    @Override
    public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addServlet'");
    }

    @Override
    public Dynamic addJspFile(String servletName, String jspFile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addJspFile'");
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createServlet'");
    }

    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServletRegistration'");
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServletRegistrations'");
    }

    @Override
    public jakarta.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFilter'");
    }

    @Override
    public jakarta.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFilter'");
    }

    @Override
    public jakarta.servlet.FilterRegistration.Dynamic addFilter(String filterName,
            Class<? extends Filter> filterClass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFilter'");
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFilter'");
    }

    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFilterRegistration'");
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFilterRegistrations'");
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSessionCookieConfig'");
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSessionTrackingModes'");
    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDefaultSessionTrackingModes'");
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEffectiveSessionTrackingModes'");
    }

    @Override
    public void addListener(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    }

    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    }

    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createListener'");
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJspConfigDescriptor'");
    }

    @Override
    public ClassLoader getClassLoader() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClassLoader'");
    }

    @Override
    public void declareRoles(String... roleNames) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'declareRoles'");
    }

    @Override
    public String getVirtualServerName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVirtualServerName'");
    }

    @Override
    public int getSessionTimeout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSessionTimeout'");
    }

    @Override
    public void setSessionTimeout(int sessionTimeout) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSessionTimeout'");
    }

    @Override
    public String getRequestCharacterEncoding() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRequestCharacterEncoding'");
    }

    @Override
    public void setRequestCharacterEncoding(String encoding) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRequestCharacterEncoding'");
    }

    @Override
    public String getResponseCharacterEncoding() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResponseCharacterEncoding'");
    }

    @Override
    public void setResponseCharacterEncoding(String encoding) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setResponseCharacterEncoding'");
    }

}
