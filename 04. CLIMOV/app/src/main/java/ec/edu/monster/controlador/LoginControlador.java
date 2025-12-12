package ec.edu.monster.controlador;

public class LoginControlador {
    public Boolean login(String username, String password) {
        if (username.equals("MONSTER") && password.equals("MONSTER9")) {
            return true;
        }
        return false;
    }

}
