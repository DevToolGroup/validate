package group.devtool.validate;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE,
    ElementType.TYPE_USE,
    ElementType.FIELD,
    ElementType.PARAMETER,
    ElementType.METHOD })
@Constraint
public @interface Spread {

}
