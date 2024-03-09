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
package org.seasar.mayaa.impl.cycle.jsp;

import jakarta.el.Expression;
import jakarta.el.ELResolver;

import org.seasar.mayaa.cycle.script.CompiledScript;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ExpressionImpl extends Expression {

    private CompiledScript _script;
    private Class<?> _expectedClass;

    public ExpressionImpl(CompiledScript script, Class<?> expectedClass) {
        _script = script;
        _expectedClass = expectedClass;
    }

    public Object evaluate(ELResolver vResolver) {
        return _script.execute(_expectedClass, null);
    }

    @Override
    public String getExpressionString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExpressionString'");
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equals'");
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hashCode'");
    }

    @Override
    public boolean isLiteralText() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLiteralText'");
    }

}
