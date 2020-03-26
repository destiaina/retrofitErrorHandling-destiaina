package id.putraprima.retrofit.api.models;

public class LoginRequest {

    public String Email, Password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.Email = email;
        Password = password;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
}