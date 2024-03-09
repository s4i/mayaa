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

import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ExpressionFactory;
import jakarta.el.FunctionMapper;
import jakarta.el.MethodExpression;
import jakarta.el.ValueExpression;
import jakarta.el.ELResolver;

import org.seasar.mayaa.cycle.script.CompiledScript;
import org.seasar.mayaa.impl.cycle.script.ScriptUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ExpressionFactoryImpl extends ExpressionFactory {

    private static ExpressionFactory _instance =
        new ExpressionFactoryImpl();

    public static ExpressionFactory getInstance() {
        return _instance;
    }

    public Object evaluate(String expression, @SuppressWarnings("rawtypes") Class expectedClass,
            ELResolver vResolver, FunctionMapper fMapper)
            throws ELException {
        ExpressionImpl exp = parseExpression(expression, expectedClass, fMapper);
        return exp.evaluate(vResolver);
    }

    public ExpressionImpl parseExpression(String expression,
            @SuppressWarnings("rawtypes") Class expectedClass, FunctionMapper fMapper) {
        CompiledScript script = ScriptUtil.compile(expression);
        return new ExpressionImpl(script, expectedClass);
    }

    @Override
    public ValueExpression createValueExpression(ELContext context, String expression, Class<?> expectedType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createValueExpression'");
    }

    @Override
    public ValueExpression createValueExpression(Object instance, Class<?> expectedType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createValueExpression'");
    }

    @Override
    public MethodExpression createMethodExpression(ELContext context, String expression, Class<?> expectedReturnType,
            Class<?>[] expectedParamTypes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMethodExpression'");
    }

    @Override
    public <T> T coerceToType(Object obj, Class<T> targetType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coerceToType'");
    }

}
