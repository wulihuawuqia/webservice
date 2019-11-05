package com.webservice.demo;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.junit.Test;

import javax.xml.namespace.QName;

/**
 * 描述:
 *
 * @author qia.wu
 * @create 2019-10-12 17:17
 * @see com.webservice.demo
 */
public class WebServiceTest {

	@Test
	public void testSayHello() {
		//创建动态客户端
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		//String wsdlUrl = "http://192.168.142.128:7001/demo/demo/api?wsdl";
		String wsdlUrl = "http://localhost:9999/demo/demo/api?wsdl";
		Client client = factory.createClient(wsdlUrl);
		// 需要密码的情况需要加上用户名和密码
		//client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(2000);  //连接超时
		httpClientPolicy.setAllowChunking(false);    //取消块编码
		httpClientPolicy.setReceiveTimeout(120000);     //响应超时
		conduit.setClient(httpClientPolicy);
		//client.getOutInterceptors().addAll(interceptors);//设置拦截器
		try{
			Object[] objects = new Object[0];
			// invoke("方法名",参数1,参数2,参数3....);
			//objects = client.invoke("sayHello", "sujin");
			// targetNamespace、方法名,新建QName对象
			QName qname = new QName("http://service.demo.webservice.com", "sayHello");
			objects = client.invoke(qname, "sujin");
			System.out.print("返回数据:" + objects[0]);
			System.out.print("返回数据:" + objects[0]);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testCall() {
		//创建动态客户端
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		String wsdlUrl = "http://192.168.142.128:7001/demo/demo/api?wsdl";
		//String wsdlUrl = "http://localhost:9999/demo/demo/api?wsdl";
		Client client = factory.createClient(wsdlUrl);
		// 需要密码的情况需要加上用户名和密码
		//client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(2000);  //连接超时
		httpClientPolicy.setAllowChunking(false);    //取消块编码
		httpClientPolicy.setReceiveTimeout(120000);     //响应超时
		conduit.setClient(httpClientPolicy);
		//client.getOutInterceptors().addAll(interceptors);//设置拦截器
		try{
			Object[] objects = new Object[0];
			// invoke("方法名",参数1,参数2,参数3....);
			//objects = client.invoke("sayHello", "sujin");
			// targetNamespace、方法名,新建QName对象
			QName qname = new QName("http://service.demo.webservice.com", "callSay");
			objects = client.invoke(qname, "http://192.168.142.1:9999/demo/demo/api?wsdl");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testCall1() {
		//创建动态客户端
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		//String wsdlUrl = "http://192.168.142.128:7001/demo/demo/api?wsdl";
		String wsdlUrl = "http://localhost:9999/demo/demo/api?wsdl";
		Client client = factory.createClient(wsdlUrl);
		// 需要密码的情况需要加上用户名和密码
		//client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(2000);  //连接超时
		httpClientPolicy.setAllowChunking(false);    //取消块编码
		httpClientPolicy.setReceiveTimeout(120000);     //响应超时
		conduit.setClient(httpClientPolicy);
		//client.getOutInterceptors().addAll(interceptors);//设置拦截器
		try{
			Object[] objects = new Object[0];
			// invoke("方法名",参数1,参数2,参数3....);
			//objects = client.invoke("sayHello", "sujin");
			// targetNamespace、方法名,新建QName对象
			QName qname = new QName("http://service.demo.webservice.com", "callSay1");
			objects = client.invoke(qname, "http://192.168.142.1:9999/demo/demo/api?wsdl");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
