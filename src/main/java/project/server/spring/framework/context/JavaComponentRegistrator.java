package project.server.spring.framework.context;

import project.server.spring.app.core.controller.base.BaseController;
import project.server.spring.app.core.controller.user.LoginController;
import project.server.spring.app.core.controller.user.UserController;
import project.server.spring.app.core.repository.user.UserRepository;

public class JavaComponentRegistrator implements ComponentRegistrator{
	private final BeanRegistry beanContainer;

	public JavaComponentRegistrator(BeanRegistry beanContainer) {
		this.beanContainer = beanContainer;
	}

	@Override
	public void start() {
		//Repository
		UserRepository userRepository = new UserRepository();
		beanContainer.addBean(UserRepository.class, userRepository);

		//Controller
		UserController userController = new UserController(userRepository);
		beanContainer.addBean(UserController.class, userController);
		LoginController loginController = new LoginController(userRepository);
		beanContainer.addBean(LoginController.class, loginController);
		BaseController baseController = new BaseController();
		beanContainer.addBean(BaseController.class, baseController);
	}
}
