package com.ymw.love.system.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

public class BeanValidatorUtil {


    /**
     * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<propertyPath +separator+ message>.
     */
    public static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e, String separator) {
        return extractPropertyAndMessageAsList(e.getConstraintViolations(), separator);
    }

    /**
     * 辅助方法, 转换Set<ConstraintViolation>为List<propertyPath +separator+ message>.
     */
    @SuppressWarnings("rawtypes")
    public static List<String> extractPropertyAndMessageAsList(Set<? extends ConstraintViolation> constraintViolations,
                                                               String separator) {
        List<String> errorMessages = Lists.newArrayList();
        for (ConstraintViolation violation : constraintViolations) {
            errorMessages.add(violation.getMessage());
        }
        return errorMessages;
    }


    /**
     * 获取参数错误消息
     */
    public static String getErrorMessage(ConstraintViolationException e) {
        List<String> list=extractPropertyAndMessageAsList(e,":");
        return addErrorMessage(list.toArray(new String[]{}));
    }


    /**
     * 添加验证参数错误消息
     */
    public static String addErrorMessage(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : StringUtils.EMPTY);
        }
        return sb.toString();
    }

}