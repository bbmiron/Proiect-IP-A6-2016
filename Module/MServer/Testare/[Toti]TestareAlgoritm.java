import customexceptions.InvalidTypesException;
import ip.Algoritm;
import ip.BazaDeDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestareAlgoritm {
    
    public TestareAlgoritm() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void Test1() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        ArrayList<Integer> rez = new ArrayList();
        rez.add(2);
        assertEquals("",rez,algo.getListaCalculatoare(1,2));
    }
    
    @Test
    public void Test2() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        ArrayList<Integer> rez = new ArrayList();
        rez.add(2); rez.add(3); rez.add(6);
        assertEquals("",rez,algo.getListaCalculatoare(1,7));
    }
    
    @Test(expected=InvalidTypesException.class)
    public void Test3() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        algo.getListaCalculatoare(7, 1);
    }
    
    @Test(expected=InvalidTypesException.class)
    public void Test4() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        algo.getListaCalculatoare(1,17);
    }
    
    @Test(expected=InvalidTypesException.class)
    public void Test5() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        algo.getListaCalculatoare(11,72);
    }
    
    @Test(expected=InvalidTypesException.class)
    public void Test6() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        algo.getListaCalculatoare(0, 0);
    }
    
    @Test(expected=InvalidTypesException.class)
    public void Test7() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        algo.getListaCalculatoare(4, 3);
    }
    
    @Test
    public void Test8() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(0);
        Algoritm algo = new Algoritm(bd);
        ArrayList<Integer> rez = new ArrayList();
        assertEquals("",rez,algo.getListaCalculatoare(4, 4));
    }
    
    @Test(expected=InvalidTypesException.class)
    public void Test9() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(1);
        Algoritm algo = new Algoritm(bd);
        algo.getListaCalculatoare(1, 5);
    }
    
    @Test
    public void Test10() throws InvalidTypesException{
        BazaDeDate bd = new BazaDeDate(1);
        Algoritm algo = new Algoritm(bd);
        ArrayList<Integer> rez = new ArrayList();
        rez.add(2);
        assertEquals("",rez,algo.getListaCalculatoare(1, 2));
    }
}
