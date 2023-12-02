import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bsuir.kirillpastukhou.webappfilms.beans.Comment;
import com.bsuir.kirillpastukhou.webappfilms.beans.User;
import com.bsuir.kirillpastukhou.webappfilms.command.impl.AddCommentCommand;
import com.bsuir.kirillpastukhou.webappfilms.command.impl.DeleteCommentCommand;
import com.bsuir.kirillpastukhou.webappfilms.command.impl.EditCommentCommand;
import com.bsuir.kirillpastukhou.webappfilms.command.impl.LogoutCommand;
import com.bsuir.kirillpastukhou.webappfilms.command.impl.RegistrationCommand;
import com.bsuir.kirillpastukhou.webappfilms.command.impl.SignInCommand;
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class ManipulateAuthorizationTest {
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpSession session;

	@Test
	public void processRegistration() {
		DBDao dao = null;
		try {
			dao = DBDaoFactory.getInstance().getDao(DBDaoFactory.DaoType.MySQL);
			
			Integer usersNum = dao.getUsersNumber(); 
			
			when(request.getSession()).thenReturn(session);
			when(request.getParameter("password")).thenReturn("01");
			when(request.getParameter("login")).thenReturn("test_user");
			
			RegistrationCommand commandTest = new RegistrationCommand();
			try {
				String result = commandTest.execute(request);
				assertEquals(result, Page.USER);
			} catch (CommandException e) {
				e.printStackTrace();
			}
			
			Integer newUsersNum = dao.getUsersNumber(); 
			
			assertEquals(usersNum, newUsersNum - 1);
			
		} catch (DatabaseDAOException e) {}
	}
	
	@Test
	public void processSignin() {
		DBDao dao = null;
		try {
			dao = DBDaoFactory.getInstance().getDao(DBDaoFactory.DaoType.MySQL);

			when(request.getSession()).thenReturn(session);
			when(request.getParameter("password")).thenReturn("01");
			when(request.getParameter("login")).thenReturn("test_user");
			
			SignInCommand commandTest = new SignInCommand();
			try {
				String result = commandTest.execute(request);
				assertEquals(result, Page.USER);
			} catch (CommandException e) {
				e.printStackTrace();
			}
			
			verify(session, times(1)).setAttribute(anyString(), any());
			verify(request, times(4)).setAttribute(anyString(), any());
			
			dao.deleteUser("test_user");
			
		} catch (DatabaseDAOException e) {}
	}
	
	@Test
	public void processLogout() {
		when(request.getSession()).thenReturn(session);
		try {
			LogoutCommand logout = new LogoutCommand();
			String result = logout.execute(request);
			assertEquals(result, Page.INDEX);
		} catch (CommandException e) {}
		verify(session, times(1)).setAttribute("user", null);
	}
		
}
