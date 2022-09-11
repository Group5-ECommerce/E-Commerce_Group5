//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Supplier;
//
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.hcl.entity.Product;
//import com.hcl.entity.User;
//import com.hcl.repo.OrderRepository;
//import com.hcl.repo.ProductRepository;
//import com.hcl.repo.UserRepository;
//import com.hcl.service.OrderService;
//import com.hcl.service.UserService;
//
//@ExtendWith(MockitoExtension.class)
//public class MockitoTester 
//{
//	@Mock
//	UserService service;
//	
//	@Mock
//	UserRepository repo;
//	
//	@BeforeEach
//	public void init()
//	{
//		MockitoAnnotations.openMocks(this);
//	}
//	
//	@Test
//	public void testAddUser()
//	{
//		User user = new User();
//		service.addUser(user, 0);
//		verify(service, times(1)).addUser(user, 0);
//		System.out.println(service.getAllUsers());
//	}
//	
//	@Test
//	public void testDeleteUser()
//	{
//	
//		User deleted = new User();
//		service.saveUser(deleted);
//		verify(service, times(1)).saveUser(deleted);
//		service.deleteUser(0);
//		verify(service, times(1)).deleteUser(deleted.getUserId());
//	}
//}
