package com.common.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.common.constant.ErrorCode;
import com.common.exception.BussinessException;

/**
 * 
 * @author zhaomy07
 * @date 2017年10月9日 下午2:49:36
 * @version V1.0.0
 * description：
 */
public abstract class Assert {

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BussinessException(errorCode);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BussinessException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    // ----------------------------------------------------------------

    public static void isNull(Object object, ErrorCode errorCode) {
        if (object != null) {
            throw new BussinessException(errorCode);
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BussinessException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    // ----------------------------------------------------------------

    public static void notNull(Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new BussinessException(errorCode);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BussinessException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    // ----------------------------------------------------------------

    public static void hasLength(String text, ErrorCode errorCode) {
        if (!StringUtils.hasLength(text)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new BussinessException(message);
        }
    }

    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    // ----------------------------------------------------------------

    public static void hasText(String text, ErrorCode errorCode) {
        if (!StringUtils.hasText(text)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BussinessException(message);
        }
    }

    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    // ----------------------------------------------------------------

    public static void doesNotContain(String textToSearch, String substring, ErrorCode errorCode) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BussinessException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    // ----------------------------------------------------------------

    public static void notEmpty(Object[] array, ErrorCode errorCode) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BussinessException(message);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    // ----------------------------------------------------------------

    public static void noNullElements(Object[] array, ErrorCode errorCode) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BussinessException(errorCode);
                }
            }
        }
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BussinessException(message);
                }
            }
        }
    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    // ----------------------------------------------------------------

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BussinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    // ----------------------------------------------------------------

    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BussinessException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    // ----------------------------------------------------------------

    public static void isInstanceOf(Class<?> type, Object obj, ErrorCode errorCode) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new BussinessException((StringUtils.hasLength(message) ? message + " " : "") + "Object of class ["
                    + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
        }
    }

    // ----------------------------------------------------------------

    public static void isAssignable(Class<?> superType, Class<?> subType, ErrorCode errorCode) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new BussinessException(errorCode);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new BussinessException((StringUtils.hasLength(message) ? message + " " : "") + subType + " is not assignable to " + superType);
        }
    }

    // ----------------------------------------------------------------

    public static void state(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BussinessException(errorCode);
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new BussinessException(message);
        }
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

}
