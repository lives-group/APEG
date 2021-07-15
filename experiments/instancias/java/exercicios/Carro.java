

public class Carro {

    int v; // Velocidade
    String cor;

    void acelerar(int a) {
	v += a;
    }

    void imprime() {
	System.out.println("Velocidade: " + v + ", cor: " + cor);
    }
    
    public static void main(String args[]) {
	Carro elton, meu;

	elton = new Carro();
	meu = new Carro();
	
	elton.v = 10;
	elton.cor = "prata";
	
	meu.v = 60;
	meu.cor = "branco";

	elton.imprime();
	meu.imprime();

	elton.acelerar(100);

	elton.imprime();
	meu.imprime();
    }
}
