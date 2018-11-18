import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import Exceptions.UserAlreadyExists;
import Exceptions.UserDoesNotExists;
import Models.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    BikeRentalSystem brs;

    @BeforeAll
    public static void initAll() {

        System.out.println("Testes: ");

    }

    @BeforeEach
    public void setUp() throws UserAlreadyExists {

        //Inicialização do sistema do aluguer de bicicletas
        brs = new BikeRentalSystem(1);

        //Adição de um User com o id=0
        brs.registerUser(0, "Rafael", 1);

        //Adição de um lock com id=0 e um Deposit com o id=0
        brs.addLock(0, 0);

        //Adição de uma bicicleta com o id=0
        brs.addBicycle(0, 0, 0);

    }

    @Test
    public void testRegisterUser() {

        //Verificação se a excepção é lançada com o id=0
        assertThrows(UserAlreadyExists.class, () -> {

            brs.registerUser(0, "Rafael", 1);

            //O teste é válido, pois o utilizador Rafael com o id 0, já existe
        }, "Should Throw Exception: UserAlreadyExists");

    }

    @Test
    public void testVerifyCredit() throws UserAlreadyExists {

        //Adiciona um crédito ao user com IDUser=0
        brs.addCredit(0, 1);

        //Adiciona mais um user sem créditos
        brs.registerUser(1, "Nuno", 2);

        //Verificação se retorna false ao verificar se é possivel
        // adicionar créditos a um utilizador não existente na lista de users ou se um user não tem creditos

        assertAll("Should return False if user in IDUser=2 does not exist or there is no credits in IDUser=1",
                () -> assertFalse(brs.verifyCredit(1)),
                () -> assertFalse(brs.verifyCredit(2))
        );

        //Verificação se retorna true ao verificar se um user tem créditos suficientes
        assertTrue(brs.verifyCredit(0));

    }

}
