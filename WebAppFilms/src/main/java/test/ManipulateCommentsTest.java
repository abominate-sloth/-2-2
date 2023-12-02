import static org.junit.jupiter.api.Assertions.*;

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
import com.bsuir.kirillpastukhou.webappfilms.controller.Page;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDao;
import com.bsuir.kirillpastukhou.webappfilms.dao.DBDaoFactory;
import com.bsuir.kirillpastukhou.webappfilms.exception.CommandException;
import com.bsuir.kirillpastukhou.webappfilms.exception.DatabaseDAOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class ManipulateCommentsTest {
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpSession session;
	
	@Mock
	User user;
	
	@Test
	public void processAdd() {
		DBDao dao = null;
		try {
			dao = DBDaoFactory.getInstance().getDao(DBDaoFactory.DaoType.MySQL);
			
			ArrayList<Comment> list = dao.getComments(1); 
			Integer size = list.size();
			
			when(request.getSession()).thenReturn(session);
			when(session.getAttribute("user")).thenReturn(user);
			when(session.getAttribute("lang")).thenReturn("en_US");
			when(user.getLogin()).thenReturn("user");
			when(request.getParameter("film")).thenReturn("1");
			when(request.getParameter("stars")).thenReturn("5");
			when(request.getParameter("comment")).thenReturn("SUPER!");
			
			AddCommentCommand commandTest = new AddCommentCommand();
			try {
				String result = commandTest.execute(request);
				assertEquals(result, Page.FILM);
			} catch (CommandException e) {
				e.printStackTrace();
			}
			
			ArrayList<Comment> newList = dao.getComments(1); 
			Integer newSize = newList.size();
			
			assertEquals(size, newSize - 1);
			
		} catch (DatabaseDAOException e) {}
	}
	
	@Test
	public void processEdit() {
		DBDao dao = null;
		try {
			dao = DBDaoFactory.getInstance().getDao(DBDaoFactory.DaoType.MySQL);
			
			ArrayList<Comment> list = dao.getComments(1); 
			Comment lastComment = list.get(list.size() - 1);
			Integer id = lastComment.getId();
			
			when(request.getSession()).thenReturn(session);
			when(session.getAttribute("user")).thenReturn(user);
			when(session.getAttribute("lang")).thenReturn("en_US");
			when(request.getParameter("film")).thenReturn("1");
			when(request.getParameter("reviewId")).thenReturn(Integer.toString(id));
			when(request.getParameter("stars")).thenReturn("4");
			when(request.getParameter("comment")).thenReturn("WELL DONE");
			
			EditCommentCommand commandTest = new EditCommentCommand();
			try {
				String result = commandTest.execute(request);
				assertEquals(result, Page.FILM);
			} catch (CommandException e) {
				e.printStackTrace();
			}
			list = dao.getComments(1); 
			lastComment = list.get(list.size() - 1);
			
			assertEquals(lastComment.getText(), "WELL DONE");
			
		} catch (DatabaseDAOException e) {}
	}
	
	@Test
	public void processDelete() {
		DBDao dao = null;
		try {
			dao = DBDaoFactory.getInstance().getDao(DBDaoFactory.DaoType.MySQL);
			
			ArrayList<Comment> list = dao.getComments(1); 
			Comment lastComment = list.get(list.size() - 1);
			Integer id = lastComment.getId();
			Integer size = list.size();
			
			when(request.getSession()).thenReturn(session);
			when(request.getParameter("film")).thenReturn("1");
			when(session.getAttribute("user")).thenReturn(user);
			when(session.getAttribute("lang")).thenReturn("en_US");
			when(request.getParameter("reviewId")).thenReturn(Integer.toString(id));
			
			DeleteCommentCommand commandTest = new DeleteCommentCommand();
			try {
				String result = commandTest.execute(request);
				assertEquals(result, Page.FILM);
			} catch (CommandException e) {
				e.printStackTrace();
			}
			
			ArrayList<Comment> newList = dao.getComments(1); 
			Integer newSize = newList.size();
			
			assertEquals(size - 1, newSize);
			
		} catch (DatabaseDAOException e) {}
	}
}
