package com.webservice.demo.service;

import javax.jws.WebService;

/**
 * 描述:
 * 测试类
 *
 * @author qia.wu
 * @create 2019-10-12 16:56
 * @see com.webservice.demo.service
 */
@WebService(name = "DemoService", // 暴露服务名称
		targetNamespace = "http://service.demo.webservice.com"// 命名空间,一般是接口的包名倒序
)
public interface DemoService {
	 String sayHello(String user);

	 void callSay(String url);

	 void callSay1(String url);
}
