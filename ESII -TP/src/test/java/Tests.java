import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import Exceptions.UserAlreadyExists;
import Exceptions.UserDoesNotExists;
import Models.User;
import org.junit.jupiter.api.*;
import java.lang.IndexOutOfBoundsException;

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

    // GET BICYCLE

    /*
    Teste Requisitar bicicleta existente com starttime=0
    */
    @Test
    public void testGetBicycleEXISTEBICICLETA() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(1, 1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(0, brs.getBicycle(1, 1, 0));

       brs.getBicycle(1, 1, 0);
    }
/*
 Teste para IDUser=-1
 */
    @Test
    public void testGetBicycleIDUSERMENOSUM() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(0, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(1, 1, 1);
        assertThrows(UserDoesNotExists.class, () -> {
                    //Retorna -1 se tentar requisitar uma bicicleta sem depósito
                    assertEquals(-1, brs.getBicycle(1, -1, 0));
                },"Should throw exception: UserDoesNotExists");

        brs.getBicycle(1, 0, 0);
    }
    /*
    Teste com depósito inexistente
     */
    @Test
    public void testGetBicycleNAOEXISTEDEPOSITO() throws UserDoesNotExists {
        //Adicionar Crédito
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(0, 1);

        //Adição de uma Bicicleta
        brs.addBicycle(1, 1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem depósito
        assertEquals(-1, brs.getBicycle(0,1,0));

        brs.getBicycle(0, 1, 0);
    }
    /*
    Teste não há bicicletas disponiveis
     */
    @Test
    public void testGetBicycleSEMBICICLETASDISP() throws UserDoesNotExists {
        //Adicionar
        brs.addCredit(1, 1);

        //Adição de um Lock
        brs.addLock(1, 1);

        //Retorna -1 se tentar requisitar uma bicicleta sem lugares ativos
        assertEquals(-1, brs.getBicycle(0,1,1));

        brs.getBicycle(0, 1, 0);
    }
    /*
    Teste requisita bicicleta com starttime=1
     */
    @Test
    public void testGetBicycleSTARTUM() throws UserDoesNotExists {
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


    // VERIFY CREDIT


    /*
    Teste com  IDUser=-1 e amount=1
     */
    @Test
    public void testVerifyCreditUSERMENOSUM() {

        //Adiciona um crédito
        brs.addCredit(-1, 1);

        assertFalse(brs.verifyCredit(-1));
    }
/*
Teste com  IDUser=0 e amount=1
 */
    @Test
    public void testVerifyCreditUSERZERO() {

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



    // ADD CREDIT



    /*
    Teste utilizador com IDUser=1 e  amount=1
     */
    @Test
    public void testAddCredit1() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(1);

        brs.addCredit(1, 1);

        //O crédito foi adicionado com sucesso
        assertEquals(1, u.getCredit(), "Expected = 1, Actual = " + u.getCredit());
    }

    /*
   Teste utilizador com IDUser=0 e amount=1
    */
    @Test
    public void testAddCredit2() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(0);

        brs.addCredit(0, 1);

        //O crédito foi adicionado com sucesso
        assertEquals(1, u.getCredit(), "Expected = 1, Actual = " + u.getCredit());
    }
    /*
    Teste utilizador com IDUser=-1 e amount =1
     */
    @Test
    public void testAddCreditUserMenosUM(){

        assertThrows(IndexOutOfBoundsException.class, () -> {


        User u = brs.getUsers().get(-1);

        brs.addCredit(-1, 1);

        assertEquals(null, u.getCredit(), "Expected = null, Actual= "+ u.getCredit());

        }, "Shoud throw Exception: IndexOutOfBoundsException");


    }

    /*
    Teste com IDUser=1 e amount=0
     */
    @Test
    public void testAddCreditAmountZero() {
        //Utilizador em que se adiciona créditos
        User u = brs.getUsers().get(1);

        brs.addCredit(1, 0);

        //O crédito foi adicionado com sucesso
        assertEquals(0, u.getCredit(), "Expected =1 , Actual = " + u.getCredit());
    }




    // REGISTER USER


    /*
Teste para tentar registar utilizador que já existe COM IDUser=0 e rentalProgram=1
 */
    @Test
    public void testRegisterUserExist() {

        //Excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {

            brs.registerUser(0, "Rafael", 1);

            //O teste é válido
        }, "Should Throw Exception: UserAlreadyExists");

    }
    /*
    Teste para registar utilizador que ainda não existe
     */
    @Test
    public void testRegisterUserValido() throws UserAlreadyExists{

        brs.registerUser(2,"Nuno", 2);

        //O teste é válido
        assertEquals(2, brs.getUsers().get(2).getIDUser());

    }
/*
Teste Utilizador já existe COM IDUser=1 e rentalProgram=2
 */
    @Test
    public void testRegisterUserUSERJAEXISTE() {
        //Verificar se a excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {
            brs.registerUser(1, "Gabriel", 2);
        }, "Should Throw Exception: UserAlreadyExists"); //O teste é válido

    }
/*
Teste IDUser inválido
 */
    @Test
    public void testRegisterUserIDINVALIDO() {
        //Verificar se a excepção é lançada
        assertThrows(IndexOutOfBoundsException.class, () -> {

            User u = brs.getUsers().get(-1);

            brs.registerUser(-1, "Nuno", 2);

            assertEquals(null, u.getIDUser(), "Expected = null, Actual= "+ u.getIDUser());

        }, "Shoud throw Exception: IndexOutOfBoundsException");


    }
/*
Teste utilizador já existe com IDUser=0
 */
    @Test
    public void testRegisterUserJAEXISTECOMZERO() {
        //Verificar se a excepção é lançada
        assertThrows(UserAlreadyExists.class, () -> {
            brs.registerUser(0, "Rafael", 1);
        }, "Should Throw Exception: UserAlreadyExists"); //O teste é válido

    }




    //RETURN BYCICLE


    /*
Teste utilizador não existe
     */
    @Test
    public void testReturnBicycleUSERNAOEXISTE(){

        //Testar se retorna -1 se o IDUser não existir
        assertThrows(UserDoesNotExists.class, () -> {
            brs.getBicycle(1, 4, 1);
        }, "Should Throw Exception: UserAlreadyExists"); //O teste é válido

    }
    /*
    Teste IDDeposit não existe
     */
    @Test
    public void testReturnBicycleDEPOSITNAOEXISTE()throws UserDoesNotExists{

        //Testar se retorna -1 se o IDDeposit não existir
            assertEquals(-1, brs.getBicycle(4, 1, 1));

    }
    /*
    Teste bicleta não está associada a lugar ativo (sarttime=-1)
     */
    @Test
    public void testReturnBicycleBICICLETANAOASSOCIADA() throws UserDoesNotExists{
        //Testar se retorna -1 se o a bicleta não está associada a lugar ativo (starttime=-1)
        assertEquals(-1, brs.getBicycle(4, 1, -1));
    }
/*
Teste sem lugares livres (endtime=-1)
 */
    @Test
    public void testReturnBicycleSEMLUGARESLIVRES() {


        //Senão existirem lugares de entrega livre (endtime=-1)
        assertEquals(-1, brs.returnBicycle(1, 1, -1));

    }
/*
Teste Retornar saldo IDUser=1
 */
    @Test
    public void testReturnBicycleRETORNASALDO() {
        //Testar se retorna o saldo atual do cliente, quando se calcula o pagamento
        assertFalse( brs.verifyCredit(1));
    }

}
