package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;

@ExtendWith(MockitoExtension.class) // 가짜 환경 생성
public class BookServiceTest {

    @InjectMocks // BookService를 IoC 컨테이너에 띄우고 가짜 객체로 의존성 주입
    private BookService bookService;

    @Mock // 가짜
    private BookRepository bookRepository;

    @Mock // 가짜
    private MailSender mailSender; // 부모타입으로 메모리에 띄우기

    @Test
    public void 책등록하기_test() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        // stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // then
        // assertEquals("junit강의", bookRespDto.getTitle());
        // assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void 책목록보기_test() {
        // given

        // stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "겟인데어"));
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookRespDto> bookRespDtoList = bookService.책목록보기();

        // print
        bookRespDtoList.stream().forEach((dto) -> {
            System.out.println("==============테스트");
            System.out.println(dto.getId());
            System.out.println(dto.getTitle());
        });

        // then
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit강의");
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("메타코딩");
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring강의");
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("겟인데어");
    }

    @Test
    public void 책한건보기_test() {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책한건보기(id);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_test() {
        // given
        Long id = 1L;
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("spring강의");
        bookSaveReqDto.setAuthor("겟인데어");

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책수정하기(id, bookSaveReqDto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(bookSaveReqDto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(bookSaveReqDto.getAuthor());
    }
}
