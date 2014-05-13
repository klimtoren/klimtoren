package be.wolkmaan.klimtoren.shared;

import com.google.common.collect.Lists;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author karl
 */
@Slf4j
@Component
public class JsonViewSupportFactoryBean implements InitializingBean {

	@Autowired
	private RequestMappingHandlerAdapter adapter;
	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodReturnValueHandler> returnHandlers = adapter.getReturnValueHandlers();
                List<HandlerMethodReturnValueHandler> handlers = Lists.newArrayList(returnHandlers);
		decorateHandlers(handlers);
		adapter.setReturnValueHandlers(handlers);
	}
	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor)
			{
				ViewInjectingReturnValueHandler decorator = new ViewInjectingReturnValueHandler(handler);
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);
				log.info("JsonView decorator support wired up");
				break;
			}
		}        

	}
}
