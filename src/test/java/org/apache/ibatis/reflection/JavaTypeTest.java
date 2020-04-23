package org.apache.ibatis.reflection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

/**
 * Java Type Interface
 * Java Type 示例代码
 *
 * Java Type 接口是所有类型的父接口，共有四个子接口和一个实现类
 * 四个子接口分别为 ParameterizedType、GenericArrayType、TypeVariable、WildcardType
 * 一个实现类则为 Class
 *
 * - Class
 *   - 原始类型。
 *   - Class类的对象表示JVM中的一个类或者接口。每个Java类在JVM中都表现为一个Class对象。
 *     在程序中可以通过 "类名.class"、"对象.getClass()" 或者 Class.forName("") 等方式获取 Class 对象。
 *     数组也被映射为Class对象,所有元素类型相同且维数相同的数组都共享一个Class对象。
 *
 * - ParameterizedType
 *   - 参数化类型,例如List<String>这种带有泛型的类型
 *     - Type getRawType()
 *       返回参数化类型中的原始类型,例如List<String>的原始类型就是List.class
 *     - Type[] getActualTypeArguments()
 *       返回参数化类型变量或是实际类型列表。例如Map<Integer, String>返回Integer和String
 *
 * - TypeVariable
 *   - 类型变量，用来反映在JVM编译泛型钱的信息。
 *     - Type[] getBounds()
 *       获取类型变量的上边界
 *     - D getGenericDeclaration()
 *       获取声明该变量的原始类型
 *     - String getName()
 *       获取在源码中定义的名字
 *
 * - GenericArrayType
 *   - 数组类型或组成元素是ParameterizedType或者TypeVariable。例如 T[] 或者 List<String> []
 *     - Type getGenericComponentType()
 *       返回数组的组成类型
 *
 * - WildcardType
 *   - 通配符类型。例如 ? extend Number 和 ? super Number
 *     - Type[] getUpperBounds()
 *       获取泛型变量的上界
 *     - Type[] getLowerBounds()
 *       获取泛型变量的下界
 */
public class JavaTypeTest {

  class A<T> {
    private List<String> list1;
    private List<T> list2;
    private T[] list3;
    private T t;
    private Map.Entry<T, String> map;
    private List<? extends Number> wilds;
  }

  @Test
  public void testParameterizedType() throws NoSuchFieldException {
    Class<?> clazz = A.class;
    Type type = clazz.getDeclaredField("list1").getGenericType();
    assertTrue(type instanceof ParameterizedType);
    assertEquals(1, ((ParameterizedType) type).getActualTypeArguments().length);
    assertEquals(List.class, ((ParameterizedType) type).getRawType());
    type = clazz.getDeclaredField("map").getGenericType();
    assertEquals(Map.class, ((ParameterizedType) type).getOwnerType());
  }

  @Test
  public void testTypeVariable() throws NoSuchFieldException {
    Class<?> clazz = A.class;
    Type type = clazz.getDeclaredField("t").getGenericType();
    assertTrue(type instanceof TypeVariable<?>);
    assertEquals(Object.class, ((TypeVariable<?>) type).getBounds()[0]);
    assertEquals(A.class, ((TypeVariable<?>) type).getGenericDeclaration());
  }

  @Test
  public void testGenericArrayType() throws NoSuchFieldException {
    Class<?> clazz = A.class;
    Type type = clazz.getDeclaredField("list3").getGenericType();
    assertTrue(type instanceof GenericArrayType);
    assertEquals("T", ((GenericArrayType) type).getGenericComponentType().getTypeName());
  }

  @Test
  public void testWildcardType() throws NoSuchFieldException {
    Class<?> clazz = A.class;
    Type type = clazz.getDeclaredField("wilds").getGenericType();
    assertTrue(type instanceof ParameterizedType);
    type = ((ParameterizedType) type).getActualTypeArguments()[0];
    assertTrue(type instanceof WildcardType);
    assertEquals(Number.class, ((WildcardType) type).getUpperBounds()[0]);
  }

}
