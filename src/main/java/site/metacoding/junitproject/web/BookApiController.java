package site.metacoding.junitproject.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService; // 컴포지션 = has 관계

    // 1. 책등록
    // 스프링 기본 파싱 전략 (x-www-form-urlencoded) key=value&key=value
    // json { "key": value, "key": value } -> json 타입으로 파싱할 때 @RequestBody 사용
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            return new ResponseEntity<>(CMRespDto.builder().code(-1).msg(errorMap.toString()).body(bookRespDto).build(),
                    HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build(),
                HttpStatus.CREATED); // 201
    }

    // 2. 책목록보기
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3. 책한건보기
    public ResponseEntity<?> getBookOne() {
        return null;
    }

    // 4. 책삭제하기
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5. 책수정하기
    public ResponseEntity<?> updateBook() {
        return null;
    }
}
