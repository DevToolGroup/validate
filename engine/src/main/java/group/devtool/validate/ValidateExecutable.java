package group.devtool.validate;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD,
    ElementType.CONSTRUCTOR,
})
public @interface ValidateExecutable {
  
  String name();

}
