package com.webservice.demo.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.webservice.client.DemoService_Service;
import com.webservice.demo.interceptor.CxfOutInterceptor;
import com.webservice.demo.service.DemoService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 接口实现类
 *
 * @author qia.wu
 * @create 2019-10-12 16:58
 * @see com.webservice.demo.service.impl
 */
@WebService(serviceName = "DemoService", // 与接口中指定的name一致
		targetNamespace = "http://service.demo.webservice.com", // 与接口中的命名空间一致,一般是接口的包名倒
		endpointInterface = "com.webservice.demo.service.DemoService"// 接口地址
)
public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String user) {
		File file = new File("/riking/test.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fw = null;
		try {
			fw = new FileOutputStream(file);
			fw.write(user.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fw) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(user + "aaa");
		return user+"，现在时间："+"("+new Date()+")" + "\n\r";
	}

	@Override
	public void callSay(String wsdlUrl) {
		//创建动态客户端
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

		//String wsdlUrl = "http://localhost:9999/demo/demo/api?wsdl";
		Client client = factory.createClient(wsdlUrl);
		// 需要密码的情况需要加上用户名和密码
		//client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
		client.getInInterceptors().add(new LoggingInInterceptor());
		List<Interceptor<? extends Message>> outInterceptors = client.getOutInterceptors();
		outInterceptors.add(new LoggingOutInterceptor());
		System.out.println(outInterceptors);
		outInterceptors.add(new CxfOutInterceptor());
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		//连接超时
		httpClientPolicy.setConnectionTimeout(2000);
		//取消块编码
		httpClientPolicy.setAllowChunking(false);
		//响应超时
		httpClientPolicy.setReceiveTimeout(120000);
		httpClientPolicy.setAcceptEncoding("UTF-8");
		httpClientPolicy.setDecoupledEndpoint("UTF-8");
		conduit.setClient(httpClientPolicy);
		//client.getOutInterceptors().addAll(interceptors);//设置拦截器
		try{
			Object[] objects = new Object[0];
			// invoke("方法名",参数1,参数2,参数3....);
			//objects = client.invoke("sayHello", "sujin");
			// targetNamespace、方法名,新建QName对象
			QName qname = new QName("http://service.demo.webservice.com", "sayHello");
			String arg = "sujin" + "\r\nb|abd";
			objects = client.invoke(qname, arg);
			System.out.print("返回数据:" + objects[0]);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static final QName SERVICE_NAME = new QName("http://service.demo.webservice.com", "DemoService");

	@Override
	public void callSay1(String url) {
		DemoService_Service ss = null;
		try {
			ss = new DemoService_Service(new URL(url), SERVICE_NAME);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		com.webservice.client.DemoService port = ss.getDemoServiceImplPort();
		{
			System.out.println("Invoking sayHello...");
			String _sayHello_arg0 = "sujin" + "\r\nb";
			String _sayHello__return = port.sayHello(_sayHello_arg0);
			System.out.print("返回数据:" + _sayHello__return);
		}
	}
}
