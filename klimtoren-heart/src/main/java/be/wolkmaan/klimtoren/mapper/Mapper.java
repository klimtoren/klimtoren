/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.mapper;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.Column;
import org.jooq.Record;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 *
 * @author karl
 */
public class Mapper {

    private static Map<Class<?>, Map<String, Method>> _methodsCache;
    private static Map<Class<?>, Map<String, Field>> _customFieldNamesCache;
    private static Map<String, Class<?>> _pseudoClassCache;

    private static Mapper _instance;

    private Mapper() {
        if (_methodsCache == null) {
            _methodsCache = new LinkedHashMap<>();
        }
        if (_customFieldNamesCache == null) {
            _customFieldNamesCache = new LinkedHashMap<>();
        }
        if (_pseudoClassCache == null) {
            _pseudoClassCache = new LinkedHashMap<>();
        }
    }

    public static Mapper getInstance() {
        if (_instance == null) {
            _instance = new Mapper();
        }
        return _instance;
    }

    public <T> T map(Record record, Class<T> toClass) {

        try {
            final T mainEntity = toClass.newInstance();

            Comparator<org.jooq.Field> byPseudoPrefix = Comparator.comparing((f) -> f.getName().contains("::"));
            Comparator<org.jooq.Field> byRefField = Comparator.comparing((f) -> f.getName().contains("."));
            Comparator<org.jooq.Field> byName = Comparator.comparing((f) -> f.getName());
            
            
            Lists.newArrayList(record.fields())
                    .stream()
                    .sorted(byName.thenComparing(byRefField).thenComparing(byPseudoPrefix))
                    .forEach((field) -> {
                        Object entityToInvoke = mainEntity;

                        String jooqFieldName = field.getName();
                        String refPseudoClassName = null;
                        if (jooqFieldName.contains("::")) {
                            refPseudoClassName = jooqFieldName.split("::")[0];
                            jooqFieldName = jooqFieldName.split("::")[1];
                        }

                        String fieldName = jooqFieldName;

                        //if the fieldname contains dot -> deep mapping children
                        if (jooqFieldName.contains(".")) {
                            String refProp = jooqFieldName.split("\\.")[0];
                            fieldName = jooqFieldName.split("\\.")[1];

                            try {
                                Field refField = toClass.getDeclaredField(refProp);
                                Class<?> pseudoClass = null;
                                if (refPseudoClassName != null) {
                                    pseudoClass = findPseudoClass(refField.getType(), refPseudoClassName);
                                }

                                String refMethodName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, refField.getName());
                                Method refSetter = methods(mainEntity.getClass()).get("set" + refMethodName);
                                Method refGetter = mainEntity.getClass().getDeclaredMethod("get" + refMethodName);
                                if (refGetter.invoke(mainEntity) == null) {
                                    refSetter.setAccessible(true);
                                    refSetter.invoke(mainEntity,
                                            pseudoClass == null
                                            ? //if the referenced field has no pseudo class implementation 
                                            //eg forParty.display_name
                                            refField.getType().getDeclaredConstructor().newInstance()
                                            : //if the referenced field has a pseudo class implementation
                                            //eg Person::forParty.givenname \ Person must extend Party
                                            pseudoClass.getDeclaredConstructor().newInstance());
                                }
                                entityToInvoke = refGetter.invoke(mainEntity);

                            } catch (InstantiationException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                                Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {
                            entityToInvoke = mainEntity;
                        }

                        try {

                            String methodName = "set" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, fieldName);

                            if (!methods(entityToInvoke.getClass()).containsKey(methodName)) {
                                if (customFieldNames(entityToInvoke.getClass()).containsKey(fieldName)) {
                                    methodName = "set" + customFieldNames(toClass).get(fieldName).getName();
                                } else {
                                    methodName = null;
                                }
                            }
                            if (methodName != null) {
                                Method method = methods(entityToInvoke.getClass()).get(methodName);
                                method.setAccessible(true);
                                method.invoke(entityToInvoke, record.getValue(field.getName(), field.getType()));
                            }
                        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                            Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

            return mainEntity;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private <X> X _internalMap(List<org.jooq.Field> fields, Class<X> toClass) {
        return null;
    }

    private List<Method> parentMethods(Class<?> clazz) {
        List<Method> methods = Lists.newArrayList(clazz.getDeclaredMethods());
        if (clazz.getSuperclass() != null) {
            methods.addAll(parentMethods(clazz.getSuperclass()));
        }
        return methods;
    }

    public Map<String, Method> methods(Class<?> clazz) {
        return _methodsCache.computeIfAbsent(clazz, c -> {
            List<Method> _methods = parentMethods(clazz);

            return _methods
                    .stream()
                    .filter(m -> isSetter(m))
                    .collect(Collectors.toMap(
                                    m -> m.getName(),
                                    m -> m));
        });
    }

    public Map<String, Field> customFieldNames(Class<?> clazz) {
        return _customFieldNamesCache.computeIfAbsent(clazz, c -> {
            return Lists.newArrayList(c.getDeclaredFields())
                    .stream()
                    .filter(f -> f.isAnnotationPresent(Column.class
                            ))
                    .collect(Collectors.toMap(
                                    f -> f.getAnnotation(Column.class
                                    ).name(),
                                    f -> f));
        }
        );
    }

    public Field fieldFor(String name, Class<?> inClass) {
        return customFieldNames(inClass)
                .get(name);
    }

    //determines if a method is a setter (starts with "set", has exact one parameter)
    private static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        return method.getParameterTypes().length == 1;
    }

    //singleton : prevent cloning
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private Class<?> findPseudoClass(Class<?> superClass, String refPseudoClassName) {
        _pseudoClassCache.computeIfAbsent(refPseudoClassName, pcn -> {
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
            provider.addIncludeFilter(new AssignableTypeFilter(superClass));

            Set<BeanDefinition> components = provider.findCandidateComponents("be/wolkmaan/klimtoren");
            for (BeanDefinition component : components) {
                try {
                    Class cls = Class.forName(component.getBeanClassName());
                    if (refPseudoClassName.toUpperCase().equals(cls.getSimpleName().toUpperCase())) {
                        return cls;
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        });
        return _pseudoClassCache.get(refPseudoClassName);
    }
}
