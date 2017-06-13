package com.jason.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-25
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
public class TypeReferenceTest4 extends TestCase {

        public void test_typeRef() throws Exception {
            TypeReference<VO<List<A>>> typeRef = new TypeReference<VO<List<A>>>() {
            };

            VO<List<A>> vo = JSON.parseObject("{\"list\":[{\"id\":123}]}", typeRef);
            A a = (A) vo.getList();
            a.getId();

            Assert.assertEquals(123, vo.getList().get(0).getId());
        }

        public class VO<T> {

            private T list;

            public T getList() {
                return list;
            }

            public void setList(T list) {
                this.list = list;
            }
        }

        public class A {

            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

        }
}
