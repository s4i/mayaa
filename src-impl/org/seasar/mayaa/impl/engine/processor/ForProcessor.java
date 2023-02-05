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
package org.seasar.mayaa.impl.engine.processor;

import org.seasar.mayaa.engine.Page;
import org.seasar.mayaa.engine.processor.IterationProcessor;
import org.seasar.mayaa.engine.processor.ProcessStatus;
import org.seasar.mayaa.engine.processor.ProcessorProperty;
import org.seasar.mayaa.impl.cycle.CycleUtil;
import org.seasar.mayaa.impl.cycle.DefaultCycleLocalInstantiator;
import org.seasar.mayaa.impl.util.ObjectUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ForProcessor extends TemplateProcessorSupport
        implements IterationProcessor {

    private static final long serialVersionUID = 109332595431942951L;

    private static final int DEFAULT_MAX = 256;
    private static final String COUNTER_KEY = ForProcessor.class.getName() + "#counter";
    static {
        CycleUtil.registVariableFactory(COUNTER_KEY,
                new DefaultCycleLocalInstantiator() {
            public Object create(Object owner, Object[] params) {
                return Integer.valueOf(0);
            }
        });
    }

    private ProcessorProperty _init;
    private ProcessorProperty _test;
    private ProcessorProperty _after;
    private int _max = DEFAULT_MAX;

    // MLD property, expectedClass=void
    public void setInit(ProcessorProperty init) {
        _init = init;
    }

    // MLD property, required=true, expectedClass=boolean
    public void setTest(ProcessorProperty test) {
        if (test == null) {
            throw new IllegalArgumentException();
        }
        assert(boolean.class.equals(test.getExpectedClass()));
        _test = test;
    }

    // MLD property, expectedClass=void
    public void setAfter(ProcessorProperty after) {
        _after = after;
    }

    // MLD property
    public void setMax(int max) {
        _max = max;
    }

    public boolean isIteration() {
        return true;
    }

    protected boolean execTest() {
        if (_test == null) {
            throw new IllegalStateException();
        }
        int count = getCounter();
        if (0 <= _max && _max < count) {
            throw new TooManyLoopException(_max);
        }
        setCounter(++count);
        return ObjectUtil.booleanValue(_test.getValue().execute(Boolean.class, null), false);
    }

    protected int getCounter() {
        return ((Integer)CycleUtil.getLocalVariable(
                COUNTER_KEY, this, null)).intValue();
    }

    protected void setCounter(int counter) {
        CycleUtil.setLocalVariable(COUNTER_KEY, this, Integer.valueOf(counter));
    }

    public ProcessStatus doStartProcess(Page topLevelPage) {
        CycleUtil.clearLocalVariable(COUNTER_KEY, this);
        if (_init != null) {
            _init.getValue().execute(Void.class, null);
        }
        return execTest() ? ProcessStatus.EVAL_BODY_INCLUDE : ProcessStatus.SKIP_BODY;
    }

    public ProcessStatus doAfterChildProcess() {
        if (_after != null) {
            _after.getValue().execute(Void.class, null);
        }
        return execTest() ? ProcessStatus.EVAL_BODY_AGAIN : ProcessStatus.SKIP_BODY;
    }

    // for serialize

    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

}
