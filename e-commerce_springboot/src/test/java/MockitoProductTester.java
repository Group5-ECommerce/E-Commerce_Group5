import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
import com.hcl.repo.OrderRepository;
import com.hcl.repo.ProductRepository;
import com.hcl.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class MockitoProductTester 
{
	@Mock
	ProductRepository repo;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void addProduct()
	{
		Product added = new Product();
		repo.save(added);
		verify(repo, times(1)).save(added);
	}
	
	@Test
	public void deleteProduct()
	{
		Product deleted = new Product();
		repo.save(deleted);
		verify(repo, times(1)).save(deleted);
		repo.deleteById(deleted.getProductId());
		verify(repo, times(1)).deleteById(deleted.getProductId());
	}
}
