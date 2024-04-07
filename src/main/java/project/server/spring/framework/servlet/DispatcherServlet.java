package project.server.spring.framework.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.context.ApplicationContext;
import project.server.spring.framework.exception.GlobalExceptionHandler;
import project.server.spring.framework.exception.NoHandlerFoundException;
import project.server.spring.framework.exception.ServletException;
import project.server.spring.framework.http.Cookie;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.servlet.handler.HandlerAdapter;
import project.server.spring.framework.servlet.handler.HandlerExecutionChain;
import project.server.spring.framework.servlet.handler.HandlerMapping;
import project.server.spring.framework.util.PageProcessor;

public class DispatcherServlet extends FrameworkServlet {

	private final List<HandlerMapping> handlerMappings;
	private final GlobalExceptionHandler exceptionHandler;
	private final List<HandlerAdapter> handlerAdapters;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final String REDIRECT_INDEX = "redirect:";

	public DispatcherServlet() {
		this.exceptionHandler = new GlobalExceptionHandler();
		this.handlerMappings = ApplicationContext.getBeans(HandlerMapping.class);
		this.handlerAdapters = ApplicationContext.getBeans(HandlerAdapter.class);
	}

	@Override
	protected void doService(
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		doDispatch(request, response);
	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HandlerExecutionChain handler = getHandler(request);
			if (handler == null) {
				throw new NoHandlerFoundException();
			}
			HandlerAdapter handlerAdapter = getHandlerAdapter(handler.getHandler());
			if (!handler.applyPreHandle(request, response)) {
				return;
			}
			ModelAndView modelAndView = handlerAdapter.handle(request, response, handler.getHandler());
			handleSession(request, response);
			processDispatchResult(response, modelAndView);
		} catch (Exception exception) {
			exceptionHandler.resolveException(response, exception);
		}

	}

	private void resolveResponseBody(HttpServletResponse response) {
		response.setStatus(200);
		response.flush(null);
	}

	private void handleSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		try {
			session = request.getSession();
		} catch (Exception e) {
			Cookie sessionCookie = Cookie.createSessionCookie("");
			sessionCookie.setMaxAge(0);
			response.addCookie(sessionCookie);
		}
		if (session != null && session.getId() != null) {
			String sessionId = session.getId();
			Cookie cookie = Cookie.createSessionCookie(sessionId);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(600);
			response.addCookie(cookie);
		}
	}

	private void resolveView(ModelAndView modelAndView, HttpServletResponse response) throws IOException {
		String viewName = modelAndView.getView();
		if (viewName.startsWith(REDIRECT_INDEX)) {
			String redirectUrl = viewName.substring(REDIRECT_INDEX.length());
			response.sendRedirect(redirectUrl);
			return;
		}
		PageProcessor pageProcessor = new PageProcessor();
		String page = pageProcessor.read(getViewName(viewName));
		if (modelAndView.hasModelMap()) {
			page = pageProcessor.changeAttributes(modelAndView, page);
		}
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(200);
		response.flush(page.getBytes(StandardCharsets.UTF_8));
	}

	private String getViewName(String viewName) {
		if (viewName.startsWith(REDIRECT_INDEX)) {
			return viewName.substring(REDIRECT_INDEX.length());
		}
		return viewName + ".html";
	}

	private HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		for (HandlerMapping mapping : this.handlerMappings) {
			HandlerExecutionChain handler = mapping.getHandler(request);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}

	private HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
		for (HandlerAdapter adapter : this.handlerAdapters) {
			if (adapter.supports(handler)) {
				return adapter;
			}
		}
		throw new ServletException("No adapter for handler [" + handler
			+ "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
	}

	private void processDispatchResult(HttpServletResponse response, ModelAndView modelAndView) throws IOException {
		if (modelAndView.getView() == null) {
			resolveResponseBody(response);
			return;
		}
		resolveView(modelAndView, response);
	}
}
