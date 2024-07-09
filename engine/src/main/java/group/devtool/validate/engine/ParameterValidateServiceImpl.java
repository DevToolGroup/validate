/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 方法/构造器参数校验服务实现类
 * 
 **/
public class ParameterValidateServiceImpl implements ParameterValidateService {

  private ConstraintAnnotationProvider provider;

  public ParameterValidateServiceImpl(Boolean openCache) {
    this(openCache, Collections.emptyList(), Collections.emptyList());
  }

  public ParameterValidateServiceImpl(Boolean openCache, List<ConstrainedValueSpreader> valueSpreaders,
      List<Class<? extends Annotation>> annotations) {
    ConstrainedValueSpreadProvider spreadProvider = new ConstrainedValueSpreadProviderImpl(valueSpreaders);
    ConstraintValidateOperateProvider operateProvider = new ConstraintValidateOperateProviderImpl(annotations);
    this.provider = new ConstraintAnnotationProviderImpl(spreadProvider, operateProvider, openCache);
  }

  public ParameterValidateServiceImpl(ConstrainedValueSpreadProvider spreadProvider,
      ConstraintValidateOperateProvider operateProvider, Boolean openCache) {
    this.provider = new ConstraintAnnotationProviderImpl(spreadProvider, operateProvider, openCache);
  }

  @Override
  public List<ConstraintValidateFailure> validate(Executable executable, Object[] parameters) throws Exception {
    List<ConstraintValidateFailure> result = new ArrayList<>();

    // 解析参数注解
    List<List<ConstraintAnnotation>> annotations = provider.resolveAnnotations(executable);
    if (annotations.size() != parameters.length) {
      throw new IllegalArgumentException("方法的参数与输入参数数量不一致，方法参数数量：" + annotations.size()
          + ", 输入参数数量：" + parameters.length);
    }
    ParameterValidateContextImpl validateContext = new ParameterValidateContextImpl(executable, parameters);
    // 执行普通约束
    for (int i = 0; i < parameters.length; i++) {
      List<ConstraintAnnotation> constrains = annotations.get(i);
      for (ConstraintAnnotation constraint : constrains) {
        result.addAll(constraint.validate(new ConstraintParameterValueContext(parameters[i], null), validateContext));
      }
    }
    // 执行reference依赖约束
    while (validateContext.hasWaitReferenceConstraint()) {
      for (int i = 0; i < parameters.length; i++) {
        List<ConstraintAnnotation> constrains = annotations.get(i);
        for (ConstraintAnnotation constraint : constrains) {
          result.addAll(constraint.validate(new ConstraintParameterValueContext(parameters[i], null), validateContext));
        }
      }
    }

    // 执行条件约束
    while (validateContext.hasConditionConstraint()) {
      for (int i = 0; i < parameters.length; i++) {
        List<ConstraintAnnotation> constrains = annotations.get(i);
        for (ConstraintAnnotation constraint : constrains) {
          result.addAll(constraint.validate(new ConstraintParameterValueContext(parameters[i], null),
              new ParameterValidateContextImpl(executable, parameters)));
        }
      }
    }

    validateContext.clear();

    return result;
  }

}
