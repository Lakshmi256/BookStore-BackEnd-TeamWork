package com.bookstore.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;
import com.bookstore.service.IBookService;

/***************************************************************************************************
 * BookController By using the object reference of BookService class This Controller Has fuctionallity to Add Book,deleteBook,
 * Updated Book,Get BookList And upload book Images functionalites Apis.   
 *
 * @author Rupesh Patil
 * @version 2.0
 * @created 2020-04-12
 * @updated 5/06/2020
 * @see {@link IBookService} implementation of all the required services & functionality
 * @see {@link BookResponse} if there is any type of response it will reflect out
 * 
 ******************************************************************************************************/
@RestController
@CrossOrigin("*")
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	IBookService bookservice;
	
	@PostMapping("/add")
	public ResponseEntity<BookResponse> addBook(@RequestBody Book book){
		return bookservice.addBook(book);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BookResponse> updateBook(@RequestBody Book book,@RequestParam("bookname") String bookName){
		return bookservice.updateBookDetails(bookName, book);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BookResponse> removeBook(@RequestParam("bookId") int bookId){
		return bookservice.removeBook(bookId);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<BookResponse> uploadImage(@RequestParam("imageFile") MultipartFile file,@RequestParam("bookId") int  bookId) throws IOException {
		String message = "";
	    try {
	      bookservice.saveBookImage(file,bookId);
	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new BookResponse(202, message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new BookResponse(203,"error"));
	    }
	}
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<BookResponse> getAllBooks(){
		return bookservice.getAllBooks();
	}
	@GetMapping("/sellerBooks")
	public ResponseEntity<BookResponse> getSellerBooks(){
		return bookservice.getSellerBooks();
	}
}
