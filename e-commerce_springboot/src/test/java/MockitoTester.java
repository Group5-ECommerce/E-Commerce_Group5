import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.ProductRepository;
import com.hcl.repo.UserRepository;
import com.hcl.service.OrderService;
import com.hcl.service.UserService;

@ExtendWith(MockitoExtension.class)
public class MockitoTester 
{
	@Mock
	UserService service;
	
	@Mock
	UserRepository repo;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testUser1()
	{
		List<User> list = new ArrayList<User>();
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
         
        list.add(u1);
        list.add(u2);
        list.add(u3);
        
        when(service.getAllUsers()).thenReturn(list);
        
        List<User> uList = service.getAllUsers();
        
        assertEquals(3, uList.size());
        verify(service, times(1)).getAllUsers();
	}
	
	@Test
	public void testUser2()
	{
		User user = new User();
		service.saveUser(user);
		verify(service, times(1)).saveUser(user);
	}
	
	
}
