/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
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
 *
 * Created on 2005/03/26
 */
package org.seasar.maya.standard.engine.processor.jstl.fmt;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import ognl.NoSuchPropertyException;

import org.seasar.maya.engine.processor.ProcessorProperty;
import org.seasar.maya.engine.processor.TemplateProcessorSupport;
import org.seasar.maya.impl.util.StringUtil;
import org.seasar.maya.standard.CONST_STANDARD;

/**
 * JSTL �� fmt:formatDate �ɂ�����l�C�e�B�u�v���Z�b�T.
 * @author suga
 */
public class FormatDateProcessor extends TemplateProcessorSupport
        implements CONST_STANDARD {

    protected static final String DATE 		= "date";
    protected static final String TIME 		= "time";
    protected static final String DATETIME 	= "both";

    protected static final String DEFAULT 	= "default";
    protected static final String SHORT 	= "short";
    protected static final String MEDIUM 	= "medium";
    protected static final String LONG 		= "long";
    protected static final String FULL 		= "full";

    protected static final String FMT_LOCALE =
        "javax.servlet.jsp.jstl.fmt.locale";

    protected static final int INVALID_STYLE = Integer.MIN_VALUE;

    /* rtexprvalue */
    protected ProcessorProperty _value;
    protected ProcessorProperty _type;
    protected ProcessorProperty _pattern;
    protected ProcessorProperty _timeZone;
    protected ProcessorProperty _dateStyle;
    protected ProcessorProperty _timeStyle;

    /* not rtexprvalue */
    protected String _var;
    protected String _scopeString;
    protected int _scope;

    public void setValue(ProcessorProperty value) {
        if(value == null) {
            throw new IllegalArgumentException();
        }
        _value = value;
    }

    public void setType(ProcessorProperty type) {
        _type = type;
    }

    public void setPattern(ProcessorProperty pattern) {
        _pattern = pattern;
    }

    public void setTimeZone(ProcessorProperty timeZone) {
        _timeZone = timeZone;
    }

    public void setDateStyle(ProcessorProperty dateStyle) {
        _dateStyle = dateStyle;
    }

    public void setTimeStyle(ProcessorProperty timeStyle) {
        _timeStyle = timeStyle;
    }

    public void setVar(String var) {
        _var = var;
    }

    public void setScope(String scope) {
        if (scope != null) {
            ScopeType scopeType = ScopeType.getByName(scope);
            _scopeString = scopeType.getName();
            _scope = scopeType.getValue();
        }
    }

    public int doStartProcess(PageContext context) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        Date value = getDateValue(context);
        if (value == null) {
            return Tag.EVAL_PAGE;
        }

        Locale locale = getLocale(context);

        String formatted = null;
        if (locale == null) {
            formatted = value.toString();
        } else {
            DateFormat format = createFormat(context, locale);
    
            TimeZone timeZone = getTimeZone(context);
            if (timeZone != null) {
                format.setTimeZone(timeZone);
            }
    
            formatted = format.format(value);
        }

        if (_var != null) {
            context.setAttribute(_var, formatted, _scope);
        } else {
            Writer out = context.getOut();
            try {
                out.write(formatted);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return Tag.SKIP_BODY;
    }

    public int doEndProcess(PageContext context) {
        return Tag.EVAL_PAGE;
    }

    /**
     * value ��]������ Date ���擾����B
     *
     * @param context �]�����̃y�[�W�R���e�L�X�g
     * @return Date
     * @throws IllegalTagAttributeException date���s���ȏꍇ
     */
    protected Date getDateValue(PageContext context) {
        Object value = null;
        try {
            value = _value.getValue(context);
        } catch (RuntimeException e) {
            // FIXME OGNL�Ɉˑ����Ȃ������� -> impl.el�ŗ�O�𒊏ۉ�����B
            if (!(e.getCause() instanceof NoSuchPropertyException)) {
                throw e;
            }
        }

        if (value == null) {
            return null;
        } else if (value instanceof Date) {
            return (Date) value;
        }
        throw new IllegalTagAttributeException(
                getTemplate(), "formatDate", "value", _value, null);
    }

    /**
     * DateFormat ���쐬����B
     * type ���Z�b�g����Ă���Ȃ� type �̕]�����ʂ����ɍ쐬����B 
     * type �� "date", "time", "both" �̂����ꂩ�B
     * type ���Z�b�g����Ă��炸�Apattern ���Z�b�g����Ă���Ȃ�
     *  pattern �̕]�����ʂ����ɍ쐬����B
     * type �� pattern ���Z�b�g����Ă��Ȃ��Ȃ�Atype="date" ���Z�b�g����Ă����
     * ���Ȃ��B
     *
     * @param context �]�����̃y�[�W�R���e�L�X�g
     * @param locale ���� Locale
     * @return DateFormat
     * @throws IllegalTagAttributeException type���s���ȏꍇ
     */
    protected DateFormat createFormat(PageContext context, Locale locale) {
        String type = null;
        if (_type != null) {
            type = (String)_type.getValue(context);
        } else if (_pattern != null) {
            String pattern = (String) _pattern.getValue(context);
            if (pattern != null) {
                return new SimpleDateFormat(pattern, locale);
            }
        }

        if ((type == null) || DATE.equalsIgnoreCase(type)) {
            return DateFormat.getDateInstance(getDateStyle(context), locale);
        } else if (TIME.equalsIgnoreCase(type)) {
            return DateFormat.getTimeInstance(getTimeStyle(context), locale);
        } else if (DATETIME.equalsIgnoreCase(type)) {
            return DateFormat.getDateTimeInstance(
                    getDateStyle(context), getTimeStyle(context), locale);
        } else {
            throw new IllegalTagAttributeException(
                    getTemplate(), "formatDate", "type", _type, null);
        }
    }

    /**
     * ���t�̃t�H�[�}�b�g�X�^�C�����擾����B
     * (DateFormat#getXxxInstance �ŗ��p����萔�l)
     * ����`�̏ꍇ�� DateFormat.DEFAULT ��Ԃ��B
     *
     * @param context �]�����̃y�[�W�R���e�L�X�g
     * @return ���t�̃t�H�[�}�b�g�X�^�C��
     * @throws IllegalTagAttributeException dateStyle���s���ȏꍇ
     */
    protected int getDateStyle(PageContext context)
            throws IllegalTagAttributeException {
        int dateStyle = getStyle(_dateStyle, context);
        if (dateStyle == INVALID_STYLE) {
            throw new IllegalTagAttributeException(
                    getTemplate(), "formatDate", "style", _dateStyle, null);
        }

        return dateStyle;
    }

    /**
     * �����̃t�H�[�}�b�g�X�^�C�����擾����B
     * (DateFormat#getXxxInstance �ŗ��p����萔�l)
     * ����`�̏ꍇ�� DateFormat.DEFAULT ��Ԃ��B
     *
     * @param context �]�����̃y�[�W�R���e�L�X�g
     * @return �����̃t�H�[�}�b�g�X�^�C��
     * @throws IllegalTagAttributeException timeStyle���s���ȏꍇ
     */
    protected int getTimeStyle(PageContext context)
            throws IllegalTagAttributeException {
        int timeStyle = getStyle(_timeStyle, context);
        if (timeStyle == INVALID_STYLE) {
            throw new IllegalTagAttributeException(
                    getTemplate(), "formatDate", "style", _timeStyle, null);
        }

        return timeStyle;
    }

    /**
     * style ��]�����A���t�܂��͎����̃t�H�[�}�b�g�X�^�C�����擾����B
     * (DateFormat#getXxxInstance �ŗ��p����萔�l)
     * style �� null �̏ꍇ�� DateFormat.DEFAULT ��Ԃ��B
     *
     * @param style �]���Ώۂ̎��B
     * @param context �]�����̃y�[�W�R���e�L�X�g�B
     * @return ���t�܂��͎����̃t�H�[�}�b�g�X�^�C���B
     *          style �̕]�����ʂ��s���ȏꍇ�� INVALID_STYLE ��Ԃ��B
     */
    protected int getStyle(ProcessorProperty style, PageContext context) {
        if (style == null) {
            return DateFormat.DEFAULT;
        }

        return getStyle((String) style.getValue(context));
    }

    /**
     * styleName �����ɓ��t�܂��͎����̃t�H�[�}�b�g�X�^�C�����擾����B
     * (DateFormat#getXxxInstance �ŗ��p����萔�l)
     * styleName �� null �̏ꍇ�� DateFormat.DEFAULT ��Ԃ��B
     * styleName �� "date", "time", "both" �̂����ꂩ�B
     *
     * @param styleName ���t�܂��͎����̃t�H�[�}�b�g�X�^�C�����B
     * @return ���t�܂��͎����̃t�H�[�}�b�g�X�^�C���B
     *          styleName ���s���ȏꍇ�� INVALID_STYLE ��Ԃ��B
     */
    protected int getStyle(String styleName) {
        if (styleName == null) {
            return DateFormat.DEFAULT;
        }

        int ret;
        if (styleName == null || DEFAULT.equalsIgnoreCase(styleName)) {
            ret = DateFormat.DEFAULT;
        } else if (SHORT.equalsIgnoreCase(styleName)) {
            ret = DateFormat.SHORT;
        } else if (MEDIUM.equalsIgnoreCase(styleName)) {
            ret = DateFormat.MEDIUM;
        } else if (LONG.equalsIgnoreCase(styleName)) {
            ret = DateFormat.LONG;
        } else if (FULL.equalsIgnoreCase(styleName)) {
            ret = DateFormat.FULL;
        } else {
            ret = INVALID_STYLE;
        }

        return ret;
    }

    /**
     * timeZone ���Z�b�g����Ă���Ȃ� TimeZone �I�u�W�F�N�g���擾����B
     * timeZone �̕]�����ʂ� TimeZone �I�u�W�F�N�g�Ȃ炻�̂܂ܕԂ��B
     * String �Ȃ� TimeZone �̕�����\���Ƃ��ĕ]�����ĕԂ��B
     *
     * @param context �]�����̃y�[�W�R���e�L�X�g
     * @return TimeZone
     * @throws IllegalTagAttributeException timeZone���s���ȏꍇ
     */
    protected TimeZone getTimeZone(PageContext context) {
        if (_timeZone == null) {
            return null;
        }

        Object timeZone = _timeZone.getValue(context);
        if (timeZone == null) {
            return null;
        } else if (timeZone instanceof String) {
            return TimeZone.getTimeZone((String) timeZone);
        } else if (timeZone instanceof TimeZone) {
            return (TimeZone) timeZone;
        }

        throw new IllegalTagAttributeException(
                getTemplate(), "formatDate", "timeZone", _timeZone, null);
    }

    /**
     * Locale ���擾����B
     * �y�[�W�R���e�L�X�g���� FMT_LOCALE ��T���A����΂����Ԃ��B
     * ������΃f�t�H���g Locale ��Ԃ��B
     *
     * @param context �]�����̃y�[�W�R���e�L�X�g 
     * @return Locale
     */
    protected Locale getLocale(PageContext context) {
        // TODO Localization
        Object locale = context.findAttribute(FMT_LOCALE);

        if (locale instanceof Locale) {
            return (Locale) locale;
        } else if (locale instanceof String) {
            return parseLocale((String) locale);
        } else {
            // TODO Locale �̃f�t�H���g�̌��� (null or getDefault)
            // return null;
            return Locale.getDefault();
        }
    }

    /**
     * Locale��������p�[�X���ALocale�I�u�W�F�N�g��Ԃ��B
     *
     * @param localeString Locale������
     * @return LocaleObject
     * @throws IllegalArgumentException �`���s���̏ꍇ
     */
    protected Locale parseLocale(String localeString) {
        int index = -1;
        String language;
        String country;

        if ((index = localeString.indexOf('-')) >= 0 ||
                (index = localeString.indexOf('_')) >= 0) {
            language = localeString.substring(0, index);
            country = localeString.substring(index + 1);
        } else {
            language = localeString;
            country = null;
        }

        if (StringUtil.isEmpty(language) ||
                (country != null && country.length() == 0)) {
            throw new IllegalArgumentException("invalid Locale: " + localeString);
        }

        if (country == null) {
            country = "";
        }

        return new Locale(language, country);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName());
        sb.append(": value=");
        sb.append(_value);
        sb.append(", type=");
        sb.append(_type);
        sb.append(", pattern=");
        sb.append(_pattern);
        sb.append(", timeZone=");
        sb.append(_timeZone);
        sb.append(", dateStyle=");
        sb.append(_dateStyle);
        sb.append(", timeStyle=");
        sb.append(_timeStyle);
        sb.append(", var=");
        sb.append(_var);
        sb.append(", scope=");
        sb.append(_scopeString);
        return new String(sb);
    }
}