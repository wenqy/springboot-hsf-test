package com.wenqy.hsf;

import com.taobao.pandora.boot.PandoraBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.*;

/**
 * 启动类
 * 指定配置\注册中心
 * 指定系统属性 java -Djmenv.tbsite.net=127.0.0.1
 * 或者/etc/hosts里配置域名映射
 * https://help.aliyun.com/document_detail/99943.html?spm=a2c4g.11186623.6.618.2d987288QbAGP3
 */
@SpringBootApplication
public class PandoraApplication {

    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {

        // 启动 Pandora Boot 用于加载 Pandora 容器
        PandoraBootstrap.run(args);
        SpringApplication.run(PandoraApplication.class, args);
        // 标记服务启动完成,并设置线程 wait。防止用户业务代码运行完毕退出后，导致容器退出。
        PandoraBootstrap.markStartupAndWait();
        
    }
    
    @EventListener(classes = { ContextStartedEvent.class, ContextStoppedEvent.class, ContextRefreshedEvent.class })
    public void handleMultipleEvents(ApplicationContextEvent event) {
        System.out.println("Multi-event listener invoked" + event.getClass());
    }
}