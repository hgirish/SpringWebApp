package com.gohands.springwebapp.bootstrap;

import com.gohands.springwebapp.domain.Author;
import com.gohands.springwebapp.domain.Book;
import com.gohands.springwebapp.domain.Publisher;
import com.gohands.springwebapp.repositories.AuthorRepository;
import com.gohands.springwebapp.repositories.BookRepository;
import com.gohands.springwebapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private  final BookRepository bookRepository;
    private   final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Publisher apress = new Publisher("Apress","123 main st","New York","NY","11345");
        publisherRepository.save(apress);

        Publisher sfg = new Publisher();
        sfg.setName("SFG Publishing");
        sfg.setCity("St Petersburg");
        sfg.setState("FL");

        publisherRepository.save(sfg);

        Author eric = new Author("Eric","Evans");
        Book ddd = new Book("Domain Driven Design","123123");
        add(apress, eric, ddd);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "393939393");
        add(sfg, rod, noEJB);


        System.out.println("Started in Bootstrap");
        System.out.println("Number of Books: " + bookRepository.count());
        System.out.println("Number of Authors: " + authorRepository.count());
        System.out.println("Number of Publishers: " + publisherRepository.count());
        System.out.println("Books by Apress: " + apress.getBooks().size());
    }

    private void add(Publisher publisher, Author author, Book book) {
        author.getBooks().add(book);
        book.getAuthors().add(author);
        book.setPublisher(publisher);
        publisher.getBooks().add(book);

        authorRepository.save(author);
        bookRepository.save(book);
        publisherRepository.save(publisher);
    }
}
