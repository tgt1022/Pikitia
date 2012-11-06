package de.htw.fb4.bilderplattform.business;

import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.dao.UserDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

class UserServiceImpl implements IUserService {

	@Override
	public User getUserByName(String username) throws UsernameNotFoundException {
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		List<User> allUser = userDAO.getAllUser();
		for (User usr : allUser) {
			if (usr.getUsername().equals(username))
				return usr;
		}
		throw new UsernameNotFoundException(username);
	}

	@Override
	public List<User> getAllUser() throws UsernameNotFoundException {
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		return userDAO.getAllUser();
	}

	@Override
	@Transactional
	public void saveOrUpdateUser(User user) {
		if (user == null) {
			return;
		}
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		user.setLastUpdateDate(Calendar.getInstance().getTime());
		userDAO.saveUser(user);
	}

	@Override
	public void deleteUser(User user) {
		if (user == null) {
			return;
		}
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		userDAO.deleteUser(user);
	}

}
