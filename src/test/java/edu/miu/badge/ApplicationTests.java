package edu.miu.badge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testmy(){
		Integer a = 3;
		Stream<Integer> inValue = Stream.of(1,2,3,4);
		boolean b = inValue.anyMatch(in -> in.equals(a));
		System.out.println(" i am true "+b);
	}

}
