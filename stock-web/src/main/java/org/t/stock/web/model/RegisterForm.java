package org.t.stock.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.joda.time.DateTime;
import org.springframework.format.annotation.NumberFormat;
import org.t.stock.model.stock.WalletStock;

/**
 *
 * @author TOM
 */
public class RegisterForm implements Serializable {

    private static final long serialVersionUID = -9044886138277306745L;

    @Size(min = 1, max = 45)
    @NotNull
    private String firstName;
    @Size(min = 1, max = 45)
    @NotNull
    private String lastName;
    @Size(min = 5, max = 45)
    private String login;
    @Size(min = 5, max = 45) 
    private String password;
    @Size(min = 5, max = 45)
    @NotNull
    private String confirmPassword;

    @NumberFormat(pattern = "0.00", style = NumberFormat.Style.CURRENCY)
    private BigDecimal money;
    private ArrayList<WalletStock> walletStocks;
    private DateTime publicationDate;

    public RegisterForm() {
    }

    public RegisterForm(String firstName, String lastName, String login, String password, String confirmPassword, BigDecimal money, ArrayList<WalletStock> walletStocks, DateTime publicationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.money = money;
        this.walletStocks = walletStocks;
        this.publicationDate = publicationDate;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public ArrayList<WalletStock> getWalletStocks() {
        return walletStocks;
    }

    public void setWalletStocks(ArrayList<WalletStock> walletStocks) {
        this.walletStocks = walletStocks;
    }

    public DateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(DateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public static class RegisterFormBuilder {

        private String firstName;
        private String lastName;
        private String login;
        private String password;
        private String confirmPassword;
        private BigDecimal money;
        private ArrayList<WalletStock> walletStocks;
        private DateTime publicationDate;

        public RegisterFormBuilder() {
        }

        public RegisterFormBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public RegisterFormBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RegisterFormBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public RegisterFormBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RegisterFormBuilder setConfirmPasswordPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public RegisterFormBuilder setMoney(BigDecimal money) {
            this.money = money;
            return this;
        }

        public RegisterFormBuilder setWalletStocks(ArrayList<WalletStock> walletStocks) {
            this.walletStocks = walletStocks;
            return this;
        }

        public RegisterFormBuilder setPublicationDate(DateTime publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public RegisterForm createRegisterForm() {
            return new RegisterForm(firstName, lastName, login, password, confirmPassword, money, walletStocks, publicationDate);
        }

    }
}
