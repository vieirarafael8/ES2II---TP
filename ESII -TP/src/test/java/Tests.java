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

        //Inicialização do sistema
        brs = new BikeRentalSystem(1);

        //User com o id=0
        brs.registerUser(0, "Rafael", 1);
        //User com o id=1
        brs.registerUser(1, "Gabriel", 1);

        //Adição de um lock
        brs.addLock(1, 0);

        //Adição de uma bicicleta
        brs.addBicycle(1, 0, 0);

    }

    @Test
    public void testRegisterUserExist() {

        //Excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {

            brs.registerUser(0, "Rafael", 1);

            //O teste é válido
        }, "Should Throw Exception: UserAlreadyExists");

    }

      @Test
    public void testRegisterUserValido() throws UserAlreadyExists{

        brs.registerUser(2,"Nuno", 2);

        //O teste é válido
       assertEquals(2, brs.getUsers().get(2).getIDUser());

    }

    //Testesw getBicycle
    @Test
    public void testGetBicycle1() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(0, 1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(0, brs.getBicycle(1, 1, 0));

       brs.getBicycle(1, 1, 0);
    }

    @Test
    public void testGetBicycle2() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(0, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(1, 1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(-1, brs.getBicycle(1,0,0));

        brs.getBicycle(1, 0, 0);
    }
    @Test
    public void testGetBicycle3() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(0, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(0, 1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(-1, brs.getBicycle(0,1,0));

        brs.getBicycle(0, 1, 0);
    }
    @Test
    public void testGetBicycle4() throws UserDoesNotExists {
        //Adicionar
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(1, 1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(-1, brs.getBicycle(1,1,-1));

        brs.getBicycle(1, 1, -1);
    }
    @Test
    public void testGetBicycle5() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(1, 1, 1);

        //Testar se retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(0, brs.getBicycle(1,1,1));

        brs.getBicycle(1, 1, 1);
    }

    @Test
    public void testVerifyCredit1() {

        //Adiciona um crédito
        brs.addCredit(-1, 1);

        assertTrue(brs.verifyCredit(-1));
    }

    @Test
    public void testVerifyCredit2() {

        //Adiciona um crédito
        brs.addCredit(0, 1);

        assertTrue(brs.verifyCredit(0));
    }
    @Test
    public void testVerifyCredit3() {

        //Adiciona um crédito
        brs.addCredit(1, 1);

        assertTrue(brs.verifyCredit(1));
    }

    @Test
    public void testAddCredit1() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(1);

        brs.addCredit(1, 1);

        //O crédito foi adicionado com sucesso
        assertEquals(1, u.getCredit(), "Expected = 1, Actual = " + u.getCredit());
    }
    @Test
    public void testAddCredit2() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(0);

        brs.addCredit(0, 1);

        //O crédito foi adicionado com sucesso
        assertEquals(1, u.getCredit(), "Expected = 1, Actual = " + u.getCredit());
    }
    @Test
    public void testAddCredit3() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(-1);

        brs.addCredit(-1, 1);

        //O crédito foi adicionado com sucesso
        assertEquals(1, u.getCredit(), "Expected = 1, Actual = " + u.getCredit());
    }
    @Test
    public void testAddCredit4() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(1);

        brs.addCredit(1, 0);

        //O crédito foi adicionado com sucesso
        assertEquals(1, u.getCredit(), "Expected = 1, Actual = " + u.getCredit());
    }

    @Test
    public void testRegisterUser1() {
        //Verificar se a excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {
            brs.registerUser(1, "Gabriel", 1);
        }, "Should Throw Exception: UserAlreadyExists"); //O teste é válido

    }

    @Test
    public void testRegisterUser2() {
        //Verificar se a excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {
            brs.registerUser(-1, "Nuno", 1);
        }, "Should Throw Exception: UserAlreadyExists"); //O teste é válido

    }

    @Test
    public void testRegisterUser3() {
        //Verificar se a excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {
            brs.registerUser(0, "Rafael", 1);
        }, "Should Throw Exception: UserAlreadyExists"); //O teste é válido

    }
    @Test
    public void testReturnBicycle() throws UserDoesNotExists {


        //Testar se retorna -1 se depósito não existir
        assertEquals(0, brs.getBicycle(1, 1, 1));

        //Senão existirem lugares de entrega livre
        assertEquals(0, brs.returnBicycle(1, 1, 1));

        //Testar se retorna o saldo atual do cliente, quando se calcula o pagamento
        assertEquals(0, brs.verifyCredit(1));
    }
}
