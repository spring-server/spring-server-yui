package project.server.spring.app.core.global.auth;

import static project.server.spring.app.core.global.error.ErrorMessage.*;

import lombok.extern.slf4j.Slf4j;
import project.server.spring.app.core.global.exception.AuthenticationException;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.servlet.handler.HandlerInterceptor;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptor {
	private static final String USER_ID = "user_id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		log.info("authentication is required : request {}", request.getRequestURI());
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute(USER_ID) == null) {
			throw new AuthenticationException(AUTHENTICATION_FAILED.getMessage());
		}
		return true;
	}
}
