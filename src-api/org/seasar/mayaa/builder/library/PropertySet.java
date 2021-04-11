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
package org.seasar.mayaa.builder.library;

import java.io.Serializable;
import java.util.Iterator;

import org.seasar.mayaa.ParameterAware;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface PropertySet extends ParameterAware, Serializable {

    /**
     * 例外やログのメッセージ用途として、ファイル中での行番号を取得する。
     * @return 行番号。
     */
    int getLineNumber();

    /**
     * 所属ライブラリの情報モデル取得。
     * @return ライブラリ情報。
     */
    LibraryDefinition getLibraryDefinition();

    /**
     * プロパティセット名の取得。
     * @return プロパティセット名。
     */
    String getName();

    /**
     * プロセッサへのバインディング情報モデル（PropertyDefinition）
     * オブジェクトのイテレート。
     * @return バインディング情報イテレーター。
     */
    Iterator<PropertyDefinition> iteratePropertyDefinition();

}
