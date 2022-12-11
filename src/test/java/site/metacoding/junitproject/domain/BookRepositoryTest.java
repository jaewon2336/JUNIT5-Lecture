package site.metacoding.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    // @BeforeAll // 테스트 시작 전에 한번만 실행
    @BeforeEach // 각 메서드 테스트 시작 전에 한번씩 실행
    public void 데이터준비() {
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    } // 트랜잭션이 종료됐다면 테스트 불가능! 트랜잭션의 범위가 어떻게 묶여있는걸까?
      // (O) 가정 1 : [데이터준비() + 책등록()], [데이터준비() + 책목록()] -> 사이즈 1
      // (X) 가정 2 : [데이터준비() + 책등록() + 데이터준비() + 책목록()] -> 사이즈 2

    // 1. 책 등록
    @Test
    public void 책등록_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book); // 영속화 된 엔티티 Persistance

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // 트랜잭션 종료(저장된 데이터를 초기화)

    // 2. 책 목록보기
    @Test
    public void 책목록보기_test() {
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        List<Book> booksPS = bookRepository.findAll();

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    } // 트랜잭션 종료(저장된 데이터를 초기화)

    // 3. 책 한건보기
    @Test
    public void 책한건보기_test() {
        // given
        String title = "junit";
        String author = "겟인데어";

        Long id = 1L;

        // when
        Book bookPS = bookRepository.findById(id).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // 트랜잭션 종료(저장된 데이터를 초기화)

    // 4. 책 삭제

    // 5. 책 수정
}
