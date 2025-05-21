

import net.hamza.banque.model.Carte;
import net.hamza.banque.model.Client;
import net.hamza.banque.repository.CarteRepo;
import net.hamza.banque.repository.ClientRepo;
import net.hamza.banque.service.CarteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarteServiceImplTest {

    @Mock
    private CarteRepo carteRepository;

    @Mock
    private ClientRepo clientRepository;

    @InjectMocks
    private CarteServiceImpl carteService;

    @Test
    void findCarteByClientEmail_whenClientExists_returnsCarte() {
        // Arrange
        String email = "test@example.com";
        Client client = new Client();
        Carte carte = new Carte();
        client.setCarte(carte);

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));

        // Act
        Optional<Carte> result = carteService.findCarteByClientEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(carte, result.get());
        verify(clientRepository).findByEmail(email);
    }

    @Test
    void findCarteByClientEmail_whenClientExistsButHasNoCarte_returnsEmptyOptional() {
        // Arrange
        String email = "test@example.com";
        Client client = new Client();
        client.setCarte(null);

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));

        // Act
        Optional<Carte> result = carteService.findCarteByClientEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(clientRepository).findByEmail(email);
    }

    @Test
    void findCarteByClientEmail_whenClientDoesntExist_returnsEmptyOptional() {

        String email = "nonexistent@example.com";
        when(clientRepository.findByEmail(email)).thenReturn(Optional.empty());


        Optional<Carte> result = carteService.findCarteByClientEmail(email);


        assertFalse(result.isPresent());
        verify(clientRepository).findByEmail(email);
    }
}