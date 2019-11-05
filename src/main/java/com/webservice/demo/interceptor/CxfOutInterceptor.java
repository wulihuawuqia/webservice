package com.webservice.demo.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPBody;

/**
 * 描述:
 * 出拦截器
 *
 * @author qia.wu
 * @create 2019-11-05 10:28
 * @see com.webservice.demo.interceptor
 */
public class CxfOutInterceptor extends AbstractPhaseInterceptor<Message> {

	private static final Logger logger = LoggerFactory.getLogger(CxfOutInterceptor.class);

	public CxfOutInterceptor() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(Message message) {
		try {
			OutputStream os = message.getContent(OutputStream.class);
			CachedStream cs = new CachedStream();
			message.setContent(OutputStream.class, cs);
			message.getInterceptorChain().doIntercept(message);
			CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);
			InputStream in = csnew.getInputStream();

			String result = IOUtils.toString(in,"UTF-8");
			result = result.replace("|","&#13;\n");
			logger.info("返回给客户端值："+result);
			/** 这里可以对result做处理，如可以对result进行加密，把密文返回给客户端
			 处理完后同理，写回流中*/
			IOUtils.copy(new ByteArrayInputStream(result.getBytes("UTF-8")), os);

			cs.close();
			os.flush();
			message.setContent(OutputStream.class, os);
			csnew.close();
			os.close();
		} catch (Exception e) {
			logger.error("GatewayOutInterceptor异常",e);
		}
	}

	private class CachedStream extends CachedOutputStream {

		public CachedStream() {
			super();
		}

		protected void doFlush() throws IOException {
			currentStream.flush();
		}

		protected void doClose() throws IOException {
		}

		protected void onWrite() throws IOException {
		}

	}


}
