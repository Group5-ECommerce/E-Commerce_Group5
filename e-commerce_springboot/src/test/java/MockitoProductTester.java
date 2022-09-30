import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.entity.Product;
import com.hcl.repo.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class MockitoProductTester {
	@Mock
	ProductRepository repo;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void addProduct() {
		Product added = new Product();
		repo.save(added);
		verify(repo, times(1)).save(added);
	}

	@Test
	public void deleteProduct() {
		Product deleted = new Product();
		repo.save(deleted);
		verify(repo, times(1)).save(deleted);
		repo.deleteById(deleted.getProductId());
		verify(repo, times(1)).deleteById(deleted.getProductId());
	}
}
