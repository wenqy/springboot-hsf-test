package com.wenqy.hsf;

import com.alibaba.boot.hsf.annotation.HSFConsumer;
import com.taobao.hsf.remoting.service.GenericService;
import com.taobao.pandora.boot.test.junit4.DelegateTo;
import com.taobao.pandora.boot.test.junit4.PandoraBootRunner;
import com.taobao.pandora.boot.test.junit4.Property;
import com.taobao.pandora.boot.test.junit4.SystemProperties;
import com.wenqy.hsf.service.EchoService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(PandoraBootRunner.class)
@DelegateTo(SpringJUnit4ClassRunner.class)
// 加载测试需要的类，一定要加入 Spring Boot 的启动类，其次需要加入本类
@SpringBootTest(classes = {PandoraApplication.class, AppTest.class})
@Component
@SystemProperties({@Property(key="my.test", value = "true")}) //潘多拉属性
public class AppTest {

    /**
     * 当这里使用 @HSFConsumer 时，一定要在 @SpringBootTest 类加载中，加载本类，通过本类来注入对象，否则当做泛化时，会报类转换异常
     */
    @HSFConsumer(generic = true)
    EchoService echoService;

    /**
     * 测试普通调用
     */
    @Test
    public void testInvoke() {
        TestCase.assertEquals("hello world"+",from-server", echoService.echo("hello world"));
    }


    /**
     * 测试泛化调用
     */
    @Test
    public void testGenericInvoke() {
        GenericService service = (GenericService) echoService;
        Object result = service.$invoke("echo", new String[] {"java.lang.String"}, new Object[] {"hello world"});
        TestCase.assertEquals("hello world"+",from-server", result);
    }

    /**
     * 测试Mock返回值
     */
    @Test
    public void testMock() {
        EchoService mock = Mockito.mock(EchoService.class, AdditionalAnswers.delegatesTo(echoService));
        Mockito.when(mock.echo("")).thenReturn("beta");
        TestCase.assertEquals("beta", mock.echo(""));
    }

}
