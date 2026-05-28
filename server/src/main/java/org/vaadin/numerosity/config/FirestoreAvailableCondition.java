package org.vaadin.numerosity.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition that matches only when a bean named firestore exists
 * in the context and its value is non-null.
 */
public class FirestoreAvailableCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            Object bean = context.getBeanFactory().getBean("firestore");
            return bean != null && bean.getClass().getSimpleName().equals("Firestore");
        } catch (Exception e) {
            return false;
        }
    }
}
