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
package org.seasar.mayaa.builder;

import org.seasar.mayaa.ParameterAware;
import org.seasar.mayaa.engine.specification.Specification;

/**
 * 設定XMLのビルダ。
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface SpecificationBuilder
        extends ParameterAware {

    /**
     * ページ設定のビルドを行う。
     * @param specification 設定オブジェクト。
     */
    void build(Specification specification);

}
