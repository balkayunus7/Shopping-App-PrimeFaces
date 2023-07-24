package com.example;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class AuthBean {

    private String email;
    private String password;
    private List<UserModel> userList;

    // Getter ve setter metotları
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthBean() {
        userList = new ArrayList<>();
        // Örnek kullanıcıları listeye ekleyelim
        userList.add(new UserModel("john@example.com", "john123"));
        userList.add(new UserModel("emma@example.com", "emma456"));
        userList.add(new UserModel("bob@example.com", "bob789"));
    }

    public String login() {
        // Burada gerçek bir kullanıcı doğrulama işlemi yapabilirsiniz.
        // Örnek olarak, kullanıcıyı listede arayalım:
        for (UserModel user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // Başarılı giriş durumunda, kullanıcıyı ana sayfaya yönlendirin.
                return "index.xhtml?faces-redirect=true";
            }
        }
        String errorMessage = "Invalid email or password.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
        return null;
    }

}
