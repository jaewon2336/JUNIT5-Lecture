package site.metacoding.junitproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookListRespDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        if (bookPS != null) {
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        } else {
            throw new RuntimeException("저장에 실패하였습니다.");
        }
        return bookPS.toDto();
    }

    // 2. 책목록보기
    public BookListRespDto 책목록보기() {
        List<BookRespDto> dtos = bookRepository.findAll().stream()
                // .map((bookPS) -> new BookRespDto().toDto(bookPS))
                // .map((bookPS) -> bookPS.toDto())
                .map(Book::toDto) // 메서드 참조식
                .collect(Collectors.toList());

        // print
        dtos.stream().forEach((dto) -> {
            System.out.println("==============본코드");
            System.out.println(dto.getId());
            System.out.println(dto.getTitle());
        });
        BookListRespDto bookListRespDto = BookListRespDto.builder().bookList(dtos).build();
        return bookListRespDto;
    }

    // 3. 책한건보기
    public BookRespDto 책한건보기(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            return bookOP.get().toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책삭제하기
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id) { // 4
        bookRepository.deleteById(id); // 1, 2, 3
    }

    // 5. 책수정하기
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책수정하기(Long id, BookSaveReqDto dto) { // id, title, author
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor()); // return 값이 없어서 update 메서드 테스트가 안됨
            return bookPS.toDto(); // 테스트를 위해 return
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    } // 메서드 종료시에 더티 체킹(flush -> commit)으로 update
}
