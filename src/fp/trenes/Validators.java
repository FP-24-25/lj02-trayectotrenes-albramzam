package fp.trenes;

public class Validators {
	
	public static Boolean sonDigitos(String cadena) {
		//para todo
		Boolean res = true;
		//for clásico
		//inicializacion; condición; incremento
		for (int i = 0;i<cadena.length();i++) {
			Character c = cadena.charAt(i);
			if (!Character.isDigit(c)) {
				res = false;
				break;
			}
		}
		return res;
	}

	public static boolean sonLetras(String cadena) {
		Boolean res = true;
		//for clásico
		//inicializacion; condición; incremento
		for (int i = 0;i<cadena.length();i++) {
			Character c = cadena.charAt(i);
			if (Character.isDigit(c)) {
				res = false;
				break;
			}
		}
		return res;
	}

}
