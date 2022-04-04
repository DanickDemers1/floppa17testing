package ulaval.glo2003.application.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.seller.Seller;

import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SellerShortAssemblerTest {
    Seller seller;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
    }

    @Test
    void givenSeller_whenSellerShortAssemblerToDto_thenReturnSellerDto() {
        var sellerShortDto = SellerShortAssembler.toDto(seller);

        assertThat(sellerShortDto.id).isEqualTo(seller.getId().toString());
        assertThat(sellerShortDto.name).isEqualTo(seller.getName());
    }

    @Test
    void givenNullSeller_whenSellerShortAssemblerToDto_thenThrowNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> SellerShortAssembler.toDto(null));
    }
}
