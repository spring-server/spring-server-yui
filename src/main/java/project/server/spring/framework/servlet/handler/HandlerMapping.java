package project.server.spring.framework.servlet.handler;

import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.servlet.HttpServletRequest;

@Component
public interface HandlerMapping {
	HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
