package it.progetto.energy.business;

import it.progetto.energy.exception.NotUpdatableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ENTITY_CANNOT_BE_UPDATED;

@Component
@Slf4j
public class UpdateMethod<T> {

    private Class<T> type;

    public T updateOldEntityWithNewEntityByFields(T newEntity, T oldEntity) {

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(newEntity);
                if (newValue == null) {
                    Object oldValue = field.get(oldEntity);
                    field.set(newEntity, oldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return oldEntity;
    }

    public T updateOldEntityWithNewEntityByMethod(T newEntity, T oldEntity){
        try {
            for (Method method : type.getDeclaredMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    String fieldName = methodName.substring(3);
                    String setMethodName = "set" + fieldName;

                    Method setMethod = type.getMethod(setMethodName, method.getReturnType());
                    Object newValue = method.invoke(newEntity);

                    if (newValue != null) {
                        setMethod.invoke(oldEntity, newValue);
                    }
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new NotUpdatableException(e.getMessage(), List.of(ENTITY_CANNOT_BE_UPDATED));
        }

        return oldEntity;
    }

    public void updateOldEntity(T newEntity, T oldEntity, UnaryOperator<T> function, Consumer<T> consumerOldEntity) {

        final T newValue = function.apply(newEntity);
        final T oldValue = function.apply(oldEntity);

        consumerOldEntity.accept(newValue != null ? newValue : oldValue);
    }

}
