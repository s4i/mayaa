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
import jakarta.el.ELResolver;

import org.seasar.mayaa.cycle.scope.AttributeScope;
import org.seasar.mayaa.impl.cycle.CycleUtil;
import org.seasar.mayaa.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ELResolverImpl extends ELResolver {

    private static ELResolver _instance = new ELResolverImpl();

    public static ELResolver getInstance() {
        return _instance;
    }

    public Object resolveVariable(String pName) {
        if (StringUtil.hasValue(pName)) {
            AttributeScope scope = CycleUtil.findStandardAttributeScope(pName);
            if (scope != null) {
                return scope.getAttribute(pName);
            }
        }
        return null;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValue'");
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setValue'");
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isReadOnly'");
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommonPropertyType'");
    }

}
