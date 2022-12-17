package site.metacoding.junitproject.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;
import site.metacoding.util.MailSender;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
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
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }
}
