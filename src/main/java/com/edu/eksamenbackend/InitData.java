package com.edu.eksamenbackend;

import com.edu.eksamenbackend.entity.Album;
import com.edu.eksamenbackend.entity.AlbumCustomer;
import com.edu.eksamenbackend.entity.Customer;
import com.edu.eksamenbackend.entity.Store;
import com.edu.eksamenbackend.enums.Genre;
import com.edu.eksamenbackend.repository.AlbumCustomerRepository;
import com.edu.eksamenbackend.repository.AlbumRepository;
import com.edu.eksamenbackend.repository.CustomerRepository;
import com.edu.eksamenbackend.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Component
public class InitData {

    private final StoreRepository storeRepository;
    private final AlbumRepository albumRepository;
    private final CustomerRepository customerRepository;
    private final AlbumCustomerRepository albumCustomerRepository;

    public InitData(StoreRepository storeRepository,
                    AlbumRepository albumRepository,
                    CustomerRepository customerRepository,
                    AlbumCustomerRepository albumCustomerRepository) {
        this.storeRepository = storeRepository;
        this.albumRepository = albumRepository;
        this.customerRepository = customerRepository;
        this.albumCustomerRepository = albumCustomerRepository;
    }

    @PostConstruct
    public void initializeData() {
        // Opret butikker
        Store store1 = new Store("Vinyl Heaven", "Main Street 1", "Copenhagen", "1000");
        Store store2 = new Store("Music Galaxy", "Broadway 10", "Aarhus", "8000");
        Store store3 = new Store("Retro Records", "Record Lane 22", "Odense", "5000");
        Store store4 = new Store("Classic Tunes", "Harmony Road 5", "Aalborg", "9000");
        Store store5 = new Store("Jazz Junction", "Jazz Alley 3", "Esbjerg", "6700");
        Store store6 = new Store("Pop Paradise", "Pop Boulevard 15", "Herning", "7400");
        Store store7 = new Store("Rock Realm", "Rockway 42", "Horsens", "8700");
        Store store8 = new Store("Blues Bazaar", "Blues Drive 8", "Randers", "8900");
        Store store9 = new Store("Metal Mansion", "Heavy Street 66", "Kolding", "6000");
        Store store10 = new Store("Electro Empire", "Synth Lane 12", "Silkeborg", "8600");
        Store store11 = new Store("Vinyl Vault", "Record Row 4", "Fredericia", "7000");
        Store store12 = new Store("Retro Revival", "Vintage Avenue 9", "Vejle", "7100");
        Store store13 = new Store("Indie Island", "Indie Court 14", "Næstved", "4700");
        Store store14 = new Store("Folk Forest", "Folk Street 7", "Holstebro", "7500");
        Store store15 = new Store("Reggae Rhythm", "Reggae Road 11", "Roskilde", "4000");

        storeRepository.saveAll(List.of(store1, store2, store3, store4, store5, store6, store7, store8, store9, store10, store11, store12, store13, store14, store15));

        // Opret albums
        Album album1 = new Album("Abbey Road", "The Beatles", Genre.ROCK, true, store1);
        Album album2 = new Album("Thriller", "Michael Jackson", Genre.POP, true, store2);
        Album album3 = new Album("Dark Side of the Moon", "Pink Floyd", Genre.CLASSICAL, false, store3);
        Album album4 = new Album("Kind of Blue", "Miles Davis", Genre.JAZZ, true, store4);
        Album album5 = new Album("Back in Black", "AC/DC", Genre.ROCK, true, store5);
        Album album6 = new Album("Rumours", "Fleetwood Mac", Genre.ROCK, true, store6);
        Album album7 = new Album("Born to Run", "Bruce Springsteen", Genre.ROCK, true, store7);
        Album album8 = new Album("1989", "Taylor Swift", Genre.POP, false, store8);
        Album album9 = new Album("Hotel California", "Eagles", Genre.ROCK, true, store9);
        Album album10 = new Album("Electric Ladyland", "Jimi Hendrix", Genre.CLASSICAL, false, store10);
        Album album11 = new Album("Bad", "Michael Jackson", Genre.POP, true, store11);
        Album album12 = new Album("Graceland", "Paul Simon", Genre.POP, true, store12);
        Album album13 = new Album("The Wall", "Pink Floyd", Genre.CLASSICAL, false, store13);
        Album album14 = new Album("s", "Stevie Wonder", Genre.POP, true, store14);
        Album album15 = new Album("The Joshua Tree", "U2", Genre.ROCK, true, store15);

        albumRepository.saveAll(List.of(album1, album2, album3, album4, album5, album6, album7, album8, album9, album10, album11, album12, album13, album14, album15));

        // Opret kunder
        Customer customer1 = new Customer("John Doe", "john.doe@example.com", "12345678");
        Customer customer2 = new Customer("Jane Smith", "jane.smith@example.com", "87654321");
        Customer customer3 = new Customer("Alice Brown", "alice.brown@example.com", "98765432");
        Customer customer4 = new Customer("Bob White", "bob.white@example.com", "56473829");
        Customer customer5 = new Customer("Charlie Green", "charlie.green@example.com", "12039847");

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));


        // Registrer kunders interesse for albums
        AlbumCustomer albumCustomer1 = new AlbumCustomer(album1, customer1, java.time.LocalDate.now(), false, false);
        AlbumCustomer albumCustomer2 = new AlbumCustomer(album2, customer2, java.time.LocalDate.now(), true, true);
        AlbumCustomer albumCustomer3 = new AlbumCustomer(album3, customer3, java.time.LocalDate.now(), false, false);
        AlbumCustomer albumCustomer4 = new AlbumCustomer(album4, customer4, java.time.LocalDate.now(), true, false);
        AlbumCustomer albumCustomer5 = new AlbumCustomer(album5, customer5, java.time.LocalDate.now(), false, false);
        albumCustomerRepository.saveAll(List.of(albumCustomer1, albumCustomer2, albumCustomer3, albumCustomer4, albumCustomer5));
    }
}
